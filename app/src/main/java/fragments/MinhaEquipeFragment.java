package fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import model.Usuario;
import nof.airsoft.R;


public class MinhaEquipeFragment extends Fragment {

    private RecyclerView recyclerViewParticipantes;
    private ArrayList<Usuario> usuarios;

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
        recyclerViewParticipantes = (RecyclerView) getView().findViewById(R.id.recycler_participantes);
        recyclerViewParticipantes.setLayoutManager(new GridLayoutManager(getContext(), 4));
       // usuarios Pega do firebase
    }
}
