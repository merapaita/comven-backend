package com.rosist.comven.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cliente", uniqueConstraints = @UniqueConstraint(columnNames = {"tipdoc", "doccli" }))
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCliente;
	
	@Column(nullable = false, length = 1)
	private String tipdoc;
	
	@Column(nullable = false, length = 15)
	private String doccli;
	
	@Size(max=60, message="nombre no debe estar vacio")
	@Column(name = "nombre", nullable = false, length = 60)
	private String nombre;
	
	@Size(max=60, message="direccion no debe estar vacio")
	@Column(name = "direccion", nullable = false, length = 60)
	private String direccion;
	
	@Size(max=2, message="estado no debe estar vacio")
	@Column(name = "estado", nullable = false, length = 2)
	private String estado;

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getTipdoc() {
		return tipdoc;
	}

	public void setTipdoc(String tipdoc) {
		this.tipdoc = tipdoc;
	}

	public String getDoccli() {
		return doccli;
	}

	public void setDoccli(String doccli) {
		this.doccli = doccli;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", tipdoc=" + tipdoc + ", doccli=" + doccli + ", nombre=" + nombre
				+ ", direccion=" + direccion + ", estado=" + estado + "]";
	}

}
