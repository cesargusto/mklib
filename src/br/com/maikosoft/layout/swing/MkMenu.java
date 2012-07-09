package br.com.maikosoft.layout.swing;

import br.com.maikosoft.core.MkRun;



public abstract class MkMenu {
	
	public abstract MkRun getAcao();

	public abstract String getTitulo();

	public abstract MkMenu getPai();
	
	public boolean isVisivel() {
		return true;
	}
	
	public boolean hasSeparador() {
		return false;
	}	
	
}
