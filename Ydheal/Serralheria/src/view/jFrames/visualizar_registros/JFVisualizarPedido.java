package view.jFrames.visualizar_registros;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.IllegalFormatException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import modelHerancas.Pedido;
import modelSuplerclasses.Cliente;
import modelSuplerclasses.Orcamento;
import modelSuplerclasses.Visita;
import view.jFrames.cadastros.JFCPagamento;
import Model.DAO.ConexaoMySQL;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class JFVisualizarPedido extends JFrame {

	private JPanel contentPane;
	private JTextField tfCliente;
	private JTextField tforcamento;
	private JTextField tfvisita;
	private JTextField tfgerado;
	private JTextField tfmateriaisenc;

	public JFVisualizarPedido(Pedido p) {

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

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/pedidos.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Visualiza\u00E7\u00E3o de Pedido");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPedido = new JLabel("Pedido");
		// Adicionando imagem.
		Image imgPedido = new ImageIcon(this.getClass().getResource(
				"/50px/pedidos.png")).getImage();
		lblPedido.setIcon(new ImageIcon(imgPedido));
		lblPedido.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPedido.setBounds(10, 0, 394, 58);
		contentPane.add(lblPedido);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 69, 394, 2);
		contentPane.add(separator);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(10, 82, 89, 14);
		contentPane.add(lblCliente);

		JLabel lblOramento = new JLabel("Or\u00E7amento");
		lblOramento.setBounds(10, 110, 89, 14);
		contentPane.add(lblOramento);

		tfCliente = new JTextField();
		tfCliente.setEditable(false);
		tfCliente.setBounds(121, 82, 150, 20);
		contentPane.add(tfCliente);
		tfCliente.setColumns(10);

		tforcamento = new JTextField();
		tforcamento.setEditable(false);
		tforcamento.setBounds(121, 107, 150, 20);
		contentPane.add(tforcamento);
		tforcamento.setColumns(10);

		JTextPane tfhistorico = new JTextPane();
		tfhistorico.setEditable(false);
		tfhistorico.setBounds(121, 262, 263, 99);
		contentPane.add(tfhistorico);

		JDateChooser dataEntrega = new JDateChooser();
		dataEntrega.getCalendarButton().setEnabled(false);
		dataEntrega.setBounds(121, 164, 150, 20);
		contentPane.add(dataEntrega);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setEnabled(false);
		Image imgSalvar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(imgSalvar));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

				String comando = "UPDATE tbl_pedido SET cliente_id = "
						+ p.getClienteId() + ", orcamento_id="
						+ tforcamento.getText() + ", pedido_dataentrega ='"
						+ dateFormat.format(dataEntrega.getDate())
						+ "', visita_id =" + p.getVisitaid()
						+ ", pedido_historico = ' " + tfhistorico.getText()
						+ "', pedido_materiaisencomendados='"
						+ tfmateriaisenc.getText() + "' where pedido_id ="
						+ p.getId() + ";";
				try {

					Class.forName("com.mysql.jdbc.Driver");

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					st.execute(comando);

					// fala q executou co sucesso
					JOptionPane.showMessageDialog(null,
							"Dados alterados com sucesso!");

					st.close(); // fecha a concxão c
					con.close();

					// Campos não editáveis.

					dispose();

				} catch (NumberFormatException erroNumero) {

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
					erroFormato.printStackTrace();
				}

				catch (ClassNotFoundException erroJDBC) {
					JOptionPane
							.showMessageDialog(
									null,
									"Falha no cadastro. \nPor favor, entre em contato com um técnico, ou tente novamente mais tarde.",
									"Aviso!", JOptionPane.WARNING_MESSAGE);
					erroJDBC.printStackTrace();
				} catch (IllegalArgumentException illegalArgument) {
					JOptionPane
							.showMessageDialog(
									null,
									"Algo deu errado.. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
									"Erro!", JOptionPane.ERROR_MESSAGE);
					illegalArgument.printStackTrace();
				} catch (Exception eArgument) {
					JOptionPane
							.showMessageDialog(
									null,
									"Algo deu errado. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
									"Erro!", JOptionPane.ERROR_MESSAGE);
					eArgument.printStackTrace();
				}
			}
		});
		btnSalvar.setBounds(0, 396, 124, 23);
		contentPane.add(btnSalvar);

		JButton btnFechar = new JButton("Fechar");
		btnFechar.setEnabled(false);
		Image imgFechar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnFechar.setIcon(new ImageIcon(imgFechar));
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				dispose();

			}
		});
		btnFechar.setBounds(270, 396, 124, 23);
		contentPane.add(btnFechar);

		JButton btnEncomendar = new JButton("Encomendar");
		btnEncomendar.setEnabled(false);
		Image imgMaterial = new ImageIcon(this.getClass().getResource(
				"/16px/materiais.png")).getImage();
		btnEncomendar.setIcon(new ImageIcon(imgMaterial));
		btnEncomendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String comando = "UPDATE tbl_pedido SET pedido_materiaisencomendados = 'Sim', pedido_nivel = 3 WHERE pedido_id = "
						+ p.getId() + ";";
				try {

					Class.forName("com.mysql.jdbc.Driver");

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					st.execute(comando);

					// fala q executou co sucesso
					JOptionPane.showMessageDialog(null,
							"Materiais encomendados com sucesso");

					st.close();
					con.close();

					dispose();

				} catch (NumberFormatException erroNumero) {

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
					erroFormato.printStackTrace();
				}

				catch (ClassNotFoundException erroJDBC) {
					JOptionPane
							.showMessageDialog(
									null,
									"Falha no cadastro. \nPor favor, entre em contato com um técnico, ou tente novamente mais tarde.",
									"Aviso!", JOptionPane.WARNING_MESSAGE);
					erroJDBC.printStackTrace();
				} catch (IllegalArgumentException illegalArgument) {
					JOptionPane
							.showMessageDialog(
									null,
									"Algo deu errado.. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
									"Erro!", JOptionPane.ERROR_MESSAGE);
					illegalArgument.printStackTrace();
				} catch (Exception eArgument) {
					JOptionPane
							.showMessageDialog(
									null,
									"Algo deu errado. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
									"Erro!", JOptionPane.ERROR_MESSAGE);
					eArgument.printStackTrace();
				}

			}
		});
		btnEncomendar.setBounds(272, 228, 114, 23);
		contentPane.add(btnEncomendar);

		JButton btnEfetuarPagamento = new JButton("Efetuar Pagamento");
		btnEfetuarPagamento.setEnabled(false);
		Image imgPagamento = new ImageIcon(this.getClass().getResource(
				"/16px/pagamento.png")).getImage();
		btnEfetuarPagamento.setIcon(new ImageIcon(imgPagamento));
		btnEfetuarPagamento.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {

				Pedido p = new Pedido();
				Orcamento o = new Orcamento();

				String comando = "select pedido_id from tbl_pedido where orcamento_id = "
						+ Integer.parseInt(tforcamento.getText());

				try {

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					while (resultSet.next()) {

						p.setId(resultSet.getInt("pedido_id"));

					}

					st.close();
					con.close();

				} // fim do try
				catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

				try {
					JFCPagamento frame = new JFCPagamento();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception erro) {
					erro.printStackTrace();
				}
			}
		});
		btnEfetuarPagamento.setBounds(0, 372, 394, 23);
		contentPane.add(btnEfetuarPagamento);

		JButton btnAlterar = new JButton("Alterar");
		Image imgAlterar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		btnAlterar.setIcon(new ImageIcon(imgAlterar));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tfhistorico.setEditable(true);
				btnFechar.setEnabled(true);
				btnSalvar.setEnabled(true);
				btnEfetuarPagamento.setEnabled(true);
				btnEncomendar.setEnabled(true);
				dataEntrega.setEnabled(true);

			}
		});
		btnAlterar.setBounds(134, 396, 124, 23);
		contentPane.add(btnAlterar);

		JLabel lblMaisInformaes = new JLabel("Mais informa\u00E7\u00F5es");
		lblMaisInformaes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				Cliente c = new Cliente();

				c.setNome(tfCliente.getText());

				SimpleDateFormat Formatodatabr = new SimpleDateFormat(
						"dd/MM/yyyy");

				String comando = "select cliente_id, cliente_nome, cliente_sobrenome, cliente_cpf, cliente_rg, "
						+ "cliente_numero, cliente_cep, cliente_endereco, cliente_bairro, cliente_complemento, "
						+ "cliente_telefone1 , cliente_telefone2 , cliente_datanasc, cliente_email from tbl_cliente where cliente_nome = '"
						+ c.getNome() + "';";

				try {

					// cria a conecxão
					Class.forName("com.mysql.jdbc.Driver");

					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					// não sei oq isso faz kk
					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					while (resultSet.next()) {
						c.setId(resultSet.getInt("cliente_id"));
						c.setNome(resultSet.getString("cliente_nome"));
						c.setSobrenome(resultSet.getString("cliente_sobrenome"));
						c.setCpf(resultSet.getString("cliente_cpf"));
						c.setRg(resultSet.getString("cliente_rg"));
						c.setNumero(resultSet.getString("cliente_numero"));
						c.setCep(resultSet.getString("cliente_cep"));
						c.setEndereco(resultSet.getString("cliente_endereco"));
						c.setBairro(resultSet.getString("cliente_bairro"));
						c.setComplemento(resultSet
								.getString("cliente_complemento"));
						c.setTelefone(resultSet.getString("cliente_telefone1"));
						c.setTelefone2(resultSet.getString("cliente_telefone2"));
						c.setDataNascimento(String.valueOf(Formatodatabr
								.format(java.sql.Date.valueOf(resultSet
										.getString("cliente_datanasc")))));
						c.setEmail(resultSet.getString("cliente_email"));

					}

					st.close();
					con.close();

				}// fim do try
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

				try {
					JFVisualizarCliente frame = new JFVisualizarCliente(c);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
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

			}
		});
		lblMaisInformaes.setBounds(292, 82, 112, 14);
		contentPane.add(lblMaisInformaes);

		JLabel label = new JLabel("Mais informa\u00E7\u00F5es");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				Orcamento o = new Orcamento();

				String comando = "select orcamento_detalesadicionais, orcamento_altura, orcamento_largura, orcamento_precopormetroquadrado,"
						+ " orcamento_metodoPagamento, orcamento_parcelamento, orcamento_juros, orcamento_valorTotal, orcamento_valorMaoDeObra,"
						+ " orcamento_listaMaterial, orcamento_listaqtd, orcamento_descricaoproduto, orcamento_detalesadicionais from tbl_orcamento where orcamento_id = "
						+ tforcamento.getText() + ";";

				o.setId(Integer.parseInt(tforcamento.getText()));

				try {

					// cria a conecxão

					Class.forName("com.mysql.jdbc.Driver");

					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					while (resultSet.next()) {

						o.setDetalesadicionais(resultSet
								.getString("orcamento_detalesadicionais"));
						o.setAltura(resultSet.getDouble("orcamento_altura"));
						o.setLargura(resultSet.getDouble("orcamento_largura"));
						o.setPrecopormetroquadrado(resultSet
								.getDouble("orcamento_precopormetroquadrado"));
						o.setFormaPagamento(resultSet
								.getString("orcamento_metodoPagamento"));
						o.setParcelamento(resultSet
								.getDouble("orcamento_parcelamento"));
						o.setJuros(resultSet.getDouble("orcamento_juros"));
						o.setValorTotal(resultSet
								.getDouble("orcamento_valorTotal"));
						o.setMaoDeObra(resultSet
								.getDouble("orcamento_valorMaoDeObra"));
						o.setListaMaterial(resultSet
								.getString("orcamento_listaMaterial"));
						o.setListaqtd(resultSet.getString("orcamento_listaqtd"));
						o.setDescricaoproduto(resultSet
								.getString("orcamento_descricaoproduto"));
						o.setDetalesadicionais(resultSet
								.getString("orcamento_detalesadicionais"));

					}

					st.close();
					con.close();

				}// fim do try
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

				try {
					JFVisualizarOrcamento viewOrcamento = new JFVisualizarOrcamento(
							o);
					viewOrcamento.setVisible(true);
					viewOrcamento.setLocationRelativeTo(null);

				} catch (Exception eee) {
					eee.printStackTrace();
				}

			}
		});
		label.setBounds(292, 110, 112, 14);
		contentPane.add(label);

		JLabel lblVisita = new JLabel("Visita:");
		lblVisita.setBounds(10, 138, 89, 14);
		contentPane.add(lblVisita);

		tfvisita = new JTextField();
		tfvisita.setEditable(false);
		tfvisita.setBounds(121, 133, 150, 20);
		contentPane.add(tfvisita);
		tfvisita.setColumns(10);

		JLabel label_1 = new JLabel("Mais informa\u00E7\u00F5es");
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				Visita visita = new Visita();

				visita.setNome(tfvisita.getText());

				SimpleDateFormat Formatodatabr = new SimpleDateFormat(
						"dd/MM/yyyy");

				String comando = "select visita_id, visita_data, visita_status, visita_horario, visita_bairro, visita_numero, visita_nome, "
						+ "visita_endereco, visita_telefone, visita_observacao from tbl_visita where visita_nome = '"
						+ visita.getNome() + "';";

				try {

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					while (resultSet.next()) {

						visita.setEndereco(resultSet
								.getString("visita_endereco"));

						visita.setHorarioVisita(resultSet
								.getString("visita_horario"));

						visita.setTelefone(resultSet
								.getString("visita_telefone"));

						visita.setObservacao(resultSet
								.getString("visita_observacao"));

						visita.setDataVisita(Formatodatabr.format(resultSet
								.getDate(("visita_data"))));

					}

					st.close();
					con.close();

				} // fim do try
				catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

				try {
					JFVisualizarVisita frame = new JFVisualizarVisita(visita);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception erro) {
					erro.printStackTrace();
				}

			}
		});
		label_1.setBounds(292, 138, 112, 14);
		contentPane.add(label_1);

		JLabel lblDataDeEntrega = new JLabel("Data de entrega:");
		lblDataDeEntrega.setBounds(10, 169, 89, 14);
		contentPane.add(lblDataDeEntrega);

		JLabel lblHistricoDoPedido = new JLabel("Hist\u00F3rico do pedido:");
		lblHistricoDoPedido.setBounds(10, 262, 112, 14);
		contentPane.add(lblHistricoDoPedido);

		JLabel lblGerado = new JLabel("Gerado:");
		lblGerado.setBounds(10, 204, 46, 14);
		contentPane.add(lblGerado);

		tfgerado = new JTextField();
		tfgerado.setEditable(false);
		tfgerado.setBounds(121, 199, 150, 20);
		contentPane.add(tfgerado);
		tfgerado.setColumns(10);

		// consulta o nome do clienjte o coloca os outros valores

		// formata a data para ficar igual no padrão brasileiro
		SimpleDateFormat Formatodatabr = new SimpleDateFormat("dd/MM/yyyy");

		String comando = "select cliente_nome from tbl_cliente where cliente_id = "
				+ p.getClienteId() + ";";

		try {

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				tfCliente.setText(resultSet.getString("cliente_nome"));

			}

			comando = "select visita_nome from tbl_visita where visita_id = "
					+ p.getVisitaid() + " ;";

			ResultSet resultSet2 = st.executeQuery(comando);

			while (resultSet2.next()) {

				tfvisita.setText(resultSet2.getString("visita_nome"));

			}

			st.close();
			con.close();

		} // fim do try
		catch (Exception ee) {
			ee.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}

		JLabel lblMateriaisEncomandados = new JLabel("Materiais enc.:");
		lblMateriaisEncomandados.setBounds(10, 232, 137, 14);
		contentPane.add(lblMateriaisEncomandados);

		tfmateriaisenc = new JTextField();
		tfmateriaisenc.setEditable(false);
		tfmateriaisenc.setBounds(121, 229, 150, 20);
		contentPane.add(tfmateriaisenc);
		tfmateriaisenc.setColumns(10);

		// da os valores ao campos

		tfhistorico.setText(p.getHistorico());

		tfgerado.setText(Formatodatabr.format(p.getDiagerado()));

		tforcamento.setText(String.valueOf(p.getOrcamentoId()));

		tfmateriaisenc.setText(p.getMateriaisencomendados());

		dataEntrega.setDate(p.getDataentrega());

		// tfdataencomanda.setText(Formatodatabr.format(p.getDiaencomendamateriais()));

	}
}
