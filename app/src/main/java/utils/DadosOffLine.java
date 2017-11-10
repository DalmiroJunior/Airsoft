package utils;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by matheus on 10/11/17.
 */

public class DadosOffLine extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);



    }
}
