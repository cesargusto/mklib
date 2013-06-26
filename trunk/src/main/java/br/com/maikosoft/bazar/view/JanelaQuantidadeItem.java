package br.com.maikosoft.bazar.view;

import br.com.maikosoft.bazar.Produto;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.MkFieldMask;
import br.com.maikosoft.mklib.MkFieldMask.EnumMkMask;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.util.MkUtil;

@SuppressWarnings("serial")
public class JanelaQuantidadeItem extends MkWindow {
	
	private MkFieldMask fieldQuantidade;

	@Override
	protected void initWindow() {
		
		fieldQuantidade.setMask(EnumMkMask.NUMBER);
		addPanelCenter(fieldQuantidade, 180, 30);
		fieldQuantidade.setText("1");
		
		fieldQuantidade.onChange(new MkRun() {
			@Override
			public void execute() {
				closeWindow();
			}
		});
	}

	public Integer getQuantidade() {
		return MkUtil.toInteger(fieldQuantidade.getText(), 1);
	}
}
