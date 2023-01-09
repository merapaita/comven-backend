package com.rosist.comven.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rosist.comven.model.Usuario;
import com.rosist.comven.repo.ILoginRepo;
import com.rosist.comven.service.ILoginService;

@Service
public class LoginServiceImpl implements ILoginService{
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private ILoginRepo repo;
	
	@Override
	public Usuario verificarNombreUsuario(String usuario) {
		return repo.verificarNombreUsuario(usuario);
	}
	
	@Override
	public Usuario verificarCorreoUsuario(String correo) {
		return repo.verificarCorreoUsuario(correo);
	}
	
	@Override
	public void cambiarClave(String clave, String nombre) {
		repo.cambiarClave(bcrypt.encode(clave), nombre);
	}
	
}
