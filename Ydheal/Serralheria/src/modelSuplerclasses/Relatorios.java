package modelSuplerclasses;

import java.sql.Date;

public class Relatorios {

	private int id;
	private String tipo;
	private Date data;

	public Relatorios() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Relatorios(int id, String tipo, Date data) {
		this.id = id;
		this.tipo = tipo;
		this.data = data;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
