package br.com.maikosoft.view.menu;

import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkMenu;
import br.com.maikosoft.view.JanelaLogin;
import br.com.maikosoft.view.JanelaUsuarioConsulta;

public class MenuCadastroUsuarioConsulta extends MkMenu {

	public MenuCadastroUsuarioConsulta(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {			
			@Override
			public void execute() {
				if (JanelaLogin.getInstance().getUsuarioLogado().isAdministrador()) {
					new JanelaUsuarioConsulta().showWindow("Consultar Usuário", false);
				} else {
					MkDialog.warm("Acesso Negado");
				}
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Consultar Usuário";
	}
	
}
