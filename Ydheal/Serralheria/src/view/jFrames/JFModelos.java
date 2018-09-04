package view.jFrames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.text.MessageFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import modelSuplerclasses.Modelo;
import view.jFrames.cadastros.JFCModelo;
import view.jFrames.visualizar_registros.JFVisualizarModelo;

@SuppressWarnings("serial")
public class JFModelos extends JFrame {

	private JPanel contentPane;
	private JTextField tfcodigo;
	private JTextField tfnome;
	private JTable tabletbl_modelos;

	public int count = 0;

	// cria atabela

	DefaultTableModel modelotbl_modelo = new DefaultTableModel();
	private JTextField tfcusto;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JFModelos() {

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/modelo.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 992, 740);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel3 = new JPanel();
		contentPane.add(panel3, BorderLayout.CENTER);
		panel3.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(0, 0, 966, 88);
		panel3.add(panel_1);

		JLabel label = new JLabel("C\u00F3digo:");
		label.setBounds(211, 27, 55, 14);
		panel_1.add(label);

		tfcodigo = new JTextField();
		tfcodigo.setColumns(10);
		tfcodigo.setBounds(259, 24, 86, 20);
		panel_1.add(tfcodigo);

		JLabel lblDescri = new JLabel("Nome:");
		lblDescri.setBounds(370, 27, 73, 14);
		panel_1.add(lblDescri);

		tfnome = new JTextField();
		tfnome.setColumns(10);
		tfnome.setBounds(416, 24, 202, 20);
		panel_1.add(tfnome);

		JLabel lblCategoria = new JLabel("Categoria: ");
		lblCategoria.setBounds(638, 27, 73, 14);
		panel_1.add(lblCategoria);

		JComboBox cbcategoria = new JComboBox();
		cbcategoria.setBounds(706, 24, 132, 20);
		panel_1.add(cbcategoria);

		JLabel lblCusto = new JLabel("Custo:");
		lblCusto.setBounds(854, 27, 55, 14);
		panel_1.add(lblCusto);

		tfcusto = new JTextField();
		tfcusto.setBounds(900, 24, 56, 20);
		panel_1.add(tfcusto);
		tfcusto.setColumns(10);

		JLabel lblModelo = new JLabel("Modelo");
		// Adicionando imagem.
		Image imgModelo = new ImageIcon(this.getClass().getResource(
				"/50px/amostra.png")).getImage();
		lblModelo.setIcon(new ImageIcon(imgModelo));
		lblModelo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblModelo.setBounds(10, 6, 343, 79);
		panel_1.add(lblModelo);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 88, 966, 592);
		panel3.add(panel);

		JLabel lblregistrosencontrados = new JLabel("0");
		lblregistrosencontrados.setHorizontalAlignment(SwingConstants.CENTER);
		lblregistrosencontrados.setForeground(new Color(0, 128, 0));
		lblregistrosencontrados.setBounds(740, 45, 46, 14);
		panel.add(lblregistrosencontrados);

		JButton btnnovo = new JButton("Novo");
		// Adicionando imagem.
		Image imgNovo = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnnovo.setIcon(new ImageIcon(imgNovo));
		btnnovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					JFCModelo frame = new JFCModelo();
					frame.setModal(true);
					frame.setVisible(true);
				} catch (Exception we) {
					we.printStackTrace();
				}

				while (tabletbl_modelos.getModel().getRowCount() > 0) {
					((DefaultTableModel) tabletbl_modelos.getModel())
							.removeRow(0);
				}

				Carregar_tela_modelo();

				lblregistrosencontrados.setText(String.valueOf(count));

			}
		});
		btnnovo.setToolTipText("Criar Novo Registro");
		btnnovo.setBounds(117, 11, 116, 23);
		panel.add(btnnovo);

		JButton bntfiltrar = new JButton("Filtrar");
		// Adicionando imagem.
		Image imgFiltrar = new ImageIcon(this.getClass().getResource(
				"/16px/search.png")).getImage();
		bntfiltrar.setIcon(new ImageIcon(imgFiltrar));
		bntfiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// if com a categoria Vazia

				if ((tfcodigo.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfcusto.getText().equals(""))
						&& (cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id  order by modelo_id;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfcusto.getText().equals(""))
						&& (cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo "
							+ "inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_id = "
							+ tfcodigo.getText() + " order by modelo_id;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfcusto.getText().equals(""))
						&& (cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_nome like '"
							+ tfnome.getText() + "%' order by modelo_id;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfcusto.getText().equals(""))
						&& (cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo "
							+ "inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_custo > "
							+ tfcusto.getText() + " order by modelo_custo;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfcusto.getText().equals(""))
						&& (cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_custo >= "
							+ tfcusto.getText()
							+ " and  modelo_id = "
							+ tfcodigo.getText() + " order by modelo_custo;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfcusto.getText().equals(""))
						&& (cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_nome like '"
							+ tfnome.getText()
							+ "%' and  modelo_id = "
							+ tfcodigo.getText() + " order by modelo_id;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfcusto.getText().equals(""))
						&& (cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_nome like '"
							+ tfnome.getText()
							+ "%' and modelo_custo >= "
							+ tfcusto.getText() + " order by modelo_id;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfcusto.getText().equals(""))
						&& (cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_nome like '"
							+ tfnome.getText()
							+ "%' and  modelo_id = "
							+ tfcodigo.getText()
							+ " and  modelo_custo >= "
							+ tfcusto.getText() + " order by modelo_id;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				// /
				// ----------------------------------------------------------------------------------------------------

				// if com a categoria selecionada

				if ((tfcodigo.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfcusto.getText().equals(""))
						&& (!cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id and categoriamodelo_nome = '"
							+ cbcategoria.getSelectedItem()
							+ "' order by modelo_id;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (tfcusto.getText().equals(""))
						&& (!cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo "
							+ "inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_id = "
							+ tfcodigo.getText()
							+ " and categoriamodelo_nome = '"
							+ cbcategoria.getSelectedItem()
							+ "' order by modelo_id;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfcusto.getText().equals(""))
						&& (!cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_nome like '"
							+ tfnome.getText()
							+ "%'  and categoriamodelo_nome = '"
							+ cbcategoria.getSelectedItem()
							+ "' order by modelo_id;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfcusto.getText().equals(""))
						&& (!cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo "
							+ "inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_custo > "
							+ tfcusto.getText()
							+ " and categoriamodelo_nome = '"
							+ cbcategoria.getSelectedItem()
							+ "' order by modelo_id;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (tfnome.getText().equals(""))
						&& (!tfcusto.getText().equals(""))
						&& (!cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_custo >= "
							+ tfcusto.getText()
							+ " and  modelo_id = "
							+ tfcodigo.getText()
							+ " and categoriamodelo_nome = '"
							+ cbcategoria.getSelectedItem()
							+ "' order by modelo_id;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (tfcusto.getText().equals(""))
						&& (!cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_nome like '"
							+ tfnome.getText()
							+ "%' and  modelo_id = "
							+ tfcodigo.getText()
							+ " and categoriamodelo_nome = '"
							+ cbcategoria.getSelectedItem()
							+ "' order by modelo_id;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				else if ((tfcodigo.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfcusto.getText().equals(""))
						&& (!cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_nome like '"
							+ tfnome.getText()
							+ "%' and modelo_custo >= "
							+ tfcusto.getText()
							+ " and categoriamodelo_nome = '"
							+ cbcategoria.getSelectedItem()
							+ "' order by modelo_id;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

				else if ((!tfcodigo.getText().equals(""))
						&& (!tfnome.getText().equals(""))
						&& (!tfcusto.getText().equals(""))
						&& (!cbcategoria.getSelectedItem().equals("Nenhuma"))) {

					String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo, modelo_descricao from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_nome like '"
							+ tfnome.getText()
							+ "%' and  modelo_id = "
							+ tfcodigo.getText()
							+ " and  modelo_custo >= "
							+ tfcusto.getText()
							+ " and categoriamodelo_nome = '"
							+ cbcategoria.getSelectedItem()
							+ "' order by modelo_id;";

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Cod(comando);

					lblregistrosencontrados.setText(String.valueOf(count));

				}

			}
		});
		bntfiltrar.setToolTipText("Filtrar Registros");
		bntfiltrar.setBounds(243, 11, 116, 23);
		panel.add(bntfiltrar);

		JButton bntlimpar = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		bntlimpar.setIcon(new ImageIcon(imgLimpar));
		bntlimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				tfnome.setText("");
				tfcodigo.setText("");
				cbcategoria.setSelectedIndex(0);

			}
		});
		bntlimpar.setToolTipText("Limpar Filtros");
		bntlimpar.setBounds(369, 11, 116, 23);
		panel.add(bntlimpar);

		JButton bntimprimir = new JButton("Imprimir");
		bntimprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// API de impressão.
				MessageFormat footer = new MessageFormat("Página {0}");
				MessageFormat header = new MessageFormat("Lista de Modelos");

				try {
					boolean complete = tabletbl_modelos.print(
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
		bntimprimir.setIcon(new ImageIcon(imgImprimir));
		bntimprimir.setToolTipText("Imprimir");
		bntimprimir.setBounds(495, 11, 116, 23);
		panel.add(bntimprimir);

		JButton bntalterar = new JButton("Alterar");
		// Adicionando imagem.
		Image imgAlterar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		bntalterar.setIcon(new ImageIcon(imgAlterar));
		bntalterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Modelo vm = new Modelo();

				String comando = "select modelo_listaCusto, modelo_listaqtd, modelo_listaCusto, categoriamodelo_nome,tbl_categoriamodelo.categoriamodelo_id, modelo_id, modelo_nome, modelo_listaMaterial, modelo_custo, modelo_descricao "
						+ "from tbl_modelo "
						+ "inner join tbl_categoriamodelo "
						+ "on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id"
						+ " where modelo_id = "
						+ modelotbl_modelo.getValueAt(
								tabletbl_modelos.getSelectedRow(), 0) + ";";

				try {

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					// não sei oq isso faz kk
					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					while (resultSet.next()) {

						String id = resultSet.getString("modelo_id");

						String lista = resultSet
								.getString("modelo_listaMaterial");
						String nome = resultSet.getString("modelo_nome");
						String custodomodelo = resultSet
								.getString("modelo_custo");
						String descricao = resultSet
								.getString("modelo_descricao");
						String categoria = resultSet
								.getString("categoriamodelo_nome");

						String qtd = resultSet.getString("modelo_listaqtd");

						vm.setCategoria(categoria);

						vm.setDescricaoModelo(descricao);
						vm.setId(Integer.parseInt(id));
						vm.setListaMaterial(lista);
						vm.setNomeModelo(nome);
						vm.setValorModelo(Double.parseDouble(custodomodelo));
						vm.setListaCusto(resultSet
								.getString("modelo_listaCusto"));

						vm.setListaqtd(qtd);

					}

					st.close();
					con.close();

				}// fim do try
				catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							JFVisualizarModelo frame = new JFVisualizarModelo(
									vm);
							//frame.setModal(true);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

				while (tabletbl_modelos.getModel().getRowCount() > 0) {
					((DefaultTableModel) tabletbl_modelos.getModel())
							.removeRow(0);
				}

				Carregar_tela_modelo();

				lblregistrosencontrados.setText(String.valueOf(count));

			}
		});
		bntalterar.setToolTipText("Alterar Registro");
		bntalterar.setBounds(621, 11, 116, 23);
		panel.add(bntalterar);

		JButton btnexcluir = new JButton("Excluir");
		// Adicionando imagem.
		Image imgExcluir = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnexcluir.setIcon(new ImageIcon(imgExcluir));
		btnexcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// pede para confirmar a resposta
				int resposta = JOptionPane.showConfirmDialog(null,
						"Deseja realmente excluir os modelos selecionados?",
						"Confirmar exclusões ", JOptionPane.YES_NO_OPTION);

				// chaca resposta e faz o de acordo
				if (resposta == JOptionPane.YES_OPTION) {

					// pega as linhas selecionadas
					int x[] = tabletbl_modelos.getSelectedRows();

					// exclui as linhas selecionadas

					int id = 0;

					for (int i = x.length - 1; i >= 0; i--) {

						// obtem o id da linha selecionada
						id = Integer.parseInt(String.valueOf(modelotbl_modelo
								.getValueAt(x[i], 0)));

						modelotbl_modelo.removeRow(x[i]);

						String comando = "DELETE FROM tbl_modelo WHERE modelo_id = "
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
							JOptionPane
									.showMessageDialog(
											null,
											"Falha na exclusão. Não será possível excluir o modelo caso exista pendências do mesmo."
													+ "\nPor favor, exclua as amostras deste antes de retirá-lo do sistema.",
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
		btnexcluir.setToolTipText("Excluir Registro");
		btnexcluir.setBounds(747, 11, 116, 23);
		panel.add(btnexcluir);

		JLabel label_2 = new JLabel("Registros Encontrados");
		label_2.setBounds(793, 45, 141, 14);
		panel.add(label_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 946, 511);
		panel.add(scrollPane);

		// cria os titulos

		{
			modelotbl_modelo.addColumn("Cod.");
			modelotbl_modelo.addColumn("Nome");
			modelotbl_modelo.addColumn("Descrição");
			modelotbl_modelo.addColumn("Custo");
			modelotbl_modelo.addColumn("Categoria");

		}

		tabletbl_modelos = new JTable(modelotbl_modelo) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		scrollPane.setViewportView(tabletbl_modelos);
		setTitle("Modelos");

		// impede que as colunas sejam reordemadas
		tabletbl_modelos.getTableHeader().setReorderingAllowed(false);

		// no fim da moddificação da célula editavel, vem a deteqção de evento
		// de cliq dublo em um registro da jtable.
		tabletbl_modelos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					Modelo vm = new Modelo();

					String comando = "select modelo_listaCusto, modelo_listaqtd, modelo_listaCusto, categoriamodelo_nome,tbl_categoriamodelo.categoriamodelo_id, modelo_id, modelo_nome, modelo_listaMaterial, modelo_custo, modelo_descricao "
							+ "from tbl_modelo "
							+ "inner join tbl_categoriamodelo "
							+ "on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id"
							+ " where modelo_id = "
							+ modelotbl_modelo.getValueAt(
									tabletbl_modelos.getSelectedRow(), 0) + ";";

					try {

						// cria a conecxão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						// não sei oq isso faz kk
						java.sql.Statement st = con.createStatement();

						ResultSet resultSet = st.executeQuery(comando);

						while (resultSet.next()) {

							String id = resultSet.getString("modelo_id");

							String lista = resultSet
									.getString("modelo_listaMaterial");
							String nome = resultSet.getString("modelo_nome");
							String custodomodelo = resultSet
									.getString("modelo_custo");
							String descricao = resultSet
									.getString("modelo_descricao");
							String categoria = resultSet
									.getString("categoriamodelo_nome");

							String qtd = resultSet.getString("modelo_listaqtd");

							vm.setCategoria(categoria);

							vm.setDescricaoModelo(descricao);
							vm.setId(Integer.parseInt(id));
							vm.setListaMaterial(lista);
							vm.setNomeModelo(nome);
							vm.setValorModelo(Double.parseDouble(custodomodelo));
							vm.setListaCusto(resultSet
									.getString("modelo_listaCusto"));

							vm.setListaqtd(qtd);

						}

						st.close();
						con.close();

					}// fim do try
					catch (Exception ee) {
						ee.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					}

					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								JFVisualizarModelo frame = new JFVisualizarModelo(
										vm);
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});

					while (tabletbl_modelos.getModel().getRowCount() > 0) {
						((DefaultTableModel) tabletbl_modelos.getModel())
								.removeRow(0);
					}

					Carregar_tela_modelo();

					lblregistrosencontrados.setText(String.valueOf(count));
				}
			}
		});

		tabletbl_modelos.getColumnModel().getColumn(0).setPreferredWidth(1);
		tabletbl_modelos.getColumnModel().getColumn(1).setPreferredWidth(1);
		tabletbl_modelos.getColumnModel().getColumn(2).setPreferredWidth(100);
		tabletbl_modelos.getColumnModel().getColumn(3).setPreferredWidth(1);
		tabletbl_modelos.getColumnModel().getColumn(4).setPreferredWidth(1);

		// carrega os valores
		Carregar_tela_modelo();

		lblregistrosencontrados.setText(String.valueOf(count));

		// carrega as categorias

		String comando = "select categoriamodelo_nome from tbl_categoriamodelo;";

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// Criando conexão.
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String nome = resultSet.getString("categoriamodelo_nome");

				cbcategoria.addItem(nome);

			}

			st.close();
			con.close();

		}// fim do try
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}

	}// fim da claase da tela

	public void Carregar_tela_modelo() {

		String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_listaMaterial, modelo_custo, modelo_descricao from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id order by modelo_id;";

		count = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String id = resultSet.getString("modelo_id");

				String nome = resultSet.getString("modelo_nome");

				String custo = resultSet.getString("modelo_custo");

				String descricao = resultSet.getString("modelo_descricao");

				String categoria = resultSet.getString("categoriamodelo_nome");

				count = (count + 1);

				modelotbl_modelo.addRow(new Object[] { id, nome, descricao,
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

				String id = resultSet.getString("modelo_id");

				String nome = resultSet.getString("modelo_nome");

				String custo = resultSet.getString("modelo_custo");

				String descricao = resultSet.getString("modelo_descricao");

				String categoria = resultSet.getString("categoriamodelo_nome");

				count = (count + 1);

				modelotbl_modelo.addRow(new Object[] { id, nome, descricao,
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
