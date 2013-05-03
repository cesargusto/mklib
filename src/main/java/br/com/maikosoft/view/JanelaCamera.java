package br.com.maikosoft.view;

import java.awt.FlowLayout;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import br.com.maikosoft.core.MkNotInstance;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.mklib.EnumMkButton;
import br.com.maikosoft.mklib.MkButton;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.mklib.MkButton.MkButtonLimpar;
import br.com.maikosoft.mklib.MkButton.MkButtonTransferir;
import de.humatic.dsj.DSCapture;
import de.humatic.dsj.DSFilterInfo;
import de.humatic.dsj.DSFiltergraph;

@SuppressWarnings("serial")
public class JanelaCamera extends MkWindow {
	
	private JPanel panelImagem;

	private MkButton buttonCapturar;
	private MkButtonLimpar buttonLimpar;
	private MkButtonTransferir buttonTransferir;
	
	@MkNotInstance
	private DSCapture graph;

	@Override
	protected void initWindow() {
		
		panelImagem = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		addPanelCenter(panelImagem, 650, 480);
		
		buttonCapturar.setText("Capturar");
		buttonCapturar.setIcon(EnumMkButton.ADICIONAR.getIcon());
		buttonCapturar.onClick(capturar());
		buttonLimpar.setText("Repetir");
		
		addPanelButton(true, buttonCapturar, buttonLimpar, buttonTransferir);
		
		limpar();
	}

	protected MkRun capturar() {
		return new MkRun() {
			@Override
			public void execute() {
				graph.stop();
				buttonCapturar.setEnabled(false);
				buttonLimpar.setEnabled(true);
				buttonTransferir.setEnabled(true);
			}
		};
	}
	
	
	protected void limpar() {
		try {
			if (graph != null) {
				panelImagem.remove(graph);
				graph.dispose();
			} 
			
			DSFilterInfo[][] dsiVideo = DSCapture.queryDevices(DSCapture.SKIP_AUDIO);
			if (dsiVideo[0].length == 0) {
				MkDialog.warm("Nenhuma camera encontrada");
				closeWindow();
			}
			
			graph = new DSCapture(DSFiltergraph.RENDER_NATIVE, dsiVideo[0][0],
					false, DSFilterInfo.doNotRender(), null);
			// Largura e Altura da imagem
			graph.setSize(640, 480);
			panelImagem.add(graph.asComponent());
			graph.setPreview();
			
			repaint();
			
			buttonCapturar.setEnabled(true);
			buttonLimpar.setEnabled(false);
			buttonTransferir.setEnabled(false);
			
		} catch (Exception ex) {
			MkDialog.error("Erro carregando camera", ex);
		}
		
	}
	
	protected void transferir() {
		buttonLimpar.setEnabled(false);
		buttonTransferir.setEnabled(false);
		closeWindow();
	}
	

	public byte[] getFoto() {
		try {
			if (graph.getImage() != null) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(graph.getImage(), "jpg", baos );
				baos.flush();
				byte[] retorno = baos.toByteArray();
				baos.close();
				graph.dispose();
				return retorno;
			}
			
		} catch (Exception ex) {
			MkDialog.error("Erro ao transferir imagem", ex);
		}
		return null;
		
	}	
	

}
