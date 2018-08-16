package com.movileHack.movileHack.converters;

import java.util.Calendar;
import java.util.Optional;

import com.movileHack.movileHack.dtos.UsuarioDTO;
import com.movileHack.movileHack.entitys.Usuario;
import com.movileHack.movileHack.utils.PasswordUtils;

public class ConverterHelper {
	
	public static UsuarioDTO converterUsuarioEntityToDto(Usuario usuario) {
		UsuarioDTO userDTO = new UsuarioDTO();
		userDTO.setId(usuario.getId());
		userDTO.setNome(usuario.getNome());
		userDTO.setEmail(usuario.getEmail());
		userDTO.setCpf(usuario.getCpf());
		userDTO.setDataCriacao(usuario.getDataCriacao());
		userDTO.setDataEdicao(usuario.getDataEdicao());
		userDTO.setPermissao(usuario.getPermissao());
		return userDTO;
	}
	
	public static Usuario converterUsuarioDtoToEntity(UsuarioDTO usuarioDTO) {
		Usuario user = new Usuario();
		user.setId(usuarioDTO.getId());
		user.setNome(usuarioDTO.getNome());
		user.setEmail(usuarioDTO.getEmail());
		if (usuarioDTO.getSenha() != null) {
			user.setSenha(PasswordUtils.gerarBCrypt(usuarioDTO.getSenha()));
		}
		user.setCpf(usuarioDTO.getCpf());
		user.setDataCriacao(usuarioDTO.getDataCriacao());
		user.setDataEdicao(usuarioDTO.getDataEdicao());
		user.setPermissao(usuarioDTO.getPermissao());
		return user;
	}
	
	public static Usuario converterUsuarioDtoEditToEntity(Usuario usuario, Optional<Usuario> usuarioOp) {
		usuarioOp.get().setNome(usuario.getNome());
		usuarioOp.get().setCpf(usuario.getCpf());
		usuarioOp.get().setPermissao(usuario.getPermissao());
		usuarioOp.get().setDataEdicao(Calendar.getInstance());
		return usuarioOp.get();
	}
	
	
}
