package com.uncc.prateek.favouritemovieswithfragments;
/*
a. Assignment #. In Class 08
b. File Name : AddMovieFragment.java
c. Group : 6
c. Name of students: Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli

 */
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;


public class AddMovieFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddMovieFragment() {
        // Required empty public constructor
    }

    public static AddMovieFragment newInstance(String param1, String param2) {
        AddMovieFragment fragment = new AddMovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_movie, container, false);
    }

/*    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().findViewById(R.id.button_addMovieOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName = "";
                String description = "";
                String genre = "";
                int rating = 0;
                int year= 0;
                String imdbLink = "";
                String logLevel = "Add Movie";
                final String MovieLIST="MOVIELIST";
                String logStep = "";
                EditText editText_movieNameVal = (EditText) getView().findViewById(R.id.editText_movieNameValue);
                movieName = editText_movieNameVal.getText().toString();
                Log.d(logLevel,"Movie name "+movieName);

                EditText editTextDesc = (EditText) getView().findViewById(R.id.textView_description_value);
                description = editTextDesc.getText().toString();
                Log.d(logLevel,description);
                final Spinner spinnerGenre  = (Spinner) getView().findViewById(R.id.spinner_genre);
                genre = spinnerGenre.getSelectedItem().toString();

                SeekBar seek = (SeekBar) getView().findViewById(R.id.seekBar_rating);
                rating= seek.getProgress();

                logStep="Get year value";
                EditText editTextYear = (EditText) getView().findViewById(R.id.editText_yearValue);
                try{
                    year= Integer.parseInt(editTextYear.getText().toString());
                }catch (Exception e){
                    Log.d(logLevel ,logStep+e.getMessage());
                    Toast.makeText(getActivity(),"Error in year",Toast.LENGTH_LONG);
                }


                EditText editTextImdbLink = (EditText) getView().findViewById(R.id.editText_imdbLink);
                imdbLink= editTextImdbLink.getText().toString();

                Movie addMovie = new Movie();
                addMovie.setName(movieName);
                addMovie.setDescription(description);
                addMovie.setGenre(genre);
                addMovie.setIMDB(imdbLink);
                addMovie.setRating(rating);
                addMovie.setYear(year);
                logStep = "Print movie obj ";

                String msg = Validator.validate(addMovie);
                Log.d(logLevel,"|"+msg+"|");
                if(msg==null){
                    msg="";
                }
                if(msg.isEmpty() || msg==null){
                    Log.d(logLevel,addMovie.toString());
                    mListener.addMovie(addMovie);
                    getActivity().getFragmentManager().popBackStack();
                }
                else{
                    Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public interface OnFragmentInteractionListener {
        public void addMovie(Movie movie);
    }
}
