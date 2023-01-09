package com.rosist.comven.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rosist.comven.model.Tipcom;

public interface ITipcomRepo extends IGenericRepo<Tipcom, Integer> {

	@Modifying
	@Query("UPDATE Tipcom t SET t.tipcom=:tipcom, t.descripcion=:descripcion WHERE t.idTipcom =:idTipcom")
	void modificaTipcom(@Param("tipcom") String tipcom, @Param("descripcion") String descripcion, @Param("idTipcom") Integer idTipcom );
	
	@Query(value = "select * from tipcom where descripcion=:descrin", nativeQuery = true)
	Page<Tipcom> listaPorDescri(@Param("descri") String descri, Pageable page);
	
}