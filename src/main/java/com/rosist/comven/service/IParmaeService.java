package com.rosist.comven.service;

import java.util.List;

import com.rosist.comven.model.Parmae;

public interface IParmaeService extends ICRUD<Parmae, Integer> {

	List<Parmae> lista() throws Exception;
	List<Parmae> listaPorTipo(String tipo) throws Exception;
	Parmae buscaPorCodigo(String tipo, String Codigo) throws Exception;
	
}
