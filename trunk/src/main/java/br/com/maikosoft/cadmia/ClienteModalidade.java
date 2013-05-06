package br.com.maikosoft.cadmia;

import br.com.maikosoft.core.MkBean;

@SuppressWarnings("serial")
public class ClienteModalidade extends MkBean {
	
	private ClienteCadMia clienteCadMia;
	private Modalidade modalidade;
	private boolean isDelete;
	
	public ClienteCadMia getCliente() {
		return clienteCadMia;
	}
	public void setCliente(ClienteCadMia clienteCadMia) {
		this.clienteCadMia = clienteCadMia;
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
