package com.example.parcial1germancarvajal;

import android.content.Intent;
import android.os.Bundle;

import com.example.parcial1germancarvajal.model.entity.RandomMath;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView operand1;
    private TextView operand2;
    private TextView operator;
    private EditText result;
    private int expectedResult;
    private Button buttonAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        operand1=findViewById(R.id.textViewOpn1);
        operand2=findViewById(R.id.textViewOpn2);
        operator=findViewById(R.id.textViewOpr);
        result=findViewById(R.id.editTextResult);
        buttonAnswer=findViewById(R.id.buttonAnswer);

        String [] operation=RandomMath.getRandomOperation().split(";");
        operand1.setText(operation[0]);
        operator.setText(operation[1]);
        operand2.setText(operation[2]);
        try{
            expectedResult=Integer.parseInt(operation[3]);
        }catch (Exception e){
            Toast.makeText(this, R.string.errorParsingInicial,Toast.LENGTH_LONG).show();
        }

        buttonAnswer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int answer=Integer.MIN_VALUE;
        try{
            answer=Integer.parseInt(result.getText().toString());
            Intent i=new Intent();
            if(answer==expectedResult){
                i.putExtra("result",true);
                Toast.makeText(this,"Resultado Correcto!",Toast.LENGTH_SHORT).show();
            }
            else{
                i.putExtra("result",false);
                Toast.makeText(this,"Resultado Incorrecto! La respuesta es "+expectedResult,Toast.LENGTH_SHORT).show();
            }
            setResult(RESULT_OK,i);
            finish();

        }catch (Exception e){
            Toast.makeText(this, R.string.errorEntradaDatsos,Toast.LENGTH_LONG).show();
        }


    }
}
