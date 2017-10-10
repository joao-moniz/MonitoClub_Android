package com.example.joao_.monitoria.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.joao_.monitoria.Modelo.Unidade_Academica;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by joao- on 08/06/2017.
 */

public class Fragment_Cadastrar_Unidade_Academica extends Fragment {

    ImageButton btAdicionarAdicionarIcone;
    EditText edDescricao,edLocaliade;
    Button btSalvar;
    Bitmap bitmap;
    private String descricao , localidade;
    private static final int SELECT_PICTURE = 1;
    Unidade_Academica unidade_academica;
    OnArticleSelectedListenerFragmentCadatroUnidadeAcademica mListener;
    OnArticleSelectedListenerFragmentCadatroUnidadeAcademica_CadastrarNovoCurso mListenerNovoCurso;

    ProgressDialog dialog;
    AlertDialog alerta;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cadastro_unidade_academica,container,false);

        bitmap = null;
        unidade_academica = new Unidade_Academica();

        btSalvar = (Button) v.findViewById(R.id.btFragmentCadastroUnidadeAcademica_Salvar);
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

        edDescricao = (EditText) v.findViewById(R.id.ed_FragmentCadastroUnidadeAcademica_Descricao);
        edLocaliade = (EditText) v.findViewById(R.id.ed_FragmentCadastroUnidadeAcademica_Localidade);

        edDescricao.setError(null);
        edLocaliade.setError(null);

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri imageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageUri);
                    btAdicionarAdicionarIcone.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 256, 256, true));
                } catch (IOException e) {

                }
            }
        }

    }

    public  boolean verificarEditext(){
        descricao = edDescricao.getText().toString();
        localidade = edLocaliade.getText().toString();
        if(descricao.isEmpty()){
            edDescricao.setError("Campo obrigatorio");
            edDescricao.requestFocus();
        }else if(localidade.isEmpty()){
            edLocaliade.setError("Campo obrigatorio");
            edLocaliade.requestFocus();
        }else{
            return  true;
        }
        return false;

    }

    public void salvaeUnidadeAcademica(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Unidades_Academicas");
        DatabaseReference newPostRef = myRef.push();
        unidade_academica.setId(newPostRef.getKey());
        newPostRef.setValue(unidade_academica).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnArticleSelectedListenerFragmentCadatroUnidadeAcademica) activity;
            mListenerNovoCurso = (OnArticleSelectedListenerFragmentCadatroUnidadeAcademica_CadastrarNovoCurso) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    public interface OnArticleSelectedListenerFragmentCadatroUnidadeAcademica {
        public void OnArticleSelectedListenerFragmentCadatroUnidadeAcademica(Unidade_Academica unidade_academica);

    }
    public interface OnArticleSelectedListenerFragmentCadatroUnidadeAcademica_CadastrarNovoCurso {
        public void OnArticleSelectedListenerFragmentCadatroUnidadeAcademica_CadastrarNovoCurso();

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
                    if(bitmap != null){
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://monitoria-93ebd.appspot.com");
                        StorageReference imagesRef = storageRef.child("Icones_Unidades_Academicas");
                        unidade_academica.setName(descricao);
                        descricao = descricao.replace(" ","_");
                        imagesRef = imagesRef.child(descricao);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        UploadTask uploadTask = imagesRef.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                dialog.dismiss();
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
                                unidade_academica.setLocalidade(localidade);
                                //unidade_academica.setIconURL(downloadUrl.toString());
                                salvaeUnidadeAcademica();
                                dialog.dismiss();
                                mListener.OnArticleSelectedListenerFragmentCadatroUnidadeAcademica(unidade_academica);
                            }
                        });
                    }else{
                        dialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Escolha um icone");
                        alerta = builder.create();
                        alerta.show();
                    }
                }else {
                    dialog.dismiss();
                }
            }
        };
        return click;
    }
}
