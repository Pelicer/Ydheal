package view.jFrames.cadastros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.IllegalFormatException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import modelSuplerclasses.Cliente;
import view.jDialogs.JDSelecionarBuscaClientes;
import Model.DAO.ConexaoMySQL;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class JFCVisitas extends JDialog {

	Cliente c = new Cliente();

	private JPanel contentPane;
	private JTextField tfEndereco;
	private JFormattedTextField tfTelefone;
	private JTextField tfObs;
	private JTextField tfbairro;
	private JTextField tfnumero;
	private JTextField tfCliente;

	public JFCVisitas() {

		// Verificação ao fechar janela.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				int resposta = JOptionPane
						.showConfirmDialog(
								null,
								"O cadastro da visita será cancelado. Tem certeza que deseja continuar?",
								"Aviso", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					dispose();
				} else if (resposta == JOptionPane.NO_OPTION) {
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// cancel
				}

			}
		});

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/visita.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Cadastro Visitas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 380, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblEndereoDoCliente = new JLabel("Endere\u00E7o:");
		lblEndereoDoCliente.setBounds(10, 180, 80, 14);
		contentPane.add(lblEndereoDoCliente);

		tfEndereco = new JTextField();
		tfEndereco.setEditable(false);
		tfEndereco.setBounds(118, 177, 234, 20);
		contentPane.add(tfEndereco);
		tfEndereco.setColumns(10);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(10, 242, 80, 14);
		contentPane.add(lblTelefone);

		// Máscara de Campo.
		MaskFormatter campoTelefone = null;
		try {
			campoTelefone = new MaskFormatter("(##)#####-####");
		} catch (Exception e) {
		}
		tfTelefone = new JFormattedTextField(campoTelefone);
		tfTelefone.setEditable(false);
		tfTelefone.setBounds(118, 239, 234, 20);
		contentPane.add(tfTelefone);
		tfTelefone.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Dia da Visita:");
		lblNewLabel_1.setBounds(10, 118, 80, 14);
		contentPane.add(lblNewLabel_1);

		JDateChooser dtVisita = new JDateChooser();
		dtVisita.setBounds(118, 118, 234, 20);
		contentPane.add(dtVisita);

		JLabel lblHorro = new JLabel("Hor\u00E1ro:");
		lblHorro.setBounds(10, 93, 65, 14);
		contentPane.add(lblHorro);

		JSpinner jsHora = new JSpinner();
		jsHora.setModel(new SpinnerNumberModel(0, 0, 24, 1));
		jsHora.setBounds(118, 90, 110, 20);
		contentPane.add(jsHora);

		JSpinner jsMinutos = new JSpinner();
		jsMinutos.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		jsMinutos.setBounds(238, 90, 114, 20);
		contentPane.add(jsMinutos);

		JLabel lblObservao = new JLabel("Observa\u00E7\u00E3o:");
		lblObservao.setBounds(10, 301, 80, 14);
		contentPane.add(lblObservao);

		tfObs = new JTextField();
		tfObs.setHorizontalAlignment(SwingConstants.LEFT);
		tfObs.setBounds(118, 301, 234, 50);
		contentPane.add(tfObs);
		tfObs.setColumns(10);

		JButton btnSalvar = new JButton("Salvar");
		// Adicionando imagem.
		Image imgSalvar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(imgSalvar));
		btnSalvar.setToolTipText("Salvar Dados");
		btnSalvar.setBounds(10, 362, 114, 20);
		contentPane.add(btnSalvar);

		JButton btnLimpar = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(imgLimpar));
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setBounds(124, 362, 114, 20);
		contentPane.add(btnLimpar);

		JButton btnCancelar = new JButton("Cancelar");
		// Adicionando imagem.
		Image imgCancelar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnCancelar.setIcon(new ImageIcon(imgCancelar));
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.setBounds(238, 362, 114, 20);
		contentPane.add(btnCancelar);

		JLabel lblCadastroDeVisitas = new JLabel("Cadastro de Visitas");
		// Adicionando imagem.
		Image imgVisitas = new ImageIcon(this.getClass().getResource(
				"/50px/visita.png")).getImage();
		lblCadastroDeVisitas.setIcon(new ImageIcon(imgVisitas));
		lblCadastroDeVisitas.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCadastroDeVisitas.setBounds(10, 11, 343, 50);
		contentPane.add(lblCadastroDeVisitas);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 72, 374, 2);
		contentPane.add(separator);

		JLabel label = new JLabel("* - Campos Obrigat\u00F3rios.");
		label.setForeground(Color.RED);
		label.setBackground(Color.RED);
		label.setBounds(132, 52, 208, 14);
		contentPane.add(label);

		JLabel label_1 = new JLabel("*");
		label_1.setForeground(Color.RED);
		label_1.setBackground(Color.RED);
		label_1.setBounds(85, 93, 25, 14);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("*");
		label_2.setForeground(Color.RED);
		label_2.setBackground(Color.RED);
		label_2.setBounds(85, 118, 25, 14);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("*");
		label_3.setForeground(Color.RED);
		label_3.setBackground(Color.RED);
		label_3.setBounds(85, 242, 25, 14);
		contentPane.add(label_3);

		JLabel label_4 = new JLabel("*");
		label_4.setForeground(Color.RED);
		label_4.setBackground(Color.RED);
		label_4.setBounds(85, 180, 25, 14);
		contentPane.add(label_4);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(10, 273, 80, 14);
		contentPane.add(lblBairro);

		JLabel label_7 = new JLabel("*");
		label_7.setForeground(Color.RED);
		label_7.setBackground(Color.RED);
		label_7.setBounds(85, 276, 25, 14);
		contentPane.add(label_7);

		tfbairro = new JTextField();
		tfbairro.setEditable(false);
		tfbairro.setBounds(118, 270, 236, 20);
		contentPane.add(tfbairro);
		tfbairro.setColumns(10);

		JLabel lblNmero = new JLabel("N\u00FAmero:");
		lblNmero.setBounds(10, 210, 65, 14);
		contentPane.add(lblNmero);

		JLabel label_6 = new JLabel("*");
		label_6.setForeground(Color.RED);
		label_6.setBackground(Color.RED);
		label_6.setBounds(85, 205, 25, 14);
		contentPane.add(label_6);

		tfnumero = new JTextField();
		tfnumero.setEditable(false);
		tfnumero.setBounds(118, 208, 234, 20);
		contentPane.add(tfnumero);
		tfnumero.setColumns(10);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(10, 155, 46, 14);
		contentPane.add(lblCliente);

		tfCliente = new JTextField();
		tfCliente.setBounds(118, 149, 110, 20);
		contentPane.add(tfCliente);
		tfCliente.setColumns(10);

		JButton btnSelecionarCliente = new JButton("Selecionar");
		// Adicionando imagem.
		Image imgOk = new ImageIcon(this.getClass().getResource("/16px/ok.png"))
				.getImage();
		btnSelecionarCliente.setIcon(new ImageIcon(imgOk));
		btnSelecionarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JDSelecionarBuscaClientes frame = new JDSelecionarBuscaClientes(
							c);
					frame.setModal(true);
					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

				tfCliente.setText(c.getNome());
				tfbairro.setText(c.getBairro());
				tfEndereco.setText(c.getEndereco());
				tfnumero.setText(c.getNumero());
				tfTelefone.setText(c.getTelefone());

			}
		});
		btnSelecionarCliente.setBounds(238, 149, 114, 20);
		contentPane.add(btnSelecionarCliente);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfEndereco.setText("");
				tfCliente.setText("");
				tfTelefone.setText("");
				tfObs.setText("");
				jsHora.grabFocus();
			}
		});
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tfEndereco.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);
				} else if (tfCliente.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else if (tfTelefone.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else if (dtVisita.getJCalendar() == null) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else if (jsHora.getValue() == null) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else if (jsMinutos.getValue() == null) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else {
					try {
						Class.forName("com.mysql.jdbc.Driver");

						// cria a conecxão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						// formata data para padrão americano
						SimpleDateFormat Formatodataam = new SimpleDateFormat(
								"yyyy/MM/dd");

						st.execute("insert into tbl_visita (visita_data,visita_status,visita_horario,visita_nome,visita_endereco,visita_telefone,visita_observacao, visita_bairro, visita_numero) values('"
								+ Formatodataam.format(dtVisita.getDate())
								+ "',0,'"
								+ String.valueOf(jsHora.getValue())
								+ ":"
								+ String.valueOf(jsMinutos.getValue())
								+ "','"
								+ tfCliente.getText()
								+ "','"
								+ tfEndereco.getText()
								+ "','"
								+ tfTelefone.getText()
								+ "','"
								+ tfObs.getText()
								+ "', '"
								+ tfbairro.getText()
								+ "', '" + tfnumero.getText() + "');");

						// fala q executou co sucesso
						JOptionPane.showMessageDialog(null,
								"Visita cadastrada com sucesso!");

						st.close();
						// fecha a concxão
						con.close();

						// fecha a tela após sucesso
						dispose();

					}

					catch (SQLException erroBanco) {
						JOptionPane
								.showMessageDialog(
										null,
										"Falha na conexão com o banco de dados. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
										"Erro!", JOptionPane.ERROR_MESSAGE);
						erroBanco.printStackTrace();
					}

					catch (NumberFormatException erroNumero) {

						JOptionPane
								.showMessageDialog(
										null,
										"Algum campo foi preenchido incorretamente. \nPor favor, verifique.",
										"Aviso!", JOptionPane.WARNING_MESSAGE);

						erroNumero.printStackTrace();
					} catch (IllegalFormatException erroFormato) {
						JOptionPane
								.showMessageDialog(
										null,
										"Algum campo foi preenchido incorretamente. \nPor favor, verifique.",
										"Aviso!", JOptionPane.ERROR_MESSAGE);
					}

					catch (ClassNotFoundException erroJDBC) {
						JOptionPane
								.showMessageDialog(
										null,
										"Falha no cadastro. \nPor favor, entre em contato com um técnico, ou tente novamente mais tarde.",
										"Aviso!", JOptionPane.WARNING_MESSAGE);
					}

				}
			}

		});
	}
}
