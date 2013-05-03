package br.com.maikosoft.view.menu;

import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuSair extends MkMenu {
	
	public MenuSair(MkMenu menuPai) {
		super(menuPai);
	}

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

}
