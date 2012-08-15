package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.view.JanelaLogin;
import br.com.maikosoft.cadmia.view.JanelaRelatorioFinanceiro;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkMenu;

public class MenuRelatorioFinanceiro extends MkMenu {

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				if (JanelaLogin.getInstance().getUsuarioLogado().isAdministrador()) {
					JanelaRelatorioFinanceiro janela = new JanelaRelatorioFinanceiro();
					janela.showWindow(getTitulo(), false);
				} else {
					MkDialog.warm("Acesso Negado");
				}
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Relatório Financeiro";
	}

	@Override
	public MkMenu getPai() {
		return EnumMenu.RELATORIO.getMenu();
	}

}
