package modelSuplerclasses;

public class Materiais {
	// Atributos
	private int id;
	private Double custoMaterial;
	private String nomeMaterial;
	private String descricaoMaterial;
	private String observacaoMaterial;
	private String Categoria;

	// Construtores

	public Materiais(int id, Double custoMaterial, String nomeMaterial,
			String descricaoMaterial, String observacaoMaterial) {
		this.id = id;
		this.custoMaterial = custoMaterial;
		this.nomeMaterial = nomeMaterial;
		this.descricaoMaterial = descricaoMaterial;
		this.observacaoMaterial = observacaoMaterial;
	}

	// Metodos de acesso

	public Materiais() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCategoria() {
		return Categoria;
	}

	public void setCategoria(String categoria) {
		Categoria = categoria;
	}

	public int getId() {
		return id;
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

	public String getNomeMaterial() {
		return nomeMaterial;
	}

	public void setNomeMaterial(String nomeMaterial) {
		this.nomeMaterial = nomeMaterial;
	}

	public String getDescricaoMaterial() {
		return descricaoMaterial;
	}

	public void setDescricaoMaterial(String descricaoMaterial) {
		this.descricaoMaterial = descricaoMaterial;
	}

	public String getObservacaoMaterial() {
		return observacaoMaterial;
	}

	public void setObservacaoMaterial(String observacaoMaterial) {
		this.observacaoMaterial = observacaoMaterial;
	}

}
