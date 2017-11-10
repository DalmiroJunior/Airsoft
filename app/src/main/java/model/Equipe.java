package model;

import com.google.firebase.database.DatabaseReference;

import utils.ConfiguracoesFirebase;

/**
 * Created by Dalmiro Junior on 05/10/2017.
 */

public class Equipe {
    private String equipeId;
    private String equipeNome;
    private String equipeLiderId;


    public Equipe(String equipeId, String equipeNome, String equipeLiderId) {
        super();
        this.equipeId = equipeId;
        this.equipeNome = equipeNome;
        this.equipeLiderId = equipeLiderId;


    }

    public String getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(String equipeId) {
        this.equipeId = equipeId;
    }

    public String getEquipeNome() {
        return equipeNome;
    }

    public void setEquipeNome(String equipeNome) {
        this.equipeNome = equipeNome;
    }

    public String getEquipeLiderId() {
        return equipeLiderId;
    }

    public void setEquipeLiderId(String equipeLiderId) {
        this.equipeLiderId = equipeLiderId;
    }


    public void criarEquipe(Usuario usuario) {
        DatabaseReference reference = ConfiguracoesFirebase.getFirebase();
        reference.child("equipes/").child(String.valueOf(getEquipeId())).child("dados/").setValue(this);

        //adiciona o membro adm que criou a equipe
        DatabaseReference reference2 = ConfiguracoesFirebase.getFirebase();

        reference2.child("equipes/").child(String.valueOf(getEquipeId())).child("membros/").child(usuario.getIdUsuario()).setValue(usuario);


    }

    public void entrar(Usuario usuario, String idEquipe) {
        DatabaseReference reference2 = ConfiguracoesFirebase.getFirebase();

        reference2.child("equipes/").child(idEquipe).child("membros/").child(usuario.getIdUsuario()).setValue(usuario);


    }


}