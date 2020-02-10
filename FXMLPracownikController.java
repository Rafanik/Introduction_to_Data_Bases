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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Rafal
 */
public class FXMLPracownikController implements Initializable {

    private Connection conn;

    private ObservableList<Pracownik> listPracownicy = FXCollections.observableArrayList();

    @FXML
    private TableView<Pracownik> pracownicyTable;

    @FXML
    private TableColumn<Pracownik, Integer> pracownikIDColumn;

    @FXML
    private TableColumn<Pracownik, String> pracownikNazwiskoColumn;

    @FXML
    private TableColumn<Pracownik, String> pracownikImieColumn;

    @FXML
    private TextField pracownikSearchField;

    @FXML
    private Button pracownikSearchButton;

    @FXML
    private Button pracownikDeleteButton;

    @FXML
    private Button pracownikAddButton1;

    @FXML
    private Button pracownikAnulateButton;

    @FXML
    private Label imieLogin;

    @FXML
    private Label nazwiskoLogin;

    @FXML
    private Button pracownikWylogujButton;

    private Integer idKlienta;

    @FXML
    private TableView<Wynagrodzenia> wynagrodzeniaTable;

    @FXML
    private TableColumn<Wynagrodzenia, Date> wynagrodzeniaDataColumn;

    @FXML
    private TableColumn<Wynagrodzenia, Float> wynagrodzeniaKwotaColumn;

    @FXML
    private Button wynagrodzeniaAddButton;

    @FXML
    private Button wynagrodzeniaDeleteButton;

    @FXML
    private Label imieWynagrodzenia;

    @FXML
    private Label nazwiskoWynagordzenia;

    @FXML
    private Button pracownikModifyButton;

    @FXML
    private Button wynagrodzeniaModifyButton;

    @FXML
    private TextField pracownikDataField;

    @FXML
    private TextField pracownikKwotaField;

    @FXML
    private Button wynagrodzeniaAddButton1;

    public void setZalogowanyPracownik(String imie, String nazwisko) {

        imieLogin.setText(imie);
        nazwiskoLogin.setText(nazwisko);
    }

    private ObservableList<Wynagrodzenia> listWynagrodzenia;

    @FXML
    public void pracownikSearchButtonOnAction(ActionEvent action) throws SQLException {
        conn = DBConnection.getConnection();
        listPracownicy = new Pracownik().getRestricted(conn, pracownikSearchField.getText().trim());
        conn = DBConnection.getConnection();
        setPracownicyTable(listPracownicy);

        conn.close();
    }

    @FXML
    public void pracownikTableOnClick(MouseEvent event) {
        conn = DBConnection.getConnection();
        listWynagrodzenia = new Wynagrodzenia().getRestricted(conn, pracownicyTable.getSelectionModel().getSelectedItem().getPracownikID());

        setWynagrodzeniaTable(listWynagrodzenia);
        imieWynagrodzenia.setText(pracownicyTable.getSelectionModel().getSelectedItem().getPracownikImie());
        nazwiskoWynagordzenia.setText(pracownicyTable.getSelectionModel().getSelectedItem().getPracownikNazwisko());

        try {
            conn.close();
        } catch (SQLException exc) {
        }
    }

    private void setWynagrodzeniaTable(ObservableList<Wynagrodzenia> listWynagrodzenia) {
        wynagrodzeniaDataColumn.setCellValueFactory(new PropertyValueFactory<>("wynagrodzeniaData"));
        wynagrodzeniaKwotaColumn.setCellValueFactory(new PropertyValueFactory<>("wynagrodzenieKwota"));
        wynagrodzeniaTable.setItems(listWynagrodzenia);

    }

    @FXML
    public void pracownikWylogujButtonOnAction(ActionEvent action) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLZaloguj.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("FXMLZaloguj.fxml"));
        Scene sce;
        //try{
        sce = pracownikAnulateButton.getScene();

        //}
        sce.setRoot(root);

        StageHelper.getStages().get(0).setHeight(300);
        StageHelper.getStages().get(0).setWidth(400);
    }

    @FXML
    public void pracownikDeleteButtonOnAction(ActionEvent action) throws SQLException {

        Integer rowIndex = pracownicyTable.getSelectionModel().getSelectedIndex();

        if (rowIndex < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błąd");
            alert.setContentText("Nie wybrano pracownika do usunięcia");
            alert.showAndWait();
            return;
        }

        Integer ID = pracownicyTable.getSelectionModel().getSelectedItem().getPracownikID();

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Potwierdzenie usunięcia");
        alert1.setContentText("Czy na pewno chcesz usunąć tego pracownika?");
        Optional<ButtonType> res = alert1.showAndWait();
        conn = DBConnection.getConnection();

        if (res.get() == ButtonType.OK) {

            Integer result = new Pracownik().removePracownik(conn, ID);
            if (result > 0) {

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Potwierdzenie usunięcia");
                alert2.setContentText("Pracownik został usunięty");
                alert2.showAndWait();

            }
        }

        listPracownicy = new Pracownik().getAll(conn);
        setPracownicyTable(listPracownicy);
        listWynagrodzenia = new Wynagrodzenia().getRestricted(conn, pracownicyTable.getItems().get(0).getPracownikID());

        setWynagrodzeniaTable(listWynagrodzenia);
        imieWynagrodzenia.setText(pracownicyTable.getItems().get(0).getPracownikImie());
        nazwiskoWynagordzenia.setText(pracownicyTable.getItems().get(0).getPracownikNazwisko());


        conn.close();

    }

    @FXML
    public void pracownikAddButton1OnAction(ActionEvent action) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLWynagrodzenia.fxml"));
        try {
            Parent root = (Parent) fxmlLoader.load();

            FXMLWynagrodzeniaController controller = fxmlLoader.<FXMLWynagrodzeniaController>getController();
            Pracownik pracownik = new Pracownik();
            pracownik.setPracownikID(-1);
            controller.initController(pracownik, imieLogin.getText(), nazwiskoLogin.getText());//pracownicyTable.getSelectionModel().getSelectedItem());

            //Parent loader = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            pracownicyTable.getScene().setRoot(root);
        } catch (IOException exc) {
        }

    }

    @FXML
    public void pracownikModifyButtonOnAction(ActionEvent action) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLWynagrodzenia.fxml"));
        try {
            Parent root = (Parent) fxmlLoader.load();

            FXMLWynagrodzeniaController controller = fxmlLoader.<FXMLWynagrodzeniaController>getController();
            Pracownik pracownik = new Pracownik();
            pracownik.setPracownikID(-1);
            try {
                controller.initController(pracownicyTable.getSelectionModel().getSelectedItem(), imieLogin.getText(), nazwiskoLogin.getText());//pracownicyTable.getSelectionModel().getSelectedItem());
            } catch (Exception exc) {
            }
            //Parent loader = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            pracownicyTable.getScene().setRoot(root);
        } catch (IOException exc) {
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        conn = DBConnection.getConnection();
        Pracownik pracownik = new Pracownik();

        listPracownicy = pracownik.getAll(conn);

        //set up of columns
        setPracownicyTable(listPracownicy);
        new Wynagrodzenia().getRestricted(conn, 1);

        imieLogin.setAlignment(Pos.CENTER_RIGHT);
        nazwiskoLogin.setAlignment(Pos.CENTER_RIGHT);
        //setting size
        StageHelper.getStages().get(0).setHeight(517);
        StageHelper.getStages().get(0).setWidth(800);

        listWynagrodzenia = new Wynagrodzenia().getRestricted(conn, pracownicyTable.getItems().get(0).getPracownikID());

        setWynagrodzeniaTable(listWynagrodzenia);
        imieWynagrodzenia.setText(pracownicyTable.getItems().get(0).getPracownikImie());
        nazwiskoWynagordzenia.setText(pracownicyTable.getItems().get(0).getPracownikNazwisko());

        try {
            conn.close();
        } catch (SQLException exc) {
        }

        pracownikDataField.setVisible(false);
        pracownikKwotaField.setVisible(false);
        wynagrodzeniaAddButton1.setVisible(false);
        pracownikAnulateButton.setVisible(false);
    }

    private void setPracownicyTable(ObservableList<Pracownik> listPracownicy) {
        PropertyValueFactory<Pracownik, Integer> ID = new PropertyValueFactory<>("PracownikID");
        PropertyValueFactory<Pracownik, String> imie = new PropertyValueFactory<>("PracownikImie");
        PropertyValueFactory<Pracownik, String> nazwisko = new PropertyValueFactory<>("PracownikNazwisko");
        pracownikIDColumn.setCellValueFactory(ID);
        pracownikImieColumn.setCellValueFactory(imie);
        pracownikNazwiskoColumn.setCellValueFactory(nazwisko);
        pracownicyTable.setItems(listPracownicy);
    }

    @FXML
    public void wyangrodzenieAddButtonOnAction(ActionEvent action) throws Exception {
        wynagrodzeniaAddButton.setVisible(false);
        wynagrodzeniaDeleteButton.setVisible(false);
        pracownikDataField.setVisible(true);
        pracownikKwotaField.setVisible(true);
        wynagrodzeniaAddButton1.setVisible(true);
        pracownikAnulateButton.setVisible(true);

    }

    final static String DATE_FORMAT = "yyyy-mm-dd";

    public static boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @FXML
    public void wyangrodzenieAddButton1OnAction(ActionEvent action) throws Exception {

        conn = DBConnection.getConnection();

        String kwota = pracownikKwotaField.getText().trim();
        if (kwota.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Kwota\" nie może być puste");
            alert.showAndWait();
            return;
        }
        String data = pracownikDataField.getText().trim();

        if (data.length() == 0) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Data\" nie może być puste");
            alert.showAndWait();

        }

        boolean valid = isDateValid(data);
        if (!valid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Data\" musi mieć format YYYY-MM-DD");
            alert.showAndWait();
            return;
        }

        try {
            new Wynagrodzenia().insertWynagrodzenia(conn, pracownicyTable.getSelectionModel().getSelectedItem().getPracownikID(), Float.parseFloat(kwota), Date.valueOf(data));
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Błąd");
            alert.setContentText("Pole \"Kwota\" musi być liczbą");
            alert.showAndWait();
            return;
        }
        listWynagrodzenia = new Wynagrodzenia().getRestricted(conn, pracownicyTable.getSelectionModel().getSelectedItem().getPracownikID());
        setWynagrodzeniaTable(listWynagrodzenia);

        conn.close();

        wynagrodzeniaAddButton.setVisible(true);
        wynagrodzeniaDeleteButton.setVisible(true);
        pracownikDataField.setVisible(false);
        pracownikKwotaField.setVisible(false);
        wynagrodzeniaAddButton1.setVisible(false);
        pracownikAnulateButton.setVisible(false);

    }

    @FXML
    public void pracownikAnulateButtonOnAction(ActionEvent action) throws Exception {
        wynagrodzeniaAddButton.setVisible(true);
        wynagrodzeniaDeleteButton.setVisible(true);
        pracownikDataField.setVisible(false);
        pracownikKwotaField.setVisible(false);
        wynagrodzeniaAddButton1.setVisible(false);
        pracownikAnulateButton.setVisible(false);
    }

    public void wyangrodzenieDeleteButtonOnAction(ActionEvent action) throws Exception {

        Integer rowIndex = wynagrodzeniaTable.getSelectionModel().getSelectedIndex();

        if (rowIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błąd");
            alert.setContentText("Nie wybrano wynagrodzenia do usunięcia");
            alert.showAndWait();
            return;
        }

        Integer ID = pracownicyTable.getSelectionModel().getSelectedItem().getPracownikID();

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Potwierdzenie usunięcia");
        alert1.setContentText("Czy na pewno chcesz usunąć tę pozycję?");
        Optional<ButtonType> res = alert1.showAndWait();
        conn = DBConnection.getConnection();

        if (res.get() == ButtonType.OK) {

            Integer result = new Wynagrodzenia().deleteWynagrodzenia(conn, wynagrodzeniaTable.getSelectionModel().getSelectedItem().getWynagrodzenieID());
            if (result > 0) {

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Potwierdzenie usunięcia");
                alert2.setContentText("Pozycja została usunięta");
                alert2.showAndWait();

            }
        }

        listWynagrodzenia = new Wynagrodzenia().getRestricted(conn, ID);
        setWynagrodzeniaTable(listWynagrodzenia);
        conn.close();

    }

}
