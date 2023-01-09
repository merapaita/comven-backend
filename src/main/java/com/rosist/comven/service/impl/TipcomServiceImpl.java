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
import com.rosist.comven.model.Tipcom;
import com.rosist.comven.repo.IGenericRepo;
import com.rosist.comven.repo.ITipcomRepo;
import com.rosist.comven.service.ITipcomService;

@Service
public class TipcomServiceImpl extends CRUDImpl<Tipcom, Integer> implements ITipcomService {

	@Autowired
	private ITipcomRepo repo;
	
    private static Logger logger = LoggerFactory.getLogger(TipcomServiceImpl.class);
    
	@Override
	protected IGenericRepo<Tipcom, Integer> getRepo() {
		return repo;
	}
	
	@Override
	public Page<Tipcom> listarPageable(Pageable page) {
		return repo.findAll(page);
	}

	@Transactional
	@Override
	public Tipcom registraTransaccion(Tipcom tipcom) throws SQLException, Exception {
//        String cUser = SecurityContextHolder.getContext().getAuthentication().getName();
//        LocalDateTime   dUser = LocalDateTime.now();
	    logger.info("registrarTransaccion...grabada.-> " + tipcom);
        
		if (tipcom.getIdTipcom()==null) {
//	        tipcom.setEstado("00");
//		    cliente.setUserup("cUser");
//		    cliente.setDuserup(dUser);
//		} else {
//		    proveedor.setUsercr("cUser");
//		    proveedor.setDusercr(dUser);
		}

		repo.save(tipcom);
//	    log.info("registrarTransaccion...grabada.-> ");
		
		return tipcom;
		/*for(DetalleConsulta det : consulta.getDetalleConsulta()) {
			det.setConsulta(consulta);
		}*/
	}

	@Transactional
	@Override
	public Tipcom modificaTransaccion(Tipcom tipcom) throws Exception {
	    logger.info("modificando cliente...grabada.-> ");
//      String cUser = SecurityContextHolder.getContext().getAuthentication().getName();
//		LocalDateTime   dUser = LocalDateTime.now();
//		proveedor.setUserup("cUser");
//		proveedor.setDusercr(dUser);
		repo.modificaTipcom(tipcom.getTipcom(), tipcom.getDescripcion(), tipcom.getIdTipcom());
		return tipcom;
	}

}
