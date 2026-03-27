/*
 * Sistema de cadastro de cliente para a empresa IFixTec 
 * 
 * Aplicação desenvolvida eem Java com a integração a banco de dados
 * MySQL, com o objetivo de gerenciar informações de clientes e serviços técnicos.
 * 
 * @author Diego de Oliveira (desenvolvimento do código)
 * @author Paulo Roberto (Interface gráfica)
 * @author Maurício (Modelagem e banco de dados) 
 * @version 1.0
 * @since 2026
 * 
 * Projeto finalizado.
 * 
 */

package view;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.Icon;

import javax.swing.JFrame;
import javax.swing.JPanel;
import model.DAO;
import utils.Validador;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.cj.jdbc.Blob;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;
import java.awt.Desktop;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AssistenciaTecnica extends JFrame {
	DAO dao = new DAO();
	private Connection con;
	private boolean fotoCarregada = false;
	private FileInputStream fis;
	private int tamanho;
	private PreparedStatement pst;
	private boolean modoPesquisa = false;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblFoto;
	private JTextField textRE;
	private JTextField textTelefone;
	private JTextField textEquipamento;
	private JTextField textRelato;
	private JTextField textRelatorio;
	private JTextField textValor;
	private JTextField textNome;
	private JLabel lblRE;
	private JLabel lblCliente;
	private JLabel lblTelefone;
	private JLabel lblEquipamento;
	private JLabel lblRelato;
	private JLabel lblRelatorio;
	private JLabel lblValor;
	private JLabel lblStatuss;
	private JButton btnBuscar;
	private JButton btnCarregar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnPDF;
	private JButton btnReset;
	private JButton btnSobre;
	private JButton btnContador;
	private JScrollPane scrollPaneLista;
	private JLabel lblData;
	private JLabel lblStatus;
	private JButton btnPesquisa;
	private JButton btnAdicionar;
	private JComboBox<String> listStatus;
	private JList listNomes;
	private JTextField textData;
	private JLabel lblCalendario;
	private ResultSet rs;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AssistenciaTecnica frame = new AssistenciaTecnica();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AssistenciaTecnica() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AssistenciaTecnica.class.getResource("/img/iFixTec.jpeg")));
		setTitle("IFixTec");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1027, 550);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(234, 227, 242));
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollPaneLista = new JScrollPane();
		scrollPaneLista.setBorder(null);
		scrollPaneLista.setBounds(200, 80, 237, 103);
		scrollPaneLista.setVisible(false);
		contentPane.add(scrollPaneLista);
		listNomes = new JList<>();
		listNomes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarNome();
			}
		});
		scrollPaneLista.setViewportView(listNomes);
		lblFoto = new JLabel("");
		lblFoto.setIcon(new ImageIcon(AssistenciaTecnica.class.getResource("/img/foto.png")));
		lblFoto.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblFoto.setBounds(705, 11, 300, 300);
		contentPane.add(lblFoto);
		textRE = new JTextField();
		textRE.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		textRE.setEnabled(false);
		textRE.setToolTipText("");
		textRE.setBounds(37, 59, 133, 20);
		contentPane.add(textRE);
		textRE.setColumns(10);
		textTelefone = new JTextField();
		textTelefone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		textTelefone.setDocument(new Validador(11));
		textTelefone.setColumns(10);
		textTelefone.setBounds(37, 134, 400, 20);
		contentPane.add(textTelefone);
		textEquipamento = new JTextField();
		textEquipamento.setColumns(10);
		textEquipamento.setDocument(new Validador(100));
		textEquipamento.setBounds(37, 179, 400, 20);
		contentPane.add(textEquipamento);
		textRelato = new JTextField();
		textRelato.setColumns(10);
		textRelato.setDocument(new Validador(100));
		textRelato.setBounds(37, 226, 400, 20);
		contentPane.add(textRelato);
		textRelatorio = new JTextField();
		textRelatorio.setBackground(new Color(255, 255, 255));
		textRelatorio.setColumns(10);
		textRelatorio.setDocument(new Validador(100));
		textRelatorio.setBounds(37, 274, 400, 20);
		contentPane.add(textRelatorio);
		textValor = new JTextField();
		textValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		textValor.setColumns(10);
		textValor.setBounds(37, 370, 120, 20);
		textValor.setDocument(new Validador(8));
		contentPane.add(textValor);
		textNome = new JTextField();
		textNome.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					scrollPaneLista.setVisible(false);
				}
			}

			public void keyReleased(KeyEvent e) {
				listarNomes();
			}
		});
		textNome.setDocument(new Validador(30));
		textNome.setBackground(new Color(255, 255, 255));
		textNome.setColumns(10);
		textNome.setBounds(200, 59, 237, 20);
		contentPane.add(textNome);
		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(AssistenciaTecnica.class.getResource("/img/adicionar.png")));
		btnAdicionar.setBounds(37, 414, 64, 64);
		contentPane.add(btnAdicionar);
		lblRE = new JLabel("Número de Requisição");
		lblRE.setFont(new Font("Arial", Font.PLAIN, 12));
		lblRE.setBounds(37, 30, 132, 29);
		contentPane.add(lblRE);
		lblCliente = new JLabel("Nome do Cliente");
		lblCliente.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCliente.setBounds(201, 30, 132, 29);
		contentPane.add(lblCliente);
		lblTelefone = new JLabel("Telefone");
		lblTelefone.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTelefone.setBounds(37, 113, 132, 20);
		contentPane.add(lblTelefone);
		lblEquipamento = new JLabel("Equipamento");
		lblEquipamento.setFont(new Font("Arial", Font.PLAIN, 12));
		lblEquipamento.setBounds(38, 154, 132, 29);
		contentPane.add(lblEquipamento);
		lblRelato = new JLabel("Relato do Cliente");
		lblRelato.setFont(new Font("Arial", Font.PLAIN, 12));
		lblRelato.setBounds(37, 203, 186, 29);
		contentPane.add(lblRelato);
		lblRelatorio = new JLabel("Relatório Técnico ");
		lblRelatorio.setFont(new Font("Arial", Font.PLAIN, 12));
		lblRelatorio.setBounds(37, 252, 186, 29);
		contentPane.add(lblRelatorio);
		lblValor = new JLabel("Valor");
		lblValor.setFont(new Font("Arial", Font.PLAIN, 12));
		lblValor.setBounds(37, 348, 28, 29);
		contentPane.add(lblValor);
		lblStatuss = new JLabel("Status");
		lblStatuss.setFont(new Font("Arial", Font.PLAIN, 12));
		lblStatuss.setBounds(37, 300, 35, 29);
		contentPane.add(lblStatuss);
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarRE();
			}
		});
		btnBuscar.setEnabled(false);
		btnBuscar.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setBounds(475, 58, 100, 23);
		contentPane.add(btnBuscar);

		btnCarregar = new JButton("Carregar Foto");
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();
			}
		});
		btnCarregar.setForeground(Color.BLACK);
		btnCarregar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnCarregar.setBackground(Color.WHITE);
		btnCarregar.setBounds(797, 322, 120, 25);
		contentPane.add(btnCarregar);
		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		btnEditar.setToolTipText("Editar");
		btnEditar.setIcon(new ImageIcon(AssistenciaTecnica.class.getResource("/img/editar.png")));
		btnEditar.setBounds(111, 414, 64, 64);
		contentPane.add(btnEditar);
		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setIcon(new ImageIcon(AssistenciaTecnica.class.getResource("/img/excluir.png")));
		btnExcluir.setBounds(259, 414, 64, 64);
		contentPane.add(btnExcluir);

		btnPDF = new JButton("");
		btnPDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gerarPDF();
			}
		});
		btnPDF.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		btnPDF.setToolTipText("PDF");
		btnPDF.setIcon(new ImageIcon(AssistenciaTecnica.class.getResource("/img/pdf.png")));
		btnPDF.setBounds(617, 125, 64, 64);
		contentPane.add(btnPDF);
		btnReset = new JButton("");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnReset.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		btnReset.setToolTipText("Limpar ");
		btnReset.setIcon(new ImageIcon(AssistenciaTecnica.class.getResource("/img/limpar.png")));
		btnReset.setBounds(185, 414, 64, 64);
		contentPane.add(btnReset);
		String[] colum = { "", "Aguardando atendimento", "Em andamento", "Concluído" };
		listStatus = new JComboBox<>(colum);
		listStatus.setToolTipText("");
		listStatus.setBounds(37, 322, 237, 22);
		contentPane.add(listStatus);
		btnSobre = new JButton("");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setToolTipText("Sobre");
		btnSobre.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		btnSobre.setIcon(new ImageIcon(AssistenciaTecnica.class.getResource("/img/sobre.png")));
		btnSobre.setBounds(617, 45, 64, 64);
		contentPane.add(btnSobre);
		btnContador = new JButton("");
		btnContador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contadorclientes();
			}
		});
		btnContador.setToolTipText("Relatórios");
		btnContador.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		btnContador.setIcon(new ImageIcon(AssistenciaTecnica.class.getResource("/img/lista.png")));
		btnContador.setBounds(617, 205, 64, 64);
		contentPane.add(btnContador);
		lblData = new JLabel("");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblData.setBounds(705, 462, 247, 38);
		contentPane.add(lblData);
		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(AssistenciaTecnica.class.getResource("/img/bancooff.png")));
		lblStatus.setBounds(958, 468, 32, 32);
		contentPane.add(lblStatus);
		btnPesquisa = new JButton("");
		btnPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lupa();
			}
		});
		btnPesquisa.setToolTipText("Pèsquisar");
		btnPesquisa.setIcon(new ImageIcon(AssistenciaTecnica.class.getResource("/img/lupa.png")));
		btnPesquisa.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		btnPesquisa.setBounds(333, 414, 64, 64);
		contentPane.add(btnPesquisa);
		textData = new JTextField();
		textData.setEnabled(false);
		textData.setColumns(10);
		textData.setBounds(288, 370, 149, 20);
		contentPane.add(textData);
		lblCalendario = new JLabel("Data de Entrada");
		lblCalendario.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCalendario.setBounds(288, 348, 125, 29);
		contentPane.add(lblCalendario);
		status();
		data();
	}

	private void status() {
		try {
			con = dao.conectar();
			if (con == null) {
				lblStatus.setIcon(new ImageIcon(AssistenciaTecnica.class.getResource("/img/bancooff.png")));
			} else {
				lblStatus.setIcon(new ImageIcon(AssistenciaTecnica.class.getResource("/img/bancoon.png")));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		con = dao.conectar();
	}

	private void data() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblData.setText(formatador.format(data));
	}

	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar Foto");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivos de Imagens(*.PNG, *JPG, JPEG*)", "png", "jpg", "jpeg"));
		int resultado = jfc.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {
				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(),
						lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
				fotoCarregada = true;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void adicionar() {
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do cliente");
			textNome.requestFocus();
		} else if (tamanho == 0) {
			JOptionPane.showMessageDialog(null, "Selecione a foto");
		} else if (textTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o telefone do cliente");
			textTelefone.requestFocus();
		} else if (textEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o equipamento");
			textEquipamento.requestFocus();
		} else if (textRelato.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o relato do cliente");
			textRelato.requestFocus();
		} else if (textRelatorio.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o relatorio do serviço");
		} else if (listStatus.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "Escolha o status do serviço");
		} else if (textValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o valor do serviço");
			textValor.requestFocus();
		} else {
			String insert = "insert into assistencia (nome_cliente, telefone, equipamento, defeito, descricao_servico, valor, foto_equipamento, status) values(?, ?, ?, ?, ?, ?, ?, ?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(insert);
				pst.setString(1, textNome.getText());
				pst.setString(2, textTelefone.getText());
				pst.setString(3, textEquipamento.getText());
				pst.setString(4, textRelato.getText());
				pst.setString(5, textRelatorio.getText());
				pst.setString(6, textValor.getText());
				pst.setBlob(7, fis, tamanho);
				pst.setString(8, listStatus.getSelectedItem().toString());
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso");
					reset();
				} else {
					JOptionPane.showMessageDialog(null, "Erro! Cliente não cadastrado");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void lupa() {
		textRE.setEnabled(true);
		btnBuscar.setEnabled(true);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);
		modoPesquisa = true;
	}

	private void buscarRE() {
		if (textRE.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o registro do cliente");
			textRE.requestFocus();
		} else {
			String readRE = "select * from assistencia where id = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readRE);
				pst.setString(1, textRE.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					textNome.setText(rs.getString(2));
					textTelefone.setText(rs.getString(3));
					textEquipamento.setText(rs.getString(4));
					textRelato.setText(rs.getString(5));
					textRelatorio.setText(rs.getString(6));
					textValor.setText(rs.getString(7));
					Blob blob = (Blob) rs.getBlob(8);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);
					String status = rs.getString(9);
					listStatus.setSelectedItem(status);
					textData.setText(rs.getString(10));
				} else {
					int confirma = JOptionPane.showConfirmDialog(null,
							"Cliente não cadastrado.\nDeseja iniciar um novo cadastro?", "Aviso",
							JOptionPane.YES_NO_OPTION);
					if (confirma == JOptionPane.YES_NO_OPTION) {
						reset();
					} else {
						lupa();
					}
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void listarNomes() {
		if (!modoPesquisa) {
			return;
		}
		DefaultListModel<String> modelo = new DefaultListModel<String>();
		listNomes.setModel(modelo);
		String readLista = "select * from assistencia where nome_cliente like '" + textNome.getText() + "%'"
				+ "order by nome_cliente";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPaneLista.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (textNome.getText().isEmpty()) {
					scrollPaneLista.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarNome() {
		if (!modoPesquisa) {
			return;
		}
		int linha = listNomes.getSelectedIndex();
		if (linha >= 0) {
			String readNome = "select * from assistencia where nome_cliente like '" + textNome.getText() + "%'"
					+ "order by nome_cliente limit " + (linha) + ", 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readNome);
				rs = pst.executeQuery();
				while (rs.next()) {
					scrollPaneLista.setVisible(false);
					textRE.setText(rs.getString(1));
					textNome.setText(rs.getString(2));
					textTelefone.setText(rs.getString(3));
					textEquipamento.setText(rs.getString(4));
					textRelato.setText(rs.getString(5));
					textRelatorio.setText(rs.getString(6));
					textValor.setText(rs.getString(7));
					Blob blob = (Blob) rs.getBlob(8);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
					}
					ImageIcon icone = new ImageIcon(imagem);

					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);
					String status = rs.getString(9);
					listStatus.setSelectedItem(status);

					textData.setText(rs.getString(10));
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editar() {
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do cliente");
			textNome.requestFocus();
		} else {
			if (fotoCarregada == true) {
				String update = "update assistencia set nome_cliente=?, telefone=?, equipamento=?, defeito=?, descricao_servico=?, valor=?, foto_equipamento=?, status=? where id=?";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(update);
					pst.setString(1, textNome.getText());
					pst.setString(2, textTelefone.getText());
					pst.setString(3, textEquipamento.getText());
					pst.setString(4, textRelato.getText());
					pst.setString(5, textRelatorio.getText());
					pst.setString(6, textValor.getText());
					pst.setBlob(7, fis, tamanho);
					pst.setString(8, listStatus.getSelectedItem().toString());
					pst.setString(9, textRE.getText().toString());
					int confirma = pst.executeUpdate();
					if (confirma == 1) {
						JOptionPane.showMessageDialog(null, "Dados do cliente alterados");
					} else {
						JOptionPane.showMessageDialog(null, "Erro! Dados do cliente não alterado");
					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				String update = "update assistencia set nome_cliente=?, telefone=?, equipamento=?, defeito=?, descricao_servico=?, valor=?, status=? where id=?";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(update);
					pst.setString(1, textNome.getText());
					pst.setString(2, textTelefone.getText());
					pst.setString(3, textEquipamento.getText());
					pst.setString(4, textRelato.getText());
					pst.setString(5, textRelatorio.getText());
					pst.setString(6, textValor.getText());
					pst.setString(7, listStatus.getSelectedItem().toString());
					pst.setString(8, textRE.getText().toString());
					int confirma = pst.executeUpdate();

					if (confirma == 1) {
						JOptionPane.showMessageDialog(null, "Dados do cliente alterados");
					} else {
						JOptionPane.showMessageDialog(null, "Erro! Dados do cliente não alterado");
					}

					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	private void excluir() {
		int confirmaExcluir = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste cliente??", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirmaExcluir == JOptionPane.YES_NO_OPTION) {
			String delete = "delete from assistencia where id=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, textRE.getText());

				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					reset();
					JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void gerarPDF() {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));
			document.open();
			Date data = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(data)));
			document.add(new Paragraph("Listagem de clientes"));
			document.add(new Paragraph(" "));

			PdfPTable tabela = new PdfPTable(8);
			PdfPCell col1 = new PdfPCell(new Paragraph("ID"));
			tabela.addCell(col1);
			PdfPCell col2 = new PdfPCell(new Paragraph("Nome"));
			tabela.addCell(col2);
			PdfPCell col3 = new PdfPCell(new Paragraph("Telefone"));
			tabela.addCell(col3);
			PdfPCell col4 = new PdfPCell(new Paragraph("Equipamento"));
			tabela.addCell(col4);
			PdfPCell col5 = new PdfPCell(new Paragraph("Defeito"));
			tabela.addCell(col5);
			// PdfPCell col6 = new PdfPCell(new Paragraph("Descrição do Serviço"));
			// tabela.addCell(col6);
			PdfPCell col6 = new PdfPCell(new Paragraph("Valor"));
			tabela.addCell(col6);
			PdfPCell col7 = new PdfPCell(new Paragraph("Foto"));
			tabela.addCell(col7);
			PdfPCell col8 = new PdfPCell(new Paragraph("Status"));
			tabela.addCell(col8);
			// PdfPCell col10 = new PdfPCell(new Paragraph("Data de Entrada"));
			// tabela.addCell(col10);
			String readLista = "select * from assistencia order by nome_cliente";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readLista);
				rs = pst.executeQuery();
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					// tabela.addCell(rs.getString(6));
					tabela.addCell(rs.getString(7));
					Blob blob = (Blob) rs.getBlob(8);
					byte[] img = blob.getBytes(1, (int) blob.length());
					com.itextpdf.text.Image imagem = com.itextpdf.text.Image.getInstance(img);
					tabela.addCell(imagem);
					tabela.addCell(rs.getString(9));
					// tabela.addCell(rs.getString(10));
				}
				con.close();
			} catch (Exception ex) {
				System.out.println(ex);
			}
			document.add(tabela);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			document.close();
		}
		try {
			Desktop.getDesktop().open(new File("clientes.pdf"));
		} catch (Exception e2) {
			System.out.println(e2);
		}
	}

	private void contadorclientes() {
		String contar = "select count(*) from assistencia;";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(contar);
			rs = pst.executeQuery();

			if (rs.next()) {
				int total = rs.getInt(1);
				JOptionPane.showMessageDialog(null, "Total de serviços registrados: " + total);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void reset() {
		scrollPaneLista.setVisible(false);
		textRE.setText(null);
		textNome.setText(null);
		textEquipamento.setText(null);
		textRelatorio.setText(null);
		listStatus.setSelectedIndex(0);
		textValor.setText(null);
		textTelefone.setText(null);
		lblFoto.setIcon(new ImageIcon(AssistenciaTecnica.class.getResource("/img/foto.png")));
		textRelato.setText(null);
		textData.setText(null);
		textNome.requestFocus();
		fotoCarregada = false;
		tamanho = 0;
		textRE.setEnabled(false);
		btnBuscar.setEnabled(false);
		btnCarregar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnPesquisa.setEnabled(true);
		btnReset.setEnabled(true);
		btnAdicionar.setEnabled(true);
		modoPesquisa = false;
	}
}