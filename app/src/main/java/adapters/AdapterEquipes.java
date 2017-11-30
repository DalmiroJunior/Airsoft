package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import model.Equipe;
import model.Usuario;
import nof.airsoft.R;

/**
 * Created by Dalmiro Junior on 17/11/2017.
 */

public class AdapterEquipes extends ArrayAdapter {
    private List<Equipe> equipes;
    private FragmentActivity activity;


    public AdapterEquipes(FragmentActivity activity, ArrayList<Equipe> equipes) {
        super(activity, R.layout.adapter_equipes, equipes);
        this.activity =activity;
        this.equipes= equipes;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.adapter_equipes, null, true);

        TextView nomeEquipe;
        nomeEquipe = (TextView) view.findViewById(R.id.text_nome_equipe);



        final Equipe  equipe = equipes.get(position);
        nomeEquipe.setText(equipe.getEquipeNome());



    return view;
    }

    public void atualiza(ArrayList<Equipe> equipes) {
        this.equipes = equipes;
        this.notifyDataSetChanged();


    }
}