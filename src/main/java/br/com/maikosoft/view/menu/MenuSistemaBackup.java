package br.com.maikosoft.view.menu;

import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;
import br.com.maikosoft.view.JanelaBackup;

public class MenuSistemaBackup extends MkMenu {

	public MenuSistemaBackup(MkMenu menuPai) {
		super(menuPai);
	}

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
	public boolean hasSeparador() {
		return true;
	}	

}
