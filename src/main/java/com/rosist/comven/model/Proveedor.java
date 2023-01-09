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
@Table(name = "proveedor", uniqueConstraints = @UniqueConstraint(columnNames = {"tipdoc", "docprv" }))
public class Proveedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProveedor;
	
	@Column(nullable = false, length = 1)
	private String tipdoc;
	
	@Column(nullable = false, length = 20)
	private String docprv;
	
	@Size(max=60, message="nombre no debe estar vacio")
	@Column(name = "nombre", nullable = false, length = 60)
	private String nombre;
	
	@Size(max=60, message="direccion no debe estar vacio")
	@Column(name = "direccion", nullable = true, length = 60)
	private String direccion;
	
	@Size(max=2, message="estado no debe estar vacio")
	@Column(name = "estado", nullable = false, length = 2)
	private String estado;

	public Integer getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getTipdoc() {
		return tipdoc;
	}

	public void setTipdoc(String tipdoc) {
		this.tipdoc = tipdoc;
	}

	public String getDocprv() {
		return docprv;
	}

	public void setDocprv(String docprv) {
		this.docprv = docprv;
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
		return "Proveedor [idProveedor=" + idProveedor + ", tipdoc=" + tipdoc + ", docprv=" + docprv + ", nombre="
				+ nombre + ", direccion=" + direccion + ", estado=" + estado + "]";
	}
	
}
