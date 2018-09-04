package view.jFrames.cadastros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.IllegalFormatException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import modelHerancas.Pedido;
import modelSuplerclasses.Cliente;
import modelSuplerclasses.Orcamento;
import modelSuplerclasses.Pagamento;
import modelSuplerclasses.Visita;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import org.joda.time.DateTime;

import view.jDialogs.JDSelecionarBuscaClientes;
import view.jDialogs.JDSelecionarBuscarOrcamentos;
import view.jDialogs.JDSelecionarBuscarVisitas;
import Model.DAO.ConexaoMySQL;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class JFCPedido extends JDialog {

	private JPanel contentPane;
	private JTextField tfCliente;
	private JTextField tfOrcamento;
	private JTextField tfvisita;

	Pedido p = new Pedido();
	Orcamento o = new Orcamento();
	Cliente c = new Cliente();
	Visita v = new Visita();
	Pagamento pa = new Pagamento();
	private JTextField tfEntrada;

	public JFCPedido() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

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
				"/16px/pedidos.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Cadastro Pedido");
		setBounds(100, 100, 380, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(10, 129, 125, 14);
		contentPane.add(lblCliente);

		JLabel lblOramento = new JLabel("Or\u00E7amento:");
		lblOramento.setBounds(10, 160, 125, 14);
		contentPane.add(lblOramento);

		tfCliente = new JTextField();
		tfCliente.setBounds(128, 126, 143, 20);
		contentPane.add(tfCliente);
		tfCliente.setColumns(10);

		tfOrcamento = new JTextField();
		tfOrcamento.setBounds(128, 157, 143, 20);
		contentPane.add(tfOrcamento);
		tfOrcamento.setColumns(10);

		JButton btnCancelar = new JButton("Cancelar");
		// Adicionando imagem.
		Image imgCancelar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnCancelar.setIcon(new ImageIcon(imgCancelar));
		;
		btnCancelar.setToolTipText("Cancelar");
		btnCancelar.setBounds(256, 244, 118, 20);
		contentPane.add(btnCancelar);

		JButton btnLimpar = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		btnLimpar.setIcon(new ImageIcon(imgLimpar));
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setBounds(128, 244, 118, 20);
		contentPane.add(btnLimpar);

		JDateChooser dataEntrega = new JDateChooser();
		dataEntrega.setBounds(128, 210, 236, 20);
		contentPane.add(dataEntrega);

		JButton btnSalvar = new JButton("Salvar");
		// Adicionando imagem.
		Image imgSalvar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(imgSalvar));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tfCliente.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campo Cliente não Preenchido", "ERRO",
							JOptionPane.ERROR_MESSAGE);
				} else if (tfOrcamento.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campo Orçamento não Preenchido", "ERRO",
							JOptionPane.ERROR_MESSAGE);
				} else if (tfvisita.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campo Visita não Preenchido", "ERRO",
							JOptionPane.ERROR_MESSAGE);
				} else if (dataEntrega.getDate().toString().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Campo Data de Entrega não Preenchido", "ERRO",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {

						int stg = 0;

						// Criando conexão com o banco.

						// Pegando valores do orçamento.
						Class.forName("com.mysql.jdbc.Driver");

						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();
						String comand = "select orcamento_valorTotal, orcamento_parcelamento, orcamento_metodoPagamento from tbl_orcamento where orcamento_id = "
								+ o.getId() + ";";

						java.sql.Statement stOrcamento = con.createStatement();

						ResultSet resultSet = stOrcamento.executeQuery(comand);
						while (resultSet.next()) {
							o.setValorTotal(resultSet
									.getDouble("orcamento_valorTotal"));
							o.setParcelamento(resultSet
									.getDouble("orcamento_parcelamento"));
							o.setFormaPagamento(resultSet
									.getString("orcamento_metodoPagamento"));
						}
						// Fim orçamento.

						double x = o.getValorTotal()
								- Double.parseDouble(tfEntrada.getText());

						// Começo cadastro do pedido e pagamento.
						java.sql.Statement st = con.createStatement();
						java.sql.Statement st2 = con.createStatement();

						Pedido p = new Pedido();
						// formata data para padrão americano
						SimpleDateFormat FormatoData = new SimpleDateFormat(
								"yyyy/MM/dd");

						p.setClienteId(c.getId());
						p.setVisitaid(v.getId());
						p.setOrcamentoId(o.getId());
						p.setPedidonome(o.getDescricaoproduto());
						p.setHistorico("Pedido criado no dia: "
								+ FormatoData.format(new DateTime().toDate())
								+ "\n");
						p.setNivel(2);

						st.execute("insert into tbl_pedido (pedido_dataentrega, pedido_nome, pedido_gerado, cliente_id, visita_id, pedido_historico, pedido_nivel, pedido_materiaisencomendados, orcamento_id)"
								+ " values('"
								+ FormatoData.format(dataEntrega.getDate())
								+ "', '"
								+ p.getPedidonome()
								+ "', '"
								+ FormatoData.format(new DateTime().toDate())
								+ "', "
								+ c.getId()
								+ ", "
								+ v.getId()
								+ ", 'Pedido criado dia "
								+ DateTime.now()
								+ "', "
								+ p.getNivel()
								+ ", 'não', "
								+ o.getId() + ");");

						String select = "select pedido_id from tbl_pedido where orcamento_id = "
								+ o.getId() + ";";

						ResultSet resultSet2 = st.executeQuery(select);

						while (resultSet2.next()) {
							stg = (resultSet2.getInt("pedido_id"));

						}

						st2.execute("insert into tbl_pagamento (pagamento_forma, pagamento_valor, pagamento_parcelamento, pagamento_status, pagamento_valorPago, pagamento_valorPendente, pagamento_valorPrazo, pagamento_valorJuros, pedido_id, pagamento_parcelasRestantes) values('"
								+ o.getFormaPagamento()
								+ "', "
								+ o.getValorTotal()
								+ ", "
								+ o.getParcelamento()
								+ ", 'Pendente', "
								+ tfEntrada.getText()
								+ ", "
								+ x
								+ ", "
								+ o.getTotalPrazo()
								+ ","
								+ o.getJuros()
								+ ","
								+ stg + ", + " + o.getParcelamento() + ") ;");

						// pedido cadastrado.
						JOptionPane.showMessageDialog(null,
								"Pedido cadastrado com sucesso!");

						
						
						HashMap param  = new HashMap(); 					
						JasperPrint jp = JasperFillManager.fillReport(
								"src/iReport/ContratoFinal.jasper", param, con);
						JasperViewer jw = new JasperViewer(jp,false);
						jw.setVisible(true);
						
                 															// pasta de destino do pdf
						JasperExportManager.exportReportToPdfFile(jp, "src/iReport/ContratoFinal.pdf");
						
						st.close();
						st2.close();
						con.close();

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
						erroFormato.printStackTrace();
					}

					catch (ClassNotFoundException erroJDBC) {
						JOptionPane
								.showMessageDialog(
										null,
										"Falha no cadastro. \nPor favor, entre em contato com um técnico, ou tente novamente mais tarde.",
										"Aviso!", JOptionPane.WARNING_MESSAGE);
						erroJDBC.printStackTrace();
					} catch (IllegalArgumentException illegalArgument) {
						JOptionPane
								.showMessageDialog(
										null,
										"Algum campo foi preenchido incorretamente. \nPor favor, verifique.",
										"Aviso!", JOptionPane.WARNING_MESSAGE);
						illegalArgument.printStackTrace();
					} catch (Exception exception) {
						JOptionPane
								.showMessageDialog(
										null,
										"Algo deu errado. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
										"Erro!", JOptionPane.ERROR_MESSAGE);
						exception.printStackTrace();
					}

				}

			}

		});
		btnSalvar.setToolTipText("Salvar Dados");
		btnSalvar.setBounds(0, 244, 118, 20);
		contentPane.add(btnSalvar);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 70, 374, 2);
		contentPane.add(separator);

		JLabel lblCadastroDePedido = new JLabel("Cadastro de Pedido");
		// Adicionando imagem.
		Image imgPedido = new ImageIcon(this.getClass().getResource(
				"/50px/pedidos.png")).getImage();
		lblCadastroDePedido.setIcon(new ImageIcon(imgPedido));
		lblCadastroDePedido.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCadastroDePedido.setBounds(10, 11, 343, 50);
		contentPane.add(lblCadastroDePedido);

		JLabel label = new JLabel("*");
		label.setForeground(Color.RED);
		label.setBackground(Color.RED);
		label.setBounds(99, 101, 25, 14);
		contentPane.add(label);

		JLabel label_1 = new JLabel("*");
		label_1.setForeground(Color.RED);
		label_1.setBackground(Color.RED);
		label_1.setBounds(99, 132, 25, 14);
		contentPane.add(label_1);

		JLabel lblCampos = new JLabel("* - Campos Obrigat\u00F3rios.");
		lblCampos.setForeground(Color.RED);
		lblCampos.setBackground(Color.RED);
		lblCampos.setBounds(110, 52, 254, 14);
		contentPane.add(lblCampos);

		JLabel lblBuscar = new JLabel("Buscar/Novo");
		lblBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					JDSelecionarBuscaClientes frame = new JDSelecionarBuscaClientes(
							c);
					frame.setModal(true);
					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

				tfCliente.setText(c.getNome());

			}
		});
		lblBuscar.setBounds(288, 129, 76, 14);
		contentPane.add(lblBuscar);

		JLabel lblBuscarnovo = new JLabel("Buscar/Novo");
		lblBuscarnovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					JDSelecionarBuscarOrcamentos frame = new JDSelecionarBuscarOrcamentos(
							o);
					frame.setModal(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

				tfOrcamento.setText(String.valueOf(o.getId()));

			}
		});
		lblBuscarnovo.setBounds(288, 160, 76, 14);
		contentPane.add(lblBuscarnovo);

		JLabel lblVisita = new JLabel("Visita:");
		lblVisita.setBounds(10, 98, 46, 14);
		contentPane.add(lblVisita);

		JLabel label_2 = new JLabel("*");
		label_2.setForeground(Color.RED);
		label_2.setBackground(Color.RED);
		label_2.setBounds(99, 160, 25, 14);
		contentPane.add(label_2);

		tfvisita = new JTextField();
		tfvisita.setColumns(10);
		tfvisita.setBounds(128, 98, 143, 20);
		contentPane.add(tfvisita);

		JLabel lblBuscarnova = new JLabel("Buscar/Nova");
		lblBuscarnova.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					JDSelecionarBuscarVisitas frame = new JDSelecionarBuscarVisitas(
							v);
					frame.setModal(true);
					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

				tfvisita.setText(String.valueOf(v.getId()));

			}
		});
		lblBuscarnova.setBounds(288, 98, 76, 14);
		contentPane.add(lblBuscarnova);

		JLabel lblNewLabel = new JLabel("Valor de Entrada:");
		lblNewLabel.setBounds(10, 185, 118, 14);
		contentPane.add(lblNewLabel);

		tfEntrada = new JTextField();
		tfEntrada.setText("0");
		tfEntrada.setEditable(false);
		tfEntrada.setBounds(128, 185, 143, 20);
		contentPane.add(tfEntrada);
		tfEntrada.setColumns(10);

		JButton btnPagar = new JButton("Pagar");
		// Adicionando imagem.
		Image imgPagar = new ImageIcon(this.getClass().getResource(
				"/16px/orcamento.png")).getImage();
		btnPagar.setIcon(new ImageIcon(imgPagar));
		btnPagar.setToolTipText("Pagar um valor de entrada");
		btnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tfEntrada.setEditable(true);
			}
		});
		btnPagar.setBounds(281, 185, 83, 23);
		contentPane.add(btnPagar);

		JLabel label_3 = new JLabel("R$");
		label_3.setBounds(99, 185, 25, 14);
		contentPane.add(label_3);

		JLabel lblDateDeEntrega = new JLabel("Date de Entrega:");
		lblDateDeEntrega.setBounds(10, 210, 118, 14);
		contentPane.add(lblDateDeEntrega);

		JLabel label_4 = new JLabel("*");
		label_4.setForeground(Color.RED);
		label_4.setBackground(Color.RED);
		label_4.setBounds(99, 210, 25, 14);
		contentPane.add(label_4);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				tfCliente.setText("");
				tfOrcamento.setText("");
				tfvisita.setText("");
				tfCliente.grabFocus();

			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
