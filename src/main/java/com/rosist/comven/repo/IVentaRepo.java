package com.rosist.comven.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rosist.comven.model.Venta;

public interface IVentaRepo extends IGenericRepo<Venta, Integer> {
	
	@Modifying
	@Query(value = "UPDATE Venta v SET "
			+ "v.id_empresa=:id_empresa ,"
			+ "v.anno=:anno ,"
			+ "v.mes=:mes ,"
			+ "v.correl=:correl ,"
			+ "v.periodo=:periodo ,"
			+ "v.asiento=:asiento ,"
			+ "v.fecha=:fecha ,"
			+ "v.fecpag=:fecpag ,"
			+ "v.tipcom=:tipcom ,"
			+ "v.serie=:serie ,"
			+ "v.codcom=:codcom ,"
			+ "v.codcom_final=:codcom_final ,"
			+ "v.id_cliente=:id_cliente ,"
			+ "v.desope=:desope ,"
			+ "v.tipope=:tipope ,"
			+ "v.basimp=:basimp ,"
			+ "v.igv=:igv ,"
			+ "v.isc=:isc ,"
			+ "v.icbp=:icbp ,"
			+ "v.otros=:otros ,"
			+ "v.importe_total=:importe_total ,"
			+ "v.moneda=:moneda ,"
			+ "v.tipo_cambio=:tipo_cambio ,"
			+ "v.feccom_modificado=:feccom_modificado ,"
			+ "v.tipom_modificado=:tipcom_modificado ,"
			+ "v.serie_com_modificado=:serie_com_modificado ,"
			+ "v.codigo_com_modificado=:codigo_com_modificado ,"
			+ "v.estado=:estado ,"
			+ "v.docum_referencia=:docum_referencia ,"
			+ "v.usercr=:usercr ,"
			+ "v.dusercr=:dusercr WHERE v.id_venta=:id_venta", nativeQuery = true)
	void modificaVenta(
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
			@Param("id_cliente") Integer id_cliente,
			@Param("desope") String desope,
			@Param("tipope") String tipope,
			@Param("basimp") Double basimp,
			@Param("igv") Double igv,
			@Param("isc") Double isc,
			@Param("icbp") Double icbp,
			@Param("otros") Double otros,
			@Param("importe_total") Double importe_total,
			@Param("moneda") String moneda,
			@Param("tipo_cambio") Double tipo_cambio,
			@Param("feccom_modificado") LocalDate feccom_modificado,
			@Param("tipcom_modificado") String tipcom_modificado,
			@Param("serie_com_modificado") String serie_com_modificado,
			@Param("codigo_com_modificado") String codigo_com_modificado,
			@Param("estado") String estado,
			@Param("docum_referencia") String docum_referencia,
			@Param("usercr") String usercr,
			@Param("dusercr") LocalDateTime dusercr,
			@Param("id_venta") Integer id_venta );
			
	@Query(value = "call pr_venta_count(:idEmpresa, :anno, :mes) ", nativeQuery = true)
	Integer listaVentasCount(@Param("idEmpresa") Integer idEmpresa, 
			@Param("anno") Integer anno, @Param("mes") Integer mes );
	
	@Query(value = "call pr_Venta(:idEmpresa, :anno, :mes, :page, :size)", nativeQuery = true)
	List<Object[]> listaVentas(@Param("idEmpresa") Integer idEmpresa, 
			@Param("anno") Integer anno, @Param("mes") Integer mes, 
			@Param("page") Integer page, @Param("size") Integer size);

	@Query(value = "select ifnull(max(correl),0)+1 from venta where id_empresa=:idEmpresa and anno=:anno and mes=:mes", nativeQuery = true)
	Integer getNewCorrel(@Param("idEmpresa") Integer idEmpresa, @Param("anno") Integer anno, @Param("mes") Integer mes);

}
