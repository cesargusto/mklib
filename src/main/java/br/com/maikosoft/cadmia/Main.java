package br.com.maikosoft.cadmia;

import br.com.maikosoft.cadmia.view.JanelaClienteConsultaRapida;
import br.com.maikosoft.cadmia.view.JanelaLogin;
import br.com.maikosoft.mklib.MkApplication;

public class Main {

	public static void main(String[] args) {
		
		MkApplication application = MkApplication.getInstance();
		application.init("CadMia");
			
		JanelaLogin.getInstance();
		
		application.updateMkMenu(EnumMenu.getListMenus());
		
		new JanelaClienteConsultaRapida().showWindow();
	}

}
