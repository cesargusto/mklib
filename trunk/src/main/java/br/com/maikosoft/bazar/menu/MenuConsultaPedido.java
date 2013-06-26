package br.com.maikosoft.bazar.menu;

import br.com.maikosoft.bazar.view.JanelaClienteConsulta;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuConsultaPedido extends MkMenu {

	public MenuConsultaPedido(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaClienteConsulta janela = new JanelaClienteConsulta();
				janela.showWindow("Consulta Pedido", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Consultar Pedidos";
	}

}
