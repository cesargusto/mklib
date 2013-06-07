package br.com.maikosoft.alianca.menu;

import br.com.maikosoft.alianca.view.JanelaDuplicataConsulta;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuConsultaDuplicata extends MkMenu {

	public MenuConsultaDuplicata(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaDuplicataConsulta janela = new JanelaDuplicataConsulta();
				janela.showWindow("Consulta Duplicata", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Consultar Duplicata";
	}

}
