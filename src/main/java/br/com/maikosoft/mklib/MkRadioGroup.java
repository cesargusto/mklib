package br.com.maikosoft.mklib;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import br.com.maikosoft.core.MkRun;

/**
*
* @author michael
*/
@SuppressWarnings("serial")
public class MkRadioGroup  extends JPanel {
	
	private List<JRadioButton> listRadio = null;
	private ButtonGroup group = null; 
	private MkRun adapter;
	
	public MkRadioGroup() {
		listRadio = new LinkedList<JRadioButton>();
		group = new ButtonGroup();
	}
	
	public void setItens(String[] titles, boolean isVertical) {
		for (String item : titles) {
			JRadioButton radioButton = new JRadioButton(item);
			radioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (adapter!=null) {
						adapter.execute();
					}
				}
			});
			group.add(radioButton);
			listRadio.add(radioButton);
			this.add(radioButton);
		}
		GridLayout gridLayout = (isVertical ? new GridLayout(listRadio.size(), 1) : new GridLayout(1, listRadio.size()));
		this.setLayout(gridLayout);
	}
	
	@Override
	public void setEnabled(boolean value) {
		super.setEnabled(value);
		for (JRadioButton radio : listRadio) {
			radio.setEnabled(value);
		}
	}
	
	public void onClick(MkRun adapter) {
    	this.adapter = adapter;
    }
	
	public void setTitle(String title) {
		if (title==null) {
			this.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		} else {
			this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		}
	}

	public String getSelected() {
		for (JRadioButton radio : listRadio) {
			if (radio.isSelected()) {
				return radio.getText();
			}
		}
		return null;
	}

	public void setSelected(String key) {
		group.clearSelection();
		if (listRadio!=null) {
			for (JRadioButton radio : listRadio) {
				if (radio.getText().equals(key)) {
					radio.setSelected(true);
					break;
				}
			}
		}
		if (adapter!=null) {
			adapter.execute();
		}
	}
}
