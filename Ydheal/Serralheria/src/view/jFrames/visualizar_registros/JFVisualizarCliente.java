package view.jFrames.visualizar_registros;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.IllegalFormatException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
public class JFVisualizarCliente extends JFrame {

	private JPanel contentPane;
	private JTextField tfNome;
	private JTextField tfEndereco;
	private JFormattedTextField tfTelefoneUm;
	private JFormattedTextField tfTelefoneDois;
	private JTextField tfEmail;
	private JTextField tfSobrenome;
	private JTextField tfBairro;
	private JTextField tfNumero;
	private JTextField tfComplemento;
	private JFormattedTextField tfCpf;
	private JFormattedTextField tfRg;
	private JTextField tfdatanacimento;
	private JFormattedTextField tfcep;

	public JFVisualizarCliente(final Cliente c) {

		// Verificação ao fechar janela.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				int resposta = JOptionPane
						.showConfirmDialog(
								null,
								"Certeza que deseja sair da visualização? /nQualquer alteração feita será perdida.",
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
		setTitle("Visualiza\u00E7\u00E3o de Cliente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 415, 532);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCliente = new JLabel("Cliente");
		// Adicionando imagem.
		Image imgCliente = new ImageIcon(this.getClass().getResource(
				"/50px/cliente.png")).getImage();
		lblCliente.setIcon(new ImageIcon(imgCliente));
		lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCliente.setBounds(10, 0, 394, 58);
		contentPane.add(lblCliente);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 69, 404, 2);
		contentPane.add(separator);

		JLabel label_1 = new JLabel("Nome:");
		label_1.setBounds(10, 101, 46, 14);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("Endere\u00E7o:");
		label_2.setBounds(10, 156, 67, 14);
		contentPane.add(label_2);

		JLabel lblTelefone = new JLabel("Telefone 1:");
		lblTelefone.setBounds(10, 307, 67, 14);
		contentPane.add(lblTelefone);

		tfNome = new JTextField();
		tfNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		tfNome.setEditable(false);
		tfNome.setColumns(10);
		tfNome.setBounds(104, 95, 285, 20);
		contentPane.add(tfNome);

		tfEndereco = new JTextField();
		tfEndereco.setEditable(false);
		tfEndereco.setColumns(10);
		tfEndereco.setBounds(104, 150, 285, 20);
		contentPane.add(tfEndereco);

		tfTelefoneUm = new JFormattedTextField(campoTelefone);
		tfTelefoneUm.setEditable(false);
		tfTelefoneUm.setColumns(10);
		tfTelefoneUm.setBounds(104, 304, 156, 20);
		contentPane.add(tfTelefoneUm);

		JButton button = new JButton("Fechar");
		Image imgCancelar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		button.setIcon(new ImageIcon(imgCancelar));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setToolTipText("Fechar");
		button.setBounds(278, 469, 111, 23);
		contentPane.add(button);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(104, 422, 156, 20);
		contentPane.add(dateChooser);

		JButton btnalterar = new JButton("Alterar");

		btnalterar.setToolTipText("Alterar Dados");
		btnalterar.setBounds(157, 469, 111, 23);
		contentPane.add(btnalterar);

		JButton btnSalvar = new JButton("Salvar");
		Image imgSalvar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(imgSalvar));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				SimpleDateFormat Formatodataam = new SimpleDateFormat(
						"yyyy/MM/dd");

				SimpleDateFormat Formatodatabr = new SimpleDateFormat(
						"dd/MM/yyyy");

				String comando = "Update tbl_cliente set " + "cliente_nome = '"
						+ tfNome.getText() + "', " + "cliente_sobrenome = '"
						+ tfSobrenome.getText() + "', " + "cliente_cpf = '"
						+ tfCpf.getText() + "', " + "cliente_rg = '"
						+ tfRg.getText() + "', " + "cliente_numero = '"
						+ tfNumero.getText() + "', " + "cliente_cep = '"
						+ tfcep.getText() + "', " + "cliente_endereco = '"
						+ tfEndereco.getText() + "', " + "cliente_bairro = '"
						+ tfBairro.getText() + "', "
						+ "cliente_complemento = '" + tfComplemento.getText()
						+ "', " + "cliente_telefone1 = '"
						+ tfTelefoneUm.getText() + "', "
						+ "cliente_telefone2 = '" + tfTelefoneDois.getText()
						+ "', " + "cliente_datanasc = '"
						+ Formatodataam.format(dateChooser.getDate()) + "', "
						+ "cliente_email = '" + tfEmail.getText()
						+ "' where cliente_id = " + c.getId() + "; ";

				try {
					Class.forName("com.mysql.jdbc.Driver");

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					st.execute(comando);

					// fala q executou co sucesso
					JOptionPane.showMessageDialog(null,
							"Dados alterados com sucesso!");

					st.close();
					// fecha a concxão
					con.close();

					// torna os campoas não editaveis dnv
					tfBairro.setEditable(false);
					tfComplemento.setEditable(false);
					tfCpf.setEditable(false);
					tfdatanacimento.setEditable(false);
					tfEmail.setEditable(false);
					tfEndereco.setEditable(false);
					tfNome.setEditable(false);
					tfNumero.setEditable(false);
					tfRg.setEditable(false);
					tfSobrenome.setEditable(false);
					tfTelefoneDois.setEditable(false);
					tfTelefoneUm.setEditable(false);
					tfcep.setEditable(false);

					dateChooser.setVisible(false);
					tfdatanacimento.setVisible(true);

					tfdatanacimento.setText(Formatodatabr.format(dateChooser
							.getDate()));

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
		});
		btnSalvar.setToolTipText("Salvar Dados");
		btnSalvar.setBounds(36, 469, 111, 23);
		contentPane.add(btnSalvar);

		JLabel lblTelefone_1 = new JLabel("Telefone 2:");
		lblTelefone_1.setBounds(10, 338, 67, 14);
		contentPane.add(lblTelefone_1);

		tfTelefoneDois = new JFormattedTextField(campoTelefone);
		tfTelefoneDois.setEditable(false);
		tfTelefoneDois.setBounds(104, 335, 156, 20);
		contentPane.add(tfTelefoneDois);
		tfTelefoneDois.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 276, 46, 14);
		contentPane.add(lblEmail);

		tfEmail = new JTextField();
		tfEmail.setEditable(false);
		tfEmail.setBounds(104, 273, 156, 20);
		contentPane.add(tfEmail);
		tfEmail.setColumns(10);

		JLabel lblNewLabel = new JLabel("Sobrenome:");
		lblNewLabel.setBounds(10, 126, 67, 14);
		contentPane.add(lblNewLabel);

		tfSobrenome = new JTextField();
		tfSobrenome.setEditable(false);
		tfSobrenome.setBounds(104, 120, 285, 20);
		contentPane.add(tfSobrenome);
		tfSobrenome.setColumns(10);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(10, 181, 46, 14);
		contentPane.add(lblBairro);

		tfBairro = new JTextField();
		tfBairro.setEditable(false);
		tfBairro.setBounds(104, 181, 156, 20);
		contentPane.add(tfBairro);
		tfBairro.setColumns(10);

		JLabel lblN = new JLabel("N\u00BA");
		lblN.setBounds(270, 181, 46, 14);
		contentPane.add(lblN);

		tfNumero = new JTextField();
		tfNumero.setEditable(false);
		tfNumero.setBounds(305, 181, 84, 20);
		contentPane.add(tfNumero);
		tfNumero.setColumns(10);

		JLabel lblComplemento = new JLabel("Complemento:");
		lblComplemento.setBounds(10, 214, 99, 14);
		contentPane.add(lblComplemento);

		tfComplemento = new JTextField();
		tfComplemento.setEditable(false);
		tfComplemento.setBounds(104, 215, 285, 20);
		contentPane.add(tfComplemento);
		tfComplemento.setColumns(10);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(10, 369, 46, 14);
		contentPane.add(lblCpf);

		tfCpf = new JFormattedTextField(campoCpf);
		tfCpf.setEditable(false);
		tfCpf.setBounds(104, 366, 156, 20);
		contentPane.add(tfCpf);
		tfCpf.setColumns(10);

		JLabel lblRg = new JLabel("RG:");
		lblRg.setBounds(10, 400, 46, 14);
		contentPane.add(lblRg);

		tfRg = new JFormattedTextField(campoRg);
		tfRg.setEditable(false);
		tfRg.setBounds(104, 397, 156, 20);
		contentPane.add(tfRg);
		tfRg.setColumns(10);

		tfcep = new JFormattedTextField(campoCep);
		tfcep.setEditable(false);
		tfcep.setBounds(104, 242, 156, 20);
		contentPane.add(tfcep);
		tfcep.setColumns(10);

		JLabel lblDataDeNascimento = new JLabel("Data de Nasc. :");
		lblDataDeNascimento.setBounds(10, 425, 79, 14);
		contentPane.add(lblDataDeNascimento);

		tfBairro.setText(c.getBairro());
		tfComplemento.setText(c.getComplemento());
		tfCpf.setText(String.valueOf(c.getCpf()));
		tfEmail.setText(c.getEmail());
		tfEndereco.setText(c.getEndereco());
		tfNome.setText(c.getNome());
		tfNumero.setText(String.valueOf(c.getNumero()));
		tfRg.setText(String.valueOf(c.getRg()));
		tfSobrenome.setText(c.getSobrenome());
		tfTelefoneDois.setText(String.valueOf(c.getTelefone()));
		tfTelefoneUm.setText(String.valueOf(c.getTelefone()));
		tfcep.setText(String.valueOf(c.getCep()));

		tfdatanacimento = new JTextField();
		tfdatanacimento.setEditable(false);
		tfdatanacimento.setBounds(104, 422, 156, 20);
		contentPane.add(tfdatanacimento);
		tfdatanacimento.setColumns(10);
		tfdatanacimento.setText(c.getDataNascimento());

		JLabel lblCep = new JLabel("Cep:");
		lblCep.setBounds(10, 246, 46, 14);
		contentPane.add(lblCep);

		dateChooser.setVisible(false);

		Image imgAlterar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		btnalterar.setIcon(new ImageIcon(imgAlterar));
		btnalterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tfNome.setEditable(true);
				tfBairro.setEditable(true);
				tfComplemento.setEditable(true);
				tfCpf.setEditable(true);
				tfEmail.setEditable(true);
				tfEndereco.setEditable(true);
				tfNumero.setEditable(true);
				tfRg.setEditable(true);
				tfSobrenome.setEditable(true);
				tfTelefoneDois.setEditable(true);
				tfTelefoneUm.setEditable(true);
				tfcep.setEditable(true);

				tfdatanacimento.setVisible(false);
				dateChooser.setVisible(true);

				java.util.Date x = null;
				try {
					x = (new SimpleDateFormat("dd/MM/yyyy").parse(c
							.getDataNascimento()));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				dateChooser.setDate(x);

				tfNome.grabFocus();
			}
		});

	}
}
