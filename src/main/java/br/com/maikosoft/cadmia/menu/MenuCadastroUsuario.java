package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.Usuario;
import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkMenu;
import br.com.maikosoft.view.JanelaLogin;
import br.com.maikosoft.view.JanelaUsuarioCadastro;

public class MenuCadastroUsuario extends MkMenu {

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				if (JanelaLogin.getInstance().getUsuarioLogado().isAdministrador()) {
					JanelaUsuarioCadastro janela = new JanelaUsuarioCadastro(new Usuario());
					janela.showWindow("Cadastro Usuário", false);
					janela.novo();
				} else {
					MkDialog.warm("Acesso Negado");
				}
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
