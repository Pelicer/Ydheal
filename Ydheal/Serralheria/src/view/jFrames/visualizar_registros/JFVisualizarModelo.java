package view.jFrames.visualizar_registros;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.IllegalFormatException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.DAO.ConexaoMySQL;
import modelSuplerclasses.Modelo;
import view.jFrames.JFSelecaodeMaterial;

@SuppressWarnings("serial")
public class JFVisualizarModelo extends JFrame {

	private JPanel contentPane;
	private JTextField tfbome;
	private JTextField tfcustodosmateriais;
	private JTextField tfcustofinal;
	private JTextField tfdescricao;
	private JTable tablevisualizarmodelo;

	Modelo mo = new Modelo();

	DefaultTableModel modelolista = new DefaultTableModel();

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public JFVisualizarModelo(Modelo vm) {

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
				"/16px/modelo.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Visualizar Material");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 693);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);

		JComboBox cbcategoria = new JComboBox();
		cbcategoria.setEnabled(false);
		cbcategoria.setBounds(142, 434, 236, 20);
		contentPane.add(cbcategoria);

		JButton button = new JButton("Salvar");
		button.setBounds(10, 630, 124, 23);
		contentPane.add(button);

		JButton btnAlterar = new JButton("Alterar");

		btnAlterar.setBounds(131, 630, 134, 23);
		contentPane.add(btnAlterar);

		JButton btnFechar = new JButton("Fechar");
		Image imgFechar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnFechar.setIcon(new ImageIcon(imgFechar));
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnFechar.setBounds(264, 630, 114, 23);
		contentPane.add(btnFechar);

		JLabel label = new JLabel("Nome: ");
		label.setBounds(10, 90, 46, 14);
		contentPane.add(label);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 61, 424, 2);
		contentPane.add(separator);

		JLabel label_2 = new JLabel("Lista de material:");
		label_2.setBounds(10, 124, 110, 14);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("Custo dos materiais:");
		label_3.setBounds(10, 383, 124, 14);
		contentPane.add(label_3);

		JLabel label_4 = new JLabel("Descri\u00E7\u00E3o:");
		label_4.setBounds(10, 485, 75, 14);
		contentPane.add(label_4);

		JLabel label_6 = new JLabel("Categoria:");
		label_6.setBounds(10, 437, 124, 14);
		contentPane.add(label_6);

		tfbome = new JTextField();
		tfbome.setEditable(false);
		tfbome.setColumns(10);
		tfbome.setBounds(142, 87, 236, 20);
		contentPane.add(tfbome);

		JButton btnadicionar = new JButton("Adicionar");
		btnadicionar.setEnabled(false);

		JButton btntremover = new JButton("Remover");
		btntremover.setEnabled(false);

		JButton btnnovacatergoria = new JButton("Adicionar categoria");
		btnnovacatergoria.setEnabled(false);

		JButton btnlimpar = new JButton("Limpar");
		btnlimpar.setEnabled(false);

		Image imAlterar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		btnAlterar.setIcon(new ImageIcon(imAlterar));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tfbome.setEditable(true);
				tfcustodosmateriais.setEditable(true);
				cbcategoria.setEnabled(true);
				tfdescricao.setEditable(true);
				tablevisualizarmodelo.setEnabled(true);

				btnAlterar.setEnabled(true);
				btntremover.setEnabled(true);
				btnadicionar.setEnabled(true);
				btnlimpar.setEnabled(true);

				btnnovacatergoria.setEnabled(true);

			}
		});

		Image imgOk = new ImageIcon(this.getClass().getResource("/16px/ok.png"))
				.getImage();
		button.setIcon(new ImageIcon(imgOk));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Criando conexão com o banco.
				try {

					Class.forName("com.mysql.jdbc.Driver");

					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = null;

					st = con.createStatement();

					// Setando os valores corretos dentro do objeto.
					vm.setNomeModelo(tfbome.getText());

					vm.setCustoMaterial(Double.parseDouble(tfcustodosmateriais
							.getText()));

					vm.setDescricaoModelo(tfdescricao.getText());

					vm.setValorModelo(Double.parseDouble(tfcustofinal.getText()));

					vm.setCategoria(String.valueOf(cbcategoria
							.getSelectedItem()));

					// seleciona o id referente a categoria
					String comando = "select tbl_categoriamodelo.categoriamodelo_id from tbl_categoriamodelo where categoriamodelo_nome = '"
							+ vm.getCategoria() + "';";

					ResultSet resultSet = st.executeQuery(comando);

					int y = 0;

					while (resultSet.next()) {

						String id = resultSet.getString("categoriamodelo_id");

						y = Integer.parseInt(id);

					}

					resultSet.close();

					// crias as lista de material e nome
					int i = tablevisualizarmodelo.getRowCount();

					String lista[] = new String[i];
					double custo[] = new double[i];
					String qtd[] = new String[i];

					while (i != 0) {

						i--;

						lista[i] = String.valueOf(tablevisualizarmodelo
								.getValueAt(i, 0));
						custo[i] = Double.parseDouble(String
								.valueOf(tablevisualizarmodelo.getValueAt(i, 1)));

						qtd[i] = String.valueOf(tablevisualizarmodelo
								.getValueAt(i, 2));

					}
					// da para o abjeto modelo
					vm.Guardarlista(lista, custo, qtd);

					// coloca um item por linha
					String lista1 = "";

					i = vm.Pegarlista().length;

					while (i != 0) {
						i--;

						lista1 = (lista1 + vm.Pegarlista()[i] + "\n");

					}

					String z = "";

					i = vm.Pegarpreco().length;

					while (i != 0) {
						i--;

						z = (z + vm.Pegarpreco()[i] + "\n");

					}

					// coloca um item por linha
					String a = "";

					i = vm.Pegarqtd().length;

					while (i != 0) {
						i--;

						a = (a + vm.Pegarqtd()[i] + "\n");

					}

					comando = "UPDATE tbl_modelo " + "SET modelo_nome = '"
							+ vm.getNomeModelo() + "',"
							+ "modelo_listaMaterial = '" + lista1 + "',"
							+ "modelo_listaqtd = '" + a + "',"
							+ "modelo_listaCusto = '" + z + "',"
							+ "modelo_custo = " + vm.getValorModelo() + ","
							+ "modelo_descricao = '" + vm.getDescricaoModelo()
							+ "'," + "categoriamodelo_id = " + y + " "

							+ "WHERE modelo_id = " + vm.getId() + ";";

					st.execute(comando);
					// Material cadastrado.
					JOptionPane.showMessageDialog(null,
							"Modelo alterado com sucesso!!");

					st.close();

					// Fechando conexão.

					con.close();

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

				// desativa td dnv

				tfbome.setEditable(false);
				tfcustodosmateriais.setEditable(false);
				cbcategoria.setEnabled(false);

				tfdescricao.setEditable(false);
				tablevisualizarmodelo.setEnabled(false);

				btntremover.setEnabled(false);
				btnadicionar.setEnabled(false);
				btnlimpar.setEnabled(false);

				btnnovacatergoria.setEnabled(false);
			}
		});

		Image imgAdicionar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnadicionar.setIcon(new ImageIcon(imgAdicionar));
		btnadicionar.addActionListener(new ActionListener() {
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

					customaodeobra = (customaodeobra + (mo.Pegarpreco()[i])
							* Double.parseDouble(mo.Pegarqtd()[i]));

				}

				tfcustodosmateriais.setText(String.valueOf(customaodeobra));

				tfcustofinal.setText(String.valueOf(Double
						.parseDouble(tfcustodosmateriais.getText())));

			}
		});
		btnadicionar.setBounds(31, 324, 103, 23);
		contentPane.add(btnadicionar);

		Image imgRemover = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btntremover.setIcon(new ImageIcon(imgRemover));
		btntremover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tablevisualizarmodelo.getSelectedRow() > 1) {

					// retorna array contendo todos os indices selecionados na
					// lista

					tfcustodosmateriais.setText(String.valueOf(Double
							.parseDouble(tfcustodosmateriais.getText())
							- Double.parseDouble(String.valueOf(modelolista
									.getValueAt(tablevisualizarmodelo
											.getSelectedRow(), 1)))));

					int[] indexes = tablevisualizarmodelo.getSelectedRows();

					int i = indexes.length;

					while (i != 0) {

						i--;

						modelolista.removeRow(indexes[i]);

					}

				} else {

					// retorna array contendo todos os indices selecionados na
					// lista
					int index = tablevisualizarmodelo.getSelectedRow();

					modelolista.removeRow(index);
				}

				// descobri como contar os registros e pegar todos os itens da
				// lista

				// pega a quantidade de registros de lista
				int i = tablevisualizarmodelo.getRowCount();

				String lista[] = new String[i];

				String qtd[] = new String[i];

				double custo[] = new double[i];

				while (i != 0) {

					i--;

					lista[i] = String.valueOf(tablevisualizarmodelo.getValueAt(
							i, 0));

					qtd[i] = String.valueOf(tablevisualizarmodelo.getValueAt(i,
							2));

					custo[i] = Double.parseDouble(String
							.valueOf(tablevisualizarmodelo.getValueAt(i, 1)));

				}

				mo.Guardarlista(lista, custo, qtd);

			}
		});
		btntremover.setBounds(149, 324, 103, 23);
		contentPane.add(btntremover);

		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnlimpar.setIcon(new ImageIcon(imgLimpar));
		btnlimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// limpando todo o Jlist

				int i = tablevisualizarmodelo.getRowCount();

				while (i != 0) {

					i--;

					modelolista.removeRow(i);

				}

				tfcustodosmateriais.setText("0");
			}
		});
		btnlimpar.setBounds(264, 324, 103, 23);
		contentPane.add(btnlimpar);

		tfcustodosmateriais = new JTextField();
		tfcustodosmateriais.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				double customaodeobra = 0;

				int i = mo.Pegarlista().length;
				while (i != 0) {

					i--;

					modelolista.addRow(new Object[] { mo.Pegarlista()[i],
							mo.Pegarpreco()[i], mo.Pegarqtd()[i] });

					customaodeobra = (customaodeobra + (mo.Pegarpreco()[i])
							* Double.parseDouble(mo.Pegarqtd()[i]));

				}

				tfcustodosmateriais.setText(String.valueOf(customaodeobra));

				tfcustofinal.setText(String.valueOf(Double
						.parseDouble(tfcustodosmateriais.getText())));

			}
		});
		tfcustodosmateriais.setEditable(false);
		tfcustodosmateriais.setText("0");
		tfcustodosmateriais.setColumns(10);
		tfcustodosmateriais.setBounds(142, 380, 236, 20);
		contentPane.add(tfcustodosmateriais);

		JLabel label_7 = new JLabel("Custo final:");
		label_7.setBounds(10, 408, 75, 14);
		contentPane.add(label_7);

		tfcustofinal = new JTextField();
		tfcustofinal.setText("0");
		tfcustofinal.setEditable(false);
		tfcustofinal.setColumns(10);
		tfcustofinal.setBounds(142, 408, 236, 20);
		contentPane.add(tfcustofinal);

		btnnovacatergoria.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {
					Class.forName("com.mysql.jdbc.Driver");

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					String comando = "select categoriamodelo_nome from tbl_categoriamodelo;";

					String y = JOptionPane
							.showInputDialog("Digite o nome da nova gategoria: ");

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
		btnnovacatergoria.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnnovacatergoria.setBounds(142, 461, 236, 16);
		contentPane.add(btnnovacatergoria);

		tfdescricao = new JTextField();
		tfdescricao.setEditable(false);
		tfdescricao.setColumns(10);
		tfdescricao.setBounds(142, 485, 236, 107);
		contentPane.add(tfdescricao);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 149, 373, 151);
		contentPane.add(scrollPane);

		// da os valores aos tfs
		tfbome.setText(vm.getNomeModelo());
		tfdescricao.setText(vm.getDescricaoModelo());

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

		cbcategoria.setSelectedItem(vm.getCategoria());

		{
			modelolista.addColumn("Nome");
			modelolista.addColumn("Custo");
			modelolista.addColumn("QTD.");
		}

		tablevisualizarmodelo = new JTable(modelolista) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};
		tablevisualizarmodelo.setEnabled(false);

		// impede que as colunas sejam reordemadas
		tablevisualizarmodelo.getTableHeader().setReorderingAllowed(false);

		tablevisualizarmodelo.getColumnModel().getColumn(0)
				.setPreferredWidth(100);
		tablevisualizarmodelo.getColumnModel().getColumn(1)
				.setPreferredWidth(50);

		scrollPane.setViewportView(tablevisualizarmodelo);

		// transforma as linhas de itens e custos em linhas de tabela
		int i = 0;

		double x = 0;

		Scanner sc = new Scanner(vm.getListaCusto());

		Scanner sc4 = new Scanner(vm.getListaqtd());

		Scanner sc2 = new Scanner(vm.getListaMaterial());

		while ((sc4.hasNextLine()) && (sc2.hasNextLine()) && (sc.hasNextLine())) {
			i++;

			modelolista.addRow(new Object[] { sc2.nextLine(), sc.nextLine(),
					sc4.nextLine() });

		}

		Scanner sc3 = new Scanner(vm.getListaCusto());
		while (sc3.hasNextLine()) {

			x = (x + Double.parseDouble(sc3.nextLine()));

		}

		sc.close();
		sc2.close();
		sc3.close();
		sc4.close();

		tfcustodosmateriais.setText(String.valueOf(x));

		String nomedomodelo = vm.getNomeModelo();

		JLabel lblModelo = new JLabel("Modelo: " + nomedomodelo);
		// Adicionando imagem.
		Image imgModelo = new ImageIcon(this.getClass().getResource(
				"/50px/amostra.png")).getImage();
		lblModelo.setIcon(new ImageIcon(imgModelo));
		lblModelo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblModelo.setBounds(10, 0, 414, 63);
		contentPane.add(lblModelo);

		JLabel label_1 = new JLabel("R$");
		label_1.setBounds(113, 412, 29, 14);
		contentPane.add(label_1);

		JLabel label_5 = new JLabel("R$");
		label_5.setBounds(113, 383, 29, 14);
		contentPane.add(label_5);

	}
}
