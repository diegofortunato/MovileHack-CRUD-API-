package com.movileHack.movileHack.controllers;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movileHack.movileHack.converters.ConverterHelper;
import com.movileHack.movileHack.dtos.UsuarioDTO;
import com.movileHack.movileHack.entitys.Usuario;
import com.movileHack.movileHack.exceptions.ApplicationException;
import com.movileHack.movileHack.response.Response;
import com.movileHack.movileHack.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * Retorna um usuario.
	 * 
	 * @param usuarioId
	 * @return ResponseEntity<Response<UsuarioDTO>>
	 */
	@GetMapping(value = "/{usuarioId}")
	public ResponseEntity<Response<UsuarioDTO>> listarPorUsuarioId(@PathVariable("usuarioId") Long usuarioId) throws ApplicationException {
		log.info("Buscando usuário: ", usuarioId);
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();
		try {
			UsuarioDTO usuario = this.usuarioService.buscarPorId(usuarioId);
			if (usuario != null) {
				response.setData(usuario);
				return ResponseEntity.ok(response);
			}else {
				log.info("Usuário não encontrado para o ID: ", usuarioId);
				response.getErrors().add("Usuário não encontrado para o ID: " + usuarioId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (ApplicationException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	/**
	 * Retorna todos os usuario.
	 * 
	 * @return ResponseEntity<Response<List<UsuarioDTO>>>
	 */
	@GetMapping()
	public ResponseEntity<Response<List<UsuarioDTO>>> listarTodosUsuarios() throws ApplicationException{
		log.info("Buscando todos usuarios");
		Response<List<UsuarioDTO>> response = new Response<List<UsuarioDTO>>();
		try {
			List<UsuarioDTO> usuarios = this.usuarioService.buscarTodosUsuarios();
			if (usuarios != null) {
				response.setData(usuarios);
				return ResponseEntity.ok(response);
			}else {
				log.info("Usuários não encontrados");
				response.getErrors().add("Usuários não encontrados");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (ApplicationException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	/**
	 * Adiciona um novo usuario.
	 * 
	 * @param usuario
	 * @param result
	 * @return ResponseEntity<Response<UsuarioDto>>
	 * @throws ParseException 
	 */
	@PostMapping
	public ResponseEntity<Response<UsuarioDTO>> adicionarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult result) throws ParseException {
		log.info("Adicionando usuário: ", usuarioDTO.getNome());
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();
		try {
			validarUsuarioEmail(usuarioDTO, result);
			Usuario usuario = ConverterHelper.converterUsuarioDtoToEntity(usuarioDTO);
			if (!result.hasErrors()) {
				usuarioDTO = this.usuarioService.persistirUsuario(usuario);
				response.setData(usuarioDTO);
				return ResponseEntity.ok(response);
			}else {
				log.error("Erro validando usuário: {}", result.getAllErrors());
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		}catch (ApplicationException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	
	/**
	 * Atualiza os dados de um usuário.
	 * 
	 * @param id
	 * @param usuarioDto
	 * @return ResponseEntity<Response<Usuario>>
	 * @throws ParseException 
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<UsuarioDTO>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult result) throws ParseException {
		log.info("Atualizando usuário: {}", usuarioDTO.getNome());
		Response<UsuarioDTO> response = new Response<UsuarioDTO>();
		try {
			validarUsuarioId(id, result);
			Usuario usuario = ConverterHelper.converterUsuarioDtoToEntity(usuarioDTO);
			if (!result.hasErrors()) {
				usuarioDTO = this.usuarioService.atualizarUsuario(usuario, id);
				response.setData(usuarioDTO);
				return ResponseEntity.ok(response);
			}else {
				log.error("Erro validando usuário: {}", result.getAllErrors());
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		}catch (ApplicationException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * Remove um usuário por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Usuario>>
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) throws ApplicationException{
		log.info("Removendo usuário: {}", id);
		Response<String> response = new Response<String>();
		try {
			UsuarioDTO usuario = this.usuarioService.buscarPorId(id);
			if (usuario != null) {
				this.usuarioService.removerUsuario(id);
				return ResponseEntity.ok(new Response<String>());
			}else {
				log.info("Erro ao remover usuário ID: ", id);
				response.getErrors().add("Erro ao remover usuário. Registro não encontrado para o id " + id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		}catch (ApplicationException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	/**
	 * Valida um usuário, verificando se ele é existente e válido no sistema por email.
	 * 
	 * @param usuarioDto
	 * @param result
	 */
	private void validarUsuarioEmail(UsuarioDTO usuarioDTO, BindingResult result) throws ApplicationException{
		if (usuarioDTO.getEmail() == null) {
			result.addError(new ObjectError("usuario", "Usuário não informado."));
			return;
		}
		log.info("Validando usuário email: ", usuarioDTO.getEmail());
		UsuarioDTO usuario = this.usuarioService.buscarPorEmail(usuarioDTO.getEmail());
		if (usuario != null) {
			result.addError(new ObjectError("usuario", "Usuário já se encontra cadastrado no sistema."));
		}
	}
	
	/**
	 * Valida um usuário, verificando se ele é existente e válido no sistema ID.
	 * 
	 * @param usuarioDto
	 * @param result
	 */
	private void validarUsuarioId(Long id, BindingResult result) throws ApplicationException{
		if (id == null) {
			result.addError(new ObjectError("usuario", "Usuário não informado."));
			return;
		}
		log.info("Validando usuário id {}: ", id);
		UsuarioDTO usuario = this.usuarioService.buscarPorId(id);
		if (usuario == null) {
			result.addError(new ObjectError("usuario", "Usuário não encontrado. ID inexistente."));
		}
	}
}