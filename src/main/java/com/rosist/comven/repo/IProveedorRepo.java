package com.rosist.comven.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rosist.comven.model.Proveedor;

public interface IProveedorRepo extends IGenericRepo<Proveedor, Integer> {
	
	@Modifying
	@Query("UPDATE Proveedor p SET p.nombre=:nombre, p.direccion=:direccion WHERE p.idProveedor =:idProveedor")
	void modificaProveedor(@Param("nombre") String nombre, @Param("direccion") String direccion, @Param("idProveedor") Integer idProveedor );
	
	@Query(value = "select * from proveedor where nombre=:nombre", nativeQuery = true)
	Page<Proveedor> listaPorNombre(@Param("nombre") String nombre, Pageable page);
	
	@Query(value = "select * from proveedor where tipdoc=:tipdoc and docprv=:docprv", nativeQuery = true)
	Proveedor listaPorDocumento(@Param("tipdoc") String tipdoc, 
			                          @Param("docprv") String docprv);
	
}