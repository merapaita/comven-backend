package com.rosist.comven.repo;

import com.rosist.comven.model.Periodo;

public interface IPeriodoRepo extends IGenericRepo<Periodo, Integer> {

//	@Modifying
//	@Query("UPDATE Cliente c SET c.nombre=:nombre, c.direccion=:direccion WHERE c.idCliente =:idCliente")
//	void modificaCliente(@Param("nombre") String nombre, @Param("direccion") String direccion, @Param("idCliente") Integer idCliente );

//	@Query(value = "select * from periodo", nativeQuery = true)
//	Page<Periodo> listaPageable(Pageable page);
	
}