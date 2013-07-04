package br.com.maikosoft.bazar.menu;

import br.com.maikosoft.bazar.view.JanelaRelatorioPedido;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkMenu;
import br.com.maikosoft.view.JanelaLogin;

public class MenuRelatorioPedido extends MkMenu {

	public MenuRelatorioPedido(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				if (JanelaLogin.getInstance().getUsuarioLogado().isAdministrador()) {
					JanelaRelatorioPedido janela = new JanelaRelatorioPedido();
					janela.showWindow(getTitulo(), false);
				} else {
					MkDialog.warm("Acesso Negado");
				}
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Relatório Pedido";
	}
	
}
