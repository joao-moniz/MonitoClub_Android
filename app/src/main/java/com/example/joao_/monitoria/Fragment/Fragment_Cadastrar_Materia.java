package com.example.joao_.monitoria.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.joao_.monitoria.Modelo.Materia;
import com.example.joao_.monitoria.Modelo.Unidade_Academica;
import com.example.joao_.monitoria.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by joao- on 08/06/2017.
 */

public class Fragment_Cadastrar_Materia extends Fragment {

    /*EditText edDescricao,edLocaliade;
    Button btSalvar;
    private String descricao ;
    FragmentSalvarMateria_CadastrarMateria mListenerNovaMateria;

    ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cadastro_materia,container,false);
        btSalvar = (Button) v.findViewById(R.id.bt_FragmentCadastroMateria_Salvar);
        btSalvar.setOnClickListener(buttonSalvar());
        btSalvar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    btSalvar.setBackgroundColor(getResources().getColor(R.color.bagroudClic));
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    btSalvar.setBackgroundColor(getResources().getColor(R.color.bagroudImpar));
                }
                return false;
            }
        });

        edDescricao = (EditText) v.findViewById(R.id.ed_FragmentCadastroMateria_Descricao);

        edDescricao.setError(null);

        return v;
    }

    public  boolean verificarEditext(){
        descricao = edDescricao.getText().toString();
        if(descricao.isEmpty()){
            edDescricao.setError("Campo obrigatorio");
            edDescricao.requestFocus();
        }else{
            return  true;
        }
        return false;

    }

    public void salvaeUnidadeAcademica(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Unidades_Academicas");
        DatabaseReference newPostRef = myRef.push();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListenerNovaMateria = (FragmentSalvarMateria_CadastrarMateria) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }


    public interface FragmentSalvarMateria_CadastrarMateria {
        public void FragmentSalvarMateria_CadastrarMateria(Materia materia);

    }


    public View.OnClickListener buttonSalvar(){
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog (getContext());
                dialog.setMessage("Por favor aguarde...");
                dialog.setCancelable(false);
                dialog.show();

                if(verificarEditext()){

                    salvaeUnidadeAcademica();
                    dialog.dismiss();

                }else {
                    dialog.dismiss();
                }
            }
        };
        return click;
    }*/
}
