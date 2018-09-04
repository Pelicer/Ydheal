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
import modelHerancas.Pedido;
import modelSuplerclasses.Cliente;
import view.jFrames.cadastros.JFCPedido;

@SuppressWarnings("serial")
public class JDSelecionarPedidoInstalacao extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfnome;
	private JTable table;

	DefaultTableModel modelo = new DefaultTableModel();

	public JDSelecionarPedidoInstalacao(Pedido p, Cliente c) {

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/amostra.png")).getImage();
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
			JLabel lblNomeDoModelo = new JLabel("Nome do Pedido:");
			lblNomeDoModelo.setBounds(10, 14, 112, 14);
			contentPanel.add(lblNomeDoModelo);
		}

		tfnome = new JTextField();
		tfnome.setBounds(132, 11, 251, 20);
		contentPanel.add(tfnome);
		tfnome.setColumns(10);

		{
			JButton btnBuscar = new JButton("Buscar");
			Image imgBuscar = new ImageIcon(this.getClass().getResource(
					"/16px/search.png")).getImage();
			btnBuscar.setIcon(new ImageIcon(imgBuscar));
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// limpa a tabela
					while (table.getModel().getRowCount() > 0) {
						((DefaultTableModel) table.getModel()).removeRow(0);
					}

					String comando = "select pedido_id, pedido_nome from tbl_pedido where pedido_nome like '"
							+ tfnome.getText() + "%' order by pedido_nome;";

					try {

						Class.forName("com.mysql.jdbc.Driver");

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						java.sql.Statement st = con.createStatement();

						ResultSet resultSet = st.executeQuery(comando);
						while (resultSet.next()) {

							String id = resultSet.getString("pedido_id");
							String nome = resultSet.getString("pedido_nome");

							modelo.addRow(new Object[] { id, nome });

						}

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

					catch (ClassNotFoundException erroJDBC) {
						JOptionPane
								.showMessageDialog(
										null,
										"Falha no cadastro. \nPor favor, entre em contato com um técnico, ou tente novamente mais tarde.",
										"Aviso!", JOptionPane.WARNING_MESSAGE);
					}

					catch (Exception error) {
						error.printStackTrace();
						JOptionPane
								.showMessageDialog(null, "Falha na consulta");
					}

				}
			});
			btnBuscar.setBounds(210, 36, 112, 23);
			contentPanel.add(btnBuscar);
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 385, 245);
		contentPanel.add(scrollPane);

		{
			modelo.addColumn("Cod.");
			modelo.addColumn("Nome.");

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

					p.setId(Integer.parseInt(String.valueOf(modelo.getValueAt(
							table.getSelectedRow(), 0))));

					JOptionPane.showMessageDialog(null,
							"Pedido selecionado com sucesso!");

					dispose();

				}
			}
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(1);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);

		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Novo Pedido");
		Image imgPedido = new ImageIcon(this.getClass().getResource(
				"/16px/pedidos.png")).getImage();
		btnNewButton.setIcon(new ImageIcon(imgPedido));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					JFCPedido frame = new JFCPedido();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
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

				catch (Exception error) {
					error.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

			}
		});
		btnNewButton.setBounds(77, 36, 112, 23);
		contentPanel.add(btnNewButton);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Selecionar");
				okButton.setToolTipText("Selecionar");
				Image imgOk = new ImageIcon(this.getClass().getResource(
						"/16px/ok.png")).getImage();
				okButton.setIcon(new ImageIcon(imgOk));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						p.setId(Integer.parseInt(String.valueOf(modelo
								.getValueAt(table.getSelectedRow(), 0))));

						JOptionPane.showMessageDialog(null,
								"Pedido selecionado com sucesso!");

						dispose();

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				Image imgCancelar = new ImageIcon(this.getClass().getResource(
						"/16px/cancelar.png")).getImage();
				cancelButton.setIcon(new ImageIcon(imgCancelar));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);

			}
		}

		new Thread() {

			public void run() {

				while (table.getModel().getRowCount() > 0) {
					((DefaultTableModel) table.getModel()).removeRow(0);
				}

				String comando = "select pedido_id, pedido_nome from tbl_pedido where cliente_id = "
						+ c.getId() + " order by pedido_nome;";

				try {
					
					Class.forName("com.mysql.jdbc.Driver");

					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);
					while (resultSet.next()) {

						String id = resultSet.getString("pedido_id");
						String nome = resultSet.getString("pedido_nome");
						modelo.addRow(new Object[] { id, nome });

					}

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

				catch (ClassNotFoundException erroJDBC) {
					JOptionPane
							.showMessageDialog(
									null,
									"Falha no cadastro. \nPor favor, entre em contato com um técnico, ou tente novamente mais tarde.",
									"Aviso!", JOptionPane.WARNING_MESSAGE);
				}

				catch (Exception error) {
					error.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

			}

		}.start();

	}
}
