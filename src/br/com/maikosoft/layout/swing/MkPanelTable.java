package br.com.maikosoft.layout.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class MkPanelTable extends JPanel {
	
	// uma ideia seria criar uma inteface MomoCell e cada componente que implementar vir com um gridcontraint default
	
	private static final Logger logger = Logger.getLogger(MkPanelTable.class);
	
	 private int linha;
	    
	    public MkPanelTable() {
	        this.setLayout(new GridBagLayout());
	    }
	    
	    public void setTitle(String title) {
	        this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
	    }    
	    
	    public MkPanelTable addRow(Object...itens) {
	    	List<Component> listComponente = new LinkedList<Component>();
	        List<GridBagConstraints> cells = new LinkedList<GridBagConstraints>();
	        
	        for (int i = 0; i < itens.length; i++) {
	        	
	            Object item = itens[i];
	            
	            if (item instanceof String) {
	                listComponente.add(new JLabel(item.toString()));
	                GridBagConstraints cellFinal = getDefaultCell(1);
	                cellFinal.anchor = GridBagConstraints.EAST;
	                cellFinal.fill = GridBagConstraints.NONE;
	                cellFinal.weightx = 0d;
	                cells.add(cellFinal);
	            } else if (item instanceof Component) {
	                listComponente.add((Component) item);
	                GridBagConstraints cellFinal = getDefaultCell((i+1) == itens.length ? GridBagConstraints.REMAINDER : 1);
	                cells.add(cellFinal);
	                
	            } else if (item instanceof GridBagConstraints) {
	                if (cells.isEmpty()) {
	                	logger.error("Posicao da formatacao da celula invalida");
	                }
	                cells.set(cells.size()-1, (GridBagConstraints) item);
	            } else if (item instanceof Integer) {
	                int test = (Integer) item;
	                GridBagConstraints cellFinal = (listComponente.size() == cells.size() ? cells.get(cells.size()-1) : getDefaultCell(1));
	                
	                switch (test) {
	                case GridBagConstraints.BOTH:
	                    cellFinal.fill = GridBagConstraints.BOTH;
	                    cellFinal.anchor = GridBagConstraints.CENTER;
	                    cellFinal.weightx = 0.5d;
	                    cellFinal.weighty = 0.5d;
	                    cellFinal.gridwidth = GridBagConstraints.REMAINDER;
	                    break;
	                case GridBagConstraints.NONE:
	                    cellFinal.fill = GridBagConstraints.NONE;
	                    cellFinal.weightx = 0d;
	                    break;
	                default:
	                    
	                }
	                
	                cells.set(cells.size()-1, cellFinal);
	            }
	        }
	        
	        int lastX = 0;
	        for (int j=0; j<listComponente.size(); j++) {
	            GridBagConstraints cellX = cells.get(j);
	            cellX.gridx = lastX;
	            cellX.gridy = linha;
	            if (cellX.gridwidth == 0) {
	            	lastX++;
	            } else {
	            	lastX += cellX.gridwidth;
	            }
	            
	            if (logger.isDebugEnabled()) {
	            	JPanel panelDebug = new JPanel(new BorderLayout());
	            	panelDebug.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		            panelDebug.add(listComponente.get(j));
		            this.add(panelDebug, cellX);
		            logger.debug("adicionando na linha:"+linha+" coluna:"+lastX+" compType: "+listComponente.get(j).getClass());
	            } else {
	            	this.add(listComponente.get(j), cellX);
	            }
	        }
	        
	        linha ++;
	        return this;
	    }
	    
	    public static GridBagConstraints getDefaultCell(int gridwidth) {
	        GridBagConstraints gridBagConstraints = new GridBagConstraints();
	         gridBagConstraints.insets = new java.awt.Insets(3, 2, 3, 2);
	         gridBagConstraints.anchor = GridBagConstraints.WEST;
	         gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
	         gridBagConstraints.weightx = 0.5d;
	         gridBagConstraints.gridwidth = gridwidth;
	         return gridBagConstraints;
	         
	    }
	
	
//	private int numeroColumna;
//	private int posicaoX = 0;
//	private int posicaoY = 0;
//	
//	
//	public MomoPanelTable(int columns) {
//        super(new GridBagLayout());
//        this.numeroColumna = columns;    
//    }
//    
//    public MomoPanelTable() {
//        this(2);    
//    }
//
//    public void setTitle(String title) {
//        this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
//    }
//
//    private static GridBagConstraints getDefaultCell() {
//           GridBagConstraints gridBagConstraints = new GridBagConstraints();
//            gridBagConstraints.insets = new java.awt.Insets(3, 2, 3, 2);
//            gridBagConstraints.anchor = GridBagConstraints.WEST;
//            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
//            gridBagConstraints.weightx = 0.5d;
//            return gridBagConstraints;
//    }
//
//    public MomoPanelTable newLine() {
//    	if ((posicaoY == 0) && (numeroColumna>posicaoX)) { // isso força respeitar o numero de coluna na tabela
//    		for (int i=posicaoX; i<numeroColumna; i++) {
//    			addItem(new MacLabel(" "));
//    		}
//    	}
//        posicaoY++;
//        posicaoX=0;
//        return this;
//    }
//
//
//    public MomoPanelTable addItem(MacComponent component) {
//       return addItem(component, getDefaultCell());
//    }
//
//    public MomoPanelTable addItem(MacComponent component, GridBagConstraints gridBagConstraints) {
//            if (gridBagConstraints.gridx > 0) {
//                posicaoX = gridBagConstraints.gridx;
//            }
//            if (gridBagConstraints.gridy > 0) {
//                posicaoY = gridBagConstraints.gridy;
//            }
//            
//            gridBagConstraints.gridx = posicaoX++;
//            gridBagConstraints.gridy = posicaoY;
//            
//            if (gridBagConstraints.gridheight > 1) {
//            	for (int i=1; i< gridBagConstraints.gridheight; i++) {
//            		//System.out.println("Tem que reservar x:"+ posicaoX+ " y:" +i);
//            	}
//            }
//            
//            if (gridBagConstraints.gridwidth > 1) {
//                posicaoX+= (gridBagConstraints.gridwidth-1);
//            }
//            
//            add((Component) component.getSwingComponent(), gridBagConstraints);
//
//            if (posicaoX >= numeroColumna) {
//            	newLine();
//            }
//            return this;
//    }
//
//    public MomoPanelTable addItem(String text) {
//        GridBagConstraints gridBagConstraints = getDefaultCell();
//        gridBagConstraints.anchor = GridBagConstraints.EAST;
//        gridBagConstraints.fill = GridBagConstraints.NONE;
//        gridBagConstraints.weightx = 0d;
//        return addItem(new MacLabel(text), gridBagConstraints);
//    }
//
//    public static GridBagConstraints getCell(EnumStyleCell...enumStyleCell) {
//        return getCell(1, 1, enumStyleCell);
//    }
//    public static GridBagConstraints getCell(int x, int y, EnumStyleCell...enumStyleCell) {
//        GridBagConstraints gridBagConstraints = getDefaultCell();        
//        gridBagConstraints.gridwidth = x;
//        gridBagConstraints.gridheight = y;
//        for (EnumStyleCell enumStyleCellItem : enumStyleCell) {
//            if (enumStyleCellItem.equals(EnumStyleCell.FILL_BOTH)) {
//                gridBagConstraints.fill = GridBagConstraints.BOTH;
//                gridBagConstraints.anchor = GridBagConstraints.CENTER;
//                gridBagConstraints.weightx = 0.5d;
//                gridBagConstraints.weighty = 0.5d;
//            }
//            if (enumStyleCellItem.equals(EnumStyleCell.FILL_NONE)) {
//                gridBagConstraints.fill = GridBagConstraints.NONE;
//                gridBagConstraints.weightx = 0d;
//            }
//            if (enumStyleCellItem.equals(EnumStyleCell.ALIGN_BUTTON)) {
//                gridBagConstraints.anchor = GridBagConstraints.SOUTH;
//                gridBagConstraints.weighty = 0.5;
//            }
//            if (enumStyleCellItem.equals(EnumStyleCell.ALIGN_TOP)) {
//                gridBagConstraints.anchor = GridBagConstraints.NORTH;
//                gridBagConstraints.weighty = 0.5;
//            }
//            if (enumStyleCellItem.equals(EnumStyleCell.ALIGN_CENTER)) {
//            	gridBagConstraints.fill = GridBagConstraints.NONE;
//                gridBagConstraints.anchor = GridBagConstraints.CENTER;
//            }
//            if (enumStyleCellItem.equals(EnumStyleCell.ALIGN_EAST)) {
//            	gridBagConstraints.fill = GridBagConstraints.NONE; // se nao permanece a esquerda
//                gridBagConstraints.anchor = GridBagConstraints.EAST;
//            }
//            if (enumStyleCellItem.equals(EnumStyleCell.FILL_CUSTON)) {
//            	gridBagConstraints.weightx = enumStyleCellItem.weightx;
//                gridBagConstraints.weighty = enumStyleCellItem.weighty;
//            }
//        }
//
//        return gridBagConstraints;
//    }
//
//    public Object getSwingComponent() {
//        return this;
//    }
//
//    public enum EnumStyleCell {
//        FILL_BOTH,
//        FILL_NONE,
//        ALIGN_TOP,
//        ALIGN_BUTTON, 
//        ALIGN_CENTER,
//        ALIGN_EAST,
//        FILL_CUSTON;        
//        
//        private double weightx = 0d;
//        private double weighty = 0d;        
//
//		public static EnumStyleCell FILL_CUSTON(double weightx, double weighty) {
//			EnumStyleCell custon = EnumStyleCell.FILL_CUSTON;
//			custon.weightx = weightx;
//			custon.weighty = weighty;
//			return custon;
//		}
//  
//    }
//    
//    /**
//     * @param Igual o metodo newRow porem pega o ultimo componente e estica
//     * @return Retorno o componente que inseriu a linha
//     */
//    public MomoPanelTable newRowFill(Object...component) {
//    	int count = component.length;
//    	Object[] list = new Object[count+1];
//    	for (int i=0; i<count; i++) {
//    		list[i] = component[i];
//    	}    	
//    	GridBagConstraints cell = getDefaultCell();
//    	if ((component[count-1] instanceof MacTextArea) || (component[count-1] instanceof MacTable)) {
//			cell = getCell(EnumStyleCell.FILL_BOTH);
//		} else if ((component[count-1] instanceof MomoFieldDate) || (component[count-1] instanceof MomoButton)) {
//			cell = getCell(EnumStyleCell.FILL_NONE);
//			
//		}
//    	cell.gridwidth = GridBagConstraints.REMAINDER;
//    	list[count] = cell;
//    	return newRow(list);    	
//    }
//    
//    /**
//     * @param Inseri o componente preenchendo toda a tabela para ambos os lados
//     * @return Retorno o componente que inseriu a linha
//     */
//    
//    public MomoPanelTable newRowFillBoth(MacComponent component) {
//    	GridBagConstraints cell = getCell(EnumStyleCell.FILL_BOTH);
//    	cell.gridwidth = GridBagConstraints.REMAINDER;
//    	return addItem(component, cell).newLine();
//    }
//    
//    
//    /**
//     * @param Lista com MacComponetes e String que viram maclabel e Integer que viram espaçamentos de colunas
//     * @return Retorno o componente que inseriu a linha
//     */
//    public MomoPanelTable newRow(Object...component) {
//		int count = component.length;
//		for (int i=0; i<count;i++) {
//			GridBagConstraints cell = getDefaultCell();
//			if (component[i] instanceof String) {
//				addItem(component[i]+"");
//				continue;
//			}			
//			
//			int next = i+1;
//			if (next != count) {
//				if (component[next] instanceof Integer) {
//					component[next] = getCell((Integer)component[next], 1);
//				}
//				if (component[next] instanceof GridBagConstraints) {
//					cell = (GridBagConstraints)component[next];
//					addItem((MacComponent)component[i], cell);
//					i++;
//					continue;
//				}
//			} 
//			if ((component[i] instanceof MacTextArea) || ((component[i] instanceof MacTable))) {
//				addItem((MacComponent) component[i], getCell(EnumStyleCell.FILL_BOTH));
//			} else if ((component[i] instanceof MomoFieldDate) || (component[i] instanceof MomoButton)) {
//				addItem((MacComponent) component[i], getCell(EnumStyleCell.FILL_NONE));
//			} else {
//				addItem((MacComponent) component[i], cell);
//			}
//			
//			
//			
//		}
//		newLine();
//		return this;
//	}
	
//    public void debug() {
//    	this.setOpaque(false);
//    }

}
