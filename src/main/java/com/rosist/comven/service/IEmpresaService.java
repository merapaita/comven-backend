package com.rosist.comven.service;

import java.sql.SQLException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rosist.comven.model.Cliente;
import com.rosist.comven.model.Empresa;

public interface IEmpresaService extends ICRUD<Empresa, Integer> {

	public Empresa registraTransaccion(Empresa empresa) throws SQLException, Exception;
	public Empresa modificaTransaccion(Empresa empresa) throws SQLException, Exception;
	Page<Empresa> listarPageable(Pageable page);
	
}