package br.com.maikosoft.cadmia.view;

import javax.swing.JCheckBox;
import javax.swing.JPasswordField;

import br.com.maikosoft.cadmia.Usuario;
import br.com.maikosoft.cadmia.service.UsuarioService;
import br.com.maikosoft.core.MkUtil;
import br.com.maikosoft.layout.swing.MkButton.MkButtonEditar;
import br.com.maikosoft.layout.swing.MkButton.MkButtonExcluir;
import br.com.maikosoft.layout.swing.MkButton.MkButtonNovo;
import br.com.maikosoft.layout.swing.MkButton.MkButtonSalvar;
import br.com.maikosoft.layout.swing.MkDialog;
import br.com.maikosoft.layout.swing.MkFieldText;
import br.com.maikosoft.layout.swing.MkPanelTable;
import br.com.maikosoft.layout.swing.MkWindow;

@SuppressWarnings("serial")
public class JanelaUsuarioCadastro extends MkWindow {
	
	private MkFieldText fieldId;
	private MkFieldText fieldNome;
	private JPasswordField fieldSenha;
	private JCheckBox checkBoxAtivo;
	
	private MkButtonNovo buttonNovo;
	private MkButtonSalvar buttonSalvar;
	private MkButtonEditar buttonEditar;
	private MkButtonExcluir buttonExcluir;
		
	private Usuario bean;
	private UsuarioService usuarioService;
	
	public JanelaUsuarioCadastro(Usuario bean) {
		this.bean = bean;
	}

	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		panelTable.addRow("Nome:",fieldNome);
		panelTable.addRow("Senha:", fieldSenha);
		panelTable.addRow("", checkBoxAtivo);
		
		addPanelCenter(panelTable, 350, 150);
		fieldId.setEnabled(false);
		checkBoxAtivo.setText("Ativo");
	
		addPanelButton(true, buttonNovo, buttonSalvar, buttonEditar, buttonExcluir);
		
		beanToForm(false);
	}
	
	public void novo() {
		bean = new Usuario();
		beanToForm(true);
	}

	protected void editar() {
		beanToForm(true);
	}

	protected void salvar() {
		try {
			bean.setNome(fieldNome.getText());
			if (fieldSenha.getPassword().length != 0 ) {
				bean.setSenha(MkUtil.getHash(new String(fieldSenha.getPassword()), "SHA-256"));
			}
			bean.setAtivo(checkBoxAtivo.isSelected());

			if (bean.getId() == null) {
				usuarioService.insert(bean);
			} else {
				usuarioService.update(bean);
			}
			MkDialog.info("Usuário salvo com sucesso");

			bean = usuarioService.findById(bean.getId());
			beanToForm(false);

		} catch (Exception ex) {
			MkDialog.error(ex.getMessage(), ex);
		}
	}

	protected void excluir() {
		if (MkDialog.confirm("Deseja excluir esse registro?")) {
			try {
				usuarioService.delete(bean.getId());
				MkDialog.info("Usuário excluido com sucesso");
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
		fieldNome.setEditable(isEditMode);
		
		checkBoxAtivo.setSelected(bean.getAtivo());
		checkBoxAtivo.setEnabled(isEditMode);
		
		fieldSenha.setEditable(isEditMode);
		
		buttonNovo.setEnabled(!isEditMode);
		buttonSalvar.setEnabled(isEditMode);
		buttonEditar.setEnabled(!isEditMode);
		buttonExcluir.setEnabled(!isEditMode);
		
	}

}
