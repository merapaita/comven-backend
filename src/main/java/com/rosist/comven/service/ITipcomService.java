package com.rosist.comven.service;

import java.sql.SQLException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rosist.comven.model.Cliente;
import com.rosist.comven.model.Tipcom;

public interface ITipcomService extends ICRUD<Tipcom, Integer> {

	public Tipcom registraTransaccion(Tipcom tipcom) throws SQLException, Exception;
	public Tipcom modificaTransaccion(Tipcom tipcom) throws Exception;
	Page<Tipcom> listarPageable(Pageable page);
	
}
