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
import com.rosist.comven.model.Proveedor;
import com.rosist.comven.repo.IGenericRepo;
import com.rosist.comven.repo.IProveedorRepo;
import com.rosist.comven.service.IProveedorService;

@Service
public class ProveedorServiceImpl extends CRUDImpl<Proveedor, Integer> implements IProveedorService {

	@Autowired
	private IProveedorRepo repo;
	
    private static Logger logger = LoggerFactory.getLogger(ProveedorServiceImpl.class);
    
	@Override
	protected IGenericRepo<Proveedor, Integer> getRepo() {
		return repo;
	}
	
	@Override
	public Page<Proveedor> listarPageable(Pageable page) {
		return repo.findAll(page);
	}

	@Transactional
	@Override
	public Proveedor registraTransaccion(Proveedor proveedor) throws SQLException, Exception {
//        String cUser = SecurityContextHolder.getContext().getAuthentication().getName();
//        LocalDateTime   dUser = LocalDateTime.now();
	    logger.info("registrarTransaccion...grabada.-> " + proveedor);
        
		if (proveedor.getIdProveedor()==0) {
	        proveedor.setEstado("00");
//		    cliente.setUserup("cUser");
//		    cliente.setDuserup(dUser);
//		} else {
//		    proveedor.setUsercr("cUser");
//		    proveedor.setDusercr(dUser);
		}

		repo.save(proveedor);
//	    log.info("registrarTransaccion...grabada.-> ");
		
		return proveedor;
		/*for(DetalleConsulta det : consulta.getDetalleConsulta()) {
			det.setConsulta(consulta);
		}*/
	}

	@Transactional
	@Override
	public Proveedor modificaTransaccion(Proveedor proveedor) throws Exception {
	    logger.info("modificando proveedor...grabada.-> ");
//      String cUser = SecurityContextHolder.getContext().getAuthentication().getName();
//		LocalDateTime   dUser = LocalDateTime.now();
//		proveedor.setUserup("cUser");
//		proveedor.setDusercr(dUser);
		repo.modificaProveedor(proveedor.getNombre(), proveedor.getDireccion(), proveedor.getIdProveedor());
		return proveedor;
	}
	
}