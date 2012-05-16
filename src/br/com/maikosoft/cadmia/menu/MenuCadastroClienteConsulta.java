package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.view.JanelaClienteConsulta;
import br.com.maikosoft.layout.swing.MkMenu;
import br.com.maikosoft.layout.swing.MkRun;

public class MenuCadastroClienteConsulta extends MkMenu {

	@Override
	public MkRun getAcao() {
		return new MkRun() {			
			@Override
			public void execute() {
				new JanelaClienteConsulta().showView("Consultar Cliente", false);
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
