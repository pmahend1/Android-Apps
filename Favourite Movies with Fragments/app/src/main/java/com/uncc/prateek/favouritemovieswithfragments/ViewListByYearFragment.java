package com.uncc.prateek.favouritemovieswithfragments;
/*
a. Assignment #. In Class 08
b. File Name : ViewListByYearFragment.java
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ViewListByYearFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<Movie> movieList;

    String logLevel = "ViewByYear";
    static int i=0;

    public ViewListByYearFragment() {
        // Required empty public constructor
        movieList = new ArrayList<Movie>();
    }


    public static ViewListByYearFragment newInstance(String param1, String param2) {
        ViewListByYearFragment fragment = new ViewListByYearFragment();
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
        return inflater.inflate(R.layout.fragment_view_list_by_year, container, false);
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

    @Override
    public void onClick(View v) {
        TextView tvName= (TextView) getView().findViewById(R.id.textView_viewYearTitle);
        TextView tvDescription = (TextView) getView().findViewById(R.id.textView_ViewYearDescription);
        TextView tvGenre= (TextView) getView().findViewById(R.id.textView_ViewYearGenre);
        TextView tvRating = (TextView) getView().findViewById(R.id.textView_ViewYearRating);
        TextView tvYear = (TextView) getView().findViewById(R.id.textView_ViewYearYear);
        TextView tvImdb = (TextView) getView().findViewById(R.id.textView_ViewYearIMDB);

        Log.d(logLevel,"onClick inside ViewListByYear");

        switch (v.getId()){
            case R.id.button_finishByYear:{
                getActivity().getFragmentManager().popBackStack();
                break;
            }
            case R.id.button_firstMovieByYear:{
                i=0;
                Movie movie = movieList.get(i);
                tvName.setText(movie.getName().toString());
                tvDescription.setText(movie.getDescription().toString());
                tvGenre.setText(movie.getGenre().toString());
                tvRating.setText(""+movie.getRating());
                tvYear.setText(""+movie.getYear());
                tvImdb.setText(movie.getIMDB().toString());
                break;
            }
            case R.id.button_lastMovieByYear:{
                Movie movie = movieList.get(movieList.size()-1);
                tvName.setText(movie.getName().toString());
                tvDescription.setText(movie.getDescription().toString());
                tvGenre.setText(movie.getGenre().toString());
                tvRating.setText(""+movie.getRating());
                tvYear.setText(""+movie.getYear());
                tvImdb.setText(movie.getIMDB().toString());
                break;
            }
            case R.id.button_previousMovieByYear:{
                if(i>0){
                    i=i-1;
                    Movie movie = movieList.get(i);
                    tvName.setText(movie.getName().toString());
                    tvDescription.setText(movie.getDescription().toString());
                    tvGenre.setText(movie.getGenre().toString());
                    tvRating.setText(""+movie.getRating());
                    tvYear.setText(""+movie.getYear());
                    tvImdb.setText(movie.getIMDB().toString());
                    break;

                }else{
                    Toast.makeText(getActivity(),"At first element already",Toast.LENGTH_SHORT).show();
                    break;
                }

            }
            case R.id.button_nextMovieByYear:{
                if(i<movieList.size()-1){
                    i=i+1;
                    Movie movie = movieList.get(i);
                    tvName.setText(movie.getName().toString());
                    tvDescription.setText(movie.getDescription().toString());
                    tvGenre.setText(movie.getGenre().toString());
                    tvRating.setText(""+movie.getRating());
                    tvYear.setText(""+movie.getYear());
                    tvImdb.setText(movie.getIMDB().toString());
                    break;

                }else{
                    Toast.makeText(getActivity(),"At Last element already",Toast.LENGTH_SHORT).show();
                    break;
                }
            }

        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void insideByYear(String txt);
    }

    public void getMoviesInYear(ArrayList<Movie> movies){
        this.movieList = movies;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(logLevel,"------Inside----");
        TextView tvName= (TextView) getView().findViewById(R.id.textView_viewYearTitle);
        TextView tvDescription = (TextView) getView().findViewById(R.id.textView_ViewYearDescription);
        TextView tvGenre= (TextView) getView().findViewById(R.id.textView_ViewYearGenre);
        TextView tvRating = (TextView) getView().findViewById(R.id.textView_ViewYearRating);
        TextView tvYear = (TextView) getView().findViewById(R.id.textView_ViewYearYear);
        TextView tvImdb = (TextView) getView().findViewById(R.id.textView_ViewYearIMDB);

        i=0;
        Button buttonFinish = (Button) getView().findViewById(R.id.button_finishByYear);
        ImageButton buttonFirst = (ImageButton) getView().findViewById(R.id.button_firstMovieByYear);
        ImageButton buttonNext = (ImageButton) getView().findViewById(R.id.button_nextMovieByYear);
        ImageButton buttonPrevious = (ImageButton) getView().findViewById(R.id.button_previousMovieByYear);
        ImageButton buttonLast = (ImageButton) getView().findViewById(R.id.button_lastMovieByYear);


        buttonFinish.setOnClickListener(this);
        buttonFirst.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonPrevious.setOnClickListener(this);
        buttonLast.setOnClickListener(this);

        Movie movie = movieList.get(i);
        tvName.setText(movie.getName().toString());
        tvDescription.setText(movie.getDescription().toString());
        tvGenre.setText(movie.getGenre().toString());
        tvRating.setText(""+movie.getRating());
        tvYear.setText(""+movie.getYear());
        tvImdb.setText(movie.getIMDB().toString());
    }


}
