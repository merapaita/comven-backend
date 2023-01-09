package com.rosist.comven.service;

import java.sql.SQLException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rosist.comven.model.Proveedor;

public interface IProveedorService extends ICRUD<Proveedor, Integer> {

	public Proveedor registraTransaccion(Proveedor proveedor) throws SQLException, Exception;
	public Proveedor modificaTransaccion(Proveedor proveedor) throws Exception;
	Page<Proveedor> listarPageable(Pageable page);
	
}
