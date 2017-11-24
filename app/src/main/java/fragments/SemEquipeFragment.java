package fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapters.AdapterMembros;
import model.Usuario;
import nof.airsoft.R;
import nof.airsoft.RegistroEquipeActivity;
import utils.GetDataFromFirebase;

public class SemEquipeFragment extends Fragment {
    private Button button;

    private Usuario usuario;
    private DatabaseReference databaseReference, referenceMembros;

    public SemEquipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sem_equipe, container, false);

        button = (Button) view.findViewById(R.id.criarEquipe);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), RegistroEquipeActivity.class));


            }


        });
        verificarEquipe();
        return view;

    }

    private void verificarEquipe() {

        String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        new GetDataFromFirebase().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios/" + idUsuario);
        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    usuario = dataSnapshot.getValue(Usuario.class);

                    if (!usuario.getIdEquipe().isEmpty()) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, new MinhaEquipeFragment()).commit();

                    }

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
