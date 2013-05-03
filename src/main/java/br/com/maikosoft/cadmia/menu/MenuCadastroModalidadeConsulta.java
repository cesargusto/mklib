package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.view.JanelaModalidadeConsulta;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuCadastroModalidadeConsulta extends MkMenu {

	public MenuCadastroModalidadeConsulta(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {			
			@Override
			public void execute() {
				new JanelaModalidadeConsulta().showWindow("Consultar Modalidade", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Consultar Modalidade";
	}

}
