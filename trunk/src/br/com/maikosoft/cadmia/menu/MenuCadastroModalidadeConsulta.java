package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.view.JanelaModalidadeConsulta;
import br.com.maikosoft.layout.swing.MkMenu;
import br.com.maikosoft.layout.swing.MkRun;

public class MenuCadastroModalidadeConsulta extends MkMenu {

	@Override
	public MkRun getAcao() {
		return new MkRun() {			
			@Override
			public void execute() {
				new JanelaModalidadeConsulta().showView("Consultar Modalidade", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Consultar Modalidade";
	}

	@Override
	public MkMenu getPai() {
		return EnumMenu.CADASTRO_MODALIDADE.getMenu();
	}

}
