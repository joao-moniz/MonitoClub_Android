package com.example.joao_.monitoria.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by joao- on 21/06/2017.
 */

public class Materia implements Serializable {
    private String id;
    private String descricao;
    private String departamentoId;
    private ArrayList<String> monitoresId;

    public Materia(String id, String descricao, String departamentoId, ArrayList<String> monitoresId) {
        this.id = id;
        this.descricao = descricao;
        this.departamentoId = departamentoId;
        this.monitoresId = monitoresId;
    }

    public Materia() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(String departamentoId) {
        this.departamentoId = departamentoId;
    }

    public ArrayList<String> getMonitoresId() {
        return monitoresId;
    }

    public void setMonitoresId(ArrayList<String> monitoresId) {
        this.monitoresId = monitoresId;
    }
}
