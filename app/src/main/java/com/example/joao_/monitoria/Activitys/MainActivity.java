package com.example.joao_.monitoria.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joao_.monitoria.Adapter.Adapter_Listview_Escolha_Universidade;
import com.example.joao_.monitoria.Adapter.ListViewAdapterCursos;
import com.example.joao_.monitoria.Fragment.DialogFragment_CadastrarUnidadeAcademica;
import com.example.joao_.monitoria.Fragment.DialogFragment_EscolhaUnidadeAcademica;
import com.example.joao_.monitoria.Modelo.Unidade_Academica;
import com.example.joao_.monitoria.Modelo.Universidade;
import com.example.joao_.monitoria.Modelo.Usuario;
import com.example.joao_.monitoria.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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

public class MainActivity extends AppCompatActivity implements DialogFragment_EscolhaUnidadeAcademica.RetornarUnidadeAcademicaEscolhida{
    Adapter_Listview_Escolha_Universidade adapter;
    static Usuario UsuarioLogado = new Usuario();
    ArrayList<Universidade> ListaUniversidade;
    static ArrayList<Unidade_Academica> UnidadesAcademicas;

    FirebaseDatabase database;
    DatabaseReference myRef;


    FragmentManager fm;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b =  getIntent().getExtras();
        if(b!=null) {
             UsuarioLogado =(Usuario) b.getSerializable("User");
        }
        fm = getSupportFragmentManager();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Unidades_Academicas");

        UnidadesAcademicas = new ArrayList<>();

        ListaUniversidade = new ArrayList<>();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_MainActivity);
        myToolbar.setNavigationIcon(R.mipmap.ic_monitoria);
        setSupportActionBar(myToolbar);


        ListView listView= (ListView)findViewById(R.id.lvCursos);
        adapter = new Adapter_Listview_Escolha_Universidade(getBaseContext(),ListaUniversidade);
        listView.setAdapter(adapter);
        BuscarUniversidades();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UnidadesAcademicas.clear();
                dialog = new ProgressDialog (MainActivity.this);
                dialog.setMessage("Por favor aguarde...");
                dialog.setCancelable(false);
                dialog.show();
                if(ListaUniversidade.get(position).getUnidades_academicas_id() == null || ListaUniversidade.get(position).getUnidades_academicas_id().isEmpty()){
                    DialogFragment newFragment = DialogFragment_EscolhaUnidadeAcademica.newInstance();
                    Bundle args = new Bundle();
                    args.putSerializable("universidade", ListaUniversidade.get(position));
                    args.putBoolean("isMain",true);
                    newFragment.setArguments(args);
                    dialog.dismiss();

                    newFragment.show(fm, "dialog");
                }else {
                    BuscarUnidadesAcademicas(ListaUniversidade.get(position));
                }
            }
        });



        final ImageButton ibPesquisar = (ImageButton) findViewById(R.id.ibPesquisar);

        ibPesquisar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    ibPesquisar.setBackground(getResources().getDrawable(R.drawable.rounded_button));
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    ibPesquisar.setBackground(getResources().getDrawable(R.color.colorTransparente));
                }

                return false;
            }

        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.itens_actionbar_login, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            case R.id.action_gerenciar:
                /*if(UsuarioLogado != null && UsuarioLogado.getTipo() == 2){
                                    goGencenciarScren();
                }else{
                    Toast.makeText(MainActivity.this, "Acesso negado",
                            Toast.LENGTH_SHORT).show();
                }*/
                goGencenciarScren();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logout(){
        LoginManager.getInstance().logOut();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent i = new Intent(MainActivity.this, FacebookLogin.class);
        startActivity(i);
    }

    public void goGencenciarScren(){
        Intent intent = new Intent(this, Activity_Cadastro.class);
        intent.putExtra("User",UsuarioLogado);
        startActivity(intent);
    }

    public void BuscarUniversidades(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Universidades");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    GenericTypeIndicator<HashMap<String, Universidade>> t = new GenericTypeIndicator<HashMap<String, Universidade>>() {};
                    HashMap<String, Universidade> beans = dataSnapshot.getValue(t);
                    Set set = beans.entrySet();
                    Iterator i = set.iterator();
                    ListaUniversidade.clear();
                    while(i.hasNext()){
                        Map.Entry me = (Map.Entry) i.next();
                        ListaUniversidade.add((Universidade) me.getValue());
                    }
                    adapter.updateResults(ListaUniversidade);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void BuscarUnidadesAcademicas(final Universidade universidade){
        if(!universidade.getUnidades_academicas_id().isEmpty()){
            Query query = myRef.orderByChild("universidadeId").equalTo(universidade.getId());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<HashMap<String, Unidade_Academica>> t = new GenericTypeIndicator<HashMap<String, Unidade_Academica>>() {};
                    HashMap<String, Unidade_Academica> beans = dataSnapshot.getValue(t);
                    Set set = beans.entrySet();
                    Iterator i = set.iterator();
                    while(i.hasNext()){
                        Map.Entry me = (Map.Entry) i.next();
                        UnidadesAcademicas.add((Unidade_Academica) me.getValue());
                    }
                    DialogFragment newFragment = DialogFragment_EscolhaUnidadeAcademica.newInstance();
                    Bundle args = new Bundle();
                    args.putSerializable("universidade", universidade);
                    args.putBoolean("isMain",true);
                    newFragment.setArguments(args);
                    dialog.dismiss();

                    newFragment.show(fm, "dialog");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public static ArrayList<Unidade_Academica> getUnidadesAcademicas(){
        return UnidadesAcademicas;
    }

    public static void setUsuario(Usuario usuario){
        UsuarioLogado.setTipo(usuario.getTipo());
    }

    @Override
    public void RetornarUnidadeAcademicaEscolhida(Unidade_Academica unidade_academica) {
        Intent intent = new Intent(MainActivity.this, Activity_Lista_Departamentos.class);
        intent.putExtra("UnidadeAcademica",unidade_academica);
        startActivity(intent);
    }
}


