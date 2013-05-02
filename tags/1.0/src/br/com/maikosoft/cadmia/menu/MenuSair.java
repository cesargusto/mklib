package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.layout.swing.MkMenu;

public class MenuSair extends MkMenu {

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			@Override
			public void execute() {
				System.exit(0);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Sair";
	}

	@Override
	public MkMenu getPai() {
		return EnumMenu.SISTEMA.getMenu();
	}
	
	

	

}
