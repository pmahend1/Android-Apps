package com.uncc.prateek.favouritemovieswithfragments;
/*
a. Assignment #. In Class 08
b. File Name : MyFavoriteMovies.java
c. Group : 6
c. Name of students: Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli

 */
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyFavoriteMovies.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyFavoriteMovies#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFavoriteMovies extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String logLevel= "FavMovies";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    AlertDialog alertDialog;

    private ArrayList<Movie> movies;

    private OnFragmentInteractionListener mListener;

    public MyFavoriteMovies() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFavoriteMovies.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFavoriteMovies newInstance(String param1, String param2) {
        MyFavoriteMovies fragment = new MyFavoriteMovies();
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
        return inflater.inflate(R.layout.fragment_my_favorite_movies, container, false);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().findViewById(R.id.button_addMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().beginTransaction().add(R.id.container,new MyFavoriteMovies(),"tag_add_movie").commit();
                mListener.goToNextFragment();
                Log.d("My fav frag","Step : add");
            }
        });


        getView().findViewById(R.id.button_editMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((movies!=null)){
                    if(movies.size()>0){
                        final List<CharSequence> moviesList = new ArrayList<CharSequence>();
                        //new String[100];
                        Log.d(logLevel,"button_editMovie");

                        for (int i = 0; i < movies.size(); i++) {
                            Movie m = movies.get(i);
                            if(m!=null){
                                try{
                                    moviesList.add(movies.get(i).getName().toString());
                                    Log.d(logLevel,"button_editMovie for loop");
                                    Log.d(logLevel+"Movies i",""+moviesList.get(i).toString());
                                    Log.d(logLevel+"get i",""+movies.get(i).getName().toString());
                                }catch (Exception e){
                                    Log.d("favFra","Exc "+e.getMessage());
                                }

                            }

                        }

                        final CharSequence[] movies1 = moviesList.toArray(new CharSequence[moviesList.size()]);


                        String title = "Choose a Movie";
                        AlertDialog.Builder alertDialogBuilder =
                                new AlertDialog.Builder(getActivity());




                        alertDialogBuilder.setTitle("Pick Movie")
                                .setItems(movies1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Log.d(logLevel,"onClik of alert /dialog intf Edit");
                                        EditMovieFragment editMovieFrag = new EditMovieFragment();
                                        Movie movieTo = movies.get(i);
                                        editMovieFrag.getEditableMovieIndex(i);
                                        editMovieFrag.getEditableMovie(movieTo);
                                        getActivity().getFragmentManager().beginTransaction().replace(R.id.container,editMovieFrag,"tag_edit_movie").addToBackStack(null).commit();
                                        Log.d("Edit","started edit movie fragment");
                                    }
                                });

                        alertDialog = alertDialogBuilder.create();
                        if(!alertDialog.isShowing()){
                            alertDialog.show();
                        }
                        //getAlertDialog(movies, title);
                        Log.d(logLevel,"button_editMovie show alert");
                    }
                    else{
                        Toast.makeText(getActivity(),"No favourite movies.Nothing to be edited.",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getActivity(),"No favourite movies.Nothing to be edited.",Toast.LENGTH_LONG).show();
                }
            }
        });


        getView().findViewById(R.id.button_deleteMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movies.size()>0 && (movies!=null)){
                    Log.d("button_deleteMovie",movies.toString());
                    final List<CharSequence> moviesList = new ArrayList<CharSequence>();
                    Log.d(logLevel,"button_deleteMovie");

                    for (int i = 0; i < movies.size(); i++) {
                        Movie m = movies.get(i);
                        if(m!=null){
                            try{
                                moviesList.add(movies.get(i).getName().toString());
                                Log.d(logLevel,"button_editMovie for loop");
                                Log.d(logLevel+"Movies i",""+moviesList.get(i).toString());
                                Log.d(logLevel+"get i",""+movies.get(i).getName().toString());
                            }catch (Exception e){
                                Log.d("favFra","Exc "+e.getMessage());
                            }

                        }
                    }

                    CharSequence[] movies2 = moviesList.toArray(new CharSequence[moviesList.size()]);

                    String title = "Pick a Movie";
                    AlertDialog.Builder alertDialogBuilder =
                            new AlertDialog.Builder(getActivity());

                    alertDialogBuilder.setTitle(title)
                            .setItems(movies2, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.d(logLevel,"onClik of Delete alert /dialog intf");
                                    String name= (String) moviesList.get(i);

                                    mListener.setDeleteIndex(i);
                                    //movies.remove(i);
                                    Toast.makeText(getActivity(),"Movie "+ name +" deleted",Toast.LENGTH_LONG).show();
                                }
                            });

                    alertDialog = alertDialogBuilder.create();
                    if(!alertDialog.isShowing()){
                        alertDialog.show();
                    }
                    //getAlertDialog(movies, title);
                    Log.d(logLevel,"button_deleteMovie show alert");

                }else{
                    Toast.makeText(getActivity(),"No favourite movies.Nothing to be deleted.",Toast.LENGTH_LONG).show();
                }
            }
        });


        getView().findViewById(R.id.button_showListByRating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movies.size()>0){
                    Comparator<Movie> comp = new Comparator<Movie>() {

                        @Override
                        public int compare(Movie movie, Movie t1) {

                            //Descending
                            if(movie.getRating()<t1.getRating()){
                                return 1;
                            }else{
                                return -1;
                            }
                        }
                    };
                    Collections.sort(movies,comp);
                    ViewListByRatingFragment frg = new ViewListByRatingFragment();
                    frg.getMoviesInRating(movies);
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.container,frg,"tag_view_vy_rating").addToBackStack(null).commit();

                }else{
                    Toast.makeText(getActivity(),"No favourite movies exist! Add and retry.",Toast.LENGTH_LONG).show();
                }
            }
        });

        getView().findViewById(R.id.button_showListByYear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movies.size()>0){

                    Collections.sort(movies,new Comparator<Movie>() {
                        @Override
                        public int compare(Movie movie, Movie t1) {
                            if(movie.getYear()<t1.getYear()){
                                return -1;
                            }else{
                                return 1;
                            }


                        }
                    });
                    ViewListByYearFragment frg = new ViewListByYearFragment();
                    frg.getMoviesInYear(movies);
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.container,frg,"tag_view_by_year").addToBackStack(null).commit();
                }else{
                    Toast.makeText(getActivity(),"No favourite movies exist! Add and retry.",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onFragmentInteraction(Uri uri);
        public void goToNextFragment();
        public void setDeleteIndex(int index);
    }

    public void getMovies(ArrayList<Movie> movies){
        this.movies = movies;
    }
}
