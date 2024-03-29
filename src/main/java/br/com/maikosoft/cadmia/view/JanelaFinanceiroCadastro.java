package br.com.maikosoft.cadmia.view;

import java.awt.GridBagConstraints;
import java.util.Date;

import br.com.maikosoft.cadmia.ClienteCadMia;
import br.com.maikosoft.cadmia.Financeiro;
import br.com.maikosoft.cadmia.service.FinanceiroService;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldDate;
import br.com.maikosoft.mklib.MkFieldMask;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTextArea;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.mklib.MkButton.MkButtonEditar;
import br.com.maikosoft.mklib.MkButton.MkButtonExcluir;
import br.com.maikosoft.mklib.MkButton.MkButtonNovo;
import br.com.maikosoft.mklib.MkButton.MkButtonSalvar;
import br.com.maikosoft.mklib.MkFieldMask.EnumMkMask;
import br.com.maikosoft.util.MkUtil;
import br.com.maikosoft.view.JanelaLogin;

@SuppressWarnings("serial")
public class JanelaFinanceiroCadastro extends MkWindow {
	
	private MkFieldText fieldId;
	private MkFieldText fieldCliente;
	private MkFieldText fieldReferencia;
	private MkFieldDate fieldDataCadastro;
	private MkFieldDate fieldDataPagamento;
	private MkFieldMask fieldValor;
	private MkTextArea textObservacao;
	
	private MkButtonNovo buttonNovo;
	private MkButtonSalvar buttonSalvar;
	private MkButtonEditar buttonEditar;
	private MkButtonExcluir buttonExcluir;
		
	private Financeiro bean;
	private FinanceiroService financeiroService;
	
	public JanelaFinanceiroCadastro(Financeiro bean) {
		this.bean = bean;
	}

	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Codigo:",fieldId, "Data Cadastro:", fieldDataCadastro);
		panelTable.addRow("Cliente:",fieldCliente);
		panelTable.addRow("Referência:",fieldReferencia);
		panelTable.addRow("Valor:", fieldValor, "Data Pagamento:", fieldDataPagamento);
		panelTable.addRow(textObservacao.getJScrollPane("Observação"), GridBagConstraints.BOTH);
		
		addPanelCenter(panelTable, 500, 350);
		fieldValor.setColumns(10);
		fieldValor.setMask(EnumMkMask.CURRENCY);
		fieldId.setEnabled(false);
		fieldCliente.setEnabled(false);
	
		addPanelButton(true, buttonNovo, buttonSalvar, buttonEditar, buttonExcluir);
		
		beanToForm(false);
	}
	
	public void novo() {
		ClienteCadMia clienteCadMia = bean.getCliente();
		bean = new Financeiro();
		bean.setDataCadastro(new Date());
		bean.setCliente(clienteCadMia);
		beanToForm(true);
		fieldReferencia.grabFocus();
	}

	protected void editar() {
		beanToForm(true);
		if (!JanelaLogin.getInstance().getUsuarioLogado().isAdministrador()) {
			fieldDataCadastro.setEditable(false);
			fieldReferencia.setEditable(false);
			fieldValor.setEditable(false);
		}
	}

	protected void salvar() {
		try {
			ClienteCadMia clienteCadMia = bean.getCliente();
			bean.setDataCadastro(fieldDataCadastro.getDate());
			bean.setReferencia(fieldReferencia.getText());
			bean.setValor(MkUtil.toBigDecimal(fieldValor.getText()));
			bean.setDataPagamento(fieldDataPagamento.getDate());
			bean.setObservacao(textObservacao.getText());
			
			if (bean.getId() == null) {
				bean.setOwner(JanelaLogin.getInstance().getUsuarioLogado().getId());
				financeiroService.insert(bean);
			} else {
				financeiroService.update(bean);
			}
			MkDialog.info("Financeiro salvo com sucesso", buttonSalvar);

			bean = financeiroService.findById(bean.getId());
			bean.setCliente(clienteCadMia);
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
					financeiroService.delete(bean.getId());
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
		fieldCliente.setText(bean.getCliente().getNome());
		fieldDataCadastro.setDate(bean.getDataCadastro());
		fieldReferencia.setText(bean.getReferencia());
		fieldValor.setValue(bean.getValor());
		fieldDataPagamento.setDate(bean.getDataPagamento());
		textObservacao.setText(bean.getObservacao());
		
		fieldDataCadastro.setEditable(isEditMode);
		fieldReferencia.setEditable(isEditMode);
		fieldValor.setEditable(isEditMode);
		fieldDataPagamento.setEditable(isEditMode);
		textObservacao.setEditable(isEditMode);
		
		buttonNovo.setEnabled(!isEditMode);
		buttonSalvar.setEnabled(isEditMode);
		buttonEditar.setEnabled(!isEditMode);
		buttonExcluir.setEnabled(!isEditMode);
		
	}

}
