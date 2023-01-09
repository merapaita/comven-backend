package com.rosist.comven.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosist.comven.model.Parmae;
import com.rosist.comven.repo.IGenericRepo;
import com.rosist.comven.repo.IParmaeRepo;
import com.rosist.comven.service.IParmaeService;

@Service
public class ParmaeServiceImpl extends CRUDImpl<Parmae, Integer> implements IParmaeService  {

	@Autowired
	private IParmaeRepo repo;
	
	@Override
	protected IGenericRepo<Parmae, Integer> getRepo() {
		return repo;
	}

	@Override
	public List<Parmae> lista() throws Exception {
		List<Parmae> lParmae = repo.listar();
		return lParmae;
	}
	
	@Override
	public List<Parmae> listaPorTipo(String tipo) throws Exception {
		List<Parmae> lParmae = repo.listarPorTipo(tipo);
		return lParmae;
	}
	
	@Override
	public Parmae buscaPorCodigo(String tipo, String codigo) throws Exception {
		Parmae Parmae = repo.buscaPorCodigo(tipo, codigo);
		return Parmae;
	}

}
