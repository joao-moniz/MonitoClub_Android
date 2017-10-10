package com.example.joao_.monitoria.Banco_De_Dados;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.joao_.monitoria.Modelo.ModeloBanco;

import java.util.ArrayList;

/**
 * Created by joao- on 02/06/2017.
 */



public class BD_Helper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "monitoria.db";

    public static final String Tabela_Usuario = "usuario_login";
    public static final String Id_Usuario = "id_usuario";
    public static final String Nome_Usuario = "nome_usuario";
    public static final String Email_Usuario = "email_usuario";
    public static final String PhotoURL_Usuario = "photoURL_usuario";
    public static final String UniversidadeId_Usuario = "universidade_id_usuario";
    public static final String UnidadeAcademicaId_Usuario = "unidade_academica_id_usuario";
    public static final String Tipo_Usuario = "tipo_usuario";

    public static final String Tabela_Monitores = "monitores";
    public static final String Id_Monitor = "id_monitor";
    public static final String Nome_Monitor = "nome_monitor";
    public static final String Email_Monitor = "email_monitor";
    public static final String MateriaId_Monitor = "materia_id_monitor";

    public static final String Tabela_MonitoresFavoritos = "monitores_favoritos";
    public static final String Id_MonitoresFavoritos = "id_monitores_favoritor";
    public static final String Nome_MonitoresFavoritos = "nome_monitores_favoritor";
    public static final String Email_MonitoresFavoritos = "email_monitores_favoritor";
    public static final String MateriaId_MonitoresFavoritos = "materia_id_monitores_favoritor";





    public static final String MonitoriasFavoritas_id = "monitoriasFavoritas_usuario";

    public BD_Helper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryUsuario = "create table usuario_login ( id_monitores_favoritor varchar(45) PRIMARY KEY, nome_usuario varchar(45) ," +
                " email_usuario varchar(45), photoURL_usuario varchar(45), universidade_id_usuario varchar(45) ," +
                " unidade_academica_id_usuario varchar(45), tipo_usuario int(11) );";
        db.execSQL(queryUsuario);

        String queryMonitoresFavoritos  = "create table monitores_favoritos( id_monitor varchar(45) PRIMARY KEY , nome_monitores_favoritor varchar(45) ," +
                " email_monitores_favoritor varchar(45), materia_id_monitores_favoritor varchar(45) );";
        db.execSQL(queryMonitoresFavoritos);

        String queryCursos  = "create table cursos( id_curso varchar(45) PRIMARY KEY , descricao_curso varchar(45) ," +
                " universidadeId_curso varchar(45) );";
        db.execSQL(queryCursos);

        String queryMonitores  = "create table monitores( id_monitor varchar(45) PRIMARY KEY , nome_monitor varchar(45) ," +
                " email_monitor varchar(45), materia_id_monitor varchar(45) );";
        db.execSQL(queryMonitores);

        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario_login ;");
        db.execSQL("DROP TABLE IF EXISTS monitores_favoritos ;");
        db.execSQL("DROP TABLE IF EXISTS cursos ;");
        db.execSQL("DROP TABLE IF EXISTS monitores ;");
    }


    public static ArrayList<ModeloBanco> DadosTabelaMonitoriasFavoritas(){
        ArrayList<ModeloBanco> DadosTabela = new ArrayList<>();

        DadosTabela.add(new ModeloBanco("Tabela","monitorias_favoritas"));
        DadosTabela.add(new ModeloBanco("MONITORIAS_FAVORITAS","monitorias_favoritas_usuario"));

        return  DadosTabela;
    }

    public static ArrayList<ModeloBanco> DadosTabelaCursps(){
        ArrayList<ModeloBanco> DadosTabela = new ArrayList<>();

        DadosTabela.add(new ModeloBanco("Tabela","cursos"));
        DadosTabela.add(new ModeloBanco("ID","id_curso"));
        DadosTabela.add(new ModeloBanco("Descrição","descricao_curso"));
        DadosTabela.add(new ModeloBanco("Universidae","universidadeId_curso"));

        return DadosTabela;
    }

}
