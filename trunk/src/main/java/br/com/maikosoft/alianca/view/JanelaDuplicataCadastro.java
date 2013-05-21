package br.com.maikosoft.alianca.view;

import java.awt.GridBagConstraints;
import java.util.Date;

import javax.swing.JCheckBox;

import br.com.maikosoft.alianca.ClienteAlianca;
import br.com.maikosoft.alianca.Duplicata;
import br.com.maikosoft.alianca.service.DuplicataService;
import br.com.maikosoft.mklib.MkButton.MkButtonEditar;
import br.com.maikosoft.mklib.MkButton.MkButtonExcluir;
import br.com.maikosoft.mklib.MkButton.MkButtonNovo;
import br.com.maikosoft.mklib.MkButton.MkButtonSalvar;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldDate;
import br.com.maikosoft.mklib.MkFieldMask;
import br.com.maikosoft.mklib.MkFieldMask.EnumMkMask;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTextArea;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.util.MkUtil;
import br.com.maikosoft.view.JanelaLogin;

@SuppressWarnings("serial")
public class JanelaDuplicataCadastro extends MkWindow {
	
	private MkFieldText fieldId;
	private MkFieldText fieldCliente;
	private MkFieldText fieldReferencia;
	private MkFieldDate fieldDataCadastro;
	private MkFieldDate fieldDataPagamento;
	private MkFieldMask fieldValor;
	private MkTextArea textObservacao;
	
	private MkFieldDate fieldDataVencimento;
	private MkFieldMask fieldValorTotal;
	private MkFieldMask fieldNumeroNota;
	private MkFieldText fieldVendedor;
	private JCheckBox fieldPago;
	
	private MkButtonNovo buttonNovo;
	private MkButtonSalvar buttonSalvar;
	private MkButtonEditar buttonEditar;
	private MkButtonExcluir buttonExcluir;
		
	private Duplicata bean;
	private DuplicataService duplicataService;
	
	public JanelaDuplicataCadastro(Duplicata bean) {
		this.bean = bean;
	}

	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Codigo:",fieldId, "Data Cadastro:", fieldDataCadastro);
		panelTable.addRow("Cliente:",fieldCliente);
		panelTable.addRow("Número Nota:",fieldNumeroNota, "Referência:",fieldReferencia);
		panelTable.addRow("Valor:", fieldValor, "Data Vencimento:", fieldDataPagamento);
		panelTable.addRow("Valor Total:", fieldValorTotal, "Data Pagamento:", fieldDataVencimento);
		panelTable.addRow("Vendedor:", fieldVendedor, fieldPago);
		panelTable.addRow(textObservacao.getJScrollPane("Observação"), GridBagConstraints.BOTH);
		
		addPanelCenter(panelTable, 500, 350);
		fieldValor.setColumns(10);
		fieldValor.setMask(EnumMkMask.CURRENCY);
		fieldNumeroNota.setMask(EnumMkMask.NUMBER);
		fieldValorTotal.setMask(EnumMkMask.CURRENCY);
		fieldPago.setText("PAGO");
		fieldId.setEnabled(false);
		fieldCliente.setEnabled(false);
	
		addPanelButton(true, buttonNovo, buttonSalvar, buttonEditar, buttonExcluir);
		
		beanToForm(false);
	}
	
	public void novo() {
		ClienteAlianca clienteAlianca = bean.getClienteAlianca();
		bean = new Duplicata();
		bean.setDataCadastro(new Date());
		bean.setClienteAlianca(clienteAlianca);
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
			ClienteAlianca clienteAlianca = bean.getClienteAlianca();
			bean.setDataCadastro(fieldDataCadastro.getDate());
			bean.setReferencia(fieldReferencia.getText());
			bean.setValor(MkUtil.toBigDecimal(fieldValor.getText()));
			bean.setDataPagamento(fieldDataPagamento.getDate());
			bean.setObservacao(textObservacao.getText());
			
			if (bean.getId() == null) {
				bean.setOwner(JanelaLogin.getInstance().getUsuarioLogado().getId());
				duplicataService.insert(bean);
			} else {
				duplicataService.update(bean);
			}
			MkDialog.info("Duplicata salva com sucesso", buttonSalvar);

			bean = duplicataService.findById(bean.getId());
			bean.setClienteAlianca(clienteAlianca);
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
					duplicataService.delete(bean.getId());
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
		fieldCliente.setText(bean.getClienteAlianca().getNome());
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
		
		fieldDataVencimento.setDate(bean.getDataVencimento());
		fieldValorTotal.setValue(bean.getValorTotal());
		fieldNumeroNota.setValue(bean.getNumeroNota());
		fieldVendedor.setText(bean.getVendedor());
		fieldPago.setSelected(bean.isPago());
		
		buttonNovo.setEnabled(!isEditMode);
		buttonSalvar.setEnabled(isEditMode);
		buttonEditar.setEnabled(!isEditMode);
		buttonExcluir.setEnabled(!isEditMode);
		
	}

}
