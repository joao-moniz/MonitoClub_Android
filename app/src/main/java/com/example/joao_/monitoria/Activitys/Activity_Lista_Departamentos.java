package com.example.joao_.monitoria.Activitys;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.joao_.monitoria.Adapter.Adapter_Listview_Escolha_Departamento;
import com.example.joao_.monitoria.Modelo.Departamento;
import com.example.joao_.monitoria.Modelo.Unidade_Academica;
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

public class Activity_Lista_Departamentos extends AppCompatActivity {

    ArrayList<Departamento> departamentos;
    static Unidade_Academica unidade_academica;

    ListView lvListaDepartamentos;
    Adapter_Listview_Escolha_Departamento adapter;
    ProgressBar progressBar;

    FragmentManager fm;

    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_departamentos);
        Bundle b =  getIntent().getExtras();
        if(b!=null) {
            unidade_academica =(Unidade_Academica) b.getSerializable("UnidadeAcademica");
        }

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Unidades_Academicas");

        fm = getSupportFragmentManager();

        departamentos = new ArrayList<>();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_ListaDepartamentoActivity);
        myToolbar.setNavigationIcon(R.mipmap.ic_monitoria);
        setSupportActionBar(myToolbar);

        progressBar = (ProgressBar) findViewById(R.id.pb_ListaDepartamentosActivity_emptyElement);
        progressBar.setVisibility(View.VISIBLE);

        adapter = new Adapter_Listview_Escolha_Departamento(getBaseContext(),departamentos);
        lvListaDepartamentos = (ListView) findViewById(R.id.lv_ListaDepartamentosActivity_Departamentos);
        //lvListaDepartamentos.setEmptyView(findViewById(R.id.pb_ListaDepartamentosActivity_emptyElement));
        lvListaDepartamentos.setAdapter(adapter);
        if(unidade_academica.getDepartamentosId() == null || unidade_academica.getDepartamentosId().isEmpty()){
            progressBar.setVisibility(View.GONE);
            lvListaDepartamentos.setEmptyView(findViewById(R.id.tv_ListaDepartamentosActivity_emptyElement));
        }else{
            BuscarDepartamentos();
        }
        lvListaDepartamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goActivityMaterias(departamentos.get(position));
            }
        });
    }

    public void goActivityMaterias(Departamento departamento){
        Intent intent = new Intent(Activity_Lista_Departamentos.this, Activity_Lista_Materias.class);
        intent.putExtra("Departamento",departamento);
        startActivity(intent);
    }

    public void BuscarDepartamentos(){
        DatabaseReference DepartamentoRef = database.getReference("Departamentos");
        Query query = DepartamentoRef.orderByChild("unidades_academicas_id").equalTo(unidade_academica.getId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Departamento>> t = new GenericTypeIndicator<HashMap<String, Departamento>>() {};
                HashMap<String, Departamento> beans = dataSnapshot.getValue(t);
                Set set = beans.entrySet();
                Iterator i = set.iterator();
                while(i.hasNext()){
                    Map.Entry me = (Map.Entry) i.next();
                    departamentos.add((Departamento) me.getValue());
                }
                progressBar.setVisibility(View.GONE);
                adapter.updateResults(departamentos);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
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

    public static Unidade_Academica getUnidadeAcademica(){
        return unidade_academica;
    }
}
