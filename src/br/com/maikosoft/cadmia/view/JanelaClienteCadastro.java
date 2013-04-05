package br.com.maikosoft.cadmia.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

import br.com.maikosoft.cadmia.Cliente;
import br.com.maikosoft.cadmia.ClienteModalidade;
import br.com.maikosoft.cadmia.EnumUF;
import br.com.maikosoft.cadmia.Modalidade;
import br.com.maikosoft.cadmia.service.ClienteModalidadeService;
import br.com.maikosoft.cadmia.service.ClienteService;
import br.com.maikosoft.cadmia.service.FinanceiroService;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.core.MkTransferObject;
import br.com.maikosoft.core.MkUtil;
import br.com.maikosoft.layout.swing.EnumMkButton;
import br.com.maikosoft.layout.swing.MkButton.MkButtonAdicionar;
import br.com.maikosoft.layout.swing.MkButton.MkButtonAtualizar;
import br.com.maikosoft.layout.swing.MkButton.MkButtonEditar;
import br.com.maikosoft.layout.swing.MkButton.MkButtonExcluir;
import br.com.maikosoft.layout.swing.MkButton.MkButtonNovo;
import br.com.maikosoft.layout.swing.MkButton.MkButtonPesquisar;
import br.com.maikosoft.layout.swing.MkButton.MkButtonRemover;
import br.com.maikosoft.layout.swing.MkButton.MkButtonSalvar;
import br.com.maikosoft.layout.swing.MkComboBox;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkFieldDate;
import br.com.maikosoft.layout.swing.MkFieldMask;
import br.com.maikosoft.layout.swing.MkFieldMask.EnumMkMask;
import br.com.maikosoft.layout.swing.MkFieldText;
import br.com.maikosoft.layout.swing.MkPanelTable;
import br.com.maikosoft.layout.swing.MkTextArea;
import br.com.maikosoft.layout.swing.MkWindow;

@SuppressWarnings("serial")
public class JanelaClienteCadastro extends MkWindow {
	
	private MkFieldText fieldId;
	private MkFieldDate fieldDataNascimento;
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
	private MkFieldText fieldCodigoBarra;
	private MkTextArea textObservacao;
	
	private JList listModalidade;
	private MkButtonAdicionar buttonAdicionar;
	private MkButtonRemover buttonRemover;
	
	private MkFieldMask fieldValorMensalidade;
	private MkComboBox<String> comboDiaPagamentoMensalidade;
	private JLabel labelSaldoFinanceiro;
	private MkButtonPesquisar buttonConsultaFinanceiro;
	
	private JLabel labelFoto;
	
	private MkButtonNovo buttonNovo;
	private MkButtonSalvar buttonSalvar;
	private MkButtonEditar buttonEditar;
	private MkButtonExcluir buttonExcluir;
		
	private Cliente bean;
	private ClienteService clienteService;
	private ClienteModalidadeService clienteModalidadeService;
	private FinanceiroService financeiroService;
	
	public JanelaClienteCadastro(Cliente bean) {
		this.bean = bean;
	}

	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Nome:",fieldNome, MkPanelTable.getDefaultCell(3), "Código:", fieldId);		
		panelTable.addRow("CPF:", fieldCpf, "RG:", fieldRg, "Data Nacimento:", fieldDataNascimento, GridBagConstraints.NONE);
		panelTable.addRow("Endereço:", fieldEndereco, MkPanelTable.getDefaultCell(3), "Numero:", fieldNumero);
		panelTable.addRow("Bairro:", fieldBairro, "CEP:", fieldCep, "Cidade:", new MkPanelTable().addRow(fieldCidade, comboUf));
		panelTable.addRow("Telefone 1:", fieldTelefone1, "Telefone 2:", fieldTelefone2, "Telefone 3:", fieldTelefone3);
		panelTable.addRow("e-mail", fieldEmail, MkPanelTable.getDefaultCell(3), "Codigo de Barra", fieldCodigoBarra);
		
		MkPanelTable panelTableModalidadeMensalidade = new MkPanelTable();
		
		MkPanelTable panelTableMensalidade = new MkPanelTable();
		panelTableMensalidade.setTitle("Mensalidade");
		panelTableMensalidade.addRow("Dia Pagamento:", comboDiaPagamentoMensalidade, "Valor:", fieldValorMensalidade);
		panelTableMensalidade.addRow(buttonConsultaFinanceiro, MkPanelTable.getDefaultCell(2), "Saldo: ", labelSaldoFinanceiro);
		panelTable.addRow(panelTableMensalidade);
		buttonConsultaFinanceiro.setIcon(EnumMkButton.getIcon("dinheiro"));
		buttonConsultaFinanceiro.setText("Consultar Financeiro");
		
		panelTableModalidadeMensalidade.addRow(panelTableMensalidade);
		
		MkPanelTable panelTableModalidade = new MkPanelTable();
		panelTableModalidade.setTitle("Modalidades");
		panelTableModalidade.addRow(buttonAdicionar, buttonRemover);
		panelTableModalidade.addRow(listModalidade);
		
		panelTableModalidadeMensalidade.addRow(panelTableModalidade, GridBagConstraints.BOTH);
		
		MkPanelTable panelTableFoto = new MkPanelTable();
		panelTableFoto.setTitle("Foto");		
		
		labelFoto = new JLabel();
		labelFoto.setHorizontalAlignment(SwingConstants.CENTER);
		labelFoto.setPreferredSize(new Dimension(320,240));
		labelFoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (buttonSalvar.isEnabled()) {
						editarFoto();
					}
				}
			}
		});
		
		panelTableFoto.addRow(labelFoto, GridBagConstraints.BOTH);
		
		panelTable.addRow(panelTableFoto, MkPanelTable.getDefaultCell(3) , panelTableModalidadeMensalidade, MkPanelTable.getDefaultCell(3));
		
		panelTable.addRow(textObservacao.getJScrollPane("Observação"), GridBagConstraints.BOTH);
		
		addPanelCenter(panelTable, 800, 550);
		
		comboUf.setList(Arrays.asList(EnumUF.values()));
		fieldCpf.setMask(EnumMkMask.CPF);
		fieldCep.setMask(EnumMkMask.CEP);
		fieldId.setEnabled(false);
		fieldValorMensalidade.setMask(EnumMkMask.CURRENCY);
		fieldValorMensalidade.setEditable(false);
		fieldValorMensalidade.setFocusable(false);
		
		comboDiaPagamentoMensalidade.setList(Arrays.asList("01","05", "10", "15", "20", "25"));
	
		addPanelButton(true, buttonNovo, buttonSalvar, buttonEditar, buttonExcluir);
		
		if (bean.getId() == null) {
			novo();
		} else {
			try {
				clienteModalidadeService.carregarModalidades(bean);
			} catch (MkServiceException exception) {
				MkDialog.error("Erro carregando Lista de Modalidade", exception);
			}
			beanToForm(false);
		}
	}
	
	protected void novo() {
		bean = new Cliente();
		beanToForm(true);
		fieldCidade.setText("Londrina");
		comboUf.setSelected(EnumUF.PR);
	}

	protected void editar() {
		beanToForm(true);
	}

	protected void salvar() {
		try {
			bean.setNome(fieldNome.getText());
			bean.setDataNascimento(fieldDataNascimento.getDate());
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
			bean.setValorMensalidade(MkUtil.toBigDecimal(fieldValorMensalidade.getText()));
			bean.setDiaPagamento(comboDiaPagamentoMensalidade.getSelected());
			bean.setCodigoBarra(fieldCodigoBarra.getText());

			if (bean.getId() == null) {
				bean.setOwner(JanelaLogin.getInstance().getUsuarioLogado().getId());
				clienteService.insert(bean);
			} else {
				clienteService.update(bean);
			}
			MkDialog.info("Cliente salvo com sucesso");
			
			clienteModalidadeService.update(bean.getListModalidade());

			bean = clienteService.findById(bean.getId());
			clienteModalidadeService.carregarModalidades(bean);
			
			beanToForm(false);
			
			application.refreshWindows();

		} catch (Exception ex) {
			MkDialog.error(ex.getMessage(), ex);
		}
	}

	protected void excluir() {
		if (MkDialog.confirm("Deseja excluir esse registro?")) {
			try {
				clienteService.delete(bean.getId());
				MkDialog.info("Cliente excluido com sucesso");
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
		fieldDataNascimento.setText(MkUtil.toString(bean.getDataNascimento()));
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
		fieldCodigoBarra.setText(bean.getCodigoBarra());
		carregarFoto(bean.getFoto());
		
		fieldNome.setEditable(isEditMode);
		fieldDataNascimento.setEditable(isEditMode);
		fieldCpf.setEditable(isEditMode);
		fieldRg.setEditable(isEditMode);
		fieldEndereco.setEditable(isEditMode);
		fieldNumero.setEditable(isEditMode);
		fieldBairro.setEditable(isEditMode);
		fieldCep.setEditable(isEditMode);
		fieldCidade.setEditable(isEditMode);
		comboUf.setEnabled(isEditMode);
		fieldTelefone1.setEditable(isEditMode);
		fieldTelefone2.setEditable(isEditMode);
		fieldTelefone3.setEditable(isEditMode);
		fieldEmail.setEditable(isEditMode);
		textObservacao.setEditable(isEditMode);
		fieldCodigoBarra.setEditable(isEditMode);
		
//		fieldValorMensalidade.setValue(bean.getValorMensalidade());
//		fieldValorMensalidade.setEditable(isEditMode);
		comboDiaPagamentoMensalidade.setSelected(bean.getDiaPagamento());
		comboDiaPagamentoMensalidade.setEnabled(isEditMode);
		
		atualizaListaModalidade();
		atualizar();
		
		buttonConsultaFinanceiro.setEnabled(!isEditMode);
		
		buttonNovo.setEnabled(!isEditMode);
		buttonSalvar.setEnabled(isEditMode);
		buttonEditar.setEnabled(!isEditMode);
		buttonExcluir.setEnabled(!isEditMode);
		buttonAdicionar.setEnabled(isEditMode);
		buttonRemover.setEnabled(isEditMode);
						
	}
	
	private void atualizaListaModalidade() {
		DefaultListModel listModelModalidade = new DefaultListModel();
		BigDecimal totalMensalidade = BigDecimal.ZERO;
		for (ClienteModalidade clienteModalidade : bean.getListModalidade()) {
			if (!clienteModalidade.isDelete()) {
				listModelModalidade.addElement(clienteModalidade.getModalidade());
				totalMensalidade = totalMensalidade.add(clienteModalidade.getModalidade().getValor());
			}
		}
		listModalidade.setModel(listModelModalidade);
		fieldValorMensalidade.setText(MkUtil.toString(totalMensalidade));
	}

	protected void adicionar() {
		MkTransferObject<Modalidade> transferObject = new MkTransferObject<Modalidade>() {
			@Override
			public void postTranfer(Modalidade object) {
				ClienteModalidade clienteModalidade = new ClienteModalidade();
				clienteModalidade.setModalidade(object);
				clienteModalidade.setCliente(bean);
				bean.getListModalidade().add(clienteModalidade);
				atualizaListaModalidade();
//				if ("0,00".equals(fieldValorMensalidade.getText())) {
//					fieldValorMensalidade.setText(MkUtil.toString(object.getValor()));
//				}
			}
		};
		JanelaModalidadeConsulta janelaModalidadeConsulta = new JanelaModalidadeConsulta();
		janelaModalidadeConsulta.setTranferir(transferObject);
		janelaModalidadeConsulta.showWindow("Transferir Modalidade", false);
	}
	
	protected void remover() {
		
		if (listModalidade.getSelectedIndex() == -1) {
			MkDialog.warm("Selecione uma modalidade");
		} else {
			Modalidade modalidade = (Modalidade) listModalidade.getModel().getElementAt(listModalidade.getSelectedIndex());
			for (ClienteModalidade clienteModalidade : bean.getListModalidade()) {
				if (clienteModalidade.getModalidade().equals(modalidade)) {
					clienteModalidade.setDelete(true);
				}
			}
			atualizaListaModalidade();
		}
		
	}
	
	protected void pesquisar() {
		JanelaFinanceiroConsulta janelaFinanceiroConsulta = new JanelaFinanceiroConsulta(bean);
		janelaFinanceiroConsulta.showWindow("Financeiro", false);
	}
	
	protected void atualizar() {
		try {
			BigDecimal saldo = financeiroService.getSaldo(bean);
			if (saldo.compareTo(BigDecimal.ZERO) < 0) {
				labelSaldoFinanceiro.setText("<html><b><font color=red>"+MkUtil.toString(saldo)+"</font></b></html>");
			} else {
				labelSaldoFinanceiro.setText("<html><b><font color=blue>EM DIA</font></b></html>");
			}
		} catch (MkServiceException ex) {
			MkDialog.error(ex.getMessage(), ex);
		}
	}
	
	private void editarFoto() {
		JanelaCamera janelaCamera = new JanelaCamera();
		janelaCamera.showWindow("Foto", true);
		byte[] foto = janelaCamera.getFoto();
		bean.setFoto(foto);
		carregarFoto(foto);
	}

	private void carregarFoto(byte[] foto) {
		if ((foto == null) || (foto.length == 0)) {
			labelFoto.setText("Dois cliques para editar foto");
			labelFoto.setIcon(null);
		} else {
			labelFoto.setIcon(new ImageIcon(foto));
			labelFoto.setText(null);
		}
	}

	@Override
	public void refreshWindow() {
		atualizar();
	}
	
	

}
