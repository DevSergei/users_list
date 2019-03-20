package me.bkkn.users;

import androidx.appcompat.app.AppCompatActivity;
import me.bkkn.users.users.UserDataBase;
import me.bkkn.users.users.UsersFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import static me.bkkn.users.users.UsersFragment.GITHUB;
import static me.bkkn.users.users.UsersFragment.STACK_OVERFLOW;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UserDataBase base = new UserDataBase(getBaseContext());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, UsersFragment.newInstance(GITHUB))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, UsersFragment.newInstance(STACK_OVERFLOW))
                    .addToBackStack("backstack")
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }

}
