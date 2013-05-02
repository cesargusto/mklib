package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.view.JanelaRecibo;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.layout.swing.MkMenu;

public class MenuMovimentacaoRecibo extends MkMenu {

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaRecibo janela = new JanelaRecibo();
				janela.showWindow("Recibo", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Recibo";
	}

	@Override
	public MkMenu getPai() {
		return EnumMenu.MOVIMENTACAO.getMenu();
	}

}
