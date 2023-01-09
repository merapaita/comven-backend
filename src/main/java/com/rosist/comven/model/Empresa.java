package com.rosist.comven.model;

import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "empresa", uniqueConstraints = @UniqueConstraint(columnNames = {"id_usuario", "ruc" }))
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idEmpresa;
	
	@Column(name = "ruc", nullable = false, length = 11)
	private String ruc;
	
	@Column(name = "razsoc", nullable = false, length = 60)
	private String razsoc;
	
	@Column(name = "direccion", nullable = false, length = 60)
	private String direccion; 
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "FK_usuario"))
	private Usuario usuario;

	public Integer getIdEmpresa() {
		return idEmpresa;
	}


	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}


	public String getRuc() {
		return ruc;
	}


	public void setRuc(String ruc) {
		this.ruc = ruc;
	}


	public String getRazsoc() {
		return razsoc;
	}


	public void setRazsoc(String razsoc) {
		this.razsoc = razsoc;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	@Override
	public String toString() {
		return "Empresa [idEmpresa=" + idEmpresa + ", ruc=" + ruc + ", razsoc=" + razsoc + ", direccion=" + direccion
				+ ", usuario=" + usuario + "]";
	}
	
}