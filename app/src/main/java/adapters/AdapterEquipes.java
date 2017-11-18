package adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import model.Equipe;
import model.Usuario;
import nof.airsoft.R;

/**
 * Created by Dalmiro Junior on 17/11/2017.
 */

public class AdapterEquipes extends RecyclerView.Adapter<AdapterEquipes.ViewHolder> {
    private List<Equipe> equipes;
    private FragmentActivity activity;

    public AdapterEquipes(FragmentActivity activity, ArrayList<Equipe> equipes) {
        this.activity = activity;
        this.equipes = equipes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_membros, parent, false)));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Equipe equipe = equipes.get(position);

        try {
            holder.textViewNome.setText(equipe.getEquipeNome());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return equipes.size();
    }

    public void atualiza(ArrayList<Equipe> equipes) {
        this.equipes = equipes;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNome = (TextView) itemView.findViewById(R.id.text_nome);
        }
    }
}