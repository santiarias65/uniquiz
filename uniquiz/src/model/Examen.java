package model;

public class Examen {
	private int id;
	private int numeroP;
	private int tiempo;
	private float porcentaje;
	private float umbral;
	private Tema tema;
	
	public Examen() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumeroP() {
		return numeroP;
	}
	public void setNumeroP(int numeroP) {
		this.numeroP = numeroP;
	}
	public int getTiempo() {
		return tiempo;
	}
	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
	
	public float getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}

	public float getUmbral() {
		return umbral;
	}

	public void setUmbral(float umbral) {
		this.umbral = umbral;
	}

	public void setUmbral(int umbral) {
		this.umbral = umbral;
	}
	public Tema getTema() {
		return tema;
	}
	public void setTema(Tema tema) {
		this.tema = tema;
	}

	@Override
	public String toString() {
		return tema.getNombre();
	}
}
