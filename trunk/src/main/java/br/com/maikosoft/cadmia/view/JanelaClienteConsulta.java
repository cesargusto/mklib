package br.com.maikosoft.cadmia.view;

import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.maikosoft.cadmia.Cliente;
import br.com.maikosoft.cadmia.EnumMenuCadMia;
import br.com.maikosoft.cadmia.service.ClienteService;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.core.MkTransferObject;
import br.com.maikosoft.mklib.EnumMkButton;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTable;
import br.com.maikosoft.mklib.MkTableModel;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.mklib.MkButton.MkButtonTransferir;

@SuppressWarnings("serial")
public class JanelaClienteConsulta extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(JanelaClienteConsulta.class);
	
	private MkFieldText fieldBusca;
	private MkPanelTable panelCenter;
	private MkTable<Cliente> table;
	private MkButtonTransferir buttonTransferir;
	
	private MkTransferObject<Cliente> transferObject;
	private ClienteService clienteService;
	
	@Override
	protected void initWindow() {
		
		panelCenter.addRow("Busca", fieldBusca, EnumMkButton.PESQUISAR.getButton(this), GridBagConstraints.NONE);
		panelCenter.addRow(table.getJScrollPane(), GridBagConstraints.BOTH);
		
		addPanelCenter(panelCenter, 500, 450);
		
		addPanelButton(true, buttonTransferir, EnumMkButton.ABRIR.getButton(this), EnumMkButton.NOVO.getButton(this));
		
		fieldBusca.onEnter(pesquisar());
		table.onDoubleClickOrEnter((transferObject==null ? abrir() : new MkRun() {
			@Override
			public void execute() {
				transferir();
			}
		}));
		
		buttonTransferir.setVisible((transferObject!=null));
		
	}
	
	protected MkRun abrir() {
		return new MkRun() {
			@Override
			public void execute() {
				Cliente bean = table.getSeleted(true);
				if (bean !=null) {
					JanelaClienteCadastro view = new JanelaClienteCadastro(bean);
					view.showWindow("Cadastro Cliente", false);					
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
					setPesquisa(list);
				} catch (Exception ex) {
					MkDialog.error("Erro ao pesquisar", ex);
				}
			}
		};
	}
	
	protected MkRun novo() {
		return EnumMenuCadMia.CADASTRO_CLIENTE_NOVO.getMenu().getAcao();
	}

	public void setPesquisa(List<Cliente> list) {
		table.setModel(new MkTableModel<Cliente>(list, "Nome") {
			@Override
			protected Object getRow(Cliente bean, int rowIndex, int columnIndex) {
				return bean.getNome();
			}
		});
		if (list.size() >0 ) {
			table.requestFocusInWindow();
		}
	}
	
	public void setTranferir(MkTransferObject<Cliente> transferObject) {
		this.transferObject = transferObject;
	}
	
	protected void transferir() {
		Cliente seleted = table.getSeleted(true);
		if (seleted != null) {
			transferObject.postTranfer(seleted);
			closeWindow();
		}
	}

	@Override
	public void refreshWindow() {
		pesquisar().execute();
	}
	
	

}
