package vistas.contestar.verdaderoFalso;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control.ModelFactoryController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import model.Respuesta;

public class VerdaderoFalsoController implements Initializable{

	@FXML
	private Button opcion1;

	@FXML
	private Button opcion2;

	private final ModelFactoryController mfc = ModelFactoryController.getInstance();
	private ArrayList<Respuesta> listaRespuestas = mfc.getUniquiz().getListaPreguntasEstudiante().get(mfc.getContadorPregunta()).getRespuestas();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		opcion1.setText(listaRespuestas.get(0).getRespuesta());
		opcion2.setText(listaRespuestas.get(1).getRespuesta());
	}

	public void limpiarOpciones() {
		opcion1.setText("");
		opcion2.setText("");
	}

	@FXML
	void elegirOpcion1() {
		mfc.setEnunciado(opcion1.getText());
		limpiarColor();
		opcion1.setStyle("-fx-background-color:#83FE00");
	}

	@FXML
	void elegirOpcion2() {
		mfc.setEnunciado(opcion2.getText());
		limpiarColor();
		opcion2.setStyle("-fx-background-color:#83FE00");
	}
	public void limpiarColor(){
		opcion1.setStyle("-fx-background-color:#E63E1A");
		opcion2.setStyle("-fx-background-color:#E63E1A");
	}

}
