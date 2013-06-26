package br.com.maikosoft.bazar;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import br.com.maikosoft.core.MkBean;

@SuppressWarnings("serial")
public class Pedido extends MkBean {
	
	private Date dataPedido;
    private String observacao;
    private ClienteBazar cliente;
    private BigDecimal desconto;
    private BigDecimal total;    
    
    private List<PedidoItem> listPedidoItem;

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public ClienteBazar getCliente() {
		return cliente;
	}

	public void setCliente(ClienteBazar cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public BigDecimal getSaldo() {
		if (this.total == null || this.desconto ==null) {
			return null;
		}
		return this.total.subtract(this.desconto);
	}
        
	public List<PedidoItem> getListPedidoItem() {
		if (listPedidoItem == null) {
			listPedidoItem = new LinkedList<PedidoItem>();
		}
		return listPedidoItem;
	}
	public void setListPedidoItem(List<PedidoItem> listPedidoItem) {
		this.listPedidoItem = listPedidoItem;
	}
    

}
