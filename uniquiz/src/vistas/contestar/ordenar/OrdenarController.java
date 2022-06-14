package vistas.contestar.ordenar;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control.ModelFactoryController;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.Pregunta;

public class OrdenarController implements Initializable {

	@FXML
    private Label ordenar1;

    @FXML
    private Label ordenar2;

    @FXML
    private Label ordenar3;

    @FXML
    private Label ordenar4;

    @FXML
    private ComboBox<String> combo4;


    @FXML
    private ComboBox<String> combo3;

    @FXML
    private ComboBox<String> combo2;

    @FXML
    private ComboBox<String> combo1;

    private final ModelFactoryController mfc = ModelFactoryController.getInstance();
    private ArrayList<String> numeros =new ArrayList<>();
    private ArrayList<Pregunta> subPreguntas =mfc.getUniquiz().getListaPreguntasEstudiante().get(mfc.getContadorPregunta()).getSubpreguntas();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		numeros.add("1");
		numeros.add("2");
		numeros.add("3");
		numeros.add("4");
		ordenar1.setText(subPreguntas.get(0).getEnunciado());
		ordenar2.setText(subPreguntas.get(1).getEnunciado());
		ordenar3.setText(subPreguntas.get(2).getEnunciado());
		ordenar4.setText(subPreguntas.get(3).getEnunciado());
		combo1.getItems().addAll(numeros);
		combo2.getItems().addAll(numeros);
		combo3.getItems().addAll(numeros);
		combo4.getItems().addAll(numeros);
	}

	@FXML
    void guardarRespuesta1() {
		mfc.getUniquiz().getEnunciados().set(0, combo1.getValue());
    }

    @FXML
    void guardarRespuesta2() {
    	mfc.getUniquiz().getEnunciados().set(1, combo2.getValue());
    }

    @FXML
    void guardarRespuesta3() {
    	mfc.getUniquiz().getEnunciados().set(2, combo3.getValue());
    }

    @FXML
    void guardarRespuesta4() {
    	mfc.getUniquiz().getEnunciados().set(3, combo4.getValue());
    }

}
