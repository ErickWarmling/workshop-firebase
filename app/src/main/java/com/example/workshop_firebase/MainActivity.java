package com.example.workshop_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText edEmail;
    private EditText edSenha;
    private Button btnEntrar;
    private TextView tvCadastro;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        edEmail = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        tvCadastro = findViewById(R.id.tvCadastro);
        
        btnEntrar.setOnClickListener(v -> {
            String emailLogin = edEmail.getText().toString();
            String senhaLogin = edSenha.getText().toString();
            
            if (!emailLogin.isEmpty() && !senhaLogin.isEmpty()) {
                mAuth.signInWithEmailAndPassword(emailLogin, senhaLogin)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    abrirTelaPrincipal();
                                } else {
                                    String msgErro = task.getException().getMessage();
                                    Toast.makeText(MainActivity.this, "Erro: " + msgErro, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        tvCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroUsuario.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        edEmail.setText("");
        edSenha.setText("");
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(MainActivity.this, TelaPrincipal.class);
        startActivity(intent);
        finish();
    }
}