package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.Cliente;
import br.com.maikosoft.cadmia.view.JanelaClienteCadastro;
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
				JanelaClienteCadastro janela = new JanelaClienteCadastro(new Cliente());
				janela.showWindow("Cadastro Cliente", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Novo Cliente";
	}

}
