package br.com.maikosoft.cadmia.menu;

import br.com.maikosoft.Usuario;
import br.com.maikosoft.cadmia.view.JanelaRelatorioClientePorDiaPagamento;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkMenu;
import br.com.maikosoft.view.JanelaLogin;

public class MenuRelatorioClientePorDiaPagamento extends MkMenu {

	public MenuRelatorioClientePorDiaPagamento(MkMenu menuPai) {
		super(menuPai);
	}

	@Override
	public MkRun getAcao() {
		return new MkRun() {
			@Override
			public void execute() {
				Usuario usuarioLogado = JanelaLogin.getInstance().getUsuarioLogado();
				if (usuarioLogado.isAdministrador() || usuarioLogado.getId().equals(3L)) {
					JanelaRelatorioClientePorDiaPagamento janela = new JanelaRelatorioClientePorDiaPagamento();
					janela.showWindow(getTitulo(), false);
				} else {
					MkDialog.warm("Acesso Negado");
				}
			}
		};
	}

	@Override
	public String getTitulo() {
		return "Relatório Clientes por Dia do Pagamento";
	}

}
