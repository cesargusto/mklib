package br.com.maikosoft.cadmia.view;

import java.awt.GridBagConstraints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.maikosoft.cadmia.EnumMenuCadMia;
import br.com.maikosoft.cadmia.Modalidade;
import br.com.maikosoft.cadmia.service.ModalidadeService;
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
import br.com.maikosoft.util.MkUtil;

@SuppressWarnings("serial")
public class JanelaModalidadeConsulta extends MkWindow {
	
	private static final Logger logger = Logger.getLogger(JanelaModalidadeConsulta.class);
	
	private MkFieldText fieldBusca;
	private MkPanelTable panelCenter;
	private MkTable<Modalidade> table;
	private MkButtonNovo buttonNovo;
	private MkButtonAbrir buttonAbrir;
	private MkButtonPesquisar buttonPesquisar;
	private MkButtonTransferir buttonTransferir;
	
	private ModalidadeService modalidadeService;
	
	private MkTransferObject<Modalidade> transferObject;
	
	@Override
	protected void initWindow() {
		
		panelCenter.addRow("Busca", fieldBusca, buttonPesquisar, GridBagConstraints.NONE);
		panelCenter.addRow(table.getJScrollPane(), GridBagConstraints.BOTH);
		
		addPanelCenter(panelCenter, 500, 450);
		
		addPanelButton(true, buttonTransferir, buttonAbrir, buttonNovo);
		
		fieldBusca.onEnter(buttonPesquisar.getOnClick());
		table.onDoubleClickOrEnter((transferObject==null ? buttonAbrir.getOnClick() : buttonTransferir.getOnClick()));
		
		buttonTransferir.setVisible((transferObject!=null));
		
		pesquisar();
		
	}
	
	protected void abrir() {
		Modalidade bean = table.getSeleted(true);
		if (bean !=null) {
			JanelaModalidadeCadastro view = new JanelaModalidadeCadastro(bean);
			view.showWindow("Cadastro Modalidade", false);					
		}
	}
	
	protected void pesquisar() {
		logger.debug("Executando perquisar");
		Map<String, Object> where = new HashMap<String, Object>();
		where.put("nomeOrId", fieldBusca.getText());
		try {
			List<Modalidade> list = modalidadeService.findAll(where);
			table.setModel(new MkTableModel<Modalidade>(list, "Nome", "Valor") {
				@Override
				protected Object getRow(Modalidade bean, int rowIndex, int columnIndex) {
					switch (columnIndex) {
					case 1:
						return MkUtil.toString(bean.getValor());
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
		EnumMenuCadMia.CADASTRO_MODALIDADE_NOVO.getMenu().getAcao().execute();
	}

	public void setTranferir(MkTransferObject<Modalidade> transferObject) {
		this.transferObject = transferObject;
	}
	
	protected void transferir() {
		Modalidade seleted = table.getSeleted(true);
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
