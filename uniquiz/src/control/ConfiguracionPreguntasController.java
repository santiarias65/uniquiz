/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Pregunta;

/**
 * FXML Controller class
 *
 * @author santi
 */
public class ConfiguracionPreguntasController implements Initializable {

    @FXML
    private TableView<Pregunta> tablaPreguntas;
    @FXML
    private TableColumn<Pregunta,String> columnaPreguntas;
    @FXML
    private TableColumn<Pregunta, String> columnaTipo;
    @FXML
    private TableView<Pregunta> tablaPreguntasSeleccionadas;
    
    @FXML
    private TableColumn<Pregunta,String> columnaPreguntasSeleccionadas;
    @FXML
    private TableColumn<String, String> columnaTipoSeleccionadas;
    @FXML
    private TextField cantidadPreguntasConfigurarAutomatico;
    
    private final ModelFactoryController mfc = ModelFactoryController.getInstance();
    //private final ArrayList<Pregunta>listaPreguntas = mfc.listaPreguntasProfesorPublicas();
    
    private ObservableList<Pregunta> observableListaPreguntas= FXCollections.observableList(mfc.listaPreguntasProfesorPublicas());
    private ArrayList<Pregunta>listaPreguntasSeleccionadas = mfc.getObservablePreguntasSeleccionadas();
    private ObservableList<Pregunta> observableListaPreguntasSeleccionadas= FXCollections.observableList(listaPreguntasSeleccionadas);
    //variables usadas para la logica
    private Pregunta preguntaSeleccionada;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        mfc.controlPregunta = this;
        columnaPreguntas.setCellValueFactory(new PropertyValueFactory<>("enunciado"));
        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("tipoPregunta"));
        columnaPreguntasSeleccionadas.setCellValueFactory(new PropertyValueFactory<>("enunciado"));
        columnaTipoSeleccionadas.setCellValueFactory(new PropertyValueFactory<>("tipoPregunta"));
        tablaPreguntas.setItems(observableListaPreguntas);
        tablaPreguntasSeleccionadas.setItems(observableListaPreguntasSeleccionadas);
    }    
    
    @FXML
    private void agregarPreguntaExamen(MouseEvent event) {
        preguntaSeleccionada = tablaPreguntas.getSelectionModel().getSelectedItem();
        listaPreguntasSeleccionadas.add(preguntaSeleccionada);
        tablaPreguntasSeleccionadas.refresh();
    }

    @FXML
    private void terminarConfiguracion(MouseEvent event) {
        int[]idPreguntasSeleccionadas = new int[listaPreguntasSeleccionadas.size()];
        for (int i = 0; i < listaPreguntasSeleccionadas.size(); i++) {
            idPreguntasSeleccionadas[i] = listaPreguntasSeleccionadas.get(i).getId();
        }
        mfc.setObservablePreguntasSeleccionadas(listaPreguntasSeleccionadas);
        mfc.setPreguntasSeleccionadasExamen(idPreguntasSeleccionadas);
        mfc.control.verificarConfiguracionExamen();
        Stage myStage = (Stage)this.tablaPreguntas.getScene().getWindow();
        myStage.close();
    }

    @FXML
    private void configuracionAutomatica(MouseEvent event) {
        ArrayList<Pregunta>listaP;
        int cantidadPreguntas = Integer.parseInt(cantidadPreguntasConfigurarAutomatico.getText());
        int idTema = mfc.control.temaSeleccionado.getId();
        listaP = mfc.configurarExamenAutomatico(idTema,cantidadPreguntas);
        listaPreguntasSeleccionadas.clear();
        listaPreguntasSeleccionadas.addAll(listaP);
        tablaPreguntasSeleccionadas.refresh();
    }
    
    public void cambioTema() {
    	listaPreguntasSeleccionadas.clear();
    	tablaPreguntasSeleccionadas.refresh();
    }
    
   
}
