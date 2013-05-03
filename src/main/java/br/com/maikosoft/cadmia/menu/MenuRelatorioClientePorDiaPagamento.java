package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.view.JanelaRelatorioClientePorDiaPagamento;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuRelatorioClientePorDiaPagamento extends MkMenu {

	public MenuRelatorioClientePorDiaPagamento(MkMenu menuPai) {
		super(menuPai);
	}

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

}
