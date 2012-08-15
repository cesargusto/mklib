package br.com.maikosoft.cadmia;

import br.com.maikosoft.core.MkBean;

@SuppressWarnings("serial")
public class Usuario extends MkBean {

	private String nome;
	private String senha;
	private boolean ativo;
	private boolean administrador;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	@Override
	public String toString() {
		return this.nome + " #" + this.id;
	}

}
