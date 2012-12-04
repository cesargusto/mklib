package br.com.maikosoft.layout.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;

import br.com.maikosoft.core.MkNotInstance;
import br.com.maikosoft.core.MkRun;
import br.com.maikosoft.core.MkService;



@SuppressWarnings("serial")
public abstract class MkWindow extends JPanel {
	
	private static final Logger logger = Logger.getLogger(MkWindow.class);
	
	protected javax.swing.JPanel panelButton;
    protected MkRun onCloseView;
    
    protected final MkApplication application = MkApplication.getInstance();
   
    public void setOnCloseView(MkRun onCloseView) {
        this.onCloseView = onCloseView;
    }
   
    public Object showWindow(String title, boolean isModal) {
    	logger.debug("executando showview");
    	initWindowDefault();
        initWindow();
        return application.showWindow(this, title, isModal);
    }
   
    protected abstract void initWindow();

	public void fecharJanela() {
		logger.debug("fechando janela");
		if (onCloseView == null) {
			logger.debug("dispose");
			application.disposeWindow(MkWindow.this);
		} else {
			logger.debug("oncloseview");
			onCloseView.execute();
		}
	}
   
    protected void addPanelButton(boolean isShowButtonFechar, MkButton ... listButton) {
    	
    	panelButton = new javax.swing.JPanel();
        panelButton.registerKeyboardAction(MkDialog.getFocusListener(false),
                KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        panelButton.registerKeyboardAction(MkDialog.getFocusListener(true),
                KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        panelButton.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        add(panelButton, java.awt.BorderLayout.SOUTH);
        
        if (isShowButtonFechar) {
        	MkButton mkButton = new MkButton(EnumMkButton.FECHAR);
			mkButton.onClick(new MkRun() {
				@Override
				public void execute() {
					fecharJanela();
				}
			});
        	panelButton.add(mkButton);
        }
    	
		for (int i = (listButton.length - 1); i >= 0; i--) {
			panelButton.add(listButton[i], 0);
		}
    }

    protected void addPanelCenter(Component component, int width, int height) {
        component.setPreferredSize(new Dimension(width, height));
        JScrollPane jScrollPane = new JScrollPane(component);
        jScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        add(jScrollPane, BorderLayout.CENTER);
    }


    private void initWindowDefault() {
    	logger.debug("executando initWindowDefaulta da classe"+this.getClass());
        setLayout(new java.awt.BorderLayout());
       
        Field[] fields = this.getClass().getDeclaredFields();
       
        if (!this.getClass().getSuperclass().equals(MkWindow.class)) {
        	logger.debug("Classe filha de uma mkWindow e seus fiels deverao tambem ser iniciados");
            Field[] fieldsHerdados = this.getClass().getSuperclass().getDeclaredFields();
            Field[] fieldsFinal = new Field[fields.length+fieldsHerdados.length];
            System.arraycopy(fields, 0, fieldsFinal, 0, fields.length);
            System.arraycopy(fieldsHerdados, 0, fieldsFinal, fields.length, fieldsHerdados.length);
            fields = fieldsFinal;
        }
       

        for (Field field : fields) {
            try {
                if (ClassUtils.isAssignable(field.getType(), Component.class, true)) {
                	if ((Modifier.isStatic(field.getModifiers()) || (field.isAnnotationPresent(MkNotInstance.class))))  {
                		continue;
                	}
                	logger.debug("instanciando field "+field.getType());
                    Object newInstance = null;
                    if (ClassUtils.isInnerClass(field.getType())) {
                        Object superClazz = field.getType().getSuperclass().newInstance();
                        Constructor<?> constructor =  field.getType().getConstructor(new Class[]{ superClazz.getClass()});
                        newInstance = constructor.newInstance(new Object[] {superClazz});

                    } else {
                        newInstance = field.getType().newInstance();
                    }
                    FieldUtils.writeField(field, this, newInstance, true);
                    if (ClassUtils.isAssignable(field.getType(), MkButton.class, true)) {
                    	MkButton button = (MkButton) newInstance;
                    	if (button.getTipo() != null) {
                    		final String methodName = button.getTipo().toString();
                    		final Object janela = MkWindow.this;
                            MkRun adapter = new MkRun() {
                                @Override
                                public void execute() {
                                	try {
                                		waitCursor(true);
                                		Method method = ReflectionUtils.findMethod(janela.getClass(), methodName);
                                		ReflectionUtils.makeAccessible(method);
                                		ReflectionUtils.invokeMethod(method, janela);
                                    } catch (Exception ex) {
                                        MkDialog.error("Erro ao "+methodName, ex);
                                    } finally {
                                    	waitCursor(false);
                                    }
                                }
                            };                           
                            button.onClick(adapter);
                    	}
                    }
                } else if (ClassUtils.isAssignable(field.getType(), MkService.class, true)) {
                	logger.debug("Injetando service: "+field.getType());
                	Object newInstance = application.getApplicationContext().getBean(field.getName());
                	FieldUtils.writeField(field, this, newInstance, true);
                }
            } catch (Exception ex) {
                throw new IllegalArgumentException("Erro inicializando componente:"+field.getName(), ex);
            }
        }
    }
       
    public void waitCursor(boolean isOn) {
    	if (isOn) {
    		SwingUtilities.getRootPane(this).getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    		SwingUtilities.getRootPane(this).getGlassPane().setVisible(true);
    	} else {
    		SwingUtilities.getRootPane(this).getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    		SwingUtilities.getRootPane(this).getGlassPane().setVisible(false);
    	}
    	
    }

}
