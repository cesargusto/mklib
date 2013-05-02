package br.com.maikosoft.cadmia.view;

import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.Usuario;
import br.com.maikosoft.cadmia.service.UsuarioService;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.EnumMkButton;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTable;
import br.com.maikosoft.mklib.MkTableModel;
import br.com.maikosoft.mklib.MkWindow;

@SuppressWarnings("serial")
public class JanelaUsuarioConsulta extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(JanelaUsuarioConsulta.class);
	
	private MkFieldText fieldBusca;
	private MkPanelTable panelCenter;
	private MkTable<Usuario> table;
	
	private UsuarioService usuarioService;
	
	
	@Override
	protected void initWindow() {
		
		panelCenter.addRow("Busca", fieldBusca, EnumMkButton.PESQUISAR.getButton(this), GridBagConstraints.NONE);
		panelCenter.addRow(table.getJScrollPane(), GridBagConstraints.BOTH);
		
		addPanelCenter(panelCenter, 500, 450);
		
		addPanelButton(true, EnumMkButton.ABRIR.getButton(this), EnumMkButton.NOVO.getButton(this));
		
		fieldBusca.onEnter(pesquisar());
		
		table.onDoubleClickOrEnter(abrir());
		
		pesquisar().execute();
		
	}
	
	protected MkRun abrir() {
		return new MkRun() {
			@Override
			public void execute() {
				Usuario bean = table.getSeleted(true);
				if (bean !=null) {
					JanelaUsuarioCadastro view = new JanelaUsuarioCadastro(bean);
					view.showWindow("Cadastro Usuario", false);					
				}
			}
		}; 
	}
	
	protected MkRun pesquisar() {
		return new MkRun() {
			@Override
			public void execute() {
				logger.debug("Executando perquisar");
				Map<String, Object> where = new HashMap<String, Object>();
				where.put("nomeOrId", fieldBusca.getText());
				try {
					List<Usuario> list = usuarioService.findAll(where);
					table.setModel(new MkTableModel<Usuario>(list, "Nome", "Ativo", "Administrador") {
						@Override
						protected Object getRow(Usuario bean, int rowIndex, int columnIndex) {
							switch (columnIndex) {
							case 1:
								return bean.getAtivo();
							case 2:
								return bean.isAdministrador();	
							default:
								return bean.getNome();
							}
						}
					});
				} catch (Exception ex) {
					MkDialog.error("Erro ao pesquisar", ex);
				}
			}
		};
	}
	
	protected MkRun novo() {
		return EnumMenu.CADASTRO_USUARIO_NOVO.getMenu().getAcao();
	}
	
	@Override
	public void refreshWindow() {
		pesquisar().execute();
	}

}
