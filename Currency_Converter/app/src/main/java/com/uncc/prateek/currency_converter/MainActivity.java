/*
    Assignment #. Homework 1
    File Name. MainActivity.java
    Student ID : 800966178
    Full name of the student : Prateek Mahendrakar
 */

package com.uncc.prateek.currency_converter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class MainActivity extends AppCompatActivity {
    private RadioGroup rg_from,rg_to;
    private String currency_from="";
    private String currency_to="";
    private double currency_from_amount=0.0;
    private double currency_to_amount=0.0;
    public static final double AUD_rate = 1.34;
    public static final double CAD_rate = 1.32;
    public static final double INR_rate = 68.14;
    public static final double GBP_rate = 0.83;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void convertDollarToOthers(View view) {

        //Code starts
        Log.d("cc", "Currency Converter APK");

        //Get entered value
        EditText amountEnteredText = (EditText) findViewById(R.id.number_enteredAmount);
        Log.i("cc", "Entered $" + amountEnteredText.getText().toString());

        //get radioGroup_from ID
        rg_from = (RadioGroup) findViewById(R.id.radioGroup_from);

        //Listener for changed values track of radioGroup_from
        rg_from.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rg_from_button = (RadioButton) findViewById(checkedId);
                Log.d("cc","Checked button is " + rg_from_button.getText().toString());
                currency_from=rg_from_button.getText().toString();
            }
        }) ;

        //get radioGroup_to ID
        rg_to = (RadioGroup) findViewById(R.id.radioGroup_to);

        //Listener for changed values track of radioGroup_to
        rg_to.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rg_to_button = (RadioButton) findViewById(checkedId);
                Log.d("cc","Checked button is " + rg_to_button.getText().toString());
                currency_to=rg_to_button.getText().toString();
            }
        }) ;

        //Listener for click of Convert button
        findViewById(R.id.button_convert).setOnClickListener(
                new View.OnClickListener(){

                    //Overriding onClick method to get changed radio button values and call
                    //currency conversion thereon

                    @Override
                    public void onClick(View v) {

                        //Get changed values for radioGroup currency to
                        if(rg_to.getCheckedRadioButtonId()== R.id.radioButton_USD){
                            Log.d("cc","Checked currency to is" + rg_to.getCheckedRadioButtonId() + " USD " + R.id.radioButton_USD);
                            currency_to = "USD";
                        }
                        else if(rg_to.getCheckedRadioButtonId()== R.id.radioButton_GBP){
                            Log.d("cc","Checked currency to is" + rg_to.getCheckedRadioButtonId() + " GBP");
                            currency_to = "GBP";
                        }
                        else{
                            Log.d("cc","Checked currency to is NULL");
                            currency_to = "USD";
                        }

                        Log.d("cc","Changed currency to after setOnClickListener  : " + currency_from);

                        //Get changed values for radioGroup currency from
                        if(rg_from.getCheckedRadioButtonId()== R.id.radioButton_AUD){
                            Log.d("cc","Checked currency from is" + rg_from.getCheckedRadioButtonId()+ " AUD");
                            currency_from = "AUD";
                        }
                        else if(rg_from.getCheckedRadioButtonId()== R.id.radioButton_CAD){
                            Log.d("cc","Checked currency from is" + rg_from.getCheckedRadioButtonId()+ " CAD");
                            currency_from = "CAD";
                        }
                        else if(rg_from.getCheckedRadioButtonId()== R.id.radioButton_INR){
                            Log.d("cc","Checked currency from is" + rg_from.getCheckedRadioButtonId()+" INR");
                            currency_from = "INR";
                        }
                        else{
                            Log.d("cc","Checked currency from is NULL");
                            currency_from = "INR";
                            currency_to = "USD";
                        }
                        Log.d("cc","Changed currency from  after setOnClickListener  : " + currency_from);

                        //calling currency conversion method
                        convertMethod();

                    }

                }
        );
        //call to currency conversion method without event listners for changed button values
        //This gets called for the first time of app run
        convertMethod();
    }

    //This method is to clear entered and converted values
    public void clear(View view){
        Log.d("clear","Clearing stuff");

        //Event listener for Clear button
        findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener(){
            //Overriding existing method to clear values
            @Override
            public void onClick(View v) {

                //clears converted field
                EditText set=(EditText) findViewById(R.id.number_convertedAmount);
                set.setText(String.valueOf(""));

                //clears entered field
                EditText ent=(EditText) findViewById(R.id.number_enteredAmount);
                ent.setText(String.valueOf("")) ;
            }
        });

    }

    /*
        convertMethod performs currency conversion operation based on selected
        radio buttons for currency_from and currency_to
        and displays it on screen
     */
    public void convertMethod(){
        currency_from = ((RadioButton)findViewById(rg_from.getCheckedRadioButtonId() )).getText().toString();
        currency_to = ((RadioButton)findViewById(rg_to.getCheckedRadioButtonId() )).getText().toString();

        Log.d("cc","Currency from checked :" + currency_from);
        Log.d("cc","Currency to checked :" + currency_to);

        //Fetching entered currency amount
        Double amountEnteredIn = 0.0;
        EditText amountEnteredText = (EditText) findViewById(R.id.number_enteredAmount);
        try {
            amountEnteredIn = Double.parseDouble(amountEnteredText.getText().toString());
            if(amountEnteredIn<0){
                throw new Exception("-ve number is Invalid Input ");
            }
        } catch (Exception e) {
            System.out.println("Exception at " + e.getMessage());
            Log.d("cc","Exception at " +e.getMessage());
            EditText numberEntered = (EditText) findViewById(R.id.number_enteredAmount);
            numberEntered.setText(String.valueOf("Invalid input"));
        } finally {
            System.out.println(amountEnteredIn);
            Log.d("cc", "Finally" + amountEnteredIn);
        }
        currency_from_amount = amountEnteredIn;

        //Currency conversions based on selected radio values
        if(currency_to.equals("USD")){
            Log.d("cc", "To USD conversions ");
            switch (currency_from){
                case "AUD":{
                    Log.d("cc","AUD to USD");
                    currency_to_amount = currency_from_amount/AUD_rate;
                    break;
                }
                case "CAD":{
                    Log.d("cc","CAD to USD");
                    currency_to_amount = currency_from_amount/CAD_rate;
                    break;
                }
                case "INR":{
                    Log.d("cc","INR to USD");
                    currency_to_amount = currency_from_amount/INR_rate;
                    break;
                }
                default:{
                    Log.d("cc","INR to USD");
                    currency_to_amount = currency_from_amount/INR_rate;
                    break;
                }
            }
        }else{
            Log.d("cc", "To GBP conversions ");
            switch (currency_from){
                case "AUD":{
                    Log.d("cc","AUD to GBP");
                    currency_to_amount = (GBP_rate*currency_from_amount)/AUD_rate;
                    break;
                }
                case "CAD":{
                    Log.d("cc","CAD to GBP");
                    currency_to_amount = (GBP_rate* currency_from_amount)/CAD_rate;
                    break;
                }
                case "INR":{
                    Log.d("cc","INR to GBP");
                    currency_to_amount = (GBP_rate*currency_from_amount)/INR_rate;
                    break;
                }
                default:{
                    Log.d("cc","INR to GBP");
                    currency_to_amount = GBP_rate*currency_from_amount/INR_rate;
                    break;
                }
            }
        }
        currency_to_amount=(double) Math.round(currency_to_amount*100)/100d;
        Log.d("cc","Currency From : " + currency_from_amount);

        Log.d("cc","Currency Converted : " + currency_to_amount);

        //Set currency conversion value
        EditText currencyToValue = (EditText) findViewById(R.id.number_convertedAmount);
        currencyToValue.setText(String.valueOf(currency_to_amount));

    }

}
