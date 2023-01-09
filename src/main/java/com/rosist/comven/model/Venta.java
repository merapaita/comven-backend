package com.rosist.comven.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "venta", uniqueConstraints = @UniqueConstraint(columnNames = { "id_empresa", "anno", "mes", "correl" }))
public class Venta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idVenta;

	@ManyToOne
	@JoinColumn(name = "id_empresa", nullable = false, foreignKey = @ForeignKey(name = "FK_empresa1"))
	private Empresa empresa;

	@Column(name = "anno", nullable = false, length = 4)
	private Integer anno;

	@Column(name = "mes", nullable = false, length = 2)
	private Integer mes;

	@Column(name = "correl", nullable = false, length = 2)
	private Integer correl;

	@Column(name = "periodo", nullable = false, length = 8)
	private String periodo;

	@Column(name = "asiento", nullable = false, length = 4)
	private String asiento;

	@Column(name = "fecha", nullable = false)
	private LocalDate fecha;

	@Column(name = "fecpag", nullable = false)
	private LocalDate fecpag;

	@Column(name = "tipcom", nullable = false, length = 2)
	private String tipcom;

	@Column(name = "serie", nullable = false, length = 20)
	private String serie;

	@Column(name = "codcom", nullable = false, length = 20)
	private String codcom;

	@Column(nullable = false, length = 20)
	private String codcomFinal;

	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false, foreignKey = @ForeignKey(name = "FK_cliente"))
	private Cliente cliente;

	// destino de operacion
	@Column(name = "desope", nullable = false, length = 1)
	private String desope;

	// tipo de operacion
	@Column(name = "tipope", nullable = false, length = 1)
	private String tipope;

	@Column(nullable = false)
	private Double basimp;

	@Column(name = "igv", nullable = false)
	private Double igv;

	@Column(nullable = false)
	private Double isc;

	@Column(nullable = false)
	private Double icbp;

	@Column(nullable = false)
	private Double otros;

	@Column(nullable = false)
	private Double importeTotal;

	@Column(nullable = false, length = 3)
	private String moneda;

	@Column(nullable = false)
	private Double tipoCambio;

	// fecha comprobante modificado
	@Column(nullable = true)
	private LocalDate feccomModificado;

	// tipo comprobante modificado
	@Column(nullable = true, length = 2)
	private String tipcomModificado;

	// serie comprobante modificado
	@Column(nullable = true, length = 20)
	private String serieComModificado;

	// codigo comprobante modificado
	@Column(nullable = true, length = 20)
	private String codigoComModificado;

	@Column(nullable = true, length = 2)
	private String estado;

	// documento de referencia - para control beneficencia
	@Column(nullable = true, length = 15)
	private String documReferencia;

	@Column(name = "userup", length = 15, nullable = true)
	private String userup;

	@Column(name = "usercr", length = 15, nullable = true)
	private String usercr;

	@Column(name = "duserup", nullable = true)
	private LocalDateTime duserup;

	@Column(name = "dusercr", nullable = true)
	private LocalDateTime dusercr;

	public Integer getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getCorrel() {
		return correl;
	}

	public void setCorrel(Integer correl) {
		this.correl = correl;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getAsiento() {
		return asiento;
	}

	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalDate getFecpag() {
		return fecpag;
	}

	public void setFecpag(LocalDate fecpag) {
		this.fecpag = fecpag;
	}

	public String getTipcom() {
		return tipcom;
	}

	public void setTipcom(String tipcom) {
		this.tipcom = tipcom;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getCodcom() {
		return codcom;
	}

	public void setCodcom(String codcom) {
		this.codcom = codcom;
	}

	public String getCodcomFinal() {
		return codcomFinal;
	}

	public void setCodcomFinal(String codcomFinal) {
		this.codcomFinal = codcomFinal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDesope() {
		return desope;
	}

	public void setDesope(String desope) {
		this.desope = desope;
	}

	public String getTipope() {
		return tipope;
	}

	public void setTipope(String tipope) {
		this.tipope = tipope;
	}

	public Double getBasimp() {
		return basimp;
	}

	public void setBasimp(Double basimp) {
		this.basimp = basimp;
	}

	public Double getIgv() {
		return igv;
	}

	public void setIgv(Double igv) {
		this.igv = igv;
	}

	public Double getIsc() {
		return isc;
	}

	public void setIsc(Double isc) {
		this.isc = isc;
	}

	public Double getIcbp() {
		return icbp;
	}

	public void setIcbp(Double icbp) {
		this.icbp = icbp;
	}

	public Double getOtros() {
		return otros;
	}

	public void setOtros(Double otros) {
		this.otros = otros;
	}

	public Double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(Double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Double getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(Double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public LocalDate getFeccomModificado() {
		return feccomModificado;
	}

	public void setFeccomModificado(LocalDate feccomModificado) {
		this.feccomModificado = feccomModificado;
	}

	public String getTipcomModificado() {
		return tipcomModificado;
	}

	public void setTipcomModificado(String tipcomModificado) {
		this.tipcomModificado = tipcomModificado;
	}

	public String getSerieComModificado() {
		return serieComModificado;
	}

	public void setSerieComModificado(String serieComModificado) {
		this.serieComModificado = serieComModificado;
	}

	public String getCodigoComModificado() {
		return codigoComModificado;
	}

	public void setCodigoComModificado(String codigoComModificado) {
		this.codigoComModificado = codigoComModificado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDocumReferencia() {
		return documReferencia;
	}

	public void setDocumReferencia(String documReferencia) {
		this.documReferencia = documReferencia;
	}

	public String getUserup() {
		return userup;
	}

	public void setUserup(String userup) {
		this.userup = userup;
	}

	public String getUsercr() {
		return usercr;
	}

	public void setUsercr(String usercr) {
		this.usercr = usercr;
	}

	public LocalDateTime getDuserup() {
		return duserup;
	}

	public void setDuserup(LocalDateTime duserup) {
		this.duserup = duserup;
	}

	public LocalDateTime getDusercr() {
		return dusercr;
	}

	public void setDusercr(LocalDateTime dusercr) {
		this.dusercr = dusercr;
	}

	@Override
	public String toString() {
		return "Venta [idVenta=" + idVenta + ", empresa=" + empresa + ", anno=" + anno + ", mes=" + mes + ", correl="
				+ correl + ", periodo=" + periodo + ", asiento=" + asiento + ", fecha=" + fecha + ", fecpag=" + fecpag
				+ ", tipcom=" + tipcom + ", serie=" + serie + ", codcom=" + codcom + ", codcomFinal=" + codcomFinal
				+ ", cliente=" + cliente + ", desope=" + desope + ", tipope=" + tipope + ", basimp=" + basimp + ", igv="
				+ igv + ", isc=" + isc + ", icbp=" + icbp + ", otros=" + otros + ", importeTotal=" + importeTotal
				+ ", moneda=" + moneda + ", tipoCambio=" + tipoCambio + ", feccomModificado=" + feccomModificado
				+ ", tipcomModificado=" + tipcomModificado + ", serieComModificado=" + serieComModificado
				+ ", codigoComModificado=" + codigoComModificado + ", estado=" + estado + ", documReferencia="
				+ documReferencia + ", userup=" + userup + ", usercr=" + usercr + ", duserup=" + duserup + ", dusercr="
				+ dusercr + "]";
	}

}