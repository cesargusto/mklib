package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.view.JanelaClienteConsulta;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuCadastroClienteConsulta extends MkMenu {

	public MenuCadastroClienteConsulta(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {			
			@Override
			public void execute() {
				new JanelaClienteConsulta().showWindow("Consultar Cliente", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Consultar Cliente";
	}

}
