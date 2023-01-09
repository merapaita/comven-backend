package com.rosist.comven.service;

import java.sql.SQLException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rosist.comven.model.Cliente;

public interface IClienteService extends ICRUD<Cliente, Integer> {

	public Cliente registraTransaccion(Cliente cliente) throws SQLException, Exception;
	public Cliente modificaTransaccion(Cliente cliente) throws Exception;
	public Page<Cliente> listarPageable(Pageable page);
	
}
