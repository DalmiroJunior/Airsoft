package nof.airsoft;

import android.app.ProgressDialog;
import android.content.Intent;
import com.google.android.gms.ads.InterstitialAd;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.ads.AdRequest;
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
import com.google.firebase.database.DatabaseReference;

import model.Usuario;
import utils.ConfiguracoesFirebase;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editText_email;
    private EditText editText_senha;
    private TextView textView_signin;
    private FirebaseAuth mAuth;
    private InterstitialAd mInterstitialAd;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9418670716851344/7361112408");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }

            }
        };
        progressDialog = new ProgressDialog(this);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_senha = (EditText) findViewById(R.id.editText_senha);
        textView_signin = (TextView) findViewById(R.id.textView_signin);
        buttonRegister.setOnClickListener(this);
        textView_signin.setOnClickListener(this);

    }

    private void registerUser() {
        final String email = editText_email.getText().toString().trim();
        String senha = editText_senha.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor digite seu email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Por favor digite sua senha", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registrando Usuario...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            String idUser = task.getResult().getUser().getUid();
                            new SharedPreferencesUser(getApplicationContext()).salvaEmail(email);
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegistroActivity.this, "Não foi possivel realizar o cadastro, tente novamente.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            registerUser();
            mInterstitialAd.show();
        }
        if (view == textView_signin) {
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
