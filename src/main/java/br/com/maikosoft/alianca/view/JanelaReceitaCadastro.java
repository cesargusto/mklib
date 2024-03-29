package br.com.maikosoft.alianca.view;

import java.awt.GridBagConstraints;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.maikosoft.alianca.ClienteAlianca;
import br.com.maikosoft.alianca.Receita;
import br.com.maikosoft.alianca.service.ReceitaService;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.core.MkTransferObject;
import br.com.maikosoft.mklib.MkButton.MkButtonEditar;
import br.com.maikosoft.mklib.MkButton.MkButtonExcluir;
import br.com.maikosoft.mklib.MkButton.MkButtonImprimir;
import br.com.maikosoft.mklib.MkButton.MkButtonNovo;
import br.com.maikosoft.mklib.MkButton.MkButtonPesquisar;
import br.com.maikosoft.mklib.MkButton.MkButtonSalvar;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldDate;
import br.com.maikosoft.mklib.MkFieldMask;
import br.com.maikosoft.mklib.MkFieldMask.EnumMkMask;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkFieldTextComplete;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTextArea;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.util.MkUtil;
import br.com.maikosoft.view.JanelaLogin;
import br.com.maikosoft.view.JanelaPrintPreview;

@SuppressWarnings("serial")
public class JanelaReceitaCadastro extends MkWindow {
	
	private MkFieldText fieldId;
	private MkFieldText fieldCliente;
	private MkFieldMask fieldTelefone;
    private MkFieldTextComplete fieldOftalmologista;
    private MkFieldDate fieldDataReceita;
    private MkFieldText fieldOlhoDireitoLonge;
    private MkFieldText fieldOlhoEsquerdoLonge;
    private MkFieldText fieldOlhoDireitoPerto;
    private MkFieldText fieldOlhoEsquerdoPerto;
    private MkFieldText fieldAdicao;
    private MkFieldText fieldLente;
    private MkFieldText fieldArmacao;
	private MkTextArea textObservacao;
	private MkFieldText fieldDP;
	private MkFieldText fieldAltura;
	
	private MkButtonNovo buttonNovo;
	private MkButtonSalvar buttonSalvar;
	private MkButtonEditar buttonEditar;
	private MkButtonExcluir buttonExcluir;
	private MkButtonImprimir buttonImprimir;
	
	private MkButtonPesquisar buttonCliente;
		
	private Receita bean;
	private ReceitaService receitaService;
	
	public JanelaReceitaCadastro(Receita bean) {
		this.bean = (bean == null ? new Receita() : bean);
	}

	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Codigo:",fieldId, "Data Receita:", fieldDataReceita, GridBagConstraints.NONE);
		panelTable.addRow("Cliente:", new MkPanelTable().addRow(fieldCliente, buttonCliente, GridBagConstraints.NONE));
		panelTable.addRow("Telefone:",fieldTelefone, MkPanelTable.getDefaultCell(1));
		panelTable.addRow("Oftalmologista:", fieldOftalmologista);
		
		panelTable.addRow("", "Direito", GridBagConstraints.CENTER, "Esquerdo", GridBagConstraints.CENTER, "DP", GridBagConstraints.CENTER);
		panelTable.addRow("Longe", fieldOlhoDireitoLonge, fieldOlhoEsquerdoLonge, fieldDP);
		panelTable.addRow("Perto", fieldOlhoDireitoPerto, fieldOlhoEsquerdoPerto, "Altura", GridBagConstraints.CENTER);
		
		panelTable.addRow("Adição", fieldAdicao, MkPanelTable.getDefaultCell(2), fieldAltura);
		
		
		panelTable.addRow("Lente:", fieldLente);
		panelTable.addRow("Armação:", fieldArmacao);
		
		panelTable.addRow(textObservacao.getJScrollPane("Observação"), GridBagConstraints.BOTH);
		
		addPanelCenter(panelTable, 610, 480);
		
		fieldId.setEnabled(false);
		fieldTelefone.setMask(EnumMkMask.CELLPHONE);
	
		addPanelButton(true, buttonImprimir, buttonNovo, buttonSalvar, buttonEditar, buttonExcluir);
		
		if (bean.getId() == null) {
			novo();
		} else {
			beanToForm(false);
		}
		
	}
	
	public void novo() {
		bean = new Receita();
		bean.setDataReceita(new Date());
		beanToForm(true);
		fieldCliente.grabFocus();
		
		try {
			List<String> listOftal = receitaService.findAllOftamologista();
			fieldOftalmologista.addPossibility(listOftal);
		} catch (MkServiceException ex) {
			MkDialog.error(ex.getMessage(), ex);
		}
	}

	protected void editar() {
		beanToForm(true);
	}

	protected void salvar() {
		try {
			
			bean.setCliente(fieldCliente.getText());
			bean.setTelefone(fieldTelefone.getText());
			bean.setOftalmologista(fieldOftalmologista.getText());
			bean.setDataReceita(fieldDataReceita.getDate());
			bean.setOlhoDireitoLonge(fieldOlhoDireitoLonge.getText());
			bean.setOlhoEsquerdoLonge(fieldOlhoEsquerdoLonge.getText());
			bean.setOlhoDireitoPerto(fieldOlhoDireitoPerto.getText());
			bean.setOlhoEsquerdoPerto(fieldOlhoEsquerdoPerto.getText());
			bean.setAdicao(fieldAdicao.getText());
			bean.setLente(fieldLente.getText());
			bean.setArmacao(fieldArmacao.getText());
			bean.setObservacao(textObservacao.getText());
			bean.setDp(fieldDP.getText());
			bean.setAltura(fieldAltura.getText());
			
			if (bean.getId() == null) {
				bean.setOwner(JanelaLogin.getInstance().getUsuarioLogado().getId());
				receitaService.insert(bean);
			} else {
				receitaService.update(bean);
			}
			MkDialog.info("Receita salva com sucesso", buttonSalvar);

			bean = receitaService.findById(bean.getId());
			beanToForm(false);
			application.refreshWindows();

		} catch (Exception ex) {
			MkDialog.error(ex.getMessage(), ex);
		}
	}

	protected void excluir() {
		if (JanelaLogin.getInstance().getUsuarioLogado().isAdministrador()) {
			if (MkDialog.confirm("Deseja excluir esse registro?")) {
				try {
					receitaService.delete(bean.getId());
					closeWindow();
					application.refreshWindows();
				} catch (Exception ex) {
					MkDialog.error(ex.getMessage(), ex);
				}
			}
		} else {
			MkDialog.warm("Acesso Negado");
		}
	}
	
	
	private void beanToForm(boolean isEditMode) {
		if (bean.getId() == null) {
			fieldId.setText("NOVO");
		} else {
			fieldId.setText(bean.getId()+"");
		}
		fieldCliente.setText(bean.getCliente());
		fieldTelefone.setText(bean.getTelefone());
		fieldOftalmologista.setText(bean.getOftalmologista());
		fieldDataReceita.setDate(bean.getDataReceita());
		fieldOlhoDireitoLonge.setText(bean.getOlhoDireitoLonge());
		fieldOlhoEsquerdoLonge.setText(bean.getOlhoEsquerdoLonge());
		fieldOlhoDireitoPerto.setText(bean.getOlhoDireitoPerto());
		fieldOlhoEsquerdoPerto.setText(bean.getOlhoEsquerdoPerto());
		fieldAdicao.setText(bean.getAdicao());
		fieldLente.setText(bean.getLente());
		fieldArmacao.setText(bean.getArmacao());
		textObservacao.setText(bean.getObservacao());
		fieldDP.setText(bean.getDp());
		fieldAltura.setText(bean.getAltura());
		
		fieldCliente.setEditable(isEditMode);
		fieldTelefone.setEditable(isEditMode);
		fieldOftalmologista.setEditable(isEditMode);
		fieldDataReceita.setEditable(isEditMode);
		fieldOlhoDireitoLonge.setEditable(isEditMode);
		fieldOlhoEsquerdoLonge.setEditable(isEditMode);
		fieldOlhoDireitoPerto.setEditable(isEditMode);
		fieldOlhoEsquerdoPerto.setEditable(isEditMode);
		fieldAdicao.setEditable(isEditMode);
		fieldLente.setEditable(isEditMode);
		fieldArmacao.setEditable(isEditMode);
		textObservacao.setEditable(isEditMode);
		fieldDP.setEditable(isEditMode);
		fieldAltura.setEditable(isEditMode);
		
		buttonNovo.setEnabled(!isEditMode);
		buttonSalvar.setEnabled(isEditMode);
		buttonEditar.setEnabled(!isEditMode);
		buttonExcluir.setEnabled(!isEditMode);
		buttonImprimir.setEnabled(!isEditMode);
		
	}
	
	protected void pesquisar() {
		MkTransferObject<ClienteAlianca> transferObject = new MkTransferObject<ClienteAlianca>() {
			@Override
			public void postTranfer(ClienteAlianca clienteAlianca) {
				fieldCliente.setText(clienteAlianca.getNome());
				String telefone = MkUtil.somenteNumero(clienteAlianca.getTelefone1());
				if (telefone.length() == 8) {
					telefone = "44"+telefone;
				}
				fieldTelefone.setText(telefone);
			}
		};
		JanelaClienteConsulta janelaClienteConsulta = new JanelaClienteConsulta();
		janelaClienteConsulta.setTranferir(transferObject);
		janelaClienteConsulta.showWindow("Transferir Cliente", false);
	}
	
	protected void imprimir() {
		try {
			this.waitCursor(true);
			
			List<Receita> list = new LinkedList<Receita>();
			list.add(bean);
			
			InputStream streamResource = JanelaDuplicataGerar.class.getClassLoader().getResourceAsStream("report/alianca/Receita.jasper");
			JasperPrint print = JasperFillManager.fillReport(streamResource, null, new JRBeanCollectionDataSource(list));
			JanelaPrintPreview.showView(print, true);
			
		} catch (Exception ex) {
			MkDialog.error("Erro ao imprimir receita", ex);
		} finally {
			this.waitCursor(false);
		}
			
	}

}
