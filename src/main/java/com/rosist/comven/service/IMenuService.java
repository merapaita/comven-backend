package com.rosist.comven.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rosist.comven.model.Menu;

public interface IMenuService extends ICRUD<Menu, Integer> {

	List<Menu> lista() throws Exception;
	
	List<Menu> listarMenuPorUsuario(String nombre);
	
	Page<Menu> listarPageable(Pageable page);

}
