package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.view.JanelaUsuarioConsulta;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.layout.swing.MkMenu;

public class MenuCadastroUsuarioConsulta extends MkMenu {

	@Override
	public MkRun getAcao() {
		return new MkRun() {			
			@Override
			public void execute() {
				new JanelaUsuarioConsulta().showWindow("Consultar Usuário", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Consultar Usuário";
	}

	@Override
	public MkMenu getPai() {
		return EnumMenu.CADASTRO_USUARIO.getMenu();
	}

}
