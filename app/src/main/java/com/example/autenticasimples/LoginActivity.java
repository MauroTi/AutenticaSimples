package com.example.autenticasimples;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

  private FirebaseAuth mAuth;

  private EditText nome;
  private EditText senha;
  private Button btnLogar;
  private Button btnCancelar;
  private Button btnCadastrar;
  private Button btnRecuperaSenha;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    mAuth = FirebaseAuth.getInstance();

    btnLogar = (Button) findViewById(R.id.btnLogar);
    btnCancelar = (Button) findViewById(R.id.btnCancelar);
    btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
    btnRecuperaSenha = (Button) findViewById(R.id.btnRecuperaSenha);
    nome = (EditText) findViewById(R.id.etNome);
    senha = (EditText) findViewById(R.id.etSenha);

    btnLogar.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            loginUser(nome.getText().toString(), senha.getText().toString());
          }
        });

      btnCadastrar.setOnClickListener(
              new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      loginNewUser(nome.getText().toString(), senha.getText().toString());
                  }
              });

      btnRecuperaSenha.setOnClickListener(
              new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                  }
              });

    btnCancelar.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            nome.setText("");
            senha.setText("");
          }
        });
  }

  private void loginUser(String email, String password) {
    mAuth
        .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                    this,
            new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                  // Sign in success, update UI with the signed-in user's information
                  Log.d("TAG", "signInWithEmail:success");
                  FirebaseUser user = mAuth.getCurrentUser();
                  Toast.makeText(getApplicationContext(), "Login realizado com sucesso!", Toast.LENGTH_LONG).show();

                  openPrincipalActivity();

                  // updateUI(user);
                } else {
                  // If sign in fails, display a message to the user.
                  Log.w("TAG", "signInWithEmail:failure", task.getException());
                  Toast.makeText(
                          getApplicationContext(), "Cadastro não encontrado! Tente novamente ou cadastre-se!", Toast.LENGTH_LONG)
                      .show();
                  // updateUI(null);
                }

                // ...
              }
            });
  }
    private void loginNewUser(final String email, final String password) {
    mAuth
        .createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(
            this,
            new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                  // Sign in success, update UI with the signed-in user's information
                  Log.d("TAG", "createUserWithEmail:success");
                  FirebaseUser user = mAuth.getCurrentUser();
                  Toast.makeText(
                          getApplicationContext(),
                          "Cadastro realizado com sucesso!",
                          Toast.LENGTH_LONG)
                      .show();
                  // updateUI(user);
                } else {
                  // If sign in fails, display a message to the user.
                  Log.w("TAG", "createUserWithEmail:failure", task.getException());



                  // Task<AuthResult> resultado =
                  // mAuth.createUserWithEmailAndPassword(email,password);
                  // resultado.getException().getMessage();
                  // Toast.makeText(getApplicationContext(), (CharSequence) resultado,
                  // Toast.LENGTH_SHORT).show();

                  // Toast.makeText(getApplicationContext(), "Authentication failed.",
                  // Toast.LENGTH_SHORT).show();
                  Toast.makeText(
                          getApplicationContext(),
                          "Cadastro não efetuado! Insira Email e senha de no mínimo 6 caracteres!",
                          Toast.LENGTH_LONG)
                      .show();
                  // updateUI(null);
                }

                // ...
              }
            });
    }

  private boolean userConnected()
  {
      FirebaseUser currentUser = mAuth.getCurrentUser();

      if (currentUser == null)
      {
          return false;
      }
      else{
          return true;
      }
  }

    @Override
    protected void onStart() {
        super.onStart();
        if (userConnected())
        {
            openPrincipalActivity();
        }
    }
    private void openPrincipalActivity()
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
