package com.example.joao_.monitoria.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by joao- on 31/05/2017.
 */

public class Departamento implements Serializable{
    private String Id;
    private String Name;
    private String IconeUrl;
    private String Unidade_academica_id;
    private ArrayList<String> Materias;

    public Departamento() {
    }

    public Departamento(String id, String name, String iconeUrl, String unidades_academicas_id, ArrayList<String> materias) {
        Id = id;
        Name = name;
        IconeUrl = iconeUrl;
        Unidade_academica_id = unidades_academicas_id;
        Materias = materias;
    }

    public String getUnidades_academicas_id() {
        return Unidade_academica_id;
    }

    public void setUnidades_academicas_id(String unidades_academicas_id) {
        Unidade_academica_id = unidades_academicas_id;
    }

    public String getIconeUrl() {
        return IconeUrl;
    }

    public void setIconeUrl(String iconeUrl) {
        IconeUrl = iconeUrl;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<String> getMaterias() {
        return Materias;
    }

    public void setMaterias(ArrayList<String> materias) {
        Materias = materias;
    }
}
