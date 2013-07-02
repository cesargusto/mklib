package br.com.maikosoft.bazar;

import java.util.LinkedList;
import java.util.List;

import br.com.maikosoft.bazar.menu.*;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkMenu;
import br.com.maikosoft.view.menu.MenuCadastroUsuario;
import br.com.maikosoft.view.menu.MenuCadastroUsuarioConsulta;
import br.com.maikosoft.view.menu.MenuDebug;
import br.com.maikosoft.view.menu.MenuSair;
import br.com.maikosoft.view.menu.MenuSistemaAtualizar;
import br.com.maikosoft.view.menu.MenuSistemaBackup;



public enum EnumMenuBazar {
	
	CADASTRO("Cadastro", null),
		CADASTRO_CLIENTE("Cliente", CADASTRO.getMenu()),
			CADASTRO_CLIENTE_CONSULTA(new MenuConsultaCliente(CADASTRO_CLIENTE.getMenu())),
    		CADASTRO_CLIENTE_NOVO(new MenuCadastroCliente(CADASTRO_CLIENTE.getMenu())),
    	CADASTRO_PRODUTO("Produto", CADASTRO.getMenu()),
			CADASTRO_PRODUTO_CONSULTA(new MenuConsultaProduto(CADASTRO_PRODUTO.getMenu())),
    		CADASTRO_PRODUTO_NOVO(new MenuCadastroProduto(CADASTRO_PRODUTO.getMenu())),
    	CADASTRO_USUARIO("Usuário", CADASTRO.getMenu()),
    		CADASTRO_USUARIO_CONSULTA(new MenuCadastroUsuarioConsulta(CADASTRO_USUARIO.getMenu())),
    		CADASTRO_USUARIO_NOVO(new MenuCadastroUsuario(CADASTRO_USUARIO.getMenu())),	
    MOVIMENTACAO("Movimentação", null),
		CADASTRO_PEDIDO_CADASTRAR(new MenuCadastroPedido(MOVIMENTACAO.getMenu())),
		CADASTRO_PEDIDO_CONSULTA(new MenuConsultaPedido(MOVIMENTACAO.getMenu())),
    SISTEMA("Sistema", null),
		SISTEMA_BACKUP(new MenuSistemaBackup(SISTEMA.getMenu())),
		SISTEMA_DEBUG(new MenuDebug(SISTEMA.getMenu())),
		SISTEMA_ATUALIZAR(new MenuSistemaAtualizar("Bazar", SISTEMA.getMenu())),
		SISTEMA_SAIR(new MenuSair(SISTEMA.getMenu()));
	
	private final MkMenu menu;
	
	private EnumMenuBazar(MkMenu menu) {
		this.menu = menu;
	}
	
	private EnumMenuBazar(final String titulo,final MkMenu pai) {
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
		
		for (EnumMenuBazar enumMenuBazar : values()) {
			list.add(enumMenuBazar.getMenu());
		}
		
		return list;
	}	
}
