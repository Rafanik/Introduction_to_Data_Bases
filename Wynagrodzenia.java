/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbd_salonsamochodowy;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author Rafal
 */
public class Wynagrodzenia {
    private Integer wynagrodzenieID;
    private Float wynagrodzenieKwota;
    private Date wynagrodzeniaData;
    private Integer wynagrodzeniaPracownikID;

    public Integer getWynagrodzenieID() {
        return wynagrodzenieID;
    }

    public void setWynagrodzenieID(Integer wynagrodzenieID) {
        this.wynagrodzenieID = wynagrodzenieID;
    }

    public Float getWynagrodzenieKwota() {
        return wynagrodzenieKwota;
    }

    public void setWynagrodzenieKwota(Float wynagrodzenieKwota) {
        this.wynagrodzenieKwota = wynagrodzenieKwota;
    }

    public Date getWynagrodzeniaData() {
        return wynagrodzeniaData;
    }

    public void setWynagrodzeniaData(Date wynagrodzeniaData) {
        this.wynagrodzeniaData = wynagrodzeniaData;
    }



    public Integer getWynagrodzeniaPracownikID() {
        return wynagrodzeniaPracownikID;
    }

    public void setWynagrodzeniaPracownikID(Integer wynagrodzeniaPracownikID) {
        this.wynagrodzeniaPracownikID = wynagrodzeniaPracownikID;
    }

    public ObservableList<Wynagrodzenia> getRestricted(Connection conn, Integer ID) {
        ObservableList<Wynagrodzenia> listWynagrodzenia = FXCollections.observableArrayList();
        String sql = "SELECT id_wynagrodzenia,data_wyplaty,kwota from wynagrodzenia where id_pracownika = ? ORDER BY data_wyplaty DESC";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Wynagrodzenia wynagrodzenia = new Wynagrodzenia();
                wynagrodzenia.wynagrodzenieID = rs.getInt(1);
                wynagrodzenia.wynagrodzeniaData= rs.getDate(2);
                wynagrodzenia.wynagrodzenieKwota = rs.getFloat(3);
                
                listWynagrodzenia.add(wynagrodzenia);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errors with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return listWynagrodzenia;
    }

    public int insertWynagrodzenia(Connection conn, Integer ID, Float kwota, Date data) {
        String sql = "INSERT INTO wynagrodzenia (id_wynagrodzenia, kwota, data_wyplaty, id_pracownika) VALUES (wynagrodzenia_id_seq.nextval, ?, ?, ?)";
        PreparedStatement stmt;
        Integer rs = null;
        
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setFloat(1, kwota);
            stmt.setDate(2, data);
            stmt.setInt(3, ID);


            rs = stmt.executeUpdate();

        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errors with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return rs;
    }
    
    public int deleteWynagrodzenia(Connection conn, Integer ID) {
        String sql = "DELETE FROM wynagrodzenia where id_wynagrodzenia = ?";
        PreparedStatement stmt;
        Integer rs = null;
        
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ID);


            rs = stmt.executeUpdate();

        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errors with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return rs;
    }
}
