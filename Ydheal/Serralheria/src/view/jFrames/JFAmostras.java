package view.jFrames;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Model.DAO.ConexaoMySQL;
import modelSuplerclasses.Amostra;
import view.jDialogs.JDVisualizarImagemGrande;
import view.jFrames.cadastros.JFCAmostra;

@SuppressWarnings("serial")
public class JFAmostras extends JFrame {

	int count = 0;

	private JPanel contentPane;
	private JTextField tfModeloId;
	@SuppressWarnings("unused")
	private JScrollPane scrollPane;
	private JTextField tfpreco;
	private JTextField tfdescricao;

	// lbls das imagens
	JLabel lblimg1 = new JLabel("");
	JLabel lblimg2 = new JLabel("");
	JLabel lblimg5 = new JLabel("");
	JLabel lblimg6 = new JLabel("");
	JLabel lblimg3 = new JLabel("");
	JLabel lblimg4 = new JLabel("");
	JLabel lblimg8 = new JLabel("");
	JLabel lblimg7 = new JLabel("");
	JLabel label_10 = new JLabel("");
	JLabel lblimg9 = new JLabel("");
	JLabel lblimg10 = new JLabel("");
	JLabel lblimg11 = new JLabel("");
	JLabel lblimg12 = new JLabel("");

	JLabel lblnumeropaginas = new JLabel("0");
	JLabel lblnumerodepaginas = new JLabel("0");

	String comando;
	String comando1;

	int registros = 12;
	boolean sent = false;

	public JFAmostras() {

		Image imgIcon = new ImageIcon(this.getClass().getResource("/16px/amostra.png"))
				.getImage();
		setIconImage(imgIcon);
		setTitle("Amostras");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 990, 740);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(12, 11, 954, 88);
		contentPane.add(panel);

		JLabel lblRelatorio = new JLabel("Amostras");
		// Adicionando imagem.
		Image imgAmostras = new ImageIcon(this.getClass().getResource(
				"/50px/amostra.png")).getImage();
		lblRelatorio.setIcon(new ImageIcon(imgAmostras));
		lblRelatorio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRelatorio.setBounds(10, 6, 215, 79);
		panel.add(lblRelatorio);

		JLabel lblModeloId = new JLabel("Cod. do modelo:");
		lblModeloId.setBounds(201, 37, 110, 14);
		panel.add(lblModeloId);

		tfModeloId = new JTextField();
		tfModeloId.setColumns(10);
		tfModeloId.setBounds(299, 34, 115, 20);
		panel.add(tfModeloId);

		JLabel lblPreo = new JLabel("Pre\u00E7o:");
		lblPreo.setBounds(671, 37, 46, 14);
		panel.add(lblPreo);

		tfpreco = new JTextField();
		tfpreco.setBounds(715, 34, 115, 20);
		panel.add(tfpreco);
		tfpreco.setColumns(10);

		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio.setBounds(445, 37, 67, 14);
		panel.add(lblDescrio);

		tfdescricao = new JTextField();
		tfdescricao.setBounds(522, 34, 115, 20);
		panel.add(tfdescricao);
		tfdescricao.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(0, 109, 966, 592);
		contentPane.add(panel_1);

		lblnumeropaginas.setHorizontalAlignment(SwingConstants.CENTER);
		lblnumeropaginas.setForeground(new Color(0, 128, 0));
		lblnumeropaginas.setBounds(886, 61, 46, 14);
		panel_1.add(lblnumeropaginas);

		JButton button = new JButton("Novo");
		// Adicionando imagem.
		Image imgNovo = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		button.setIcon(new ImageIcon(imgNovo));
		button.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {

				try {
					JFCAmostra frame = new JFCAmostra();
					frame.setModal(true);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);

				} catch (Exception ee) {
					ee.printStackTrace();
				}

				comando = "select amostra_id, amostra_descricao, amostra_preco, amostra_imagem, tbl_amostra.modelo_id from tbl_amostra inner join tbl_modelo on tbl_amostra.modelo_id = tbl_modelo.modelo_id order by amostra_id ";

				comando1 = "select COUNT(amostra_id) from tbl_amostra;";

				Cod(comando, comando1);

				// limpa os registros
				int i = 12;

				while (i != 0) {

					if (i == 1) {
						lblimg1.setIcon(null);
					} else if (i == 2) {
						lblimg2.setIcon(null);
					} else if (i == 3) {
						lblimg3.setIcon(null);
					} else if (i == 4) {
						lblimg4.setIcon(null);
					} else if (i == 5) {
						lblimg5.setIcon(null);
					} else if (i == 6) {
						lblimg6.setIcon(null);
					} else if (i == 7) {
						lblimg7.setIcon(null);
					} else if (i == 8) {
						lblimg8.setIcon(null);
					} else if (i == 9) {
						lblimg9.setIcon(null);
					} else if (i == 10) {
						lblimg10.setIcon(null);
					} else if (i == 11) {
						lblimg11.setIcon(null);
					} else if (i == 12) {
						lblimg12.setIcon(null);
					}

					i--;

				}

				registros = 12;

				boolean sent = false;

				Cod(comando, comando1);

				lblnumerodepaginas.setText(String.valueOf(count));

			}
		});

		button.setToolTipText("Criar Novo Registro");
		button.setBounds(259, 11, 116, 23);
		panel_1.add(button);

		lblnumerodepaginas.setForeground(new Color(0, 128, 0));
		lblnumerodepaginas.setHorizontalAlignment(SwingConstants.CENTER);
		lblnumerodepaginas.setBounds(122, 61, 46, 14);
		panel_1.add(lblnumerodepaginas);

		JButton button_1 = new JButton("Filtrar");
		// Adicionando imagem.
		Image imgFiltrar = new ImageIcon(this.getClass().getResource(
				"/16px/search.png")).getImage();
		button_1.setIcon(new ImageIcon(imgFiltrar));
		button_1.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent arg0) {

				if ((tfModeloId.getText().equals(""))
						&& (tfdescricao.getText().equals(""))
						&& (tfpreco.getText().equals(""))) {

					comando = "select amostra_id, amostra_descricao, amostra_preco, amostra_imagem, tbl_amostra.modelo_id from tbl_amostra inner join tbl_modelo on tbl_amostra.modelo_id = tbl_modelo.modelo_id order by amostra_id ";

					comando1 = "select COUNT(amostra_id) from tbl_amostra;";

					// limpa os registros
					int i = 12;

					while (i != 0) {

						if (i == 1) {
							lblimg1.setIcon(null);
						} else if (i == 2) {
							lblimg2.setIcon(null);
						} else if (i == 3) {
							lblimg3.setIcon(null);
						} else if (i == 4) {
							lblimg4.setIcon(null);
						} else if (i == 5) {
							lblimg5.setIcon(null);
						} else if (i == 6) {
							lblimg6.setIcon(null);
						} else if (i == 7) {
							lblimg7.setIcon(null);
						} else if (i == 8) {
							lblimg8.setIcon(null);
						} else if (i == 9) {
							lblimg9.setIcon(null);
						} else if (i == 10) {
							lblimg10.setIcon(null);
						} else if (i == 11) {
							lblimg11.setIcon(null);
						} else if (i == 12) {
							lblimg12.setIcon(null);
						}

						i--;

					}

					registros = 12;

					boolean sent = false;

					Cod(comando, comando1);

					lblnumerodepaginas.setText(String.valueOf(count));

				}

				else if ((!tfModeloId.getText().equals(""))
						&& (tfdescricao.getText().equals(""))
						&& (tfpreco.getText().equals(""))) {

					comando = "select amostra_id, amostra_descricao, amostra_preco, amostra_imagem, tbl_amostra.modelo_id from tbl_amostra inner join tbl_modelo on tbl_amostra.modelo_id = tbl_modelo.modelo_id where tbl_amostra.modelo_id = "
							+ tfModeloId.getText() + " order by amostra_id ";

					comando1 = "select COUNT(amostra_id) from tbl_amostra where modelo_id = "
							+ tfModeloId.getText() + ";";

					// limpa os registros
					int i = 12;

					while (i != 0) {
						if (i == 1) {
							lblimg1.setIcon(null);
						} else if (i == 2) {
							lblimg2.setIcon(null);
						} else if (i == 3) {
							lblimg3.setIcon(null);
						} else if (i == 4) {
							lblimg4.setIcon(null);
						} else if (i == 5) {
							lblimg5.setIcon(null);
						} else if (i == 6) {
							lblimg6.setIcon(null);
						} else if (i == 7) {
							lblimg7.setIcon(null);
						} else if (i == 8) {
							lblimg8.setIcon(null);
						} else if (i == 9) {
							lblimg9.setIcon(null);
						} else if (i == 10) {
							lblimg10.setIcon(null);
						} else if (i == 11) {
							lblimg11.setIcon(null);
						} else if (i == 12) {
							lblimg12.setIcon(null);
						}

						i--;
					}

					registros = 12;
					boolean sent = false;

					Cod(comando, comando1);

					lblnumerodepaginas.setText(String.valueOf(count));
				}

				else if ((tfModeloId.getText().equals(""))
						&& (!tfdescricao.getText().equals(""))
						&& (tfpreco.getText().equals(""))) {

					comando = "select amostra_id, amostra_descricao, amostra_preco, amostra_imagem, tbl_amostra.modelo_id from tbl_amostra inner join tbl_modelo on tbl_amostra.modelo_id = tbl_modelo.modelo_id where amostra_descricao like '"
							+ tfdescricao.getText()
							+ "%' order by amostra_id  ";

					comando1 = "select COUNT(amostra_id) from tbl_amostra where amostra_descricao like '"
							+ tfdescricao.getText() + "%';";

					// limpa os registros
					int i = 12;

					while (i != 0) {
						if (i == 1) {
							lblimg1.setIcon(null);
						} else if (i == 2) {
							lblimg2.setIcon(null);
						} else if (i == 3) {
							lblimg3.setIcon(null);
						} else if (i == 4) {
							lblimg4.setIcon(null);
						} else if (i == 5) {
							lblimg5.setIcon(null);
						} else if (i == 6) {
							lblimg6.setIcon(null);
						} else if (i == 7) {
							lblimg7.setIcon(null);
						} else if (i == 8) {
							lblimg8.setIcon(null);
						} else if (i == 9) {
							lblimg9.setIcon(null);
						} else if (i == 10) {
							lblimg10.setIcon(null);
						} else if (i == 11) {
							lblimg11.setIcon(null);
						} else if (i == 12) {
							lblimg12.setIcon(null);
						}

						i--;
					}

					registros = 12;
					boolean sent = false;

					Cod(comando, comando1);

					lblnumerodepaginas.setText(String.valueOf(count));

				}

				else if ((tfModeloId.getText().equals(""))
						&& (tfdescricao.getText().equals(""))
						&& (!tfpreco.getText().equals(""))) {

					comando = "select amostra_id, amostra_descricao, amostra_preco, amostra_imagem, tbl_amostra.modelo_id from tbl_amostra inner join tbl_modelo on tbl_amostra.modelo_id = tbl_modelo.modelo_id where amostra_preco >= "
							+ tfpreco.getText() + " order by amostra_preco  ";

					comando1 = "select COUNT(amostra_id) from tbl_amostra where amostra_preco >= "
							+ tfpreco.getText() + ";";

					// limpa os registros
					int i = 12;

					while (i != 0) {
						if (i == 1) {
							lblimg1.setIcon(null);
						} else if (i == 2) {
							lblimg2.setIcon(null);
						} else if (i == 3) {
							lblimg3.setIcon(null);
						} else if (i == 4) {
							lblimg4.setIcon(null);
						} else if (i == 5) {
							lblimg5.setIcon(null);
						} else if (i == 6) {
							lblimg6.setIcon(null);
						} else if (i == 7) {
							lblimg7.setIcon(null);
						} else if (i == 8) {
							lblimg8.setIcon(null);
						} else if (i == 9) {
							lblimg9.setIcon(null);
						} else if (i == 10) {
							lblimg10.setIcon(null);
						} else if (i == 11) {
							lblimg11.setIcon(null);
						} else if (i == 12) {
							lblimg12.setIcon(null);
						}

						i--;
					}

					registros = 12;
					boolean sent = false;

					Cod(comando, comando1);

					lblnumerodepaginas.setText(String.valueOf(count));
				}

				else if ((tfModeloId.getText().equals(""))
						&& (!tfdescricao.getText().equals(""))
						&& (!tfpreco.getText().equals(""))) {

					comando = "select amostra_id, amostra_descricao, amostra_preco, amostra_imagem, tbl_amostra.modelo_id from tbl_amostra inner join tbl_modelo on tbl_amostra.modelo_id = tbl_modelo.modelo_id where amostra_preco >= "
							+ tfpreco.getText()
							+ " and amostra_descricao like '"
							+ tfdescricao.getText()
							+ "%' order by amostra_preco ";

					comando1 = "select COUNT(amostra_id) from tbl_amostra where amostra_preco >= "
							+ tfpreco.getText()
							+ "  and amostra_descricao like '"
							+ tfdescricao.getText() + "%';";

					// limpa os registros
					int i = 12;

					while (i != 0) {
						if (i == 1) {
							lblimg1.setIcon(null);
						} else if (i == 2) {
							lblimg2.setIcon(null);
						} else if (i == 3) {
							lblimg3.setIcon(null);
						} else if (i == 4) {
							lblimg4.setIcon(null);
						} else if (i == 5) {
							lblimg5.setIcon(null);
						} else if (i == 6) {
							lblimg6.setIcon(null);
						} else if (i == 7) {
							lblimg7.setIcon(null);
						} else if (i == 8) {
							lblimg8.setIcon(null);
						} else if (i == 9) {
							lblimg9.setIcon(null);
						} else if (i == 10) {
							lblimg10.setIcon(null);
						} else if (i == 11) {
							lblimg11.setIcon(null);
						} else if (i == 12) {
							lblimg12.setIcon(null);
						}

						i--;
					}

					registros = 12;
					boolean sent = false;

					Cod(comando, comando1);

					lblnumerodepaginas.setText(String.valueOf(count));

				}

				else if ((!tfModeloId.getText().equals(""))
						&& (!tfdescricao.getText().equals(""))
						&& (tfpreco.getText().equals(""))) {

					comando = "select amostra_id, amostra_descricao, amostra_preco, amostra_imagem, tbl_amostra.modelo_id from tbl_amostra inner join tbl_modelo on tbl_amostra.modelo_id = tbl_modelo.modelo_id where amostra_descricao like '"
							+ tfdescricao.getText()
							+ "%' and tbl_amostra.modelo_id = "
							+ tfModeloId.getText() + "; order by amostra_id ";

					comando1 = "select COUNT(amostra_id) from tbl_amostra where amostra_descricao like '"
							+ tfdescricao.getText()
							+ "%' and tbl_amostra.modelo_id = "
							+ tfModeloId.getText() + ";";

					// limpa os registros
					int i = 12;

					while (i != 0) {
						if (i == 1) {
							lblimg1.setIcon(null);
						} else if (i == 2) {
							lblimg2.setIcon(null);
						} else if (i == 3) {
							lblimg3.setIcon(null);
						} else if (i == 4) {
							lblimg4.setIcon(null);
						} else if (i == 5) {
							lblimg5.setIcon(null);
						} else if (i == 6) {
							lblimg6.setIcon(null);
						} else if (i == 7) {
							lblimg7.setIcon(null);
						} else if (i == 8) {
							lblimg8.setIcon(null);
						} else if (i == 9) {
							lblimg9.setIcon(null);
						} else if (i == 10) {
							lblimg10.setIcon(null);
						} else if (i == 11) {
							lblimg11.setIcon(null);
						} else if (i == 12) {
							lblimg12.setIcon(null);
						}

						i--;
					}

					registros = 12;
					boolean sent = false;

					Cod(comando, comando1);

					lblnumerodepaginas.setText(String.valueOf(count));
				}

				else if ((!tfModeloId.getText().equals(""))
						&& (tfdescricao.getText().equals(""))
						&& (!tfpreco.getText().equals(""))) {

					comando = "select amostra_id, amostra_descricao, amostra_preco, amostra_imagem, tbl_amostra.modelo_id from tbl_amostra inner join tbl_modelo on tbl_amostra.modelo_id = tbl_modelo.modelo_id where amostra_preco >= "
							+ tfpreco.getText()
							+ " and tbl_amostra.modelo_id = "
							+ tfModeloId.getText() + " order by amostra_preco;";

					comando1 = "select COUNT(amostra_id) from tbl_amostra where amostra_preco >= "
							+ tfpreco.getText()
							+ " and tbl_amostra.modelo_id = "
							+ tfModeloId.getText() + ";";

					// limpa os registros
					int i = 12;

					while (i != 0) {
						if (i == 1) {
							lblimg1.setIcon(null);
						} else if (i == 2) {
							lblimg2.setIcon(null);
						} else if (i == 3) {
							lblimg3.setIcon(null);
						} else if (i == 4) {
							lblimg4.setIcon(null);
						} else if (i == 5) {
							lblimg5.setIcon(null);
						} else if (i == 6) {
							lblimg6.setIcon(null);
						} else if (i == 7) {
							lblimg7.setIcon(null);
						} else if (i == 8) {
							lblimg8.setIcon(null);
						} else if (i == 9) {
							lblimg9.setIcon(null);
						} else if (i == 10) {
							lblimg10.setIcon(null);
						} else if (i == 11) {
							lblimg11.setIcon(null);
						} else if (i == 12) {
							lblimg12.setIcon(null);
						}

						i--;
					}

					registros = 12;
					boolean sent = false;

					Cod(comando, comando1);

					lblnumerodepaginas.setText(String.valueOf(count));

				}

				else if ((!tfModeloId.getText().equals(""))
						&& (!tfdescricao.getText().equals(""))
						&& (!tfpreco.getText().equals(""))) {

					comando = "select amostra_id, amostra_descricao, amostra_preco, amostra_imagem, tbl_amostra.modelo_id from tbl_amostra inner join tbl_modelo on tbl_amostra.modelo_id = tbl_modelo.modelo_id where amostra_preco >= "
							+ tfpreco.getText()
							+ " and tbl_amostra.modelo_id = "
							+ tfModeloId.getText()
							+ " and amostra_descricao like '"
							+ tfdescricao.getText()
							+ "%' order by amostra_preco order by amostra_id ";

					comando1 = "select COUNT(amostra_id) from tbl_amostra where amostra_preco >= "
							+ tfpreco.getText()
							+ " and tbl_amostra.modelo_id = "
							+ tfModeloId.getText()
							+ " and amostra_descricao like '"
							+ tfdescricao.getText() + "%';";

					// limpa os registros
					int i = 12;

					while (i != 0) {
						if (i == 1) {
							lblimg1.setIcon(null);
						} else if (i == 2) {
							lblimg2.setIcon(null);
						} else if (i == 3) {
							lblimg3.setIcon(null);
						} else if (i == 4) {
							lblimg4.setIcon(null);
						} else if (i == 5) {
							lblimg5.setIcon(null);
						} else if (i == 6) {
							lblimg6.setIcon(null);
						} else if (i == 7) {
							lblimg7.setIcon(null);
						} else if (i == 8) {
							lblimg8.setIcon(null);
						} else if (i == 9) {
							lblimg9.setIcon(null);
						} else if (i == 10) {
							lblimg10.setIcon(null);
						} else if (i == 11) {
							lblimg11.setIcon(null);
						} else if (i == 12) {
							lblimg12.setIcon(null);
						}

						i--;
					}
					registros = 12;
					boolean sent = false;

					Cod(comando, comando1);

					lblnumerodepaginas.setText(String.valueOf(count));

				}

			}
		});
		button_1.setToolTipText("Filtrar Registros");
		button_1.setBounds(385, 11, 116, 23);
		panel_1.add(button_1);

		JButton button_2 = new JButton("Limpar");
		// Adicionando imagem.
		Image imgLimpar = new ImageIcon(this.getClass().getResource(
				"/16px/limpar.png")).getImage();
		button_2.setIcon(new ImageIcon(imgLimpar));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfdescricao.setText("");
				tfModeloId.setText("");
				tfpreco.setText("");
			}
		});
		button_2.setToolTipText("Limpar Filtros");
		button_2.setBounds(511, 11, 116, 23);
		panel_1.add(button_2);

		JButton button_3 = new JButton("Imprimir");
		// Adicionando imagem.
		Image imgImprimir = new ImageIcon(this.getClass().getResource(
				"/16px/imprimir.png")).getImage();
		button_3.setIcon(new ImageIcon(imgImprimir));
		button_3.setToolTipText("Imprimir");
		button_3.setBounds(637, 11, 116, 23);
		panel_1.add(button_3);

		JPanel Imagens = new JPanel();
		Imagens.setBorder(new LineBorder(new Color(0, 0, 0)));
		Imagens.setBounds(10, 78, 946, 514);
		panel_1.add(Imagens);
		Imagens.setLayout(null);
		lblimg1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				Amostra a = new Amostra();

				String id = lblimg1.getToolTipText();

				a.setId(Integer.parseInt(id));
				try {
					JDVisualizarImagemGrande frame = new JDVisualizarImagemGrande(a);
					frame.setVisible(true);
					frame.setModal(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Carregar_tela_amostra();

			}
		});

		lblimg1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblimg1.setBounds(59, 11, 203, 156);
		Imagens.add(lblimg1);
		lblimg5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Amostra a = new Amostra();

				String id = lblimg5.getToolTipText();

				a.setId(Integer.parseInt(id));
				try {
					JDVisualizarImagemGrande frame = new JDVisualizarImagemGrande(
							a);

					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			}
		});

		lblimg5.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblimg5.setBounds(59, 178, 203, 156);
		Imagens.add(lblimg5);
		lblimg2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Amostra a = new Amostra();

				String id = lblimg2.getToolTipText();

				a.setId(Integer.parseInt(id));
				try {
					JDVisualizarImagemGrande frame = new JDVisualizarImagemGrande(
							a);

					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			}
		});

		lblimg2.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblimg2.setBounds(272, 11, 203, 156);
		Imagens.add(lblimg2);
		lblimg6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Amostra a = new Amostra();

				String id = lblimg6.getToolTipText();

				a.setId(Integer.parseInt(id));
				try {
					JDVisualizarImagemGrande frame = new JDVisualizarImagemGrande(
							a);

					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			}
		});

		lblimg6.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblimg6.setBounds(272, 178, 203, 156);
		Imagens.add(lblimg6);
		lblimg3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Amostra a = new Amostra();

				String id = lblimg3.getToolTipText();

				a.setId(Integer.parseInt(id));
				try {
					JDVisualizarImagemGrande frame = new JDVisualizarImagemGrande(
							a);

					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			}
		});

		lblimg3.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblimg3.setBounds(485, 11, 203, 156);
		Imagens.add(lblimg3);
		lblimg4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Amostra a = new Amostra();

				String id = lblimg4.getToolTipText();

				a.setId(Integer.parseInt(id));
				try {
					JDVisualizarImagemGrande frame = new JDVisualizarImagemGrande(
							a);

					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			}
		});

		lblimg4.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblimg4.setBounds(698, 11, 203, 156);
		Imagens.add(lblimg4);
		lblimg8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Amostra a = new Amostra();

				String id = lblimg1.getToolTipText();

				a.setId(Integer.parseInt(id));
				try {
					JDVisualizarImagemGrande frame = new JDVisualizarImagemGrande(
							a);

					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			}
		});

		lblimg8.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblimg8.setBounds(698, 178, 203, 156);
		Imagens.add(lblimg8);
		lblimg7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Amostra a = new Amostra();

				String id = lblimg7.getToolTipText();

				a.setId(Integer.parseInt(id));
				try {
					JDVisualizarImagemGrande frame = new JDVisualizarImagemGrande(
							a);

					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			}
		});

		lblimg7.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblimg7.setBounds(485, 178, 203, 156);
		Imagens.add(lblimg7);

		label_10.setBounds(272, 178, 203, 156);
		Imagens.add(label_10);
		lblimg9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Amostra a = new Amostra();

				String id = lblimg9.getToolTipText();

				a.setId(Integer.parseInt(id));
				try {
					JDVisualizarImagemGrande frame = new JDVisualizarImagemGrande(
							a);

					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			}
		});

		lblimg9.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblimg9.setBounds(59, 345, 203, 156);
		Imagens.add(lblimg9);
		lblimg10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Amostra a = new Amostra();

				String id = lblimg10.getToolTipText();

				a.setId(Integer.parseInt(id));
				try {
					JDVisualizarImagemGrande frame = new JDVisualizarImagemGrande(
							a);

					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			}
		});

		lblimg10.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblimg10.setBounds(272, 345, 203, 156);
		Imagens.add(lblimg10);
		lblimg11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Amostra a = new Amostra();

				String id = lblimg11.getToolTipText();

				a.setId(Integer.parseInt(id));
				try {
					JDVisualizarImagemGrande frame = new JDVisualizarImagemGrande(
							a);

					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			}
		});

		lblimg11.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblimg11.setBounds(485, 345, 203, 156);
		Imagens.add(lblimg11);
		lblimg12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Amostra a = new Amostra();

				String id = lblimg12.getToolTipText();

				a.setId(Integer.parseInt(id));
				try {
					JDVisualizarImagemGrande frame = new JDVisualizarImagemGrande(
							a);

					frame.setVisible(true);
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			}
		});

		lblimg12.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblimg12.setBounds(698, 345, 203, 156);
		Imagens.add(lblimg12);

		JLabel gdfd = new JLabel("N\u00FAmero de paginas:");
		gdfd.setBounds(774, 61, 128, 14);
		panel_1.add(gdfd);

		JLabel lblPaginaAnterior = new JLabel("Pagina Anterior");
		lblPaginaAnterior.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				lblPaginaAnterior.setForeground(new Color(0, 128, 0));

				if (registros >= 12) {

					registros = (((registros / 12) * 12) - 12);

					if (registros < 0) {
						registros = 0;
					}

				}

				try {

					Class.forName("com.mysql.jdbc.Driver");
					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
					// não sei oq isso faz kk
					java.sql.Statement st = con.createStatement();

					// obtem a quantidade de registros
					int tamanho = 0;

					ResultSet x = st.executeQuery(comando1);
					while (x.next()) {

						tamanho = x.getInt("COUNT(amostra_id)");

					}

					String comandooriginal = comando;
					String comandoparaexecutar = "";

					if (sent = true) {

						comandoparaexecutar = (comandooriginal + " limit "
								+ registros + ", 12;");

					}

					if ((tamanho > 12) && (sent == false)) {

						// apartir desse retorne os 12 primeiros
						comandoparaexecutar = (comando + " limit " + registros + ", 12;");

						sent = true;

					}

					String caminho[];
					caminho = new String[12];
					String id[] = new String[12];

					// aloca cada registro em uma das 12 posições que tem
					int y = 12;
					ResultSet rs = st.executeQuery(comandoparaexecutar);
					while ((rs.next()) && (y != 0)) {

						y--;

						caminho[y] = rs.getString("amostra_imagem");
						id[y] = rs.getString("amostra_id");

					}

					// coloca os registros
					int i = 12;

					while (i != 0) {

						i--;

						if (i == 11) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg1.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg1.setIcon(img);

								}
							}.start();

						}

						else if (i == 10) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg2.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg2.setIcon(img);
								}
							}.start();

						}

						else if (i == 9) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg3.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg3.setIcon(img);
								}
							}.start();

						} else if (i == 8) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg4.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg4.setIcon(img);
								}
							}.start();

						} else if (i == 7) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg5.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg5.setIcon(img);
								}
							}.start();

						} else if (i == 6) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg6.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg6.setIcon(img);
								}
							}.start();

						} else if (i == 5) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg7.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg7.setIcon(img);
								}
							}.start();

						} else if (i == 4) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg8.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg8.setIcon(img);
								}
							}.start();

						} else if (i == 3) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg9.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg9.setIcon(img);
								}
							}.start();

						} else if (i == 2) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg10.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg10.setIcon(img);
								}
							}.start();

						} else if (i == 1) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg11.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg11.setIcon(img);
								}
							}.start();

						} else if (i == 0) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg12.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg12.setIcon(img);
								}
							}.start();

						}

					}
					st.close();
					con.close();

				}// fim do try
				catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

			}

			@Override
			public void mouseExited(MouseEvent e) {

				lblPaginaAnterior.setForeground(new Color(0, 0, 0));

			}
		});
		lblPaginaAnterior.setBounds(357, 61, 89, 14);
		panel_1.add(lblPaginaAnterior);

		JLabel lblPaginaSeguinte = new JLabel("Pagina Seguinte");

		lblPaginaSeguinte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				lblPaginaSeguinte.setForeground(new Color(0, 128, 0));

				if (registros <= 0) {

					registros = 12;

				}

				try {

					count = 0;

					Class.forName("com.mysql.jdbc.Driver");
					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
					// não sei oq isso faz kk
					java.sql.Statement st = con.createStatement();

					// obtem a quantidade de registros
					int tamanho = 0;

					ResultSet x = st.executeQuery(comando1);
					while (x.next()) {

						tamanho = x.getInt("COUNT(amostra_id)");

					}

					String comandooriginal = comando;
					String comandoparaexecutar = "";

					if (sent = true) {

						comandoparaexecutar = (comandooriginal + " limit "
								+ registros + ", 12;");

					}

					if ((tamanho > 12) && (sent == false)) {

						// apartir desse retorne os 12 primeiros
						comandoparaexecutar = (comando + " limit " + registros + ", 12;");

						sent = true;

					}

					String caminho[];
					caminho = new String[12];
					String id[] = new String[12];

					// aloca cada registro em uma das 12 posições que tem
					int y = 12;
					ResultSet rs = st.executeQuery(comandoparaexecutar);
					while ((rs.next()) && (y != 0)) {

						y--;

						caminho[y] = rs.getString("amostra_imagem");
						id[y] = rs.getString("amostra_id");

					}

					// coloca os registros
					int i = 12;

					while (i != 0) {

						i--;

						if (i == 11) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg1.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg1.setIcon(img);

								}
							}.start();

						}

						else if (i == 10) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg2.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg2.setIcon(img);
								}
							}.start();

						}

						else if (i == 9) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg3.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg3.setIcon(img);
								}
							}.start();

						} else if (i == 8) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg4.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg4.setIcon(img);
								}
							}.start();

						} else if (i == 7) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg5.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg5.setIcon(img);
								}
							}.start();

						} else if (i == 6) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg6.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg6.setIcon(img);
								}
							}.start();

						} else if (i == 5) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg7.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg7.setIcon(img);
								}
							}.start();

						} else if (i == 4) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg8.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg8.setIcon(img);
								}
							}.start();

						} else if (i == 3) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg9.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg9.setIcon(img);
								}
							}.start();

						} else if (i == 2) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg10.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg10.setIcon(img);
								}
							}.start();

						} else if (i == 1) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg11.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg11.setIcon(img);
								}
							}.start();

						} else if (i == 0) {
							// -------------------------------------------------------------------------------
							ImageIcon img = new ImageIcon(caminho[i]);

							lblimg12.setToolTipText(id[i]);

							new Thread() {

								public void run() {

									img.setImage(img.getImage()
											.getScaledInstance(203, 156, 100));

									lblimg12.setIcon(img);
								}
							}.start();

						}

					}
					st.close();
					con.close();

					registros = (registros * 2);

				}// fim do try
				catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(null, "Falha na consulta");
				}

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

				lblPaginaSeguinte.setForeground(new Color(0, 0, 0));

			}
		});
		lblPaginaSeguinte.setBounds(456, 61, 116, 14);
		panel_1.add(lblPaginaSeguinte);

		JLabel lblNmeroDeRegistros = new JLabel("N\u00FAmero de registros:");
		lblNmeroDeRegistros.setBounds(10, 61, 128, 14);
		panel_1.add(lblNmeroDeRegistros);

		// carrega a tela de login
		// Carregar_tela_amostra();

	}// Fim da tela inicial.

	public void Cod(String comando, String comando1) {

		try {

			count = 0;

			Class.forName("com.mysql.jdbc.Driver");
			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			// calcula o numero de registros.
			ResultSet p = st.executeQuery(comando1);
			while (p.next()) {
				count = p.getInt("Count(amostra_id)");
			}

			// calcula o numero de paginas
			int x = 0;
			int c = count;
			do {

				x++;

				c = (c - 12);

			} while (c > 0);

			lblnumeropaginas.setText(String.valueOf(x));

			String caminho[] = new String[12];
			String id[] = new String[12];

			// aloca cada registro em uma das 12 posições que tem
			int y = 12;
			ResultSet rs = st.executeQuery(comando);
			while ((rs.next()) && (y != 0)) {

				y--;

				caminho[y] = rs.getString("amostra_imagem");
				id[y] = rs.getString("amostra_id");

			}

			// coloca os registros
			int i = 12;

			while (i != 0) {

				i--;

				if (i == 11) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg1.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg1.setIcon(img);

						}
					}.start();

				}

				else if (i == 10) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg2.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg2.setIcon(img);
						}
					}.start();

				}

				else if (i == 9) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg3.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg3.setIcon(img);
						}
					}.start();

				} else if (i == 8) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg4.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg4.setIcon(img);
						}
					}.start();

				} else if (i == 7) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg5.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg5.setIcon(img);
						}
					}.start();

				} else if (i == 6) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg6.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg6.setIcon(img);
						}
					}.start();

				} else if (i == 5) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg7.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg7.setIcon(img);
						}
					}.start();

				} else if (i == 4) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg8.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg8.setIcon(img);
						}
					}.start();

				} else if (i == 3) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg9.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg9.setIcon(img);
						}
					}.start();

				} else if (i == 2) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg10.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg10.setIcon(img);
						}
					}.start();

				} else if (i == 1) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg11.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg11.setIcon(img);
						}
					}.start();

				} else if (i == 0) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg12.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg12.setIcon(img);
						}
					}.start();

				}

			}
			st.close();
			con.close();

		}// fim do try
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}
	}

	public void Carregar_tela_amostra() {

		String comando = "select COUNT(amostra_id) from tbl_amostra;";

		count = 0;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// cria a conecxão
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

			// não sei oq isso faz kk
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			String id[] = new String[12];
			String caminho[] = null;

			while (resultSet.next()) {
				// vetor para guardar os caminhos, do tamanho da quantidade de
				// imagens (de acordo com a quantidade de resultados retornados
				// pelo select)
				caminho = new String[Integer.parseInt(resultSet
						.getString("COUNT(amostra_id)"))];

			}

			// contador
			int i = caminho.length;

			comando = "select amostra_imagem from tbl_amostra;";

			ResultSet rs = st.executeQuery(comando);
			while (rs.next()) {

				i--;

				caminho[i] = rs.getString("amostra_imagem");
				id[i] = rs.getString("amostra_id");

			}

			i = caminho.length;

			while (i != 0) {

				i--;

				if (i == 11) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg1.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg1.setIcon(img);

						}
					}.start();

				}

				else if (i == 10) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg2.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg2.setIcon(img);
						}
					}.start();

				}

				else if (i == 9) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg3.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg3.setIcon(img);
						}
					}.start();

				} else if (i == 8) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg4.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg4.setIcon(img);
						}
					}.start();

				} else if (i == 7) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg5.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg5.setIcon(img);
						}
					}.start();

				} else if (i == 6) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg6.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg6.setIcon(img);
						}
					}.start();

				} else if (i == 5) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg7.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg7.setIcon(img);
						}
					}.start();

				} else if (i == 4) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg8.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg8.setIcon(img);
						}
					}.start();

				} else if (i == 3) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg9.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg9.setIcon(img);
						}
					}.start();

				} else if (i == 2) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg10.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg10.setIcon(img);
						}
					}.start();

				} else if (i == 1) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg11.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg11.setIcon(img);
						}
					}.start();

				} else if (i == 0) {
					// -------------------------------------------------------------------------------
					ImageIcon img = new ImageIcon(caminho[i]);

					lblimg12.setToolTipText(id[i]);

					new Thread() {

						public void run() {

							img.setImage(img.getImage().getScaledInstance(203,
									156, 100));

							lblimg12.setIcon(img);
						}
					}.start();

				}

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
