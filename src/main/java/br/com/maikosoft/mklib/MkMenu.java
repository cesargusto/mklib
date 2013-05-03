package br.com.maikosoft.mklib;

import br.com.maikosoft.core.MkRun;



public abstract class MkMenu {
	
	private MkMenu menuPai;

	public MkMenu(MkMenu menuPai) {
		this.menuPai = menuPai;
	}
	
	public abstract MkRun getAcao();

	public abstract String getTitulo();

	public MkMenu getPai() {
		return menuPai;
	}
	
	public boolean isVisivel() {
		return true;
	}
	
	public boolean hasSeparador() {
		return false;
	}	
	
}
