package com.rosist.comven.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rosist.comven.model.Compra;

public interface ICompraRepo extends IGenericRepo<Compra, Integer> {
	
	@Modifying
	@Query(value = "UPDATE Compras c SET "
			+ "c.id_empresa=:id_empresa ,"
			+ "c.anno=:anno ,"
			+ "c.mes=:mes ,"
			+ "c.correl=:correl ,"
			+ "c.periodo=:periodo ,"
			+ "c.asiento=:asiento ,"
			+ "c.fecha=:fecha ,"
			+ "c.fecpag=:fecpag ,"
			+ "c.tipcom=:tipcom ,"
			+ "c.serie=:serie ,"
			+ "c.codcom=:codcom ,"
			+ "c.codcom_final=:codcom_final ,"
			+ "c.id_proveedor=:id_proveedor ,"
			+ "c.desope=:desope ,"
			+ "c.tipope=:tipope ,"
			+ "c.basimp=:basimp ,"
			+ "c.igv=:igv ,"
			+ "c.valor_nogravado=:valor_nogravado ,"
			+ "c.isc=:isc ,"
			+ "c.icbp=:icbp ,"
			+ "c.otros=:otros ,"
			+ "c.importe_total=:importe_total ,"
			+ "c.moneda=:moneda ,"
			+ "c.tipo_cambio=:tipo_cambio ,"
			+ "c.fecha_com_modificado=:fecha_com_modificado ,"
			+ "c.tipo_com_modificado=:tipo_com_modificado ,"
			+ "c.serie_com_modificado=:serie_com_modificado ,"
			+ "c.codigo_com_modificado=:codigo_com_modificado ,"
			+ "c.estado=:estado ,"
			+ "c.docum_referencia=:docum_referencia ,"
			+ "c.usercr=:usercr ,"
			+ "c.dusercr=:dusercr "
			+ "WHERE c.id_compras=:id_compras", nativeQuery = true)
	void modificaCompra(
			@Param("id_empresa") Integer id_empresa, 
			@Param("anno") Integer anno,
			@Param("mes") Integer mes,
			@Param("correl") Integer correl,
			@Param("periodo") String periodo,
			@Param("asiento") String asiento,
			@Param("fecha") LocalDate fecha,
			@Param("fecpag") LocalDate fecpag,
			@Param("tipcom") String tipcom,
			@Param("serie") String serie,
			@Param("codcom") String codcom,
			@Param("codcom_final") String codcom_final,
			@Param("id_proveedor") Integer id_proveedor,
			@Param("desope") String desope,
			@Param("tipope") String tipope,
			@Param("basimp") Double basimp,
			@Param("igv") Double igv,
			@Param("valor_nogravado") Double valor_nogravado,
			@Param("isc") Double isc,
			@Param("icbp") Double icbp,
			@Param("otros") Double otros,
			@Param("importe_total") Double importe_total,
			@Param("moneda") String moneda,
			@Param("tipo_cambio") Double tipo_cambio,
			@Param("fecha_com_modificado") LocalDate fecha_com_modificado,
			@Param("tipo_com_modificado") String tipo_com_modificado,
			@Param("serie_com_modificado") String serie_com_modificado,
			@Param("codigo_com_modificado") String codigo_com_modificado,
			@Param("estado") String estado,
			@Param("docum_referencia") String docum_referencia,
			@Param("usercr") String usercr,
			@Param("dusercr") LocalDateTime dusercr,
			@Param("id_compras") Integer id_compras );
	
	@Query(value = "call pr_compra_count(:idEmpresa, :anno, :mes) ", nativeQuery = true)
	Integer listaComprasCount(@Param("idEmpresa") Integer idEmpresa, 
			@Param("anno") Integer anno, @Param("mes") Integer mes );
	
	@Query(value = "call pr_compra(:idEmpresa, :anno, :mes, :page, :size)", nativeQuery = true)
	List<Object[]> listaCompras(@Param("idEmpresa") Integer idEmpresa, 
			@Param("anno") Integer anno, @Param("mes") Integer mes, 
			@Param("page") Integer page, @Param("size") Integer size);
	
	@Query(value = "select ifnull(max(correl),0)+1 from compra where id_empresa=:idEmpresa and anno=:anno and mes=:mes", nativeQuery = true)
	Integer getNewCorrel(@Param("idEmpresa") Integer idEmpresa, @Param("anno") Integer anno, @Param("mes") Integer mes);

}
