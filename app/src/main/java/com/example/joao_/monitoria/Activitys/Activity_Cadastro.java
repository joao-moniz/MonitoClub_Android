package com.example.joao_.monitoria.Activitys;


import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joao_.monitoria.Fragment.DialogFragment_CadastrarUnidadeAcademica;
import com.example.joao_.monitoria.Fragment.DialogFragment_Gerenciar_EscolhaUnidadeAcademica;
import com.example.joao_.monitoria.Fragment.Fragment_Cadastro_Universidade;
import com.example.joao_.monitoria.Fragment.Fragment_Lista_Universidades;
import com.example.joao_.monitoria.Modelo.Unidade_Academica;
import com.example.joao_.monitoria.Modelo.Universidade;
import com.example.joao_.monitoria.Modelo.Usuario;
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

public class Activity_Cadastro extends AppCompatActivity implements Fragment_Lista_Universidades.OnArticleSelectedListener,
        Fragment_Cadastro_Universidade.OnArticleSelectedListenerFragmentCadatroUniversidade_NovaUnidadeAcademica,
        DialogFragment_CadastrarUnidadeAcademica.RetornarUnidadeAcademica,
        Fragment_Cadastro_Universidade.Action_SalvarUnicersidades,
        DialogFragment_Gerenciar_EscolhaUnidadeAcademica.RetornarGerenciarUnidadeAcademicaEscolhida {

    ListView lvUniversidades ;
    static ArrayList<Unidade_Academica> unidadeAcademicas;
    Usuario User = new Usuario();
    Fragment FragmentAberto;
    FragmentManager fm;
    FragmentTransaction ft;
    Fragment_Lista_Universidades fragment_universidade;

    Fragment_Cadastro_Universidade fragment_cadastro_universidade;
    Boolean OnbackPressed;

    FirebaseDatabase database;
    DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__cadastro);
        OnbackPressed = false;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                User = null;
            } else {
                User = (Usuario) extras.getSerializable("User");
            }
        } else {
            User= (Usuario) savedInstanceState.getSerializable("User");
        }

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Unidades_Academicas");

        unidadeAcademicas = new ArrayList<>();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_CadastroActivity);
        myToolbar.setTitle("Universidades");
        myToolbar.setNavigationIcon(R.mipmap.ic_monitoria);
        setSupportActionBar(myToolbar);

        fm = getSupportFragmentManager();

        if(savedInstanceState == null){
            fragment_universidade = new Fragment_Lista_Universidades();
            ft = fm.beginTransaction();
            ft.add(R.id.fragmentVenda, fragment_universidade, "fragment_universidade");
            ft.addToBackStack("pilha");
            ft.commit();
            FragmentAberto = fragment_universidade;
        }
    }

    public void BuscarUnidadesAcademicas(final Universidade universidade) {
        if (!universidade.getUnidades_academicas_id().isEmpty()) {
            Query query = myRef.orderByChild("universidadeId").equalTo(universidade.getId());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<HashMap<String, Unidade_Academica>> t = new GenericTypeIndicator<HashMap<String, Unidade_Academica>>() {
                    };
                    HashMap<String, Unidade_Academica> beans = dataSnapshot.getValue(t);
                    Set set = beans.entrySet();
                    Iterator i = set.iterator();
                    while (i.hasNext()) {
                        Map.Entry me = (Map.Entry) i.next();
                        unidadeAcademicas.add((Unidade_Academica) me.getValue());
                    }
                    DialogFragment newFragment = DialogFragment_Gerenciar_EscolhaUnidadeAcademica.newInstance();
                    Bundle args = new Bundle();
                    args.putSerializable("universidade", universidade);
                    args.putBoolean("isMain",false);
                    newFragment.setArguments(args);
                    newFragment.show(fm, "dialog");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public static ArrayList<Unidade_Academica> getUnidadesAcademicas(){
        return unidadeAcademicas;
    }

    public void goListaDepartamentoScreen(Unidade_Academica unidade_academica){
        Intent intent = new Intent(Activity_Cadastro.this, Activity_Gerenciar_Lista_Departamentos.class);
        intent.putExtra("UnidadeAcademica",unidade_academica);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.itens_actionbar_cadatro_universidade, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addUniversidade:
                if(FragmentAberto == fragment_universidade){
                    fragment_cadastro_universidade= new Fragment_Cadastro_Universidade();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragmentVenda, fragment_cadastro_universidade, "fragment_cadastro_universidade");
                    ft.addToBackStack("pilha");
                    ft.commit();
                    FragmentAberto = fragment_cadastro_universidade;
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
       if(fm.getBackStackEntryCount() > 0) {
           fm.popBackStack();
           if(fm.getBackStackEntryCount()-1 == 0){
               finish();
           }
           int i = fm.getBackStackEntryCount();
           if(i > 1){
               FragmentAberto = fm.getFragments().get(i - 2);
           }else{
               FragmentAberto = null;
           }

       }
       else{
           super.onBackPressed();
       }
    }

    @Override
    public void onArticleSelected(Universidade universidade) {
        unidadeAcademicas.clear();
        if(universidade.getUnidades_academicas_id() == null || universidade.getUnidades_academicas_id().isEmpty()){
            DialogFragment newFragment = DialogFragment_Gerenciar_EscolhaUnidadeAcademica.newInstance();
            Bundle args = new Bundle();
            args.putSerializable("universidade", universidade);
            args.putBoolean("isMain",false);
            newFragment.setArguments(args);

            newFragment.show(fm, "dialog");
        }else {
            BuscarUnidadesAcademicas(universidade);
        }

    }

    @Override
    public void OnArticleSelectedListenerFragmentCadatroUniversidade_NovaUnidadeAcademica() {
        DialogFragment newFragment = DialogFragment_CadastrarUnidadeAcademica.newInstance();
        newFragment.show(fm, "dialog");

    }


    @Override
    public void RetornarUnidadeAcademica(Unidade_Academica unidade_academica) {
        fragment_cadastro_universidade.AdicionarUnidadeAcademica(unidade_academica);
    }

    @Override
    public void Action_SalvarUnicersidades(Universidade universidade) {
        fm.popBackStack();
        finish();
    }

    @Override
    public void RetornarGerenciarUnidadeAcademicaEscolhida(Unidade_Academica unidade_academica) {
        goListaDepartamentoScreen(unidade_academica);
    }
}
