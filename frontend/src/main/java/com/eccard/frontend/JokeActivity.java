package com.eccard.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_activity);

        setUpToolbar();

        if(savedInstanceState == null) {
            setUpFragments();
        }
    }

    private void setUpToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void setUpFragments() {

        Bundle extras = getIntent().getExtras();

        if(extras != null){

            String joke = extras.getString(Intent.EXTRA_TEXT);

            if(joke != null) {
                JokeFragment jokeActivityFragment = JokeFragment.newInstance(joke);

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, jokeActivityFragment)
                        .commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
