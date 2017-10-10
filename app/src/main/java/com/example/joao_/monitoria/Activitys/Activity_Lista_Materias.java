package com.example.joao_.monitoria.Activitys;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.joao_.monitoria.Adapter.Adapter_ListView_Materias;
import com.example.joao_.monitoria.Modelo.Departamento;
import com.example.joao_.monitoria.Modelo.Materia;
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

public class Activity_Lista_Materias extends AppCompatActivity {
    ProgressBar pbListaMaterias;
    ListView lvMaterias;
    Departamento departamento;

    FirebaseDatabase database;
    FragmentManager fm;
    FragmentTransaction ft;

    static ArrayList<Materia> materias;
    static Adapter_ListView_Materias adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_materias);
        Intent intent = getIntent();
        departamento = new Departamento();
        departamento = (Departamento) intent.getSerializableExtra("Departamento");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_ActivityListaMaterias);
        myToolbar.setTitle("Materias");
        myToolbar.setNavigationIcon(R.mipmap.ic_monitoria);
        setSupportActionBar(myToolbar);

       // List<String> cursos = Arrays.asList(getResources().getStringArray(R.array.MonitoriaProdução));
        fm = getSupportFragmentManager();

        materias = new ArrayList<>();
        adapter = new Adapter_ListView_Materias(getBaseContext(),materias);
        database = FirebaseDatabase.getInstance();


        pbListaMaterias = (ProgressBar) findViewById(R.id.pb_ActivityListaMarerias);
        lvMaterias= (ListView)findViewById(R.id.lv_ActivityListaMaterias_Materias);
        lvMaterias.setAdapter(adapter);
        if(departamento.getMaterias() == null || departamento.getMaterias().isEmpty()){
            lvMaterias.setEmptyView(findViewById(R.id.tv_ActivityListaMaterias_emptView));
        }else{
            pbListaMaterias.setVisibility(View.VISIBLE);
            BuscarMaterias();
        }
        lvMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Activity_Lista_Materias.this, Activity_Editar_Materia.class);
                intent.putExtra("Materia",materias.get(position));
                startActivity(intent);
            }
        });

        final ImageButton ibPesquisar = (ImageButton) findViewById(R.id.ib_ActivityListaMaterias_Pesquisar);
        ibPesquisar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    ibPesquisar.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                }
                else {
                    ibPesquisar.setBackground(getResources().getDrawable(R.color.colorTransparente));
                }

                return false;
            }

        });
    }

    @Override
    protected void onResume() {
        BuscarMaterias();
        super.onResume();
    }

    public void BuscarMaterias(){
        DatabaseReference DepartamentoRef = database.getReference("Materias");
        Query query = DepartamentoRef.orderByChild("departamentoId").equalTo(departamento.getId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!= null){
                    GenericTypeIndicator<HashMap<String, Materia>> t = new GenericTypeIndicator<HashMap<String, Materia>>() {};
                    HashMap<String, Materia> beans = dataSnapshot.getValue(t);
                    Set set = beans.entrySet();
                    Iterator i = set.iterator();
                    materias.clear();
                    while (i.hasNext()) {
                        Map.Entry me = (Map.Entry) i.next();
                        materias.add((Materia) me.getValue());
                    }
                    pbListaMaterias.setVisibility(View.GONE);
                    adapter.updateResults(materias);

                }else{
                    lvMaterias.setEmptyView(findViewById(R.id.tv_ActivityListaMaterias_emptView));
                }


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
        inflater.inflate(R.menu.itens_actionbar_cadastro_materias, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addMateria:
                Intent intent = new Intent(Activity_Lista_Materias.this, Activity_Cadastro_Materia.class);
                intent.putExtra("Departamento",departamento);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
