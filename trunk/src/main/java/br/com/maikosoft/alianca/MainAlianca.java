package br.com.maikosoft.alianca;

import br.com.maikosoft.cadmia.view.JanelaClienteConsultaRapida;
import br.com.maikosoft.cadmia.view.JanelaLogin;
import br.com.maikosoft.mklib.MkApplication;

public class MainAlianca {

	public static void main(String[] args) {
		
		MkApplication application = MkApplication.getInstance();
		
		application.init("Ótica Aliança", "alianca");
		
		JanelaLogin.getInstance();
		
		application.updateMkMenu(EnumMenuAlianca.getListMenus());
		
		new JanelaClienteConsultaRapida().showWindow();
	}

}
