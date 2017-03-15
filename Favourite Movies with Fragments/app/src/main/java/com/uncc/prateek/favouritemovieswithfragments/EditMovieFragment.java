package com.uncc.prateek.favouritemovieswithfragments;
/*
a. Assignment #. In Class 08
b. File Name : EditMovieFragment.java
c. Group : 6
c. Name of students: Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli

 */
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class EditMovieFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Movie movie;
    private int index;

    public EditMovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditMovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditMovieFragment newInstance(String param1, String param2) {
        EditMovieFragment fragment = new EditMovieFragment();
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
        return inflater.inflate(R.layout.fragment_edit_movie, container, false);
    }


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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void editMovie(Movie movie);
        public void setEditIndex(int index);
    }
    
    public void getEditableMovie(Movie movie){
        this.movie = movie;
    }

    public void getEditableMovieIndex(int index){
        this.index = index;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int ratingValue=0;
        final String logLevel="EditMovieActivity";
        TextView movieName = (TextView) getView().findViewById(R.id.editText_movieNameValueEdit);
        TextView description = (TextView) getView().findViewById(R.id.textView_description_valueEdit);
        Spinner genreSpinner = (Spinner) getView().findViewById(R.id.spinner_genreEdit);
        SeekBar rating = (SeekBar) getView().findViewById(R.id.seekBar_ratingEdit);
        EditText year= (EditText) getView().findViewById(R.id.editText_yearValueEdit);
        EditText imdb = (EditText) getView().findViewById(R.id.editText_imdbLinkEdit);
        Button saveChanges = (Button) getView().findViewById(R.id.button_saveChanges);

        ratingValue= rating.getProgress();
        TextView tvRatingVal = (TextView) getView().findViewById(R.id.textView_ratingValueEdit);
        tvRatingVal.setText(Integer.toString(ratingValue));

        description.setMovementMethod(new ScrollingMovementMethod());

        rating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                TextView tvRatingVal = (TextView) getView().findViewById(R.id.textView_ratingValueEdit);
                tvRatingVal.setText(String.valueOf(progress));
                Log.d(logLevel,"Seekbar value changed");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        if(movie!=null){

            //setting field values
            movieName.setText(movie.getName().toString());
            Log.d(logLevel,"movie nname set");
            description.setText(movie.getDescription().toString());
            Log.d(logLevel,"descrition set");
            String compareValue = movie.getGenre().toString();
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_genreValues, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            genreSpinner.setAdapter(adapter);
            if (!compareValue.equals(null)) {
                int spinnerPosition = adapter.getPosition(compareValue);
                genreSpinner.setSelection(spinnerPosition);
            }

            rating.setProgress(movie.getRating());
            Log.d(logLevel,"Rating set");

            year.setText(Integer.toString(movie.getYear()));
            Log.d(logLevel,"Year set");
            imdb.setText(movie.getIMDB().toString());
            Log.d(logLevel,"IMDB link set");

            Log.d(logLevel,"on click of button checking and performing edits");

            saveChanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //declare variables
                    String movieNameStr = "";
                    String descriptionStr = "";
                    String genreStr = "";
                    int ratingStr = 0;
                    int yearStr= 0;
                    String imdbLinkStr = "";
                    String logLevel = "EditMovieOnclick";
                    Log.d(logLevel,"variable set");

                    EditText movieNameET = (EditText) getView().findViewById(R.id.editText_movieNameValueEdit);
                    movieNameStr = movieNameET.getText().toString();
                    Log.d(logLevel,"Movie name "+movieNameStr);

                    EditText description = (EditText) getView().findViewById(R.id.textView_description_valueEdit);
                    descriptionStr = description.getText().toString();
                    Log.d(logLevel,descriptionStr);

                    Spinner genreSpinner = (Spinner) getView().findViewById(R.id.spinner_genreEdit);
                    genreStr = genreSpinner.getSelectedItem().toString();

                    SeekBar seek = (SeekBar) getView().findViewById(R.id.seekBar_ratingEdit);

                    ratingStr= seek.getProgress();

                    //logStep="Get year value";
                    EditText year = (EditText) getView().findViewById(R.id.editText_yearValueEdit);
                    try{
                        yearStr= Integer.parseInt(year.getText().toString());
                    }catch (Exception e){
                        Log.d(logLevel ,e.getMessage());
                        Toast.makeText(getActivity(),"Error in year",Toast.LENGTH_LONG);
                    }


                    EditText imdb = (EditText) getView().findViewById(R.id.editText_imdbLinkEdit);
                    imdbLinkStr= imdb.getText().toString();

                    Movie editMovie = new Movie();
                    editMovie.setName(movieNameStr);
                    editMovie.setDescription(descriptionStr);
                    editMovie.setGenre(genreStr);
                    editMovie.setIMDB(imdbLinkStr);
                    editMovie.setRating(ratingStr);
                    editMovie.setYear(yearStr);

                    String msg = Validator.validate(editMovie);
                    if(msg==null){
                        msg="";
                    }
                    if(msg.isEmpty()){
                        Log.d(logLevel,editMovie.toString());
                        mListener.setEditIndex(index);
                        mListener.editMovie(editMovie);
                    }
                    else{
                        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
                    }


                }
            });

        }
    }
}
