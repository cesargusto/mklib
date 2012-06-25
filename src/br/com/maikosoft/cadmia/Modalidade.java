package br.com.maikosoft.cadmia;

import java.math.BigDecimal;

import br.com.maikosoft.core.MkBean;
import br.com.maikosoft.core.MkUtil;

@SuppressWarnings("serial")
public class Modalidade extends MkBean {

	private String nome;
	private BigDecimal valor;
	private String observacao;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	@Override
	public String toString() {
		return this.nome + " - Valor: " + MkUtil.toString(this.valor)+ " #" + this.id;
	}
	
}
