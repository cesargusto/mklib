package br.com.maikosoft.bazar.menu;

import br.com.maikosoft.bazar.Pedido;
import br.com.maikosoft.bazar.view.JanelaPedidoCadastro;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuCadastroPedido extends MkMenu {

	public MenuCadastroPedido(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaPedidoCadastro janela = new JanelaPedidoCadastro(new Pedido());
				janela.showWindow("Cadastro Pedido", false);
				janela.novo();
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Novo Pedido";
	}

}
