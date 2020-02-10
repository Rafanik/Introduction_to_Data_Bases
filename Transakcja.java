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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author Rafal
 */
public class Transakcja {
    private Integer transakcjaID;
    private Date data;
    private Float cena;
    private String marka;
    private String model;

    public Integer getTransakcjaID() {
        return transakcjaID;
    }

    public void setTransakcjaID(Integer transakcjaID) {
        this.transakcjaID = transakcjaID;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Float getCena() {
        return cena;
    }

    public void setCena(Float cena) {
        this.cena = cena;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    
    public ObservableList<Transakcja> getRestricted(Connection conn, Integer ID) {
        ObservableList<Transakcja> listTransakcje = FXCollections.observableArrayList();
        String sql = "SELECT nazwa, nazwa_modelu, cena, data\n" +
"from transakcje t join samochody s using(id_samochodu) join modele m using(id_model) join marki ma using(id_marki) where id_klienta = ?";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Transakcja transakcja = new Transakcja();
                transakcja.marka = rs.getString(1);
                transakcja.model = rs.getString(2);
                transakcja.cena = rs.getFloat(3);
                transakcja.data = rs.getDate(4);

                listTransakcje.add(transakcja);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errors with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return listTransakcje;
    }
    
}
