package br.com.maikosoft.cadmia;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import br.com.maikosoft.Cliente;

@SuppressWarnings("serial")
public class ClienteCadMia extends Cliente {

	private String diaPagamento;
	private BigDecimal valorMensalidade;
	private String codigoBarra;
	
	private List<ClienteModalidade> listModalidade;
	
	
	public List<ClienteModalidade> getListModalidade() {
		if (listModalidade == null) {
			listModalidade = new LinkedList<ClienteModalidade>();
		}
		return listModalidade;
	}
	public void setListModalidade(List<ClienteModalidade> listModalidade) {
		this.listModalidade = listModalidade;
	}
	public String getDiaPagamento() {
		return diaPagamento;
	}
	public void setDiaPagamento(String diaPagamento) {
		this.diaPagamento = diaPagamento;
	}
	public BigDecimal getValorMensalidade() {
		return valorMensalidade;
	}
	public void setValorMensalidade(BigDecimal valorMensalidade) {
		this.valorMensalidade = valorMensalidade;
	}
	public String getCodigoBarra() {
		return codigoBarra;
	}
	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}
	
}
