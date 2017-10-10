package com.example.joao_.monitoria.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by joao- on 03/06/2017.
 */

public class Universidade implements Serializable{
    private String id;
    private String name;
    private String imagem_url;
    private ArrayList<String>  unidades_academicas_id;

    public Universidade(String id, String name, String imagem_url, ArrayList<String> unidades_academicas_id) {
        this.id = id;
        this.name = name;
        this.imagem_url = imagem_url;
        this.unidades_academicas_id = unidades_academicas_id;
    }

    public Universidade() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagem_url() {
        return imagem_url;
    }

    public void setImagem_url(String imagem_id) {
        this.imagem_url = imagem_id;
    }

    public ArrayList<String> getUnidades_academicas_id() {
        return unidades_academicas_id;
    }

    public void setUnidades_academicas_id(ArrayList<String> unidades_academicas_id) {
        this.unidades_academicas_id = unidades_academicas_id;
    }
}
