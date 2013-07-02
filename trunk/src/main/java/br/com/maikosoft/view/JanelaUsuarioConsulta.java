package br.com.maikosoft.view;

import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.maikosoft.Usuario;
import br.com.maikosoft.cadmia.EnumMenuCadMia;
import br.com.maikosoft.mklib.MkButton.MkButtonAbrir;
import br.com.maikosoft.mklib.MkButton.MkButtonNovo;
import br.com.maikosoft.mklib.MkButton.MkButtonPesquisar;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTable;
import br.com.maikosoft.mklib.MkTableModel;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.service.UsuarioService;

@SuppressWarnings("serial")
public class JanelaUsuarioConsulta extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(JanelaUsuarioConsulta.class);
	
	private MkFieldText fieldBusca;
	private MkPanelTable panelCenter;
	private MkTable<Usuario> table;
	private MkButtonNovo buttonNovo;
	private MkButtonAbrir buttonAbrir;
	private MkButtonPesquisar buttonPesquisar;
	
	private UsuarioService usuarioService;
	
	
	@Override
	protected void initWindow() {
		
		panelCenter.addRow("Busca", fieldBusca, buttonPesquisar, GridBagConstraints.NONE);
		panelCenter.addRow(table.getJScrollPane(), GridBagConstraints.BOTH);
		
		addPanelCenter(panelCenter, 500, 450);
		
		addPanelButton(true, buttonAbrir, buttonNovo);
		
		fieldBusca.onEnter(buttonPesquisar.getOnClick());		
		table.onDoubleClickOrEnter(buttonAbrir.getOnClick());
		
		pesquisar();
		
	}
	
	protected void abrir() {
		Usuario bean = table.getSeleted(true);
		if (bean !=null) {
			JanelaUsuarioCadastro view = new JanelaUsuarioCadastro(bean);
			view.showWindow("Cadastro Usuario", false);					
		}
	}
	
	protected void pesquisar() {
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
	
	protected void novo() {
		EnumMenuCadMia.CADASTRO_USUARIO_NOVO.getMenu().getAcao().execute();
	}
	
	@Override
	public void refreshWindow() {
		pesquisar();
	}

}
