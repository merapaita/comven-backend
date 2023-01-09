package com.rosist.comven.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rosist.comven.model.Venta;

public interface IVentaService extends ICRUD<Venta, Integer> {

	public Venta registraTransaccion(Venta venta) throws SQLException, Exception;
	public Venta modificaTransaccion(Venta venta) throws Exception;
	public List<Venta> listaVentas(Integer idEmpresa, Integer anno, Integer mes, Integer page, Integer size) throws Exception;
	public Page<Venta> listarPageable(Pageable page);
	
}
