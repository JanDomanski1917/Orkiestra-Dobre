package com.example.orkiestra;

public class Member {
    private String Imie;
    private String Nazwisko;
    private String Telefon;
    private String Glos;

    public Member() {
    }

    public Member(String imie, String nazwisko, String telefon, String glos) {
        Imie = imie;
        Nazwisko = nazwisko;
        Telefon = telefon;
        Glos = glos;
    }

    public String getImie() {
        return Imie;
    }

    public void setImie(String imie) {
        Imie = imie;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        Nazwisko = nazwisko;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    public String getGlos() {
        return Glos;
    }

    public void setGlos(String glos) {
        Glos = glos;
    }


    @Override
    public String toString() {
        return Imie + " " + Nazwisko + " " + Telefon + " " + Glos;
    }
}