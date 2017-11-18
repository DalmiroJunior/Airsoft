package fragments;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapters.AdapterEquipes;
import adapters.AdapterMembros;
import model.Equipe;
import model.Usuario;
import nof.airsoft.R;
import utils.GetDataFromFirebase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EquipesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EquipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EquipesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Equipe equipe;
    private ListView listViewEquipes;
    private ArrayList<Equipe> equipes;
    private OnFragmentInteractionListener mListener;
    private FragmentManager fragmentManager;
    private DatabaseReference databaseReference, referenceEquipe;
    private AdapterEquipes adapterEquipes;




    public EquipesFragment() {
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
    public static EquipesFragment newInstance(String param1, String param2) {
        EquipesFragment fragment = new EquipesFragment();
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

            ListView listaEquipe = (ListView) getActivity().findViewById(R.id.listaEquipe);


            carregarEquipe(equipe.getEquipeNome());

            listViewEquipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Equipe equipe = equipes.get(position);

                    final android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content, new EquipeFragment()).addToBackStack(null).commit();
                }
            });
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void carregarEquipe(String idEquipe) {

        equipes = new ArrayList<>();
        new GetDataFromFirebase().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        referenceEquipe = FirebaseDatabase.getInstance().getReference("equipes/" + idEquipe);
        referenceEquipe.keepSynced(true);
        referenceEquipe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    equipes.clear();
                    Log.v("", "Equipes " + dataSnapshot);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Equipe equipe = snapshot.getValue(Equipe.class);
                        Log.e("aaa",equipe.getEquipeNome());
                        equipes.add(equipe);
                    }
                    adapterEquipes.atualiza(equipes);

                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
