package com.rosist.comven.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rosist.comven.model.Usuario;
public interface IUsuarioService extends ICRUD<Usuario, Integer> {

	Integer getNewId();

	Page<Usuario> listarPageable(Pageable page);
	
//	Usuario registrarRoles(Usuario usuario) throws Exception;

}
