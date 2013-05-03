package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.cadmia.Modalidade;
import br.com.maikosoft.cadmia.view.JanelaModalidadeCadastro;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;

public class MenuCadastroModalidade extends MkMenu {

	public MenuCadastroModalidade(MkMenu menuPai) {
		super(menuPai);
	}

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

}
