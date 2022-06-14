package vistas.contestar.preguntaAbierta;

import java.net.URL;
import java.util.ResourceBundle;

import control.ModelFactoryController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class PreguntaAbiertaController implements Initializable{

	@FXML
    private TextArea enunciado;

	private final ModelFactoryController mfc = ModelFactoryController.getInstance();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
    void guardarRespuesta() {
		mfc.setEnunciado(enunciado.getText());
    }


}
