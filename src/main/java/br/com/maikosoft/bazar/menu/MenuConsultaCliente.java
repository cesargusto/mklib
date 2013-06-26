package br.com.maikosoft.bazar.menu;

import br.com.maikosoft.bazar.view.JanelaClienteConsulta;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuConsultaCliente extends MkMenu {

	public MenuConsultaCliente(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaClienteConsulta janela = new JanelaClienteConsulta();
				janela.showWindow("Consulta Cliente", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Consultar Cliente";
	}

}
