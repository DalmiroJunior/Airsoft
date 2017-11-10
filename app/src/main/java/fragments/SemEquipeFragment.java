package fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SemEquipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SemEquipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SemEquipeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button button;
    private TextView textView;
    private RecyclerView recyclerView;
    private ArrayList<Usuario> membros_list;
    private AdapterMembros adapterMembros;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;
    private Usuario usuario;
    private DatabaseReference databaseReference, referenceMembros;

    public SemEquipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EquipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SemEquipeFragment newInstance(String param1, String param2) {
        SemEquipeFragment fragment = new SemEquipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sem_equipe, container, false);

        button = (Button) view.findViewById(R.id.criarEquipe);
        textView = (TextView) view.findViewById(R.id.textView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_membros);

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
                    if (usuario.getIdEquipe().equals("_")) {
                        button.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);


                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        carregarMenbros(usuario.getIdEquipe());

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                        adapterMembros = new AdapterMembros(getActivity(), membros_list, recyclerView);
                        recyclerView.setAdapter(adapterMembros);
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

    private void carregarMenbros(String idEquipe) {

        membros_list = new ArrayList<>();
        new GetDataFromFirebase().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        referenceMembros = FirebaseDatabase.getInstance().getReference("equipes/" + idEquipe + "/" + "membros");
        referenceMembros.keepSynced(true);
        referenceMembros.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    membros_list.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Usuario usuario = snapshot.getValue(Usuario.class);
                        Log.e("ggg",usuario.getUsuarioNome());
                        membros_list.add(usuario);

                    }
                    adapterMembros.atualiza(membros_list);

                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
