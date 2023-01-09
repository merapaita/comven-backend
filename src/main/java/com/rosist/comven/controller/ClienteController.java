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
import com.rosist.comven.model.Cliente;
import com.rosist.comven.repo.IClienteRepo;
import com.rosist.comven.service.IClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private IClienteService service;
	@Autowired
	private IClienteRepo repo;
	
    private static Logger logger = LoggerFactory.getLogger(ClienteController.class);
    
	@GetMapping
	public ResponseEntity<List<Cliente>> listar() throws Exception{
		return new ResponseEntity<>(service.listar(), HttpStatus.OK);
	}
    
	@GetMapping("/pageable")
	  public ResponseEntity<Map<String, Object>> getAllCliente(
		        @RequestParam(value="nombre", required = false) String nombre,
		        @RequestParam(value="page", defaultValue = "0") int page,
		        @RequestParam(value="size", defaultValue = "10") int size
			  ) {
		
		List<Cliente> cliente = new ArrayList<Cliente>();
		Pageable paging = PageRequest.of(page, size);
		
		Page<Cliente> pageTuts;
		if (nombre != null) {
			pageTuts = repo.listaPorNombre(nombre, paging);
		}
		else {
			pageTuts = repo.findAll(paging);
		}

//		cliente = pageTuts.getContent();

		Map<String, Object> response = new HashMap<>();
		response.put("content", pageTuts.getContent());
		response.put("number", pageTuts.getNumber());
		response.put("totalElements", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Cliente obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModelNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		return new ResponseEntity<>(obj, HttpStatus.OK);		
	}
	
//	@GetMapping("/ruc/{ruc}")
//	public ResponseEntity<Cliente> listarPorRuc(@PathVariable("ruc") String ruc) throws Exception{
//		Cliente obj = service.buscaPorRUC(ruc);
////		if(obj == null) {
////			throw new ModelNotFoundException("ID NO ENCONTRADO " + ruc);
////		}
//		return new ResponseEntity<>(obj, HttpStatus.OK);		
//	}

	@PostMapping
	public ResponseEntity<Cliente> registrar(@Valid @RequestBody Cliente cliente) throws Exception {
		Cliente obj = service.registraTransaccion(cliente);
		//localhost:8080/medicos/1
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdCliente()).toUri();
		return ResponseEntity.created(location).build();
//		return new ResponseEntity<>(service.registrar(paciente), HttpStatus.CREATED);		// 201
	}
	
	@PutMapping
	public ResponseEntity<Cliente> modificar(@Valid @RequestBody Cliente cliente) throws Exception {
	logger.info("midificar...cliente,.> " + cliente);
		return new ResponseEntity<>(service.modificaTransaccion(cliente), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<Cliente> listarHateoasPorId(@PathVariable("id") Integer id) throws Exception{
		Cliente obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModelNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		EntityModel<Cliente> recurso = EntityModel.of(obj);
		//localhost:8080/medicos/hateoas/1
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarHateoasPorId(id));
		WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).listarHateoasPorId(id));
		recurso.add(link1.withRel("Paciente-recurso1"));
		recurso.add(link2.withRel("Paciente-recurso2"));
		
		return recurso;
		
	}
	
}
