package control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.Pregunta;

public class PresentarExamenController implements Initializable {

	@FXML
	private Label pregunta;

	@FXML
	private Label tiempo;

	@FXML
    private Pane respuestas2;

	private final ModelFactoryController mfc = ModelFactoryController.getInstance();
	private ArrayList<Pregunta> listaPreguntas = mfc.getUniquiz().getListaPreguntasEstudiante();
	private int i;
	int minutos = 0;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		reiniciarInterfaz();

		Task tarea = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				while(minutos<1000000) {
				Thread.sleep(1000);
				minutos++;
				Platform.runLater(()->{	tiempo.setText(""+minutos);});
				}
				return null;
			}
		};
		// (new Thread(tarea)).start();
		Thread hilo = new Thread(tarea);
		hilo.setDaemon(true);
		hilo.start();
	}

	@FXML
	void siguiente() {

		if(listaPreguntas.get(i).getTipoPreguntaEst()==4 ||listaPreguntas.get(i).getTipoPreguntaEst()==5 ){
			mfc.guardarRespuestaM();
		}else{
			mfc.guardarRespuesta();
		}
		i++;
		mfc.contador();
		respuestas2.getChildren().clear();
		reiniciarInterfaz();
	}

	public void reiniciarInterfaz() {
		// TODO Auto-generated method stub
		if (i < listaPreguntas.size()) {
			pregunta.setText(listaPreguntas.get(i).getEnunciado());

			if (listaPreguntas != null) {
				if(listaPreguntas.get(i).getTipoPreguntaEst()==1) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/contestar/seleccionMultiple/SeleccionMultiple.fxml"));
					try {
						Pane pane = (Pane) loader.load();
						respuestas2.getChildren().add(pane);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}if(listaPreguntas.get(i).getTipoPreguntaEst()==2){
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/contestar/verdaderoFalso/VerdaderoFalso.fxml"));
					try {
						Pane pane = (Pane) loader.load();
						respuestas2.getChildren().add(pane);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}if(listaPreguntas.get(i).getTipoPreguntaEst()==3) {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/contestar/preguntaAbierta/PreguntaAbierta.fxml"));

					try {
						Pane pane = (Pane) loader.load();
						respuestas2.getChildren().add(pane);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}if(listaPreguntas.get(i).getTipoPreguntaEst()==4) {
					mfc.obtenerSubPreguntas(listaPreguntas.get(i));
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/contestar/ordenar/Ordenar.fxml"));
					try {

						Pane pane = (Pane) loader.load();
						respuestas2.getChildren().add(pane);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}if(listaPreguntas.get(i).getTipoPreguntaEst()==5) {
					mfc.obtenerSubPreguntas(listaPreguntas.get(i));
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/contestar/emparejar1/Emparejar.fxml"));
					try {
						Pane pane = (Pane) loader.load();
						respuestas2.getChildren().add(pane);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else {
			mfc.getMain().cerrarPaginaExamen();
			JOptionPane.showMessageDialog(null, "Termino el examen \n Su calificacion es: "+ mfc.calificarExamen(minutos));
			mfc.setContadorPregunta(0);
			mfc.getUniquiz().getListaPreguntas().clear();
			mfc.getUniquiz().getListaPreguntasEstudiante().clear();
			mfc.getUniquiz().getListaGruposAlumnos().clear();
			mfc.getMain().abrirPaginaEstudiante();
		}


	}


}
