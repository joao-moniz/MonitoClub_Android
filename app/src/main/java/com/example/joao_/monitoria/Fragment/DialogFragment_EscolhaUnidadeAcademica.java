package com.example.joao_.monitoria.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.joao_.monitoria.Activitys.Activity_Cadastro;
import com.example.joao_.monitoria.Activitys.MainActivity;
import com.example.joao_.monitoria.Adapter.Adapter_ListView_Unidades_Academicas;
import com.example.joao_.monitoria.Modelo.Unidade_Academica;
import com.example.joao_.monitoria.Modelo.Universidade;
import com.example.joao_.monitoria.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by joao- on 17/06/2017.
 */

public class DialogFragment_EscolhaUnidadeAcademica extends DialogFragment {

    ListView lvUnidadesAcademicas;
    Adapter_ListView_Unidades_Academicas adapter;
    ArrayList<Unidade_Academica> UnidadesAcademicas;
    Universidade universidade ;

    RetornarUnidadeAcademicaEscolhida mListener;



    public static DialogFragment_EscolhaUnidadeAcademica newInstance() {
        DialogFragment_EscolhaUnidadeAcademica f = new DialogFragment_EscolhaUnidadeAcademica();
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
        View v = inflater.inflate(R.layout.custom_dialog_escolha_unidade_academica, container, false);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int largura = size.x;
        //v.setMinimumWidth( (int) (largura*0.9));

        adapter = new Adapter_ListView_Unidades_Academicas(getContext(),UnidadesAcademicas);
        lvUnidadesAcademicas = (ListView) v.findViewById(R.id.lv_dialogEscolhaUnidadeAcademia_UnidadesAcademicas);
        lvUnidadesAcademicas.setEmptyView(v.findViewById(R.id.emptyElementListaUnidadeAcademica));
        lvUnidadesAcademicas.setAdapter(adapter);
        lvUnidadesAcademicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.RetornarUnidadeAcademicaEscolhida(UnidadesAcademicas.get(position));
                dismiss();
            }
        });

        return v;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (RetornarUnidadeAcademicaEscolhida) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }
    public interface RetornarUnidadeAcademicaEscolhida {
        public void RetornarUnidadeAcademicaEscolhida(Unidade_Academica unidade_academica);
    }
}
