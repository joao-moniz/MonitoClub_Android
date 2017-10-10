package com.example.joao_.monitoria.Fragment;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.joao_.monitoria.Adapter.Adapter_ListView_Unidades_Academicas;
import com.example.joao_.monitoria.Modelo.Unidade_Academica;
import com.example.joao_.monitoria.Modelo.Universidade;
import com.example.joao_.monitoria.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by joao- on 08/06/2017.
 */

public class Fragment_Cadastro_Universidade extends Fragment {
    String descricao;
    int Contador = 0;

    EditText edDescricao;

    ListView lvUnidadesAcademicas;
    Adapter_ListView_Unidades_Academicas adapter;

    ArrayList<Unidade_Academica> unidadesAcademicas;
    Universidade universidade;

    Button btSalvar;

    Bitmap bitmap;
    ImageButton btAdicionarUnidadeAcademica, btAdicionarAdicionarIcone;

    OnArticleSelectedListenerFragmentCadatroUniversidade_NovaUnidadeAcademica mListener;
    Action_SalvarUnicersidades mListener_SalvarUniversidade;

    FirebaseDatabase database;
    DatabaseReference myRef, myUniversidadeRef;

    private static final int SELECT_PICTURE = 1;

    static Dialog dialog;
    AlertDialog alerta;
    ProgressDialog progressDialog;

    ScrollView scrollViewCadastroUniversidade;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cadastro_universidade, container, false);

        dialog = new Dialog(getActivity());

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Unidades_Academicas");
        myUniversidadeRef = database.getReference("Universidades");

        bitmap = null;
        unidadesAcademicas = new ArrayList<>();
        universidade = new Universidade();

        scrollViewCadastroUniversidade = (ScrollView) v.findViewById(R.id.scrollViewCadastroUniversidade);

        edDescricao = (EditText) v.findViewById(R.id.ed_FragmentCadastroUniversidade_Descricao);
        edDescricao.setError(null);

        lvUnidadesAcademicas = (ListView) v.findViewById(R.id.lvUnidadesAcademicas);
        lvUnidadesAcademicas.setEmptyView(v.findViewById(R.id.emptyElement));
        adapter = new Adapter_ListView_Unidades_Academicas(getActivity().getApplicationContext(), unidadesAcademicas);
        lvUnidadesAcademicas.setAdapter(adapter);

        btAdicionarAdicionarIcone = (ImageButton) v.findViewById(R.id.im_FragmentCadastrarUniversidade_AdicionarIcone);
        btAdicionarAdicionarIcone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Buscar imagens galeria"),
                        SELECT_PICTURE);
            }
        });


        btSalvar = (Button) v.findViewById(R.id.btFragmentCadastroUniversidade_Salvar);
        btSalvar.setOnClickListener(clickBtSalvar());
        btSalvar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btSalvar.setBackgroundColor(getResources().getColor(R.color.bagroudClic));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btSalvar.setBackground(getResources().getDrawable(R.color.bagroudImpar));
                }
                return false;
            }
        });
        btAdicionarUnidadeAcademica = (ImageButton) v.findViewById(R.id.im_FragmentCadastrarUniversidade_AdicionarUnidadeAcademica);
        btAdicionarUnidadeAcademica.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btAdicionarUnidadeAcademica.setImageResource(R.drawable.plus_green);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btAdicionarUnidadeAcademica.setImageResource(R.drawable.plus);
                }
                return false;
            }
        });
        btAdicionarUnidadeAcademica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnArticleSelectedListenerFragmentCadatroUniversidade_NovaUnidadeAcademica();
            }
        });
        return v;
    }

    public void AdicionarUnidadeAcademica(Unidade_Academica uni){
        unidadesAcademicas.add(uni);
        adapter.updateResults(unidadesAcademicas);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnArticleSelectedListenerFragmentCadatroUniversidade_NovaUnidadeAcademica) activity;
            mListener_SalvarUniversidade = (Action_SalvarUnicersidades) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    public interface OnArticleSelectedListenerFragmentCadatroUniversidade_NovaUnidadeAcademica {
        public void OnArticleSelectedListenerFragmentCadatroUniversidade_NovaUnidadeAcademica();

    }

    public interface Action_SalvarUnicersidades {
        public void Action_SalvarUnicersidades(Universidade universidade);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri imageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageUri);
                    btAdicionarAdicionarIcone.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 128, 128, true));
                } catch (IOException e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public View.OnClickListener clickBtSalvar(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Por favor aguarde...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                if(edDescricao.getText().toString().isEmpty()){
                    progressDialog.dismiss();
                    edDescricao.setError("Campo obrigatorio");
                    edDescricao.requestFocus();
                }else if(bitmap == null){
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"Selecione um ícone",Toast.LENGTH_LONG).show();
                }else {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://monitoria-93ebd.appspot.com");
                    StorageReference imagesRef = storageRef.child("Icones_Universidades");
                    descricao = edDescricao.getText().toString();
                    universidade.setName(descricao);
                    descricao = descricao.replace(" ","_");
                    imagesRef = imagesRef.child(descricao);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = imagesRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Falha");
                            builder.setMessage(exception.getMessage());
                            alerta = builder.create();
                            alerta.show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            try {
                                universidade.setImagem_url(downloadUrl.toString());
                            }catch (NullPointerException e){
                                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                            salvarUniversidade();
                        }
                    });
                }
            }
        };
        return onClickListener;
    }


    public void salvarUniversidade(){
        if(!unidadesAcademicas.isEmpty()){
            ArrayList<String> ids = new ArrayList<>();
            for(int i = 0 ; i < unidadesAcademicas.size()-1 ; ++i ){
                ids.add(unidadesAcademicas.get(i).getId());
            }
            universidade.setUnidades_academicas_id(ids);
        }

        DatabaseReference newPostRef = myUniversidadeRef.push();
        universidade.setId(newPostRef.getKey());
        newPostRef.setValue(universidade).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if(unidadesAcademicas.size()>0){
                    salvaeUnidadesAcademicas();
                }
                else{
                    progressDialog.dismiss();
                    mListener_SalvarUniversidade.Action_SalvarUnicersidades(universidade);
                }


            }
        });
    }
    public void salvaeUnidadesAcademicas(){
        DatabaseReference newRef = myRef.push();
        unidadesAcademicas.get(Contador).setId(newRef.getKey());
        unidadesAcademicas.get(Contador).setUniversidadeId(universidade.getId());
        newRef.setValue(unidadesAcademicas.get(Contador)).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Contador++;
                if (Contador < unidadesAcademicas.size()) {
                    salvaeUnidadesAcademicas();
                }
                else {
                    Contador = 0;
                    ArrayList<String> unidades_academicas = new ArrayList<String>();
                    for(int i=0;i<unidadesAcademicas.size() ; i++) {
                        unidades_academicas.add(unidadesAcademicas.get(i).getId());
                    }
                    universidade.setUnidades_academicas_id(unidades_academicas);
                    myUniversidadeRef.child(universidade.getId()).setValue(universidade).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            mListener_SalvarUniversidade.Action_SalvarUnicersidades(universidade);
                        }
                    });

                }
            }
        });
    }

}

    /*public void buscasUnidadesAcademicas(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Unidades_Academicas");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Unidade_Academica>> t = new GenericTypeIndicator<HashMap<String, Unidade_Academica>>() {};

                HashMap<String, Unidade_Academica> beans = dataSnapshot.getValue(t);
                Set set = beans.entrySet();
                Iterator i = set.iterator();

                // Display elements
                while (i.hasNext()) {
                    Map.Entry me = (Map.Entry) i.next();
                    try{
                        unidadesAcademicas.add((Unidade_Academica) me.getValue());
                    }catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                adapter.updateResults(unidadesAcademicas);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

