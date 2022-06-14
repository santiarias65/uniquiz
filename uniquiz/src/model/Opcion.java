package model;

public class Opcion {

	private String opcion;
	private int id;
	public Opcion() {
		super();
	}
	public String getOpcion() {
		return opcion;
	}
	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return opcion;
	}


}
