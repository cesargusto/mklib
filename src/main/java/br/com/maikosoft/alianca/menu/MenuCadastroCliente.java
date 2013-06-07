package br.com.maikosoft.alianca.menu;

import br.com.maikosoft.alianca.view.JanelaClienteCadastro;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuCadastroCliente extends MkMenu {

	public MenuCadastroCliente(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaClienteCadastro janela = new JanelaClienteCadastro(null);
				janela.showWindow("Cadastro Cliente", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Novo Cliente";
	}

}
