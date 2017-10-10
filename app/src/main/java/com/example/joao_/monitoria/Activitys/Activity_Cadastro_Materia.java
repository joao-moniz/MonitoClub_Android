package com.example.joao_.monitoria.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.joao_.monitoria.Adapter.Adapter_ListView_Monitores;
import com.example.joao_.monitoria.Fragment.DialogFragment_CadastrarMonitor;
import com.example.joao_.monitoria.Modelo.Departamento;
import com.example.joao_.monitoria.Modelo.Materia;
import com.example.joao_.monitoria.Modelo.Monitor;
import com.example.joao_.monitoria.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Activity_Cadastro_Materia extends AppCompatActivity implements DialogFragment_CadastrarMonitor.CustomSalvarMonitor{
    ImageButton btAdicionarMonitor;
    EditText edDescricao;
    Button btSalvar;
    ListView lvMonitores;


    private String descricao ;
    ArrayList<Monitor> monitores;
    Adapter_ListView_Monitores adapter;
    Materia materia;
    Departamento departamento;

    ProgressDialog dialog;
    FirebaseDatabase database;
    DatabaseReference myRef, MateriaRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__cadastro__materia);

        Intent intent = getIntent();
        departamento = (Departamento) intent.getSerializableExtra("Departamento");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_ActivityCadastroMaterias_mytoolbar);
        myToolbar.setNavigationIcon(R.mipmap.ic_monitoria);
        myToolbar.setTitle("Cadastrar Matéria");
        setSupportActionBar(myToolbar);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Materias");

        monitores = new ArrayList<>();
        materia = new Materia();
        MateriaRef = myRef.push();
        materia.setId(MateriaRef.getKey());



        btSalvar = (Button) findViewById(R.id.bt_FragmentCadastroMateria_Salvar);
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

        edDescricao = (EditText) findViewById(R.id.ed_FragmentCadastroMateria_Descricao);
        btAdicionarMonitor = (ImageButton) findViewById(R.id.ib_ActivityCadastrarMateria_addMonitor);
        btAdicionarMonitor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btAdicionarMonitor.setImageResource(R.drawable.plus_green);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btAdicionarMonitor.setImageResource(R.drawable.plus);
                }
                return false;
            }
        });
        btAdicionarMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                DialogFragment newFragment = DialogFragment_CadastrarMonitor.newInstance();
                Bundle args = new Bundle();
                args.putString("materiaId", materia.getId());
                newFragment.setArguments(args);
                newFragment.show(fm, "dialog");
            }
        });
        edDescricao.setError(null);

        adapter = new Adapter_ListView_Monitores(getBaseContext(),monitores);
        lvMonitores = (ListView) findViewById(R.id.lv_ActivityCadastroMateria_Monitores);
        lvMonitores.setEmptyView(findViewById(R.id.tv_ActivityCadastroMateria_EmptView));
        lvMonitores.setAdapter(adapter);
    }


    public View.OnClickListener buttonSalvar(){
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog (Activity_Cadastro_Materia.this);
                dialog.setMessage("Por favor aguarde...");
                dialog.setCancelable(false);
                dialog.show();

                if(verificarEditext()){
                    materia.setDescricao(edDescricao.getText().toString());
                    ArrayList<String> monitorId = new ArrayList<>();
                    for(int i=0; i<monitores.size()-1;++i){;
                        monitorId.add(monitores.get(i).getId());
                    }
                    materia.setMonitoresId(monitorId);
                    if(departamento != null){
                        materia.setDepartamentoId(departamento.getId());
                    }
                    salvarMateria();
                }else {
                    dialog.dismiss();
                }
            }
        };
        return click;
    }

    public void salvarMateria(){
        MateriaRef.setValue(materia).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dialog.dismiss();
                finish();
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.itens_actionbar_cadatro_materia, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void CustomSalvarMonitor(Monitor monitor) {
        monitores.add(monitor);
        adapter.updateResults(monitores);
    }
}
