package modelSuplerclasses;

public class Amostra {

	private int id;
	private int modeloId;
	private double amostraPreco;
	private String descricao;

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

	public double getAmostraPreco() {
		return amostraPreco;
	}

	public void setAmostraPreco(double amostraPreco)
			throws IllegalArgumentException {
		if (amostraPreco > 0) {
			this.amostraPreco = amostraPreco;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
