/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbd_salonsamochodowy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author Rafal
 */
public class Samochody {

    private Integer samochodyID;
    private String model;
    private String marka;
    private String typNadwozia;
    private Float pojemnosc;
    private Integer cenaBazowa;

    public Integer getSamochodyID() {
        return samochodyID;
    }

    public void setSamochodyID(Integer samochodyID) {
        this.samochodyID = samochodyID;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getTypNadwozia() {
        return typNadwozia;
    }

    public void setTypNadwozia(String typNadwozia) {
        this.typNadwozia = typNadwozia;
    }

    public Float getPojemnosc() {
        return pojemnosc;
    }

    public void setPojemnosc(Float pojemnosc) {
        this.pojemnosc = pojemnosc;
    }

    public Integer getCenaBazowa() {
        return cenaBazowa;
    }

    public void setCenaBazowa(Integer cenaBazowa) {
        this.cenaBazowa = cenaBazowa;
    }

    public ObservableList<Samochody> getAll(Connection conn) {
        ObservableList<Samochody> listSamochody = FXCollections.observableArrayList();
        String sql = "SELECT ma.nazwa, mo.nazwa_modelu, s.typ_nadwozia, s.pojemnosc, s.cena_bazowa from samochody s \n"
                + "    join modele mo using(id_model) \n"
                + "    join marki ma using(id_marki)";

        Statement stmt;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Samochody samochody = new Samochody();
                samochody.marka = rs.getString(1);
                samochody.model = rs.getString(2);
                samochody.typNadwozia = rs.getString(3);
                samochody.pojemnosc = rs.getFloat(4);
                samochody.cenaBazowa = rs.getInt(5);

                listSamochody.add(samochody);
                ;
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errors with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return listSamochody;
    }

    public ObservableList<Samochody> getRestricted(Connection conn, String marka, String model) {

        String sql = "SELECT ma.nazwa, mo.nazwa_modelu, s.typ_nadwozia, s.pojemnosc, s.cena_bazowa from samochody s \n"
                + "    join modele mo using(id_model) \n"
                + "    join marki ma using(id_marki) where (ma.nazwa like ? AND mo.nazwa_modelu like ?)";

        ObservableList<Samochody> listSamochody = FXCollections.observableArrayList();

        PreparedStatement stmt;
        ResultSet rs;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, marka + "%");
            stmt.setString(2, model +"%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Samochody samochody = new Samochody();
                samochody.marka = rs.getString(1);
                samochody.model = rs.getString(2);
                samochody.typNadwozia = rs.getString(3);
                samochody.pojemnosc = rs.getFloat(4);
                samochody.cenaBazowa = rs.getInt(5);

                listSamochody.add(samochody);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errors with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return listSamochody;
    }

}
