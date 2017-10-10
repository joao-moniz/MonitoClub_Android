package com.example.joao_.monitoria.Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by joao- on 21/06/2017.
 */

public class Monitor implements Serializable{
    private String id;
    private String name;
    private String email;
    private String materiaId;
    private ArrayList<MonitoriaClass> monitorias;

    public Monitor() {

    }

    public Monitor(String name, String email, String id, String materiaId, ArrayList<MonitoriaClass> monitorias) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.materiaId = materiaId;
        this.monitorias = monitorias;
    }

    public ArrayList<MonitoriaClass> getMonitorias() {
        return monitorias;
    }

    public void setMonitorias(ArrayList<MonitoriaClass> monitorias) {
        this.monitorias = monitorias;
    }

    public String getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(String materiaId) {
        this.materiaId = materiaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
