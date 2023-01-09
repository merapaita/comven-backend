package com.rosist.comven.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rosist.comven.model.Rol;
import com.rosist.comven.repo.IGenericRepo;
import com.rosist.comven.repo.IRolRepo;
import com.rosist.comven.service.IRolService;

@Service
public class RolServiceImpl extends CRUDImpl<Rol, Integer> implements IRolService {

	@Autowired
	private IRolRepo repo;
	
	@Override
	protected IGenericRepo<Rol, Integer> getRepo() {
		return repo;
	}
	
	@Override
	public Page<Rol> listarPageable(Pageable page) {
		return repo.findAll(page);
	}

}