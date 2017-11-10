package adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import model.Usuario;
import nof.airsoft.R;

/**
 * Created by matheus on 10/11/17.
 */

public class AdapterMembros extends RecyclerView.Adapter<AdapterMembros.ViewHolder> {
    private List<Usuario> membros_list;
    private FragmentActivity activity;

    public AdapterMembros(FragmentActivity activity, ArrayList<Usuario> membros_list, RecyclerView recyclerView) {
        this.activity = activity;
        this.membros_list = membros_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_membros, parent, false)));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Usuario usuario = membros_list.get(position);

        try {
            holder.textViewNome.setText(usuario.getUsuarioNome());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return membros_list.size();
    }

    public void atualiza(ArrayList<Usuario> membros_list) {
        this.membros_list = membros_list;
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
