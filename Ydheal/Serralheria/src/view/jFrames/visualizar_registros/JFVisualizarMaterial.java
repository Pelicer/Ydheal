package view.jFrames.visualizar_registros;

import java.awt.Font;
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
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Model.DAO.ConexaoMySQL;
import modelSuplerclasses.Materiais;

@SuppressWarnings("serial")
public class JFVisualizarMaterial extends JDialog {

	private JPanel contentPane;
	private JTextField tfNome;
	private JFormattedTextField tfCusto;
	private JTextField tfDescricao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JFVisualizarMaterial(Materiais m) {
		
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
				"/16px/materiais.png")).getImage();
		setIconImage(imgIcon);
		setResizable(false);
		setTitle("Visualiza\u00E7\u00E3o de Material");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 320, 374);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblMaterial = new JLabel("Material");
		// Adicionando imagem.
		Image imgMateriais = new ImageIcon(this.getClass().getResource(
				"/50px/materiais.png")).getImage();
		lblMaterial.setIcon(new ImageIcon(imgMateriais));
		lblMaterial.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMaterial.setBounds(10, 11, 394, 58);
		contentPane.add(lblMaterial);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 80, 304, 2);
		contentPane.add(separator);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 96, 46, 14);
		contentPane.add(lblNome);

		tfNome = new JTextField();
		tfNome.setEditable(false);
		tfNome.setBounds(86, 93, 208, 20);
		contentPane.add(tfNome);
		tfNome.setColumns(10);

		JLabel lblCusto = new JLabel("Custo:");
		lblCusto.setBounds(10, 158, 46, 14);
		contentPane.add(lblCusto);

		tfCusto = new JFormattedTextField();
		tfCusto.setEditable(false);
		tfCusto.setBounds(86, 155, 208, 20);
		contentPane.add(tfCusto);
		tfCusto.setColumns(10);

		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio.setBounds(10, 183, 66, 14);
		contentPane.add(lblDescrio);

		tfDescricao = new JTextField();
		tfDescricao.setEditable(false);
		tfDescricao.setBounds(86, 186, 208, 20);
		contentPane.add(tfDescricao);
		tfDescricao.setColumns(10);

		JLabel lblObservao = new JLabel("Observa\u00E7\u00E3o:");
		lblObservao.setBounds(10, 215, 72, 14);
		contentPane.add(lblObservao);

		JTextArea tfObservicao = new JTextArea();
		tfObservicao.setEditable(false);
		tfObservicao.setBounds(86, 217, 208, 71);
		contentPane.add(tfObservicao);

		JComboBox cbcategoria = new JComboBox();
		cbcategoria.setBounds(86, 125, 208, 20);
		contentPane.add(cbcategoria);

		JButton btnSalvar = new JButton("Salvar");
		Image imgSalvar = new ImageIcon(this.getClass().getResource(
				"/16px/ok.png")).getImage();
		btnSalvar.setIcon(new ImageIcon(imgSalvar));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// pega o id da categoria
				String comando = "select tbl_categoriamaterial.categoriamaterial_id from tbl_categoriamaterial where categoriamaterial_nome = '"
						+ cbcategoria.getSelectedItem() + "'";

				try {
					Class.forName("com.mysql.jdbc.Driver");

					// cria a conecxão
					java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();

					java.sql.Statement st = con.createStatement();

					ResultSet resultSet = st.executeQuery(comando);

					int y = 0;

					while (resultSet.next()) {

						String id = resultSet.getString("categoriamaterial_id");

						y = Integer.parseInt(id);

					}

					comando = "Update tbl_material set 	"
							+ "categoriamaterial_id  = " + y + ", "
							+ "material_custo = "
							+ Double.parseDouble(tfCusto.getText()) + ", "
							+ "material_nome = '" + tfNome.getText() + "', "
							+ "material_descricao = '" + tfDescricao.getText()
							+ "', " + "material_observacao = '"
							+ tfObservicao.getText() + "' where material_id = "
							+ m.getId() + ";";

					st.execute(comando);

					// fala q executou co sucesso
					JOptionPane.showMessageDialog(null,
							"Dados alterados com sucesso!");

					st.close();
					// fecha a concxão
					con.close();
					
					dispose();

				}

				catch (SQLException erroBanco) {
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

				// torna os campoas não editaveis dnv
				tfCusto.setEditable(false);
				tfDescricao.setEditable(false);
				tfNome.setEditable(false);
				tfObservicao.setEditable(false);
				cbcategoria.setEditable(false);

			}
		});
		btnSalvar.setBounds(10, 303, 89, 23);
		contentPane.add(btnSalvar);

		JButton btnAlterar = new JButton("Alterar");
		Image imgAlterar = new ImageIcon(this.getClass().getResource(
				"/16px/editar.png")).getImage();
		btnAlterar.setIcon(new ImageIcon(imgAlterar));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfNome.setEditable(true);
				tfCusto.setEditable(true);
				tfDescricao.setEditable(true);
				tfObservicao.setEditable(true);
				cbcategoria.setEditable(true);
				tfNome.grabFocus();
			}
		});
		btnAlterar.setBounds(109, 303, 89, 23);
		contentPane.add(btnAlterar);

		JButton btnFechar = new JButton("Fechar");
		Image imgFechar = new ImageIcon(this.getClass().getResource(
				"/16px/cancelar.png")).getImage();
		btnFechar.setIcon(new ImageIcon(imgFechar));
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				dispose();
			}
		});
		btnFechar.setBounds(208, 303, 89, 23);
		contentPane.add(btnFechar);

		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(10, 125, 72, 14);
		contentPane.add(lblCategoria);

		// carrega as infs

		tfCusto.setText(m.getCustoMaterial().toString());
		tfDescricao.setText(m.getDescricaoMaterial());
		tfNome.setText(m.getNomeMaterial());
		tfObservicao.setText(m.getObservacaoMaterial());

		// carrega as categorias

		String comando = "select categoriamaterial_nome, categoriamaterial_id from tbl_categoriamaterial;";

		try {

			Class.forName("com.mysql.jdbc.Driver");

			// Criando conexão.
			java.sql.Connection con = ConexaoMySQL.getConexaoMySQL();
			java.sql.Statement st = con.createStatement();

			ResultSet resultSet = st.executeQuery(comando);

			String id = "";

			while (resultSet.next()) {

				String nome = resultSet.getString("categoriamaterial_nome");

				cbcategoria.addItem(nome);

				if (m.getCategoria().equals(nome)) {
					id = resultSet.getString("categoriamaterial_id");
				}

			}

			st.close();
			con.close();

			cbcategoria.setSelectedIndex((Integer.parseInt(id) - 1));

			JLabel label = new JLabel("R$");
			label.setBounds(70, 158, 29, 14);
			contentPane.add(label);

		}// fim do try
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}

	}
}
