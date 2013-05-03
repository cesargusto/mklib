package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.view.JanelaRelatorioClientePorModalidade;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkMenu;
import br.com.maikosoft.view.JanelaLogin;

public class MenuRelatorioClientePorModalidade extends MkMenu {

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				if (JanelaLogin.getInstance().getUsuarioLogado().isAdministrador()) {
					JanelaRelatorioClientePorModalidade janela = new JanelaRelatorioClientePorModalidade();
					janela.showWindow(getTitulo(), false);
				} else {
					MkDialog.warm("Acesso Negado");
				}
				
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Relatório Clientes por Modalidade";
	}

	@Override
	public MkMenu getPai() {
		return EnumMenu.RELATORIO.getMenu();
	}

}
