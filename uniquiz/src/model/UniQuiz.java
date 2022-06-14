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
public class UniQuiz {
    private ArrayList<Tema>listaTemas = new ArrayList<>();
    private ArrayList<Profesor>listaProfesores = new ArrayList<>();
    private ArrayList<Grupo>listaGruposProfesores = new ArrayList<>();
    private ArrayList<Pregunta>listaPreguntasProfesores = new ArrayList<>();
    
    private ArrayList<Grupo>listaGruposAlumnos = new ArrayList<>();
    private ArrayList<Examen>listaExamenesAlumnos = new ArrayList<>();
    private ArrayList<Pregunta>listaPreguntas = new ArrayList<>();
    private ArrayList<Pregunta>listaPreguntasEstudiante=new ArrayList<>();
    private ArrayList<String>enunciados=new ArrayList<>();
    
    
    public UniQuiz() {
		enunciados.add("");
		enunciados.add("");
		enunciados.add("");
		enunciados.add("");
    }

    public ArrayList<Tema> getListaTemas() {
        return listaTemas;
    }

    public void setListaTemas(ArrayList<Tema> listaTemas) {
        this.listaTemas = listaTemas;
    }

    public ArrayList<Profesor> getListaProfesores() {
        return listaProfesores;
    }

    public void setListaProfesores(ArrayList<Profesor> listaProfesores) {
        this.listaProfesores = listaProfesores;
    }

    public ArrayList<Grupo> getListaGruposProfesores() {
        return listaGruposProfesores;
    }

    public void setListaGruposProfesores(ArrayList<Grupo> listaGruposProfesores) {
        this.listaGruposProfesores = listaGruposProfesores;
    }

    public ArrayList<Pregunta> getListaPreguntasProfesores() {
        return listaPreguntasProfesores;
    }

    public void setListaPreguntasProfesores(ArrayList<Pregunta> listaPreguntasProfesores) {
        this.listaPreguntasProfesores = listaPreguntasProfesores;
    }

	public ArrayList<Grupo> getListaGruposAlumnos() {
		return listaGruposAlumnos;
	}

	public void setListaGruposAlumnos(ArrayList<Grupo> listaGruposAlumnos) {
		this.listaGruposAlumnos = listaGruposAlumnos;
	}

	public ArrayList<Examen> getListaExamenesAlumnos() {
		return listaExamenesAlumnos;
	}

	public void setListaExamenesAlumnos(ArrayList<Examen> listaExamenesAlumnos) {
		this.listaExamenesAlumnos = listaExamenesAlumnos;
	}

	public ArrayList<Pregunta> getListaPreguntas() {
		return listaPreguntas;
	}

	public void setListaPreguntas(ArrayList<Pregunta> listaPreguntas) {
		this.listaPreguntas = listaPreguntas;
	}

	public ArrayList<Pregunta> getListaPreguntasEstudiante() {
		return listaPreguntasEstudiante;
	}

	public void setListaPreguntasEstudiante(ArrayList<Pregunta> listaPreguntasEstudiante) {
		this.listaPreguntasEstudiante = listaPreguntasEstudiante;
	}

	public ArrayList<String> getEnunciados() {
		return enunciados;
	}

	public void setEnunciados(ArrayList<String> enunciados) {
		this.enunciados = enunciados;
	}
    
    
    
    
}
