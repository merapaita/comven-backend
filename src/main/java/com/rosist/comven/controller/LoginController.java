package com.rosist.comven.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosist.comven.model.ResetToken;
import com.rosist.comven.model.Usuario;
import com.rosist.comven.service.ILoginService;
import com.rosist.comven.service.IResetTokenService;
import com.rosist.comven.util.EmailUtil;
import com.rosist.comven.util.Mail;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private ILoginService service;
	
	@Autowired	
	private IResetTokenService tokenService;
	
	@Autowired
	private EmailUtil emailUtil;
	
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    
	@PostMapping(value = "/enviarCorreo", consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<Integer> enviarCorreo(@RequestBody String correo) throws Exception {
		int rpta = 0;
		
//		logger.info("enviarcorreo...");
		Usuario us = service.verificarCorreoUsuario(correo);
//		logger.info("enviarcorreo...us:" + us);
		if(us != null && us.getIdUsuario() > 0) {
			ResetToken token = new ResetToken();
			token.setToken(UUID.randomUUID().toString());
			token.setUser(us);
			token.setExpiracion(10);
			tokenService.guardar(token);
			
			Mail mail = new Mail();
			mail.setFrom("email.prueba.demo@gmail.com");
			mail.setTo(us.getCorreo());
			mail.setSubject("RESTABLECER CONTRASEÑA - LABORATORIO");
			
			Map<String, Object> model = new HashMap<>();
			String url = "http://localhost:8080/comven-frontend/#/recuperar/" + token.getToken();
//			String url = "http://localhost:4200/recuperar/" + token.getToken();
			model.put("user", token.getUser().getCorreo());
//			model.put("user", token.getUser().getUsername());
			model.put("resetUrl", url);
			mail.setModel(model);
			
			emailUtil.enviarMail(mail);
			
			rpta = 1;
		}
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
	
	@GetMapping(value = "/restablecer/verificar/{token}")
	public ResponseEntity<Integer> verificarToken(@PathVariable("token") String token) {
		int rpta = 0;
//		logger.info("xx-1 token " + token);
		try {
			if (token != null && !token.isEmpty()) {
				ResetToken rt = tokenService.findByToken(token);
//				logger.info("xx-1 " + rt);
				if (rt != null && rt.getId() > 0) {
					if (!rt.estaExpirado()) {
						rpta = 1;
					}
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
	
	@GetMapping(value = "/perfil/{token}")
	public ResponseEntity<Integer> perfil(@PathVariable("token") String token) {
		int rpta = 0;
		logger.info("xx-1 token " + token);
		try {
			if (token != null && !token.isEmpty()) {
				ResetToken rt = tokenService.findByToken(token);
				logger.info("xx-1 " + rt);
				if (rt != null && rt.getId() > 0) {
					if (!rt.estaExpirado()) {
						rpta = 1;
					}
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
	
	@PostMapping(value = "/restablecer/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> restablecerClave(@PathVariable("token") String token, @RequestBody String clave) {		
		try {
			ResetToken rt = tokenService.findByToken(token);			
			service.cambiarClave(clave, rt.getUser().getUsername());
			tokenService.eliminar(rt);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
}
