package com.adiguzel.anil.smartwage;

/**
 * Created by dell on 04.02.2018.
 */

public class liste {

    private String thema;
    private String Nachricht;
    private String Datum;
    private String id;

    public liste(String thema, String Nachricht , String Datum , String id) {
        this.thema = thema;
        this.Nachricht = Nachricht;
        this.Datum=Datum;
        this.id = id;

    }


    public String getThema() {
        return thema;
    }

    public String getNachricht() {
        return Nachricht;
    }

    public String getDatum() {
        return Datum;
    }

    public String getId() {
        return id;
    }





}
