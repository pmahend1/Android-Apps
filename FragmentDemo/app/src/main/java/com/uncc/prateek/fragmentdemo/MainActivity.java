package com.uncc.prateek.fragmentdemo;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements AFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("main","onCreate before inflate");
        setContentView(R.layout.activity_main);
        Log.d("main","onCreate after inflate");

        getFragmentManager().beginTransaction().add(R.id.container, new AFragment(), "tag_a_fragment").commit();


        getFragmentManager().beginTransaction().add(R.id.container, new AFragment(), "tag_a_fragment1").commit();
        //Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment);

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                //AFragment fragment = (AFragment) getFragmentManager().findFragmentById(R.id.fragment);
                AFragment fragment = (AFragment) getFragmentManager().findFragmentByTag("tag_a_fragment");
                AFragment fragment1 = (AFragment) getFragmentManager().findFragmentByTag("tag_a_fragment1");

                if(checkedId==R.id.radioButton_red){
                    fragment.changeColor(Color.RED);
                    fragment1.changeColor(Color.RED);
                }else if(checkedId==R.id.radioButton_blue){
                    fragment.changeColor(Color.BLUE);
                    fragment1.changeColor(Color.BLUE);
                }else if(checkedId==R.id.radioButton_green){
                    fragment.changeColor(Color.GREEN);
                    fragment1.changeColor(Color.GREEN);
                }

            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("main","onPostResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("main","onStart");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("main","onDestroy");
    }

    @Override
    public void onTextChange(String text) {
        TextView tv = (TextView) findViewById(R.id.textView_main);
        tv.setText(text.toString());
    }
}
