package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.Cliente;
import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.view.JanelaClienteCadastro;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.layout.swing.MkMenu;

public class MenuCadastroCliente extends MkMenu {

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaClienteCadastro janela = new JanelaClienteCadastro(new Cliente());
				janela.showView("Cadastro Cliente", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Novo Cliente";
	}

	@Override
	public MkMenu getPai() {
		return EnumMenu.CADASTRO_CLIENTE.getMenu();
	}

}