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
import com.example.joao_.monitoria.Modelo.MonitoriaClass;
import com.example.joao_.monitoria.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * Created by João MOniz on 15/06/2017.
 * Projeto monitoria dialog cadastro Unidade Academica
 */

public class DialogFragment_CadastrarMonitoria extends DialogFragment {

    EditText edDiaSemana,edInicio,edFim,edSala;
    Button btSalvar;
    private String diaSemana,inicio,fim,sala ;
    Monitor monitor;
    MonitoriaClass monitoria;
    CustomSalvarMonitoria mListenerNovoMonitor;
    String monitorId;

    ProgressDialog dialog;

    public static DialogFragment_CadastrarMonitoria newInstance() {
        DialogFragment_CadastrarMonitoria f = new DialogFragment_CadastrarMonitoria();
        f.setShowsDialog(true);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        monitorId = getArguments().getString("monitorId");
        monitor = new Monitor();
        monitor= (Monitor) getArguments().getSerializable("monitor");

        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_dialog_cadastro_monitoria, container, false);
        edDiaSemana = (EditText) v.findViewById(R.id.ed_CustomCadastrarMonitoria_DiaSemana);
        edFim = (EditText) v.findViewById(R.id.ed_CustomCadastrarMonitoria_fim);
        edInicio = (EditText) v.findViewById(R.id.ed_CustomCadastrarMonitoria_inioo);
        edSala = (EditText) v.findViewById(R.id.ed_CustomCadastroMonitoria_sala);

        monitoria = new MonitoriaClass();

        btSalvar = (Button) v.findViewById(R.id.bt_CustomCadastrarMonitoria_Salvar);
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
                    monitoria.setSalas(sala);
                    monitoria.setDiaSemana(diaSemana);
                    monitoria.setFim(fim);
                    monitoria.setInicio(inicio);
                    monitoria.setMonitorId(monitorId);
                    salvarMonitor();
                }else{
                    dialog.dismiss();
                }
            }
        });

       // monitor = new Monitor();

        edDiaSemana.setError(null);
        edInicio.setError(null);
        edFim.setError(null);

        return v;
    }
    public  boolean verificarEditext(){
        diaSemana = edDiaSemana.getText().toString();
        inicio = edInicio.getText().toString();
        fim = edFim.getText().toString();
        sala = edSala.getText().toString();
        if(diaSemana.isEmpty()){
            edDiaSemana.setError("Campo obrigatorio");
            edDiaSemana.requestFocus();
        }else if(inicio.isEmpty()){
            edInicio.setError("Campo obrigatorio");
            edInicio.requestFocus();
        }else if(fim.isEmpty()) {
            edFim.setError("Campo obrigatorio");
            edFim.requestFocus();
        }else if(sala.isEmpty()){
            edSala.setError("Campo obrigatorio");
            edSala.requestFocus();
        } else {
            return  true;
        }
        return false;
    }

    public void salvarMonitor(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Monitores");
        ArrayList<MonitoriaClass> monitorias;
        if(monitor.getMonitorias() == null){
            monitorias= new ArrayList<>();
        }else{
            monitorias= monitor.getMonitorias();
        }

        monitorias.add(monitoria);
        myRef.child(monitorId).child("monitorias").setValue(monitorias)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dialog.dismiss();
                mListenerNovoMonitor.CustomSalvarMonitoria(monitoria);
                dismiss();
            }
        });
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListenerNovoMonitor = (CustomSalvarMonitoria) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }


    public interface CustomSalvarMonitoria {
        public void CustomSalvarMonitoria(MonitoriaClass monitoria);

    }
}

