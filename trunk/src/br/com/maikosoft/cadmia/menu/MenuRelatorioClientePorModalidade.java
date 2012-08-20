package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.view.JanelaLogin;
import br.com.maikosoft.cadmia.view.JanelaRelatorioClientePorModalidade;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkMenu;

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
