package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.Modalidade;
import br.com.maikosoft.cadmia.view.JanelaModalidadeCadastro;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.layout.swing.MkMenu;

public class MenuCadastroModalidade extends MkMenu {

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			
			@Override
			public void execute() {
				JanelaModalidadeCadastro janela = new JanelaModalidadeCadastro(new Modalidade());
				janela.showWindow("Cadastro Modalidade", false);
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
