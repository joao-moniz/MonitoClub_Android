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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joao_.monitoria.Adapter.Adapter_ListView_Monitores;
import com.example.joao_.monitoria.Fragment.DialogFragment_CadastrarMonitor;
import com.example.joao_.monitoria.Fragment.DialogFragment_CadastrarMonitoria;
import com.example.joao_.monitoria.Fragment.DialogFragment_ListaMonitorias;
import com.example.joao_.monitoria.Modelo.Departamento;
import com.example.joao_.monitoria.Modelo.Materia;
import com.example.joao_.monitoria.Modelo.Monitor;
import com.example.joao_.monitoria.Modelo.MonitoriaClass;
import com.example.joao_.monitoria.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class Activity_Editar_Materia extends AppCompatActivity implements
        DialogFragment_CadastrarMonitor.CustomSalvarMonitor ,
        DialogFragment_CadastrarMonitoria.CustomSalvarMonitoria {
    ImageButton btAdicionarMonitor;
    EditText edDescricao;
    Button btSalvar;
    ListView lvMonitores;


    private String descricao ;
    ArrayList<Monitor> monitores;
    Adapter_ListView_Monitores adapter;
    Materia materia;

    ProgressDialog dialog;
    FirebaseDatabase database;
    DatabaseReference myRef, MateriaRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__cadastro__materia);

        Intent intent = getIntent();
        materia = (Materia) intent.getSerializableExtra("Materia");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_ActivityCadastroMaterias_mytoolbar);
        myToolbar.setNavigationIcon(R.mipmap.ic_monitoria);
        myToolbar.setTitle("Editar Matéria");
        setSupportActionBar(myToolbar);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Materias");
        MateriaRef = myRef.child(materia.getId());

        dialog = new ProgressDialog (Activity_Editar_Materia.this);
        dialog.setMessage("Por favor aguarde...");
        dialog.setCancelable(false);
        dialog.show();

        monitores = new ArrayList<>();
        monitores = buscarMonitores();

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
        edDescricao.setText(materia.getDescricao());
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
        lvMonitores.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getSupportFragmentManager();
                DialogFragment newFragment = DialogFragment_CadastrarMonitoria.newInstance();
                Bundle args = new Bundle();
                args.putSerializable("monitor",monitores.get(position));
                args.putString("monitorId", monitores.get(position).getId());
                newFragment.setArguments(args);
                newFragment.show(fm, "dialog");
                return true;
            }
        });
        lvMonitores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(monitores.get(position).getMonitorias() == null){
                    Toast.makeText(Activity_Editar_Materia.this,"Nenhuma monitoria cadastrada.",Toast.LENGTH_LONG).show();
                }else{
                    FragmentManager fm = getSupportFragmentManager();
                    DialogFragment newFragment = DialogFragment_ListaMonitorias.newInstance();
                    Bundle args = new Bundle();
                    args.putSerializable("monitorias",monitores.get(position).getMonitorias());
                    args.putString("monitorName", monitores.get(position).getName());
                    newFragment.setArguments(args);
                    newFragment.show(fm, "dialog");
                }
            }
        });
    }

    public ArrayList<Monitor> buscarMonitores(){
        DatabaseReference DepartamentoRef = database.getReference("Monitores");
        Query query = DepartamentoRef.orderByChild("materiaId").equalTo(materia.getId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!= null){
                    GenericTypeIndicator<HashMap<String, Monitor>> t = new GenericTypeIndicator<HashMap<String, Monitor>>() {
                    };
                    HashMap<String, Monitor> beans = dataSnapshot.getValue(t);
                    Set set = beans.entrySet();
                    Iterator i = set.iterator();
                    monitores.clear();
                    while (i.hasNext()) {
                        Map.Entry me = (Map.Entry) i.next();
                        monitores.add((Monitor) me.getValue());
                    }
                    adapter.updateResults(monitores);
                    dialog.dismiss();
                }
                else{
                    dialog.dismiss();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        return monitores;
    }


    public View.OnClickListener buttonSalvar(){
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                if(verificarEditext()){
                    materia.setDescricao(edDescricao.getText().toString());
                    ArrayList<String> monitorId = new ArrayList<>();
                    for(int i=0; i<monitores.size()-1;++i){;
                        monitorId.add(monitores.get(i).getId());
                    }
                    materia.setMonitoresId(monitorId);
                    salvarMateria();
                }else {
                    dialog.dismiss();
                }
            }
        };
        return click;
    }

    public void salvarMateria(){
        MateriaRef.child("monitoresId").setValue(materia.getMonitoresId()).addOnFailureListener(new OnFailureListener() {
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

    @Override
    public void CustomSalvarMonitoria(MonitoriaClass monitoria) {
        buscarMonitores();
    }
}
