package br.com.maikosoft.alianca.menu;

import br.com.maikosoft.alianca.view.JanelaReceitaCadastro;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuCadastroReceita extends MkMenu {

	public MenuCadastroReceita(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaReceitaCadastro janela = new JanelaReceitaCadastro(null);
				janela.showWindow("Cadastro Receita", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Nova Receita";
	}

}
