package br.com.maikosoft.layout.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 *
 * @author michael
 */
@SuppressWarnings("serial")
public class MkButton extends JButton {

	private MkRun adapter;
	private EnumMkButton tipo;
	
    public MkButton() {
		super();
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				adapter.execute();
			}
		});
    }
    
    public MkButton(EnumMkButton tipo) {
		super(tipo.title, tipo.getIcon());
		this.tipo =  tipo;
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				adapter.execute();
			}
		});
	}

	public void onClick(MkRun adapter) {
    	this.adapter = adapter;
    }
        
    @Override
	public void setText(String text) {
    	super.setText(text);
    	int mnemonic = text.indexOf("&");
        if (mnemonic > -1) {
           setText(text.replace("&",""));
           setMnemonic(text.charAt(mnemonic+1));
        }
	}
    
    public EnumMkButton getTipo() {
		return tipo;
	}

	public class MkButtonAbrir extends MkButton {
    	public MkButtonAbrir() {
    		super(EnumMkButton.ABRIR);
    	}
	}
    
    public class MkButtonAdicionar extends MkButton {
    	public MkButtonAdicionar() {
    		super(EnumMkButton.ADICIONAR);
    	}
	}

    public class MkButtonArquivo extends MkButton {
    	public MkButtonArquivo() {
    		super(EnumMkButton.ARQUIVO);
    	}
	}
    
    public class MkButtonCancelar extends MkButton {    	
    	public MkButtonCancelar() {
    		super(EnumMkButton.CANCELAR);
    	}
	}
    
    public class MkButtonConfirmar extends MkButton {    	
    	public MkButtonConfirmar() {
    		super(EnumMkButton.CONFIRMAR);
    	}
	}
    
    public class MkButtonEditar extends MkButton {    	
    	public MkButtonEditar() {
    		super(EnumMkButton.EDITAR);
    	}
    }
    
    public class MkButtonExcluir extends MkButton {    	
		public MkButtonExcluir() {
			super(EnumMkButton.EXCLUIR);
		}
	}
    
    public class MkButtonFechar extends MkButton {    	
    	public MkButtonFechar() {
    		super(EnumMkButton.FECHAR);
    	}
    }
    
    public class MkButtonImprimir extends MkButton {    	
    	public MkButtonImprimir() {
    		super(EnumMkButton.IMPRIMIR);
    	}
    }
    
    public class MkButtonLimpar extends MkButton {    	
    	public MkButtonLimpar() {
    		super(EnumMkButton.LIMPAR);
    	}
    }
    
    public class MkButtonNovo extends MkButton {    	
    	public MkButtonNovo() {
    		super(EnumMkButton.NOVO);
    	}
	}

    public class MkButtonPesquisar extends MkButton {    	
    	public MkButtonPesquisar() {
    		super(EnumMkButton.PESQUISAR);
    	}
    }
    
    public class MkButtonRemover extends MkButton {
    	public MkButtonRemover() {
    		super(EnumMkButton.REMOVER);
    	}
	}
	
	public class MkButtonSalvar extends MkButton {
		public MkButtonSalvar() {
			super(EnumMkButton.SALVAR);
		}
	}
		
	public class MkButtonTransferir extends MkButton {    	
		public MkButtonTransferir() {
			super(EnumMkButton.TRANSFERIR);
		}
	}
	
}
