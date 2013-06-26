package br.com.maikosoft.bazar.menu;

import br.com.maikosoft.bazar.view.JanelaProdutoCadastro;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuCadastroProduto extends MkMenu {

	public MenuCadastroProduto(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaProdutoCadastro janela = new JanelaProdutoCadastro(null);
				janela.showWindow("Cadastro Produto", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Novo Produto";
	}

}
