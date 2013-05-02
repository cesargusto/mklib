package br.com.maikosoft.cadmia;

import java.math.BigDecimal;
import java.util.Date;

import br.com.maikosoft.core.MkBean;

@SuppressWarnings("serial")
public class Financeiro extends MkBean {
	
	private Date dataCadastro;
	private Cliente cliente;
	private BigDecimal valor;
	private String observacao;
	private String referencia;
	private Date dataPagamento;
	private boolean pagar;
	
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente pessoa) {
		this.cliente = pessoa;
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
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public boolean getPagar() {
		return pagar;
	}
	public void setPagar(boolean pagar) {
		this.pagar = pagar;
	}

}
