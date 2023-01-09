package com.rosist.comven.service.impl;

import java.sql.SQLException;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rosist.comven.model.Cliente;
import com.rosist.comven.repo.IClienteRepo;
import com.rosist.comven.repo.IGenericRepo;
import com.rosist.comven.service.IClienteService;

@Service
public class ClienteServiceImpl extends CRUDImpl<Cliente, Integer> implements IClienteService {

	@Autowired
	private IClienteRepo repo;
	
    private static Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);
    
	@Override
	protected IGenericRepo<Cliente, Integer> getRepo() {
		return repo;
	}
	
	@Override
	public Page<Cliente> listarPageable(Pageable page) {
		return repo.findAll(page);
	}

	@Transactional
	@Override
	public Cliente registraTransaccion(Cliente cliente) throws SQLException, Exception {
//        String cUser = SecurityContextHolder.getContext().getAuthentication().getName();
//        LocalDateTime   dUser = LocalDateTime.now();
	    logger.info("registrarTransaccion...grabada.-> " + cliente);
        
		if (cliente.getIdCliente()==0) {
	        cliente.setEstado("00");
//		    cliente.setUserup("cUser");
//		    cliente.setDuserup(dUser);
//		} else {
//		    proveedor.setUsercr("cUser");
//		    proveedor.setDusercr(dUser);
		}

		repo.save(cliente);
//	    log.info("registrarTransaccion...grabada.-> ");
		
		return cliente;
		/*for(DetalleConsulta det : consulta.getDetalleConsulta()) {
			det.setConsulta(consulta);
		}*/
	}

	@Transactional
	@Override
	public Cliente modificaTransaccion(Cliente cliente) throws Exception {
	    logger.info("modificando cliente...grabada.-> ");
//      String cUser = SecurityContextHolder.getContext().getAuthentication().getName();
//		LocalDateTime   dUser = LocalDateTime.now();
//		proveedor.setUserup("cUser");
//		proveedor.setDusercr(dUser);
		repo.modificaCliente(cliente.getNombre(), cliente.getDireccion(), cliente.getIdCliente());
		return cliente;
	}

}
