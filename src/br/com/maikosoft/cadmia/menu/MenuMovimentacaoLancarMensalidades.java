package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.view.JanelaLancaMensalidades;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.layout.swing.MkMenu;

public class MenuMovimentacaoLancarMensalidades extends MkMenu {

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

	@Override
	public MkMenu getPai() {
		return EnumMenu.MOVIMENTACAO.getMenu();
	}

}
