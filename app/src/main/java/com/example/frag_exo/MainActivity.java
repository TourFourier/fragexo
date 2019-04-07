package com.example.frag_exo;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

// (1)Activity implements the fragment listener to handle events
public class MainActivity extends AppCompatActivity implements PlayerFragment.OnFragmentInteractionListener
{
    // (2)Can be any fragment, `PlayerFragment` is just an example
    PlayerFragment fragment;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // (3)Get access to the player view fragment by id
        fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentHolder);

       // InitializePlayerFragment(savedInstanceState);
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {



    }


    public void onClickButton(View v)
    {
        InitializePlayerFragment(b);

    }
        // Using fragment manager  to create a fragment transaction and then adding the fragment to the avctivity
        // Let's first dynamically add a fragment into a frame container
    private void InitializePlayerFragment(Bundle savedInstanceState)
    {
        if (savedInstanceState == null)
        {
            // Begin the transaction
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // Replace the contents of the container with the new fragment
            // Give it a tag so later we can lookup the fragment by tag
            ft.replace(R.id.fragmentHolder, new PlayerFragment(), "TAG");// TAG is a handle to the fragment instance
            // or ft.add(R.id.fragmentHolder, new PlayerFragment());
            // Complete the changes added above
            ft.commit();

            /*Look up example:
            DemoFragment fragmentDemo = (DemoFragment) getSupportFragmentManager().findFragmentByTag("TAG");
             */

        }
    }
}


