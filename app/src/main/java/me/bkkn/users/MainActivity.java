package me.bkkn.users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import me.bkkn.users.user.UserDataBase;
import me.bkkn.users.user.UsersFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jakewharton.rxbinding3.appcompat.RxSearchView;

import java.util.concurrent.TimeUnit;

import static me.bkkn.users.user.UsersFragment.GITHUB;
import static me.bkkn.users.user.UsersFragment.STACK_OVERFLOW;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchView = findViewById(R.id.search_view);

        RxSearchView.queryTextChangeEvents(searchView)
                .debounce(5000, TimeUnit.MILLISECONDS)
                .subscribe(unit-> Log.d("DDD",searchView.getQuery().toString() ));

//        UserDataBase base = new UserDataBase(getBaseContext());

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
