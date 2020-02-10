/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbd_salonsamochodowy;

import java.sql.Connection;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author Rafal
 */
public class Pracownik {

    private Integer PracownikID;
    private String PracownikImie;
    private String PracownikNazwisko;
    private String pracownikPESEL;
    private String PracownikPlec;
    private String PracownikNrKonta;
    private String pracownikMiasto;
    private String PracownikUlica;
    private String PracownikNrDomu;
    private String pracownikNrMieszkania;
    private String PracownikKodPocztowy;
    private String PracownikAdresMail;
    

    public Integer getPracownikID() {
        return PracownikID;
    }

    public void setPracownikID(Integer PracownikID) {
        this.PracownikID = PracownikID;
    }

    public String getPracownikImie() {
        return PracownikImie;
    }

    public void setPracownikImie(String PracownikImie) {
        this.PracownikImie = PracownikImie;
    }

    public String getPracownikNazwisko() {
        return PracownikNazwisko;
    }

    public void setPracownikNazwisko(String PracownikNazwisko) {
        this.PracownikNazwisko = PracownikNazwisko;
    }

    public String getPracownikPESEL() {
        return pracownikPESEL;
    }

    public void setPracownikPESEL(String pracownikPESEL) {
        this.pracownikPESEL = pracownikPESEL;
    }

    public String getPracownikPlec() {
        return PracownikPlec;
    }

    public void setPracownikPlec(String PracownikPlec) {
        this.PracownikPlec = PracownikPlec;
    }

    public String getPracownikNrKonta() {
        return PracownikNrKonta;
    }

    public void setPracownikNrKonta(String PracownikNrKonta) {
        this.PracownikNrKonta = PracownikNrKonta;
    }

    public String getPracownikMiasto() {
        return pracownikMiasto;
    }

    public void setPracownikMiasto(String pracownikMiasto) {
        this.pracownikMiasto = pracownikMiasto;
    }

    public String getPracownikUlica() {
        return PracownikUlica;
    }

    public void setPracownikUlica(String PracownikUlica) {
        this.PracownikUlica = PracownikUlica;
    }

    public String getPracownikNrDomu() {
        return PracownikNrDomu;
    }

    public void setPracownikNrDomu(String PracownikNrDomu) {
        this.PracownikNrDomu = PracownikNrDomu;
    }

    public String getPracownikNrMieszkania() {
        return pracownikNrMieszkania;
    }

    public void setPracownikNrMieszkania(String pracownikNrMieszkania) {
        this.pracownikNrMieszkania = pracownikNrMieszkania;
    }

    public String getPracownikKodPocztowy() {
        return PracownikKodPocztowy;
    }

    public void setPracownikKodPocztowy(String PracownikKodPocztowy) {
        this.PracownikKodPocztowy = PracownikKodPocztowy;
    }

    public String getPracownikAdresMail() {
        return PracownikAdresMail;
    }

    public void setPracownikAdresMail(String PracownikAdresMail) {
        this.PracownikAdresMail = PracownikAdresMail;
    }

    public ObservableList<Pracownik> getAll(Connection conn) {
        ObservableList<Pracownik> listPracownicy = FXCollections.observableArrayList();
        String sql = "SELECT id_pracownika,imie,nazwisko, pesel, plec, nr_konta, miasto, kod_pocztowy, ulica, numer_domu, numer_mieszkania, email from pracownicy order by id_pracownika asc";
        // id_pracownika, imie, nazwisko, pesel, plec, narodowosc, nr_konta, miasto, kod_pocztowy, ulica, numer_domu, numer_mieszkania, nr_telefonu, email, id_salonu
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Pracownik pracownik = new Pracownik();
                pracownik.PracownikID = rs.getInt(1);
                pracownik.PracownikImie = rs.getString(2);
                pracownik.PracownikNazwisko = rs.getString(3);
                pracownik.pracownikPESEL = rs.getString(4);
                pracownik.PracownikPlec = rs.getString(5);
                pracownik.PracownikNrKonta = rs.getString(6);
                pracownik.pracownikMiasto = rs.getString(7);
                pracownik.PracownikKodPocztowy = rs.getString(8);
                pracownik.PracownikUlica = rs.getString(9);
                pracownik.PracownikNrDomu = rs.getString(10);
                pracownik.pracownikNrMieszkania = rs.getString(11);
                pracownik.PracownikAdresMail = rs.getString(12);
                
                listPracownicy.add(pracownik);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errors with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return listPracownicy;
    }

    public ObservableList<Pracownik> getRestricted(Connection conn, String nazwisko) {
        ObservableList<Pracownik> listPracownicy = FXCollections.observableArrayList();
        String sql = "SELECT id_pracownika,imie,nazwisko from pracownicy where upper(nazwisko) like ? order by id_pracownika";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nazwisko.toUpperCase() + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Pracownik pracownik = new Pracownik();
                pracownik.PracownikID = rs.getInt(1);
                pracownik.PracownikImie = rs.getString(2);
                pracownik.PracownikNazwisko = rs.getString(3);


                listPracownicy.add(pracownik);
            }
        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errors with data access");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return listPracownicy;
    }

    public int insertPracownik(Connection conn, String imie, String nazwisko, String pesel, String plec, String nrKonta, String miasto, String kodPocztowy, String ulica, String nrDomu ,String nrMieszkania, String adresMail) {
        String sql = "INSERT INTO pracownicy (id_pracownika, imie, nazwisko, pesel, plec, narodowosc, nr_konta, miasto, kod_pocztowy, ulica, numer_domu, numer_mieszkania, nr_telefonu, email, id_salonu) VALUES (pracownik_id_seq.nextval, ?, ?, ?, ?, 'Polska', ?, ?, ?, ?, ?, ?, '777456222', ?, 1)";
        PreparedStatement stmt;
        Integer rs = null;
        
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            stmt.setString(3, pesel);
            stmt.setString(4, plec);
            stmt.setString(5, nrKonta);
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

    public int removePracownik(Connection conn, Integer ID) {
        String sql = "DELETE FROM wynagrodzenia where id_pracownika = ? ";//DELETE FROM pracownicy where id_pracownika =?";
        PreparedStatement stmt;
        Integer rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ID);
            rs = stmt.executeUpdate();

        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Problem z usunięciem pracownika");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        
        sql = "DELETE FROM pracownicy where id_pracownika =?";
        
        rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ID);
            rs = stmt.executeUpdate();

        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Problem z usunięciem pracownika");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return rs;
    }

    public int updatePracownik(Connection conn, Integer ID, String imie, String nazwisko, String pesel, String plec, String nrKonta, String miasto, String kodPocztowy, String ulica, String nrDomu ,String nrMieszkania, String adresMail) {
        String sql = "UPDATE pracownicy set imie = ?, nazwisko = ?, pesel = ?, plec = ?, nr_konta = ?, miasto = ?, kod_pocztowy = ?, ulica = ?, numer_domu = ?, numer_mieszkania = ?, email = ? where id_pracownika = ?";
        PreparedStatement stmt;

        Integer rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            stmt.setInt(3, ID);
            stmt.setString(3, pesel);
            stmt.setString(4, plec);
            stmt.setString(5, nrKonta);
            stmt.setString(6, miasto);
            stmt.setString(7, kodPocztowy);
            stmt.setString(8, ulica);
            stmt.setString(9, nrDomu);
            stmt.setString(10, nrMieszkania);
            stmt.setString(11, adresMail);
            stmt.setInt(12, ID);
            
            rs = stmt.executeUpdate();

        } catch (SQLException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Problem z modyfikacją danych pracownika");
            alert.setContentText("Details: " + exc.getMessage());
            alert.showAndWait();
        }
        return rs;

    }

    public Integer checkPracownik(Connection conn, String imie, String nazwisko) {
        ObservableList<Pracownik> listPracownicy = FXCollections.observableArrayList();
        String sql = "SELECT id_pracownika from pracownicy where imie = ? AND nazwisko = ?";
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
