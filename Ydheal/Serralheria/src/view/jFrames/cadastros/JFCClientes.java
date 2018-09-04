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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import modelSuplerclasses.Cliente;
import Model.DAO.ConexaoMySQL;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class JFCClientes extends JDialog {

	Cliente cli = new Cliente();
	private JPanel contentPane;
	private JTextField tfNome;
	private JTextField tfEndereco;
	private JFormattedTextField tfTelefone;
	private JTextField tfEmail;
	private JFormattedTextField tfCpf;
	private JFormattedTextField tfRg;
	private JTextField tfBairro;
	private JTextField tfComplemento;
	private JFormattedTextField tfTelefone2;
	private JFormattedTextField tfcep;
	private JTextField tfsobrenome;
	private JTextField tfnumero;

	public JFCClientes() {

		// Verificação ao fechar janela.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				int resposta = JOptionPane
						.showConfirmDialog(
								null,
								"O cadastro do cliente será cancelado. Tem certeza que deseja continuar?",
								"Aviso", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					dispose();
				} else if (resposta == JOptionPane.NO_OPTION) {
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// cancel
				}

			}
		});

		// Máscara de Campo.
		MaskFormatter campoTelefone = null;
		MaskFormatter campoCpf = null;
		MaskFormatter campoRg = null;
		MaskFormatter campoCep = null;
		try {
			campoTelefone = new MaskFormatter("(##)#####-####");
			campoCpf = new MaskFormatter("###.###.###-##");
			campoRg = new MaskFormatter("##.###.###-#");
			campoCep = new MaskFormatter("##.###-###");
		} catch (Exception e) {
		}

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/cliente.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Cadastro Cliente");
		setBounds(100, 100, 515, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfNome = new JTextField();
		tfNome.setBounds(106, 101, 145, 20);
		contentPane.add(tfNome);
		tfNome.setColumns(10);

		JLabel lblEndereco = new JLabel("Endere\u00E7o:");
		lblEndereco.setBounds(6, 132, 70, 14);
		contentPane.add(lblEndereco);

		tfEndereco = new JTextField();
		tfEndereco.setBounds(106, 129, 382, 20);
		contentPane.add(tfEndereco);
		tfEndereco.setColumns(10);

		JLabel lblTelefone1 = new JLabel("Telefone 1:");
		lblTelefone1.setBounds(6, 252, 70, 14);
		contentPane.add(lblTelefone1);

		tfTelefone = new JFormattedTextField(campoTelefone);
		tfTelefone.setBounds(106, 249, 145, 20);
		contentPane.add(tfTelefone);
		tfTelefone.setColumns(10);

		JLabel lblEmail = new JLabel("E-Mail:");
		lblEmail.setBounds(6, 224, 70, 14);
		contentPane.add(lblEmail);

		tfEmail = new JTextField();
		tfEmail.setBounds(106, 221, 382, 20);
		contentPane.add(tfEmail);
		tfEmail.setColumns(10);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(6, 280, 70, 14);
		contentPane.add(lblCpf);

		JLabel lblRg = new JLabel("RG:");
		lblRg.setBounds(261, 280, 38, 14);
		contentPane.add(lblRg);

		tfCpf = new JFormattedTextField(campoCpf);
		tfCpf.setBounds(106, 277, 145, 20);
		contentPane.add(tfCpf);
		tfCpf.setColumns(10);

		tfRg = new JFormattedTextField(campoRg);
		tfRg.setBounds(343, 277, 145, 20);
		contentPane.add(tfRg);
		tfRg.setColumns(10);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(6, 104, 55, 14);
		contentPane.add(lblNome);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(6, 160, 55, 14);
		contentPane.add(lblBairro);

		tfBairro = new JTextField();
		tfBairro.setBounds(106, 157, 145, 20);
		contentPane.add(tfBairro);
		tfBairro.setColumns(10);

		JLabel lblComplemento = new JLabel("Complemento:");
		lblComplemento.setBounds(261, 160, 85, 14);
		contentPane.add(lblComplemento);

		tfComplemento = new JTextField();
		tfComplemento.setBounds(342, 160, 146, 20);
		contentPane.add(tfComplemento);
		tfComplemento.setColumns(10);

		JLabel lblTelefone2 = new JLabel("Telefone 2:");
		lblTelefone2.setBounds(261, 252, 70, 14);
		contentPane.add(lblTelefone2);

		tfTelefone2 = new JFormattedTextField(campoTelefone);
		tfTelefone2.setColumns(10);
		tfTelefone2.setBounds(342, 249, 146, 20);
		contentPane.add(tfTelefone2);

		JButton btnNewButton_1 = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnNewButton_1.setIcon(new ImageIcon(imgLimpar));
		btnNewButton_1.setToolTipText("Limpar Campos");
		btnNewButton_1.setBounds(204, 335, 118, 20);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Cancelar");
		// Adicionando imagem.
		Image imgCancelar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnNewButton_2.setIcon(new ImageIcon(imgCancelar));
		btnNewButton_2.setToolTipText("Cancelar");
		btnNewButton_2.setBounds(322, 335, 118, 20);
		contentPane.add(btnNewButton_2);

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setBounds(6, 196, 38, 14);
		contentPane.add(lblCep);

		tfcep = new JFormattedTextField(campoCep);
		tfcep.setBounds(106, 193, 145, 20);
		contentPane.add(tfcep);
		tfcep.setColumns(10);

		JLabel lblDataDeNasc = new JLabel("Data de Nasc:");
		lblDataDeNasc.setBounds(6, 310, 85, 14);
		contentPane.add(lblDataDeNasc);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(106, 308, 382, 20);
		contentPane.add(dateChooser);

		JLabel lblCadastroDeClientes = new JLabel("Cadastro de Clientes");
		// Adicionando Imagem.
		Image imgClientes = new ImageIcon(this.getClass().getResource(
				"/50px/cliente.png")).getImage();
		lblCadastroDeClientes.setIcon(new ImageIcon(imgClientes));
		lblCadastroDeClientes.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCadastroDeClientes.setBounds(21, 11, 343, 50);
		contentPane.add(lblCadastroDeClientes);

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 72, 521, 2);
		contentPane.add(separator);

		JButton btnNewButton = new JButton("Salvar");
		// Adicionando imagem.
		Image imgSalvar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnNewButton.setIcon(new ImageIcon(imgSalvar));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
					throws IllegalArgumentException {
				if (tfNome.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);
				} else if (tfEndereco.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else if (tfBairro.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else if (tfcep.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else if (tfCpf.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else if (tfEmail.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else if (tfEndereco.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else if (tfnumero.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else if (tfRg.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else if (tfsobrenome.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else if (tfTelefone.getText().isEmpty()) {
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

						cli.setNumero(tfnumero.getText());
						cli.setCep(tfcep.getText());
						cli.setCpf(tfCpf.getText());
						cli.setRg(tfRg.getText());
						cli.setTelefone(tfTelefone.getText());
						cli.setTelefone2(tfTelefone2.getText());

						st.execute("insert into tbl_cliente (cliente_numero, cliente_cep, cliente_datanasc, cliente_sobrenome, cliente_nome, cliente_cpf, cliente_rg, cliente_endereco, cliente_bairro, cliente_complemento, cliente_telefone1, cliente_telefone2,"
								+ " cliente_email) values ('"
								+ cli.getNumero()
								+ "',  '"
								+ cli.getCep()
								+ "', '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "', '"
								+ tfsobrenome.getText()
								+ "', '"
								+ tfNome.getText()
								+ "', '"
								+ cli.getCpf()
								+ "', '"
								+ cli.getRg()
								+ "', '"
								+ tfEndereco.getText()
								+ "', '"
								+ tfBairro.getText()
								+ "',  '"
								+ tfComplemento.getText()
								+ "', '"
								+ cli.getTelefone()
								+ "',  '"
								+ cli.getTelefone2()
								+ "',  '"
								+ tfEmail.getText() + "');");

						// fala que executou com sucesso
						JOptionPane.showMessageDialog(null,
								"Cliente cadastrado com sucesso!");

						st.close();
						// fecha a concxão
						con.close();

						// fecha a tela após sucesso
						dispose();

					} catch (SQLException erroBanco) {
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

		btnNewButton.setToolTipText("Salvar Dados");
		btnNewButton.setBounds(86, 335, 118, 20);
		contentPane.add(btnNewButton);

		JLabel lblSobrenome = new JLabel("Sobrenome:");
		lblSobrenome.setBounds(261, 104, 70, 14);
		contentPane.add(lblSobrenome);

		tfsobrenome = new JTextField();
		tfsobrenome.setBounds(343, 101, 146, 20);
		contentPane.add(tfsobrenome);
		tfsobrenome.setColumns(10);

		JLabel lblNumero = new JLabel("Numero:");
		lblNumero.setBounds(261, 196, 56, 14);
		contentPane.add(lblNumero);

		tfnumero = new JTextField();
		tfnumero.setBounds(342, 193, 146, 20);
		contentPane.add(tfnumero);
		tfnumero.setColumns(10);

		JLabel label = new JLabel("*");
		label.setForeground(Color.RED);
		label.setBackground(Color.RED);
		label.setBounds(86, 104, 25, 14);
		contentPane.add(label);

		JLabel label_1 = new JLabel("*");
		label_1.setForeground(Color.RED);
		label_1.setBackground(Color.RED);
		label_1.setBounds(330, 104, 34, 14);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("*");
		label_2.setForeground(Color.RED);
		label_2.setBackground(Color.RED);
		label_2.setBounds(86, 132, 25, 14);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("*");
		label_3.setForeground(Color.RED);
		label_3.setBackground(Color.RED);
		label_3.setBounds(86, 160, 25, 14);
		contentPane.add(label_3);

		JLabel label_4 = new JLabel("*");
		label_4.setForeground(Color.RED);
		label_4.setBackground(Color.RED);
		label_4.setBounds(86, 199, 25, 14);
		contentPane.add(label_4);

		JLabel label_5 = new JLabel("*");
		label_5.setForeground(Color.RED);
		label_5.setBackground(Color.RED);
		label_5.setBounds(86, 224, 25, 14);
		contentPane.add(label_5);

		JLabel label_6 = new JLabel("*");
		label_6.setForeground(Color.RED);
		label_6.setBackground(Color.RED);
		label_6.setBounds(86, 252, 25, 14);
		contentPane.add(label_6);

		JLabel label_7 = new JLabel("*");
		label_7.setForeground(Color.RED);
		label_7.setBackground(Color.RED);
		label_7.setBounds(86, 280, 25, 14);
		contentPane.add(label_7);

		JLabel label_8 = new JLabel("*");
		label_8.setForeground(Color.RED);
		label_8.setBackground(Color.RED);
		label_8.setBounds(330, 196, 34, 14);
		contentPane.add(label_8);

		JLabel label_9 = new JLabel("*");
		label_9.setForeground(Color.RED);
		label_9.setBackground(Color.RED);
		label_9.setBounds(331, 280, 33, 14);
		contentPane.add(label_9);

		JLabel label_10 = new JLabel("*");
		label_10.setForeground(Color.RED);
		label_10.setBackground(Color.RED);
		label_10.setBounds(86, 310, 25, 14);
		contentPane.add(label_10);

		JLabel lblCampo = new JLabel("* - Campos Obrigat\u00F3rios.");
		lblCampo.setForeground(Color.RED);
		lblCampo.setBackground(Color.RED);
		lblCampo.setBounds(274, 32, 214, 14);
		contentPane.add(lblCampo);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfCpf.setText("");
				tfEmail.setText("");
				tfEndereco.setText("");
				tfNome.setText("");
				tfRg.setText("");
				tfTelefone.setText("");
				tfComplemento.setText("");
				tfBairro.setText("");
				tfTelefone2.setText("");
				tfNome.grabFocus();
			}
		});

	}
}
