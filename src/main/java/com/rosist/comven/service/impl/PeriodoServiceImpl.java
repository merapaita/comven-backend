package com.rosist.comven.service.impl;

import java.sql.SQLException;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rosist.comven.model.Periodo;
import com.rosist.comven.repo.IPeriodoRepo;
import com.rosist.comven.repo.IGenericRepo;
import com.rosist.comven.service.IPeriodoService;

@Service
public class PeriodoServiceImpl extends CRUDImpl<Periodo, Integer> implements IPeriodoService {

	@Autowired
	private IPeriodoRepo repo;
	
    private static Logger logger = LoggerFactory.getLogger(PeriodoServiceImpl.class);
    
	@Override
	protected IGenericRepo<Periodo, Integer> getRepo() {
		return repo;
	}
	
	@Override
	public Page<Periodo> listaPageable(Pageable page) {
		return repo.findAll(page);
	}

	@Transactional
	@Override
	public Periodo registraTransaccion(Periodo periodo) throws SQLException, Exception {
//        String cUser = SecurityContextHolder.getContext().getAuthentication().getName();
//        LocalDateTime   dUser = LocalDateTime.now();
	    logger.info("registrarTransaccion...grabada.-> " + periodo);
        
//		if (periodo.getIdPeriodo()==0) {
	        periodo.setEstdo(true);
//		}

		repo.save(periodo);
		
		return periodo;
		/*for(DetalleConsulta det : consulta.getDetalleConsulta()) {
			det.setConsulta(consulta);
		}*/
	}

	@Transactional
	@Override
	public Periodo modificaTransaccion(Periodo periodo) throws Exception {
	    logger.info("modificando periodo...grabada.-> ");
//      String cUser = SecurityContextHolder.getContext().getAuthentication().getName();
//		LocalDateTime   dUser = LocalDateTime.now();
//		proveedor.setUserup("cUser");
//		proveedor.setDusercr(dUser);
		repo.save(periodo);
//		repo.modificaPeriodo(periodo.getNombre(), periodo.getDireccion(), periodo.getIdPeriodo());
		return periodo;
	}

}
