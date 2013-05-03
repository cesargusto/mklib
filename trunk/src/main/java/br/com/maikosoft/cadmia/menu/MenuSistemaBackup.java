package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;
import br.com.maikosoft.view.JanelaBackup;

public class MenuSistemaBackup extends MkMenu {

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			@Override
			public void execute() {
				new JanelaBackup().showWindow("Backup Sistema", true);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Backup";
	}

	@Override
	public MkMenu getPai() {
		return EnumMenu.SISTEMA.getMenu();
	}

	@Override
	public boolean hasSeparador() {
		return true;
	}	

}
