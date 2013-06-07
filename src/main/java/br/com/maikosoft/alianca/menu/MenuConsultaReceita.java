package br.com.maikosoft.alianca.menu;

import br.com.maikosoft.alianca.view.JanelaReceitaConsulta;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuConsultaReceita extends MkMenu {

	public MenuConsultaReceita(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaReceitaConsulta janela = new JanelaReceitaConsulta();
				janela.showWindow("Consulta Receita", false);
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Consultar Receita";
	}

}
