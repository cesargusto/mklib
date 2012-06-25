package br.com.maikosoft.cadmia.view;

import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import br.com.maikosoft.cadmia.Cliente;
import br.com.maikosoft.cadmia.EnumMenu;
import br.com.maikosoft.cadmia.service.ClienteService;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.layout.swing.EnumMkButton;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkFieldText;
import br.com.maikosoft.layout.swing.MkPanelTable;
import br.com.maikosoft.layout.swing.MkTable;
import br.com.maikosoft.layout.swing.MkTableModel;
import br.com.maikosoft.layout.swing.MkWindow;

@SuppressWarnings("serial")
public class JanelaClienteConsulta extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(JanelaClienteConsulta.class);
	
	private MkFieldText fieldBusca;
	private MkPanelTable panelCenter;
	private MkTable<Cliente> table;
	
	private ClienteService clienteService;
	
	@Override
	protected void initWindow() {
		
		panelCenter.addRow("Busca", fieldBusca, EnumMkButton.PESQUISAR.getButton(this), GridBagConstraints.NONE);
		panelCenter.addRow(table.getJScrollPane(), GridBagConstraints.BOTH);
		
		addPanelCenter(panelCenter, 500, 450);
		
		addPanelButton(true, EnumMkButton.ABRIR.getButton(this), EnumMkButton.NOVO.getButton(this));
		
		fieldBusca.onEnter(pesquisar());		
		table.onDoubleClickOrEnter(abrir());
		
	}
	
	protected MkRun abrir() {
		return new MkRun() {
			@Override
			public void execute() {
				Cliente bean = table.getSeleted(true);
				if (bean !=null) {
					JanelaClienteCadastro view = new JanelaClienteCadastro(bean);
					view.showView("Cadastro Cliente", false);					
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
					List<Cliente> list = clienteService.findAll(where);
					table.setModel(new MkTableModel<Cliente>(list, "Nome") {
						@Override
						protected Object getRow(Cliente bean, int rowIndex, int columnIndex) {
							return bean.getNome();
						}
					});
					
				} catch (Exception ex) {
					MkDialog.error("Erro ao pesquisar", ex);
				}
			}
		};
	}
	
	protected MkRun novo() {
		return EnumMenu.CADASTRO_CLIENTE_NOVO.getMenu().getAcao();
	}

}
