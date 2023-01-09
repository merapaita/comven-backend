package com.rosist.comven.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tipcom", uniqueConstraints = @UniqueConstraint(columnNames = {"tipcom" }))
public class Tipcom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTipcom;
	
	@Column(name = "tipcom", nullable = false, length = 2)
	private String tipcom;
	
	@Column(name = "descripcion", nullable = false, length = 200)
	private String descripcion;
	
	@Column(name = "ln_serie", nullable = true)
	private Integer lnSerie;
	
	@Column(name = "ln_numero", nullable = true)
	private Integer lnNumero;

	public Tipcom() {
	}

	public Tipcom(Integer idTipcom, String tipcom, String descripcion, Integer lnSerie, Integer lnNumero) {
		this.idTipcom = idTipcom;
		this.tipcom = tipcom;
		this.descripcion = descripcion;
		this.lnSerie = lnSerie;
		this.lnNumero = lnNumero;
	}

	public Integer getIdTipcom() {
		return idTipcom;
	}

	public void setIdTipcom(Integer idTipcom) {
		this.idTipcom = idTipcom;
	}

	public String getTipcom() {
		return tipcom;
	}

	public void setTipcom(String tipcom) {
		this.tipcom = tipcom;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getLnSerie() {
		return lnSerie;
	}

	public void setLnSerie(Integer lnSerie) {
		this.lnSerie = lnSerie;
	}

	public Integer getLnNumero() {
		return lnNumero;
	}

	public void setLnNumero(Integer lnNumero) {
		this.lnNumero = lnNumero;
	}

	@Override
	public String toString() {
		return "Tipcom [idTipcom=" + idTipcom + ", tipcom=" + tipcom + ", descripcion=" + descripcion + ", lnSerie="
				+ lnSerie + ", lnNumero=" + lnNumero + "]";
	}

}
