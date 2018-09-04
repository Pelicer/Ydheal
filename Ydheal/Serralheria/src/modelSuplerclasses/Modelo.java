package modelSuplerclasses;

public class Modelo {
	// Atributos
	private int id;
	private Double custoMaterial;
	private Double valorModelo;
	private Double custoMaoDeObra;
	private String nomeModelo;
	private String listaMaterial;
	private String listaCusto;
	private String listaqtd;
	private String descricaoModelo;
	private String Categoria;

	private int modeloid;

	// Metodos de Acesso

	public String getListaqtd() {
		return listaqtd;
	}

	public int getModeloid() {
		return modeloid;
	}

	public void setModeloid(int modeloid) {
		this.modeloid = modeloid;
	}

	public void setListaqtd(String listaqtd) {
		this.listaqtd = listaqtd;
	}

	public int getId() {
		return id;
	}

	public String getListaCusto() {
		return listaCusto;
	}

	public void setListaCusto(String listaCusto) {
		this.listaCusto = listaCusto;
	}

	public String getCategoria() {
		return Categoria;
	}

	public void setCategoria(String categoria) {
		Categoria = categoria;
	}

	public void setId(int id) throws IllegalArgumentException,
			NumberFormatException {
		if (id > 0) {
			this.id = id;
		} else {
			throw new NumberFormatException();
		}
	}

	public Double getCustoMaterial() {
		return custoMaterial;
	}

	public void setCustoMaterial(Double custoMaterial)
			throws IllegalArgumentException, NumberFormatException {
		if (custoMaterial > 0) {
			this.custoMaterial = custoMaterial;
		} else {
			throw new NumberFormatException();
		}
	}

	public Double getValorModelo() {
		return valorModelo;
	}

	public void setValorModelo(Double valorModelo)
			throws IllegalArgumentException, NumberFormatException {
		if (valorModelo > 0) {
			this.valorModelo = valorModelo;
		} else {
			throw new NumberFormatException();
		}
	}

	public Double getCustoMaoDeObra() {
		return custoMaoDeObra;
	}

	public void setCustoMaoDeObra(Double custoMaoDeObra)
			throws IllegalArgumentException, NumberFormatException {
		if (custoMaoDeObra > 0) {
			this.custoMaoDeObra = custoMaoDeObra;
		} else {
			throw new NumberFormatException();
		}
	}

	public String getNomeModelo() {
		return nomeModelo;
	}

	public void setNomeModelo(String nomeModelo) {
		this.nomeModelo = nomeModelo;
	}

	public String getListaMaterial() {
		return listaMaterial;
	}

	public void setListaMaterial(String listaMaterial) {
		this.listaMaterial = listaMaterial;
	}

	public String getDescricaoModelo() {
		return descricaoModelo;
	}

	public void setDescricaoModelo(String descricaoModelo) {
		this.descricaoModelo = descricaoModelo;
	}

	// Funçoes da classe

	public Double calcularCusto() {
		// Sem descrição
		return 0.0;
	}

	String x[] = { "" };
	double y[] = { 0 };
	String z[] = { "" };

	public void Guardarlista(String lista[], double[] custo, String qtd[]) {

		x = lista;
		y = custo;
		z = qtd;

	}

	public String[] Pegarlista() {

		return x;

	}

	public double[] Pegarpreco() {

		return y;

	}

	public String[] Pegarqtd() {

		return z;

	}

}
