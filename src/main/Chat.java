package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class Chat extends JFrame {
	
	private JPanel panel;
	private JTextArea areaDeTexto;
	private JButton boton;
	private JTextField cajaDeTexto;
	
	public Chat() {
		setSize(800, 600);
		setTitle("ChatBot");
		setLocationRelativeTo(null);  // Establecemos la ventana en el centro de la pantalla
		iniciarComponentes();
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Apagamos el programa cuando cerramos la ventana
	}
	
	private void iniciarComponentes() {
		agregarPanel();
		//agregarEtiquetaUsuario();
		agregarAreaDeTexto();
		agregarBoton();
		agregarCajaDeTexto();
	}
	
	private void agregarPanel() {
		panel = new JPanel();
		panel.setLayout(null); // Desactivamos el diseño por defecto
		getContentPane().add(panel); // Agregamos el panel a la ventana
	}
	
	private void agregarAreaDeTexto() {
		areaDeTexto = new JTextArea();
		areaDeTexto.setBounds(10, 10, this.getWidth() - 35, this.getHeight() - 100);
		areaDeTexto.setEditable(false);
		panel.add(areaDeTexto);
		
		JScrollPane scroll = new JScrollPane(areaDeTexto, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(10, 10, this.getWidth() - 35, this.getHeight() - 100);
		panel.add(scroll);
		}
	
	private void agregarBoton() {
		boton = new JButton("Enviar");
		boton.setBounds(700, 525, 80, 30);
		panel.add(boton);
		eventoOyenteDeMouse();
	}
	
	private void eventoOyenteDeMouse() {
		MouseListener mouseListener = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				agregarMensaje();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		};
		
		boton.addMouseListener(mouseListener);
	}
	
	private void agregarCajaDeTexto() {
		cajaDeTexto = new JTextField();
		cajaDeTexto.setBounds(10, 525, 675, 30);
		panel.add(cajaDeTexto);
		
		eventosDelTeclado();
	}
	
	private void eventosDelTeclado() {
		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == '\n') {
					agregarMensaje();
				}
			}
		};
		cajaDeTexto.addKeyListener(keyListener);
	}
	
	private void agregarMensaje() {
		areaDeTexto.append("Tú: " + cajaDeTexto.getText() + "\n");
		cajaDeTexto.setText("");
	}

	


	

}
