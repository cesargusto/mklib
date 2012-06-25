package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.Usuario;
import br.com.maikosoft.cadmia.view.JanelaUsuarioCadastro;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.layout.swing.MkMenu;

public class MenuCadastroUsuario extends MkMenu {

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaUsuarioCadastro janela = new JanelaUsuarioCadastro(new Usuario());
				janela.showView("Cadastro Usuário", false);
				janela.novo();
				
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Novo Usuário";
	}

	@Override
	public MkMenu getPai() {
		return EnumMenu.CADASTRO_USUARIO.getMenu();
	}

}
