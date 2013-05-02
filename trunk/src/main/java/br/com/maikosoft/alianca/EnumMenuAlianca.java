package br.com.maikosoft.alianca;

import java.util.LinkedList;
import java.util.List;

import br.com.maikosoft.cadmia.menu.MenuCadastroCliente;
import br.com.maikosoft.cadmia.menu.MenuCadastroClienteConsulta;
import br.com.maikosoft.cadmia.menu.MenuCadastroUsuario;
import br.com.maikosoft.cadmia.menu.MenuCadastroUsuarioConsulta;
import br.com.maikosoft.cadmia.menu.MenuDebug;
import br.com.maikosoft.cadmia.menu.MenuSair;
import br.com.maikosoft.cadmia.menu.MenuSistemaAtualizar;
import br.com.maikosoft.cadmia.menu.MenuSistemaBackup;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;



public enum EnumMenuAlianca {
	
	CADASTRO("Cadastro", null),
		CADASTRO_CLIENTE("Cliente", CADASTRO.getMenu()),
			CADASTRO_CLIENTE_CONSULTA(new MenuCadastroClienteConsulta()),
    		CADASTRO_CLIENTE_NOVO(new MenuCadastroCliente()),
//    	CADASTRO_RECEITA("Receita", CADASTRO.getMenu()),
//			CADASTRO_RECEITA_CONSULTA(new MenuCadastroReceitaConsulta()),
//    		CADASTRO_RECEITA_NOVO(new MenuCadastroReceita()),	
    	CADASTRO_USUARIO("Usuário", CADASTRO.getMenu()),
			CADASTRO_USUARIO_CONSULTA(new MenuCadastroUsuarioConsulta()),
    		CADASTRO_USUARIO_NOVO(new MenuCadastroUsuario()),		
    MOVIMENTACAO("Movimentação", null),
    	// DUPLICATA
    SISTEMA("Sistema", null),
    	SISTEMA_BACKUP(new MenuSistemaBackup()),
    	SISTEMA_DEBUG(new MenuDebug()),
    	SISTEMA_ATUALIZAR(new MenuSistemaAtualizar("Otica")),
    	SISTEMA_SAIR(new MenuSair());
	
	private final MkMenu menu;
	
	private EnumMenuAlianca(MkMenu menu) {
		this.menu = menu;
	}
	
	private EnumMenuAlianca(final String titulo,final MkMenu pai) {
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
		
		for (EnumMenuAlianca enumMenuAlianca : values()) {
			list.add(enumMenuAlianca.getMenu());
		}
		
		return list;
	}	
}
