package br.com.maikosoft.bazar;

import java.math.BigDecimal;

import br.com.maikosoft.core.MkBean;

@SuppressWarnings("serial")
public class Produto extends MkBean {
	
	private String nome;
	private BigDecimal valor;
	private String codigoBarra;
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
	public String getCodigoBarra() {
		return codigoBarra;
	}
	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}
	
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@Override
	public String toString() {
		return nome + " #" + id;
	}
	
	

}
