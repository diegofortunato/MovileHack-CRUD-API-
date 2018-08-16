package com.movileHack.movileHack.dtos;

import java.util.Calendar;

import com.movileHack.movileHack.enums.PermissaoEnum;

public class UsuarioDTO {
	
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private Calendar dataCriacao;
	private Calendar dataEdicao;
	private PermissaoEnum permissao;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Calendar getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Calendar getDataEdicao() {
		return dataEdicao;
	}
	public void setDataEdicao(Calendar dataEdicao) {
		this.dataEdicao = dataEdicao;
	}
	public PermissaoEnum getPermissao() {
		return permissao;
	}
	public void setPermissao(PermissaoEnum permissao) {
		this.permissao = permissao;
	}
}
