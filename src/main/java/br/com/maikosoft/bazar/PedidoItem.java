package br.com.maikosoft.bazar;

import java.math.BigDecimal;

import br.com.maikosoft.core.MkBean;

@SuppressWarnings("serial")
public class PedidoItem extends MkBean {
	
	private Pedido pedido;
	private Produto produto;
    private Integer quantidade;
    private BigDecimal valor;
    
    private boolean delete;
    
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
    
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		PedidoItem other = (PedidoItem) obj;
//		if (getId() == null) {
//			if(other.getId() != null)
//				return false;
//			if (!getProduto().equals(other.getProduto()))
//				return false;
//		} else if (!getId().equals(other.getId()))
//			return false;
//		return true;
//	}

}
