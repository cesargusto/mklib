package br.com.maikosoft.cadmia;

import java.util.LinkedList;
import java.util.List;

import br.com.maikosoft.cadmia.menu.MenuCadastroModalidade;
import br.com.maikosoft.cadmia.menu.MenuCadastroModalidadeConsulta;
import br.com.maikosoft.cadmia.menu.MenuMovimentacaoLancarMensalidades;
import br.com.maikosoft.cadmia.menu.MenuMovimentacaoRecibo;
import br.com.maikosoft.cadmia.menu.MenuRelatorioClientePorDiaPagamento;
import br.com.maikosoft.cadmia.menu.MenuRelatorioClientePorModalidade;
import br.com.maikosoft.cadmia.menu.MenuRelatorioFinanceiro;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;
import br.com.maikosoft.view.menu.MenuCadastroCliente;
import br.com.maikosoft.view.menu.MenuCadastroClienteConsulta;
import br.com.maikosoft.view.menu.MenuCadastroUsuario;
import br.com.maikosoft.view.menu.MenuCadastroUsuarioConsulta;
import br.com.maikosoft.view.menu.MenuDebug;
import br.com.maikosoft.view.menu.MenuSair;
import br.com.maikosoft.view.menu.MenuSistemaAtualizar;
import br.com.maikosoft.view.menu.MenuSistemaBackup;



public enum EnumMenuCadMia {
	
	CADASTRO("Cadastro", null),
		CADASTRO_CLIENTE("Cliente", CADASTRO.getMenu()),
			CADASTRO_CLIENTE_CONSULTA(new MenuCadastroClienteConsulta(CADASTRO_CLIENTE.getMenu())),
    		CADASTRO_CLIENTE_NOVO(new MenuCadastroCliente(CADASTRO_CLIENTE.getMenu())),
    	CADASTRO_MODALIDADE("Modalidade", CADASTRO.getMenu()),
			CADASTRO_MODALIDADE_CONSULTA(new MenuCadastroModalidadeConsulta(CADASTRO_MODALIDADE.getMenu())),
    		CADASTRO_MODALIDADE_NOVO(new MenuCadastroModalidade(CADASTRO_MODALIDADE.getMenu())),	
    	CADASTRO_USUARIO("Usuário", CADASTRO.getMenu()),
			CADASTRO_USUARIO_CONSULTA(new MenuCadastroUsuarioConsulta(CADASTRO_USUARIO.getMenu())),
    		CADASTRO_USUARIO_NOVO(new MenuCadastroUsuario(CADASTRO_USUARIO.getMenu())),		
    MOVIMENTACAO("Movimentação", null),
    	MOVIMENTACAO_LANCAR_MENSALIDADES(new MenuMovimentacaoLancarMensalidades(MOVIMENTACAO.getMenu())),
    	MOVIMENTACAO_RECIBO(new MenuMovimentacaoRecibo(MOVIMENTACAO.getMenu())),
    RELATORIO("Relatório", null),
    	RELATORIO_FINANCEIRO(new MenuRelatorioFinanceiro(RELATORIO.getMenu())),
    	RELATORIO_CLIENTE_POR_MODALIDADE(new MenuRelatorioClientePorModalidade(RELATORIO.getMenu())),
    	RELATORIO_CLIENTE_POR_DIA_PAGAMENTO(new MenuRelatorioClientePorDiaPagamento(RELATORIO.getMenu())),
    SISTEMA("Sistema", null),
    	SISTEMA_BACKUP(new MenuSistemaBackup(SISTEMA.getMenu())),
    	SISTEMA_DEBUG(new MenuDebug(SISTEMA.getMenu())),
    	SISTEMA_ATUALIZAR(new MenuSistemaAtualizar("CadMia", SISTEMA.getMenu())),
    	SISTEMA_SAIR(new MenuSair(SISTEMA.getMenu()));
	
	private final MkMenu menu;
	
	private EnumMenuCadMia(MkMenu menu) {
		this.menu = menu;
	}
	
	private EnumMenuCadMia(final String titulo,final MkMenu pai) {
		this.menu = new MkMenu(pai) {
			@Override
			public MkRun getAcao() {
				return null;
			}

			@Override
			public String getTitulo() {
				return titulo;
			}
		};
	}	

	public MkMenu getMenu() {
		return menu;
	}
	
	public static List<MkMenu> getListMenus() {
		LinkedList<MkMenu> list = new LinkedList<MkMenu>();
		
		for (EnumMenuCadMia enumMenuCadMia : values()) {
			list.add(enumMenuCadMia.getMenu());
		}
		
		return list;
	}	
}
