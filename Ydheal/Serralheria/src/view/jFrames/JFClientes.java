package view.jFrames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.IllegalFormatException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTable.PrintMode;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Model.DAO.ConexaoMySQL;
import modelSuplerclasses.Cliente;
import view.jFrames.cadastros.JFCClientes;
import view.jFrames.visualizar_registros.JFVisualizarCliente;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@SuppressWarnings("serial")
public class JFClientes extends JFrame {

	int cont = 0;

	private DefaultTableModel ModeloJTCli = new DefaultTableModel();
	private JPanel contentPane;
	private JTextField tfcodigo;
	private JTable tabletbl_clientes;
	private JTextField tfnome;
	private JTextField tfendereco;
	private JTextField tfrg;
	private JTextField tfcpf;

	public JFClientes() {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				Carregar_tela_cliente();
			}
		});

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/cliente.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Clientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 992, 740);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setLayout(null);
		contentPane.add(panel);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(0, 0, 966, 88);
		panel.add(panel_2);

		JLabel label_6 = new JLabel("C\u00F3digo:");
		label_6.setBounds(207, 11, 55, 14);
		panel_2.add(label_6);

		tfcodigo = new JTextField();
		tfcodigo.setColumns(10);
		tfcodigo.setBounds(280, 8, 86, 20);
		panel_2.add(tfcodigo);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(376, 11, 46, 14);
		panel_2.add(lblNome);

		tfnome = new JTextField();
		tfnome.setBounds(414, 8, 202, 20);
		panel_2.add(tfnome);
		tfnome.setColumns(10);

		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setBounds(207, 45, 67, 14);
		panel_2.add(lblEndereo);

		tfendereco = new JTextField();
		tfendereco.setBounds(280, 42, 336, 20);
		panel_2.add(tfendereco);
		tfendereco.setColumns(10);

		JLabel lblClientes = new JLabel("Clientes");
		// Adicionando imagem.
		Image imgCliente = new ImageIcon(this.getClass().getResource(
				"/50px/cliente.png")).getImage();
		lblClientes.setIcon(new ImageIcon(imgCliente));
		lblClientes.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClientes.setBounds(10, 6, 343, 79);
		panel_2.add(lblClientes);

		JLabel lblRg = new JLabel("RG:");
		lblRg.setBounds(647, 11, 46, 14);
		panel_2.add(lblRg);

		tfrg = new JTextField();
		tfrg.setBounds(703, 8, 121, 20);
		panel_2.add(tfrg);
		tfrg.setColumns(10);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(647, 45, 46, 14);
		panel_2.add(lblCpf);

		tfcpf = new JTextField();
		tfcpf.setBounds(703, 42, 121, 20);
		panel_2.add(tfcpf);
		tfcpf.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 88, 966, 592);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JButton Novo = new JButton("Novo");
		// Adicionando imagem.
		Image imgNovo = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		Novo.setIcon(new ImageIcon(imgNovo));
		Novo.setBounds(108, 11, 116, 23);
		panel_1.add(Novo);
		Novo.setToolTipText("Criar Novo Registro");

		JButton Filtrar = new JButton("Filtrar");
		// Adicionando imagem.
		Image imgFiltrar = new ImageIcon(this.getClass().getResource(
				"/16px/search.png")).getImage();
		Filtrar.setIcon(new ImageIcon(imgFiltrar));
		Filtrar.setBounds(234, 11, 116, 23);
		panel_1.add(Filtrar);
		Filtrar.setToolTipText("Filtrar Registros");

		JButton Limpar = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		Limpar.setIcon(new ImageIcon(imgLimpar));
		Limpar.setBounds(360, 11, 116, 23);
		panel_1.add(Limpar);
		Limpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tfcodigo.setText("");
				tfcpf.setText("");
				tfendereco.setText("");
				tfnome.setText("");
				tfrg.setText("");
			}
		});
		Limpar.setToolTipText("Limpar Filtros");

		JButton Imprimir = new JButton("Imprimir");
		Imprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				MessageFormat header = new MessageFormat("Lista de Clientes");
				MessageFormat footer = new MessageFormat("Página {0}");

				try {
					boolean complete = tabletbl_clientes.print(
							PrintMode.FIT_WIDTH, header, footer, true, null,
							true);
					if (complete) {
						JOptionPane.showMessageDialog(null,
								"Impressão concluida!", "Concluído",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (PrinterException erroImpressao) {
					erroImpressao.printStackTrace();
					JOptionPane
							.showMessageDialog(
									null,
									"Houve uma falha ao imprimir. \nPor favor, contate um técnico ou tente novamente mais tarde.",
									"Erro!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		// Adicionando imagem.
		Image imgImprimir = new ImageIcon(this.getClass().getResource(
				"/16px/imprimir.png")).getImage();
		Imprimir.setIcon(new ImageIcon(imgImprimir));
		Imprimir.setBounds(486, 11, 116, 23);
		panel_1.add(Imprimir);
		Imprimir.setToolTipText("Imprimir");

		JButton Alterar = new JButton("Alterar");
		// Adicionando imagem.
		Image imgAlterar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		Alterar.setIcon(new ImageIcon(imgAlterar));
		Alterar.setBounds(612, 11, 116, 23);
		Alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Cliente c = new Cliente();

				SimpleDateFormat Formatodatabr = new SimpleDateFormat(
						"dd/MM/yyyy");

				String comando = "select cliente_id, cliente_nome, cliente_sobrenome, cliente_cpf, cliente_rg, "
						+ "cliente_numero, cliente_cep, cliente_endereco, cliente_bairro, cliente_complemento, "
						+ "cliente_telefone1 , cliente_telefone2 , cliente_datanasc, cliente_email from tbl_cliente where cliente_id = "
						+ ModeloJTCli.getValueAt(
								tabletbl_clientes.getSelectedRow(), 0) + ";";

				cont = 0;

				try {

					// cria a conecxão
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

				catch (NumberFormatException eee) {
					eee.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				} catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

				try {
					JFVisualizarCliente viewCliente = new JFVisualizarCliente(c);
					//viewCliente.setModal(true);
					viewCliente.setVisible(true);
					viewCliente.setLocationRelativeTo(null);

				} catch (Exception eee) {
					eee.printStackTrace();
				}
			}
		});
		panel_1.add(Alterar);
		Alterar.setToolTipText("Alterar Registro");

		JLabel lblregistrosencontrados = new JLabel("0");
		lblregistrosencontrados.setBounds(740, 45, 46, 14);
		panel_1.add(lblregistrosencontrados);
		lblregistrosencontrados.setHorizontalAlignment(SwingConstants.CENTER);
		lblregistrosencontrados.setForeground(new Color(0, 128, 0));

		Novo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					JFCClientes frame = new JFCClientes();
					frame.setModal(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);

				} catch (Exception error) {
					error.printStackTrace();
				}

				while (tabletbl_clientes.getModel().getRowCount() > 0) {
					((DefaultTableModel) tabletbl_clientes.getModel())
							.removeRow(0);
				}

				Carregar_tela_cliente();

				lblregistrosencontrados.setText(String.valueOf(cont));

			}
		});

		JButton Excluir = new JButton("Excluir");
		// Adicionando imagem.
		Image imgExcluir = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		Excluir.setIcon(new ImageIcon(imgExcluir));
		Excluir.setBounds(738, 11, 116, 23);
		panel_1.add(Excluir);
		Excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// pede para confirmar a resposta
				int resposta = JOptionPane
						.showConfirmDialog(
								null,
								"Deseja realmente excluir os clientes selecionados?"
										+ "\nNão será possível excluí-lo caso exista pendências do mesmo.",
								"Confirmar exclusões ",
								JOptionPane.YES_NO_OPTION);

				// chaca resposta e faz o de acordo
				if (resposta == JOptionPane.YES_OPTION) {

					// pega as linhas selecionadas
					int x[] = tabletbl_clientes.getSelectedRows();

					// exclui as linhas selecionadas

					int id = 0;

					for (int i = x.length - 1; i >= 0; i--) {

						// obtem o id da linha selecionada
						id = Integer.parseInt(String.valueOf(ModeloJTCli
								.getValueAt(x[i], 0)));

						ModeloJTCli.removeRow(x[i]);

						// Comando para excluir cliente.
						String comandoCliente = "DELETE FROM tbl_cliente WHERE cliente_id = "
								+ id + ";";

						try {

							Class.forName("com.mysql.jdbc.Driver");

							// cria a conecxão
							java.sql.Connection con = ConexaoMySQL
									.getConexaoMySQL();

							java.sql.Statement st = con.createStatement();

							// Excluindo cliente...
							st.execute(comandoCliente);

							st.close();
							con.close();

							JOptionPane.showMessageDialog(null,
									"Cliente excluído com sucesso.");

						}// fim do try
						catch (Exception ee) {
							ee.printStackTrace();
							JOptionPane
									.showMessageDialog(
											null,
											"Falha na exclusão. Isso significa que existem pedidos pendentes deste cliente."
													+ "\nPor favor, exclua as pendências antes de excluir o cliente.",
											"AVISO!",
											JOptionPane.WARNING_MESSAGE);
						}

						lblregistrosencontrados.setText(String.valueOf(Integer
								.parseInt(lblregistrosencontrados.getText()) - 1));

					}// fecha o for de exclusão

				} else if (resposta == JOptionPane.NO_OPTION) {
					// Usuário clicou em não. Executar o código correspondente.

				}

			}
		});
		Excluir.setToolTipText("Excluir Registro");

		JLabel label = new JLabel("Registros Encontrados");
		label.setBounds(793, 45, 141, 14);
		panel_1.add(label);

		Filtrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// ifs com um campo

				if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente;";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText() + ";";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_cpf like '"
							+ tfcpf.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_endereco like '%"
							+ tfendereco.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_nome like '%"
							+ tfnome.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_rg like '"
							+ tfrg.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				// fim dos ifs com um campo

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ " and cliente_nome like '%"
							+ tfnome.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ " and cliente_rg like '"
							+ tfrg.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ " and cliente_endereco like '%"
							+ tfendereco.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ " and cliente_cpf like '"
							+ tfcpf.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_rg like '"
							+ tfrg.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_endereco like '%"
							+ tfendereco.getText() + "%' ;";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_cpf like '"
							+ tfcpf.getText() + "%' ;";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_cpf like '"
							+ tfcpf.getText() + "%' ;";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_rg like '"
							+ tfrg.getText()
							+ "%' and cliente_nome like '%"
							+ tfnome.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_rg like '"
							+ tfrg.getText()
							+ "%' and cliente_endereco like '%"
							+ tfendereco.getText() + "%' ;";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_rg like '"
							+ tfrg.getText()
							+ "%'  and cliente_cpf like '"
							+ tfcpf.getText() + "%' ;";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_cpf like '"
							+ tfcpf.getText()
							+ "%'  and cliente_endereco like '%"
							+ tfendereco.getText() + "%'  ;";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ " and cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_rg like '"
							+ tfrg.getText() + "%';";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_cpf like '"
							+ tfcpf.getText()
							+ "%' and cliente_endereco like '%"
							+ tfendereco.getText() + "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_endereco like '%"
							+ tfendereco.getText()
							+ "%' and cliente_id = "
							+ tfcodigo.getText()
							+ "  and cliente_rg like '"
							+ tfrg.getText() + "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_rg like '"
							+ tfrg.getText()
							+ "%' and cliente_cpf like '"
							+ tfcpf.getText()
							+ "%' and cliente_nome like '%"
							+ tfnome.getText() + "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_rg like '"
							+ tfrg.getText()
							+ "%'  and cliente_endereco like '%"
							+ tfendereco.getText()
							+ "%'  and cliente_nome like '%"
							+ tfnome.getText()
							+ "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ "  and cliente_endereco like '%"
							+ tfendereco.getText()
							+ "%'  and cliente_nome like '%"
							+ tfnome.getText()
							+ "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ "  and cliente_cpf like '"
							+ tfcpf.getText()
							+ "%'  and cliente_nome like '%"
							+ tfnome.getText() + "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_id = "
							+ tfcodigo.getText()
							+ "  and cliente_rg like '"
							+ tfrg.getText()
							+ "%'   and cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_endereco like '%"
							+ tfendereco.getText() + "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_cpf like '"
							+ tfcpf.getText()
							+ "%' and cliente_rg like '"
							+ tfrg.getText()
							+ "%'   and cliente_nome like '%"
							+ tfnome.getText()
							+ "%' and cliente_endereco like '%"
							+ tfendereco.getText() + "%'; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (tfendereco.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_cpf like '"
							+ tfcpf.getText()
							+ "%' and cliente_rg like '"
							+ tfrg.getText()
							+ "%'   and cliente_nome like '%"
							+ tfnome.getText()
							+ "%'  and cliente_id = "
							+ tfcodigo.getText() + " ; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_cpf like '"
							+ tfcpf.getText()
							+ "%' and cliente_rg like '"
							+ tfrg.getText()
							+ "%'    and cliente_endereco like '%"
							+ tfendereco.getText()
							+ "%' and cliente_id = "
							+ tfcodigo.getText() + " ; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& !(tfnome.getText().equals(""))
						&& (tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_cpf like '"
							+ tfcpf.getText()
							+ "%' and cliente_nome like '%"
							+ tfnome.getText()
							+ "%'  and cliente_endereco like '%"
							+ tfendereco.getText()
							+ "%' and cliente_id = "
							+ tfcodigo.getText() + " ; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfcpf.getText().equals(""))
						&& (!tfendereco.getText().equals(""))
						&& !(tfnome.getText().equals(""))
						&& (!tfrg.getText().equals(""))) {

					while (tabletbl_clientes.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_clientes.getModel())
								.removeRow(0);
					}

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
							+ "cliente_numero, cliente_endereco, "
							+ "cliente_telefone1, cliente_numero,"
							+ " cliente_email from tbl_cliente where cliente_cpf like '"
							+ tfcpf.getText()
							+ "%' and cliente_nome like '%"
							+ tfnome.getText()
							+ "%'  and cliente_endereco like '%"
							+ tfendereco.getText()
							+ "%' and cliente_id = "
							+ tfcodigo.getText()
							+ "  and cliente_rg like '"
							+ tfrg.getText() + "%' ; ";

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(cont));

				}

			}// fim do evento

		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 946, 511);
		panel_1.add(scrollPane);

		tabletbl_clientes = new JTable(ModeloJTCli) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};
		scrollPane.setViewportView(tabletbl_clientes);

		// cria os titulos

		{
			ModeloJTCli.addColumn("Cod.");
			ModeloJTCli.addColumn("Nome.");
			ModeloJTCli.addColumn("Endereço");
			ModeloJTCli.addColumn("Número");
			ModeloJTCli.addColumn("Telefone");
			ModeloJTCli.addColumn("E-mail");
		}

		tabletbl_clientes = new JTable(ModeloJTCli) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		// no fim da moddificação da célula editavel, vem a deteqção de evento
		// de click dublo em um registro da jtable.
		tabletbl_clientes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					Cliente c = new Cliente();

					SimpleDateFormat Formatodatabr = new SimpleDateFormat(
							"dd/MM/yyyy");

					String comando = "select cliente_id, cliente_nome, cliente_sobrenome, cliente_cpf, cliente_rg, "
							+ "cliente_numero, cliente_cep, cliente_endereco, cliente_bairro, cliente_complemento, "
							+ "cliente_telefone1 , cliente_telefone2 , cliente_datanasc, cliente_email from tbl_cliente where cliente_id = "
							+ ModeloJTCli.getValueAt(
									tabletbl_clientes.getSelectedRow(), 0)
							+ ";";

					cont = 0;

					try {

						Class.forName("com.mysql.jdbc.Driver");

						// cria a conecxão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						// não sei oq isso faz kk
						java.sql.Statement st = con.createStatement();

						ResultSet resultSet = st.executeQuery(comando);

						while (resultSet.next()) {

							c.setId(resultSet.getInt("cliente_id"));
							c.setNome(resultSet.getString("cliente_nome"));
							c.setSobrenome(resultSet
									.getString("cliente_sobrenome"));
							c.setCpf(resultSet.getString("cliente_cpf"));
							c.setRg(resultSet.getString("cliente_rg"));
							c.setNumero(resultSet.getString("cliente_numero"));
							c.setCep(resultSet.getString("cliente_cep"));
							c.setEndereco(resultSet
									.getString("cliente_endereco"));
							c.setBairro(resultSet.getString("cliente_bairro"));
							c.setComplemento(resultSet
									.getString("cliente_complemento"));
							c.setTelefone(resultSet
									.getString("cliente_telefone1"));
							c.setTelefone2(resultSet
									.getString("cliente_telefone2"));
							c.setDataNascimento(String.valueOf(Formatodatabr
									.format(java.sql.Date.valueOf(resultSet
											.getString("cliente_datanasc")))));
							c.setEmail(resultSet.getString("cliente_email"));
						}

						st.close();
						con.close();

					}// fim do try
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
					} catch (IllegalArgumentException illegalArgument) {
						JOptionPane
								.showMessageDialog(
										null,
										"Algo deu errado.. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
										"Erro!", JOptionPane.ERROR_MESSAGE);
					} catch (Exception eArgument) {
						JOptionPane
								.showMessageDialog(
										null,
										"Algo deu errado.. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
										"Erro!", JOptionPane.ERROR_MESSAGE);
					}

					try {
						JFVisualizarCliente viewCliente = new JFVisualizarCliente(
								c);
						viewCliente.setVisible(true);
						viewCliente.setLocationRelativeTo(null);

					} catch (Exception eee) {
						eee.printStackTrace();
					}

				}
			}
		});

		// da os tamanhos para as colunas
		tabletbl_clientes.getColumnModel().getColumn(0).setPreferredWidth(1);
		tabletbl_clientes.getColumnModel().getColumn(1).setPreferredWidth(1);
		tabletbl_clientes.getColumnModel().getColumn(2).setPreferredWidth(100);
		tabletbl_clientes.getColumnModel().getColumn(3).setPreferredWidth(1);
		tabletbl_clientes.getColumnModel().getColumn(4).setPreferredWidth(1);
		tabletbl_clientes.getColumnModel().getColumn(5).setPreferredWidth(10);

		// carrega a tela ao abrir

		Carregar_tela_cliente();

		lblregistrosencontrados.setText(String.valueOf(cont));

	}// fim da classe da tela

	public void Cod(String comando) {

		cont = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String id = resultSet.getString("cliente_id");
				String nome = resultSet.getString("cliente_nome");
				String sobrenome = resultSet.getString("cliente_sobrenome");
				String numero = resultSet.getString("cliente_numero");
				String endereco = resultSet.getString("cliente_endereco");
				String telefone1 = resultSet.getString("cliente_telefone1");
				String email = resultSet.getString("cliente_email");
				nome = (nome + " " + sobrenome);

				cont = (cont + 1);
				ModeloJTCli.addRow(new Object[] { id, nome, endereco, numero,
						telefone1, email });

			}

			st.close();
			con.close();

		}// fim do try
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}
	}

	public void Carregar_tela_cliente() {

		String comando = "select cliente_id, cliente_nome, cliente_sobrenome, "
				+ "cliente_numero, cliente_endereco, "
				+ "cliente_telefone1, cliente_numero,"
				+ " cliente_email from tbl_cliente;";

		cont = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String id = resultSet.getString("cliente_id");
				String nome = resultSet.getString("cliente_nome");
				String sobrenome = resultSet.getString("cliente_sobrenome");
				String numero = resultSet.getString("cliente_numero");
				String endereco = resultSet.getString("cliente_endereco");
				String telefone1 = resultSet.getString("cliente_telefone1");
				String email = resultSet.getString("cliente_email");
				nome = (nome + " " + sobrenome);

				cont = (cont + 1);
				ModeloJTCli.addRow(new Object[] { id, nome, endereco, numero,
						telefone1, email });

			}

			st.close();
			con.close();

		}// fim do try
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}

	}

}
