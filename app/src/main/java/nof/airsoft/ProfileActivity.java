package nof.airsoft;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import model.Equipe;
import model.Usuario;
import utils.GetDataFromFirebase;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private EditText editTextNome;
    private EditText editTextContato;
    private EditText editTextEndereco;
    private Button buttonLogout;
    private Button buttonSalvar;
    private Usuario usuario;
    private Equipe equipe;
    private String idUsuario, nomeUsuario, nomeEquipe, idEquipe;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferencesUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        idUsuario = mAuth.getCurrentUser().getUid();
        carregaUsuario(idUsuario);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    public void carregaUsuario(String idUsuario) {

        Log.v("", "IdUsuario");
        new GetDataFromFirebase().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios/" + idUsuario);
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    usuario = dataSnapshot.getValue(Usuario.class);


                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        databaseReference = FirebaseDatabase.getInstance().getReference().child("usuarios");
        buttonSalvar = (Button) findViewById(R.id.buttonSalvar);
        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextEndereco = (EditText) findViewById(R.id.editTextEndereco);
        editTextContato = (EditText) findViewById(R.id.editTextContato);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(this);
        buttonSalvar.setOnClickListener(this);




    }



            private void saveUserInformation(){
                SharedPreferencesUser sharedPreferencesUser = new SharedPreferencesUser(this);
                    String nome = editTextNome.getText().toString().trim();
                    String contato = editTextContato.getText().toString().trim();
                    String endereco = editTextEndereco.getText().toString().trim();

                    Usuario userInformation = new Usuario(nome, contato, endereco);

                    databaseReference.child(idUsuario).setValue(userInformation);

                    Toast.makeText(this, "Informações salvas " + nome, Toast.LENGTH_SHORT).show();


            }



    public void onClick(View view){
        if (view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
         if (view == buttonSalvar){
             saveUserInformation();
             startActivity(new Intent(this, MainActivity.class));

         }
    }
}
