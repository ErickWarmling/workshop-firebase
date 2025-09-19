package com.example.workshop_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.workshop_firebase.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroUsuario extends AppCompatActivity {

    private ImageView imageViewSetaVoltar;
    private EditText edNome;
    private EditText edTelefone;
    private EditText edEmail;
    private EditText edSenha;
    private Button btnCadastrar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_usuario);

        mAuth = FirebaseAuth.getInstance();
        imageViewSetaVoltar = findViewById(R.id.imageViewSetaVoltar);
        edNome = findViewById(R.id.edNome);
        edTelefone = findViewById(R.id.edTelefone);
        edEmail = findViewById(R.id.edEmail);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        edSenha = findViewById(R.id.edSenha);

        btnCadastrar.setOnClickListener(v -> {
            String nomeCompleto = edNome.getText().toString();
            String telefone = edTelefone.getText().toString();
            String email = edEmail.getText().toString();
            String senha = edSenha.getText().toString();

            Usuario usuario = new Usuario();
            usuario.setNome(nomeCompleto);
            usuario.setTelefone(telefone);
            usuario.setEmail(email);

            if (!nomeCompleto.isEmpty() && !telefone.isEmpty() && !email.isEmpty() && !senha.isEmpty()) {
                mAuth.createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    usuario.setId(mAuth.getUid());
                                    usuario.salvarUsuario();
                                    abrirTelaLogin();
                                } else {
                                    String msgErro = task.getException().getMessage();
                                    Toast.makeText(CadastroUsuario.this, "Erro: " + msgErro, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        imageViewSetaVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(CadastroUsuario.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void abrirTelaLogin() {
        Intent intent = new Intent(CadastroUsuario.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}