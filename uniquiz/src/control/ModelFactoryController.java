package control;


import aplicacion.Aplicacion;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Examen;
import model.Grupo;
import model.Opcion;
import model.Pregunta;
import model.Respuesta;
import model.Tema;
import model.UniQuiz;


public class ModelFactoryController{
        private Aplicacion main;

        //SELECT MAX(id) AS id FROM jugadores; consulta da el ultimo id
        //private Jugador jugador = new Jugador();
        //private ArrayList<Jugador>listaTop= new ArrayList<>();
	private Connection con;
        private Statement stmt;
        private ResultSet rs;
        private UniQuiz uniquiz = new UniQuiz();
        private int personaLogeada;
        static private final Logger LOGGER = Logger.getLogger("mx.com.hash.checkip.CheckIP");

        //variables utilizadas en logica para el profesor
        private int []preguntasSeleccionadasExamen;
        private ArrayList<Pregunta>observablePreguntasSeleccionadas = new ArrayList<>();

        //variables utilizadas en logica para el estudiante
    	private int contadorPregunta=0;
    	private int examenId;
    	private String enunciado;
    	private int numPreguntas;

        //controlador de controles
        public PaginaProfesorController control;
        public ConfiguracionPreguntasController controlPregunta;

	//------------------------------  Singleton ------------------------------------------------
	// Clase estatica oculta. Tan solo se instanciara el singleton una vez
	private static class SingletonHolder {
		// El constructor de Singleton puede ser llamado desde aqu� al ser protected
		private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
	}

	// M�todo para obtener la instancia de nuestra clase
	public static ModelFactoryController getInstance() {
		return SingletonHolder.eINSTANCE;
	}

	public ModelFactoryController() {
            abrirConexion();
	}
        public void abrirDatosProfesor(){
            //si el logueo detectamos que es un profesor abrimos este metodo
            //obtenerTemasBd();
            obtenerGruposBdDelProfesorLogeado();
            //obtenerPreguntasConfiguracionExamen();
        }

        public void abrirDatosEstudiante() {
    		obtenerGruposEstudiantes();
    	}


        public final void abrirConexion(){
            try
            {   //servidor //usuario //clave

                Class.forName("oracle.jdbc.driver.OracleDriver");
                con=DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","PRUEBA","root");
                stmt=con.createStatement();
                //ResultSet rs=stmt.executeQuery("use PRUEBA;");
                System.out.println("Connected");
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        public final void cerrarConexion(){
            try {
                //System.out.println("DESCONECTADO");
                stmt.close();
                con.close();
                rs.close();
                System.out.println("DESCONECTADO");
            } catch (SQLException ex) {
                Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    public int validarLogin(String correo,String clave){
            try {
                String query = "SELECT p.identificacion,p.email,p.clave,p.rol_id FROM persona p";
                rs = stmt.executeQuery(query);
                while(rs.next()){
                    if(correo.equalsIgnoreCase(rs.getString("EMAIL")) && clave.equals(rs.getString("CLAVE"))){
                        personaLogeada = rs.getInt("IDENTIFICACION");
                        return rs.getInt("ROL_ID");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        return 0;
    }

    public final ArrayList<Pregunta> obtenerPreguntasExamenes(int idExamen) {
		uniquiz.getListaPreguntas().clear();
		try {
			String query ="select p.enunciado,p.id,p.tipo_pregunta_id from pregunta p join Banco b on b.pregunta_id=p.id where b.examen_id="+ idExamen;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Pregunta pregunta = new Pregunta();
				pregunta.setEnunciado(rs.getString(1));
				pregunta.setId(rs.getInt(2));
				pregunta.setTipoPreguntaEst(rs.getInt(3));
				uniquiz.getListaPreguntas().add(pregunta);
			}
			for(int i =0;i<numPreguntas;i++){
				Pregunta pregunta = uniquiz.getListaPreguntas().get(numeroAleatorio(0,uniquiz.getListaPreguntas().size()));
				if(!(uniquiz.getListaPreguntasEstudiante().contains(pregunta))){
					uniquiz.getListaPreguntasEstudiante().add(pregunta);

				}else{
					i=i-1;
				}
			}
			uniquiz.setListaPreguntasEstudiante(obtenerRespuestasPregunta(uniquiz.getListaPreguntasEstudiante()));
			uniquiz.setListaPreguntasEstudiante(obtenerOpcionPregunta(uniquiz.getListaPreguntasEstudiante()));
			guardarPreguntasEstudiantes();
			return uniquiz.getListaPreguntas();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

    public void guardarPreguntasEstudiantes(){
		ArrayList<Pregunta> preguntas = uniquiz.getListaPreguntasEstudiante();
		for (Pregunta pregunta : preguntas) {
			String query= "INSERT INTO ESTUDIANTE_EXAMEN_BANCO VALUES ("+String.valueOf(examenId)+","+personaLogeada+","+String.valueOf(examenId)+","  + pregunta.getId() +")";
			try {
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int numeroAleatorio(int minimo,int maximo){
			int numero;
	        Random ran = new Random();
	        numero = ran.nextInt(maximo) + minimo;
		return numero;
	}
	public final ArrayList<Pregunta> obtenerRespuestasPregunta(ArrayList<Pregunta>preguntas) {


		for(Pregunta pregunta : preguntas){
			try {
				String query ="select r.id,r.respuesta from respuesta r join pregunta p on p.id=r.pregunta_id where p.id="+ pregunta.getId();
				rs = stmt.executeQuery(query);
				while (rs.next()) {

					Respuesta respuesta = new Respuesta();
					respuesta.setId(rs.getInt(1));
					respuesta.setRespuesta(rs.getString(2));
					pregunta.getRespuestas().add(respuesta);
				}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return preguntas;

	}
	public final ArrayList<Pregunta> obtenerOpcionPregunta(ArrayList<Pregunta>preguntas) {


		for(Pregunta pregunta : preguntas){
			try {
				String query ="select o.id,o.opcion from opcion o join pregunta p on p.id=o.pregunta_id where p.id="+ pregunta.getId();
				rs = stmt.executeQuery(query);
				while (rs.next()) {

					Opcion opcion = new Opcion();
					opcion.setId(rs.getInt(1));
					opcion.setOpcion(rs.getString(2));
					pregunta.setOpcion(opcion);

				}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return preguntas;
	}

	public void guardarRespuesta(){

		//abrirConexion();
		try {
			String preguntaId = String.valueOf(uniquiz.getListaPreguntasEstudiante().get(contadorPregunta).getId());
			String opcion = String.valueOf(uniquiz.getListaPreguntasEstudiante().get(contadorPregunta).getOpcion().getId());
			String query = "INSERT INTO respuestas VALUES (null,"+opcion+","+String.valueOf(examenId)+","+ personaLogeada + "," + String.valueOf(examenId)+ "," + preguntaId+ ",'" + enunciado+ "')";
			stmt.executeUpdate(query);

		} catch (SQLException ex) {
			Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
		}
		//cerrarConexion();
	}
	public void guardarRespuestaM(){
		ArrayList<String> enunciados=uniquiz.getEnunciados();
		//abrirConexion();
		try {
			String preguntaId = String.valueOf(uniquiz.getListaPreguntasEstudiante().get(contadorPregunta).getId());
			String opcion = String.valueOf(uniquiz.getListaPreguntasEstudiante().get(contadorPregunta).getOpcion().getId());
			String query = "INSERT INTO respuestas VALUES (null,"+opcion+","+String.valueOf(examenId)+","+ personaLogeada + "," + String.valueOf(examenId)+ "," + preguntaId+ ",'" +enunciados.get(0)+","+enunciados.get(1)+","+enunciados.get(2)+","+enunciados.get(3) + "')";
			stmt.executeUpdate(query);

		} catch (SQLException ex) {
			Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
		}
		//cerrarConexion();
	}

	public double calificarExamen(int minutos){
		//abrirConexion();
		int preguntas=0;
		int preguntasBuenas=0;
		int j=0;
		double nota;
		int buenas=0;
		ArrayList<String>opciones=new ArrayList<>();
		ArrayList<String>respuestas=new ArrayList<>();
		ArrayList<String>tipos=new ArrayList<>();
		ArrayList<String>id=new ArrayList<>();


		try {
			String query1="select r.respuesta,o.opcion,p.tipo_pregunta_id,p.id from respuestas r join opcion o on o.id=r.opcion_id join pregunta p on p.id=r.estexban_banco_pregunta_id where r.estexban_estexa_estudiante_id="+personaLogeada+" and r.estexban_banco_examen_id="+examenId;
			rs = stmt.executeQuery(query1);
			while (rs.next()) {
				respuestas.add(rs.getString(1));
				opciones.add(rs.getString(2));
				tipos.add(rs.getString(3));
				id.add(rs.getString(4));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for(int i=0;i<tipos.size();i++){
			if(tipos.get(i).equalsIgnoreCase("4")||tipos.get(i).equalsIgnoreCase("5")){
				try {
						String query="select o.opcion from pregunta p join opcion o on o.pregunta_id=p.id where p.pregunta_id="+id.get(i);
						rs = stmt.executeQuery(query);
					    String[] employeeNamesSplit = respuestas.get(i).split(",");//Splitting names
				        ArrayList<String> list = new ArrayList<String>(Arrays.asList(employeeNamesSplit));
				        while(rs.next()){
				        	if(list.get(j).toLowerCase().equalsIgnoreCase(rs.getString(1).toLowerCase())){
				        		buenas=buenas+1;
				        	}
				        	j++;
				        }

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				preguntas=preguntas+1;
				j=0;
			}else{
				if(respuestas.get(i).toLowerCase().equalsIgnoreCase(opciones.get(i).toLowerCase())){
					preguntasBuenas=preguntasBuenas+1;
				}
				preguntas=preguntas + 1;
			}
		}


		nota=(preguntasBuenas*5.0)/preguntas;
		double aumento = 5.0/preguntas;
		nota=nota+(buenas*(aumento/4));
		guardarNota(nota,minutos);
		//cerrarConexion();
		return nota;
	}

		public void guardarNota(double nota,int num){
			String ip = "";
			try {
				CheckIP checkIP = new CheckIP();
				ip =checkIP.obtenerIP();
			} catch (UnknownHostException ex) {
				LOGGER.log(Level.SEVERE, "Error al consultar el Host");
				LOGGER.log(Level.SEVERE, null, ex);
			}

			String fecha = LocalDateTime.now().toString();
			int hor=num/3600;
	        int min=(num-(3600*hor))/60;
	        int seg=num-((hor*3600)+(min*60));
	        String minSt = hor+":"+min+":"+seg;
			String query = "Update estudiante_examen ee set ee.calificacion="+nota+",ee.tiempo='"+minSt+"',ee.fecha='"+fecha+"',ee.ip='"+ip+"' where ee.estudiante_identificacion="+personaLogeada+"and ee.examenest_id="+examenId;
			try {
				rs = stmt.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	public void obtenerSubPreguntas(Pregunta pregunta1){
		//abrirConexion();

		try {
			String query ="select p2.enunciado,p2.id,p2.tipo_pregunta_id from pregunta p join pregunta p2 on p.id=p2.pregunta_id where p.id="+pregunta1.getId();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Pregunta pregunta = new Pregunta();
				pregunta.setEnunciado(rs.getString(1));
				pregunta.setId(rs.getInt(2));
				pregunta.setTipoPreguntaEst(rs.getInt(3));

				pregunta1.getSubpreguntas().add(pregunta);
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		for(int i =0;i<pregunta1.getSubpreguntas().size();i++){
			obtenerOpcionSubpregunta(pregunta1.getSubpreguntas().get(i));
		}

		//cerrarConexion();

	}
	public void obtenerOpcionSubpregunta(Pregunta pregunta){
		try {
			String query ="select o.id,o.opcion from opcion o join pregunta p on p.id=o.pregunta_id where p.id="+ pregunta.getId();
			rs = stmt.executeQuery(query);
			while (rs.next()) {

				Opcion opcion = new Opcion();
				opcion.setId(rs.getInt(1));
				opcion.setOpcion(rs.getString(2));
				pregunta.setOpcion(opcion);

			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public final void obtenerExamenesEstudiates(int idGrupo) {
		uniquiz.getListaExamenesAlumnos().clear();
		try {
			String query = "select t.nombre,e.numero_preguntas,e.tiempo_minutos,e.pesoporcetaje,e.umbral,e.id from examen e join estudiante_examen ee on ee.examenest_id=e.id  join tema t on e.id_tema = t.id where e.id_grupo="+idGrupo+"and ee.calificacion is null and ee.estudiante_identificacion="+personaLogeada;

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Examen examen = new Examen();
				Tema tema = new Tema();
				examen.setNumeroP(rs.getInt(2));
				examen.setTiempo(rs.getInt(3));
				examen.setPorcentaje(rs.getFloat(4));
				examen.setUmbral(rs.getFloat(5));
				examen.setId(rs.getInt(6));
				tema.setNombre(rs.getString(1));
				examen.setTema(tema);
				uniquiz.getListaExamenesAlumnos().add(examen);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Examen obtenerDatosExamen(int idExamen) {
		for (Examen examen: listaExamanesEst()) {
			if(examen.getId()==idExamen) {
				return examen;
			}
		}
		return null;
	}

	public final void obtenerGruposEstudiantes() {

		try {
			String query = "select g.id,g.nombre from estudiante_grupo eg join grupo g on eg.grupo_id = g.id where eg.estudiante_identificacion="
					+ personaLogeada;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Grupo grupo = new Grupo();
				grupo.setId(rs.getInt(1));
				grupo.setNombre(rs.getString(2));
				uniquiz.getListaGruposAlumnos().add(grupo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //obtenemos los temas de la base de datos
    public final void obtenerTemasBd(int idGrupo){
            uniquiz.getListaTemas().clear();
            try {
                String query="SELECT t.id,t.nombre FROM grupo g JOIN asignatura a ON g.asignatura_idasignatura = a.idasignatura join tema t on t.plan_estudio_id =a.plan_estudio_id WHERE g.id="+idGrupo;
                rs = stmt.executeQuery(query);
                while(rs.next()){
                    Tema tema = new Tema();
                    tema.setId(rs.getInt(1));
                    tema.setNombre(rs.getString(2));
                    uniquiz.getListaTemas().add(tema);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    //obtenemos los grupos de la base de datos del profesor logueado
    public void obtenerGruposBdDelProfesorLogeado(){
            try {
                String query = "select g.id,g.nombre from grupo g WHERE g.docente_persona_identificacion ="+personaLogeada;
                rs = stmt.executeQuery(query);
                while(rs.next()){
                    Grupo grupo = new Grupo();
                    grupo.setId(rs.getInt(1));
                    grupo.setNombre(rs.getString(2));
                    uniquiz.getListaGruposProfesores().add(grupo);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    //obtener preguntas publicas o del profesor logeado configuracion del examen
    public void obtenerPreguntasConfiguracionExamen(int idTema){
            try {
            	String query = "select p.id,p.enunciado,t.tipo from pregunta p JOIN tipo_pregunta t on p.tipo_pregunta_id=t.id WHERE (p.docente_persona_identificacion="+personaLogeada+"or p.privacidad_id=1)and p.tema_id="+idTema+"and p.pregunta_id is null";
                //String query = "select p.id,p.enunciado,t.tipo from pregunta p JOIN tipo_pregunta t on p.tipo_pregunta_id=t.id WHERE (p.docente_persona_identificacion="+personaLogeada+" or p.privacidad_id=1)and p.tema_id="+idTema;
                rs = stmt.executeQuery(query);
                while(rs.next()){
                    Pregunta pregunta = new Pregunta();
                    pregunta.setId(rs.getInt(1));
                    pregunta.setEnunciado(rs.getString(2));
                    pregunta.setTipoPregunta(rs.getString(3));
                    uniquiz.getListaPreguntasProfesores().add(pregunta);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    //obtener nombre del profesor logeado
    public String obtenerNombreProfesroLogueado(){
            try {
                String query = "select s.nombre,s.apellido_paterno from persona s WHERE s.identificacion ="+personaLogeada;
                rs = stmt.executeQuery(query);
                rs.next();
                return rs.getString(1)+" "+rs.getString(2);
            } catch (SQLException ex) {
                Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }

    //ingresa el examen a la base de datos
    //datos necesarios null,idProfesor,fechainicio,fechafin,numeropreguntas,tiempo,pesoporcentaje,umbral,idtema
    public void crearExamenBd(LocalDate fechaInicio,LocalDate fechaFin,int numeroPreguntas,int tiempo,double peso,double umbral,int idTema,int idGrupo){
            try {
                String query = "INSERT INTO examen VALUES (null,"+personaLogeada+",'"+fechaInicio+"','"+fechaFin+"',"+numeroPreguntas+","+tiempo+","+peso+","+umbral+","+idTema+","+idGrupo+")";
                stmt.executeUpdate(query);
            } catch (SQLException ex) {
                Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    //obtenemos el id del ultimo examen creado para configuracion de preguntas
    public int ultimoExamenCreado(){
            try {
                String query = "SELECT e.id FROM examen e WHERE ROWNUM=1 ORDER BY e.id DESC";
                rs = stmt.executeQuery(query);
                rs.next();
                return rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        return 0;
    }
    //creamos la relacion entre examen y preguntas
    public void crearBanco(int idExamen,int []idPreguntasSeleccionadas){
        for (int i = 0; i < idPreguntasSeleccionadas.length; i++) {
            try {
                String query = "INSERT INTO BANCO VALUES("+idExamen+","+idPreguntasSeleccionadas[i]+")";
                stmt.executeUpdate(query);
            } catch (SQLException ex) {
                Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //creamos estudiantes con examen por la loquita de emerson
    public ArrayList<Integer> estudiantesEnElGrupo(int idGrupo){
        ArrayList<Integer>ids=new ArrayList<>();
            try {

                String query = "select e.estudiante_identificacion from estudiante_grupo e WHERE e.grupo_id ="+idGrupo;
                rs = stmt.executeQuery(query);
                while(rs.next()){
                    ids.add(rs.getInt(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        return ids;
    }
    public void crearEstudiantesExamenPorGrupo(int idGrupo,int idExamen){
        String query;
        ArrayList<Integer>ids = estudiantesEnElGrupo(idGrupo);
        for (int i = 0; i < ids.size(); i++) {
        	query = "INSERT INTO estudiante_examen VALUES ("+ids.get(i)+","+idExamen+",null,null,null,null)";
            try {
                stmt.executeUpdate(query);
            } catch (SQLException ex) {
                Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    public ArrayList<Pregunta> configurarExamenAutomatico(int idTema,int cantidadPreguntas){
        ArrayList<Pregunta>lista= new ArrayList<>();
            try {
                String query="SELECT p.id,p.enunciado,tp.tipo\n" +
                        "  FROM pregunta p JOIN tipo_pregunta tp ON p.tipo_pregunta_id=tp.id,\n" +
                        "       (SELECT pr.id\n" +
                        "          FROM pregunta pr\n" +
                        "      ORDER BY DBMS_RANDOM.VALUE) rand\n" +
                        "      \n" +
                        " WHERE p.tema_id="+idTema+" and p.id = rand.id\n" +
                        "   AND p.pregunta_id is null AND ROWNUM <="+cantidadPreguntas;
                rs = stmt.executeQuery(query);
                while(rs.next()){
                    Pregunta pr = new Pregunta();
                    pr.setId(rs.getInt(1));
                    pr.setEnunciado(rs.getString(2));
                    pr.setTipoPregunta(rs.getString(3));
                    lista.add(pr);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        return lista;
    }
    public ResultSet graficoExamenesPorGrupo(){
            try {

                String query = "SELECT g.nombre,COUNT(e.id_grupo) FROM examen e RIGHT JOIN grupo g on e.id_grupo=g.id WHERE g.docente_persona_identificacion="+personaLogeada+" GROUP BY g.nombre";
                rs = stmt.executeQuery(query);
            } catch (SQLException ex) {
                Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        return rs;
    }

    public ResultSet graficoExamaneEstudiante() {
    	try {

    		String query = "select t.nombre,ex.calificacion from estudiante_examen ex join examen e on ex.examenest_id = e.id join tema t on t.id = e.id_tema where ex.estudiante_identificacion = "+personaLogeada+" AND ex.calificacion is not null";
    		rs = stmt.executeQuery(query);
		} catch (SQLException ex) {
			// TODO: handle exception
			Logger.getLogger(ModelFactoryController.class.getName()).log(Level.SEVERE, null, ex);
		}
    	return rs;
    }
     //retornamos el arraylist con los temas
    public ArrayList<Tema> listaTemas(){
        return uniquiz.getListaTemas();
    }
    //retornamos el arraylist con los grupos del profesor logueado
    public ArrayList<Grupo> listaGruposProfesor(){
        return uniquiz.getListaGruposProfesores();
    }
    //retornamos el arraylist con las preguntas del profesor logeado o publicas
    public ArrayList<Pregunta> listaPreguntasProfesorPublicas(){
        return uniquiz.getListaPreguntasProfesores();
    }

    public ArrayList<Grupo> listaGruposEst() {
		return uniquiz.getListaGruposAlumnos();
	}

	public ArrayList<Examen> listaExamanesEst(){
		return uniquiz.getListaExamenesAlumnos();
	}

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public UniQuiz getUniquiz() {
        return uniquiz;
    }

    public void setUniquiz(UniQuiz uniquiz) {
        this.uniquiz = uniquiz;
    }

    public int[] getPreguntasSeleccionadasExamen() {
        return preguntasSeleccionadasExamen;
    }

    public void setPreguntasSeleccionadasExamen(int[] preguntasSeleccionadasExamen) {
        this.preguntasSeleccionadasExamen = preguntasSeleccionadasExamen;
    }

    public Aplicacion getMain() {
        return main;
    }

    public void setMain(Aplicacion main) {
        this.main = main;
    }

    public ArrayList<Pregunta> getObservablePreguntasSeleccionadas() {
        return observablePreguntasSeleccionadas;
    }

    public void setObservablePreguntasSeleccionadas(ArrayList<Pregunta> observablePreguntasSeleccionadas) {
        this.observablePreguntasSeleccionadas = observablePreguntasSeleccionadas;
    }

    public void contador(){

		contadorPregunta=contadorPregunta+1;
	}

	public int getNumPreguntas() {
		return numPreguntas;
	}

	public void setNumPreguntas(int numPreguntas) {
		this.numPreguntas = numPreguntas;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public int getExamenId() {
		return examenId;
	}

	public void setExamenId(int examenId) {
		this.examenId = examenId;
	}

	public int getContadorPregunta() {
		return contadorPregunta;
	}

	public void setContadorPregunta(int contadorPregunta) {
		this.contadorPregunta = contadorPregunta;
	}



}
