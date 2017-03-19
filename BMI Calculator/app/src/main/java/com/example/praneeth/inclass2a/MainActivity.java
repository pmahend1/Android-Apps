package com.example.praneeth.inclass2a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String weightRange="";
    public static final String lessThan18_5 = "lessThan18_5";
    public static final String between18_5to24_99 = "between18_5to24_99";
    public static final String getBetween25to29_99 = "getBetween25to29_99";
    public static final String above29_9 = "above29_9";
    private double weight = 0.0;
    private double BMI = 0.0;

    private double heightInFeet=0.0;
    private double heightInInches=0.0;
    private double netHeight=0.0;
    private String textView_weightEstimatorOutput="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void calculateBMI(View view){
        Log.d("WE", "calculateMI");
       // radioGroup_BMIRanges = (RadioGroup) findViewById(R.id.radioGroup_BMIRanges);

        findViewById(R.id.button_calculateBMI).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                EditText weightTextView = (EditText) findViewById(R.id.editText_weight);
                EditText heightInch = (EditText) findViewById(R.id.editText_inches);
                EditText heightFeet = (EditText) findViewById(R.id.editText_feet);

                try {
                    heightInFeet=Double.parseDouble(heightFeet.getText().toString());

                }catch (Exception ex){
                    //throw new Exception("Invalid feet");
                    Log.d("WE","Invalid Feet value");
                }


                try {
                    heightInInches=Double.parseDouble(heightInch.getText().toString());

                }catch (Exception ex){
                    //throw new Exception("Invalid inches");
                    Log.d("WE","Invalid Inches");
                }


                try {
                    weight=Double.parseDouble(weightTextView.getText().toString());

                }catch (Exception ex){
                    //throw new Exception("Invalid inches");
                    Log.d("WE","Invalid Weight");
                }
                netHeight = heightInFeet * 12 + heightInInches;

                BMI = (weight /(netHeight*netHeight))*703;
                
                String textView_weightresultMsg="";
                if(BMI<18.5){
                    textView_weightresultMsg="Underweight";
                }else if(BMI>18.5 && BMI<24.99){
                    textView_weightresultMsg="Normal Weight";
                }
                else if(BMI>24.99 && BMI<29.99){
                    textView_weightresultMsg="Overweight";
                }
                else if(BMI>29.99){
                    textView_weightresultMsg="Obesity";
                }
                else{
                    textView_weightresultMsg="Error";
                }
                Log.d("WE","Calculation"+BMI);
                //Set currency conversion value
                TextView textView_BMIresultTX = (TextView) findViewById(R.id.textView_BMIresult);
                textView_BMIresultTX.setText(String.valueOf(BMI));

                TextView textView_weightresult = (TextView) findViewById(R.id.textView_weightresult);
                textView_weightresult.setText(String.valueOf(textView_weightresultMsg));
            }
        });

    }

}
