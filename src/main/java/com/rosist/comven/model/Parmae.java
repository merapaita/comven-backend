package com.rosist.comven.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "parmae", uniqueConstraints=@UniqueConstraint(columnNames={"tipo","codigo"}))
public class Parmae {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idParmae;
	
	@Column(name = "tipo", length = 6, nullable = true)
	private String tipo;

	@Column(name = "codigo", length = 6, nullable = true)
	private String codigo;
	
	@Column(name = "descri", length = 100, nullable = true)
	private String descri;
	
	public Integer getIdParmae() {
		return idParmae;
	}

	public void setIdParmae(Integer idParmae) {
		this.idParmae = idParmae;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescri() {
		return descri;
	}

	public void setDescri(String descri) {
		this.descri = descri;
	}

	@Override
	public String toString() {
		return "Parmae [idParmae=" + idParmae + ", tipo=" + tipo + ", codigo=" + codigo + ", descri=" + descri + "]";
	}

}
