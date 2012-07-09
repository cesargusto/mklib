package br.com.maikosoft.cadmia;

import java.util.LinkedList;
import java.util.List;

import br.com.maikosoft.cadmia.menu.MenuCadastroCliente;
import br.com.maikosoft.cadmia.menu.MenuCadastroClienteConsulta;
import br.com.maikosoft.cadmia.menu.MenuCadastroModalidade;
import br.com.maikosoft.cadmia.menu.MenuCadastroModalidadeConsulta;
import br.com.maikosoft.cadmia.menu.MenuCadastroUsuario;
import br.com.maikosoft.cadmia.menu.MenuCadastroUsuarioConsulta;
import br.com.maikosoft.cadmia.menu.MenuDebug;
import br.com.maikosoft.cadmia.menu.MenuMovimentacaoLancarMensalidades;
import br.com.maikosoft.cadmia.menu.MenuRelatorioFinanceiro;
import br.com.maikosoft.cadmia.menu.MenuSair;
import br.com.maikosoft.cadmia.menu.MenuSistemaBackup;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.layout.swing.MkMenu;



public enum EnumMenu {
	
	CADASTRO("Cadastro", null),
		CADASTRO_CLIENTE("Cliente", CADASTRO.getMenu()),
			CADASTRO_CLIENTE_CONSULTA(new MenuCadastroClienteConsulta()),
    		CADASTRO_CLIENTE_NOVO(new MenuCadastroCliente()),
    	CADASTRO_MODALIDADE("Modalidade", CADASTRO.getMenu()),
			CADASTRO_MODALIDADE_CONSULTA(new MenuCadastroModalidadeConsulta()),
    		CADASTRO_MODALIDADE_NOVO(new MenuCadastroModalidade()),	
    	CADASTRO_USUARIO("Usuário", CADASTRO.getMenu()),
			CADASTRO_USUARIO_CONSULTA(new MenuCadastroUsuarioConsulta()),
    		CADASTRO_USUARIO_NOVO(new MenuCadastroUsuario()),		
    MOVIMENTACAO("Movimentação", null),
    	MOVIMENTACAO_LANCAR_MENSALIDADES(new MenuMovimentacaoLancarMensalidades()),
    RELATORIO("Relatório", null),
    	RELATORIO_FINANCEIRO(new MenuRelatorioFinanceiro()),
    SISTEMA("Sistema", null),
    	SISTEMA_BACKUP(new MenuSistemaBackup()),
    	SISTEMA_DEBUG(new MenuDebug()),
    	SISTEMA_SAIR(new MenuSair());
	
	private final MkMenu menu;
	
	private EnumMenu(MkMenu menu) {
		this.menu = menu;
	}
	
	private EnumMenu(final String titulo,final MkMenu pai) {
		this.menu = new MkMenu() {
			@Override
			public MkRun getAcao() {
				return null;
			}

			@Override
			public String getTitulo() {
				return titulo;
			}

			@Override
			public MkMenu getPai() {
				return pai;
			}
		};
	}	

	public MkMenu getMenu() {
		return menu;
	}
	
	public static List<MkMenu> getListMenus() {
		LinkedList<MkMenu> list = new LinkedList<MkMenu>();
		
		for (EnumMenu enumMenu : values()) {
			list.add(enumMenu.getMenu());
		}
		
		return list;
	}	
}
