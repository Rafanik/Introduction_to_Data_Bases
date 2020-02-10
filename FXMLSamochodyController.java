/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbd_salonsamochodowy;

import com.sun.javafx.stage.StageHelper;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Rafal
 */
public class FXMLSamochodyController {

    @FXML
    private AnchorPane pracownicyAnchorPane;

    @FXML
    private TableView<Samochody> samochodyTable;

    @FXML
    private TableColumn<Samochody, String> samochodMarkaColumn;

    @FXML
    private TableColumn<Samochody, String> samochodModelColumn;

    @FXML
    private TableColumn<Samochody, String> samochodTypNadwoziaColumn;

    @FXML
    private TableColumn<Samochody, Float> samochodPojemnoscColumn;

    @FXML
    private TableColumn<Samochody, Integer> samochodCenaBazowaColumn;

    @FXML
    private TextField samochodModelSearchField;

    @FXML
    private Button samochodSearchButton;

    @FXML
    private Label imieLogin;

    @FXML
    private Label nazwiskoLogin;

    @FXML
    private Button samochodWylogujButton;

    @FXML
    private TextField samochodMarkaSearchField;

    @FXML
    private Button samochodPowrotButton;

    @FXML
    private Label imiePowitanie;

    private ObservableList<Samochody> listSamochody;

    private Connection conn;

    public void initController(String imie, String nazwisko) {
        conn = DBConnection.getConnection();
        imieLogin.setText(nazwisko);
        nazwiskoLogin.setText(imie);
        imiePowitanie.setText(" "+nazwisko+"!");
        imieLogin.setAlignment(Pos.CENTER_RIGHT);
        nazwiskoLogin.setAlignment(Pos.CENTER_RIGHT);
        listSamochody = new Samochody().getAll(conn);
        setSamochodyTable(listSamochody);
        StageHelper.getStages().get(0).setHeight(555);
        StageHelper.getStages().get(0).setWidth(930);
    }

    @FXML
    public void klientWylogujButtonOnAction(ActionEvent action) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLZaloguj.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("FXMLZaloguj.fxml"));
        Scene sce;
        sce = imieLogin.getScene();

        sce.setRoot(root);

        StageHelper.getStages().get(0).setHeight(300);
        StageHelper.getStages().get(0).setWidth(400);
    }

    @FXML
    public void klientPowrotButtonOnAction(ActionEvent action) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLZaloguj.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLKlient.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        FXMLKlientController controller = fxmlLoader.<FXMLKlientController>getController();
        controller.setZalogowanyKlient(imieLogin.getText(), nazwiskoLogin.getText());

        //Parent loader = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        samochodModelSearchField.getScene().setRoot(root);
    }

    private void setSamochodyTable(ObservableList<Samochody> listSamochody) {

        samochodMarkaColumn.setCellValueFactory(new PropertyValueFactory<>("marka"));
        samochodModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        samochodTypNadwoziaColumn.setCellValueFactory(new PropertyValueFactory<>("typNadwozia"));
        samochodPojemnoscColumn.setCellValueFactory(new PropertyValueFactory<>("pojemnosc"));
        samochodCenaBazowaColumn.setCellValueFactory(new PropertyValueFactory<>("cenaBazowa"));
        samochodyTable.setItems(listSamochody);

    }

    @FXML
    public void samochodySearchButtonOnAction(ActionEvent action) throws IOException, SQLException {
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLZaloguj.fxml"));
        conn = DBConnection.getConnection();
        String marka = samochodMarkaSearchField.getText();
        String model = samochodModelSearchField.getText();
        if (model.length() == 0) {
            model = "%";
        }
        if (marka.length() == 0) {
            marka = "%";
        }
        listSamochody = new Samochody().getRestricted(conn, marka, model);

        setSamochodyTable(listSamochody);
        conn.close();
    }

}
