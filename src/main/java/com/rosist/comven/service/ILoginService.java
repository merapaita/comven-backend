package com.rosist.comven.service;

import com.rosist.comven.model.Usuario;

public interface ILoginService {

	Usuario verificarNombreUsuario(String usuario);
	Usuario verificarCorreoUsuario(String correo);
	void cambiarClave(String clave, String nombre);
}
