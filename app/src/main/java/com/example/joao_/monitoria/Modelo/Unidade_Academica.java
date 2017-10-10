package com.example.joao_.monitoria.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by joao- on 03/06/2017.
 */

public class Unidade_Academica  implements Serializable{
    private String id;
    private String name;
    private String localidade;
    private String UniversidadeId;
    private ArrayList<String> DepartamentosId;

    public Unidade_Academica() {
    }

    public Unidade_Academica(String id, String name, String localidade, String universidadeId, ArrayList<String> cursos_id) {
        this.id = id;
        this.name = name;
        this.localidade = localidade;
        this.UniversidadeId = universidadeId;
        this.DepartamentosId = cursos_id;
    }

    public String getUniversidadeId() {
        return UniversidadeId;
    }

    public void setUniversidadeId(String universidadeId) {
        UniversidadeId = universidadeId;
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

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public ArrayList<String> getDepartamentosId() {
        return DepartamentosId;
    }

    public void setDepartamentosId(ArrayList<String> departamentosId) {
        this.DepartamentosId = departamentosId;
    }
}
