/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbd_salonsamochodowy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Rafal
 */
public class WBD_SalonSamochodowy extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//        
//        Scene scene = new Scene(root);
//        
//        stage.setScene(scene);
//        stage.show();

//    Parent root = FXMLLoader.load(getClass().getResource("FXMLZaloguj.fxml"));
//    Scene scene = new Scene(root);
//    stage.setScene(scene);
//    stage.show();
    
            Parent root = FXMLLoader.load(getClass().getResource("FXMLZaloguj.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Salon Samochodowy");
        Image icon = new Image(getClass().getResourceAsStream("IkonkaSamochodu.png"));
        stage.getIcons().add(icon);
   
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
