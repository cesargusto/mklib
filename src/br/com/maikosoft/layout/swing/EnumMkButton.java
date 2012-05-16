package br.com.maikosoft.layout.swing;

import java.lang.reflect.Method;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.springframework.util.ReflectionUtils;

public enum EnumMkButton {
	ABRIR("A&brir"),
	ADICIONAR("A&dicionar"),
	ARQUIVO("&Arquivo"),
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
		return new ImageIcon(getClass().getClassLoader().getResource("resource/icon/"+this.toString()+".png"));
	}

	@Override
	public String toString() {
		return name().toLowerCase();
	}

	public MkButton getButton(final MkWindow janela) {
		MkButton button = new MkButton();
		button.setText(this.title);
		button.setIcon(getIcon());
		final String methodName = this.toString();
		Method method = ReflectionUtils.findMethod(janela.getClass(), methodName);
		if (method == null) {
			throw new RuntimeException("Metodo nao implementado para onClick:"+methodName);
		}
		ReflectionUtils.makeAccessible(method);
		button.onClick((MkRun) ReflectionUtils.invokeMethod(method, janela));
		return button;
	}
	
}
