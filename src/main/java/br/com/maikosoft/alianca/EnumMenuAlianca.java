package br.com.maikosoft.alianca;

import java.util.LinkedList;
import java.util.List;

import br.com.maikosoft.alianca.view.JanelaClienteCadastro;
import br.com.maikosoft.alianca.view.JanelaClienteConsulta;
import br.com.maikosoft.alianca.view.JanelaDuplicataConsulta;
import br.com.maikosoft.alianca.view.JanelaReceitaCadastro;
import br.com.maikosoft.alianca.view.JanelaReceitaConsulta;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;
import br.com.maikosoft.view.menu.MenuCadastroUsuario;
import br.com.maikosoft.view.menu.MenuCadastroUsuarioConsulta;
import br.com.maikosoft.view.menu.MenuDebug;
import br.com.maikosoft.view.menu.MenuSair;
import br.com.maikosoft.view.menu.MenuSistemaAtualizar;
import br.com.maikosoft.view.menu.MenuSistemaBackup;



public enum EnumMenuAlianca {
	
	CADASTRO("Cadastro", null),
		CADASTRO_CLIENTE("Cliente", CADASTRO.getMenu()),
			CADASTRO_CLIENTE_CONSULTA(MkMenu.createMenu(CADASTRO_CLIENTE.getMenu(), "Consulta Cliente", new JanelaClienteConsulta())),
    		CADASTRO_CLIENTE_NOVO(MkMenu.createMenu(CADASTRO_CLIENTE.getMenu(), "Cadastro Cliente", new JanelaClienteCadastro(null))),
    	CADASTRO_RECEITA("Receita", CADASTRO.getMenu()),
			CADASTRO_RECEITA_CONSULTA(MkMenu.createMenu(CADASTRO_RECEITA.getMenu(), "Consulta Receita", new JanelaReceitaConsulta())),
    		CADASTRO_RECEITA_NOVO(MkMenu.createMenu(CADASTRO_RECEITA.getMenu(), "Cadastro Receita", new JanelaReceitaCadastro(null))),
    	CADASTRO_USUARIO("Usuário", CADASTRO.getMenu()),
    		CADASTRO_USUARIO_CONSULTA(new MenuCadastroUsuarioConsulta(CADASTRO_USUARIO.getMenu())),
    		CADASTRO_USUARIO_NOVO(new MenuCadastroUsuario(CADASTRO_USUARIO.getMenu())),	
    MOVIMENTACAO("Movimentação", null),
    	CADASTRO_DUPLICATA_CONSULTA(MkMenu.createMenu(MOVIMENTACAO.getMenu(), "Consulta Duplicata", new JanelaDuplicataConsulta())),
//    	CADASTRO_DUPLICATA_NOVO(MkMenu.createMenu(MOVIMENTACAO.getMenu(), "Cadastro Duplicata", new JanelaDuplicataCadastro(null))),
    SISTEMA("Sistema", null),
		SISTEMA_BACKUP(new MenuSistemaBackup(SISTEMA.getMenu())),
		SISTEMA_DEBUG(new MenuDebug(SISTEMA.getMenu())),
		SISTEMA_ATUALIZAR(new MenuSistemaAtualizar("Alianca", SISTEMA.getMenu())),
		SISTEMA_SAIR(new MenuSair(SISTEMA.getMenu()));
	
	private final MkMenu menu;
	
	private EnumMenuAlianca(MkMenu menu) {
		this.menu = menu;
	}
	
	private EnumMenuAlianca(final String titulo,final MkMenu pai) {
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
		
		for (EnumMenuAlianca enumMenuAlianca : values()) {
			list.add(enumMenuAlianca.getMenu());
		}
		
		return list;
	}	
}
