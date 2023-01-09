package com.rosist.comven.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rosist.comven.model.Empresa;

public interface IEmpresaRepo extends IGenericRepo<Empresa, Integer> {

	@Modifying
	@Query(value = "UPDATE Empresa e SET e.razsoc=:razsoc, e.direccion=:direccion WHERE e.id_empresa=:idEmpresa", nativeQuery = true)
	void modificaEmpresa(@Param("razsoc") String razsoc, @Param("direccion") String direccion, @Param("idEmpresa") Integer idEmpresa );
	
}