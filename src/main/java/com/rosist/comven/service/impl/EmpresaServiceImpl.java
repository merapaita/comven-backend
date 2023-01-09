package com.rosist.comven.service.impl;

import java.sql.SQLException;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rosist.comven.model.Empresa;
import com.rosist.comven.model.Usuario;
import com.rosist.comven.repo.IEmpresaRepo;
import com.rosist.comven.repo.IGenericRepo;
import com.rosist.comven.repo.IUsuarioRepo;
import com.rosist.comven.service.IEmpresaService;

@Service
public class EmpresaServiceImpl extends CRUDImpl<Empresa, Integer> implements IEmpresaService {

	@Autowired
	private IEmpresaRepo repo;
	@Autowired
	private IUsuarioRepo repoUsuario;
	
    private static Logger logger = LoggerFactory.getLogger(EmpresaServiceImpl.class);
    
	@Override
	protected IGenericRepo<Empresa, Integer> getRepo() {
		return repo;
	}
	
	@Override
	public Page<Empresa> listarPageable(Pageable page) {
		return repo.findAll(page);
	}

	@Transactional
	@Override
	public Empresa registraTransaccion(Empresa empresa) throws SQLException, Exception {
		Usuario usuario = null;
//		if (empresa.getIdEmpresa()==0) {
//			// agregamos el id_usuario
//			usuario = repoUsuario.findOneByUsername(empresa.getUsername());
//			empresa.setUsuario(usuario);
//		}

		repo.save(empresa);
		return empresa;
	}

	@Transactional
	@Override
	public Empresa modificaTransaccion(Empresa empresa) throws SQLException, Exception {
	    logger.info("modificando empresa...grabada.-> ");
		repo.modificaEmpresa(empresa.getRazsoc(), empresa.getDireccion(), empresa.getIdEmpresa());
		return empresa;
	}
	
}
