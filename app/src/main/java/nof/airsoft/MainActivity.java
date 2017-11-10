package nof.airsoft;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import fragments.EquipesFragment;
import fragments.JogosMarcadosFragment;
import fragments.MinhaEquipeFragment;
import fragments.SemEquipeFragment;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.customToolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_equipes:
                        transaction.replace(R.id.content, new EquipesFragment()).addToBackStack(null).commit();
                        break;

                    case R.id.navigation_minha_equipe:
                        transaction.replace(R.id.content, new SemEquipeFragment()).addToBackStack(null).commit();

                        break;
                    case R.id.navigation_jogos_marcados:
                        transaction.replace(R.id.content, new JogosMarcadosFragment()).addToBackStack(null).commit();
                        break;
                   

                }

                return true;


            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.profile: {
                startActivity(new Intent(this, ProfileActivity.class));
            }
        }
        return false;
    }
}
