package br.com.maikosoft.alianca;

import java.math.BigDecimal;
import java.util.Date;

import br.com.maikosoft.Cliente;
import br.com.maikosoft.core.MkBean;

@SuppressWarnings("serial")
public class Duplicata extends MkBean {
	
	private Long numeroNota;
    private String ordem;
    private Date dataDulicata;
    private Date dataVencimento;
    private boolean pago;
    private Date dataPagamento;
    private String vendedor;
    private String observacao;
    private Cliente cliente;
    private BigDecimal valor;
    private BigDecimal valorTotal;
        
	public Long getNumeroNota() {
		return numeroNota;
	}
	public void setNumeroNota(Long numeroNota) {
		this.numeroNota = numeroNota;
	}
	public String getOrdem() {
		return ordem;
	}
	public void setOrdem(String ordem) {
		this.ordem = ordem;
	}
	public Date getDataDulicata() {
		return dataDulicata;
	}
	public void setDataDulicata(Date dataDulicata) {
		this.dataDulicata = dataDulicata;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public boolean isPago() {
		return pago;
	}
	public void setPago(boolean pago) {
		this.pago = pago;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
    
    

}
