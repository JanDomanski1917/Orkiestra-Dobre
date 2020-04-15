package com.example.orkiestra;

public class User {
    private String Imie;
    private String Tel;
    private String Haslo;
    private String PIN;

    public User() {
    }

    public User(String imie, String haslo, String pin) {
        Imie         = imie;
        Haslo        = haslo;
        this.PIN     = pin; }

    public String  getImie()                  {return Imie;}
    public void  setImie(String imie)         { Imie = imie;}

    public String getTel()                    {return Tel;}
    public void setTel(String telefon)        { Tel = telefon; }

    public String getHaslo()                  {return Haslo;}
    public void setHaslo (String haslo)       {Haslo = haslo;}

    public String getPIN()                    { return PIN; }
    public void setPIN(String secureCode)     { this.PIN = secureCode ; }
}