package com.example.frag_exo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    /**
     * the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
     * These are used to pass values from the activity to the fragment ie. when creating
     * the fragment, dynamically, in the activity we pass in values that we want to
     * retreive in the fragment code.
     * Used in newInstance() method
     */
    // Uri in string form
    private static final String URI_STRING = "param1";

    // TODO: Rename and change types of parameters
    // Variables used to hold retreived arguments passed to the fragment on instantiation
    // used in onCreate() method

    // Uri in string form
    private String sUriString;

    private OnFragmentInteractionListener mListener;
//s<<<<<
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

   /*public String sourceToPlay()
    {
        return "asset:///test_vid.mp4";

        if (SORT.equals("Quick Sort"))
            return "asset:///quicksort.mp4";
        if (SORT.equals("Merge Sort"))
            return "file:///Videos/Backpropagation_calculus_Deep_learning_chapter4.mp4";
            switch (SORT)
            {
                case "Quick Sort":
                    return "vid1";
                case "Merge Sort":
                    return "vid2";
                case "Quick Sort":
                    return ;
                case "Quick Sort":
                    return ;
                case "Quick Sort":
                    return ;
                case "Quick Sort":
                    return ;
            }
        return "";
    }*/

    private void initializePlayer() {
        if (player == null)
        {
            player = ExoPlayerFactory.newSimpleInstance(getContext());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
        ExtractorMediaSource mediaSource = buildMediaSource(Uri.parse(sUriString));
        player.prepare(mediaSource, true, false);
    }

    private ExtractorMediaSource buildMediaSource(Uri uri)
    {

        // A DataSource.Factory that produces DefaultDataSource instances that delegate to DefaultHttpDataSources for non-file/asset/content URIs.
        DefaultDataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(/*this is how you get context in a fragment*/getActivity(), Util.getUserAgent(getActivity(), "fragexo"));
        // Creates a new factory for ExtractorMediaSources; Sets the factory for Extractors to process the media stream ;Returns a new ExtractorMediaSource using the current parameters.
        return ( new ExtractorMediaSource.Factory(dataSourceFactory).setExtractorsFactory(new DefaultExtractorsFactory()).createMediaSource(uri));
    }


    private void releasePlayer()
    {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    // Gives you a full screen by hiding the ui visuals
    @SuppressLint("InlinedApi")
    private void hideSystemUi()
    {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

//f<<<<<
//TODO:
    /** Required empty public constructor
     Use this to create a fragment instance without passing any arguments
     */
    public PlayerFragment()
    {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param s_uUri Parameter 1.
     * @return A new instance of fragment PlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerFragment newInstance( String s_uUri ) // Uri in string form

    {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putString(URI_STRING, s_uUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            sUriString = getArguments().getString(URI_STRING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Defines the xml file for the fragment
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        // Setup any handles to view objects here
        playerView = view.findViewById(R.id.video_view);
    }

//s<<<<<<<<<<<<<<<<<<<<<<<<<
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //showSystemUi();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    //f<<<<<<<<<<<<<<<<<<<<<<<<<

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * The activity that hosts this fragment is passed into this function when the system calls this callback in order to add frag to activity
     * By casting the activity (context) to a OnFragmentInteractionListener, we are checking if the activity implemented the OnFragmentInteractionListener interface
     * If the activity hasn't implemented the interface, then the fragment throws a ClassCastException.
     * On success, the mListener member holds a reference to activity's implementation of OnFragmentInteractionListener, so that fragment A can share events with the activity
     * by calling methods defined by the OnFragmentInteractionListener interface.
     */
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
