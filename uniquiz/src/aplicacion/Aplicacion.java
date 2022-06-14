/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import control.LoginController;
import control.ModelFactoryController;
import control.PresentarExamenController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author santi
 */
public class Aplicacion extends Application {
        private Stage stagePrincipal;
        private Stage stageModal;
        private ModelFactoryController mfc = ModelFactoryController.getInstance();

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
                mfc.setMain(this);

                try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/Login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stagePrincipal = primaryStage;
		stagePrincipal.setScene(scene);
		stagePrincipal.show();
            } catch (Exception e) {
                    System.out.println(e);
            }

	}
    public void abrirPaginaProfesor(){
            mfc.abrirDatosProfesor();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/PaginaProfesor.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                stagePrincipal.setScene(scene);
                stagePrincipal.show();


            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    public void abrirPaginaEstudiante() {
    	mfc.abrirDatosEstudiante();
		// TODO Auto-generated method stub
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/PaginaEstudiante.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stagePrincipal.setScene(scene);
            stagePrincipal.show();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
    public void abrirPaginaPresentarExamen() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplicacion.class.getResource("/vistas/PresentarExamen.fxml"));
			loader.setController(new PresentarExamenController());
			AnchorPane vistaIndex = (AnchorPane) loader.load();
			Scene scene = new Scene(vistaIndex);
			stagePrincipal.setScene(scene);
			stagePrincipal.show();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
    public void abrirPaginaConfiguracionPreguntas(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/ConfiguracionPreguntas.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stageModal = new Stage();

            stageModal.setScene(scene);

            stageModal.initModality(Modality.WINDOW_MODAL);

            stageModal.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarPaginaExamen() {
		stagePrincipal.close();
	}

    public static void main(String[] args) {
	launch(args);
    }

}
