package br.com.maikosoft.cadmia;

import br.com.maikosoft.core.MkBean;

@SuppressWarnings("serial")
public class ClienteModalidade extends MkBean {
	
	private Cliente cliente;
	private Modalidade modalidade;
	private boolean isDelete;
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Modalidade getModalidade() {
		return modalidade;
	}
	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
