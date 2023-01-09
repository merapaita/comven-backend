package com.rosist.comven.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rosist.comven.exception.ModelNotFoundException;
import com.rosist.comven.model.Tipcom;
import com.rosist.comven.repo.ITipcomRepo;
import com.rosist.comven.service.ITipcomService;

@RestController
@RequestMapping("/tipcom")
public class TipcomController {

	@Autowired
	private ITipcomService service;
	@Autowired
	private ITipcomRepo repo;
	
    private static Logger logger = LoggerFactory.getLogger(TipcomController.class);
    
	@GetMapping
	public ResponseEntity<List<Tipcom>> listar() throws Exception{
		return new ResponseEntity<>(service.listar(), HttpStatus.OK);
	}
    
	@GetMapping("/pageable")
	  public ResponseEntity<Map<String, Object>> getAllTipcom(
		        @RequestParam(value="descripcion", required = false) String descri,
		        @RequestParam(value="page", defaultValue = "0") int page,
		        @RequestParam(value="size", defaultValue = "10") int size
			  ) {
		
		List<Tipcom> tipcom = new ArrayList<Tipcom>();
		Pageable paging = PageRequest.of(page, size);
		
		Page<Tipcom> pageTuts;
//		logger.info("getAllSecfun...periodo:" + periodo);
//		logger.info("getAllSecfun...secfun:" + cadena);
		if (descri != null) {
			pageTuts = repo.listaPorDescri(descri, paging);	// ojo esta es la linea que vale
		}
		else {
			pageTuts = repo.findAll(paging);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("content", pageTuts.getContent());
		response.put("number", pageTuts.getNumber());
		response.put("totalElements", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tipcom> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Tipcom obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModelNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		return new ResponseEntity<>(obj, HttpStatus.OK);		
	}
	
//	@GetMapping("/ruc/{ruc}")
//	public ResponseEntity<Tipcom> listarPorRuc(@PathVariable("ruc") String ruc) throws Exception{
//		Tipcom obj = service.buscaPorRUC(ruc);
////		if(obj == null) {
////			throw new ModelNotFoundException("ID NO ENCONTRADO " + ruc);
////		}
//		return new ResponseEntity<>(obj, HttpStatus.OK);		
//	}

	@PostMapping
	public ResponseEntity<Tipcom> registrar(@Valid @RequestBody Tipcom tipcom) throws Exception {
		Tipcom obj = service.registraTransaccion(tipcom);
		//localhost:8080/medicos/1
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdTipcom()).toUri();
		return ResponseEntity.created(location).build();
//		return new ResponseEntity<>(service.registrar(paciente), HttpStatus.CREATED);		// 201
	}
	
	@PutMapping
	public ResponseEntity<Tipcom> modificar(@Valid @RequestBody Tipcom tipcom) throws Exception {
	logger.info("midificar...tipcom,.> " + tipcom);
		return new ResponseEntity<>(service.modificaTransaccion(tipcom), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<Tipcom> listarHateoasPorId(@PathVariable("id") Integer id) throws Exception{
		Tipcom obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModelNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		EntityModel<Tipcom> recurso = EntityModel.of(obj);
		//localhost:8080/medicos/hateoas/1
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarHateoasPorId(id));
		WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).listarHateoasPorId(id));
		recurso.add(link1.withRel("Paciente-recurso1"));
		recurso.add(link2.withRel("Paciente-recurso2"));
		
		return recurso;
		
	}
	
}
