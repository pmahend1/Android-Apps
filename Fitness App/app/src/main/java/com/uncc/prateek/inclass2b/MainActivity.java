package com.uncc.prateek.inclass2b;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup_BMIRanges;
    private String weightRange="";
    public static final String lessThan18_5 = "lessThan18_5";
    public static final String between18_5to24_99 = "between18_5to24_99";
    public static final String getBetween25to29_99 = "getBetween25to29_99";
    public static final String above29_9 = "above29_9";
    private double weightLow = 0.0;
    private double weightHigh = 0.0;
    private double heightInFeet=0.0;
    private double heightInInches=0.0;
    private double netHeight=0.0;
    private String textView_weightEstimatorOutput="";
    private String errorMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void weightEstimator(View view){
        Log.d("WE", "Weight Estimator");
        radioGroup_BMIRanges = (RadioGroup) findViewById(R.id.radioGroup_BMIRanges);

                Log.d("WE","here2");
                errorMessage="";
                if(radioGroup_BMIRanges.getCheckedRadioButtonId()==R.id.radioButton_lessThan18_5){
                        weightRange=lessThan18_5;

                }
                else if(radioGroup_BMIRanges.getCheckedRadioButtonId()==R.id.radioButton_between18_5to24_9){
                        weightRange=between18_5to24_99;
                }

                else if(radioGroup_BMIRanges.getCheckedRadioButtonId()==R.id.radioButton_between25to29_9){
                    weightRange=getBetween25to29_99;
                }
                else if(radioGroup_BMIRanges.getCheckedRadioButtonId()==R.id.radioButton_above29_9){
                    weightRange=above29_9;
                }
                else{
                    try{
                        throw new Exception();
                    }
                    catch (Exception ex ){
                        errorMessage =  errorMessage +"BMI range not selected" + "; ";
                        Log.d("WE",errorMessage);
                    }
                }

                EditText heightFeet = (EditText) findViewById(R.id.editText_feet);
                EditText heightInch = (EditText) findViewById(R.id.editText_Inches);

                try {
                    heightInFeet=Double.parseDouble(heightFeet.getText().toString());
                    if(heightInFeet<=0 || heightFeet.getText().toString().isEmpty()){
                        throw new Exception();
                    }

                }catch (Exception ex){
                    errorMessage =  errorMessage + "Invalid feet value" + "; ";
                    Log.d("WE",errorMessage);

                }


                try {
                    heightInInches=Double.parseDouble(heightInch.getText().toString());
                    if(heightInInches<=0 || heightInch.getText().toString()==null){
                        throw new Exception();
                    }


                }catch (Exception ex){
                    errorMessage =  errorMessage + "Invalid inches value" +"; " ;
                    Log.d("WE",errorMessage);
                }
                if(errorMessage.isEmpty()){

                    netHeight = (heightInFeet*12)+heightInInches;

                    switch(weightRange){
                        case lessThan18_5 :{
                            weightHigh=(18.5*netHeight*netHeight)/703;
                            weightHigh =(double) Math.round(weightHigh*100)/100d;
                            textView_weightEstimatorOutput = "Your weight should be less than ";
                            textView_weightEstimatorOutput = textView_weightEstimatorOutput + weightHigh+ "lb";
                            break;
                        }
                        case between18_5to24_99:{
                            weightLow=(18.5*netHeight*netHeight)/703;
                            weightHigh=(24.99*netHeight*netHeight)/703;
                            weightLow =(double) Math.round(weightLow*100)/100d;
                            weightHigh =(double) Math.round(weightHigh*100)/100d;
                            textView_weightEstimatorOutput = "Your weight should be in between " + weightLow + " to " + weightHigh+ "lb";
                            break;
                        }
                        case getBetween25to29_99:{
                            weightLow=(25*netHeight*netHeight)/703;
                            weightHigh=(29.99*netHeight*netHeight)/703;
                            weightLow =(double) Math.round(weightLow*100)/100d;
                            weightHigh =(double) Math.round(weightHigh*100)/100d;
                            textView_weightEstimatorOutput = "Your weight should be in between " + weightLow + " to " + weightHigh + "lb";

                            break;
                        }
                        case above29_9:{
                            weightLow = (29.99*netHeight*netHeight)/703;
                            weightLow =(double) Math.round(weightLow*100)/100d;
                            textView_weightEstimatorOutput = "Your weight should be more than " + weightLow + "lb";
                        }

                    }
                    weightLow =(double) Math.round(weightLow*100)/100d;
                    weightHigh =(double) Math.round(weightHigh*100)/100d;

                    Log.d("WE","Checking weight estimator");
                    Log.d("WE",textView_weightEstimatorOutput);

                    //Set currency conversion value
                    TextView weightEstTxtVw = (TextView) findViewById(R.id.textView_weightEstimatorOutput);
                    weightEstTxtVw.setText(String.valueOf(textView_weightEstimatorOutput));

                    Toast.makeText(getApplicationContext(),
                            "Weight calculated", Toast.LENGTH_LONG).show();
                }else{
                    Log.d("WE",errorMessage);
                    TextView weightEstTxtVw = (TextView) findViewById(R.id.textView_weightEstimatorOutput);
                    weightEstTxtVw.setTextColor(Color.RED);
                    weightEstTxtVw.setText(String.valueOf(errorMessage));

                }



    }

}
