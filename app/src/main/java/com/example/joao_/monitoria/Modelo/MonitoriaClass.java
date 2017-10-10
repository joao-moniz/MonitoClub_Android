package com.example.joao_.monitoria.Modelo;

import java.util.ArrayList;

/**
 * Created by joao- on 21/06/2017.
 */

public class MonitoriaClass {
    private String id;
    private String monitorId;
    private String Salas;
    private String diaSemana;
    private String inicio;
    private String fim;


    public MonitoriaClass() {
    }

    public MonitoriaClass(String id, String monitorId, String salas, String diaSemana, String inicio, String fim) {
        this.id = id;
        this.monitorId = monitorId;
        this.Salas = salas;
        this.diaSemana = diaSemana;
        this.inicio = inicio;
        this.fim = fim;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalas() {
        return Salas;
    }

    public void setSalas(String salas) {
        Salas = salas;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

}
