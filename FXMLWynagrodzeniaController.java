/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbd_salonsamochodowy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Rafal
 */
public class FXMLWynagrodzeniaController {

    @FXML
    private AnchorPane klientAnchorPane;

    @FXML
    private Label imiePracownik;

    @FXML
    private Label nazwiskoPracownik;

    @FXML
    private Button wynagrodzeniaWylogujButton;

    @FXML
    private Button wynagrodzeniaZatwierdzButton;

    @FXML
    private Label imieLogin;

    @FXML
    private Label nazwiskoLogin;

    @FXML
    private TextField pracownikIDField;

    @FXML
    private Label pracownikIDLabel;

    @FXML
    private Label pracownikNazwiskoLabel;

    @FXML
    private TextField pracownikNazwiskoField;

    @FXML
    private Label pracownikImieLabel;

    @FXML
    private TextField pracownikImieField;

    @FXML
    private Label pracownikPESELLabel;

    @FXML
    private TextField pracownikPESELField;

    @FXML
    private Label pracownikPlecLabel;

    @FXML
    private ChoiceBox<String> pracownikPlecField;

    private void initPracownikPlecField() {
        pracownikPlecField.getItems().add("Kobieta");
        pracownikPlecField.getItems().add("Mężczyzna");
    }

    @FXML
    private Label pracownikNrKontaLabel;

    @FXML
    private TextField pracownikNrKontaField;

    @FXML
    private Label pracownikMiastoLabel;

    @FXML
    private Label pracownikUlicaLabel;

    @FXML
    private Label pracownikNrMieszkaniaLabel;

    @FXML
    private Label pracownikNrDomuLabel;

    @FXML
    private Label pracownikKodPocztowyLabel;

    @FXML
    private Label pracownikAdresMailLabel;

    @FXML
    private TextField pracownikMiastoField;

    @FXML
    private TextField pracownikUlicaField;

    @FXML
    private TextField pracownikNrDomuField;

    @FXML
    private TextField pracownikNrMieszkaniaField;

    @FXML
    private TextField pracownikKodPocztowyField;

    @FXML
    private TextField pracownikAdresMailField;

    @FXML
    private Button wynagrodzeniaAnulateButton;

    private String imiePracownika;

    private String nazwiskoPracownika;

    private Integer pracownikID;

    private Pracownik pracownik;

    private Connection conn;

    private ObservableList<Wynagrodzenia> listWynagrodzenia = FXCollections.observableArrayList();

    public void initController(Pracownik prac, String imieLogin, String nazwiskoLogin) throws SQLException{
        this.imieLogin.setText(imieLogin);
        this.nazwiskoLogin.setText(nazwiskoLogin);
        this.imieLogin.setAlignment(Pos.CENTER_RIGHT);
        this.nazwiskoLogin.setAlignment(Pos.CENTER_RIGHT);
        
        initPracownikPlecField();
        pracownikID = prac.getPracownikID();
        
        if (pracownikID != -1) {
            pracownikID = prac.getPracownikID();
            pracownik = prac;
            imiePracownik.setText(prac.getPracownikImie());
            nazwiskoPracownik.setText(prac.getPracownikNazwisko());
            conn = DBConnection.getConnection();

            

            pracownikIDField.setText(prac.getPracownikID().toString());
            pracownikImieField.setText(prac.getPracownikImie());
            pracownikNazwiskoField.setText(prac.getPracownikNazwisko());

            pracownikPESELField.setText(prac.getPracownikPESEL());
            String plec = prac.getPracownikPlec();

            if (plec.startsWith("M")) {
                plec = "Mężczyzna";
            } else {
                plec = "Kobieta";
            }
            pracownikPlecField.setValue(plec);
            pracownikNrKontaField.setText(prac.getPracownikNrKonta());

            pracownikMiastoField.setText(prac.getPracownikMiasto());
            pracownikUlicaField.setText(prac.getPracownikUlica());
            pracownikNrDomuField.setText(prac.getPracownikNrDomu());

            pracownikNrMieszkaniaField.setText(prac.getPracownikNrMieszkania());
            pracownikKodPocztowyField.setText(prac.getPracownikKodPocztowy());
            pracownikAdresMailField.setText(prac.getPracownikAdresMail());

            pracownikIDField.setDisable(true);
        } else {
            imiePracownik.setText(" ");
            nazwiskoPracownik.setText(" ");
            pracownikIDField.setDisable(true);
        }
        

    }

    @FXML
    public void pracownikAnulateButtonOnAction(ActionEvent action) throws Exception {
        changeToPreviousView();
    }

    private void changeToPreviousView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLPracownik.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        FXMLPracownikController controller = fxmlLoader.<FXMLPracownikController>getController();
        controller.setZalogowanyPracownik(imieLogin.getText(), nazwiskoLogin.getText());

        //Parent loader = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        imiePracownik.getScene().setRoot(root);

    }

    @FXML
    public void pracownikZatwierdzButtonOnAction(ActionEvent action) throws Exception{

        conn = DBConnection.getConnection();

        String imie = pracownikImieField.getText().trim();
        if (imie.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Imię\" nie może być puste");
            alert.showAndWait();
            return;
        }
        String nazwisko = pracownikNazwiskoField.getText().trim();
        if (nazwisko.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Nazwisko\" nie może być puste");
            alert.showAndWait();
            return;
        }

        String pesel = pracownikPESELField.getText().trim();
        if (pesel.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"PESEL\" nie może być puste");
            alert.showAndWait();
            return;
        }

        String plec = (String) pracownikPlecField.getValue();

        if (plec == "Kobieta") {
            plec = "K";
        }

        if (plec == "Mężczyzna") {
            plec = "M";
        }
        if (plec.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Płeć\" nie może być puste");
            alert.showAndWait();
            return;
        }

        String nrKonta = pracownikNrKontaField.getText().trim();
        if (nrKonta.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Nr Konta\" nie może być puste");
            alert.showAndWait();
            return;
        }

        String miasto = pracownikMiastoField.getText().trim();
        if (miasto.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Miasto\" nie może być puste");
            alert.showAndWait();
            return;
        }

        String kodPocztowy = pracownikKodPocztowyField.getText().trim();
        if (kodPocztowy.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Kod Pocztowy\" nie może być puste");
            alert.showAndWait();
            return;
        }

        String ulica = pracownikUlicaField.getText().trim();
        if (ulica.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Ulica\" nie może być puste");
            alert.showAndWait();
            return;
        }

        String nrDomu = pracownikNrDomuField.getText().trim();
        if (nrDomu.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Numer Domu\" nie może być puste");
            alert.showAndWait();
            return;

        }

        String nrMieszkania = pracownikNrMieszkaniaField.getText().trim();
        if (nrMieszkania.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Numer Mieszkania\" nie może być puste");
            alert.showAndWait();
            return;
        }

        String adresMail = pracownikAdresMailField.getText().trim();
        if(pracownikAdresMailField.getText().isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Adres E-Mail\" nie może być puste");
            alert.showAndWait();
            return;
        }
        if (adresMail.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Adres E-Mail\" nie może być puste");
            alert.showAndWait();
            return;
        }

        if (pracownikID != -1) {
            Integer result = new Pracownik().updatePracownik(conn, Integer.parseInt(pracownikIDField.getText()), imie, nazwisko, pesel, plec, nrKonta, miasto, kodPocztowy, ulica, nrDomu, nrMieszkania, adresMail);
            /*if (result > 0) {

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Potwierdzenie zmiany danych");
            alert2.setContentText("Dane pracownika zostały zmienione");
            alert2.showAndWait();

        }*/
        } else {

            new Pracownik().insertPracownik(conn, imie, nazwisko, pesel, plec, nrKonta, miasto, kodPocztowy, ulica, nrDomu, nrMieszkania, adresMail);
        }

        conn.close();
        changeToPreviousView();
    }
}
