package com.example.joao_.monitoria.Fragment;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.joao_.monitoria.Activitys.Activity_Cadastro;
import com.example.joao_.monitoria.Activitys.MainActivity;
import com.example.joao_.monitoria.Adapter.Adapter_ListView_Unidades_Academicas;
import com.example.joao_.monitoria.Modelo.Unidade_Academica;
import com.example.joao_.monitoria.Modelo.Universidade;
import com.example.joao_.monitoria.R;

import java.util.ArrayList;

/**
 * Created by joao- on 17/06/2017.
 */

public class DialogFragment_Gerenciar_EscolhaUnidadeAcademica extends DialogFragment {

    ListView lvUnidadesAcademicas;
    Adapter_ListView_Unidades_Academicas adapter;
    ArrayList<Unidade_Academica> UnidadesAcademicas;
    Universidade universidade ;

    RetornarGerenciarUnidadeAcademicaEscolhida mListener;



    public static DialogFragment_Gerenciar_EscolhaUnidadeAcademica newInstance() {
        DialogFragment_Gerenciar_EscolhaUnidadeAcademica f = new DialogFragment_Gerenciar_EscolhaUnidadeAcademica();
        f.setShowsDialog(true);
        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        universidade = ((Universidade) getArguments().getSerializable("universidade"));
        Boolean isMain = getArguments().getBoolean("isMain");
        if(isMain){
            UnidadesAcademicas = MainActivity.getUnidadesAcademicas();
        }else{
            UnidadesAcademicas = Activity_Cadastro.getUnidadesAcademicas();
        }



        int style = DialogFragment.STYLE_NORMAL;
        int theme = R.style.Theme_AppCompat_DayNight_Dialog_Alert;
        setStyle(style, theme);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_dialog_gerenciar_escolha_unidade_academica, container, false);

        adapter = new Adapter_ListView_Unidades_Academicas(getContext(),UnidadesAcademicas);
        lvUnidadesAcademicas = (ListView) v.findViewById(R.id.lv_dialogEscolhaUnidadeAcademia_UnidadesAcademicas);
        lvUnidadesAcademicas.setEmptyView(v.findViewById(R.id.emptyElementListaUnidadeAcademica));
        lvUnidadesAcademicas.setAdapter(adapter);
        lvUnidadesAcademicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.RetornarGerenciarUnidadeAcademicaEscolhida(UnidadesAcademicas.get(position));
                dismiss();
            }
        });

        return v;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (RetornarGerenciarUnidadeAcademicaEscolhida) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }
    public interface RetornarGerenciarUnidadeAcademicaEscolhida {
        public void RetornarGerenciarUnidadeAcademicaEscolhida(Unidade_Academica unidade_academica);
    }
}
