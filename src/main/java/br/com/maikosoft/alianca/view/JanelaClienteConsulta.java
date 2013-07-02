package br.com.maikosoft.alianca.view;

import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.maikosoft.alianca.ClienteAlianca;
import br.com.maikosoft.alianca.EnumMenuAlianca;
import br.com.maikosoft.alianca.service.ClienteService;
import br.com.maikosoft.core.MkTransferObject;
import br.com.maikosoft.mklib.MkButton.MkButtonAbrir;
import br.com.maikosoft.mklib.MkButton.MkButtonNovo;
import br.com.maikosoft.mklib.MkButton.MkButtonPesquisar;
import br.com.maikosoft.mklib.MkButton.MkButtonTransferir;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTable;
import br.com.maikosoft.mklib.MkTableModel;
import br.com.maikosoft.mklib.MkWindow;

@SuppressWarnings("serial")
public class JanelaClienteConsulta extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(JanelaClienteConsulta.class);
	
	private MkFieldText fieldBusca;
	private MkPanelTable panelCenter;
	private MkTable<ClienteAlianca> table;
	private MkButtonNovo buttonNovo;
	private MkButtonAbrir buttonAbrir;
	private MkButtonPesquisar buttonPesquisar;
	private MkButtonTransferir buttonTransferir;
	
	
	private MkTransferObject<ClienteAlianca> transferObject;
	private ClienteService clienteService;
	
	@Override
	protected void initWindow() {
		
		panelCenter.addRow("Busca", fieldBusca, buttonPesquisar, GridBagConstraints.NONE);
		panelCenter.addRow(table.getJScrollPane(), GridBagConstraints.BOTH);
		
		addPanelCenter(panelCenter, 700, 450);
		
		addPanelButton(true, buttonTransferir, buttonAbrir, buttonNovo);
		
		fieldBusca.onEnter(buttonPesquisar.getOnClick());
		table.onDoubleClickOrEnter((transferObject==null ? buttonAbrir.getOnClick() : buttonTransferir.getOnClick()));
		
		buttonTransferir.setVisible((transferObject!=null));
		
	}
	
	protected void abrir() {
		ClienteAlianca bean = table.getSeleted(true);
		if (bean !=null) {
			JanelaClienteCadastro view = new JanelaClienteCadastro(bean);
			view.showWindow("Cadastro Cliente", false);					
		} 
	}
	
	protected void pesquisar() {
		logger.debug("Executando perquisar");
		Map<String, Object> where = new HashMap<String, Object>();
		where.put("nomeOrId", fieldBusca.getText());
		try {
			List<ClienteAlianca> list = clienteService.findAll(where);
			setPesquisa(list);
		} catch (Exception ex) {
			MkDialog.error("Erro ao pesquisar", ex);
		}
	}
	
	protected void novo() {
		EnumMenuAlianca.CADASTRO_CLIENTE_NOVO.getMenu().getAcao().execute();
	}

	public void setPesquisa(List<ClienteAlianca> list) {
		table.setModel(new MkTableModel<ClienteAlianca>(list, "Nome", "Telefone 1", "Telefone 2") {
			@Override
			protected Object getRow(ClienteAlianca bean, int rowIndex, int columnIndex) {
				switch (columnIndex) {
				case 1:
					return bean.getTelefone1();
				case 2:
					return bean.getTelefone2();
				default:
					return bean.getNome();
				}
			}
		});
		if (list.size() >0 ) {
			table.requestFocusInWindow();
			table.getColumnModel().getColumn(0).setPreferredWidth(400);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
		}
	}
	
	public void setTranferir(MkTransferObject<ClienteAlianca> transferObject) {
		this.transferObject = transferObject;
	}
	
	protected void transferir() {
		ClienteAlianca seleted = table.getSeleted(true);
		if (seleted != null) {
			transferObject.postTranfer(seleted);
			closeWindow();
		}
	}

	@Override
	public void refreshWindow() {
		pesquisar();
	}
	
	

}
