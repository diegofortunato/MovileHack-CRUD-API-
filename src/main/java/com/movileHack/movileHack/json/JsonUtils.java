package com.movileHack.movileHack.json;

import java.util.Calendar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movileHack.movileHack.dtos.UsuarioDTO;
import com.movileHack.movileHack.enums.PermissaoEnum;

public class JsonUtils {
	
	public static void main(String[] args) throws JsonProcessingException {
		System.out.println(obterJsonUsuario());
	}
	
	public static String obterJsonUsuario() throws JsonProcessingException {
		UsuarioDTO user = new UsuarioDTO();
		user.setNome("Diego");
		user.setEmail("diego@gmail.com");
		user.setCpf("12345678911");
		user.setDataCriacao(Calendar.getInstance());
		user.setPermissao(PermissaoEnum.ADMIN);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(user);
	}
}
