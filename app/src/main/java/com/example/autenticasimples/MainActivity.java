package com.example.autenticasimples;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

  private FirebaseAuth mAuth;
  private Button btnSair;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btnSair = (Button) findViewById(R.id.btnSair);

    btnSair.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            disconnect();
          }
        });

    // Initialize Firebase Auth
    mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    if (user != null) {
      Toast.makeText(
              getApplicationContext(),
              "Bem vindo de volta " + user.getEmail() + "!",
              Toast.LENGTH_LONG)
          .show();
    } else {
      Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
      startActivity(intent);
      finish();
    }
  }

  private void disconnect() {
    FirebaseAuth.getInstance().signOut();
    closePrincipal();
  }
  private void closePrincipal()
  {
    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
    startActivity(intent);
    finish();
  }

  @Override
  public void onStart() {
    super.onStart();
    // Check if user is signed in (non-null) and update UI accordingly.
    FirebaseUser currentUser = mAuth.getCurrentUser();
    updateUI(currentUser);
  }

  private void updateUI(FirebaseUser currentUser) {}
}
