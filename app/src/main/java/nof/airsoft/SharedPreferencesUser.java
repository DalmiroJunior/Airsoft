package nof.airsoft;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

import java.io.StringReader;
import java.util.Map;
import java.util.Set;

import model.Usuario;



public class SharedPreferencesUser {

    private static final String CHAVE_EQUIPE = "idEquipe";
    private static final String CHAVE_EMAIL = "email";
    //ATRIBUTOS
    private Context contexto;
    private SharedPreferences preferencias;
    private final String NOME_ARQUIVO = "STUDYNG.PREFERENCES";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "id";
    private final String CHAVE_NOME = "nome";
    private final String CHAVE_NOMEJOG = "nomeJog";
    private final String CHAVE_CONTATO = "contato";
    private final String CHAVE_ENDERECO = "endereco";

    public SharedPreferencesUser(Context contextoParametro) {
        contexto = contextoParametro;
        preferencias = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferencias.edit();
    }

    //SALVANDO USUARIO
    public void salvarUsuarioPreferences(String ident, String nome, String contato, String endereco, String idEquipe) {
        editor.putString(CHAVE_IDENTIFICADOR, ident);
        editor.putString(CHAVE_NOME, nome);
        editor.putString(CHAVE_CONTATO, contato);
        editor.putString(CHAVE_ENDERECO, endereco);
        editor.putString(CHAVE_EQUIPE, idEquipe);
        editor.apply();
    }

    public Usuario getUsuario(){
        Usuario usuario = new Usuario();
        usuario.setIdEquipe(getIdEquipe());
        usuario.setUsuarioNome(getUsuarioNome());
        return usuario;
    }

    private String getIdEquipe() {
        return preferencias.getString(CHAVE_EQUIPE, "");
    }

    //RECUPERANDO
    public String getIdentificador() {
        return preferencias.getString(CHAVE_IDENTIFICADOR, "");
    }
    public String getUsuarioNome() {
        return preferencias.getString(CHAVE_NOME, "");
    }
    public String getUsuarioNomeJog() {
        return preferencias.getString(CHAVE_NOMEJOG, "");
    }
    public String getUsuarioContato() {
        return preferencias.getString(CHAVE_CONTATO, "");
    }
    public String getUsuarioEndereco() {
        return preferencias.getString(CHAVE_ENDERECO, "");
    }

    public boolean possuiEquipe() {
        if (getUsuarioNomeJog().contains(getUsuarioNome())){
            return true;
        }
        return false;
    }

    public void salvaEmail(String email) {
        editor.putString(CHAVE_EMAIL, email.replace(".",""));
        editor.apply();
    }

    public String getEmail() {
        return preferencias.getString(CHAVE_EMAIL, "");
    }
}