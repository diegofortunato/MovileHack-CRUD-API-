package com.movileHack.movileHack.services;

import java.util.List;

import javax.validation.Valid;

import com.movileHack.movileHack.dtos.UsuarioDTO;
import com.movileHack.movileHack.entitys.Usuario;
import com.movileHack.movileHack.exceptions.ApplicationException;
import com.movileHack.movileHack.security.dto.JwtAuthenticationDto;

public interface UsuarioService {

	boolean login(@Valid JwtAuthenticationDto authenticationDto) throws ApplicationException;;
	UsuarioDTO buscarPorId(Long usuarioId) throws ApplicationException;
	UsuarioDTO buscarPorEmail(String email) throws ApplicationException;
	List<UsuarioDTO> buscarTodosUsuarios() throws ApplicationException;
	UsuarioDTO persistirUsuario(Usuario usuario) throws ApplicationException;
	UsuarioDTO atualizarUsuario(Usuario usuario, Long usuarioId) throws ApplicationException;;
	void removerUsuario(Long id) throws ApplicationException;
}
