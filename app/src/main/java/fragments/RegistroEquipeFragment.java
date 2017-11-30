package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.Usuario;
import nof.airsoft.MainActivity;
import nof.airsoft.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistroEquipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistroEquipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroEquipeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button registarEquipe;
    private EditText editText_nomeEquipe;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabase;
    FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RegistroEquipeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RegistroEquipeFragment newInstance(String param1, String param2) {
        RegistroEquipeFragment fragment = new RegistroEquipeFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registro_equipe, container, false);
        registarEquipe = (Button) view.findViewById(R.id.registrarEquipe);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        editText_nomeEquipe = (EditText) view.findViewById(R.id.editText_nomeEquipe);
        registarEquipe.setOnClickListener((View.OnClickListener) getActivity());
        return inflater.inflate(R.layout.fragment_registro_equipe, container, false);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public void onClick(View view) {
        if(view == registarEquipe){
            registerTeam();

        }

    }

    private void registerTeam() {
        String nome = editText_nomeEquipe.getText().toString().trim();

        if (TextUtils.isEmpty(nome)) {
            Toast.makeText(getActivity(),"Escreva o nome por favor!",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(),"Informações Salvas!",Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getActivity(), MainActivity.class));
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
