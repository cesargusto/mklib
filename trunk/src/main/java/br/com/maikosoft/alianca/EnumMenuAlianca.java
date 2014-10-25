package br.com.maikosoft.alianca;

import java.util.LinkedList;
import java.util.List;

import br.com.maikosoft.alianca.menu.MenuCadastroCliente;
import br.com.maikosoft.alianca.menu.MenuCadastroReceita;
import br.com.maikosoft.alianca.menu.MenuConsultaCliente;
import br.com.maikosoft.alianca.menu.MenuConsultaDuplicata;
import br.com.maikosoft.alianca.menu.MenuConsultaReceita;
import br.com.maikosoft.alianca.menu.MenuGerarDuplicata;
import br.com.maikosoft.alianca.menu.MenuNotaEncomenda;
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
			CADASTRO_CLIENTE_CONSULTA(new MenuConsultaCliente(CADASTRO_CLIENTE.getMenu())),
    		CADASTRO_CLIENTE_NOVO(new MenuCadastroCliente(CADASTRO_CLIENTE.getMenu())),
    	CADASTRO_RECEITA("Receita", CADASTRO.getMenu()),
			CADASTRO_RECEITA_CONSULTA(new MenuConsultaReceita(CADASTRO_RECEITA.getMenu())),
    		CADASTRO_RECEITA_NOVO(new MenuCadastroReceita(CADASTRO_RECEITA.getMenu())),
    	CADASTRO_USUARIO("Usuário", CADASTRO.getMenu()),
    		CADASTRO_USUARIO_CONSULTA(new MenuCadastroUsuarioConsulta(CADASTRO_USUARIO.getMenu())),
    		CADASTRO_USUARIO_NOVO(new MenuCadastroUsuario(CADASTRO_USUARIO.getMenu())),	
    MOVIMENTACAO("Movimentação", null),
    	CADASTRO_DUPLICATA_CONSULTA(new MenuConsultaDuplicata(MOVIMENTACAO.getMenu())),
    	CADASTRO_DUPLICATA_GERAR(new MenuGerarDuplicata(MOVIMENTACAO.getMenu())),
    	NOTA_ENCOMENDA(new MenuNotaEncomenda(MOVIMENTACAO.getMenu())),
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
