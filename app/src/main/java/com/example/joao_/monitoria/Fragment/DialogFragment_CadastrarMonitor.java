package com.example.joao_.monitoria.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.joao_.monitoria.Modelo.Monitor;
import com.example.joao_.monitoria.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by João MOniz on 15/06/2017.
 * Projeto monitoria dialog cadastro Unidade Academica
 */

public class DialogFragment_CadastrarMonitor extends DialogFragment {

    EditText edName,edEmail;
    Button btSalvar;
    private String name,email ;
    Monitor monitor;
    CustomSalvarMonitor mListenerNovoMonitor;
    String MateriaId;

    ProgressDialog dialog;

    public static DialogFragment_CadastrarMonitor newInstance() {
        DialogFragment_CadastrarMonitor f = new DialogFragment_CadastrarMonitor();
        f.setShowsDialog(true);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MateriaId = getArguments().getString("materiaId");
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_dialog_novo_monitor, container, false);
        edName = (EditText) v.findViewById(R.id.ed_CustomCadastararMonitor_Name);
        edEmail = (EditText) v.findViewById(R.id.ed_CustomCadastararMonitor_Email);
        btSalvar = (Button) v.findViewById(R.id.bt_CustomCadastararMonitor_Salvar);
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
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog (getContext());
                dialog.setMessage("Por favor aguarde...");
                dialog.setCancelable(false);
                dialog.show();
                if(verificarEditext()){
                    email = edEmail.getText().toString();
                    name = edName.getText().toString();
                    monitor.setEmail(email);
                    monitor.setName(name);
                    monitor.setMateriaId(MateriaId);
                    salvarMonitor();
                }else{
                    dialog.dismiss();
                }
            }
        });

        monitor = new Monitor();

        edName.setError(null);

        return v;
    }
    public  boolean verificarEditext(){
        name = edName.getText().toString();
        if(name.isEmpty()){
            edName.setError("Campo obrigatorio");
            edName.requestFocus();
        }else{
            return  true;
        }
        return false;

    }

    public void salvarMonitor(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Monitores");
        DatabaseReference newPostRef = myRef.push();
        monitor.setId(newPostRef.getKey());
        newPostRef.setValue(monitor).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dialog.dismiss();
                mListenerNovoMonitor.CustomSalvarMonitor(monitor);
                dismiss();
            }
        });
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListenerNovoMonitor = (CustomSalvarMonitor) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }


    public interface CustomSalvarMonitor {
        public void CustomSalvarMonitor(Monitor monitor);

    }

}

