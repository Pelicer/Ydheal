package view.jFrames.cadastros;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import modelSuplerclasses.Materiais;
import Model.DAO.ConexaoMySQL;

import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class JFCMateriais extends JDialog {

	private JPanel contentPane;
	private JTextField tfNome;
	private JTextField tfCusto;
	private JTextField tfDescricao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JFCMateriais() {

		// Verificação ao fechar janela.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				int resposta = JOptionPane
						.showConfirmDialog(
								null,
								"O cadastro do material será cancelado. Tem certeza que deseja continuar?",
								"Aviso", JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					dispose();
				} else if (resposta == JOptionPane.NO_OPTION) {
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// cancel
				}

			}
		});

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/materiais.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Cadastro Material");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 335, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 99, 80, 14);
		contentPane.add(lblNome);

		JLabel lblCusto = new JLabel("Custo:");
		lblCusto.setBounds(10, 174, 57, 14);
		contentPane.add(lblCusto);

		JLabel lblDescricao = new JLabel("Descri\u00E7\u00E3o:");
		lblDescricao.setBounds(10, 199, 80, 14);
		contentPane.add(lblDescricao);

		JLabel lblObservacao = new JLabel("Observa\u00E7\u00E3o:");
		lblObservacao.setBounds(10, 224, 80, 14);
		contentPane.add(lblObservacao);

		tfNome = new JTextField();
		tfNome.setBounds(110, 99, 204, 20);
		contentPane.add(tfNome);
		tfNome.setColumns(10);

		tfCusto = new JTextField();
		tfCusto.setBounds(110, 171, 204, 20);
		tfCusto.setColumns(10);
		contentPane.add(tfCusto);

		JTextArea tfObs = new JTextArea();
		tfObs.setBounds(114, 227, 200, 75);
		contentPane.add(tfObs);

		tfDescricao = new JTextField();
		tfDescricao.setBounds(110, 199, 204, 20);
		tfDescricao.setColumns(10);
		contentPane.add(tfDescricao);

		JPanel panel = new JPanel();
		panel.setBounds(0, 320, 314, 20);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 3, 0, 0));

		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Selecione...");
		comboBox.setBounds(110, 124, 204, 20);
		contentPane.add(comboBox);

		JButton btnSalvar = new JButton("Salvar");
		// Adicionando imagem.
		Image imgSalvar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(imgSalvar));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tfNome.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);
				} else if (tfCusto.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campos obrigatórios devem ser preenchidos.",
							"WARNING!", JOptionPane.WARNING_MESSAGE);

				} else if (comboBox.getSelectedItem() == "Selecione...") {
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

						Materiais m = new Materiais();

						// pega os valores dos tf e da para o obj material
						m.setNomeMaterial(tfNome.getText());
						m.setCustoMaterial(Double.parseDouble(tfCusto.getText()));
						m.setDescricaoMaterial(tfDescricao.getText());
						m.setObservacaoMaterial(tfObs.getText());
						m.setCategoria(String.valueOf(comboBox
								.getSelectedItem()));

						String comando = "select tbl_categoriamaterial.categoriamaterial_id from tbl_categoriamaterial where categoriamaterial_nome = '"
								+ m.getCategoria() + "'";

						ResultSet resultSet = st.executeQuery(comando);

						int y = 0;

						while (resultSet.next()) {

							String id = resultSet
									.getString("categoriamaterial_id");

							y = Integer.parseInt(id);

						}

						resultSet.close();

						st.execute("insert into tbl_material (categoriamaterial_id, material_custo, material_nome, material_descricao, material_observacao) "
								+ "values ("
								+ y
								+ ", ' "
								+ m.getCustoMaterial()
								+ "',  '"
								+ m.getNomeMaterial()
								+ "', '"
								+ m.getDescricaoMaterial()
								+ "', '"
								+ m.getObservacaoMaterial() + "');");

						// Material cadastrado.
						JOptionPane.showMessageDialog(null,
								"Material cadastrado com sucesso!!");

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
		btnSalvar.setToolTipText("Salvar Dados");
		panel.add(btnSalvar);

		JButton btnLimpar = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(imgLimpar));
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfNome.setText("");
				tfCusto.setText("");
				tfDescricao.setText("");
				tfObs.setText("");
				tfNome.grabFocus();
			}
		});
		panel.add(btnLimpar);

		JButton btnCancelar = new JButton("Cancelar");
		panel.add(btnCancelar);
		// Adicionando imagem.
		Image imgCancelar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnCancelar.setIcon(new ImageIcon(imgCancelar));
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 68, 374, 2);
		contentPane.add(separator);

		JLabel lblCadastroDeMateriais = new JLabel("Cadastro de Materiais");
		// Adicionando imagem.
		Image imgMateriais = new ImageIcon(this.getClass().getResource(
				"/50px/materiais.png")).getImage();
		lblCadastroDeMateriais.setIcon(new ImageIcon(imgMateriais));
		lblCadastroDeMateriais.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCadastroDeMateriais.setBounds(10, 10, 343, 50);
		contentPane.add(lblCadastroDeMateriais);

		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(10, 124, 80, 14);
		contentPane.add(lblCategoria);

		JButton btnAdicionarCategoria = new JButton("Adicionar categoria");
		btnAdicionarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					
					String y = JOptionPane
							.showInputDialog("Digite o nome da nova gategoria: ");
					if (y.equals("") || y.equals(" ") || y.equals(null)) {

					} else {

						// cria a conecxão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						String comando = "select categoriamaterial_nome from tbl_categoriamaterial;";
				
						ResultSet resultSet = st.executeQuery(comando);

						while (resultSet.next()) {

							String nome = resultSet
									.getString("categoriamaterial_nome");

							if (nome.equals(y)) {
								JOptionPane.showMessageDialog(null,
										"Já existe essa categoria");

								break;
							} else {
								st.execute("insert into tbl_categoriamaterial set categoriamaterial_nome ='"
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

				String comando = "select categoriamaterial_nome from tbl_categoriamaterial;";

				try {

					Class.forName("com.mysql.jdbc.Driver");

					// Criando conexão.
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					comboBox.removeAllItems();

					while (resultSet.next()) {

						String nome = resultSet
								.getString("categoriamaterial_nome");

						comboBox.addItem(nome);

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
		btnAdicionarCategoria.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnAdicionarCategoria.setBounds(110, 151, 204, 16);
		contentPane.add(btnAdicionarCategoria);

		JLabel label = new JLabel("*");
		label.setForeground(Color.RED);
		label.setBackground(Color.RED);
		label.setBounds(78, 99, 25, 14);
		contentPane.add(label);

		JLabel label_1 = new JLabel("*");
		label_1.setForeground(Color.RED);
		label_1.setBackground(Color.RED);
		label_1.setBounds(78, 124, 25, 14);
		contentPane.add(label_1);

		JLabel lblCampos = new JLabel("* - Campos Obrigat\u00F3rios.");
		lblCampos.setForeground(Color.RED);
		lblCampos.setBackground(Color.RED);
		lblCampos.setBounds(100, 52, 204, 14);
		contentPane.add(lblCampos);

		JLabel label_3 = new JLabel("R$");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setBounds(89, 174, 34, 14);
		contentPane.add(label_3);

		JLabel label_2 = new JLabel("*");
		label_2.setForeground(Color.RED);
		label_2.setBackground(Color.RED);
		label_2.setBounds(77, 174, 25, 14);
		contentPane.add(label_2);

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

				comboBox.addItem(nome);

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
