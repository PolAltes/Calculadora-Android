package com.example.calculator;

import static android.icu.lang.UProperty.MATH;

import android.annotation.SuppressLint;
import android.media.VolumeShaper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTv,solutionTv;
    MaterialButton buttonC, buttonDot;
    MaterialButton buttonDivide,buttonMultiply,buttonPlus,buttonMinus,buttonEquals;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;

    boolean useDegrees = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignID(buttonC,R.id.button_c);
        assignID(buttonDivide,R.id.button_divide);
        assignID(buttonMultiply,R.id.button_multiply);
        assignID(buttonPlus,R.id.button_plus);
        assignID(buttonMinus,R.id.button_minus);
        assignID(buttonEquals,R.id.button_equals);
        assignID(button0,R.id.button_0);
        assignID(button1,R.id.button_1);
        assignID(button2,R.id.button_2);
        assignID(button3,R.id.button_3);
        assignID(button4,R.id.button_4);
        assignID(button5,R.id.button_5);
        assignID(button6,R.id.button_6);
        assignID(button7,R.id.button_7);
        assignID(button8,R.id.button_8);
        assignID(button9,R.id.button_9);
        assignID(buttonDot,R.id.button_dot);


    }

    void assignID(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if(buttonText.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if(buttonText.equals("=")){
            solutionTv.setText(resultTv.getText());
            return;
        }
        if(buttonText.equals("C")){
            dataToCalculate=dataToCalculate.substring(0,dataToCalculate.length()-1);
        }
        else{
            dataToCalculate = dataToCalculate+buttonText;
        }

        solutionTv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);
        if(!finalResult.equals("Error")){
            resultTv.setText(finalResult);
        }
    }

    String getResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult=finalResult.replace(".0","");
            }
            return finalResult;
        }catch(Exception e){
            return ("Error");
        }
    }

    public void onDegrees(View v){
        useDegrees = !useDegrees;
        ((Button) v).setText(useDegrees?"D":"R");
    }

    public void onSIN(View v){
        double input;
        try{
            input = Double.parseDouble(resultTv.getText().toString());
        }catch(NumberFormatException e){
            return;
        }
        if(useDegrees) input *= (Math.PI/180);
        resultTv.setText(String.valueOf(Math.sin(input)));
    }

    public void onCOS(View v){
        double input;
        try{
            input = Double.parseDouble(resultTv.getText().toString());
        }catch(NumberFormatException e){
            return;
        }
        if(useDegrees) input *= (Math.PI/180);
        resultTv.setText(String.valueOf(Math.cos(input)));
    }

    public void onTAN(View v) {
        double input;
        try {
            input = Double.parseDouble(resultTv.getText().toString());
        } catch (NumberFormatException e) {
            return ;
        }
        if (useDegrees) input *= (Math.PI / 180);
        resultTv.setText(String.valueOf(Math.tan(input)));
    }
}