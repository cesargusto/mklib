package br.com.maikosoft.mklib;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum EnumMkButton {
	ABRIR("A&brir"),
	ADICIONAR("A&dicionar"),
	ARQUIVO("&Arquivo"),
	ATUALIZAR("&Atualizar"),
	CANCELAR("&Cancelar"),
	CONFIRMAR("C&onfirmar"),    	
	EDITAR("&Editar"),
	FECHAR("&Fechar"),
	IMPRIMIR("&Imprimir"),
	LIMPAR("&Limpar"),
	NOVO("&Novo"),
	PESQUISAR("&Pesquisar"),
	REMOVER("&Remover"),
	SALVAR("&Salvar"),
	TRANSFERIR("&Transferir"),
	EXCLUIR("E&xcluir");
	
	public final String title;		
	
	private EnumMkButton(String title) {
		this.title = title;
	}
	
	public Icon getIcon() {
		return EnumMkButton.getIcon(this.toString());
	}
	
	public static Icon getIcon(String nome) {
		return new ImageIcon(EnumMkButton.class.getClassLoader().getResource("icon/"+nome+".png"));
	}
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
	
}
