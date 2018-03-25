package nof.airsoft;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.CollationElementIterator;

import model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private Button buttonSignin;
    private EditText editText_email;
    private EditText editText_senha;
    private TextView textView_register;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private CollationElementIterator resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        buttonSignin = (Button) findViewById(R.id.buttonSignin);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_senha = (EditText) findViewById(R.id.editText_senha);
        textView_register = (TextView) findViewById(R.id.textView_register);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null){

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }

            }
        };

        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iniciaLogin();

            }
        });

        textView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistroActivity.class));
            }
        });

    }

    public void callReset(View view){
        Intent intent = new Intent(this, ResetActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

    }
    public void iniciaLogin() {
        final String email = editText_email.getText().toString().trim();
        String senha = editText_senha.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Usuário e/ou senha não preenchido", Toast.LENGTH_SHORT).show();
            return;
        } else {

            mAuth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Usuário e/ou senha incorreta", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }



}
