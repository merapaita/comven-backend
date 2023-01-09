package com.rosist.comven.service;

import java.sql.SQLException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rosist.comven.model.Periodo;

public interface IPeriodoService extends ICRUD<Periodo, Integer> {

	public Periodo registraTransaccion(Periodo periodo) throws SQLException, Exception;
	public Periodo modificaTransaccion(Periodo periodo) throws Exception;
	public Page<Periodo> listaPageable(Pageable page);
	
}
