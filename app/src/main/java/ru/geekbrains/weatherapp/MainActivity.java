package ru.geekbrains.weatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import ru.geekbrains.weatherapp.fragments.ShowCityFragment;
import ru.geekbrains.weatherapp.ui.gallery.GalleryFragment;
import ru.geekbrains.weatherapp.ui.home.HomeFragment;


public class MainActivity extends AppCompatActivity implements SelectCityListener{

    private AppBarConfiguration mAppBarConfiguration;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view
                , "Replace with your own action"
                , Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        HomeFragment fragment = new HomeFragment();
        setFragment(fragment);
        navigationView.setCheckedItem(R.id.nav_home);

        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment fragment1;
            if (item.getItemId() == R.id.nav_gallery) {
                fragment1 = new GalleryFragment();
                navigationView.setCheckedItem(R.id.nav_gallery);
            } else {
                fragment1 = new HomeFragment();
                navigationView.setCheckedItem(R.id.nav_home);
            }
            setFragment(fragment1);
            drawer.closeDrawers();
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    @Override
    public void onOpenFragmentWeatherMain(String string) {

        Bundle bundle = new Bundle();
        bundle.putString(ShowCityFragment.KEY, string);

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
            if (fragment instanceof HomeFragment) {
                Fragment fragmentReplace;
                fragmentReplace = new ShowCityFragment();
                fragmentReplace.setArguments(bundle);
                fm.beginTransaction()
                        .replace(R.id.fragmentContainer, fragmentReplace, ShowCityFragment.TAG)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(ShowCityFragment.TAG)
                        .commit();
        }
    }
}