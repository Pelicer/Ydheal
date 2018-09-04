package modelSuplerclasses;

public class Orcamento {
	// Atributos
	private int id;
	private int modeloId;
	private int visitaId;
	private int clienteId;
	private double metodoPagamento;
	private double maoDeObra;
	private String formaPagamento;
	private double valorTotal;
	private double totalPrazo;
	private double Juros;
	private double Parcelamento;
	private double Largura;
	private double Altura;
	private double Area;
	private double Precopormetroquadrado;
	private String Listaqtd;
	private String ListaMaterial;
	private String nomedopedido;
	private String descricaoproduto;
	private String detalesadicionais;

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

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) throws IllegalArgumentException,
			NumberFormatException {
		if (clienteId > 0) {
			this.clienteId = clienteId;
		} else {
			throw new NumberFormatException();
		}
	}

	public int getModeloId() {
		return modeloId;
	}

	public void setModeloId(int modeloId) throws IllegalArgumentException,
			NumberFormatException {
		if (modeloId > 0) {
			this.modeloId = modeloId;
		} else {
			throw new NumberFormatException();
		}
	}

	public int getVisitaId() {
		return visitaId;
	}

	public void setVisitaId(int visitaId) throws IllegalArgumentException,
			NumberFormatException {
		if (visitaId > 0) {
			this.visitaId = visitaId;
		} else {
			throw new NumberFormatException();
		}
	}

	public double getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(double metodoPagamento)
			throws IllegalArgumentException, NumberFormatException {
		if (metodoPagamento > 0) {
			this.metodoPagamento = metodoPagamento;
		} else {
			throw new NumberFormatException();
		}
	}

	public double getMaoDeObra() {
		return maoDeObra;
	}

	public void setMaoDeObra(double maoDeObra) throws IllegalArgumentException,
			NumberFormatException {
		if (maoDeObra > 0) {
			this.maoDeObra = maoDeObra;
		} else {
			throw new NumberFormatException();
		}
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal)
			throws IllegalArgumentException, NumberFormatException {
		if (valorTotal > 0) {
			this.valorTotal = valorTotal;
		} else {
			throw new NumberFormatException();
		}
	}

	public double getTotalPrazo() {
		return totalPrazo;
	}

	public void setTotalPrazo(double totalPrazo)
			throws IllegalArgumentException, NumberFormatException {
		if (totalPrazo > 0) {
			this.totalPrazo = totalPrazo;
		} else {
			throw new NumberFormatException();
		}
	}

	public double getJuros() {
		return Juros;
	}

	public void setJuros(double juros) throws IllegalArgumentException,
			NumberFormatException {
		if (juros >= 0) {
			this.Juros = juros;
		} else {
			throw new NumberFormatException();
		}
	}

	public double getParcelamento() {
		return Parcelamento;
	}

	public void setParcelamento(double parcelamento)
			throws IllegalArgumentException, NumberFormatException {
		if (parcelamento >= 0) {
			this.Parcelamento = parcelamento;
		} else {
			throw new NumberFormatException();
		}
	}

	public double getLargura() {
		return Largura;
	}

	public void setLargura(double largura) throws IllegalArgumentException,
			NumberFormatException {
		if (largura > 0) {
			this.Largura = largura;
		} else {
			throw new NumberFormatException();
		}
	}

	public double getAltura() {
		return Altura;
	}

	public void setAltura(double altura) throws IllegalArgumentException,
			NumberFormatException {
		if (altura > 0) {
			this.Altura = altura;
		} else {
			throw new NumberFormatException();
		}
	}

	public double getArea() {
		return Area;
	}

	public void setArea(double area) throws IllegalArgumentException,
			NumberFormatException {
		if (area > 0) {
			this.Area = area;
		} else {
			throw new NumberFormatException();
		}
	}

	public double getPrecopormetroquadrado() {
		return Precopormetroquadrado;
	}

	public void setPrecopormetroquadrado(double precopormetroquadrado)
			throws IllegalArgumentException, NumberFormatException {
		if (precopormetroquadrado > 0) {
			this.Precopormetroquadrado = precopormetroquadrado;
		} else {
			throw new NumberFormatException();
		}
	}

	public String getListaqtd() {
		return Listaqtd;
	}

	public void setListaqtd(String listaqtd) {
		Listaqtd = listaqtd;
	}

	public String getListaMaterial() {
		return ListaMaterial;
	}

	public void setListaMaterial(String listaMaterial) {
		ListaMaterial = listaMaterial;
	}

	public String getNomedopedido() {
		return nomedopedido;
	}

	public void setNomedopedido(String nomedopedido) {
		this.nomedopedido = nomedopedido;
	}

	public String getDescricaoproduto() {
		return descricaoproduto;
	}

	public void setDescricaoproduto(String descricaoproduto) {
		this.descricaoproduto = descricaoproduto;
	}

	public String getDetalesadicionais() {
		return detalesadicionais;
	}

	public void setDetalesadicionais(String detalesadicionais) {
		this.detalesadicionais = detalesadicionais;
	}

	String x[] = { "" };

	public void Guardarlista(String lista[]) {

		x = lista;

	}

	public String[] Pegarlista() {
		return x;

	}

}