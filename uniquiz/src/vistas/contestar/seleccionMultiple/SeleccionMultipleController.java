package vistas.contestar.seleccionMultiple;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control.ModelFactoryController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.Respuesta;

public class SeleccionMultipleController implements Initializable{

	@FXML
	private Button opcion1;

	@FXML
	private Button opcion2;

	@FXML
	private Button opcion3;

	@FXML
	private Button opcion4;

	private final ModelFactoryController mfc = ModelFactoryController.getInstance();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		opcion1.setText(mfc.getUniquiz().getListaPreguntasEstudiante().get(mfc.getContadorPregunta()).getRespuestas().get(0).getRespuesta());
		opcion2.setText(mfc.getUniquiz().getListaPreguntasEstudiante().get(mfc.getContadorPregunta()).getRespuestas().get(1).getRespuesta());
		opcion3.setText(mfc.getUniquiz().getListaPreguntasEstudiante().get(mfc.getContadorPregunta()).getRespuestas().get(2).getRespuesta());
		opcion4.setText(mfc.getUniquiz().getListaPreguntasEstudiante().get(mfc.getContadorPregunta()).getRespuestas().get(3).getRespuesta());

	}

	public void limpiarOpciones() {
		opcion1.setText("");
		opcion2.setText("");
		opcion3.setText("");
		opcion4.setText("");
	}

	public void limpiarColor(){
		opcion1.setStyle("-fx-background-color:#E63E1A");
		opcion2.setStyle("-fx-background-color:#E63E1A");
		opcion3.setStyle("-fx-background-color:#E63E1A");
		opcion4.setStyle("-fx-background-color:#E63E1A");
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

	@FXML
	void elegirOpcion3() {
		mfc.setEnunciado(opcion3.getText());
		limpiarColor();
		opcion3.setStyle("-fx-background-color:#83FE00");
	}

	@FXML
	void elegirOpcion4() {
		mfc.setEnunciado(opcion4.getText());
		limpiarColor();
		opcion4.setStyle("-fx-background-color:#83FE00");
	}

}
