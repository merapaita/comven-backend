package com.rosist.comven.repo;

import org.springframework.data.jpa.repository.Query;

//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import com.rosist.comven.model.Usuario;

public interface IUsuarioRepo extends IGenericRepo<Usuario, Integer> {

	@Query(value = "select coalesce(max(id_usuario),0)+1 from usuario", nativeQuery = true)
	Integer getNewId();

	//from usuario where username = ?
	Usuario findOneByUsername(String username);

	//@Transactional
//	@Modifying
//	@Query(value = "update usuario set username= :username where id_usuario= :idusuario", nativeQuery = true)
//	Integer registrar(@Param("username") String username, @Param("idusuario") Integer idusuario);
	
}
