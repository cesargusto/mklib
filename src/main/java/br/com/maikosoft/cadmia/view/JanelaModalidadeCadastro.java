package br.com.maikosoft.cadmia.view;

import java.awt.GridBagConstraints;

import br.com.maikosoft.cadmia.Modalidade;
import br.com.maikosoft.cadmia.service.ModalidadeService;
import br.com.maikosoft.mklib.MkDialog;
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
public class JanelaModalidadeCadastro extends MkWindow {
	
	private MkFieldText fieldId;
	private MkFieldText fieldNome;
	private MkFieldMask fieldValor;
	private MkTextArea textObservacao;
	
	private MkButtonNovo buttonNovo;
	private MkButtonSalvar buttonSalvar;
	private MkButtonEditar buttonEditar;
	private MkButtonExcluir buttonExcluir;
		
	private Modalidade bean;
	private ModalidadeService modalidadeService;
	
	public JanelaModalidadeCadastro(Modalidade bean) {
		this.bean = bean;
	}

	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Nome:",fieldNome);
		panelTable.addRow("Valor:", fieldValor, GridBagConstraints.NONE);
		panelTable.addRow(textObservacao.getJScrollPane("Observação"), GridBagConstraints.BOTH);
		
		addPanelCenter(panelTable, 500, 250);
		fieldValor.setColumns(10);
		fieldValor.setMask(EnumMkMask.CURRENCY);
		fieldId.setEnabled(false);
	
		addPanelButton(true, buttonNovo, buttonSalvar, buttonEditar, buttonExcluir);
		
		beanToForm(false);
	}
	
	public void novo() {
		bean = new Modalidade();
		beanToForm(true);
	}

	protected void editar() {
		beanToForm(true);
		if (!JanelaLogin.getInstance().getUsuarioLogado().isAdministrador()) {
			fieldValor.setEditable(false);
		}
	}

	protected void salvar() {
		try {
			bean.setNome(fieldNome.getText());
			bean.setValor(MkUtil.toBigDecimal(fieldValor.getText()));
			bean.setObservacao(textObservacao.getText());

			if (bean.getId() == null) {
				modalidadeService.insert(bean);
			} else {
				modalidadeService.update(bean);
			}
			MkDialog.info("Modalidade salvo com sucesso", buttonSalvar);

			bean = modalidadeService.findById(bean.getId());
			beanToForm(false);
			
			application.refreshWindows();

		} catch (Exception ex) {
			MkDialog.error(ex.getMessage(), ex);
		}
	}

	protected void excluir() {
		if (MkDialog.confirm("Deseja excluir esse registro?")) {
			try {
				modalidadeService.delete(bean.getId());
				closeWindow();
				application.refreshWindows();
			} catch (Exception ex) {
				MkDialog.error(ex.getMessage(), ex);
			}
		}
	}
	
	
	private void beanToForm(boolean isEditMode) {
		if (bean.getId() == null) {
			fieldId.setText("NOVO");
		} else {
			fieldId.setText(bean.getId()+"");
		}
		fieldNome.setText(bean.getNome());
		fieldValor.setValue(bean.getValor());
		textObservacao.setText(bean.getObservacao());
		
		fieldNome.setEditable(isEditMode);
		fieldValor.setEditable(isEditMode);
		textObservacao.setEditable(isEditMode);
		
		buttonNovo.setEnabled(!isEditMode);
		buttonSalvar.setEnabled(isEditMode);
		buttonEditar.setEnabled(!isEditMode);
		buttonExcluir.setEnabled(!isEditMode);
		
	}

}
