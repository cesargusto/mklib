package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.Modalidade;
import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.view.JanelaModalidadeCadastro;
import br.com.maikosoft.layout.swing.MkMenu;
import br.com.maikosoft.layout.swing.MkRun;

public class MenuCadastroModalidade extends MkMenu {

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaModalidadeCadastro janela = new JanelaModalidadeCadastro(new Modalidade());
				janela.showView("Cadastro Modalidade", false);
				janela.novo();
				
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Novo Modalidade";
	}

	@Override
	public MkMenu getPai() {
		return EnumMenu.CADASTRO_MODALIDADE.getMenu();
	}

}
