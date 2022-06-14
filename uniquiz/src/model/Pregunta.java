/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author santi
 */
public class Pregunta {
    private int id;
    private String enunciado;
    private String tipoPregunta;
	private ArrayList<Respuesta> respuestas = new ArrayList<>();
	private Opcion opcion = new Opcion();
	private ArrayList<Pregunta> subpreguntas=new ArrayList<>();
	 private int tipoPreguntaEst;
    public Pregunta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getTipoPregunta() {
        return tipoPregunta;
    }

    public void setTipoPregunta(String tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
    }
    public ArrayList<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(ArrayList<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}

	@Override
	public String toString() {
		return enunciado;
	}
	
	public ArrayList<Pregunta> getSubpreguntas() {
		return subpreguntas;
	}

	public void setSubpreguntas(ArrayList<Pregunta> subpreguntas) {
		this.subpreguntas = subpreguntas;
	}

	public Opcion getOpcion() {
		return opcion;
	}

	public void setOpcion(Opcion opcion) {
		this.opcion = opcion;
	}

	public int getTipoPreguntaEst() {
		return tipoPreguntaEst;
	}

	public void setTipoPreguntaEst(int tipoPreguntaEst) {
		this.tipoPreguntaEst = tipoPreguntaEst;
	}
    
    
}
