package br.com.maikosoft.bazar.view;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;
import br.com.maikosoft.bazar.Pedido;
import br.com.maikosoft.bazar.service.PedidoService;
import br.com.maikosoft.core.MkException;
import br.com.maikosoft.core.MkServiceException;
import br.com.maikosoft.mklib.MkButton.MkButtonImprimir;
import br.com.maikosoft.mklib.MkDialog;
import br.com.maikosoft.mklib.MkFieldDate;
import br.com.maikosoft.mklib.MkPanelTable;
import br.com.maikosoft.mklib.MkWindow;
import br.com.maikosoft.util.MkUtil;
import br.com.maikosoft.view.JanelaPrintPreview;

@SuppressWarnings("serial")
public class JanelaRelatorioPedido extends MkWindow {
	
//	private static final Logger logger = Logger.getLogger(JanelaLancaMensalidades.class);
	
	private MkFieldDate fieldDataInicial;
	private MkFieldDate fieldDataFinal;
	private MkButtonImprimir buttonImprimir;	
	
	private PedidoService pedidoService;
	
	@Override
	protected void initWindow() {
		
		MkPanelTable panelTable = new MkPanelTable();
		
		panelTable.addRow("Data Inicial:", fieldDataInicial);
		panelTable.addRow("Data Final:", fieldDataFinal);
		
		addPanelCenter(panelTable, 420, 180);
		
		addPanelButton(true, buttonImprimir);
				
	}
	
	protected void imprimir() {
			
		try {
			if (fieldDataInicial.getDate() == null) {
				MkDialog.warm("Informe a Data Inicial");
				fieldDataInicial.grabFocus();
			} else if (fieldDataFinal.getDate() == null) {
				MkDialog.warm("Informe a Data Final");
				fieldDataFinal.grabFocus();
			} else {
					this.waitCursor(true);
					
					Map<String, Object> where = new HashMap<String, Object>();
					where.put("before_data_pedido", MkUtil.setUltimaHora(fieldDataFinal.getDate()));
					where.put("after_data_pedido", fieldDataInicial.getDate());
					
					JRDataSourcePedido dataSourcePedido = new JRDataSourcePedido(pedidoService.findAll(where));
										
					HashMap<String, Object> parametro = new HashMap<String, Object>();
					parametro.put("DATA_INICIAL", fieldDataInicial.getDate());
					parametro.put("DATA_FINAL", fieldDataFinal.getDate());
					InputStream streamResource = JanelaRelatorioPedido.class.getClassLoader().getResourceAsStream("report/bazar/bazar_vendas.jasper");
					JasperPrint print = JasperFillManager.fillReport(streamResource, parametro, dataSourcePedido);
					JanelaPrintPreview.showView(print, true);
					
			}
		} catch (MkServiceException exception) {
			MkDialog.error("Erro ao buscar pedidos", exception);
		} catch (MkException ex) {
			MkDialog.error("Data informada é invalida", ex);
		} catch (JRException ex) {
			MkDialog.error("Erro ao gerar relatório", ex);
		} finally {
			this.waitCursor(false);
		}
			
	}
	
	private class JRDataSourcePedido extends JRAbstractBeanDataSource {
		

		private Iterator<Pedido> iterator = null;
		private Pedido currentBean = null;
		
		public JRDataSourcePedido(List<Pedido> list) {
			super(true);
			iterator = list.iterator();
		}


		public boolean next()
		{
			boolean hasNext = false;
			
			if (this.iterator != null) {
				hasNext = this.iterator.hasNext();				
				if (hasNext) {
					this.currentBean = this.iterator.next();
				}
			}			
			return hasNext;
		}
		
		
		public Object getFieldValue(JRField field) throws JRException {
			if ("saldo".equalsIgnoreCase(field.getName())) {
				return currentBean.getSaldo();
			}
			if ("nome".equalsIgnoreCase(field.getName())) {
				return currentBean.getCliente().getNome();
			}
			if ("cpf".equalsIgnoreCase(field.getName())) {
				return currentBean.getCliente().getCpf();
			}
			if ("rg".equalsIgnoreCase(field.getName())) {
				return currentBean.getCliente().getRg();
			}
			return getFieldValue(currentBean, field);
		}
		
		public void moveFirst()
		{
			
		}		
	}
}
