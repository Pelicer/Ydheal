package view.jDialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.IllegalFormatException;

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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelSuplerclasses.Visita;
import view.jFrames.cadastros.JFCVisitas;
import Model.DAO.ConexaoMySQL;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class JDSelecionarBuscarVisitas extends JDialog {

	public int cont = 0;

	// cria o modelo da tabela
	DefaultTableModel modelotbl_visitas = new DefaultTableModel();

	private JPanel contentPane;
	private JTable tabletbl_visitas;
	private JPanel panel_1;
	private JLabel lblCdigo;
	private JLabel lblNome;
	private JLabel lblHorrio;
	private JLabel lblNomeDoCliente;
	private JLabel lblEndereo;
	private JTextField txtCodigo;
	private JTextField txtCliente;
	private JTextField txthorario;
	private JTextField txtendereço;
	private JButton btnFiltrar;
	private JButton btnLimpar;
	private JPanel panel_4;
	private JButton btnExcluir;
	private JLabel lblVisitas;
	private JButton btnSelecionarVisita;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JDSelecionarBuscarVisitas(Visita v) {

		Image imgIcon = new ImageIcon(this.getClass().getResource(
				"/16px/visita.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Visitas Finalizadas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 992, 740);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// inicia no centro da tela
		setLocationRelativeTo(null);
		contentPane.setLayout(new BorderLayout(0, 0));

		// formata a data para ficar igual no padrão americano
		SimpleDateFormat Formatodataam = new SimpleDateFormat("yyyy/MM/dd");

		panel_1 = new JPanel();
		panel_1.setLayout(null);
		contentPane.add(panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 88, 966, 592);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		JButton btnNovo = new JButton("Novo");
		// Adicionando imagem.
		Image imgNovo = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnNovo.setIcon(new ImageIcon(imgNovo));
		btnNovo.setToolTipText("Criar Novo Registro");

		btnNovo.setBounds(198, 11, 116, 23);
		panel_2.add(btnNovo);

		panel_4 = new JPanel();
		panel_4.setBounds(0, 0, 966, 88);
		panel_1.add(panel_4);
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setLayout(null);

		txthorario = new JTextField();
		txthorario.setBounds(211, 39, 86, 20);
		panel_4.add(txthorario);
		txthorario.setColumns(10);

		lblHorrio = new JLabel("Hor\u00E1rio:");
		lblHorrio.setBounds(159, 42, 55, 14);
		panel_4.add(lblHorrio);

		lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setBounds(307, 42, 69, 14);
		panel_4.add(lblEndereo);

		txtendereço = new JTextField();
		txtendereço.setBounds(372, 39, 419, 20);
		panel_4.add(txtendereço);
		txtendereço.setColumns(10);

		JComboBox cbstatus = new JComboBox();
		cbstatus.setModel(new DefaultComboBoxModel(new String[] { "Todas",
				"Em aberto", "Conclu\u00EDdas", "Canceladas" }));
		cbstatus.setBounds(801, 39, 135, 20);
		panel_4.add(cbstatus);

		lblCdigo = new JLabel("C\u00F3digo:");
		lblCdigo.setBounds(159, 14, 55, 14);
		panel_4.add(lblCdigo);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(211, 11, 86, 20);
		panel_4.add(txtCodigo);
		txtCodigo.setColumns(10);

		lblNome = new JLabel("Data:");
		lblNome.setBounds(307, 14, 46, 14);
		panel_4.add(lblNome);

		lblNomeDoCliente = new JLabel("Nome do Cliente:");
		lblNomeDoCliente.setBounds(542, 14, 103, 14);
		panel_4.add(lblNomeDoCliente);

		txtCliente = new JTextField();
		txtCliente.setBounds(629, 14, 307, 20);
		panel_4.add(txtCliente);
		txtCliente.setColumns(10);

		lblVisitas = new JLabel("Visitas");
		// Adicionando imagem.
		Image imgVisita = new ImageIcon(this.getClass().getResource(
				"/50px/visita.png")).getImage();
		lblVisitas.setIcon(new ImageIcon(imgVisita));
		lblVisitas.setBounds(10, 6, 343, 79);
		panel_4.add(lblVisitas);
		lblVisitas.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(372, 14, 160, 20);
		panel_4.add(dateChooser);

		btnLimpar = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(imgLimpar));
		btnLimpar.setToolTipText("Limpar Filtros");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				txtCodigo.setText("");
				dateChooser.setDate(null);

				txtCliente.setText("");
				txthorario.setText("");
				txtendereço.setText("");

			}
		});
		btnLimpar.setBounds(450, 11, 116, 23);
		panel_2.add(btnLimpar);
		btnExcluir = new JButton("Excluir");
		btnExcluir.setToolTipText("Excluir Registro");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 946, 511);
		panel_2.add(scrollPane);

		tabletbl_visitas = new JTable(modelotbl_visitas) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};
		scrollPane.setViewportView(tabletbl_visitas);

		// Detecção de clique duplo.
		tabletbl_visitas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					v.setId(Integer.parseInt(String.valueOf(modelotbl_visitas
							.getValueAt(tabletbl_visitas.getSelectedRow(), 0))));

					v.setNome(String.valueOf(modelotbl_visitas.getValueAt(
							tabletbl_visitas.getSelectedRow(), 3)));

					JOptionPane.showMessageDialog(null, "Visita selecionada!");

					dispose();

				}
			}
		});

		JLabel lblregistrosencontrados = new JLabel("0");
		lblregistrosencontrados.setForeground(new Color(0, 128, 0));
		lblregistrosencontrados.setHorizontalAlignment(SwingConstants.CENTER);
		lblregistrosencontrados.setBounds(740, 45, 46, 14);
		panel_2.add(lblregistrosencontrados);

		JLabel lbl = new JLabel("Registros Encontrados");
		lbl.setBounds(793, 45, 141, 14);
		panel_2.add(lbl);

		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				setModal(false);

				// Abre o formulário de cadastro de visitas.
				try {
					JFCVisitas frame = new JFCVisitas();
					frame.setLocationRelativeTo(null);
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

				catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

				while (tabletbl_visitas.getModel().getRowCount() > 0) {
					((DefaultTableModel) tabletbl_visitas.getModel())
							.removeRow(0);
				}

				Carregar_tela_visita();

				lblregistrosencontrados.setText(String.valueOf(cont));

			}
		});

		// impede que as colunas sejam reordemadas
		tabletbl_visitas.getTableHeader().setReorderingAllowed(false);

		btnFiltrar = new JButton("Filtrar");
		// Adicionando Imagem.
		Image imgFiltrar = new ImageIcon(this.getClass().getResource(
				"/16px/search.png")).getImage();
		btnFiltrar.setIcon(new ImageIcon(imgFiltrar));
		btnFiltrar.setToolTipText("Filtrar Registros");
		btnFiltrar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (cbstatus.getSelectedItem().equals("Todas")) {

					// Ifs com um campo só
					if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_status = 0 or visita_status = 1  or visita_status = 11;";

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {
						// consulta se tiver algo no campo cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText() + "%';";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {

						// pesquisa por código
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText() + "';";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {
						// no campo endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_endereco LIKE '"
								+ txtendereço.getText() + "%';";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)) {
						// no campo horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_horario like '"
								+ txthorario.getText() + "%';";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))) {
						// no campo data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "';";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}// fim dos if com um campo só

					// ifs com 2 campos
					// código e mais um
					else if ((txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtCliente.getText().equals(""))) {
						// código e cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText() + "%';";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// código e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txthorario.getText().equals(""))) {
						// código e horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!txtCodigo.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// código e data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'";
						JOptionPane.showMessageDialog(null,
								"Data pesquisada no criterio com 2");

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}// fim dos ifs com codigo e mais um

					// data e mais um
					else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCliente.getText().equals(""))) {
						// data e cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtendereço.getText().equals(""))) {
						// data e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txthorario.getText().equals(""))) {
						// data e horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_horario like '"
								+ txthorario.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}
					// fim dos ifs com data e mais um

					// ifs com nome e mais um

					else if ((txtCodigo.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// nome e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText() + "%'	";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))) {
						// nome e horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText() + "%'	";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com nome e mais um

					// ifs com hora e mais um

					else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txthorario.getText().equals(""))) {
						// hora e data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_horario like '"
								+ txthorario.getText()
								+ "%'	 and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// hora e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_horario like '"
								+ txthorario.getText()
								+ "%'	 and visita_endereco LIKE '"
								+ txtendereço.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txtCliente.getText().equals(""))) {
						// hora e cliente(nome)
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_horario like '"
								+ txthorario.getText()
								+ "%'	 and visita_nome LIKE '"
								+ txtCliente.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}
					// fim dos ifs com hora e mais um

					// ifs com endereço e mais um

					else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))) {
						// endereço e hora
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%'  and visita_horario like '"
								+ txthorario.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com endereço e mais um

					// fim dos ifs com 2 campos

					// ifs com 3 campos

					else if ((txtCliente.getText().equals(""))
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// codigo+ horario + data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// cliente + horario + data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// codigo + endereço + cliente]

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_endereco LIKE '%"
								+ txtendereço.getText()
								+ "%' and visita_nome LIKE '"
								+ txtCliente.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// data + hora + endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// endereço + horario + nome
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_endereco LIKE '%"
								+ txtendereço.getText()
								+ "%'  and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((!txtCliente.getText().equals(""))
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// codigo + data + Cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))) {
						// codigo + horario + Cliente

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_nome LIKE '"
								+ txtCliente.getText() + "%';";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// codigo + horario + endereço

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// codigo + data + endereço

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + endereço + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com 3 campos

					// ifs com 4 campos

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// endereço + codigo + horario + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))) {
						// data + codigo + horario + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + codigo + endereço + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + codigo + endereço + horario

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + nome + endereço + horario

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText() + "%'";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com 4 campos

					// ifs com 5 campos

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + nome + endereço + horario

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_id = '"
								+ txtCodigo.getText()
								+ "' ";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com 5 campos

				} // fim do if da situaão todas

				// ------------------------------------------------

				// situação: Em aberto
				else if (cbstatus.getSelectedItem().equals("Em aberto")) {
					// Ifs com um campo só
					if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_status = 0 ;";

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {
						// consulta se tiver algo no campo cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 0;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {

						// pesquisa por código
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_status = 0;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {
						// no campo endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 0;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)) {
						// no campo horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 0;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))) {
						// no campo data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 0;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}// fim dos if com um campo só

					// ifs com 2 campos
					// código e mais um
					else if ((txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtCliente.getText().equals(""))) {
						// código e cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 0;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// código e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txthorario.getText().equals(""))) {
						// código e horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!txtCodigo.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// código e data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 0;";
						JOptionPane.showMessageDialog(null,
								"Data pesquisada no criterio com 2");

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}// fim dos ifs com codigo e mais um

					// data e mais um
					else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCliente.getText().equals(""))) {
						// data e cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtendereço.getText().equals(""))) {
						// data e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txthorario.getText().equals(""))) {
						// data e horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}
					// fim dos ifs com data e mais um

					// ifs com nome e mais um

					else if ((txtCodigo.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// nome e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 0	";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))) {
						// nome e horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 0	";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com nome e mais um

					// ifs com hora e mais um

					else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txthorario.getText().equals(""))) {
						// hora e data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_horario like '"
								+ txthorario.getText()
								+ "%'	 and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// hora e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_horario like '"
								+ txthorario.getText()
								+ "%'	 and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txtCliente.getText().equals(""))) {
						// hora e cliente(nome)
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_horario like '"
								+ txthorario.getText()
								+ "%'	 and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}
					// fim dos ifs com hora e mais um

					// ifs com endereço e mais um

					else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))) {
						// endereço e hora
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com endereço e mais um

					// fim dos ifs com 2 campos

					// ifs com 3 campos

					else if ((txtCliente.getText().equals(""))
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// codigo+ horario + data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// cliente + horario + data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// codigo + endereço + cliente]

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_endereco LIKE '%"
								+ txtendereço.getText()
								+ "%' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// data + hora + endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// endereço + horario + nome
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_endereco LIKE '%"
								+ txtendereço.getText()
								+ "%'  and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((!txtCliente.getText().equals(""))
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// codigo + data + Cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))) {
						// codigo + horario + Cliente

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 0;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// codigo + horario + endereço

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// codigo + data + endereço

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + endereço + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com 3 campos

					// ifs com 4 campos

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// endereço + codigo + horario + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))) {
						// data + codigo + horario + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + codigo + endereço + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + codigo + endereço + horario

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + nome + endereço + horario

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com 4 campos

					// ifs com 5 campos

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + nome + endereço + horario

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_status = 0";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}
				}// fim do else if de visitas em aberto

				// --------------------------------------------

				// if da visita concluidas
				else if (cbstatus.getSelectedItem().equals("Concluídas")) {

					// Ifs com um campo só
					if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_status = 1 ;";

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {
						// consulta se tiver algo no campo cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 1;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {

						// pesquisa por código
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_status = 1;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {
						// no campo endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 1;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)) {
						// no campo horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 1;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))) {
						// no campo data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 1;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}// fim dos if com um campo só

					// ifs com 2 campos
					// código e mais um
					else if ((txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtCliente.getText().equals(""))) {
						// código e cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 1;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// código e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txthorario.getText().equals(""))) {
						// código e horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!txtCodigo.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// código e data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 1;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}// fim dos ifs com codigo e mais um

					// data e mais um
					else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCliente.getText().equals(""))) {
						// data e cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtendereço.getText().equals(""))) {
						// data e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txthorario.getText().equals(""))) {
						// data e horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}
					// fim dos ifs com data e mais um

					// ifs com nome e mais um

					else if ((txtCodigo.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// nome e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 1	";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))) {
						// nome e horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 1	";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com nome e mais um

					// ifs com hora e mais um

					else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txthorario.getText().equals(""))) {
						// hora e data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_horario like '"
								+ txthorario.getText()
								+ "%'	 and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// hora e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_horario like '"
								+ txthorario.getText()
								+ "%'	 and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txtCliente.getText().equals(""))) {
						// hora e cliente(nome)
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_horario like '"
								+ txthorario.getText()
								+ "%'	 and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}
					// fim dos ifs com hora e mais um

					// ifs com endereço e mais um

					else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))) {
						// endereço e hora
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com endereço e mais um

					// fim dos ifs com 2 campos

					// ifs com 3 campos

					else if ((txtCliente.getText().equals(""))
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// codigo+ horario + data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// cliente + horario + data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// codigo + endereço + cliente]

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_endereco LIKE '%"
								+ txtendereço.getText()
								+ "%' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// data + hora + endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// endereço + horario + nome
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_endereco LIKE '%"
								+ txtendereço.getText()
								+ "%'  and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((!txtCliente.getText().equals(""))
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// codigo + data + Cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))) {
						// codigo + horario + Cliente

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 1;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// codigo + horario + endereço

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// codigo + data + endereço

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + endereço + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com 3 campos

					// ifs com 4 campos

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// endereço + codigo + horario + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))) {
						// data + codigo + horario + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + codigo + endereço + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + codigo + endereço + horario

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + nome + endereço + horario

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com 4 campos

					// ifs com 5 campos

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + nome + endereço + horario

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_status = 1";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

				}

				// ---------------------------------------------------------------------

				// / ifs com aopçao concelados

				else if (cbstatus.getSelectedItem().equals("Canceladas")) {

					// Ifs com um campo só
					if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_status = 11 ;";

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {
						// consulta se tiver algo no campo cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 11;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {

						// pesquisa por código
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_status = 11;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)) {
						// no campo endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 11;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)) {
						// no campo horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 11;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))) {
						// no campo data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 11;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}// fim dos if com um campo só

					// ifs com 2 campos
					// código e mais um
					else if ((txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtCliente.getText().equals(""))) {
						// código e cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 11;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// código e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txthorario.getText().equals(""))) {
						// código e horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!txtCodigo.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// código e data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 11;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}// fim dos ifs com codigo e mais um

					// data e mais um
					else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCliente.getText().equals(""))) {
						// data e cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtendereço.getText().equals(""))) {
						// data e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txthorario.getText().equals(""))) {
						// data e horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}
					// fim dos ifs com data e mais um

					// ifs com nome e mais um

					else if ((txtCodigo.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// nome e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 11	";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))) {
						// nome e horario
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 11	";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com nome e mais um

					// ifs com hora e mais um

					else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txthorario.getText().equals(""))) {
						// hora e data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_horario like '"
								+ txthorario.getText()
								+ "%'	 and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// hora e endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_horario like '"
								+ txthorario.getText()
								+ "%'	 and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCliente.getText().equals(""))
							&& (!txtCliente.getText().equals(""))) {
						// hora e cliente(nome)
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_horario like '"
								+ txthorario.getText()
								+ "%'	 and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}
					// fim dos ifs com hora e mais um

					// ifs com endereço e mais um

					else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))) {
						// endereço e hora
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com endereço e mais um

					// fim dos ifs com 2 campos

					// ifs com 3 campos

					else if ((txtCliente.getText().equals(""))
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// codigo+ horario + data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// cliente + horario + data
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// codigo + endereço + cliente]

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_endereco LIKE '%"
								+ txtendereço.getText()
								+ "%' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((txtCliente.getText().equals(""))
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// data + hora + endereço
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// endereço + horario + nome
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_endereco LIKE '%"
								+ txtendereço.getText()
								+ "%'  and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((!txtCliente.getText().equals(""))
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))) {
						// codigo + data + Cliente
						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))) {
						// codigo + horario + Cliente

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_status = 11;";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// codigo + horario + endereço

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// codigo + data + endereço

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + endereço + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where  visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com 3 campos

					// ifs com 4 campos

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (dateChooser.getDate() == null)
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// endereço + codigo + horario + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (txtendereço.getText().equals(""))) {
						// data + codigo + horario + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					else if ((!txtCliente.getText().equals(""))
							&& (txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + codigo + endereço + nome

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "' and visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + codigo + endereço + horario

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					} else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + nome + endereço + horario

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

					// fim dos ifs com 4 campos

					// ifs com 5 campos

					else if ((!txtCliente.getText().equals(""))
							&& (!txthorario.getText().equals(""))
							&& (!dateChooser.getDate().equals(null))
							&& (!txtCodigo.getText().equals(""))
							&& (!txtendereço.getText().equals(""))) {
						// data + nome + endereço + horario

						String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_nome LIKE '"
								+ txtCliente.getText()
								+ "%' and visita_data = '"
								+ Formatodataam.format(dateChooser.getDate())
								+ "'  and visita_horario like '"
								+ txthorario.getText()
								+ "%' and visita_endereco LIKE '"
								+ txtendereço.getText()
								+ "%' and visita_id = '"
								+ txtCodigo.getText()
								+ "' and visita_status = 11";

						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						Cod(comando);

						lblregistrosencontrados.setText(String.valueOf(cont));

					}

				}

			}// fim do evento
		});
		btnFiltrar.setBounds(324, 11, 116, 23);
		panel_2.add(btnFiltrar);
		{
			modelotbl_visitas.addColumn("Cod.");
			modelotbl_visitas.addColumn("Data");
			modelotbl_visitas.addColumn("Horario");
			modelotbl_visitas.addColumn("Nome do Cliente");
			modelotbl_visitas.addColumn("Telefone");
			modelotbl_visitas.addColumn("Endereço");
		}
		tabletbl_visitas.getColumnModel().getColumn(0).setPreferredWidth(1);
		tabletbl_visitas.getColumnModel().getColumn(1).setPreferredWidth(50);
		tabletbl_visitas.getColumnModel().getColumn(2).setPreferredWidth(1);
		tabletbl_visitas.getColumnModel().getColumn(3).setPreferredWidth(100);
		tabletbl_visitas.getColumnModel().getColumn(4).setPreferredWidth(50);
		tabletbl_visitas.getColumnModel().getColumn(5).setPreferredWidth(200);

		// carrega a tela ao abrir com os registros

		Carregar_tela_visita();

		lblregistrosencontrados.setText(String.valueOf(cont));
		btnSelecionarVisita = new JButton("Selecionar Visita");
		Image imgSelecionar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSelecionarVisita.setIcon(new ImageIcon(imgSelecionar));
		btnSelecionarVisita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				v.setId(Integer.parseInt(String.valueOf(modelotbl_visitas
						.getValueAt(tabletbl_visitas.getSelectedRow(), 0))));

				v.setNome(String.valueOf(modelotbl_visitas.getValueAt(
						tabletbl_visitas.getSelectedRow(), 3)));

				JOptionPane.showMessageDialog(null, "Visita selecionada!");

				dispose();

			}
		});
		btnSelecionarVisita.setBounds(591, 11, 163, 23);
		panel_2.add(btnSelecionarVisita);

	}

	public void Cod(String comando) {

		SimpleDateFormat Formatodatabr = new SimpleDateFormat("dd/MM/yyyy");

		cont = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String id = resultSet.getString("visita_id");
				String data = resultSet.getString("visita_data");
				String horario = resultSet.getString("visita_horario");
				String nomecliente = resultSet.getString("visita_nome");
				String telefone = resultSet.getString("visita_telefone");
				String endereco = resultSet.getString("visita_endereco");

				cont = (cont + 1);

				modelotbl_visitas
						.addRow(new Object[] {
								id,
								Formatodatabr.format(Date.valueOf(String
										.valueOf(data))), horario, nomecliente,
								telefone, endereco });

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

	public void Carregar_tela_visita() {

		SimpleDateFormat Formatodatabr = new SimpleDateFormat("dd/MM/yyyy");

		String comando = "select visita_id, visita_data, visita_horario, visita_nome, visita_telefone, visita_endereco from tbl_visita where visita_status = 1;";

		cont = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				String id = resultSet.getString("visita_id");
				String data = resultSet.getString("visita_data");
				String horario = resultSet.getString("visita_horario");
				String nomecliente = resultSet.getString("visita_nome");
				String telefone = resultSet.getString("visita_telefone");
				String endereco = resultSet.getString("visita_endereco");

				cont = (cont + 1);

				modelotbl_visitas
						.addRow(new Object[] {
								id,
								Formatodatabr.format(Date.valueOf(String
										.valueOf(data))), horario, nomecliente,
								telefone, endereco });

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
