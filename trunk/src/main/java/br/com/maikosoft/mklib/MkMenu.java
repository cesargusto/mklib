package br.com.maikosoft.mklib;

import br.com.maikosoft.core.MkRun;



public abstract class MkMenu {
	
	private MkMenu menuPai;
	private MkWindow janela;

	public MkMenu(MkMenu menuPai) {
		this.menuPai = menuPai;
	}
	
	public MkMenu(MkMenu menuPai , MkWindow janela) {
		this(menuPai);
		this.janela = janela;
	}
	
	public MkRun getAcao() {
		return new MkRun() {			
				@Override
				public void execute() {
					janela.showWindow(getTitulo(), false);
				}
			};
	}

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
	
	public static MkMenu createMenu(MkMenu menuPai, final String titulo, MkWindow janela) {
		return new MkMenu(menuPai, janela) {
			@Override
			public String getTitulo() {
				return titulo;
			}
		};
	}
	
}
