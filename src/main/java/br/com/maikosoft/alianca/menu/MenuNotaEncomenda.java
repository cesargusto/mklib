package br.com.maikosoft.alianca.menu;

import br.com.maikosoft.alianca.view.JanelaNotaEncomenda;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuNotaEncomenda extends MkMenu {

	public MenuNotaEncomenda(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaNotaEncomenda janela = new JanelaNotaEncomenda();
				janela.showWindow("Nota de Encomenda", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Nota de Encomenda";
	}

}
