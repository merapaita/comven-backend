package com.rosist.comven.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "periodo", uniqueConstraints = @UniqueConstraint(columnNames = {"periodo"}))
public class Periodo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPeriodo;
	
	@Column(nullable = false, length = 4)
	private Integer anno;
	
	@Column(nullable = false, length = 2)
	private Integer mes;
	
	@Column(nullable = false, length = 8)
	private String periodo;
	
	@Column(nullable = false, length = 8)
	private boolean estdo;

	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
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

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public boolean isEstdo() {
		return estdo;
	}

	public void setEstdo(boolean estdo) {
		this.estdo = estdo;
	}

	@Override
	public String toString() {
		return "Periodo [idPeriodo=" + idPeriodo + ", anno=" + anno + ", mes=" + mes + ", periodo=" + periodo
				+ ", estdo=" + estdo + "]";
	}

}