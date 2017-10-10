package com.example.joao_.monitoria.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by joao- on 06/06/2017.
 */

public class Usuario implements Serializable {
    private String id;
    private String nome;
    private String email;
    private String photoURL;
    private ArrayList<String> MonitoriasFavoritas_id;
    private String Universidade_Id;
    private String Unidade_Academica_Id;
    private int tipo;

    public Usuario() {
    }

    public Usuario(String id, String nome, String email, String photoURL, ArrayList<String> monitoriasFavoritas_id, String universidade_Id, String unidade_Academica_Id, int tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.photoURL = photoURL;
        MonitoriasFavoritas_id = monitoriasFavoritas_id;
        Universidade_Id = universidade_Id;
        Unidade_Academica_Id = unidade_Academica_Id;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public ArrayList<String> getMonitoriasFavoritas_id() {
        return MonitoriasFavoritas_id;
    }

    public void setMonitoriasFavoritas_id(ArrayList<String> monitoriasFavoritas_id) {
        MonitoriasFavoritas_id = monitoriasFavoritas_id;
    }

    public String getUniversidade_Id() {
        return Universidade_Id;
    }

    public void setUniversidade_Id(String universidade_Id) {
        Universidade_Id = universidade_Id;
    }

    public String getUnidade_Academica_Id() {
        return Unidade_Academica_Id;
    }

    public void setUnidade_Academica_Id(String unidade_Academica_Id) {
        Unidade_Academica_Id = unidade_Academica_Id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
