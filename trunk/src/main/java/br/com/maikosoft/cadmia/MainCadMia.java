package br.com.maikosoft.cadmia;

import br.com.maikosoft.cadmia.view.JanelaClienteConsultaRapida;
import br.com.maikosoft.mklib.MkApplication;
import br.com.maikosoft.view.JanelaLogin;

public class MainCadMia {

	public static void main(String[] args) {
		
		MkApplication application = MkApplication.getInstance();
		
		application.init("CadMia", "cadmia");
		
		JanelaLogin.getInstance();
		
		application.updateMkMenu(EnumMenuCadMia.getListMenus());
		
		new JanelaClienteConsultaRapida().showWindow();
	}

}
