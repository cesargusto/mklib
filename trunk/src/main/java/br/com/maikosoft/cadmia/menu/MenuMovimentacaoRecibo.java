package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.view.JanelaRecibo;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuMovimentacaoRecibo extends MkMenu {

	public MenuMovimentacaoRecibo(MkMenu menuPai) {
		super(menuPai);
	}

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
}
