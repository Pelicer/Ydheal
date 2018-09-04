package view.jFrames;

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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTable.PrintMode;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Model.DAO.ConexaoMySQL;
import modelSuplerclasses.Materiais;
import modelSuplerclasses.Modelo;
import view.jFrames.cadastros.JFCMateriais;
import view.jFrames.visualizar_registros.JFVisualizarMaterial;

@SuppressWarnings("serial")
public class JFSelecaodeMaterial extends JDialog {

	int count = 0;
	private DefaultTableModel ModeloJTMaterial = new DefaultTableModel();
	private JPanel contentPane;
	private JTextField tfCodigo;
	private JTextField tfNome;
	private JTextField tfCusto;
	private JTable tabletbl_material;

	DefaultTableModel listadeitens = new DefaultTableModel();

	private JTable tablelista;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JFSelecaodeMaterial(Modelo mo) {

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/materiais.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Material");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1160, 745);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelFiltros = new JPanel();
		panelFiltros.setBounds(9, 11, 1125, 88);
		contentPane.add(panelFiltros);
		panelFiltros.setLayout(null);
		panelFiltros.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel label = new JLabel("C\u00F3digo:");
		label.setBounds(223, 38, 55, 14);
		panelFiltros.add(label);

		tfCodigo = new JTextField();
		tfCodigo.setColumns(10);
		tfCodigo.setBounds(271, 35, 86, 20);
		panelFiltros.add(tfCodigo);

		JLabel label_1 = new JLabel("Nome:");
		label_1.setBounds(367, 38, 46, 14);
		panelFiltros.add(label_1);

		tfNome = new JTextField();
		tfNome.setColumns(10);
		tfNome.setBounds(405, 35, 202, 20);
		panelFiltros.add(tfNome);

		JLabel lblMaterial = new JLabel("Material");
		// Adicionando imagem.
		Image imgMateriais = new ImageIcon(this.getClass().getResource(
				"/50px/materiais.png")).getImage();
		lblMaterial.setIcon(new ImageIcon(imgMateriais));
		lblMaterial.setBounds(10, 9, 343, 79);
		panelFiltros.add(lblMaterial);
		lblMaterial.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JLabel lblCusto = new JLabel("Custo:");
		lblCusto.setBounds(617, 38, 46, 14);
		panelFiltros.add(lblCusto);

		tfCusto = new JTextField();
		tfCusto.setBounds(659, 35, 86, 20);
		panelFiltros.add(tfCusto);
		tfCusto.setColumns(10);

		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(755, 38, 66, 14);
		panelFiltros.add(lblCategoria);

		JComboBox cbcategoria = new JComboBox();
		cbcategoria.setModel(new DefaultComboBoxModel(new String[] { "" }));
		cbcategoria.setBounds(819, 35, 123, 20);
		panelFiltros.add(cbcategoria);

		JPanel panelTabela = new JPanel();
		panelTabela.setBounds(9, 103, 1125, 592);
		contentPane.add(panelTabela);
		panelTabela.setLayout(null);

		JLabel registrosEncontrados = new JLabel("0");
		registrosEncontrados.setHorizontalAlignment(SwingConstants.CENTER);
		registrosEncontrados.setForeground(new Color(0, 128, 0));
		registrosEncontrados.setBounds(544, 45, 46, 14);
		panelTabela.add(registrosEncontrados);

		JButton btnNovo = new JButton("Novo");
		// Adicionando iamgem.
		Image imgNovo = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnNovo.setIcon(new ImageIcon(imgNovo));
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Abre o formulário de cadastro de materiais.
				try {
					JFCMateriais frame = new JFCMateriais();
					frame.setModal(true);
					frame.setVisible(true);

				} catch (Exception error) {
					error.printStackTrace();
				}

				while (tabletbl_material.getModel().getRowCount() > 0) {
					((DefaultTableModel) tabletbl_material.getModel())
							.removeRow(0);
				}

				Carregar_tela_material();

				registrosEncontrados.setText(String.valueOf(count));

			}
		});
		btnNovo.setToolTipText("Criar Novo Registro");
		btnNovo.setBounds(65, 11, 116, 23);
		panelTabela.add(btnNovo);

		JButton btnFiltrar = new JButton("Filtrar");
		// Adicionando imagem.
		Image imgFiltrar = new ImageIcon(this.getClass().getResource(
				"/16px/search.png")).getImage();
		btnFiltrar.setIcon(new ImageIcon(imgFiltrar));
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// if de categoria
				if (cbcategoria.getSelectedItem().equals("")) {

					if ((tfCodigo.getText().equals(""))
							&& (tfCusto.getText().equals(""))
							&& (tfNome.getText().equals(""))) {

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Carregar_tela_material();

						registrosEncontrados.setText(String.valueOf(count));

					}

					else if ((!tfCodigo.getText().equals(""))
							&& (tfCusto.getText().equals(""))
							&& (tfNome.getText().equals(""))) {

						String comando = "select categoriamaterial_nome, material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_id = "
								+ tfCodigo.getText() + ";";

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

					else if ((tfCodigo.getText().equals(""))
							&& (!tfCusto.getText().equals(""))
							&& (tfNome.getText().equals(""))) {

						String comando = "select  categoriamaterial_nome, material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_custo = "
								+ tfCusto.getText() + ";";

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

					else if ((tfCodigo.getText().equals(""))
							&& (tfCusto.getText().equals(""))
							&& (!tfNome.getText().equals(""))) {

						String comando = "select  categoriamaterial_nome, material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_nome Like '"
								+ tfNome.getText() + "%';";

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

					else if ((!tfCodigo.getText().equals(""))
							&& (!tfCusto.getText().equals(""))
							&& (tfNome.getText().equals(""))) {

						String comando = "select  categoriamaterial_nome, material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_id = "
								+ tfCodigo.getText()
								+ " and material_custo = "
								+ tfCusto.getText() + ";";

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

					else if ((!tfCodigo.getText().equals(""))
							&& (tfCusto.getText().equals(""))
							&& (!tfNome.getText().equals(""))) {

						String comando = "select categoriamaterial_nome, material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_id = "
								+ tfCodigo.getText()
								+ " and  material_nome Like '"
								+ tfNome.getText() + "%';";

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

					else if ((tfCodigo.getText().equals(""))
							&& (!tfCusto.getText().equals(""))
							&& (!tfNome.getText().equals(""))) {

						String comando = "select categoriamaterial_nome,  material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_custo = "
								+ tfCusto.getText()
								+ " and  material_nome Like '"
								+ tfNome.getText() + "%';";

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

					else if ((!tfCodigo.getText().equals(""))
							&& (!tfCusto.getText().equals(""))
							&& (!tfNome.getText().equals(""))) {

						String comando = "select categoriamaterial_nome, material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_custo = "
								+ tfCusto.getText()
								+ " and  material_nome Like '"
								+ tfNome.getText()
								+ "%' and material_id = "
								+ tfCodigo.getText() + ";";

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

				}// fim d oif de categoria

				// caso tenha uma categoria no combo box, ele pesquisa com base
				// nele
				else {

					if ((tfCodigo.getText().equals(""))
							&& (tfCusto.getText().equals(""))
							&& (tfNome.getText().equals(""))) {

						String comando = "select categoriamaterial_nome, material_id, material_nome, material_descricao, material_custo"
								+ " from tbl_material"
								+ " inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where categoriamaterial_nome = '"
								+ cbcategoria.getSelectedItem() + "';";

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

					else if ((!tfCodigo.getText().equals(""))
							&& (tfCusto.getText().equals(""))
							&& (tfNome.getText().equals(""))) {

						String comando = " select categoriamaterial_nome, material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_id = "
								+ tfCodigo.getText()
								+ " and  categoriamaterial_nome = '"
								+ cbcategoria.getSelectedItem() + "';";
						;

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

					else if ((tfCodigo.getText().equals(""))
							&& (!tfCusto.getText().equals(""))
							&& (tfNome.getText().equals(""))) {

						String comando = "select categoriamaterial_nome,  material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_custo = "
								+ tfCusto.getText()
								+ " and  categoriamaterial_nome = '"
								+ cbcategoria.getSelectedItem() + "';";

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

					else if ((tfCodigo.getText().equals(""))
							&& (tfCusto.getText().equals(""))
							&& (!tfNome.getText().equals(""))) {

						String comando = "select categoriamaterial_nome,  material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_nome Like '"
								+ tfNome.getText()
								+ "%' and  categoriamaterial_nome = '"
								+ cbcategoria.getSelectedItem() + "';";

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

					else if ((!tfCodigo.getText().equals(""))
							&& (!tfCusto.getText().equals(""))
							&& (tfNome.getText().equals(""))) {

						String comando = "select  categoriamaterial_nome, material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_id = "
								+ tfCodigo.getText()
								+ " and material_custo = "
								+ tfCusto.getText()
								+ " and  categoriamaterial_nome = '"
								+ cbcategoria.getSelectedItem() + "';";

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

					else if ((!tfCodigo.getText().equals(""))
							&& (tfCusto.getText().equals(""))
							&& (!tfNome.getText().equals(""))) {

						String comando = "select  categoriamaterial_nome,  material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_id = "
								+ tfCodigo.getText()
								+ " and  material_nome Like '"
								+ tfNome.getText()
								+ "%'  and  categoriamaterial_nome = '"
								+ cbcategoria.getSelectedItem() + "';";

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

					else if ((tfCodigo.getText().equals(""))
							&& (!tfCusto.getText().equals(""))
							&& (!tfNome.getText().equals(""))) {

						String comando = "select  categoriamaterial_nome,  material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_custo = "
								+ tfCusto.getText()
								+ " and  material_nome Like '"
								+ tfNome.getText()
								+ "%'  and  categoriamaterial_nome = '"
								+ cbcategoria.getSelectedItem() + "';";

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

					else if ((!tfCodigo.getText().equals(""))
							&& (!tfCusto.getText().equals(""))
							&& (!tfNome.getText().equals(""))) {

						String comando = "select  categoriamaterial_nome,  material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_custo = "
								+ tfCusto.getText()
								+ " and  material_nome Like '"
								+ tfNome.getText()
								+ "%' and material_id = "
								+ tfCodigo.getText()
								+ " and  categoriamaterial_nome = '"
								+ cbcategoria.getSelectedItem() + "';";

						while (tabletbl_material.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_material.getModel())
									.removeRow(0);
						}

						Cod(comando);

						registrosEncontrados.setText(String.valueOf(count));

					}

				}

			}
		});
		btnFiltrar.setToolTipText("Filtrar Registros");
		btnFiltrar.setBounds(189, 11, 116, 23);
		panelTabela.add(btnFiltrar);

		JButton btnLimpar = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(imgLimpar));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tfCodigo.setText("");
				tfCusto.setText("");
				tfNome.setText("");
				tfCodigo.grabFocus();
			}
		});
		btnLimpar.setToolTipText("Limpar Filtros");
		btnLimpar.setBounds(315, 11, 116, 23);
		panelTabela.add(btnLimpar);

		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// API de impressão.
				MessageFormat footer = new MessageFormat("Página {0}");
				MessageFormat header = new MessageFormat("Lista de Materiais");

				try {
					boolean complete = tabletbl_material.print(
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
		btnImprimir.setIcon(new ImageIcon(imgImprimir));
		btnImprimir.setToolTipText("Imprimir");
		btnImprimir.setBounds(441, 11, 116, 23);
		panelTabela.add(btnImprimir);

		JButton btnAlterar = new JButton("Alterar");
		// Adicionando imagem.
		Image imgAlterar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		btnAlterar.setIcon(new ImageIcon(imgAlterar));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Materiais m = new Materiais();

				String comando = "select material_observacao, categoriamaterial_nome, material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id where material_id = "
						+ ModeloJTMaterial.getValueAt(
								tabletbl_material.getSelectedRow(), 0)
						+ " group by material_id; ";

				try {

					Class.forName("com.mysql.jdbc.Driver");

					// Criando conexão.
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					while (resultSet.next()) {

						String id = resultSet.getString("material_id");
						String custo = resultSet.getString("material_custo");
						String nome = resultSet.getString("material_nome");
						String descricao = resultSet
								.getString("material_descricao");
						String categoria = resultSet
								.getString("categoriamaterial_nome");
						String obs = resultSet.getString("material_observacao");

						m.setNomeMaterial(nome);
						m.setCustoMaterial(Double.parseDouble(custo));
						m.setDescricaoMaterial(descricao);
						m.setObservacaoMaterial(obs);
						m.setCategoria(categoria);
						m.setId(Integer.parseInt(id));

					}

					st.close();
					con.close();

				}// fim do try
				catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

				try {
					JFVisualizarMaterial frame = new JFVisualizarMaterial(m);
					frame.setModal(true);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception erro) {
					erro.printStackTrace();
				}
			}
		});
		btnAlterar.setToolTipText("Alterar Registro");
		btnAlterar.setBounds(567, 11, 116, 23);
		panelTabela.add(btnAlterar);

		JButton btnExcluir = new JButton("Excluir");
		// Adicionando imagem.
		Image imgExcluir = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnExcluir.setIcon(new ImageIcon(imgExcluir));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// pede para confirmar a resposta
				int resposta = JOptionPane.showConfirmDialog(null,
						"Deseja realmente excluir os materiais selecionados?",
						"Confirmar exclusões ", JOptionPane.YES_NO_OPTION);

				// chaca resposta e faz o de acordo
				if (resposta == JOptionPane.YES_OPTION) {

					// pega as linhas selecionadas
					int x[] = tabletbl_material.getSelectedRows();

					// exclui as linhas selecionadas

					int id = 0;

					for (int i = x.length - 1; i >= 0; i--) {

						// obtem o id da linha selecionada
						id = Integer.parseInt(String.valueOf(ModeloJTMaterial
								.getValueAt(x[i], 0)));

						ModeloJTMaterial.removeRow(x[i]);

						String comando = "DELETE FROM tbl_material WHERE material_id = "
								+ id + ";";

						try {

							Class.forName("com.mysql.jdbc.Driver");

							// cria a conecxão
							java.sql.Connection con = ConexaoMySQL
									.getConexaoMySQL();

							// não sei oq isso faz kk
							java.sql.Statement st = con.createStatement();

							st.execute(comando);

							st.close();
							con.close();

						}// fim do try
						catch (Exception ee) {
							ee.printStackTrace();
							JOptionPane.showMessageDialog(null,
									"Falha na exclusão");
						}

						registrosEncontrados.setText(String.valueOf(Integer
								.parseInt(registrosEncontrados.getText()) - 1));

					}// fecha o for de exclusão

				} else if (resposta == JOptionPane.NO_OPTION) {
					// Usuário clicou em não. Executar o código correspondente.

				}

			}
		});

		JLabel quantidaddeitensadicionados = new JLabel("0");
		quantidaddeitensadicionados
				.setHorizontalAlignment(SwingConstants.CENTER);
		quantidaddeitensadicionados.setForeground(new Color(0, 128, 0));
		quantidaddeitensadicionados.setBounds(1069, 34, 46, 14);
		panelTabela.add(quantidaddeitensadicionados);

		btnExcluir.setToolTipText("Excluir Registro");
		btnExcluir.setBounds(693, 11, 116, 23);
		panelTabela.add(btnExcluir);

		JLabel label7 = new JLabel("Registros Encontrados");
		label7.setBounds(597, 45, 141, 14);
		panelTabela.add(label7);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 728, 511);
		panelTabela.add(scrollPane);
		{
			ModeloJTMaterial.addColumn("Cod");
			ModeloJTMaterial.addColumn("Nome");
			ModeloJTMaterial.addColumn("Descrição");
			ModeloJTMaterial.addColumn("Custo");
			ModeloJTMaterial.addColumn("Categoria");

		}

		tabletbl_material = new JTable(ModeloJTMaterial) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		// impede que as colunas sejam reordemadas
		tabletbl_material.getTableHeader().setReorderingAllowed(false);

		scrollPane.setViewportView(tabletbl_material);

		tabletbl_material.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					listadeitens.addRow(new Object[] {
							ModeloJTMaterial.getValueAt(
									tabletbl_material.getSelectedRow(), 1),
							ModeloJTMaterial.getValueAt(
									tabletbl_material.getSelectedRow(), 3),
							ModeloJTMaterial.getValueAt(
									tabletbl_material.getSelectedRow(), 4), "1" });

					quantidaddeitensadicionados.setText(String.valueOf(Integer
							.parseInt(quantidaddeitensadicionados.getText()) + 1));

				}
			}
		});

		tabletbl_material.getColumnModel().getColumn(0).setPreferredWidth(1);
		tabletbl_material.getColumnModel().getColumn(1).setPreferredWidth(10);
		tabletbl_material.getColumnModel().getColumn(2).setPreferredWidth(200);
		tabletbl_material.getColumnModel().getColumn(3).setPreferredWidth(1);
		tabletbl_material.getColumnModel().getColumn(4).setPreferredWidth(1);

		// Carregando valores.
		Carregar_tela_material();

		// carrega as categorias

		String comando = "select categoriamaterial_nome from tbl_categoriamaterial;";

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// Criando conexão.
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String nome = resultSet.getString("categoriamaterial_nome");

				cbcategoria.addItem(nome);

			}

			st.close();
			con.close();

		}// fim do try
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}

		registrosEncontrados.setText(String.valueOf(count));

		JLabel lblListaDeItens = new JLabel("Lista de itens selecionados");
		lblListaDeItens.setBounds(901, 15, 214, 14);
		panelTabela.add(lblListaDeItens);

		JLabel lblQtdDeItem = new JLabel("Qtd. de iten(s) selecionado(s):");
		lblQtdDeItem.setBounds(910, 34, 170, 14);
		panelTabela.add(lblQtdDeItem);

		JButton btnLimpar_1 = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar2 = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar_1.setIcon(new ImageIcon(imgLimpar2));
		btnLimpar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// limpando todo o Jlist

				int i = tablelista.getRowCount();

				while (i != 0) {

					i--;

					listadeitens.removeRow(i);

				}

				quantidaddeitensadicionados.setText("0");

			}
		});
		btnLimpar_1.setBounds(1015, 558, 100, 23);
		panelTabela.add(btnLimpar_1);

		JButton btnRemover = new JButton("Remover");
		// Adicionando imagem.
		Image imgRemover = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnRemover.setIcon(new ImageIcon(imgRemover));
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int x[] = tablelista.getSelectedRows();

				// exclui as linhas selecionadas

				int i = x.length;

				while (i != 0) {

					i--;

					listadeitens.removeRow(x[i]);

					quantidaddeitensadicionados.setText(String.valueOf(Integer
							.parseInt(quantidaddeitensadicionados.getText()) - 1));

				}

			}
		});
		btnRemover.setBounds(910, 558, 106, 23);
		panelTabela.add(btnRemover);

		JButton btnConfirmarSeleo = new JButton("Confirmar sele\u00E7\u00E3o");
		// Adicionando imagem.
		Image imgConfirmar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnConfirmarSeleo.setIcon(new ImageIcon(imgConfirmar));
		btnConfirmarSeleo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// descobri como contar os registros e pegar todos os itens da
				// lista

				// pega a quantidade de registros de lista
				int i = tablelista.getRowCount();

				String lista[] = new String[i];

				String qtd[] = new String[i];

				double custo[] = new double[i];

				while (i != 0) {

					i--;

					lista[i] = String.valueOf(listadeitens.getValueAt(i, 0));

					qtd[i] = String.valueOf(listadeitens.getValueAt(i, 3));

					custo[i] = Double.parseDouble(String.valueOf(listadeitens
							.getValueAt(i, 1)));

				}

				mo.Guardarlista(lista, custo, qtd);

				JOptionPane.showMessageDialog(null,
						"Lista selecionada com sucesso");

				dispose();

			}
		});
		btnConfirmarSeleo.setBounds(748, 558, 159, 23);
		panelTabela.add(btnConfirmarSeleo);

		JButton button = new JButton(">>>");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int x[] = tabletbl_material.getSelectedRows();

				// pega o numero de linha selecionas
				int i = x.length;

				while (i != 0) {

					i--;

					listadeitens.addRow(new Object[] {
							ModeloJTMaterial.getValueAt(x[i], 1),
							ModeloJTMaterial.getValueAt(x[i], 3),
							ModeloJTMaterial.getValueAt(x[i], 4), "1" });

					quantidaddeitensadicionados.setText(String.valueOf(Integer
							.parseInt(quantidaddeitensadicionados.getText()) + 1));

				}

			}
		});
		button.setBounds(819, 11, 72, 23);
		panelTabela.add(button);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(750, 70, 365, 477);
		panelTabela.add(scrollPane_1);

		{
			listadeitens.addColumn("Nome");

			listadeitens.addColumn("Custo");

			listadeitens.addColumn("Categoria");

			listadeitens.addColumn("QTD");

		}

		tablelista = new JTable(listadeitens);

		// impede que as colunas sejam reordemadas
		tablelista.getTableHeader().setReorderingAllowed(false);

		tablelista.getColumnModel().getColumn(0).setPreferredWidth(1);
		tablelista.getColumnModel().getColumn(1).setPreferredWidth(20);
		tablelista.getColumnModel().getColumn(2).setPreferredWidth(10);

		scrollPane_1.setViewportView(tablelista);

	}// fim class da tela

	public void Carregar_tela_material() {

		String comando = "select categoriamaterial_nome, material_id, material_nome, material_descricao, material_custo from tbl_material inner join tbl_categoriamaterial on tbl_categoriamaterial.categoriamaterial_id = tbl_material.categoriamaterial_id order by material_id; ";

		count = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// Criando conexão.
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String id = resultSet.getString("material_id");
				String custo = resultSet.getString("material_custo");
				String nome = resultSet.getString("material_nome");
				String descricao = resultSet.getString("material_descricao");
				String categoria = resultSet
						.getString("categoriamaterial_nome");

				count = (count + 1);

				ModeloJTMaterial.addRow(new Object[] { id, nome, descricao,
						custo, categoria });

			}

			st.close();
			con.close();

		}// fim do try
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}

	}

	public void Cod(String comando) {

		count = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String id = resultSet.getString("material_id");
				String custo = resultSet.getString("material_custo");
				String nome = resultSet.getString("material_nome");
				String descricao = resultSet.getString("material_descricao");

				String categoria = resultSet
						.getString("categoriamaterial_nome");

				count = (count + 1);

				ModeloJTMaterial.addRow(new Object[] { id, nome, descricao,
						custo, categoria });

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
