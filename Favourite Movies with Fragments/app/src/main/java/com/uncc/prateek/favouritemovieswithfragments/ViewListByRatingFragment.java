package com.uncc.prateek.favouritemovieswithfragments;
/*
a. Assignment #. In Class 08
b. File Name : ViewListByRatingFragment.java
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


public class ViewListByRatingFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ArrayList<Movie> movieList;

    private OnFragmentInteractionListener mListener;

    String logLevel = "ViewByRating";
    static int i=0;


    public ViewListByRatingFragment() {
        // Required empty public constructor
        movieList = new ArrayList<Movie>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewListByRatingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewListByRatingFragment newInstance(String param1, String param2) {
        ViewListByRatingFragment fragment = new ViewListByRatingFragment();
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
        return inflater.inflate(R.layout.fragment_view_list_by_rating, container, false);
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
        TextView tvName= (TextView) getView().findViewById(R.id.textView_viewRatingTitle);
        TextView tvDescription = (TextView) getView().findViewById(R.id.textView_ViewRatingDescription);
        TextView tvGenre= (TextView) getView().findViewById(R.id.textView_ViewRatingGenre);
        TextView tvRating = (TextView) getView().findViewById(R.id.textView_ViewRatingRatingValue);
        TextView tvYear = (TextView) getView().findViewById(R.id.textView_ViewRatingYear);
        TextView tvImdb = (TextView) getView().findViewById(R.id.textView_ViewRatingIMDBValue);


        Log.d(logLevel,"onClick inside ViewListByRating");


        switch (v.getId()){
            case R.id.button_finishRating:{
                getActivity().getFragmentManager().popBackStack();
                break;
            }
            case R.id.button_firstMovieByRating:{
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
            case R.id.button_lastMovieByRating:{
                Movie movie = movieList.get(movieList.size()-1);
                tvName.setText(movie.getName().toString());
                tvDescription.setText(movie.getDescription().toString());
                tvGenre.setText(movie.getGenre().toString());
                tvRating.setText(""+movie.getRating());
                tvYear.setText(""+movie.getYear());
                tvImdb.setText(movie.getIMDB().toString());
                break;
            }
            case R.id.button_previousMovieByRating:{
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
            case R.id.button_nextMovieByRating:{
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

    public void getMoviesInRating(ArrayList<Movie> movies){
        this.movieList = movies;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void insideByRating(String txt);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(logLevel,"------Inside layout_view_list_by_rating----");
        TextView tvName= (TextView) getView().findViewById(R.id.textView_viewRatingTitle);
        TextView tvDescription = (TextView) getView().findViewById(R.id.textView_ViewRatingDescription);
        TextView tvGenre= (TextView) getView().findViewById(R.id.textView_ViewRatingGenre);
        TextView tvRating = (TextView) getView().findViewById(R.id.textView_ViewRatingRatingValue);
        TextView tvYear = (TextView) getView().findViewById(R.id.textView_ViewRatingYear);
        TextView tvImdb = (TextView) getView().findViewById(R.id.textView_ViewRatingIMDBValue);

        i=0;
        Button buttonFinish = (Button) getView().findViewById(R.id.button_finishRating);
        ImageButton buttonFirst = (ImageButton) getView().findViewById(R.id.button_firstMovieByRating);
        ImageButton buttonNext = (ImageButton) getView().findViewById(R.id.button_nextMovieByRating);
        ImageButton buttonPrevious = (ImageButton) getView().findViewById(R.id.button_previousMovieByRating);
        ImageButton buttonLast = (ImageButton) getView().findViewById(R.id.button_lastMovieByRating);


        buttonFinish.setOnClickListener(this);
        buttonFirst.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonPrevious.setOnClickListener(this);
        buttonLast.setOnClickListener(this);

        if(movieList!=null){
            Movie movie = movieList.get(i);
            tvName.setText(movie.getName().toString());
            tvDescription.setText(movie.getDescription().toString());
            tvGenre.setText(movie.getGenre().toString());
            tvRating.setText(""+movie.getRating());
            tvYear.setText(""+movie.getYear());
            tvImdb.setText(movie.getIMDB().toString());
        }else{
            Toast.makeText(getActivity(), "No movies", Toast.LENGTH_SHORT).show();

        }
    }
}
