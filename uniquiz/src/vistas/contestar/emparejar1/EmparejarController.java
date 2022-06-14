package vistas.contestar.emparejar1;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import control.ModelFactoryController;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class EmparejarController implements Initializable {

	@FXML
    private Label emparejar1;

    @FXML
    private Label emparejar2;

    @FXML
    private Label emparejar3;

    @FXML
    private Label emparejar4;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		numeros.add(mfc.getUniquiz().getListaPreguntasEstudiante().get(mfc.getContadorPregunta()).getRespuestas().get(0).getRespuesta());
		numeros.add(mfc.getUniquiz().getListaPreguntasEstudiante().get(mfc.getContadorPregunta()).getRespuestas().get(1).getRespuesta());
		numeros.add(mfc.getUniquiz().getListaPreguntasEstudiante().get(mfc.getContadorPregunta()).getRespuestas().get(2).getRespuesta());
		numeros.add(mfc.getUniquiz().getListaPreguntasEstudiante().get(mfc.getContadorPregunta()).getRespuestas().get(3).getRespuesta());
		emparejar1.setText(mfc.getUniquiz().getListaPreguntasEstudiante().get(mfc.getContadorPregunta()).getSubpreguntas().get(0).getEnunciado());
		emparejar2.setText(mfc.getUniquiz().getListaPreguntasEstudiante().get(mfc.getContadorPregunta()).getSubpreguntas().get(1).getEnunciado());
		emparejar3.setText(mfc.getUniquiz().getListaPreguntasEstudiante().get(mfc.getContadorPregunta()).getSubpreguntas().get(2).getEnunciado());
		emparejar4.setText(mfc.getUniquiz().getListaPreguntasEstudiante().get(mfc.getContadorPregunta()).getSubpreguntas().get(3).getEnunciado());
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
    void guardarRespuesta4s() {
    	mfc.getUniquiz().getEnunciados().set(3, combo4.getValue());
    }
}
