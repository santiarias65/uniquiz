/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author santi
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField correo;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton entrar;
    @FXML
    private JFXButton crearCuenta;
    
    private ModelFactoryController mfc = ModelFactoryController.getInstance();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //mfc.abrirConexion();
        //mfc.obtenerTemasBd();
        //mfc.cerrarConexion();
        //UniQuiz uniquiz = mfc.getUniquiz();
        
        //System.out.println(uniquiz.getListaTemas().get(0));
    }    

    @FXML
    private void iniciarSesion(ActionEvent event) {
        //quemado para iniciar ligero
        //int bandera = mfc.validarLogin("santiarias@gmail.com","SANTInacional");
        int bandera = mfc.validarLogin(correo.getText(),password.getText());
        if(bandera==1) {
        	abrirPaginaProfesor();
        }
        if(bandera==2) {
        	abrirPaginaEstudiante();
        }
        if(bandera==0) {
        	JOptionPane.showMessageDialog( null, "Credenciales Invalidas" );
        }
    }

    @FXML
    private void crearCuenta(ActionEvent event) {
    }
    
    public void abrirPaginaProfesor(){
        mfc.getMain().abrirPaginaProfesor();
    }
    
    public void abrirPaginaEstudiante() {
    	//mfc.abrirDatosEstudiante();
    	mfc.getMain().abrirPaginaEstudiante();
    }
}
