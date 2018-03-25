package fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import adapters.AdapterEquipes;
import model.Equipe;
import model.Usuario;
import nof.airsoft.R;
import utils.GetDataFromFirebase;


public class MinhaEquipeFragment extends Fragment {

    private RecyclerView recyclerViewParticipantes;
    private DatabaseReference databaseReference, referenceUsuarios;
    private Usuario usuario;
    private TextView textViewNome;


    public MinhaEquipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_minha_equipe, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textViewNome = (TextView) getView().findViewById(R.id.text_nome_equipe);
        String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
        carregaUsuario(idUsuario);

    }


       // usuarios Pega do firebase
    public void carregaUsuario(String idUsuario) {

        new GetDataFromFirebase().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios/" + idUsuario);
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    usuario = dataSnapshot.getValue(Usuario.class);
                    String idEquipe = usuario.getIdEquipe();
                    carregaEquipe(idEquipe);




                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    // Pega do Firebase as Equipes
    public void carregaEquipe(String idEquipe) {

        databaseReference = FirebaseDatabase.getInstance().getReference("equipes/" + idEquipe + "/dados/equipeNome");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                 //   equipe = dataSnapshot.getValue(Equipe.class);

                    textViewNome.setText(dataSnapshot.getValue().toString());

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
