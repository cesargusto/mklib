package br.com.maikosoft.alianca.menu;

import br.com.maikosoft.alianca.view.JanelaDuplicataGerar;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuGerarDuplicata extends MkMenu {

	public MenuGerarDuplicata(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaDuplicataGerar janela = new JanelaDuplicataGerar();
				janela.showWindow("Gerar Duplicata", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Gerar Duplicata";
	}

}
