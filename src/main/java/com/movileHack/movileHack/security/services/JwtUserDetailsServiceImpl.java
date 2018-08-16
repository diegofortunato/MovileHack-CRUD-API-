package com.movileHack.movileHack.security.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.movileHack.movileHack.dtos.UsuarioDTO;
import com.movileHack.movileHack.exceptions.ApplicationException;
import com.movileHack.movileHack.security.JwtUserFactory;
import com.movileHack.movileHack.services.UsuarioService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioDTO usuario;
		try {
			usuario = usuarioService.buscarPorEmail(username);
			if (usuario != null) {
				return JwtUserFactory.create(usuario);
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		throw new UsernameNotFoundException("Email não encontrado.");
	}
}
