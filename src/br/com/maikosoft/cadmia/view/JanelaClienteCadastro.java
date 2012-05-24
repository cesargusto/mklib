package br.com.maikosoft.cadmia.view;

import java.awt.GridBagConstraints;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataListener;

import br.com.maikosoft.cadmia.Cliente;
import br.com.maikosoft.cadmia.ClienteModalidade;
import br.com.maikosoft.cadmia.EnumUF;
import br.com.maikosoft.cadmia.service.ClienteService;
import br.com.maikosoft.layout.swing.MkButton.MkButtonAdicionar;
import br.com.maikosoft.layout.swing.MkComboBox;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkFieldDate;
import br.com.maikosoft.layout.swing.MkFieldMask;
import br.com.maikosoft.layout.swing.MkFieldText;
import br.com.maikosoft.layout.swing.MkPanelTable;
import br.com.maikosoft.layout.swing.MkTextArea;
import br.com.maikosoft.layout.swing.MkUtil;
import br.com.maikosoft.layout.swing.MkWindow;
import br.com.maikosoft.layout.swing.MkButton.MkButtonEditar;
import br.com.maikosoft.layout.swing.MkButton.MkButtonExcluir;
import br.com.maikosoft.layout.swing.MkButton.MkButtonNovo;
import br.com.maikosoft.layout.swing.MkButton.MkButtonRemover;
import br.com.maikosoft.layout.swing.MkButton.MkButtonSalvar;
import br.com.maikosoft.layout.swing.MkFieldMask.EnumMask;

@SuppressWarnings("serial")
public class JanelaClienteCadastro extends MkWindow {
	
	private MkFieldText fieldId;
	private MkFieldDate fieldDate;
	private MkFieldText fieldNome;
	private MkFieldMask fieldCpf;
	private MkFieldText fieldRg;
	private MkFieldText fieldEndereco;
	private MkFieldText fieldNumero;
	private MkFieldText fieldBairro;
	private MkFieldMask fieldCep;
	private MkFieldText fieldCidade;
	private MkComboBox<EnumUF> comboUf;
	private MkFieldText fieldTelefone1;
	private MkFieldText fieldTelefone2;
	private MkFieldText fieldTelefone3;
	private MkFieldText fieldEmail;
	private MkTextArea textObservacao;
	
	private JList listModalidade;
	private MkButtonAdicionar buttonAdicionar;
	private MkButtonRemover buttonRemover;
	
	private JList listMensalidade;
	
	private MkButtonNovo buttonNovo;
	private MkButtonSalvar buttonSalvar;
	private MkButtonEditar buttonEditar;
	private MkButtonExcluir buttonExcluir;
		
	private Cliente bean;
	private ClienteService clienteService;
	
	public JanelaClienteCadastro(Cliente bean) {
		this.bean = bean;
	}

	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Nome:",fieldNome, MkPanelTable.getDefaultCell(3), "Código:", fieldId);		
		panelTable.addRow("Data Nacimento:", fieldDate, GridBagConstraints.NONE, "CPF:", fieldCpf, "RG:", fieldRg);
		panelTable.addRow("Endereço:", fieldEndereco, MkPanelTable.getDefaultCell(3), "Numero:", fieldNumero);
		panelTable.addRow("Bairro:", fieldBairro, "CEP:", fieldCep, "Cidade:", new MkPanelTable().addRow(fieldCidade, comboUf));
		panelTable.addRow("Telefone 1:", fieldTelefone1, "Telefone 2:", fieldTelefone2, "Telefone 3:", fieldTelefone3);
		panelTable.addRow("e-mail", fieldEmail);
		panelTable.addRow(textObservacao.getJScrollPane("Observação"), GridBagConstraints.BOTH);
		
		MkPanelTable panelTableModalidade = new MkPanelTable();
		panelTableModalidade.setTitle("Modalidades");
		panelTableModalidade.addRow(buttonAdicionar, buttonRemover);
		panelTableModalidade.addRow(new JScrollPane(listModalidade));
		
		MkPanelTable panelTableMensalidade = new MkPanelTable();
		panelTableMensalidade.setTitle("Ultimas Mensalidades");
		panelTableMensalidade.addRow(new JScrollPane(listMensalidade));
		
		panelTable.addRow(panelTableModalidade, MkPanelTable.getDefaultCell(3) , panelTableMensalidade, MkPanelTable.getDefaultCell(3));
		
		addPanelCenter(panelTable, 800, 550);
		
		comboUf.setList(Arrays.asList(EnumUF.values()));
		fieldCpf.setMask(EnumMask.CPF);
		fieldCep.setMask(EnumMask.CEP);
		fieldId.setEnabled(false);
	
		addPanelButton(true, buttonNovo, buttonSalvar, buttonEditar, buttonExcluir);
		
		if (bean.getId() == null) {
			novo();
		} else {
			beanToForm(false);
		}
	}
	
	protected void novo() {
		bean = new Cliente();
		beanToForm(true);
		fieldCidade.setText("Londrina");
	}

	protected void editar() {
		beanToForm(true);
	}

	protected void salvar() {
		try {
			bean.setNome(fieldNome.getText());
			bean.setDataNascimento(fieldDate.getDate());
			bean.setCpf(fieldCpf.getText());
			bean.setRg(fieldRg.getText());
			bean.setEndereco(fieldEndereco.getText());
			bean.setNumero(fieldNumero.getText());
			bean.setBairro(fieldBairro.getText());
			bean.setCep(fieldCep.getText());
			bean.setCidade(fieldCidade.getText());
			bean.setUf(comboUf.getSelected().name());
			bean.setTelefone1(fieldTelefone1.getText());
			bean.setTelefone2(fieldTelefone2.getText());
			bean.setTelefone3(fieldTelefone3.getText());
			bean.setEmail(fieldEmail.getText());
			bean.setObservacao(textObservacao.getText());

			if (bean.getId() == null) {
				clienteService.insert(bean);
			} else {
				clienteService.update(bean);
			}
			MkDialog.info("Cliente salvo com sucesso");

			bean = clienteService.findById(bean.getId());
			
			beanToForm(false);

		} catch (Exception ex) {
			MkDialog.error(ex.getMessage(), ex);
		}
	}

	protected void excluir() {
		if (MkDialog.confirm("Deseja excluir esse registro?")) {
			try {
				clienteService.delete(bean.getId());
				MkDialog.info("Cliente excluido com sucesso");
				fecharJanela();
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
		fieldDate.setText(MkUtil.toString(bean.getDataNascimento()));
		fieldCpf.setText(bean.getCpf());
		fieldRg.setText(bean.getRg());
		fieldEndereco.setText(bean.getEndereco());
		fieldNumero.setText(bean.getNumero());
		fieldBairro.setText(bean.getBairro());
		fieldCep.setValue(bean.getCep());
		fieldCidade.setText(bean.getCidade());
		if (bean.getUf() != null) {
			comboUf.setSelected(EnumUF.valueOf(bean.getUf()));
		}
		fieldTelefone1.setText(bean.getTelefone1());
		fieldTelefone2.setText(bean.getTelefone2());
		fieldTelefone3.setText(bean.getTelefone3());
		fieldEmail.setText(bean.getEmail());
		textObservacao.setText(bean.getObservacao());
		
		fieldNome.setEditable(isEditMode);
		fieldDate.setEditable(isEditMode);
		fieldCpf.setEditable(isEditMode);
		fieldRg.setEditable(isEditMode);
		fieldEndereco.setEditable(isEditMode);
		fieldNumero.setEditable(isEditMode);
		fieldBairro.setEditable(isEditMode);
		fieldCep.setEditable(isEditMode);
		fieldCidade.setEditable(isEditMode);
		comboUf.setEditable(isEditMode);
		fieldTelefone1.setEditable(isEditMode);
		fieldTelefone2.setEditable(isEditMode);
		fieldTelefone3.setEditable(isEditMode);
		fieldEmail.setEditable(isEditMode);
		textObservacao.setEditable(isEditMode);
		
		DefaultListModel listModelModalidade = new DefaultListModel();		
		for (ClienteModalidade clienteModalidade : bean.getListModalidade()) {
			listModelModalidade.addElement(clienteModalidade.getModalidade());
		}
		listModalidade.setModel(listModelModalidade);

		
		buttonNovo.setEnabled(!isEditMode);
		buttonSalvar.setEnabled(isEditMode);
		buttonEditar.setEnabled(!isEditMode);
		buttonExcluir.setEnabled(!isEditMode);
		
	}

}
