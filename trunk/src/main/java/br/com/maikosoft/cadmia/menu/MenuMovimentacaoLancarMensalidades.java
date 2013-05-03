package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.view.JanelaLancaMensalidades;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuMovimentacaoLancarMensalidades extends MkMenu {

	public MenuMovimentacaoLancarMensalidades(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaLancaMensalidades janela = new JanelaLancaMensalidades();
				janela.showWindow("Lançar Mensalidades", true);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Lançar Mensalidades";
	}
}
