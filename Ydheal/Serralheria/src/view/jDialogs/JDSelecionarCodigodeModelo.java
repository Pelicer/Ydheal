package view.jDialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.IllegalFormatException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.DAO.ConexaoMySQL;
import modelSuplerclasses.Amostra;
import view.jFrames.cadastros.JFCModelo;

@SuppressWarnings("serial")
public class JDSelecionarCodigodeModelo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfnome;
	private JTable table;

	DefaultTableModel modelo = new DefaultTableModel();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JDSelecionarCodigodeModelo(Amostra m) {

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/modelo.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Selecionar C\u00F3digo");
		setBounds(100, 100, 421, 398);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);

		{
			JLabel lblNomeDoModelo = new JLabel("Nome do modelo:");
			lblNomeDoModelo.setBounds(10, 15, 112, 14);
			contentPanel.add(lblNomeDoModelo);
		}

		tfnome = new JTextField();
		tfnome.setBounds(132, 12, 125, 20);
		contentPanel.add(tfnome);
		tfnome.setColumns(10);

		JComboBox cbcategoria = new JComboBox();
		cbcategoria.setBounds(132, 37, 125, 20);
		contentPanel.add(cbcategoria);

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

		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}

		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(10, 40, 112, 14);
		contentPanel.add(lblCategoria);
		{
			JButton btnBuscar = new JButton("Buscar");
			Image imgBuscar = new ImageIcon(this.getClass().getResource(
					"/16px/search.png")).getImage();
			btnBuscar.setIcon(new ImageIcon(imgBuscar));
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if ((tfnome.getText().equals(""))
							&& (cbcategoria.getSelectedItem().equals("Nenhuma"))) {

						String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id order by modelo_id;";

						while (table.getModel().getRowCount() > 0) {
							((DefaultTableModel) table.getModel()).removeRow(0);
						}

						Cod(comando);

					}

					else if ((!tfnome.getText().equals(""))
							&& (cbcategoria.getSelectedItem().equals("Nenhuma"))) {

						String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_nome like '"
								+ tfnome.getText() + "%' order by modelo_id;";

						while (table.getModel().getRowCount() > 0) {
							((DefaultTableModel) table.getModel()).removeRow(0);
						}

						Cod(comando);

					}

					else if ((tfnome.getText().equals(""))
							&& (!cbcategoria.getSelectedItem()
									.equals("Nenhuma"))) {

						String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where categoriamodelo_nome = '"
								+ cbcategoria.getSelectedItem()
								+ "' order by modelo_id;";

						while (table.getModel().getRowCount() > 0) {
							((DefaultTableModel) table.getModel()).removeRow(0);
						}

						Cod(comando);

					}

					else if ((!tfnome.getText().equals(""))
							&& (!cbcategoria.getSelectedItem()
									.equals("Nenhuma"))) {

						String comando = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id where modelo_nome like '"
								+ tfnome.getText()
								+ "%' and categoriamodelo_nome = '"
								+ cbcategoria.getSelectedItem()
								+ "' order by modelo_id;";

						while (table.getModel().getRowCount() > 0) {
							((DefaultTableModel) table.getModel()).removeRow(0);
						}

						Cod(comando);

					}

				}
			});
			btnBuscar.setBounds(283, 36, 112, 23);
			contentPanel.add(btnBuscar);
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 88, 385, 227);
		contentPanel.add(scrollPane);

		{
			modelo.addColumn("Cod.");
			modelo.addColumn("Nome.");
			modelo.addColumn("Categoria");
			modelo.addColumn("Custo");

		}

		table = new JTable(modelo) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		// Impede a reordenação das colunas.
		table.getTableHeader().setReorderingAllowed(false);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					m.setId(Integer.parseInt(String.valueOf(modelo.getValueAt(
							table.getSelectedRow(), 0))));

					m.setAmostraPreco(Double.parseDouble(String.valueOf(modelo
							.getValueAt(table.getSelectedRow(), 3))));

					dispose();

				}
			}
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(1);
		table.getColumnModel().getColumn(1).setPreferredWidth(10);
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(0).setPreferredWidth(10);

		scrollPane.setViewportView(table);

		String comando1 = "select categoriamodelo_nome, modelo_id, modelo_nome, modelo_custo from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id order by modelo_id;";

		while (table.getModel().getRowCount() > 0) {
			((DefaultTableModel) table.getModel()).removeRow(0);
		}

		Cod(comando1);

		JButton btnNewButton = new JButton("Novo modelo");
		Image imgModelo = new ImageIcon(this.getClass().getResource(
				"/16px/modelo.png")).getImage();
		btnNewButton.setIcon(new ImageIcon(imgModelo));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					JFCModelo frame = new JFCModelo();
					frame.setModal(true);
					frame.setVisible(true);
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
				}

				catch (Exception erro) {
					erro.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

				while (table.getModel().getRowCount() > 0) {
					((DefaultTableModel) table.getModel()).removeRow(0);
				}

				String comando = "select categoriamodelo_nome, modelo_id, modelo_nome from tbl_modelo inner join tbl_categoriamodelo on tbl_categoriamodelo.categoriamodelo_id = tbl_modelo.categoriamodelo_id order by modelo_id;";

				Cod(comando);

			}
		});
		btnNewButton.setBounds(283, 11, 112, 23);
		contentPanel.add(btnNewButton);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Selecionar");
				Image imgOk = new ImageIcon(this.getClass().getResource(
						"/16px/ok.png")).getImage();
				okButton.setIcon(new ImageIcon(imgOk));
				okButton.setToolTipText("Selecionar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						m.setId(Integer.parseInt(String.valueOf(modelo
								.getValueAt(table.getSelectedRow(), 0))));

						m.setAmostraPreco(Double.parseDouble(String
								.valueOf(modelo.getValueAt(
										table.getSelectedRow(), 3))));

						dispose();

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				Image imgCancel = new ImageIcon(this.getClass().getResource(
						"/16px/cancelar.png")).getImage();
				cancelButton.setIcon(new ImageIcon(imgCancel));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);

			}
		}
	}

	public void Cod(String comando) {

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

				String categoria = resultSet.getString("categoriamodelo_nome");

				modelo.addRow(new Object[] { id, nome, categoria, custo });

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
		}

		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}

	}
}
