package rabbidcompany.liveat500px.activity;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Toast;

import rabbidcompany.liveat500px.R;
import rabbidcompany.liveat500px.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout01;
    ActionBarDrawerToggle actionBarDrawerToggle01; //This is a hamburger button.
    Toolbar toolBar01; //This is a bar inside an activity and similar to an ActionBar.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstance();

        //The first creation
        if(savedInstanceState == null){
            //Add a newly instantiated fragment into a content container.
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ContentContainerID01, MainFragment.newInstance())
                    .commit();
        }
    }

    private void initInstance() {
        toolBar01 = (Toolbar) findViewById(R.id.ToolBarID01);
        setSupportActionBar(toolBar01); //Set a tool bar to act like an ActionBar.
        drawerLayout01 = (DrawerLayout) findViewById(R.id.DrawerLayoutID01);
        actionBarDrawerToggle01 = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout01,
                R.string.open_drawer,
                R.string.close_drawer);
        drawerLayout01.addDrawerListener(actionBarDrawerToggle01);
        getSupportActionBar().setHomeButtonEnabled(true); //Enable the home button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Go back a single level.
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle01.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Call the onConfigurationChanged method for the hamburger button too.
        actionBarDrawerToggle01.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle01.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
