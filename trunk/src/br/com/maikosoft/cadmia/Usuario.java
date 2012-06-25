package br.com.maikosoft.cadmia;

import br.com.maikosoft.core.MkBean;

@SuppressWarnings("serial")
public class Usuario extends MkBean {

	private String nome;
	private String senha;
	private boolean ativo;

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

	@Override
	public String toString() {
		return this.nome + " #" + this.id;
	}

}
