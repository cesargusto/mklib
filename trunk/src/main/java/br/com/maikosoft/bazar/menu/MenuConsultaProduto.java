package br.com.maikosoft.bazar.menu;

import br.com.maikosoft.bazar.view.JanelaProdutoConsulta;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuConsultaProduto extends MkMenu {

	public MenuConsultaProduto(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaProdutoConsulta janela = new JanelaProdutoConsulta();
				janela.showWindow("Consulta Produto", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Consultar Produto";
	}

}
