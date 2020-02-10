/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbd_salonsamochodowy;

import com.sun.javafx.stage.StageHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Rafal
 */
public class FXMLZalogujController implements Initializable {

    private Connection conn;

    private ObservableList<Pracownik> listPracownicy = FXCollections.observableArrayList();

    @FXML
    private TextField loginImieField;

    @FXML
    private Button zalogujButton;

    @FXML
    private TextField loginNazwiskoField;

    @FXML
    public void zalogujButtonOnAction(ActionEvent action) throws IOException, SQLException {
//        listPracownicy = new Pracownik().getRestricted(conn, pracownikSearchField.getText().trim());
//        conn = DBConnection.getConnection();
//        setPracownicyTable(listPracownicy);

        conn = DBConnection.getConnection();
        String imie = loginImieField.getText().trim();
        if (imie.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Imię\" nie może być puste");
            alert.showAndWait();
            return;
        }
        String nazwisko = loginNazwiskoField.getText().trim();
        if (nazwisko.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Nazwisko\" nie może być puste");
            alert.showAndWait();
            return;
        }

        Integer ID = new Pracownik().checkPracownik(conn, nazwisko, imie);
//        Stage s = (Stage) zalogujButton.getScene().getWindow();
//        s.setHeight(425);
//        s.setWidth(618);
        if (ID == -1) {
            //TODO 
            ID = new Klient().checkKlient(conn, nazwisko, imie);

            if (ID == -1) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Klient/Pracownik nie istnieje");
            alert.showAndWait();
            return;
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLSamochody.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                FXMLSamochodyController controller = fxmlLoader.<FXMLSamochodyController>getController();
                controller.initController(imie, nazwisko);

                //Parent loader = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                zalogujButton.getScene().setRoot(root);
                
            }
        } else {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLPracownik.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            FXMLPracownikController controller = fxmlLoader.<FXMLPracownikController>getController();
            controller.setZalogowanyPracownik(nazwisko,imie);

            //Parent loader = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            zalogujButton.getScene().setRoot(root);
        }
        conn.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //conn = DBConnection.getConnection();
        Pracownik pracownik = new Pracownik();

    }

}
