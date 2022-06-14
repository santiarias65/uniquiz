package control;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.Examen;
import model.Grupo;

public class PaginaEstudianteController implements Initializable {

	@FXML
	private ComboBox<Grupo> grupos;

	@FXML
	private ComboBox<Examen> examanes;

	@FXML
	private Label numPreguntas;

	@FXML
	private Label tiempo;

	@FXML
	private Label porcentaje;

	@FXML
	private Label umbral;

	@FXML
	private Button realizarExamen;

	@FXML
	private Label nombre;
	@FXML
    private BarChart<String,Integer> graficoExamenPorEstudiante;

	private final ModelFactoryController mfc = ModelFactoryController.getInstance();

	private final ArrayList<Grupo> listaGrupos = mfc.listaGruposEst();

	private ArrayList<Examen> listaExamanes;

	Examen examen = new Examen();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		iniciarGraficoExamenesPorEstudiante();
		realizarExamen.setDisable(true);
		nombre.setText(mfc.obtenerNombreProfesroLogueado());
		grupos.getItems().addAll(listaGrupos);
		//mfc.cerrarConexion();
	}

	public void limpiarCamposExamen() {
		numPreguntas.setText("");
		tiempo.setText("");
		porcentaje.setText("");
		umbral.setText("");
	}

	@FXML
	void listarExamanes() {
		examanes.getItems().clear();
		limpiarCamposExamen();
		realizarExamen.setDisable(true);
		mfc.obtenerExamenesEstudiates(grupos.getValue().getId());
		listaExamanes = mfc.listaExamanesEst();
		examanes.getItems().addAll(listaExamanes);

	}

	@FXML
	void detalleExamen() {
		limpiarCamposExamen();
		mfc.setExamenId(examanes.getValue().getId());
		try {
			examen = mfc.obtenerDatosExamen(examanes.getValue().getId());
			mfc.setNumPreguntas(examen.getNumeroP());
		} catch (Exception e) {
			e.getMessage();
		}
		numPreguntas.setText("" + examen.getNumeroP());
		tiempo.setText(String.valueOf(examen.getTiempo()));
		porcentaje.setText("" + examen.getPorcentaje());
		umbral.setText("" + examen.getUmbral());
		realizarExamen.setDisable(false);

	}

	@FXML
	void presentarExamen() {

		mfc.obtenerPreguntasExamenes(examen.getId());
		mfc.getMain().abrirPaginaPresentarExamen();
		mfc.setExamenId(examen.getId());
		realizarExamen.setDisable(true);
	}
	
	
	
	@FXML
    private void seleccionReportes(Event event) {
		graficoExamenPorEstudiante.getData().clear();
        iniciarGraficoExamenesPorEstudiante();
    }
	
	 public void iniciarGraficoExamenesPorEstudiante(){
	        XYChart.Series serie = new XYChart.Series<>();
	        serie.setName("Examenes");
	        ResultSet rs = mfc.graficoExamaneEstudiante();
	        try {
	            while(rs.next()){
	                serie.getData().add(new XYChart.Data(rs.getString(1),rs.getDouble(2)));
	            }
	        } catch (SQLException ex) {
	            Logger.getLogger(PaginaProfesorController.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        graficoExamenPorEstudiante.getData().addAll(serie);
	        
	        
	    }
}
