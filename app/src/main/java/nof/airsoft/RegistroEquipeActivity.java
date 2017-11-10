package nof.airsoft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import utils.ConfiguracoesFirebase;
import utils.GetDataFromFirebase;

public class RegistroEquipeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registarEquipe;
    private EditText editText_nomeEquipe;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private FirebaseUser currentFirebaseUser;
    private DatabaseReference databaseReference;
    private String idUsuario, nomeUsuario, nomeEquipe;
    private Usuario usuario;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_equipe);


        registarEquipe = (Button) findViewById(R.id.registrarEquipe);


        mAuth = FirebaseAuth.getInstance();
        idUsuario = mAuth.getCurrentUser().getUid();
        carregaDados(idUsuario);

        progressDialog = new ProgressDialog(this);
        editText_nomeEquipe = (EditText) findViewById(R.id.editText_nomeEquipe);
        registarEquipe.setOnClickListener(this);


    }

    public void carregaDados(String idUsuario) {

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


    }

    @Override
    public void onCreateSupportNavigateUpTaskStack(@NonNull TaskStackBuilder builder) {
        super.onCreateSupportNavigateUpTaskStack(builder);
    }

    private void registerTeam() {

        nomeEquipe = editText_nomeEquipe.getText().toString().trim();
        if (TextUtils.isEmpty(nomeEquipe)) {
            Toast.makeText(this, "Por favor digite o nome", Toast.LENGTH_SHORT).show();
        } else {


            String equipeId = ConfiguracoesFirebase.getFirebase().push().getKey();
            Equipe equipe = new Equipe(equipeId, nomeEquipe, idUsuario);



            //atualiza Dados do usuario e cria equipe


            Usuario usuarioUpdate = new Usuario(usuario.getIdUsuario(), usuario.getUsuarioNome(), usuario.getUsuarioContato(),usuario.getUsuarioEndereco(),equipe.getEquipeId());
            usuarioUpdate.salvarUsuario();

            equipe.criarEquipe(usuarioUpdate);





            Toast.makeText(this, "Informações Salvas!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }


    }

    @Override
    public void onClick(View view) {
        if (view == registarEquipe) {
            registerTeam();

        }

    }
}