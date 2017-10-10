package com.example.joao_.monitoria.Banco_De_Dados;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.joao_.monitoria.Aplication.Monitoria;
import com.example.joao_.monitoria.Modelo.ModeloBanco;
import com.example.joao_.monitoria.Modelo.Monitor;
import com.example.joao_.monitoria.Modelo.MonitoriaClass;
import com.example.joao_.monitoria.Modelo.Usuario;

import java.util.ArrayList;

/**
 * Created by joao- on 05/07/2017.
 */

public class UsuarioDB {
    private SQLiteDatabase db;
    private BD_Helper banco;

    public UsuarioDB(Context context){
        banco = new BD_Helper(context);
    }

    public boolean InserirUsuario (Usuario user){
        ContentValues valores;
        long resultado;
        valores = new ContentValues();

        valores.put(banco.Id_Usuario,user.getId());
        valores.put(banco.Nome_Usuario,user.getNome());
        valores.put(banco.Email_Usuario,user.getEmail());
        valores.put(banco.PhotoURL_Usuario,user.getPhotoURL());
        valores.put(banco.UnidadeAcademicaId_Usuario,user.getUnidade_Academica_Id());
        valores.put(banco.UniversidadeId_Usuario,user.getUniversidade_Id());
        valores.put(banco.Tipo_Usuario,user.getTipo());

        db = banco.getWritableDatabase();
        resultado = db.insert(banco.Tabela_Usuario,null,valores);
        db.close();
        if(resultado == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean InserirMonitoriasFavoritas (Monitor monitor){
        ContentValues valores;
        long resultado;
        valores = new ContentValues();

        valores.put(banco.Id_MonitoresFavoritos,monitor.getId());
        valores.put(banco.Nome_MonitoresFavoritos,monitor.getName());
        valores.put(banco.Email_MonitoresFavoritos,monitor.getEmail());
        valores.put(banco.MateriaId_MonitoresFavoritos,monitor.getMateriaId());

        db = banco.getWritableDatabase();
        resultado = db.insert(banco.Tabela_MonitoresFavoritos  ,null,valores);
        db.close();
        if(resultado == -1){
            return false;
        }
        else {
            return true;
        }
    }
}
