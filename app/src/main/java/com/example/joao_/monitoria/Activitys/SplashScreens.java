package com.example.joao_.monitoria.Activitys;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.joao_.monitoria.Modelo.Usuario;
import com.example.joao_.monitoria.R;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SplashScreens extends Activity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener ;
    private Usuario UsuarioLogado;
    FirebaseUser user;
    private boolean usuarioLogado,verificado,usuarioDefinido, acabado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screens);

        UsuarioLogado = new Usuario();
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                verificado = true;
                if (user != null) {
                    usuarioLogado = true;
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Usuario");
                        DatabaseReference RefUser = myRef.child(user.getUid());
                        RefUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if(dataSnapshot.getValue() != null) {
                                    UsuarioLogado = dataSnapshot.getValue(Usuario.class);
                                }
                                else{
                                    DefinirUsuariLogado();
                                }
                                usuarioDefinido = true;

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }else{
                    usuarioLogado = false;

                    /*
                        DefinirUsuariLogado();
                        usuarioDefinido = true;*/
                    }
                }
        };

        new CountDownTimer  (10000, 1000) {
            public void onFinish() {
                if(!acabado){
                    if(verificado && usuarioLogado){
                        goMainScreen();
                    }else{
                        mAuth.signOut();
                        LoginManager.getInstance().logOut();
                        goLoginScreen();
                    }
                }
            }
            public void onTick(long millisUntilFinished) {
                if(!acabado){
                    if(millisUntilFinished < 5000 && verificado && usuarioLogado && usuarioDefinido){
                        mAuth.removeAuthStateListener(mAuthListener);
                        acabado = true;
                        goMainScreen();
                    }else if(millisUntilFinished < 5000 && verificado && !usuarioLogado){
                        goLoginScreen();
                        acabado = true;
                    }
                }
            }

        }.start();
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("User",UsuarioLogado);
        startActivity(intent);
    }

    private void goLoginScreen(){
        Intent intent = new Intent(this, FacebookLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void DefinirUsuariLogado(){
        UsuarioLogado.setNome(user.getDisplayName());
        UsuarioLogado.setId(user.getUid());
        UsuarioLogado.setTipo(0);
        UsuarioLogado.setEmail(user.getEmail());
        try{
            UsuarioLogado.setPhotoURL(user.getPhotoUrl().toString());
        }catch (NullPointerException e){
            System.out.print(e.getMessage());
        }
    }

    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

}
