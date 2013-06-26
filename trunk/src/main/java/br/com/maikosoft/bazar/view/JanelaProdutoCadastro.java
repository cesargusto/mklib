package br.com.maikosoft.bazar.view;

import java.awt.GridBagConstraints;

import br.com.maikosoft.bazar.Produto;
import br.com.maikosoft.bazar.service.ProdutoService;
import br.com.maikosoft.mklib.MkButton.MkButtonEditar;
import br.com.maikosoft.mklib.MkButton.MkButtonExcluir;
import br.com.maikosoft.mklib.MkButton.MkButtonNovo;
import br.com.maikosoft.mklib.MkButton.MkButtonSalvar;
import br.com.maikosoft.mklib.MkFieldMask.EnumMkMask;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldMask;
import br.com.maikosoft.mklib.MkFieldText;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkTextArea;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.util.MkUtil;
import br.com.maikosoft.view.JanelaLogin;

@SuppressWarnings("serial")
public class JanelaProdutoCadastro extends MkWindow {
	
	private MkFieldText fieldId;
	private MkFieldText fieldNome;
	private MkFieldText fieldCodigoBarra;
	private MkTextArea textObservacao;
	private MkFieldMask fieldValor;
	
	private MkButtonNovo buttonNovo;
	private MkButtonSalvar buttonSalvar;
	private MkButtonEditar buttonEditar;
	private MkButtonExcluir buttonExcluir;
		
	private Produto bean;
	private ProdutoService produtoService;
	
	public JanelaProdutoCadastro(Produto bean) {
		this.bean = (bean == null ? new Produto() : bean);
		
	}

	@Override
	protected void initWindow() {
		
//		fieldValor.setColumns(6);
		fieldValor.setMask(EnumMkMask.CURRENCY);
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Código:", fieldId);
		panelTable.addRow("Nome:",fieldNome);
		panelTable.addRow("Valor", fieldValor, "Codigo de Barra", fieldCodigoBarra);
							
		panelTable.addRow(textObservacao.getJScrollPane("Observação"), GridBagConstraints.BOTH);
		
		addPanelCenter(panelTable, 500, 250);
		
		fieldId.setEnabled(false);
	
		addPanelButton(true, buttonNovo, buttonSalvar, buttonEditar, buttonExcluir);
		
		if (bean.getId() == null) {
			novo();
		} else {
			beanToForm(false);
		}
	}

	protected void novo() {
		bean = new Produto();
		beanToForm(true);
	}

	protected void editar() {
		beanToForm(true);
	}

	protected void salvar() {
		try {
			bean.setNome(fieldNome.getText());
			bean.setCodigoBarra(fieldCodigoBarra.getText());
			bean.setObservacao(textObservacao.getText());
			bean.setValor(MkUtil.toBigDecimal(fieldValor.getText()));

			if (bean.getId() == null) {
				bean.setOwner(JanelaLogin.getInstance().getUsuarioLogado().getId());
				produtoService.insert(bean);
			} else {
				produtoService.update(bean);
			}
			MkDialog.info("Produto salvo com sucesso", buttonSalvar);
			
			bean = produtoService.findById(bean.getId());
			
			beanToForm(false);
			
			application.refreshWindows();

		} catch (Exception ex) {
			MkDialog.error(ex.getMessage(), ex);
		}
	}

	protected void excluir() {
		if (MkDialog.confirm("Deseja excluir esse registro?")) {
			try {
				produtoService.delete(bean.getId());
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
		textObservacao.setText(bean.getObservacao());
		fieldCodigoBarra.setText(bean.getCodigoBarra());
		fieldValor.setValue(bean.getValor());
		
		fieldNome.setEditable(isEditMode);
		fieldCodigoBarra.setEditable(isEditMode);
		textObservacao.setEditable(isEditMode);
		fieldValor.setEditable(isEditMode);
				
		buttonNovo.setEnabled(!isEditMode);
		buttonSalvar.setEnabled(isEditMode);
		buttonEditar.setEnabled(!isEditMode);
		buttonExcluir.setEnabled(!isEditMode);
						
	}
	

}
