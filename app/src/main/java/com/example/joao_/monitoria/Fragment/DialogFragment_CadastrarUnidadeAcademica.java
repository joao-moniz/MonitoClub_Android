package com.example.joao_.monitoria.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.joao_.monitoria.Modelo.Unidade_Academica;
import com.example.joao_.monitoria.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by João MOniz on 15/06/2017.
 * Projeto monitoria dialog cadastro Unidade Academica
 */

public class DialogFragment_CadastrarUnidadeAcademica extends DialogFragment {
    EditText edDescricao , edLocalidade;

    private String descricao , localidade;
    Unidade_Academica unidade_academica;

    ProgressDialog dialog;

    RetornarUnidadeAcademica mListener;

    public static DialogFragment_CadastrarUnidadeAcademica newInstance() {
        DialogFragment_CadastrarUnidadeAcademica f = new DialogFragment_CadastrarUnidadeAcademica();
        f.setShowsDialog(true);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_DayNight_Dialog_Alert);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_dialog_nova_unidade_academica, container, false);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int largura = size.x;
        v.setMinimumWidth( (int) (largura*0.9));

        unidade_academica = new Unidade_Academica();

        final Button salvar = (Button) v.findViewById(R.id.btCustomUnidadeAcademica_Salvar);
        salvar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    salvar.setBackgroundColor(getResources().getColor(R.color.bagroudClic));
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    salvar.setBackgroundColor(getResources().getColor(R.color.bagroudImpar));
                }
                return false;
            }
        });
        salvar.setOnClickListener(buttonSalvar());

        edDescricao = (EditText) v.findViewById(R.id.ed_CustomUnidadeAcademica_Descricao);
        edLocalidade = (EditText) v.findViewById(R.id.ed_CustomUnidadeAcademica_Localidade);
        edDescricao.setError(null);
        edLocalidade.setError(null);


        return v;
    }
    public View.OnClickListener buttonSalvar(){
         View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (verificarEditext()) {
                    unidade_academica.setName(descricao);
                    unidade_academica.setLocalidade(localidade);
                    mListener.RetornarUnidadeAcademica(unidade_academica);
                    dismiss();
                }
            }

        };
        return click;
    }
    public  boolean verificarEditext(){
        descricao = edDescricao.getText().toString();
        localidade = edLocalidade.getText().toString();
        if(descricao.isEmpty()){
            edDescricao.setError("Campo obrigatorio");
            edDescricao.requestFocus();
        }else if(localidade.isEmpty()){
            edLocalidade.setError("Campo obrigatorio");
            edLocalidade.requestFocus();
        }else{
            return  true;
        }
        return false;
    }
    public void salvaeUnidadeAcademica(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Unidades_Academicas");
        DatabaseReference newPostRef = myRef.push();
        unidade_academica.setId(newPostRef.getKey());
        newPostRef.setValue(unidade_academica).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dialog.dismiss();


                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (RetornarUnidadeAcademica) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    public interface RetornarUnidadeAcademica {
        public void RetornarUnidadeAcademica(Unidade_Academica unidade_academica);
    }

}
