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
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class FXMLKlientController implements Initializable {

    private Connection conn;

    private ObservableList<Transakcja> listTranskacje = FXCollections.observableArrayList();

    @FXML
    private AnchorPane klientAnchorPane;

    @FXML
    private TableView<Transakcja> transakcjeTable;

    @FXML
    private TableColumn<Transakcja, Date> transakcjeDataColumn;

    @FXML
    private TableColumn<Transakcja, String> transakcjeMarkaColumn;

    @FXML
    private TableColumn<Transakcja, String> transkacjeModelColumn;

    @FXML
    private TableColumn<Transakcja, Float> transakcjeCenaColumn;

    @FXML
    private TextField klientIDField;

    @FXML
    private TextField klientNazwiskoField;

    @FXML
    private TextField klientImieField;

    @FXML
    private Label klientIDLabel;

    @FXML
    private Label klientNazwiskoLabel;

    @FXML
    private Label klientImieLabel;

    @FXML
    private Button klientModifyButton;

    @FXML
    private TextField klientPESELField;

    @FXML
    private Label klientPESELLabel;

    @FXML
    private ChoiceBox<String> klientPlecField;

    private void initKlientPlecField() {
        klientPlecField.getItems().add("Kobieta");
        klientPlecField.getItems().add("Mężczyzna");
    }

    @FXML
    private Label klientPlecLabel;

    @FXML
    private Label klientNrKontaLabel;

    @FXML
    private TextField klientNrKontaField;

    @FXML
    private Label klientMiastoLabel;

    @FXML
    private Label klientUlicaLabel;

    @FXML
    private Label klientNrDomuLabel;

    @FXML
    private Label klientNrMieszkaniaLabel;

    @FXML
    private Label klientKodPocztowyLabel;

    @FXML
    private Label klientAdresMailLabel;

    @FXML
    private TextField klientMiastoField;

    @FXML
    private TextField klientUlicaField;

    @FXML
    private TextField klientNrDomuField;

    @FXML
    private TextField klientNrMieszkaniaField;

    @FXML
    private TextField klientKodPocztowyField;

    @FXML
    private TextField klientAdresMailField;

    @FXML
    private Label imieLogin;

    @FXML
    private Label nazwiskoLogin;

    @FXML
    private Button klientWylogujButton;

    @FXML
    private Label imiePowitanie;

    private String imie;

    private String nazwisko;

    private Klient klient;

    private void setTransakcjeTable() {
        conn = DBConnection.getConnection();
        listTranskacje = new Transakcja().getRestricted(conn, klient.getKlientID());
        PropertyValueFactory<Transakcja, Date> data = new PropertyValueFactory<>("data");
        PropertyValueFactory<Transakcja, String> marka = new PropertyValueFactory<>("marka");
        PropertyValueFactory<Transakcja, String> model = new PropertyValueFactory<>("model");
        PropertyValueFactory<Transakcja, Float> cena = new PropertyValueFactory<>("cena");
        transakcjeDataColumn.setCellValueFactory(data);
        transakcjeMarkaColumn.setCellValueFactory(marka);
        transakcjeCenaColumn.setCellValueFactory(cena);
        transkacjeModelColumn.setCellValueFactory(model);
        transakcjeTable.setItems(listTranskacje);
        try {
            conn.close();
        } catch (SQLException exc) {
        }
    }

    public void setZalogowanyKlient(String imie, String nazwisko) {
        this.imie = imie;
        System.out.println(imie);
        this.nazwisko = nazwisko;
        imieLogin.setText(imie);
        nazwiskoLogin.setText(nazwisko);
        imiePowitanie.setText(" "+imie+"!");
        imieLogin.setAlignment(Pos.CENTER_RIGHT);
        nazwiskoLogin.setAlignment(Pos.CENTER_RIGHT);

        updateView();
        setTransakcjeTable();
    }

    private void updateView() {

        conn = DBConnection.getConnection();
        klient.initKlient(conn, imie, nazwisko);

        klientImieField.setText(klient.getKlientImie());
        klientNazwiskoField.setText(klient.getKlientNazwisko());
        klientMiastoField.setText(klient.getKlientMiasto());
        klientNrDomuField.setText(klient.getKlientNrDomu());
        klientUlicaField.setText(klient.getKlientUlica());
        klientNrMieszkaniaField.setText(klient.getKlientNrMieszkania());
        klientNrDomuField.setText(klient.getKlientNrDomu());
        klientKodPocztowyField.setText(klient.getKlientKodPocztowy());
        klientPESELField.setText(klient.getKlientPESEL());
        klientAdresMailField.setText(klient.getKlientAdresMail());
        String plec = klient.getKlientPlec();
        if(!plec.isEmpty()){
        System.out.println(plec);
        if (plec.startsWith("M")) {
            plec = "Mężczyzna";
        } else {
            plec = "Kobieta";
        }}
        klientPlecField.setValue(plec);

        try {
            conn.close();
        } catch (SQLException exc) {
        }
    }

    @FXML
    public void klientWylogujButtonOnAction(ActionEvent action) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLZaloguj.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("FXMLZaloguj.fxml"));
        Scene sce;
        //try{
        sce = klientImieField.getScene();
        //catch(NullPointerException exc){
        //System.out.println("Whatever");

        //}
        sce.setRoot(root);

        StageHelper.getStages().get(0).setHeight(300);
        StageHelper.getStages().get(0).setWidth(400);
    }

    @FXML
    public void klientSamochodyButtonOnAction(ActionEvent action) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLSamochody.fxml"));
        try {
            Parent root = (Parent) fxmlLoader.load();

            FXMLSamochodyController controller = fxmlLoader.<FXMLSamochodyController>getController();
            Pracownik pracownik = new Pracownik();
            pracownik.setPracownikID(-1);
            try {
                controller.initController(nazwiskoLogin.getText(), imieLogin.getText());//pracownicyTable.getSelectionModel().getSelectedItem());
            } catch (Exception exc) {
            }
            //Parent loader = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            klientAdresMailField.getScene().setRoot(root);
        } catch (IOException exc) {
        }

        StageHelper.getStages().get(0).setHeight(555);
        StageHelper.getStages().get(0).setWidth(930);
    }

    @FXML
    public void klientModifyButtonOnAction(ActionEvent action) {

        conn = DBConnection.getConnection();

        String imie = klientImieField.getText().trim();
        if (imie.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Imię\" nie może być puste");
            alert.showAndWait();
            return;
        }
        String nazwisko = klientNazwiskoField.getText().trim();
        if (nazwisko.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Nazwisko\" nie może być puste");
            alert.showAndWait();
            return;
        }

        String pesel = klientPESELField.getText().trim();
        if (pesel.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"PESEL\" nie może być puste");
            alert.showAndWait();
            return;
        }

        String plec = (String) klientPlecField.getValue();
        System.out.println(plec);
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

        String miasto = klientMiastoField.getText().trim();
        if (pesel.length()==0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Miasto\" nie może być puste");
            alert.showAndWait();
            return;
        }

        String kodPocztowy = klientKodPocztowyField.getText().trim();
        if (pesel.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Kod Pocztowy\" nie może być puste");
            alert.showAndWait();
            return;
        }

        String ulica = klientUlicaField.getText().trim();
        if (pesel.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Ulica\" nie może być puste");
            alert.showAndWait();
            return;
        }

        String nrDomu = klientNrDomuField.getText().trim();
        if (pesel.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Numer Domu\" nie może być puste");
            alert.showAndWait();
            return;

        }

        String nrMieszkania = klientNrMieszkaniaField.getText().trim();
        if (pesel.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Numer Mieszkania\" nie może być puste");
            alert.showAndWait();
            return;
        }

        String adresMail = klientAdresMailField.getText().trim();
        if (pesel.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Adres E-Mail\" nie może być puste");
            alert.showAndWait();
            return;
        }
        Integer result = new Klient().updateKlient(conn, klient.getKlientID(), imie, nazwisko, pesel, plec, miasto, kodPocztowy, ulica, nrDomu, nrMieszkania, adresMail);
        if (result > 0) {

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Potwierdzenie zmiany danych");
            alert2.setContentText("Twoje dane zostały zmienione");
            alert2.showAndWait();

        }
        klient.initKlient(conn, imie, nazwisko);
        imieLogin.setText(klient.getKlientImie());
        nazwiskoLogin.setText(klient.getKlientNazwisko());
        imiePowitanie.setText(" "+klient.getKlientImie()+"!");
        updateView();

        try {
            conn.close();
        } catch (SQLException exc) {
        }

    }

    private void setTransakcjeTable(ObservableList<Klient> listKlienci) {
        PropertyValueFactory<Klient, Integer> ID = new PropertyValueFactory<>("KlientID");
        PropertyValueFactory<Klient, String> imie = new PropertyValueFactory<>("KlientImie");
        PropertyValueFactory<Klient, String> nazwisko = new PropertyValueFactory<>("KlientNazwisko");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initKlientPlecField();
        System.out.println("Hello!");
        conn = DBConnection.getConnection();
        klient = new Klient();

        //set up of columns
        new Wynagrodzenia().getRestricted(conn, 1);
        //setting size
        StageHelper.getStages().get(0).setHeight(555);
        StageHelper.getStages().get(0).setWidth(930);

        try {
            conn.close();
        } catch (SQLException exc) {
        }
    }

}
