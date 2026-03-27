package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.net.URI;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;

public class Sobre extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblFix;
	private JButton btnDiego;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre frame = new Sobre();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Sobre() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 534, 294);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblFix = new JLabel("IFix");
		lblFix.setForeground(new Color(64, 0, 128));
		lblFix.setFont(new Font("Verdana", Font.PLAIN, 26));
		lblFix.setBounds(209, 11, 48, 49);
		contentPane.add(lblFix);
		
		JLabel lblTec = new JLabel("Tec");
		lblTec.setForeground(new Color(255, 128, 0));
		lblTec.setFont(new Font("Verdana", Font.PLAIN, 26));
		lblTec.setBounds(256, 11, 48, 49);
		contentPane.add(lblTec);
		
		btnDiego = new JButton("");
		btnDiego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				link("https://github.com/diegoog26");
			}
		});
		btnDiego.setIcon(new ImageIcon(Sobre.class.getResource("/img/github.png")));
		btnDiego.setBounds(10, 59, 48, 48);
		contentPane.add(btnDiego);
		
		JButton btnMauricio = new JButton("");
		btnMauricio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				link("https://github.com/Mauri902");
			}
		});
		btnMauricio.setIcon(new ImageIcon(Sobre.class.getResource("/img/github.png")));
		btnMauricio.setBounds(10, 118, 48, 48);
		contentPane.add(btnMauricio);
		
		JButton btnPaulo = new JButton("");
		btnPaulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				link("https://github.com/Paulo-Roberto753");
			}
		});
		btnPaulo.setIcon(new ImageIcon(Sobre.class.getResource("/img/github.png")));
		btnPaulo.setBounds(10, 177, 48, 48);
		contentPane.add(btnPaulo);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Sobre.class.getResource("/img/license.png")));
		lblNewLabel.setBounds(383, 70, 96, 96);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("@Diego de Oliveira");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(68, 71, 148, 27);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("@Mauricio Afonso");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1_1.setBounds(68, 129, 148, 27);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("@Paulo Roberto");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1_1_1.setBounds(68, 187, 148, 27);
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		btnOk.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		btnOk.setBounds(408, 202, 89, 23);
		contentPane.add(btnOk);

	}
	private void link(String url) {
		Desktop desktop = Desktop.getDesktop();
		try {
			URI uri = new URI(url);
			desktop.browse(uri);
		} catch (Exception e) {
			
		}
		
	}
}
