package com.example.parcial1germancarvajal.model.entity;

import java.util.Random;

public class RandomMath {

    private static final String[] OPERATORS={"/","*","+","-"};
    private static final int maxNumber=99;
    private static final int minNumber=-99;

    //Para facilidad de la evaluación, todas las respuestas de las preguntas son números enteros
    public static String getRandomOperation(){
        String result="";
        Random r=new Random();
        int operand1 = r.nextInt(maxNumber-minNumber) + minNumber-1;
        int operand2 = r.nextInt(maxNumber-minNumber) + minNumber-1;
        String operator=OPERATORS[r.nextInt(OPERATORS.length)];
        int answer=Integer.MIN_VALUE;
        switch (operator){
            case "/":
                //Se cambia el operador1 en caso de que la división no sea exacta para asegurar respuestas enteras
                if(operand1%operand2!=0){
                    operand1=(operand1/operand2)*operand2;
                }
                answer=operand1/operand2;
                break;
            case "*":
                answer=operand1*operand2;
                break;
            case "+":
                answer=operand1+operand2;
                break;
            case "-":
                answer=operand1-operand2;
                break;
        }
        result=operand1+";"+operator+";"+operand2+";"+answer;
        return result;
    }


}
