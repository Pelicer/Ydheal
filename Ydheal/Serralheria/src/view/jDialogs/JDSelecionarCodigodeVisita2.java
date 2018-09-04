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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class JDSelecionarCodigodeVisita2 extends JDialog {

	private final JPanel contentPanel = new JPanel();

	DefaultTableModel modelo = new DefaultTableModel();

	private JTextField tfnome;
	private JTable table;

	public JDSelecionarCodigodeVisita2(Pedido pedido) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				
				// carrega os nomes ao carregar
				new Thread() {

					public void run() {

						// limpa a tabela
						while (table.getModel().getRowCount() > 0) {
							((DefaultTableModel) table.getModel()).removeRow(0);
						}

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_status = 1;";

						try {
							
							Class.forName("com.mysql.jdbc.Driver");

							java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

							java.sql.Statement st = con.createStatement();

							ResultSet resultSet = st.executeQuery(comando);
							while (resultSet.next()) {

								String id = resultSet.getString("visita_id");
								String nome = resultSet.getString("visita_nome");
								String telefone = resultSet
										.getString("visita_telefone");
								modelo.addRow(new Object[] { id, nome, telefone });

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

						catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Falha na consulta");
						}

					}

				}.start();

			}
		});

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/visita.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Selecionar C\u00F3digo");
		setBounds(100, 100, 421, 398);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);

		JLabel lblNomeDoCliente = new JLabel("Nome do Cliente:");
		lblNomeDoCliente.setBounds(10, 15, 112, 14);
		contentPanel.add(lblNomeDoCliente);

		tfnome = new JTextField();
		tfnome.setColumns(10);
		tfnome.setBounds(120, 12, 153, 20);
		contentPanel.add(tfnome);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 385, 275);
		contentPanel.add(scrollPane);

		{
			modelo.addColumn("Cod.");
			modelo.addColumn("Nome.");
			modelo.addColumn("Telefone");

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

					pedido.setVisitaid(Integer.parseInt(String.valueOf(modelo
							.getValueAt(table.getSelectedRow(), 0))));

					JOptionPane.showMessageDialog(null,
							"Código selecionado com sucesso!");

					dispose();

				}
			}
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(1);
		table.getColumnModel().getColumn(1).setPreferredWidth(20);
		table.getColumnModel().getColumn(0).setPreferredWidth(1);

		scrollPane.setViewportView(table);

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

				String comando = "select visita_telefone, visita_id, visita_nome from tbl_visita where visita_nome like '"
						+ tfnome.getText() + "%' order by visita_nome;";

				try {

					Class.forName("com.mysql.jdbc.Driver");

					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);
					while (resultSet.next()) {

						String id = resultSet.getString("visita_id");
						String nome = resultSet.getString("visita_nome");
						String telefone = resultSet
								.getString("visita_telefone");
						modelo.addRow(new Object[] { id, nome, telefone });

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

				catch (Exception erro) {
					erro.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

			}
		});

		btnBuscar.setBounds(283, 11, 112, 23);
		contentPanel.add(btnBuscar);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Selecionar");
				Image imgOk = new ImageIcon(this.getClass().getResource(
						"/16px/ok.png")).getImage();
				okButton.setIcon(new ImageIcon(imgOk));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						pedido.setVisitaid(Integer.parseInt(String
								.valueOf(modelo.getValueAt(
										table.getSelectedRow(), 0))));

						JOptionPane.showMessageDialog(null,
								"Código selecionado com sucesso!");

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

		
	}
}
