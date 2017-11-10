package model;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import utils.ConfiguracoesFirebase;


/**
 * Created by Dalmiro Junior on 28/09/2017.
 */

public class Usuario implements Serializable {
    private String idUsuario;
    public String usuarioNome;
    private String usuarioContato;
    private String usuarioEndereco;
    private String idEquipe;


    public Usuario(String idUsuario, String nome, String contato, String endereco, String idEquipe) {
        this.idUsuario = idUsuario;
        this.usuarioNome = nome;
        this.usuarioContato = contato;
        this.usuarioEndereco = endereco;
        this.idEquipe = idEquipe;


    }

    public Usuario() {

    }


    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public String getUsuarioContato() {
        return usuarioContato;
    }

    public void setUsuarioContato(String usuarioContato) {
        this.usuarioContato = usuarioContato;
    }

    public String getUsuarioEndereco() {
        return usuarioEndereco;
    }

    public void setUsuarioEndereco(String usuarioEndereco) {
        this.usuarioEndereco = usuarioEndereco;
    }

    public String getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(String idEquipe) {
        this.idEquipe = idEquipe;
    }

    public void salvarUsuario() {
        DatabaseReference reference = ConfiguracoesFirebase.getFirebase();
        reference.child("usuarios/").child(String.valueOf(getIdUsuario())).setValue(this);



    }


    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

//
//    @Override
//    public Stream<Usuario> stream() {
//        return null;
//    }
}