package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.view.JanelaClienteConsulta;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuCadastroClienteConsulta extends MkMenu {

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

	@Override
	public MkMenu getPai() {
		return EnumMenu.CADASTRO_CLIENTE.getMenu();
	}

}
