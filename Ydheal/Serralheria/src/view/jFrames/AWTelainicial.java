package view.jFrames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.geom.AffineTransform;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.IllegalFormatException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import Model.DAO.ConexaoMySQL;
import modelHerancas.Pedido;
import modelSuplerclasses.Instalacao;
import modelSuplerclasses.Pagamento;
import modelSuplerclasses.Visita;
import view.jDialogs.JDFechar;
import view.jFrames.cadastros.JFCClientes;
import view.jFrames.cadastros.JFCMateriais;
import view.jFrames.cadastros.JFCOrcamento_materiais;
import view.jFrames.cadastros.JFCPedido;
import view.jFrames.cadastros.JFCVisitas;
import view.jFrames.visualizar_registros.JDMateriaisaPedir;
import view.jFrames.visualizar_registros.JFVisualizarInstalacao;
import view.jFrames.visualizar_registros.JFVisualizarPagamento;
import view.jFrames.visualizar_registros.JFVisualizarPedido;
import view.jFrames.visualizar_registros.JFVisualizarVisita;

@SuppressWarnings("serial")
public class AWTelainicial extends JFrame {

	public JFrame frmSerralheriaYdheal;
	@SuppressWarnings("unused")
	private JTable tbl_visitas;
	private JTable tabletbl_visitas;
	private JTable tabletbl_pedidos;
	private JTable tabletbl_pagamentos;
	private JTable tabletbl_instalacoes;
	private JTable tabletbl_materiais;

	@SuppressWarnings("rawtypes")
	public AWTelainicial() {

		frmSerralheriaYdheal = new JFrame();
		Image imgIcon = new ImageIcon(this.getClass().getResource("/logo.png"))
				.getImage();
		frmSerralheriaYdheal.setIconImage(imgIcon);
		frmSerralheriaYdheal.setTitle("Serralheria Ydheal");
		frmSerralheriaYdheal.setBounds(100, 100, 1446, 707);
		frmSerralheriaYdheal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// inicia no centro da tela
		frmSerralheriaYdheal.setLocationRelativeTo(null);

		// criação dos modelos das tabelas

		DefaultTableModel modelotbl_visitas = new DefaultTableModel();
		DefaultTableModel modelotbl_materiais = new DefaultTableModel();
		DefaultTableModel modelotbl_pedidos = new DefaultTableModel();
		DefaultTableModel modelotbl_pagamentos = new DefaultTableModel();
		DefaultTableModel modelotbl_instalacoes = new DefaultTableModel();

		JMenuBar menuBar = new JMenuBar();
		frmSerralheriaYdheal.setJMenuBar(menuBar);

		JMenuItem menuItem = new JMenuItem("");
		// Adicionando Imagem.
		Image imgSerralheria = new ImageIcon(this.getClass().getResource(
				"/logo.png")).getImage();
		menuItem.setIcon(new ImageIcon(imgSerralheria));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		menuItem.setBackground(Color.WHITE);
		menuBar.add(menuItem);

		JMenuItem mntmClientes = new JMenuItem("");
		// Adicionando imagem.
		Image imgCliente = new ImageIcon(this.getClass().getResource(
				"/50px/cliente.png")).getImage();
		mntmClientes.setIcon(new ImageIcon(imgCliente));
		mntmClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abre a tela JDSelecionarBuscaClientes.
				try {
					JFClientes frame = new JFClientes();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception erroCliente) {
					erroCliente.printStackTrace();
				}
			}
		});
		mntmClientes.setHorizontalAlignment(SwingConstants.CENTER);
		mntmClientes.setBackground(Color.WHITE);
		menuBar.add(mntmClientes);

		JMenuItem mntmPedidos = new JMenuItem("");
		// Adicionando imagem.
		Image imgPedidos = new ImageIcon(this.getClass().getResource(
				"/50px/pedidos.png")).getImage();
		mntmPedidos.setIcon(new ImageIcon(imgPedidos));
		mntmPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFPedidos frame = new JFPedidos();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception erro) {
					erro.printStackTrace();
				}
			}
		});
		mntmPedidos.setHorizontalAlignment(SwingConstants.CENTER);
		mntmPedidos.setBackground(Color.WHITE);
		menuBar.add(mntmPedidos);

		JMenuItem mntmOramentos = new JMenuItem("");
		// Adicionando imagem.
		Image imgOrcamento = new ImageIcon(this.getClass().getResource(
				"/50px/orcamento.png")).getImage();
		mntmOramentos.setIcon(new ImageIcon(imgOrcamento));
		mntmOramentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFOrcamento frame = new JFOrcamento();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mntmOramentos.setHorizontalAlignment(SwingConstants.CENTER);
		mntmOramentos.setBackground(Color.WHITE);
		menuBar.add(mntmOramentos);

		JMenuItem mntmVisita = new JMenuItem("");
		// Adicionando imagem.
		Image imgVisita = new ImageIcon(this.getClass().getResource(
				"/50px/visita.png")).getImage();
		mntmVisita.setIcon(new ImageIcon(imgVisita));
		mntmVisita.setHorizontalAlignment(SwingConstants.CENTER);
		mntmVisita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Abre a tela JDSelecionarBuscarVisitas.
				try {
					JFVisitas frame = new JFVisitas();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mntmVisita.setBackground(Color.WHITE);
		menuBar.add(mntmVisita);

		JMenuItem mntmPagamentos = new JMenuItem("");
		// Adicionando imagem.
		Image imgPagamento = new ImageIcon(this.getClass().getResource(
				"/50px/pagamento.png")).getImage();
		mntmPagamentos.setIcon(new ImageIcon(imgPagamento));
		mntmPagamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Formulário de pagamentos.
				try {
					JFPagamento frame = new JFPagamento();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception erroPagamento) {
					erroPagamento.printStackTrace();
				}
			}
		});
		mntmPagamentos.setHorizontalAlignment(SwingConstants.CENTER);
		mntmPagamentos.setBackground(Color.WHITE);
		menuBar.add(mntmPagamentos);

		JMenuItem mntmNewMenuItem = new JMenuItem("");
		// Adicionando imagem.
		Image imgMaterial = new ImageIcon(this.getClass().getResource(
				"/50px/materiais.png")).getImage();
		mntmNewMenuItem.setIcon(new ImageIcon(imgMaterial));
		mntmNewMenuItem.setHorizontalAlignment(SwingConstants.CENTER);
		mntmNewMenuItem.setBackground(Color.WHITE);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abre a tela de materiais.
				try {
					JFMaterial frame = new JFMaterial();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception erro) {
					erro.printStackTrace();
				}
			}

		});
		menuBar.add(mntmNewMenuItem);

		JMenuItem mntmAmstras = new JMenuItem("");
		// Adicionando imagem.
		Image imgAmostra = new ImageIcon(this.getClass().getResource(
				"/50px/amostra.png")).getImage();
		mntmAmstras.setIcon(new ImageIcon(imgAmostra));
		mntmAmstras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Abre o formulário de amostras.
				try {
					JFAmostras frame = new JFAmostras();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception erroAmostra) {
					erroAmostra.printStackTrace();
				}
			}
		});

		JMenuItem mntmModelo = new JMenuItem("");
		mntmModelo.setBackground(Color.WHITE);
		mntmModelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFModelos frame = new JFModelos();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// Adicionando imagem.
		Image imgModelo = new ImageIcon(this.getClass().getResource(
				"/50px/modelo.png")).getImage();
		mntmModelo.setIcon(new ImageIcon(imgModelo));
		mntmModelo.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mntmModelo);
		mntmAmstras.setHorizontalAlignment(SwingConstants.CENTER);
		mntmAmstras.setBackground(Color.WHITE);
		menuBar.add(mntmAmstras);

		JMenuItem mntmSair = new JMenuItem("");
		// Adicionando imagem.
		Image imgSair = new ImageIcon(this.getClass().getResource(
				"/shutdown.png")).getImage();
		mntmSair.setIcon(new ImageIcon(imgSair));
		mntmSair.setHorizontalAlignment(SwingConstants.RIGHT);
		mntmSair.setBackground(Color.WHITE);
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					JDFechar dialog = new JDFechar();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					// Adicionando Imagem.
					Image imgSair = new ImageIcon(this.getClass().getResource(
							"/logo.png")).getImage();
					dialog.setIconImage(imgSair);
					dialog.setResizable(false);
					dialog.setVisible(true);
					dialog.setLocationRelativeTo(null);
				} catch (Exception erro) {
					erro.printStackTrace();
				}
			}
		});

		JMenuItem mntmInstalacao = new JMenuItem("");
		Image imgInstalacao = new ImageIcon(this.getClass().getResource(
				"/50px/agendar.png")).getImage();
		mntmInstalacao.setIcon(new ImageIcon(imgInstalacao));
		mntmInstalacao.setHorizontalAlignment(SwingConstants.RIGHT);
		mntmInstalacao.setBackground(Color.WHITE);
		mntmInstalacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFInstalacao frame = new JFInstalacao();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		menuBar.add(mntmInstalacao);
		menuBar.add(mntmSair);
		// Adicionando imagem.

		JPanel panel = new JPanel();
		frmSerralheriaYdheal.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		@SuppressWarnings("unused")
		class BackgroundButton extends javax.swing.JButton {
			// Carrega a sua imagem
			ImageIcon imagem = new ImageIcon(this.getClass().getResource(
					"C:/Users/Strabello/Desktop/Sem Título-2.png"));

			// criar uma subclasse de JButton e sobreescrever o método
			// paintComponent, para desenhar a imagem antes de colocar o texto
			// faz a imagem ficar no fundo

			public void paintComponent(Graphics g) {

				((Graphics2D) g).setRenderingHint(
						RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

				final Image backgroundImage = imagem.getImage();
				double scaleX = getWidth()
						/ (double) backgroundImage.getWidth(null);
				double scaleY = getHeight()
						/ (double) backgroundImage.getHeight(null);
				AffineTransform xform = AffineTransform.getScaleInstance(
						scaleX, scaleY);
				((Graphics2D) g).drawImage(backgroundImage, xform, this);

				String texto = this.getText();

				// Find the size of string s in font f in the current Graphics
				// context g.
				Font font = new Font("Dialog", Font.PLAIN, 11);
				java.awt.FontMetrics fm = g.getFontMetrics(font);
				java.awt.geom.Rectangle2D rect = fm.getStringBounds(texto, g);

				int textWidth = (int) (rect.getWidth());
				int textHeight = (int) (rect.getHeight());

				Dimension size = this.getSize();

				int x = (size.width - textWidth) / 2;
				int y = (size.height - textHeight) / 2 + fm.getAscent();

				g.drawString(texto, x, y);

			}
		}

		JButton btnVisita = new JButton("");
		// Adicionando Imagem.
		Image imgCAmostra = new ImageIcon(this.getClass().getResource(
				"/100px/visita.png")).getImage();
		btnVisita.setIcon(new ImageIcon(imgCAmostra));
		btnVisita.setBackground(Color.WHITE);
		btnVisita.setToolTipText("Cadastrar Visita");
		btnVisita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abre o formulário de cadastro de visitas.
				try {
					JFCVisitas frame = new JFCVisitas();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		// JButton.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		// JButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnVisita.setBorder(null);
		panel_1.add(btnVisita);

		JButton btnCliente = new JButton("");
		// Adicionando Imagem.
		Image imgCCliente = new ImageIcon(this.getClass().getResource(
				"/100px/cliente.png")).getImage();
		btnCliente.setIcon(new ImageIcon(imgCCliente));
		btnCliente.setBackground(Color.WHITE);
		btnCliente.setToolTipText("Cadastrar Cliente");
		btnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abre o formulário de cadastro de clientes.
				try {
					JFCClientes frame = new JFCClientes();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		panel_1.add(btnCliente);

		JButton btnPedido = new JButton("");
		// Adicionando Imagem.
		Image imgCPedido = new ImageIcon(this.getClass().getResource(
				"/100px/pedidos.png")).getImage();
		btnPedido.setIcon(new ImageIcon(imgCPedido));
		btnPedido.setBackground(Color.WHITE);
		btnPedido.setToolTipText("Cadastrar Pedido");
		btnPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abre o formulário de cadastro de pedidos.
				try {
					JFCPedido frame = new JFCPedido();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		panel_1.add(btnPedido);

		JButton btnOrcamento = new JButton("");
		// Adicionando Imagem.
		Image imgCOrcamento = new ImageIcon(this.getClass().getResource(
				"/100px/orcamento.png")).getImage();
		btnOrcamento.setIcon(new ImageIcon(imgCOrcamento));
		btnOrcamento.setBackground(Color.WHITE);
		btnOrcamento.setToolTipText("Cadastrar Or\u00E7amento");
		btnOrcamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abre o formulário de cadastro de orçamentos.
				try {
					JFCOrcamento_materiais frame = new JFCOrcamento_materiais(
							null);
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		panel_1.add(btnOrcamento);

		JButton btnMaterial = new JButton("");
		// Adicionando imagem.
		Image imgCMaterial = new ImageIcon(this.getClass().getResource(
				"/100px/materiais.png")).getImage();
		btnMaterial.setIcon(new ImageIcon(imgCMaterial));
		btnMaterial.setBackground(Color.WHITE);
		btnMaterial.setToolTipText("Cadastrar Material");
		btnMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abre o formulário de cadastro de materiais.
				try {
					JFCMateriais frame = new JFCMateriais();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		});
		btnMaterial.setBorder(null);

		panel_1.add(btnMaterial);

		JScrollPane scrollPanetbl_visita = new JScrollPane();
		panel.add(scrollPanetbl_visita);

		// add colunas
		modelotbl_visitas.addColumn("Cod.");
		modelotbl_visitas.addColumn("Visita ao(s) Cliente(s):");

		// crio a jtable com o modelo como construtor(parametro)
		tabletbl_visitas = new JTable(modelotbl_visitas) {

			// torno as céculas não editaveis
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};
		// no fim da moddificação da célula editavel, vem a deteqção de evento
		// de cliq dublo em um registro da jtable.
		tabletbl_visitas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					// Setando os valores.

					Visita visita = new Visita();

					String comando = "select * from tbl_visita where visita_id = "
							+ String.valueOf(modelotbl_visitas.getValueAt(
									tabletbl_visitas.getSelectedRow(), 0))
							+ ";";

					// fas as buscas dos valores

					try {

						Class.forName("com.mysql.jdbc.Driver");

						// cria a conecxão
						java.sql.Connection con = ConexaoMySQL
								.getConexaoMySQL();

						// não sei oq isso faz kk
						java.sql.Statement st = con.createStatement();

						ResultSet resultSet = st.executeQuery(comando);

						while (resultSet.next()) {

							visita.setId(resultSet.getInt("visita_id"));
							visita.setDataVisita(String.valueOf(resultSet
									.getDate("visita_data")));
							visita.setHorarioVisita(resultSet
									.getString("visita_horario"));
							visita.setNome(resultSet.getString("visita_nome"));
							visita.setTelefone(resultSet
									.getString("visita_telefone"));
							visita.setEndereco(resultSet
									.getString("visita_endereco"));
							visita.setObservacao(resultSet
									.getString("visita_observacao"));
							visita.setStatus(resultSet.getInt("visita_status"));

						}

						st.close();
						con.close();

					}// fim do try
					catch (SQLException erroBanco) {
						JOptionPane
								.showMessageDialog(
										null,
										"Falha na conexão com o banco de dados. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
										"Erro!", JOptionPane.ERROR_MESSAGE);
						erroBanco.printStackTrace();
					}

					catch (NumberFormatException erroNumero) {

						
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
					} catch (IllegalArgumentException illegalArgument) {
						JOptionPane
								.showMessageDialog(
										null,
										"Algo falhou durante o carregando. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
										"Erro!", JOptionPane.ERROR_MESSAGE);
						illegalArgument.printStackTrace();
					} catch (Exception exception) {
						JOptionPane
								.showMessageDialog(
										null,
										"Algo falhou durante o carregando. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
										"Erro!", JOptionPane.ERROR_MESSAGE);
						exception.printStackTrace();
					}

					try {
						JFVisualizarVisita frame = new JFVisualizarVisita(
								visita);
						frame.setLocationRelativeTo(null);
						frame.setResizable(false);
						frame.setVisible(true);
					}

					catch (NumberFormatException erroNumero) {


						erroNumero.printStackTrace();
					} catch (IllegalFormatException erroFormato) {
						JOptionPane
								.showMessageDialog(
										null,
										"Algum campo foi preenchido incorretamente. \nPor favor, verifique.",
										"Aviso!", JOptionPane.ERROR_MESSAGE);
					}

					catch (IllegalArgumentException illegalArgument) {
						JOptionPane
								.showMessageDialog(
										null,
										"Algo falhou durante o carregando. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
										"Erro!", JOptionPane.ERROR_MESSAGE);
						illegalArgument.printStackTrace();
					} catch (Exception exception) {
						JOptionPane
								.showMessageDialog(
										null,
										"Algo falhou durante o carregando. \nEntre em contato com um técnico, ou tente novamente mais tarde.",
										"Erro!", JOptionPane.ERROR_MESSAGE);
						exception.printStackTrace();
					}

				}
			}
		});
		// fim da detecção de duplo clique

		// define o tamanho da coluna, nesse caso, reduz a código
		tabletbl_visitas.getColumnModel().getColumn(1).setPreferredWidth(270);

		scrollPanetbl_visita.setViewportView(tabletbl_visitas);

		JList list = new JList();
		panel.add(list);

		JScrollPane scrollPanetbl_pedidos = new JScrollPane();
		panel.add(scrollPanetbl_pedidos);

		{
			// add colunas
			modelotbl_pedidos.addColumn("Cod.");
			modelotbl_pedidos.addColumn("Pedidos em aberto");

			// crio a jtable com o modelo como construtor(parametro)
			tabletbl_pedidos = new JTable(modelotbl_pedidos) {

				// torno as céculas não editaveis da table acima /\
				public boolean isCellEditable(int row, int column) {
					return false;
				}

			};
			// no fim da moddificação da célula editavel, vem a deteqção de
			// evento de cliq dublo em um registro da jtable.
			tabletbl_pedidos.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {

						Pedido p = new Pedido();

						String comando = "select pedido_diaencomendamateriais, pedido_materiaisencomendados, visita_id, orcamento_id, pedido_historico, pedido_id, pedido_dataentrega, pedido_gerado, pedido_nome, tbl_pedido.cliente_id from tbl_pedido inner join tbl_cliente on tbl_cliente.cliente_id = tbl_pedido.cliente_id where pedido_id = "
								+ String.valueOf(modelotbl_pedidos.getValueAt(
										tabletbl_pedidos.getSelectedRow(), 0))
								+ ";";

						p.setId(Integer.parseInt(String
								.valueOf(modelotbl_pedidos.getValueAt(
										tabletbl_pedidos.getSelectedRow(), 0))));

						try {

							// cria a conecxão
							java.sql.Connection con = ConexaoMySQL
									.getConexaoMySQL();

							// não sei oq isso faz kk
							java.sql.Statement st = con.createStatement();

							ResultSet resultSet = st.executeQuery(comando);

							while (resultSet.next()) {

								p.setDataentrega(resultSet
										.getDate("pedido_dataentrega"));
								p.setDiagerado(resultSet
										.getDate("pedido_gerado"));
								p.setPedidonome(resultSet
										.getString("pedido_nome"));
								p.setClienteId(resultSet.getInt("cliente_id"));
								p.setId(resultSet.getInt("pedido_id"));
								p.setHistorico(resultSet
										.getString("pedido_historico"));
								p.setVisitaid(resultSet.getInt("visita_id"));
								p.setOrcamentoId(resultSet
										.getInt("orcamento_id"));
								p.setMateriaisencomendados(resultSet
										.getString("pedido_materiaisencomendados"));
								p.setDiaencomendamateriais(resultSet
										.getDate("pedido_diaencomendamateriais"));

							}

							st.close();
							con.close();

						} // fim do try
						catch (Exception ee) {
							ee.printStackTrace();
							JOptionPane.showMessageDialog(null,
									"Falha na consulta");
						}

						try {
							JFVisualizarPedido frame = new JFVisualizarPedido(p);
							frame.setVisible(true);
							frame.setResizable(false);
							frame.setLocationRelativeTo(null);
						} catch (Exception erro) {
							erro.printStackTrace();
						}

					}
				}
			});
			// fim da detecção de duplo clique

			// define o tamanho da coluna, nesse caso, reduz a código
			tabletbl_pedidos.getColumnModel().getColumn(1)
					.setPreferredWidth(270);

		}
		scrollPanetbl_pedidos.setViewportView(tabletbl_pedidos);

		JScrollPane scrollPanetbl_materiais = new JScrollPane();
		panel.add(scrollPanetbl_materiais);

		{
			// add colunas
			modelotbl_materiais.addColumn("Cod.");
			modelotbl_materiais.addColumn("Materiais à pedir");

			// crio a jtable com o modelo como construtor(parametro)
			tabletbl_materiais = new JTable(modelotbl_materiais) {

				// torno as céculas não editaveis da table acima /\
				public boolean isCellEditable(int row, int column) {
					return false;
				}

			};
			// no fim da moddificação da célula editavel, vem a deteqção de
			// evento de cliq dublo em um registro da jtable.
			tabletbl_materiais.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {

						Pedido p = new Pedido();

						p.setId(Integer.parseInt(String.valueOf(modelotbl_materiais
								.getValueAt(
										tabletbl_materiais.getSelectedRow(), 0))));

						try {
							JDMateriaisaPedir dialog = new JDMateriaisaPedir(p);
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setResizable(false);
							dialog.setVisible(true);
						} catch (Exception ee) {
							ee.printStackTrace();
						}

					}
				}
			});
			// fim da detecção de duplo clique

			// define o tamanho da coluna, nesse caso, reduz a código
			tabletbl_materiais.getColumnModel().getColumn(1)
					.setPreferredWidth(270);

		}
		scrollPanetbl_materiais.setViewportView(tabletbl_materiais);

		JScrollPane scrollPanetbl_pagamentos = new JScrollPane();
		panel.add(scrollPanetbl_pagamentos);

		{
			// add colunas
			modelotbl_pagamentos.addColumn("Cod.");
			modelotbl_pagamentos.addColumn("Pedidos com pagamentos pendentes");

			// crio a jtable com o modelo como construtor(parametro)
			tabletbl_pagamentos = new JTable(modelotbl_pagamentos) {

				// torno as céculas não editaveis da table acima /\
				public boolean isCellEditable(int row, int column) {
					return false;
				}

			};
			// no fim da moddificação da célula editavel, vem a deteqção de
			// evento de cliq dublo em um registro da jtable.
			tabletbl_pagamentos.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {

						Pagamento p = new Pagamento();

						String comando = "select pagamento_id, pagamento_forma, pagamento_valor, pagamento_parcelamento, pagamento_status, pagamento_valorPago, pagamento_valorPendente, pedido_id from tbl_pagamento where pagamento_id = "
								+ modelotbl_pagamentos.getValueAt(
										tabletbl_pagamentos.getSelectedRow(), 0)
								+ ";";

						try {
							// cria a conecxão
							java.sql.Connection con = ConexaoMySQL
									.getConexaoMySQL();

							// não sei oq isso faz kk
							java.sql.Statement st = con.createStatement();

							ResultSet resultSet = st.executeQuery(comando);

							while (resultSet.next()) {
								p.setIdPagamento(resultSet
										.getInt("pagamento_id"));
								p.setFormaPagamento(resultSet
										.getString("pagamento_forma"));
								p.setParcelamentoPagamento(resultSet
										.getInt("pagamento_parcelamento"));
								p.setPedidoId(resultSet.getInt("pedido_id"));
								p.setStatus(resultSet
										.getString("pagamento_status"));
								p.setValorPagamento(resultSet
										.getDouble("pagamento_valor"));
								p.setPagamentoValorPago(resultSet
										.getDouble("pagamento_valorPago"));
								p.setPagamentoValorPendente(resultSet
										.getDouble("pagamento_valorPendente"));

							}

							st.close();
							con.close();

						}// fim do try

						catch (NumberFormatException eee) {
							eee.printStackTrace();
							JOptionPane.showMessageDialog(null,
									"Falha na consulta");
						} catch (Exception ee) {
							ee.printStackTrace();
							JOptionPane.showMessageDialog(null,
									"Falha na consulta");
						}

						try {
							JFVisualizarPagamento frame = new JFVisualizarPagamento(
									p);
							frame.setVisible(true);
							frame.setResizable(false);
							frame.setLocationRelativeTo(null);
						} catch (Exception ee) {
							ee.printStackTrace();
						}

					}
				}
			});
			// fim da detecção de duplo clique

			// define o tamanho da coluna, nesse caso, reduz a código
			tabletbl_pagamentos.getColumnModel().getColumn(1)
					.setPreferredWidth(270);

		}

		scrollPanetbl_pagamentos.setViewportView(tabletbl_pagamentos);

		JScrollPane scrollPanetbl_instalacoes = new JScrollPane();
		panel.add(scrollPanetbl_instalacoes);

		{
			// add colunas
			modelotbl_instalacoes.addColumn("Cod.");
			modelotbl_instalacoes.addColumn("Instalações pendentes");

			// crio a jtable com o modelo como construtor(parametro)
			tabletbl_instalacoes = new JTable(modelotbl_instalacoes) {

				// torno as céculas não editaveis da table acima /\
				public boolean isCellEditable(int row, int column) {
					return false;
				}

			};
			// no fim da moddificação da célula editavel, vem a deteqção de
			// evento de cliq dublo em um registro da jtable.
			tabletbl_instalacoes.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {

						Instalacao i = new Instalacao();

						String comando = "select instalacao_id, instalacao_dia, instalacao_horario, instalacao_observacao, cliente_id, pedido_id from tbl_instalacao where instalacao_id = "
								+ modelotbl_instalacoes.getValueAt(
										tabletbl_instalacoes.getSelectedRow(),
										0) + ";";

						try {

							Class.forName("com.mysql.jdbc.Driver");

							// cria a conecxão
							java.sql.Connection con = ConexaoMySQL
									.getConexaoMySQL();

							// não sei oq isso faz kk
							java.sql.Statement st = con.createStatement();

							ResultSet resultSet = st.executeQuery(comando);

							while (resultSet.next()) {

								i.setInstalacaoId(resultSet
										.getInt("instalacao_id"));
								i.setInstalacaoClienteId(resultSet
										.getInt("cliente_id"));
								i.setInstalacaoPedidoId((resultSet
										.getInt("pedido_id")));
								i.setInstalacaoData(resultSet
										.getDate("instalacao_dia"));
								i.setInstalacaoObservacao(resultSet
										.getString("instalacao_observacao"));
								i.setInstalacaoHorario(resultSet
										.getString("instalacao_horario"));

							}

							st.close();
							con.close();

						}// fim do try
						catch (Exception ee) {
							ee.printStackTrace();
							JOptionPane.showMessageDialog(null,
									"Falha na consulta");
						}

						try {
							try {
								JFVisualizarInstalacao frame = new JFVisualizarInstalacao(
										i);
								frame.setVisible(true);
								frame.setResizable(false);
								frame.setLocationRelativeTo(null);
							} catch (Exception erroViewInstalacao) {
								erroViewInstalacao.printStackTrace();
							}
						} catch (Exception eee) {
							eee.printStackTrace();
						}

					}
				}
			});
			// fim da detecção de duplo clique

			// define o tamanho da coluna, nesse caso, reduz a código
			tabletbl_instalacoes.getColumnModel().getColumn(1)
					.setPreferredWidth(270);

		}

		scrollPanetbl_instalacoes.setViewportView(tabletbl_instalacoes);

		frmSerralheriaYdheal.addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {

				// busca da tabala visita
				new Thread() {
					public void run() {

						// limpa a tabela
						while (tabletbl_visitas.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_visitas.getModel())
									.removeRow(0);
						}

						try {
							// Busca da tela inicial, visitas

							// cria a conecxão
							Connection con = ConexaoMySQL.getConexaoMySQL();
							// cria um estamento
							java.sql.Statement st = con.createStatement();

							// busca da tabela visita
							// Strin com o comando mysql
							String comando = "select visita_id, visita_nome from tbl_visita where visita_status = 0;";
							// pega o resultado obtido da execão do comando
							// mysql
							ResultSet resultSet = st.executeQuery(comando);

							while (resultSet.next()) {

								// cria-se uma variavel que recebe o campo
								// retornado da
								// consulta, aqui, enquanto tiver resultado, faz
								// algo.
								String cod = resultSet.getString("visita_id");
								String nome = resultSet
										.getString("visita_nome");

								// adiciona uma linha na tabela referente a
								// busca
								modelotbl_visitas.addRow(new Object[] { cod,
										nome });
							}
							// fim da busaca da tabela visita

							st.close();
							con.close();
						} catch (Exception erro) {
							JOptionPane.showMessageDialog(null,
									"Falha no carregamento das visitas");
							erro.printStackTrace();
						}
					}
				}.start();
				// fim da busca da tabela visita

				// busca da tabala pedidos em aberto
				new Thread() {
					public void run() {

						// limpa a tabela
						while (tabletbl_pedidos.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_pedidos.getModel())
									.removeRow(0);
						}

						try {
							// Busca da tela inicial, pedidos
							// cria a conecxão
							Connection con = ConexaoMySQL.getConexaoMySQL();
							// cria um estamento
							java.sql.Statement st = con.createStatement();

							// busca da tabela pedidos

							String comando = "select pedido_id, pedido_nome from tbl_pedido where pedido_nivel between 2 AND 3;";

							ResultSet resultSet = st.executeQuery(comando);

							while (resultSet.next()) {

								String cod = resultSet.getString("pedido_id");
								String nome = resultSet
										.getString("pedido_nome");

								// adiciona uma linha na tabela referente a
								// busca
								modelotbl_pedidos.addRow(new Object[] { cod,
										nome });
							}
							// fim da busaca da tabela pedido

							st.close();
							con.close();
						} catch (Exception erro) {
							JOptionPane.showMessageDialog(null,
									"Falha no carregamento dos pedidos");
							erro.printStackTrace();
						}
					}
				}.start();
				// fim da busca da tabela pedidos

				// busca da tabala materiais a pedir
				new Thread() {
					public void run() {

						// limpa a tabela
						while (tabletbl_materiais.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_materiais.getModel())
									.removeRow(0);
						}

						try {
							// Busca da tela inicial, materiais a pedir
							// cria a conecxão
							Connection con = ConexaoMySQL.getConexaoMySQL();
							// cria um estamento
							java.sql.Statement st = con.createStatement();

							// busca da tabela pedidos

							String comando = "select pedido_id, pedido_nome from tbl_pedido where pedido_nivel = 2;";

							ResultSet resultSet = st.executeQuery(comando);

							while (resultSet.next()) {

								String cod = resultSet.getString("pedido_id");
								String nome = resultSet
										.getString("pedido_nome");

								// adiciona uma linha na tabela referente a
								// busca
								modelotbl_materiais.addRow(new Object[] { cod,
										nome });
							}
							// fim da busaca da tabela pedido

							st.close();
							con.close();
						} catch (Exception erro) {
							JOptionPane.showMessageDialog(null,
									"Falha no carregamento dos pedidos");
							erro.printStackTrace();
						}
					}
				}.start();
				// fim da busca da tabela materiais a pedir

				// busca da tabala pagamentos
				new Thread() {
					public void run() {

						// limpa a tabela
						while (tabletbl_pagamentos.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_pagamentos.getModel())
									.removeRow(0);
						}

						try {
							// Busca da tela inicial, materiais a pedir
							// cria a conecxão
							Connection con = ConexaoMySQL.getConexaoMySQL();
							// cria um estamento
							java.sql.Statement st = con.createStatement();

							// busca da tabela pedidos

							String comando = "select pagamento_id, pedido_nome from tbl_pagamento inner join tbl_pedido where pagamento_status = 'Pendente' group by pagamento_id; ";

							ResultSet resultSet = st.executeQuery(comando);

							while (resultSet.next()) {

								String cod = resultSet
										.getString("pagamento_id");
								String nome = resultSet
										.getString("pedido_nome");

								// adiciona uma linha na tabela referente a
								// busca
								modelotbl_pagamentos.addRow(new Object[] { cod,
										nome });
							}
							// fim da busaca da tabela pedido

							st.close();
							con.close();
						} catch (Exception erro) {
							JOptionPane.showMessageDialog(null,
									"Falha no carregamento dos pedidos");
							erro.printStackTrace();
						}
					}
				}.start();
				// fim da busca da tabela pagamentos
				// busca da tabala pagamentos

				new Thread() {
					public void run() {

						// limpa a tabela
						while (tabletbl_instalacoes.getModel().getRowCount() > 0) {
							((DefaultTableModel) tabletbl_instalacoes
									.getModel()).removeRow(0);
						}

						try {
							// Busca da tela inicial, materiais a pedir
							// cria a conecxão
							Connection con = ConexaoMySQL.getConexaoMySQL();
							// cria um estamento
							java.sql.Statement st = con.createStatement();

							// busca da tabela pedidos

							String comando = "select instalacao_id, instalacao_dia from tbl_instalacao group by instalacao_id; ";

							ResultSet resultSet = st.executeQuery(comando);

							while (resultSet.next()) {

								String cod = resultSet
										.getString("instalacao_id");
								String dia = resultSet
										.getString("instalacao_dia");

								// adiciona uma linha na tabela referente a
								// busca
								modelotbl_instalacoes.addRow(new Object[] {
										cod, dia });
							}
							// fim da busaca da tabela pedido

							st.close();
							con.close();
						} catch (Exception erro) {
							JOptionPane.showMessageDialog(null,
									"Falha no carregamento das instalações.");
							erro.printStackTrace();
						}
					}
				}.start();
				// fim do evento de clique

			}

			public void windowLostFocus(WindowEvent arg0) {
			}
		});

	}

}
