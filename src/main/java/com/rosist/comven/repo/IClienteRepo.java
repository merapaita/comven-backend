package com.rosist.comven.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rosist.comven.model.Cliente;

public interface IClienteRepo extends IGenericRepo<Cliente, Integer> {

	@Modifying
	@Query("UPDATE Cliente c SET c.nombre=:nombre, c.direccion=:direccion WHERE c.idCliente =:idCliente")
	void modificaCliente(@Param("nombre") String nombre, @Param("direccion") String direccion, @Param("idCliente") Integer idCliente );

	@Query(value = "select * from cliente where nombre=:nombre", nativeQuery = true)
	Page<Cliente> listaPorNombre(@Param("nombre") String nombre, Pageable page);
	
}