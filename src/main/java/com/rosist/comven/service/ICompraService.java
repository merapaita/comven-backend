package com.rosist.comven.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rosist.comven.model.Compra;

public interface ICompraService extends ICRUD<Compra, Integer> {

	public Compra registraTransaccion(Compra compra) throws SQLException, Exception;
	public Compra modificaTransaccion(Compra compra) throws Exception;
	public List<Compra> listaCompras(Integer idEmpresa, Integer anno, Integer mes, Integer page, Integer size) throws Exception;
	Page<Compra> listarPageable(Pageable page);

}