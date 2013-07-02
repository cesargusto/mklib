package br.com.maikosoft.bazar.view;

import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.maikosoft.bazar.ClienteBazar;
import br.com.maikosoft.bazar.EnumMenuBazar;
import br.com.maikosoft.bazar.service.ClienteService;
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
	private MkTable<ClienteBazar> table;
	private MkButtonTransferir buttonTransferir;
	private MkButtonNovo buttonNovo;
	private MkButtonAbrir buttonAbrir;
	private MkButtonPesquisar buttonPesquisar;
	
	private MkTransferObject<ClienteBazar> transferObject;
	private ClienteService clienteService;
	
	@Override
	protected void initWindow() {
		
		panelCenter.addRow("Busca", fieldBusca, buttonPesquisar, GridBagConstraints.NONE);
		panelCenter.addRow(table.getJScrollPane(), GridBagConstraints.BOTH);
		
		addPanelCenter(panelCenter, 500, 450);
		
		addPanelButton(true, buttonTransferir, buttonAbrir, buttonNovo);
		
		fieldBusca.onEnter(buttonPesquisar.getOnClick());
		table.onDoubleClickOrEnter((transferObject==null ? buttonAbrir.getOnClick() : buttonTransferir.getOnClick()));
		
		buttonTransferir.setVisible((transferObject!=null));
		
	}
	
	protected void abrir() {
		ClienteBazar bean = table.getSeleted(true);
		if (bean != null) {
			JanelaClienteCadastro view = new JanelaClienteCadastro(bean);
			view.showWindow("Cadastro Cliente", false);
		}
	}
	
	protected void pesquisar() {
		logger.debug("Executando perquisar");
		Map<String, Object> where = new HashMap<String, Object>();
		where.put("nomeOrId", fieldBusca.getText());
		try {
			List<ClienteBazar> list = clienteService.findAll(where);
			setPesquisa(list);
		} catch (Exception ex) {
			MkDialog.error("Erro ao pesquisar", ex);
		}
	}
	
	protected void novo() {
		EnumMenuBazar.CADASTRO_CLIENTE_NOVO.getMenu().getAcao().execute();
	}

	public void setPesquisa(List<ClienteBazar> list) {
		table.setModel(new MkTableModel<ClienteBazar>(list, "Nome") {
			@Override
			protected Object getRow(ClienteBazar bean, int rowIndex, int columnIndex) {
				return bean.getNome();
			}
		});
		if (list.size() > 0 ) {
			table.requestFocusInWindow();
		}
	}
	
	public void setTranferir(MkTransferObject<ClienteBazar> transferObject) {
		this.transferObject = transferObject;
	}
	
	protected void transferir() {
		ClienteBazar seleted = table.getSeleted(true);
		if (seleted != null) {
			closeWindow();
			transferObject.postTranfer(seleted);
		}
	}

	@Override
	public void refreshWindow() {
		pesquisar();
	}
	
	

}
