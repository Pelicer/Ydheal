package view.jFrames.cadastros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.IllegalFormatException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.DAO.ConexaoMySQL;
import modelSuplerclasses.Modelo;
import view.jFrames.JFSelecaodeMaterial;

@SuppressWarnings("serial")
public class JFCModelo extends JDialog {

	private JPanel contentPane;
	private JTextField tfnome;
	private JTextField tfcustomateriais;

	DefaultTableModel modelolista = new DefaultTableModel();

	Modelo mo = new Modelo();
	private JTable tablelista;
	private JTextField tfDescricao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JFCModelo() {

		// Verificação ao fechar janela.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				int resposta = JOptionPane
						.showConfirmDialog(
								null,
								"O cadastro do modelo será cancelado. Tem certeza que deseja continuar?",
								"Aviso", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					dispose();
				} else if (resposta == JOptionPane.NO_OPTION) {
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// cancel
				}

			}
		});

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/modelo.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Cadastro de Modelo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 409, 651);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox cbcategoria = new JComboBox();
		cbcategoria.addItem("Selecione...");
		cbcategoria.setBounds(142, 408, 236, 20);
		contentPane.add(cbcategoria);

		JButton btnSalvar = new JButton("Salvar");
		// Adicionando imagem.
		Image imgSalvar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(imgSalvar));
		btnSalvar.setBounds(10, 577, 103, 23);
		contentPane.add(btnSalvar);

		JButton btnLimpar = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(imgLimpar));
		btnLimpar.setBounds(123, 577, 142, 23);
		contentPane.add(btnLimpar);

		JButton btnCancelar = new JButton("Cancelar");
		// Adicionando imagem.
		Image imgCancelar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnCancelar.setIcon(new ImageIcon(imgCancelar));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();
			}
		});
		btnCancelar.setBounds(275, 577, 103, 23);
		contentPane.add(btnCancelar);

		JLabel lblNome = new JLabel("Nome: ");
		lblNome.setBounds(10, 90, 46, 14);
		contentPane.add(lblNome);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 61, 424, 2);
		contentPane.add(separator);

		JLabel lblCadastroDeModelo = new JLabel("Cadastro de Modelo");
		// Adicionando imagem.
		Image imgModelo = new ImageIcon(this.getClass().getResource(
				"/50px/amostra.png")).getImage();
		lblCadastroDeModelo.setIcon(new ImageIcon(imgModelo));
		lblCadastroDeModelo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCadastroDeModelo.setBounds(10, 0, 414, 63);
		contentPane.add(lblCadastroDeModelo);

		JLabel lblListaDeMaterial = new JLabel("Lista de material:");
		lblListaDeMaterial.setBounds(10, 124, 110, 14);
		contentPane.add(lblListaDeMaterial);

		JLabel lblCusto = new JLabel("Custo dos materiais:");
		lblCusto.setBounds(10, 383, 124, 14);
		contentPane.add(lblCusto);

		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio.setBounds(10, 459, 75, 14);
		contentPane.add(lblDescrio);

		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(10, 411, 124, 14);
		contentPane.add(lblCategoria);

		tfnome = new JTextField();
		tfnome.setBounds(142, 87, 236, 20);
		contentPane.add(tfnome);
		tfnome.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 149, 373, 151);
		contentPane.add(scrollPane);

		{
			modelolista.addColumn("Nome");
			modelolista.addColumn("Custo");
			modelolista.addColumn("QTD");
		}

		tablelista = new JTable(modelolista) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		// impede que as colunas sejam reordemadas
		tablelista.getTableHeader().setReorderingAllowed(false);

		tablelista.getColumnModel().getColumn(0).setPreferredWidth(100);
		tablelista.getColumnModel().getColumn(1).setPreferredWidth(50);

		scrollPane.setViewportView(tablelista);
		setLocationRelativeTo(null);

		JButton btnAdicionar = new JButton("Adicionar");
		// Adicionar imagem.
		Image imgAdicionar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnAdicionar.setIcon(new ImageIcon(imgAdicionar));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					JFSelecaodeMaterial frame = new JFSelecaodeMaterial(mo);
					frame.setModal(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);

				} catch (Exception erro) {
					erro.printStackTrace();
				}

				int i = mo.Pegarlista().length;

				double customaodeobra = 0;

				while (i != 0) {

					i--;

					modelolista.addRow(new Object[] { mo.Pegarlista()[i],
							mo.Pegarpreco()[i], mo.Pegarqtd()[i] });

					customaodeobra = (customaodeobra + (mo.Pegarpreco()[i])
							* Double.parseDouble(mo.Pegarqtd()[i]));

				}

				tfcustomateriais.setText(String.valueOf(customaodeobra));

			}
		});
		btnAdicionar.setBounds(10, 324, 103, 23);
		contentPane.add(btnAdicionar);

		JButton btnRemover = new JButton("Remover");
		// Adicionando imagem.
		Image imgRemover = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnRemover.setIcon(new ImageIcon(imgRemover));
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (tablelista.getSelectedRow() > 1) {

					// retorna array contendo todos os indices selecionados na
					// lista
					int[] indexes = tablelista.getSelectedRows();

					int i = indexes.length;

					while (i != 0) {

						i--;

						modelolista.removeRow(indexes[i]);

					}

				} else {

					// retorna array contendo todos os indices selecionados na
					// lista
					int index = tablelista.getSelectedRow();

					modelolista.removeRow(index);
				}

				// descobri como contar os registros e pegar todos os itens da
				// lista

				// pega a quantidade de registros de lista
				int i = tablelista.getRowCount();

				String lista[] = new String[i];

				String qtd[] = new String[i];

				double custo[] = new double[i];

				while (i != 0) {

					i--;

					lista[i] = String.valueOf(tablelista.getValueAt(i, 0));

					qtd[i] = String.valueOf(tablelista.getValueAt(i, 2));

					custo[i] = Double.parseDouble(String.valueOf(tablelista
							.getValueAt(i, 1)));

				}

				mo.Guardarlista(lista, custo, qtd);

			}
		});
		btnRemover.setBounds(275, 324, 103, 23);
		contentPane.add(btnRemover);

		JButton btnLimpar_1 = new JButton("Limpar");
		// Adicionar imagem.
		Image imgLimpar2 = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar_1.setIcon(new ImageIcon(imgLimpar2));
		btnLimpar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// limpando todo o Jlist

				int i = tablelista.getRowCount();

				while (i != 0) {

					i--;

					modelolista.removeRow(i);

				}

			}
		});
		btnLimpar_1.setBounds(123, 324, 142, 23);
		contentPane.add(btnLimpar_1);

		JLabel lblR = new JLabel("R$");
		lblR.setHorizontalAlignment(SwingConstants.LEFT);
		lblR.setBounds(142, 383, 59, 14);
		contentPane.add(lblR);

		tfcustomateriais = new JTextField();
		tfcustomateriais.setHorizontalAlignment(SwingConstants.LEFT);
		tfcustomateriais.setEditable(false);
		tfcustomateriais.setText("0");
		tfcustomateriais.setBounds(165, 380, 213, 20);
		contentPane.add(tfcustomateriais);
		tfcustomateriais.setColumns(10);

		// carrega as categorias

		String comando = "select categoriamodelo_nome from tbl_categoriamodelo;";

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// Criando conexão.
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			cbcategoria.removeAllItems();

			while (resultSet.next()) {

				String nome = resultSet.getString("categoriamodelo_nome");

				cbcategoria.addItem(nome);

			}

			st.close();
			con.close();

		}// fim do try
		catch (Exception eee) {
			eee.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}

		JButton button = new JButton("Adicionar categoria");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					String y = JOptionPane
							.showInputDialog("Digite o nome da nova gategoria: ");

					if (y.equals("") || y.equals(" ") || y.equals(null)) {

					} else {

						// cria a conecxão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando = "select categoriamodelo_nome from tbl_categoriamodelo;";

						ResultSet resultSet = st.executeQuery(comando);

						while (resultSet.next()) {

							String nome = resultSet
									.getString("categoriamodelo_nome");

							if (nome.equals(y)) {
								JOptionPane.showMessageDialog(null,
										"Já existe essa categoria");

								break;
							} else {
								st.execute("insert into tbl_categoriamodelo set categoriamodelo_nome ='"
										+ y + "';");

								// fala q executou co sucesso
								JOptionPane.showMessageDialog(null,
										"Categoria cadastrada com sucesso!");

								break;
							}

						}

						resultSet.close();

						st.close();
						// fecha a concxão
						con.close();
					}
				}

				catch (Exception ee) {

					JOptionPane.showMessageDialog(null, "Falha no cadastro");

					ee.printStackTrace();
				}

				// carrega as categorias

				String comando = "select categoriamodelo_nome from tbl_categoriamodelo;";

				try {

					Class.forName("com.mysql.jdbc.Driver");

					// Criando conexão.
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					cbcategoria.removeAllItems();

					while (resultSet.next()) {

						String nome = resultSet
								.getString("categoriamodelo_nome");

						cbcategoria.addItem(nome);

					}

					st.close();
					con.close();

				}// fim do try
				catch (Exception eee) {
					eee.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 9));
		button.setBounds(142, 435, 236, 16);
		contentPane.add(button);

		tfDescricao = new JTextField();
		tfDescricao.setBounds(142, 459, 236, 107);
		contentPane.add(tfDescricao);
		tfDescricao.setColumns(10);

		JLabel label = new JLabel("*");
		label.setForeground(Color.RED);
		label.setBackground(Color.RED);
		label.setBounds(123, 90, 25, 14);
		contentPane.add(label);

		JLabel label_1 = new JLabel("*");
		label_1.setForeground(Color.RED);
		label_1.setBackground(Color.RED);
		label_1.setBounds(123, 383, 25, 14);
		contentPane.add(label_1);

		JLabel label_3 = new JLabel("*");
		label_3.setForeground(Color.RED);
		label_3.setBackground(Color.RED);
		label_3.setBounds(123, 411, 25, 14);
		contentPane.add(label_3);

		JLabel lblCampos = new JLabel("* - Campos Obrigat\u00F3rios.");
		lblCampos.setForeground(Color.RED);
		lblCampos.setBackground(Color.RED);
		lblCampos.setBounds(123, 45, 255, 14);
		contentPane.add(lblCampos);

		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				mo.setValorModelo(Double.parseDouble(tfcustomateriais.getText()));

				if (tfnome.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"AVISO!", JOptionPane.WARNING_MESSAGE);

				} else if (tfcustomateriais.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"AVISO!", JOptionPane.WARNING_MESSAGE);

				} else if (cbcategoria.getSelectedItem() == "Selecione...") {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"AVISO!", JOptionPane.WARNING_MESSAGE);

				} else {
					// Criando conexão com o banco.
					try {

						Class.forName("com.mysql.jdbc.Driver");

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = null;

						st = con.createStatement();

						// Setando os valores corretos dentro do objeto.
						mo.setNomeModelo(tfnome.getText());

						mo.setDescricaoModelo(tfDescricao.getText());

						mo.setCategoria(String.valueOf(cbcategoria
								.getSelectedItem()));

						mo.setValorModelo(Double.parseDouble(tfcustomateriais
								.getText()));

						String comando = "select tbl_categoriamodelo.categoriamodelo_id from tbl_categoriamodelo where categoriamodelo_nome = '"
								+ mo.getCategoria() + "';";

						ResultSet resultSet = st.executeQuery(comando);

						int y = 0;

						while (resultSet.next()) {

							String id = resultSet
									.getString("categoriamodelo_id");

							y = Integer.parseInt(id);

						}

						resultSet.close();

						String lista = "";

						int i = mo.Pegarlista().length;

						while (i != 0) {
							i--;

							lista = (lista + mo.Pegarlista()[i] + "\n");

						}

						String a = "";

						i = mo.Pegarqtd().length;

						while (i != 0) {
							i--;

							a = (a + mo.Pegarqtd()[i] + "\n");

						}

						String x = "";

						i = mo.Pegarpreco().length;

						double preco = 0;

						while (i != 0) {
							i--;

							preco = (preco + mo.Pegarpreco()[i]);

							x = (x + mo.Pegarpreco()[i] + "\n");

						}

						String comando2 = "insert into tbl_modelo(modelo_nome, modelo_listaMaterial, modelo_custo, modelo_listaqtd, modelo_listacusto, modelo_descricao, categoriamodelo_id) values('"
								+ mo.getNomeModelo()
								+ "', '"
								+ lista
								+ "', "
								+ preco
								+ ", '"
								+ a
								+ "', '"
								+ x
								+ "', '"
								+ mo.getDescricaoModelo() + "', " + y + ");";

						st.execute(comando2);
						// Material cadastrado.
						JOptionPane.showMessageDialog(null,
								"Modelo cadastrado com sucesso!!");

						st.close();

						// Fechando conexão.

						con.close();

						// Fechando o JFrame de cadastro.
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

		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				tfnome.setText("");
				tfDescricao.setText("");
				cbcategoria.setSelectedIndex(0);
				tfnome.grabFocus();

			}
		});

	}
}
