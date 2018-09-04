package view.jFrames.cadastros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.Normalizer.Form;
import java.util.IllegalFormatException;
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
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

import modelSuplerclasses.Materiais;
import modelSuplerclasses.Modelo;
import modelSuplerclasses.Orcamento;
import modelSuplerclasses.Visita;
import view.jDialogs.JDSelecionarCodigodeVisita;
import view.jFrames.visualizar_registros.JFVisualizarMaterial;
import Model.DAO.ConexaoMySQL;

import javax.swing.ListSelectionModel;

import org.joda.time.DateTime;

@SuppressWarnings("serial")
public class JFCOrcamento_materiais extends JDialog {

	Orcamento o = new Orcamento();
	Modelo m = new Modelo();

	private JPanel contentPane;
	DefaultTableModel modeloJtable = new DefaultTableModel();
	DefaultTableModel modeloJtablemodelo = new DefaultTableModel();
	DefaultTableModel modeloJtablematerial = new DefaultTableModel();

	private JTable tablelistademateriais;
	private JTextField tfmaterialbusca;
	private JTextField tfmodelobusca;
	private JTextField tfVisita;
	private JTable table_modelo;
	private JTable table_material;
	private JTextField tflargura;
	private JTextField tfaltura;
	private JTextField tfarea;

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public JFCOrcamento_materiais(Visita v) {

		// Verificação ao fechar janela.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				int resposta = JOptionPane
						.showConfirmDialog(
								null,
								"O cadastro do orçamento será cancelado. Tem certeza que deseja continuar?",
								"Aviso", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					dispose();
				} else if (resposta == JOptionPane.NO_OPTION) {
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// cancel
				}

			}
		});

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/orcamento.png")).getImage();
		setIconImage(imgIcon);

		setResizable(false);
		setTitle("Cadastro de Or\u00E7amento");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1070, 729);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JComboBox cbcategoriamodelo = new JComboBox();
		cbcategoriamodelo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				// limpa a tabela
				while (table_modelo.getModel().getRowCount() > 0) {
					((DefaultTableModel) table_modelo.getModel()).removeRow(0);
				}

				if (cbcategoriamodelo.getSelectedIndex() == 0) {

					try {

						String comando = "Select categoriamodelo_id from tbl_categoriamodelo where categoriamodelo_nome = '"
								+ cbcategoriamodelo.getSelectedItem() + "'; ";

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando2 = "select modelo_nome from tbl_modelo;";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							String nome = resultSet2.getString("modelo_nome");
							modeloJtablemodelo.addRow(new Object[] { nome });
						}

						st.close();
						con.close();

					} catch (Exception erroBuscaModelo) {
						erroBuscaModelo.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					}

				} else {

					try {

						String comando = "Select categoriamodelo_id from tbl_categoriamodelo where categoriamodelo_nome = '"
								+ cbcategoriamodelo.getSelectedItem() + "'; ";

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						ResultSet resultSet = st.executeQuery(comando);

						int id = 0;

						while (resultSet.next()) {

							id = resultSet.getInt("categoriamodelo_id");

						}

						String comando2 = "select modelo_nome from tbl_modelo inner join tbl_categoriamodelo on tbl_modelo.categoriamodelo_id = "
								+ id
								+ " and modelo_nome like '"
								+ tfmodelobusca.getText()
								+ "%' group by modelo_id;";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							String nome = resultSet2.getString("modelo_nome");
							modeloJtablemodelo.addRow(new Object[] { nome });
						}

						st.close();
						con.close();

					} catch (Exception erroBuscaModelo) {
						erroBuscaModelo.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					}
				}

			}
		});
		JComboBox cbcategoriamaterial = new JComboBox();
		cbcategoriamaterial.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				while (table_material.getModel().getRowCount() > 0) {
					((DefaultTableModel) table_material.getModel())
							.removeRow(0);
				}

				if (cbcategoriamodelo.getSelectedIndex() == 0) {

					try {

						String comando = "Select categoriamaterial_id from tbl_categoriamaterial where categoriamaterial_nome = '"
								+ cbcategoriamaterial.getSelectedItem() + "'; ";

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando2 = "select material_nome from tbl_material;";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							String nome = resultSet2.getString("material_nome");
							modeloJtablematerial.addRow(new Object[] { nome });
						}

						st.close();
						con.close();

					} catch (Exception erroBuscaModelo) {
						erroBuscaModelo.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					}

				} else {

					try {

						String comando = "Select categoriamaterial_id from tbl_categoriamaterial where categoriamaterial_nome = '"
								+ cbcategoriamaterial.getSelectedItem() + "'; ";

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						ResultSet resultSet = st.executeQuery(comando);

						int id = 0;

						while (resultSet.next()) {

							id = resultSet.getInt("categoriamaterial_id");

						}

						String comando2 = "select material_nome from tbl_material inner join tbl_categoriamaterial on tbl_material.categoriamaterial_id = "
								+ id
								+ " and material_nome like '"
								+ tfmaterialbusca.getText()
								+ "%'group by material_id ;";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							String nome = resultSet2.getString("material_nome");
							modeloJtablematerial.addRow(new Object[] { nome });
						}

						st.close();
						con.close();

					} catch (Exception erroBuscaModelo) {
						erroBuscaModelo.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					}
				}

			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		// Adicionando imagem.
		Image imgCancelar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnCancelar.setIcon(new ImageIcon(imgCancelar));
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.setBounds(521, 624, 115, 20);
		contentPane.add(btnCancelar);

		JLabel lblModelo = new JLabel("Modelo Pronto");
		lblModelo.setBounds(10, 67, 201, 36);
		contentPane.add(lblModelo);

		;

		JLabel lblNewLabel = new JLabel("Materiais Para Modelo Personalizado");
		lblNewLabel.setBounds(814, 67, 230, 36);
		contentPane.add(lblNewLabel);

		JScrollPane previewPedido = new JScrollPane();
		previewPedido.setBounds(271, 179, 512, 352);
		contentPane.add(previewPedido);
		{

			// Colunas da tabela de materiais.
			modeloJtable.addColumn("Código");
			modeloJtable.addColumn("Nome");
			modeloJtable.addColumn("Qtd");
			modeloJtable.addColumn("Preço");

		}

		tablelistademateriais = new JTable(modeloJtable);
		
		tablelistademateriais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tablelistademateriais.getColumnModel().getColumn(0)
				.setPreferredWidth(1);
		tablelistademateriais.getColumnModel().getColumn(1)
				.setPreferredWidth(1);
		tablelistademateriais.getColumnModel().getColumn(2)
				.setPreferredWidth(1);
		tablelistademateriais.getColumnModel().getColumn(3)
				.setPreferredWidth(1);

		tablelistademateriais.getTableHeader().setReorderingAllowed(false);

		previewPedido.setViewportView(tablelistademateriais);

		JButton btnSelecionarmodelo = new JButton("Selecionar");
		btnSelecionarmodelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					// pega as linhas selecionadas
					int x = table_modelo.getSelectedRow();

					// pega o nome da linha selecionada para servir de busca das
					// informações para a coluna principal.
					String nomecampo = "";
					// obtem o nome da linha selecionada
					nomecampo = String.valueOf(table_modelo.getValueAt(x, 0));

					// busca das informações
					String comando = "select modelo_id,modelo_listaMaterial, modelo_nome, modelo_listaqtd, modelo_listaCusto from tbl_modelo where modelo_nome = '"
							+ nomecampo + "'";

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					// não sei oq isso faz kk
					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					int cont = 0;

					// faz o select da lista de material

					// vai ser guardado os selectes com o nome do material
					// especifico
					String[] comando2 = null;

					// contador
					int i = 0;

					while (resultSet.next()) {

						String id = resultSet.getString("modelo_id");

						o.setModeloId(Integer.parseInt(id));

						String nome = resultSet.getString("modelo_nome");
						String listaqtd = resultSet
								.getString("modelo_listaqtd");
						String listamaterial = resultSet
								.getString("modelo_listaMaterial");
						String listacusto = resultSet
								.getString("modelo_listaCusto");

						// carregou a string que veio com os nomes
						Scanner sc4 = new Scanner(listamaterial);

						// adiciona na tabela central os itens dos modelo
						Scanner sc3 = new Scanner(listamaterial);
						Scanner sc2 = new Scanner(listaqtd);
						Scanner sc = new Scanner(listacusto);

						while ((sc3.hasNextLine()) && (sc2.hasNextLine())
								&& (sc.hasNextLine())) {

							modeloJtable.addRow(new Object[] { 0,
									sc3.nextLine(), sc2.nextLine(),
									sc.nextLine(), });

							cont++;

						}
						comando2 = new String[cont];
						// cada linha, ele cria um select com UM nome de um
						// material da lista, guarda no vetor
						while ((sc4.hasNextLine())) {

							comando2[i] = "select material_id from tbl_material inner join tbl_modelo on material_nome = '"
									+ sc4.nextLine()
									+ "' group by material_id;";

							i++;
						}

						sc.close();
						sc2.close();
						sc3.close();
						sc4.close();

					}

					// outro select abaixo \/

					// o i tem o tamanho do cont, que tem a quantidade de
					// materiais do modelo

					i = (modeloJtable.getRowCount());
					int c = comando2.length;
					// faz as consultas especificas. Aqui, ele pega o ID do
					// material PELO nome dele.
					while (i != 0) {

						c--;
						i--;
						if (c <= -1) {
							break;
						}
						resultSet = st.executeQuery(comando2[c]);

						while (resultSet.next()) {

							// substitui o registro da primeira coluna, os ID
							// dos materias, pelo id CORRETO.

							modeloJtable.setValueAt(
									resultSet.getString("material_id"), i, 0);

						}

					}
					// Selecionando

					st.close();
					con.close();

				} // fim do try
				catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na passagem");
				}

			}

		});
		// Adicionando imagem.
		Image imgSelecionarModelo = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSelecionarmodelo.setIcon(new ImageIcon(imgSelecionarModelo));
		btnSelecionarmodelo.setToolTipText("Selecionar Modelo");
		btnSelecionarmodelo.setBounds(10, 542, 230, 23);
		contentPane.add(btnSelecionarmodelo);

		JLabel lblMateriaisDoPedido = new JLabel("Materiais do Pedido");
		lblMateriaisDoPedido.setBounds(271, 133, 170, 35);
		contentPane.add(lblMateriaisDoPedido);
		// Adicionando imagem.
		Image imgEditar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();

		JButton btnRemover = new JButton("Remover");
		// Adicionando imagem.
		Image imgRemover = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnRemover.setIcon(new ImageIcon(imgRemover));

		btnRemover.setToolTipText("Remover Item");
		btnRemover.setBounds(271, 542, 240, 23);
		contentPane.add(btnRemover);

		JButton btnLimparLista = new JButton("Limpar Lista");
		// Adicionando imagem.
		Image imgLimparLista = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimparLista.setIcon(new ImageIcon(imgLimparLista));
		btnLimparLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				while (tablelistademateriais.getModel().getRowCount() > 0) {
					((DefaultTableModel) tablelistademateriais.getModel())
							.removeRow(0);
				}

			}
		});
		btnLimparLista.setToolTipText("Limpar Lista");
		btnLimparLista.setBounds(556, 542, 227, 23);
		contentPane.add(btnLimparLista);

		JPanel panel = new JPanel();
		panel.setBounds(814, 189, 230, 51);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblBuscar = new JLabel("Buscar");
		lblBuscar.setBounds(99, 5, 91, 14);
		panel.add(lblBuscar);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 30, 45, 15);
		panel.add(lblNome);

		tfmaterialbusca = new JTextField();
		tfmaterialbusca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				while (table_material.getModel().getRowCount() > 0) {
					((DefaultTableModel) table_material.getModel())
							.removeRow(0);
				}

				if (cbcategoriamaterial.getSelectedItem().equals("Todas")) {

					try {

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando2 = "select material_nome from tbl_material where material_nome like '"
								+ tfmaterialbusca.getText()
								+ "%' group by material_id ;";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							String nome = resultSet2.getString("material_nome");
							modeloJtablematerial.addRow(new Object[] { nome });
						}

						st.close();
						con.close();

					} catch (Exception erroBuscaModelo) {
						erroBuscaModelo.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Falha na no carregamento dos materiais");
					}

				}

				else {

					try {

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando = "Select categoriamaterial_id from tbl_categoriamaterial where categoriamaterial_nome = '"
								+ cbcategoriamaterial.getSelectedItem()
								+ "' group by material_id; ";

						ResultSet resultSet = st.executeQuery(comando);

						int id = 0;

						while (resultSet.next()) {

							id = resultSet.getInt("categoriamaterial_id");

						}

						String comando2 = "select material_nome from tbl_material inner join tbl_categoriamaterial on tbl_material.categoriamaterial_id = "
								+ id
								+ " and material_nome like '"
								+ tfmaterialbusca.getText()
								+ "%' group by material_id;";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							String nome = resultSet2.getString("material_nome");
							modeloJtablematerial.addRow(new Object[] { nome });
						}

						st.close();
						con.close();

					} catch (Exception erroBuscaModelo) {
						erroBuscaModelo.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Falha na no carregamento dos materiais");
					}
				}
			}
		});
		tfmaterialbusca.setBounds(54, 27, 135, 20);
		panel.add(tfmaterialbusca);
		tfmaterialbusca.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(10, 184, 230, 56);
		contentPane.add(panel_1);

		JLabel label = new JLabel("Buscar");
		label.setBounds(99, 5, 91, 14);
		panel_1.add(label);

		JLabel label_1 = new JLabel("Nome:");
		label_1.setBounds(10, 30, 45, 15);
		panel_1.add(label_1);

		tfmodelobusca = new JTextField();
		tfmodelobusca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				// limpa a tabela
				while (table_modelo.getModel().getRowCount() > 0) {
					((DefaultTableModel) table_modelo.getModel()).removeRow(0);
				}

				if (cbcategoriamodelo.getSelectedIndex() == 0) {

					try {
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando2 = "select modelo_nome from tbl_modelo where modelo_nome like '"
								+ tfmodelobusca.getText() + "'%;";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							String nome = resultSet2.getString("modelo_nome");
							modeloJtablemodelo.addRow(new Object[] { nome });
						}

						st.close();
						con.close();

					} catch (Exception erroBuscaModelo) {
						erroBuscaModelo.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					}

				} else {

					try {

						String comando = "Select categoriamodelo_id from tbl_categoriamodelo where categoriamodelo_nome = '"
								+ cbcategoriamodelo.getSelectedItem() + "'; ";

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						ResultSet resultSet = st.executeQuery(comando);

						int id = 0;

						while (resultSet.next()) {

							id = resultSet.getInt("categoriamodelo_id");

						}

						String comando2 = "select modelo_nome from tbl_modelo inner join tbl_categoriamodelo on tbl_modelo.categoriamodelo_id = "
								+ id
								+ " and modelo_nome like '"
								+ tfmodelobusca.getText()
								+ "%' group by modelo_id;";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							String nome = resultSet2.getString("modelo_nome");
							modeloJtablemodelo.addRow(new Object[] { nome });
						}

						st.close();
						con.close();

					} catch (Exception erroBuscaModelo) {
						erroBuscaModelo.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					}
				}
			}
		});
		tfmodelobusca.setColumns(10);
		tfmodelobusca.setBounds(54, 27, 136, 20);
		panel_1.add(tfmodelobusca);

		JButton btnselecionarmaterial = new JButton("Selecionar");
		btnselecionarmaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					// pegar nome.
					// pesquisar código, e custo.
					// adicionar na lista principal

					String nome = String.valueOf(modeloJtablematerial
							.getValueAt(table_material.getSelectedRow(), 0));

					String comando = "select material_id, material_custo from tbl_material where material_nome = '"
							+ nome + "';";

					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					while (resultSet.next()) {

						String id = resultSet.getString("material_id");
						String custo = resultSet.getString("material_custo");

						modeloJtable
								.addRow(new Object[] { id, nome, 1, custo, });

					}

					st.close();
					con.close();

				} // fim do try
				catch (Exception item) {
					item.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Falha na passagem do material.");
				}

			}
		});

		// Adicionando imagem.
		Image imgSelecionarMaterial = new ImageIcon(this.getClass()
				.getResource("/16px/ok.png")).getImage();
		btnselecionarmaterial.setIcon(new ImageIcon(imgSelecionarMaterial));
		btnselecionarmaterial.setToolTipText("Selecionar Item");
		btnselecionarmaterial.setBounds(814, 542, 230, 23);
		contentPane.add(btnselecionarmaterial);

		tablelistademateriais = new JTable(modeloJtable) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 67, 1054, 2);
		contentPane.add(separator);

		JLabel lblMateriais = new JLabel(
				"Cadastro de Or\u00E7amento - Materiais");
		lblMateriais.setFont(new Font("Tahoma", Font.PLAIN, 18));
		// Adicionando Imagem.
		Image imgOrcamentoMaterial = new ImageIcon(this.getClass().getResource(
				"/50px/orcamento.png")).getImage();
		lblMateriais.setIcon(new ImageIcon(imgOrcamentoMaterial));
		lblMateriais.setBounds(11, 0, 1033, 50);
		contentPane.add(lblMateriais);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 598, 1054, 2);
		contentPane.add(separator_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 107, 230, 66);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoria.setBounds(79, 11, 85, 14);
		panel_2.add(lblCategoria);

		JLabel lblNome_1 = new JLabel("Nome:");
		lblNome_1.setBounds(10, 40, 46, 14);
		panel_2.add(lblNome_1);

		String itemModelo = null;

		try {

			String comando = "Select categoriamodelo_nome from tbl_categoriamodelo where categoriamodelo_id between '1' and '100'";

			Class.forName("com.mysql.jdbc.Driver");

			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String item = resultSet.getString("categoriamodelo_nome");
				itemModelo = item;

			}

			st.close();
			con.close();

		} // fim do try
		catch (Exception item) {
			item.printStackTrace();
			JOptionPane.showMessageDialog(null, "Nenhum item encontrado.");
		}

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBounds(814, 112, 230, 66);
		contentPane.add(panel_3);

		JLabel label_2 = new JLabel("Categoria");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(79, 11, 85, 14);
		panel_3.add(label_2);

		JLabel label_3 = new JLabel("Nome:");
		label_3.setBounds(10, 40, 46, 14);
		panel_3.add(label_3);

		tfVisita = new JTextField();
		tfVisita.setBounds(355, 75, 86, 20);
		contentPane.add(tfVisita);
		tfVisita.setColumns(10);

		tflargura = new JTextField();
		tflargura.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				tfarea.setText(String.valueOf(Double.parseDouble(tfaltura
						.getText()) * (Double.parseDouble(tfaltura.getText()))));

			}
		});
		tflargura.setBounds(556, 75, 86, 20);
		contentPane.add(tflargura);
		tflargura.setColumns(10);

		tfaltura = new JTextField();

		tfaltura.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				tfarea.setText(String.valueOf(Double.parseDouble(tfaltura
						.getText()) * (Double.parseDouble(tflargura.getText()))));

			}
		});

		tfaltura.setColumns(10);
		tfaltura.setBounds(556, 107, 86, 20);
		contentPane.add(tfaltura);

		JLabel lblVisita = new JLabel("Cod. Visita:");
		lblVisita.setBounds(271, 78, 74, 14);
		contentPane.add(lblVisita);

		JButton btnBuscarModelo = new JButton("");
		Image imgBuscarModelo = new ImageIcon(this.getClass().getResource(
				"/16px/search.png")).getImage();
		btnBuscarModelo.setIcon(new ImageIcon(imgBuscarModelo));
		btnBuscarModelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Buscar Modelo

				// limpa a tabela
				while (table_modelo.getModel().getRowCount() > 0) {
					((DefaultTableModel) table_modelo.getModel()).removeRow(0);
				}

				if (cbcategoriamodelo.getSelectedItem().equals("Todas")) {

					try {

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando2 = "select modelo_nome from tbl_modelo where modelo_nome like '"
								+ tfmodelobusca.getText() + "%';";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							String nome = resultSet2.getString("modelo_nome");
							modeloJtablemodelo.addRow(new Object[] { nome });
						}

						st.close();
						con.close();

					} catch (Exception erroBuscaModelo) {
						erroBuscaModelo.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Falha na no carregamento dos modelos");
					}
				}

				else {

					try {

						String comando = "Select categoriamodelo_id from tbl_categoriamodelo where categoriamodelo_nome = '"
								+ cbcategoriamodelo.getSelectedItem() + "'; ";

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						ResultSet resultSet = st.executeQuery(comando);

						int id = 0;

						while (resultSet.next()) {

							id = resultSet.getInt("categoriamodelo_id");

						}

						String comando2 = "select modelo_nome from tbl_modelo inner join tbl_categoriamodelo on tbl_modelo.categoriamodelo_id = "
								+ id
								+ " and modelo_nome like '"
								+ tfmodelobusca.getText()
								+ "%' group by modelo_id;";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							String nome = resultSet2.getString("modelo_nome");
							modeloJtablemodelo.addRow(new Object[] { nome });
						}

						st.close();
						con.close();

					} catch (Exception erroBuscaModelo) {
						erroBuscaModelo.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					}

				}
			}
		});
		btnBuscarModelo.setToolTipText("buscar");
		btnBuscarModelo.setBounds(200, 26, 20, 20);
		panel_1.add(btnBuscarModelo);

		JButton limparModelo = new JButton("Limpar Filtro e Lista");
		Image imgLimparFiltros1 = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		limparModelo.setIcon(new ImageIcon(imgLimparFiltros1));
		limparModelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tfmodelobusca.setText("");

				// limpa a tabela
				while (table_modelo.getModel().getRowCount() > 0) {
					((DefaultTableModel) table_modelo.getModel()).removeRow(0);
				}

			}
		});
		limparModelo.setBounds(10, 564, 230, 23);
		contentPane.add(limparModelo);
		// Adicionando imagem.
		Image imgBuscarCategoriaMateriais = new ImageIcon(this.getClass()
				.getResource("/16px/search.png")).getImage();

		cbcategoriamaterial.setModel(new DefaultComboBoxModel(
				new String[] { "Todas" }));
		cbcategoriamaterial.setBounds(66, 37, 136, 20);
		panel_3.add(cbcategoriamaterial);

		JButton btnbuscarmaterial = new JButton("");
		btnbuscarmaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Buscar material

				while (table_material.getModel().getRowCount() > 0) {
					((DefaultTableModel) table_material.getModel())
							.removeRow(0);
				}

				if (cbcategoriamaterial.getSelectedItem().equals("Todas")) {

					try {

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando2 = "select material_nome from tbl_material;";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							String nome = resultSet2.getString("material_nome");
							modeloJtablematerial.addRow(new Object[] { nome });
						}

						st.close();
						con.close();

					} catch (Exception erroBuscaModelo) {
						erroBuscaModelo.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Falha na no carregamento dos materiais");
					}

				}

				else {

					try {

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando = "Select categoriamaterial_id from tbl_categoriamaterial where categoriamaterial_nome = '"
								+ cbcategoriamaterial.getSelectedItem() + "'; ";

						ResultSet resultSet = st.executeQuery(comando);

						int id = 0;

						while (resultSet.next()) {

							id = resultSet.getInt("categoriamaterial_id");

						}

						String comando2 = "select material_nome from tbl_material inner join tbl_categoriamaterial on tbl_material.categoriamaterial_id = "
								+ id
								+ " and material_nome like '"
								+ tfmaterialbusca.getText()
								+ "%' group by material_id;";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							String nome = resultSet2.getString("material_nome");
							modeloJtablematerial.addRow(new Object[] { nome });
						}

						st.close();
						con.close();

					} catch (Exception erroBuscaModelo) {
						erroBuscaModelo.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Falha na no carregamento dos materiais");
					}
				}

			}
		});// Adicionando imagem.
		Image imgBuscarMaterial = new ImageIcon(this.getClass().getResource(
				"/16px/search.png")).getImage();
		btnbuscarmaterial.setIcon(new ImageIcon(imgBuscarMaterial));
		btnbuscarmaterial.setToolTipText("buscar");
		btnbuscarmaterial.setBounds(200, 27, 20, 20);
		panel.add(btnbuscarmaterial);

		JButton btnLimparFiltros = new JButton("Limpar Filtro e Lista");
		Image imgLimparFiltros2 = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnLimparFiltros.setIcon(new ImageIcon(imgLimparFiltros2));
		btnLimparFiltros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tfmaterialbusca.setText("");
				// limpa a tabela
				while (table_material.getModel().getRowCount() > 0) {
					((DefaultTableModel) table_material.getModel())
							.removeRow(0);
				}
			}
		});
		btnLimparFiltros.setBounds(814, 564, 230, 23);
		contentPane.add(btnLimparFiltros);

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int resposta = JOptionPane
						.showConfirmDialog(
								null,
								"O cadastro do pedido será cancelado. Tem certeza que deseja continuar?",
								"Aviso", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					dispose();
				} else if (resposta == JOptionPane.NO_OPTION) {

					try {

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando2 = "select max(orcamento_id) from tbl_orcamento;";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							int id = resultSet2.getInt("max(orcamento_id)");

							o.setId(id);

						}

						comando2 = "delete from tbl_orcamento where id = "
								+ o.getId() + ";";

					} catch (SQLException ee) {

						ee.printStackTrace();

					}

				}

			}
		});

		// Adicionando imagem.
		Image imgBuscarCategoriaModelo = new ImageIcon(this.getClass()
				.getResource("/16px/search.png")).getImage();

		cbcategoriamodelo.setModel(new DefaultComboBoxModel(
				new String[] { "Todas" }));
		cbcategoriamodelo.setBounds(68, 36, 136, 20);
		panel_2.add(cbcategoriamodelo);

		JButton btnConfirmar = new JButton("Selecionar");
		Image imgConfirmar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnConfirmar.setIcon(new ImageIcon(imgConfirmar));
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Selecionando visita.

				try {
					JDSelecionarCodigodeVisita frame = new JDSelecionarCodigodeVisita(
							o);
					frame.setModal(true);
					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

				tfVisita.setText(String.valueOf(o.getVisitaId()));

				try {

					String comando = "select visita_id, visita_data, visita_status, visita_horario, visita_nome, visita_endereco, visita_telefone, visita_observacao from tbl_visita where visita_id = "
							+ tfVisita.getText() + ";";

					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);
					while (resultSet.next()) {
						v.setId(resultSet.getInt("visita_id"));
						v.setDataVisita(resultSet.getString("visita_data"));
						v.setStatus(resultSet.getInt("visita_status"));
						v.setHorarioVisita(resultSet
								.getString("visita_horario"));
						v.setNome(resultSet.getString("visita_nome"));
						v.setEndereco(resultSet.getString("visita_endereco"));
						v.setTelefone(resultSet.getString("visita_telefone"));
						v.setObservacao(resultSet
								.getString("visita_observacao"));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		btnConfirmar.setBounds(271, 107, 170, 23);
		contentPane.add(btnConfirmar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 243, 230, 288);
		contentPane.add(scrollPane);

		{

			// Colunas da tabela de modelos
			modeloJtablemodelo.addColumn("Nome");

		}

		table_modelo = new JTable(modeloJtablemodelo) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		// evento de 2 cliques n linha da table

		table_modelo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					try {

						// pega as linhas selecionadas
						int x = table_modelo.getSelectedRow();

						// pega o nome da linha selecionada para servir de busca
						// das
						// informações para a coluna principal.
						String nomecampo = "";
						// obtem o nome da linha selecionada
						nomecampo = String.valueOf(table_modelo
								.getValueAt(x, 0));

						// busca das informações
						String comando = "select modelo_id,modelo_listaMaterial, modelo_nome, modelo_listaqtd, modelo_listaCusto from tbl_modelo where modelo_nome = '"
								+ nomecampo + "'";

						// cria a conecxão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						// não sei oq isso faz kk
						java.sql.Statement st = con.createStatement();

						ResultSet resultSet = st.executeQuery(comando);

						int cont = 0;

						// faz o select da lista de material

						// vai ser guardado os selectes com o nome do material
						// especifico
						String[] comando2 = null;

						// contador
						int i = 0;

						while (resultSet.next()) {

							String id = resultSet.getString("modelo_id");

							o.setModeloId(Integer.parseInt(id));

							String nome = resultSet.getString("modelo_nome");

							o.setNomedopedido(nome);

							String listaqtd = resultSet
									.getString("modelo_listaqtd");
							String listamaterial = resultSet
									.getString("modelo_listaMaterial");
							String listacusto = resultSet
									.getString("modelo_listaCusto");

							// carregou a string que veio com os nomes
							Scanner sc4 = new Scanner(listamaterial);

							// adiciona na tabela central os itens dos modelo
							Scanner sc3 = new Scanner(listamaterial);
							Scanner sc2 = new Scanner(listaqtd);
							Scanner sc = new Scanner(listacusto);

							while ((sc3.hasNextLine()) && (sc2.hasNextLine())
									&& (sc.hasNextLine())) {

								modeloJtable.addRow(new Object[] { 0,
										sc3.nextLine(), sc2.nextLine(),
										sc.nextLine(), });

								cont++;

							}
							comando2 = new String[cont];
							// cada linha, ele cria um select com UM nome de um
							// material da lista, guarda no vetor
							while ((sc4.hasNextLine())) {

								comando2[i] = "select material_id from tbl_material inner join tbl_modelo on material_nome = '"
										+ sc4.nextLine()
										+ "' group by material_id;";

								i++;
							}

							sc.close();
							sc2.close();
							sc3.close();
							sc4.close();

						}

						// outro select abaixo \/

						// o i tem o tamanho do cont, que tem a quantidade de
						// materiais do modelo

						i = (modeloJtable.getRowCount());
						int c = comando2.length;
						// faz as consultas especificas. Aqui, ele pega o ID do
						// material PELO nome dele.
						while (i != 0) {

							c--;
							i--;
							if (c <= -1) {
								break;
							}
							resultSet = st.executeQuery(comando2[c]);

							while (resultSet.next()) {

								// substitui o registro da primeira coluna, os
								// ID
								// dos materias, pelo id CORRETO.

								modeloJtable.setValueAt(
										resultSet.getString("material_id"), i,
										0);

							}

						}
						// Selecionando

						st.close();
						con.close();

					} // fim do try
					catch (Exception ee) {
						ee.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na passagem");
					}

				}
			}
		});

		scrollPane.setViewportView(table_modelo);

		table_modelo.getTableHeader().setReorderingAllowed(false);

		new Thread() {

			public void run() {

				// carrega as categoria de modelos

				String comando = "select categoriamodelo_nome from tbl_categoriamodelo;";

				try {

					Class.forName("com.mysql.jdbc.Driver");

					// Criando conexão.
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					while (resultSet.next()) {

						String nome = resultSet
								.getString("categoriamodelo_nome");

						cbcategoriamodelo.addItem(nome);

					}

					st.close();
					con.close();

				} // fim do try
				catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

				// carrega as categoria de materiais

				String comando1 = "select categoriamaterial_nome from tbl_categoriamaterial;";

				try {

					Class.forName("com.mysql.jdbc.Driver");

					// Criando conexão.
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando1);

					while (resultSet.next()) {

						String nome = resultSet
								.getString("categoriamaterial_nome");

						cbcategoriamaterial.addItem(nome);

					}

					st.close();
					con.close();

				} // fim do try
				catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

			}
		}.start();

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(814, 243, 230, 288);
		contentPane.add(scrollPane_1);

		{

			// Colunas da tabela de materiais.
			modeloJtablematerial.addColumn("Nome");

		}

		table_material = new JTable(modeloJtablematerial) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		table_material.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					try {
						// pegar nome.
						// pesquisar código, e custo.
						// adicionar na lista principal

						String nome = String.valueOf(modeloJtablematerial
								.getValueAt(table_material.getSelectedRow(), 0));

						String comando = "select material_id, material_custo from tbl_material where material_nome = '"
								+ nome + "';";

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						ResultSet resultSet = st.executeQuery(comando);

						while (resultSet.next()) {

							String id = resultSet.getString("material_id");
							String custo = resultSet
									.getString("material_custo");

							modeloJtable.addRow(new Object[] { id, nome, 1,
									custo, });

						}

						st.close();
						con.close();

					} // fim do try
					catch (Exception item) {
						item.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Falha na passagem do material.");
					}

				}
			}
		});

		scrollPane_1.setViewportView(table_material);

		table_material.getTableHeader().setReorderingAllowed(false);

		// carrega altomaticamente os MODELOS ao carregar a tela

		while (table_modelo.getModel().getRowCount() > 0) {
			((DefaultTableModel) table_modelo.getModel()).removeRow(0);
		}

		new Thread() {

			public void run() {

				try {

					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					String comando2 = "select modelo_nome from tbl_modelo;";

					ResultSet resultSet2 = st.executeQuery(comando2);

					while (resultSet2.next()) {

						String nome = resultSet2.getString("modelo_nome");
						modeloJtablemodelo.addRow(new Object[] { nome });
					}

					st.close();
					con.close();

				} catch (Exception erroBuscaModelo) {
					erroBuscaModelo.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Falha na no carregamento automatico dos modelos");
				}

			}
		}.start();

		// carrega altomaticamente os MATERIAIS ao carregar a tela

		// limpa a tabela
		while (table_material.getModel().getRowCount() > 0) {
			((DefaultTableModel) table_material.getModel()).removeRow(0);
		}

		new Thread() {

			public void run() {

				try {

					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					String comando2 = "select material_nome from tbl_material;";

					ResultSet resultSet2 = st.executeQuery(comando2);

					while (resultSet2.next()) {

						String nome = resultSet2.getString("material_nome");
						modeloJtablematerial.addRow(new Object[] { nome });
					}

					st.close();
					con.close();

				} catch (Exception erroBuscaModelo) {
					erroBuscaModelo.printStackTrace();
					JOptionPane
							.showMessageDialog(null,
									"Falha na no carregamento automatico dos materiais");
				}

			}
		}.start();

		
		JButton btnSalvar = new JButton("Pr\u00F3ximo");
		// Adicionando imagem.
		Image imgSalvar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(imgSalvar));
		btnSalvar.setToolTipText("Salvar Dados");
		btnSalvar.addActionListener(new ActionListener() {

			// abre atela para a próxima parte d orçamento
			public void actionPerformed(ActionEvent e) {

				if (tfaltura.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(
									null,
									"Todos os campos obrigatórios devem ser preenchidos",
									"AVISO!", JOptionPane.WARNING_MESSAGE);
				} else if (tfarea.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(
									null,
									"Todos os campos obrigatórios devem ser preenchidos",
									"AVISO!", JOptionPane.WARNING_MESSAGE);

				} else if (tflargura.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(
									null,
									"Todos os campos obrigatórios devem ser preenchidos",
									"AVISO!", JOptionPane.WARNING_MESSAGE);

				} else if (tfVisita.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(
									null,
									"Todos os campos obrigatórios devem ser preenchidos",
									"AVISO!", JOptionPane.WARNING_MESSAGE);

				}

				try {


					String comando = "select visita_id, visita_data, visita_status, visita_horario, visita_nome, visita_endereco, visita_telefone, visita_observacao from tbl_visita where visita_id = "
							+ tfVisita.getText() + ";";

					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);
					while (resultSet.next()) {
						v.setId(Integer.parseInt(resultSet
								.getString("visita_id")));
						v.setDataVisita(resultSet.getString("visita_data"));
						v.setStatus(resultSet.getInt("visita_status"));
						v.setHorarioVisita(resultSet
								.getString("visita_horario"));
						v.setNome(resultSet.getString("visita_nome"));
						v.setEndereco(resultSet.getString("visita_endereco"));
						v.setTelefone(resultSet.getString("visita_telefone"));
						v.setObservacao(resultSet
								.getString("visita_observacao"));
					}

				} catch (Exception ee) {
					ee.printStackTrace();
				}

				try {
					if (tfVisita.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null,
								"Por favor, selecione uma visita.", "Aviso!",
								JOptionPane.WARNING_MESSAGE);
					} else {

						o.setVisitaId(Integer.parseInt(tfVisita.getText()));

						Class.forName("com.mysql.jdbc.Driver");

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						// salva a lista de materiais selecinado em um string
						int i = tablelistademateriais.getRowCount();

						String a = "";

						while (i != 0) {
							i--;

							a = (a + tablelistademateriais.getValueAt(i, 1) + "\n");

						}

						// salva em lista as quantides selecinado em um string
						i = tablelistademateriais.getRowCount();

						String b = "";

						while (i != 0) {
							i--;

							b = (b + tablelistademateriais.getValueAt(i, 2) + "\n");

						}

						o.setArea(Double.parseDouble(tfarea.getText()));

						// formata data para padrão americano
						SimpleDateFormat Formatodataam = new SimpleDateFormat(
								"yyyy/MM/dd");

						
						String comando = "insert into tbl_orcamento set orcamento_descricaoproduto = 'Nenhum', orcamento_detalesadicionais = 'Nenhum', orcamento_listaqtd = '"
								+ b
								+ "', orcamento_largura = "
								+ tfaltura.getText()
								+ ", orcamento_altura = "
								+ tfaltura.getText()
								+ ", orcamento_area = "
								+ tfarea.getText()
								+ ", orcamento_metodoPagamento = 0, orcamento_precopormetroquadrado = 0, orcamento_valorMaoDeObra = 0, orcamento_totalPrazo = 0, orcamento_juros = 0, orcamento_parcelamento = 0, orcamento_valorTotal = 0, orcamento_listaMaterial = '"
								+ a
								+ "', modelo_id = "
								+ o.getModeloId()
								+ ", visita_id = " + tfVisita.getText() + ", orcamento_datagerado = '" + Formatodataam.format(DateTime.now().toDate())+"' ;";
						st.execute(comando);

						String comando2 = "select max(orcamento_id) from tbl_orcamento;";

						ResultSet resultSet2 = st.executeQuery(comando2);

						while (resultSet2.next()) {

							int id = resultSet2.getInt("max(orcamento_id)");

							o.setId(id);

						}

						try {
							JFCOrcamento_financeiro dialog = new JFCOrcamento_financeiro(
									o, v);
							dialog.setModal(true);
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setVisible(true);
						} catch (Exception error) {
							error.printStackTrace();
						}

						dispose();

					}

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
		btnSalvar.setBounds(378, 624, 133, 20);
		contentPane.add(btnSalvar);

		JLabel lblLargura = new JLabel("Largura:");
		lblLargura.setBounds(472, 77, 74, 17);
		contentPane.add(lblLargura);

		JLabel lblAltura = new JLabel("Altura:");
		lblAltura.setBounds(472, 107, 46, 14);
		contentPane.add(lblAltura);

		JLabel lblMetros = new JLabel("Metros");
		lblMetros.setBounds(655, 78, 60, 14);
		contentPane.add(lblMetros);

		JLabel label_4 = new JLabel("Metros");
		label_4.setBounds(655, 108, 60, 14);
		contentPane.add(label_4);

		JLabel lblMetro = new JLabel("Area:");
		lblMetro.setBounds(472, 143, 52, 14);
		contentPane.add(lblMetro);

		tfarea = new JTextField();
		tfarea.setEditable(false);
		tfarea.setBounds(556, 140, 86, 20);
		contentPane.add(tfarea);
		tfarea.setColumns(10);

		JLabel lblMetro_1 = new JLabel("Metro\u00B2");
		lblMetro_1.setBounds(655, 143, 46, 14);
		contentPane.add(lblMetro_1);

		JLabel lblNewLabel_1 = new JLabel("* - Campos obrigat\u00F3rios");
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(355, 21, 156, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("*");
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setBounds(521, 78, 46, 14);
		contentPane.add(lblNewLabel_2);

		JLabel label_5 = new JLabel("*");
		label_5.setForeground(Color.RED);
		label_5.setBounds(521, 108, 46, 14);
		contentPane.add(label_5);

		JLabel label_6 = new JLabel("*");
		label_6.setForeground(Color.RED);
		label_6.setBounds(521, 143, 46, 14);
		contentPane.add(label_6);

		JLabel label_7 = new JLabel("*");
		label_7.setForeground(Color.RED);
		label_7.setBounds(331, 78, 46, 14);
		contentPane.add(label_7);
		
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// pede para confirmar a resposta
				int resposta = JOptionPane.showConfirmDialog(null,
						"Deseja remover o material selecionado do orçamento?",
						"Confirmar Remoção ", JOptionPane.YES_NO_OPTION);

				// chaca resposta e faz o de acordo
				if (resposta == JOptionPane.YES_OPTION) {

					
					// pega as linhas selecionadas
					int x = tablelistademateriais.getSelectedRow();
					
					JOptionPane.showMessageDialog(null, modeloJtable.getRowCount());
					
					JOptionPane.showMessageDialog(null, x);

					modeloJtable.removeRow(x);

				} else if (resposta == JOptionPane.NO_OPTION) {
					

				}

			}
		});
		

	}
}
