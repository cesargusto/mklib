package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.view.JanelaRelatorioClientePorDiaPagamento;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.layout.swing.MkMenu;

public class MenuRelatorioClientePorDiaPagamento extends MkMenu {

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaRelatorioClientePorDiaPagamento janela = new JanelaRelatorioClientePorDiaPagamento();
				janela.showWindow(getTitulo(), false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Relatório Clientes por Dia do Pagamento";
	}

	@Override
	public MkMenu getPai() {
		return EnumMenu.RELATORIO.getMenu();
	}

}
