package com.movileHack.movileHack.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.movileHack.movileHack.converters.ConverterHelper;
import com.movileHack.movileHack.dtos.UsuarioDTO;
import com.movileHack.movileHack.entitys.Usuario;
import com.movileHack.movileHack.exceptions.ApplicationException;
import com.movileHack.movileHack.repositories.UsuarioRepository;
import com.movileHack.movileHack.security.dto.JwtAuthenticationDto;
import com.movileHack.movileHack.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UsuarioDTO buscarPorId(Long usuarioId) throws ApplicationException{
		Optional<Usuario> usuarioOp = usuarioRepository.findById(usuarioId);
		if (usuarioOp.isPresent()) {
			return ConverterHelper.converterUsuarioEntityToDto(usuarioOp.get());
		}else {
			return null;
		}
	}

	@Override
	public UsuarioDTO buscarPorEmail(String email) throws ApplicationException {
		Optional<Usuario> usuarioOp = usuarioRepository.findByEmail(email);
		if (usuarioOp.isPresent()) {
			return ConverterHelper.converterUsuarioEntityToDto(usuarioOp.get());
		}else {
			return null;
		}
	}

	@Override
	public List<UsuarioDTO> buscarTodosUsuarios() throws ApplicationException{
		List<UsuarioDTO> usuarios = usuarioRepository.findAll().stream().map(ConverterHelper::converterUsuarioEntityToDto).collect(Collectors.toList());
		if (usuarios != null) {
			return usuarios;
		}else {
			return null;
		}
	}

	@Override
	public UsuarioDTO persistirUsuario(Usuario usuario) throws ApplicationException{
		usuario = this.usuarioRepository.save(usuario);
		return ConverterHelper.converterUsuarioEntityToDto(usuario);
	}

	@Override
	public UsuarioDTO atualizarUsuario(Usuario usuario, Long usuarioId) throws ApplicationException {
		Optional<Usuario> usuarioOp = usuarioRepository.findById(usuarioId);
		if (usuarioOp.isPresent()) {
			usuario = ConverterHelper.converterUsuarioDtoEditToEntity(usuario, usuarioOp);
			usuario = this.usuarioRepository.save(usuario);
			return ConverterHelper.converterUsuarioEntityToDto(usuario);
		}else {
			return null;
		}
	}

	@Override
	public void removerUsuario(Long id) throws ApplicationException{
		this.usuarioRepository.deleteById(id);
	}

	@Override
	public boolean login(@Valid JwtAuthenticationDto authenticationDto) throws ApplicationException {
		Optional<Usuario> usuarioOp = usuarioRepository.findByEmail(authenticationDto.getEmail());
		if (usuarioOp.isPresent()) {
			if (BCrypt.checkpw(authenticationDto.getSenha(), usuarioOp.get().getSenha())) {
				return true;
			} else {
				return false;
			}
		}else {
			return false;
		}
	}
}
