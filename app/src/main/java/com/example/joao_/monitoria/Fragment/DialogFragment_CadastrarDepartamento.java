package com.example.joao_.monitoria.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.joao_.monitoria.Activitys.Activity_Gerenciar_Lista_Departamentos;
import com.example.joao_.monitoria.Modelo.Departamento;
import com.example.joao_.monitoria.Modelo.Unidade_Academica;
import com.example.joao_.monitoria.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by João MOniz on 15/06/2017.
 * Projeto monitoria dialog cadastro Unidade Academica
 */

public class DialogFragment_CadastrarDepartamento extends DialogFragment {
    EditText edDescricao;
    ImageButton imIcone;

    private String descricao;
    Departamento departamento;
    private static final int SELECT_PICTURE = 1;
    Bitmap bitmap;
    Unidade_Academica unidade_academica;

    ProgressDialog progressDialog;
    AlertDialog alerta;
    RetornarGerenciarDepartamento mListener;

    public static DialogFragment_CadastrarDepartamento newInstance() {
        DialogFragment_CadastrarDepartamento f = new DialogFragment_CadastrarDepartamento();
        f.setShowsDialog(true);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        departamento = new Departamento();
        unidade_academica = Activity_Gerenciar_Lista_Departamentos.getUnidadeAcademica();

        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_dialog_novo_departamento, container, false);

        final Button salvar = (Button) v.findViewById(R.id.btCustomCadastrarDepartamento_Salvar);
        salvar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    salvar.setBackgroundColor(getResources().getColor(R.color.bagroudClic));
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    salvar.setBackgroundColor(getResources().getColor(R.color.bagroudImpar));
                }
                return false;
            }
        });
        salvar.setOnClickListener(buttonSalvar());

        edDescricao = (EditText) v.findViewById(R.id.ed_CustomCadastrarDepartamento_Descricao);
        edDescricao.setError(null);

        imIcone = (ImageButton) v.findViewById(R.id.im_CustomCadastrarDepartamento_icone);
        imIcone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    imIcone.setBackground(getResources().getDrawable(R.color.bagroudNormal));
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    imIcone.setBackground(getResources().getDrawable(R.color.colorTransparente));
                }
                return false;
            }
        });
        imIcone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Buscar imagens galeria"),
                        SELECT_PICTURE);
            }
        });

        return v;
    }
    public View.OnClickListener buttonSalvar(){
         View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Por favor aguarde...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                if (!verificarEditext()) {
                    progressDialog.dismiss();
                }else if(bitmap == null){
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"Selecione um ícone",Toast.LENGTH_LONG).show();
                }else{
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://monitoria-93ebd.appspot.com");
                    StorageReference imagesRef = storageRef.child("Icones_Departamentos");
                    descricao = edDescricao.getText().toString();
                    departamento.setName(descricao);
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
                                departamento.setIconeUrl(downloadUrl.toString());
                                departamento.setUnidades_academicas_id(unidade_academica.getId());
                                SalvarDepartamento();
                            }catch (NullPointerException e){
                                progressDialog.dismiss();
                                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            }

        };
        return click;
    }
    public void SalvarDepartamento(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Departamentos");
        DatabaseReference newPostRef = myRef.push();
        departamento.setId(newPostRef.getKey());
        newPostRef.setValue(departamento).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                SalvarDepartamentoUnidadeAcademica(departamento.getId());
            }
        });
    }
    public void SalvarDepartamentoUnidadeAcademica(String DepartamentoId){
        ArrayList<String> DepartamentosId = new ArrayList<>();
        if(unidade_academica.getDepartamentosId() != null){
            DepartamentosId = unidade_academica.getDepartamentosId();
        }
        DepartamentosId.add(DepartamentoId);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Unidades_Academicas");
        myRef.child(unidade_academica.getId()).child("DepartamentosId").setValue(DepartamentosId)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        dismiss();
                        mListener.RetornarGerenciarDepartamento(departamento);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri imageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageUri);
                    imIcone.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 128, 128, true));
                } catch (IOException e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (RetornarGerenciarDepartamento) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    public interface RetornarGerenciarDepartamento {
        public void RetornarGerenciarDepartamento(Departamento departamento);
    }

}
