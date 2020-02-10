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
public class Klient {
     private Integer KlientID;
    private String KlientImie;
    private String KlientNazwisko;
    private String klientPESEL;
    private String KlientPlec;
    private String klientMiasto;
    private String KlientUlica;
    private String KlientNrDomu;
    private String klientNrMieszkania;
    private String KlientKodPocztowy;
    private String KlientAdresMail;
    

    public Integer getKlientID() {
        return KlientID;
    }

    public void setKlientID(Integer KlientID) {
        this.KlientID = KlientID;
    }

    public String getKlientImie() {
        return KlientImie;
    }

    public void setKlientImie(String KlientImie) {
        this.KlientImie = KlientImie;
    }

    public String getKlientNazwisko() {
        return KlientNazwisko;
    }

    public void setKlientNazwisko(String KlientNazwisko) {
        this.KlientNazwisko = KlientNazwisko;
    }

    public String getKlientPESEL() {
        return klientPESEL;
    }

    public void setKlientPESEL(String klientPESEL) {
        this.klientPESEL = klientPESEL;
    }

    public String getKlientPlec() {
        return KlientPlec;
    }

    public void setKlientPlec(String KlientPlec) {
        this.KlientPlec = KlientPlec;
    }


    public String getKlientMiasto() {
        return klientMiasto;
    }

    public void setKlientMiasto(String klientMiasto) {
        this.klientMiasto = klientMiasto;
    }

    public String getKlientUlica() {
        return KlientUlica;
    }

    public void setKlientUlica(String KlientUlica) {
        this.KlientUlica = KlientUlica;
    }

    public String getKlientNrDomu() {
        return KlientNrDomu;
    }

    public void setKlientNrDomu(String KlientNrDomu) {
        this.KlientNrDomu = KlientNrDomu;
    }

    public String getKlientNrMieszkania() {
        return klientNrMieszkania;
    }

    public void setKlientNrMieszkania(String klientNrMieszkania) {
        this.klientNrMieszkania = klientNrMieszkania;
    }

    public String getKlientKodPocztowy() {
        return KlientKodPocztowy;
    }

    public void setKlientKodPocztowy(String KlientKodPocztowy) {
        this.KlientKodPocztowy = KlientKodPocztowy;
    }

    public String getKlientAdresMail() {
        return KlientAdresMail;
    }

    public void setKlientAdresMail(String KlientAdresMail) {
        this.KlientAdresMail = KlientAdresMail;
    }


    public void initKlient(Connection conn, String imie, String nazwisko) {
        ObservableList<Klient> listPracownicy = FXCollections.observableArrayList();
        String sql = "SELECT id_klienta,imie,nazwisko, PESEL, Plec, Miasto, kod_pocztowy, ulica, numer_domu, numer_mieszkania, email from klienci where imie = ? AND nazwisko = ?";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            rs = stmt.executeQuery();
            while (rs.next()) {

                this.KlientID = rs.getInt(1);
                this.KlientImie = rs.getString(2);
                this.KlientNazwisko = rs.getString(3);
                this.klientPESEL = rs.getString(4);
                this.KlientPlec = rs.getString(5);
                this.klientMiasto = rs.getString(6);
                this.KlientKodPocztowy = rs.getString(7);
                this.KlientUlica = rs.getString(8);
                this.KlientNrDomu = rs.getString(9);
                this.klientNrMieszkania = rs.getString(10);
                this.KlientAdresMail = rs.getString(11);

            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errors with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
    }

    public int insertKlient(Connection conn, String imie, String nazwisko, String pesel, String plec,  String miasto, String kodPocztowy, String ulica, String nrDomu ,String nrMieszkania, String adresMail) {
        String sql = "INSERT INTO klienci (id_klienta, imie, nazwisko, pesel, plec, narodowosc, miasto, kod_pocztowy, ulica, numer_domu, numer_mieszkania, nr_telefonu, email, id_salonu) VALUES (klient_id_seq.nextval, ?, ?, ?, ?, 'Polska', ?, ?, ?, ?,  ?, '777456222', ?, 1)";
        PreparedStatement stmt;
        Integer rs = null;
        
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            stmt.setString(3, pesel);
            stmt.setString(4, plec);
            stmt.setString(6, miasto);
            stmt.setString(7, kodPocztowy);
            stmt.setString(8, ulica);
            stmt.setString(9, nrDomu);
            stmt.setString(10, nrMieszkania);
            stmt.setString(11, adresMail);

            rs = stmt.executeUpdate();

        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errors with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return rs;
    }

    public int removeKlient(Connection conn, Integer ID) {
        String sql = "DELETE FROM klienci where id_klienta =?";
        PreparedStatement stmt;
        Integer rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ID);
            rs = stmt.executeUpdate();

        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Problem z usunięciem klienta");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return rs;
    }

    public int updateKlient(Connection conn, Integer ID, String imie, String nazwisko, String pesel, String plec,String miasto, String kodPocztowy, String ulica, String nrDomu ,String nrMieszkania, String adresMail) {
        String sql = "UPDATE klienci set imie = ?, nazwisko = ?, pesel = ?, plec = ?,  miasto = ?, kod_pocztowy = ?, ulica = ?, numer_domu = ?, numer_mieszkania = ?, email = ? where id_klienta = ?";
        PreparedStatement stmt;

        Integer rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            stmt.setInt(3, ID);
            stmt.setString(3, pesel);
            stmt.setString(4, plec);
            stmt.setString(5, miasto);
            stmt.setString(6, kodPocztowy);
            stmt.setString(7, ulica);
            stmt.setString(8, nrDomu);
            stmt.setString(9, nrMieszkania);
            stmt.setString(10, adresMail);
            stmt.setInt(11, ID);
            
            rs = stmt.executeUpdate();

        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Problem z modyfikacją danych klienta");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return rs;

    }

    public Integer checkKlient(Connection conn, String imie, String nazwisko) {
        String sql = "SELECT id_klienta from klienci where imie = ? AND nazwisko = ?";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errors with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return -1;
    }
}
