/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Grupo;
import model.Tema;

/**
 * FXML Controller class
 *
 * @author santi
 */
public class PaginaProfesorController implements Initializable {


    @FXML
    private ComboBox<Tema> comboTema;
    @FXML
    private DatePicker fechaInicio;
    @FXML
    private DatePicker fechaFinal;
    @FXML
    private TextField textNumeroPreguntas;
    @FXML
    private TextField textMinutos;
    @FXML
    private TextField textPeso;
    @FXML
    private TextField TextUmbral;
    @FXML
    public Label labelNombre;
    @FXML
    private ComboBox<Grupo> comboGrupo;
    @FXML
    private Button botonCrearExamen;
    @FXML
    private BarChart<String,Integer> graficoExamenPorGrupo;
    @FXML
    private Button botonConfigurarPreguntas;

    private final ModelFactoryController mfc = ModelFactoryController.getInstance();
    private ArrayList<Tema>listaTemas;
    private ArrayList<Grupo>listaGrupos = mfc.listaGruposProfesor();


    //variables para logica
    public Tema temaSeleccionado;
    private Grupo grupoSeleccionado;





    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //mandamos el controlador al control de controladores para trabajar con un solo control por fxml
        mfc.control=this;


        comboGrupo.getItems().addAll(listaGrupos);
        iniciarGraficoExamenesPorGrupo();
        labelNombre.setText(mfc.obtenerNombreProfesroLogueado());
    }

    @FXML
    private void volver(ActionEvent event) {
    }

    @FXML
    private void crearExamen(MouseEvent event) {
        //datos necesarios null,idProfesor,fechainicio,fechafin,numeropreguntas,tiempo,pesoporcentaje,umbral,idtema
        temaSeleccionado = comboTema.getValue();
        grupoSeleccionado = comboGrupo.getValue();
        int numeroPreguntas = Integer.parseInt(textNumeroPreguntas.getText());
        int tiempo = Integer.parseInt(textMinutos.getText());
        Double peso= Double.parseDouble(textPeso.getText());
        Double umbral = Double.parseDouble(TextUmbral.getText());
        mfc.crearExamenBd(fechaInicio.getValue(),fechaFinal.getValue(),numeroPreguntas,tiempo,peso,umbral,temaSeleccionado.getId(),grupoSeleccionado.getId());

        //creamos el banco
        int banderaUltimoExamen = mfc.ultimoExamenCreado();
        mfc.crearBanco(banderaUltimoExamen,mfc.getPreguntasSeleccionadasExamen());
        mfc.crearEstudiantesExamenPorGrupo(grupoSeleccionado.getId(),banderaUltimoExamen);
    }

    @FXML
    private void abrirPreguntas(MouseEvent event) {
        mfc.getMain().abrirPaginaConfiguracionPreguntas();
    }

    public void verificarConfiguracionExamen(){
        if(mfc.getPreguntasSeleccionadasExamen().length>0)botonCrearExamen.setDisable(false);
    }
    //reporte: de cantidad de examenes por grupo
    public void iniciarGraficoExamenesPorGrupo(){
        XYChart.Series serie = new XYChart.Series<>();
        serie.setName("Examenes");
        ResultSet rs = mfc.graficoExamenesPorGrupo();
        try {
            while(rs.next()){
                serie.getData().add(new XYChart.Data(rs.getString(1),rs.getInt(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaginaProfesorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        graficoExamenPorGrupo.getData().addAll(serie);


    }

    //prueba graficoos de pastel
/*        ResultSet p = mfc.pruebaGrafico();
        try {
            while(p.next()){
                datosGrafico.add(new PieChart.Data(p.getString(1),p.getInt(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaginaProfesorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        grafico.setData(datosGrafico);

        //fin prueba graficoos*/

    @FXML
    private void seleccionTema(ActionEvent event) {
    	if(mfc.controlPregunta!=null) {
    		mfc.controlPregunta.cambioTema();
    	}
        if(comboTema.getSelectionModel().getSelectedIndex()!=-1){
            temaSeleccionado = comboTema.getSelectionModel().getSelectedItem();
            mfc.getUniquiz().getListaPreguntasProfesores().clear();
            int idTema = comboTema.getSelectionModel().getSelectedItem().getId();
            mfc.obtenerPreguntasConfiguracionExamen(idTema);
            verificarCondiciones();
        }
    }
    @FXML
    private void seleccionGrupo(ActionEvent event) {
        try {
            grupoSeleccionado= comboGrupo.getSelectionModel().getSelectedItem();
            comboTema.getItems().clear();
            mfc.obtenerTemasBd(grupoSeleccionado.getId());


            listaTemas = mfc.listaTemas();
            comboTema.getItems().addAll(listaTemas);
            comboTema.setDisable(false);
        } catch (Exception e) {

            grupoSeleccionado= comboGrupo.getSelectionModel().getSelectedItem();
            comboTema.getItems().clear();
            mfc.obtenerTemasBd(grupoSeleccionado.getId());


            listaTemas = mfc.listaTemas();
            comboTema.getItems().addAll(listaTemas);
        }
        verificarCondiciones();
    }
    @FXML
    private void seleccionReportes(Event event) {
        graficoExamenPorGrupo.getData().clear();

        iniciarGraficoExamenesPorGrupo();
    }

    public void verificarCondiciones(){
        if(grupoSeleccionado!=null&&temaSeleccionado!=null){
            botonConfigurarPreguntas.setDisable(false);
        }
    }
}
