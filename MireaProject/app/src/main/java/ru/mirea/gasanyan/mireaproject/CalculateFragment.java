package ru.mirea.gasanyan.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//
//public class CalculateFragment extends Fragment {
//
//
//    TextView resultField; // текстовое поле для вывода результата
//    EditText numberField;   // поле для ввода числа
//    TextView operationField;    // текстовое поле для вывода знака операции
//    Double operand = null;  // операнд операции
//    String lastOperation = "="; // последняя операция
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_calculate, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState){
////        View view = inflater.inflate(R.layout.fragment_calculate, container, false);
//        super.onViewCreated(view, savedInstanceState);
//        resultField = view.findViewById(R.id.resultField); // текстовое поле для вывода результата
//        numberField = view.findViewById(R.id.numberField);   // поле для ввода числа
//        operationField = view.findViewById(R.id.operationField);    // текстовое поле для вывода знака операции
//    }
//    // обработка нажатия на числовую кнопку
//    public void onNumberClick(View view){
//
//        Button button = (Button)view;
//        numberField.append(button.getText());
//
//        if(lastOperation.equals("=") && operand!=null){
//            operand = null;
//        }
//    }
//    // обработка нажатия на кнопку операции
//    public void onOperationClick(View view){
//
//        Button button = (Button)view;
//        String op = button.getText().toString();
//        String number = numberField.getText().toString();
//        // если введенно что-нибудь
//        if(number.length()>0){
//            number = number.replace(',', '.');
//            try{
//                performOperation(Double.valueOf(number), op);
//            }catch (NumberFormatException ex){
//                numberField.setText("");
//            }
//        }
//        lastOperation = op;
//        operationField.setText(lastOperation);
//    }
//
//    private void performOperation(Double number, String operation){
//
//        // если операнд ранее не был установлен (при вводе самой первой операции)
//        if(operand ==null){
//            operand = number;
//        }
//        else{
//            if(lastOperation.equals("=")){
//                lastOperation = operation;
//            }
//            switch(lastOperation){
//                case "=":
//                    operand =number;
//                    break;
//                case "/":
//                    if(number==0){
//                        operand =0.0;
//                    }
//                    else{
//                        operand /=number;
//                    }
//                    break;
//                case "*":
//                    operand *=number;
//                    break;
//                case "+":
//                    operand +=number;
//                    break;
//                case "-":
//                    operand -=number;
//                    break;
//            }
//        }
//        resultField.setText(operand.toString().replace('.', ','));
//        numberField.setText("");
//    }
//
//}


public class CalculateFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private String line="";

    public CalculateFragment() { }

    public static CalculateFragment newInstance(String param1, String param2) {
        CalculateFragment fragment = new CalculateFragment();
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

    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate, container, false);
        TextView textView=  view.findViewById(R.id.answer);

        showLine(view,textView,R.id.zero);
        showLine(view,textView,R.id.one);
        showLine(view,textView,R.id.two);
        showLine(view,textView,R.id.three);
        showLine(view,textView,R.id.four);
        showLine(view,textView,R.id.five);
        showLine(view,textView,R.id.six);
        showLine(view,textView,R.id.seven);
        showLine(view,textView,R.id.eight);
        showLine(view,textView,R.id.nine);


        showLine(view,textView,R.id.point);
        showLine(view,textView,R.id.plus);
        showLine(view,textView,R.id.minus);
        showLine(view,textView,R.id.divided);
        showLine(view,textView,R.id.times);
        showLine(view,textView,R.id.delete);
        showLine(view,textView,R.id.equals);

        return view;
    }

    public void showLine(View view,TextView textView, int number){
        button =  view.findViewById(number);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line+=((Button)v).getText().toString();
                if (((Button)v).getText().toString().equals("C")) line="";
                if (((Button)v).getText().toString().equals("=")) {
                    String cutline = (String) textView.getText();
                    int k=0,j=0,countA=0,countB=0;
                    String a = "",b= "",c= "";
                    float result = 0, first=0, second=0;
                    for (int i = 0; i < cutline.length(); i++) {
                        if (cutline.charAt(i)=='-' && i==0)
                            countA++;
                        else {
                            if ((cutline.charAt(i)=='+'||cutline.charAt(i)=='-'||cutline.charAt(i)=='*'||cutline.charAt(i)=='/') && k==0) {
                                k++;
                                j=i;
                            }

                            if (k==0) a+=(String.valueOf(cutline.charAt(i)));
                            else {
                                c =(String.valueOf(cutline.charAt(j)));
                                if (i > j) {
                                    if (cutline.charAt(i)=='-' && i==j+1) countB++;
                                    else b+=(String.valueOf(cutline.charAt(i)));
                                }
                            }
                        }
                    }
                    first = Float.parseFloat(a);
                    second = Float.parseFloat(b);
                    if (countA==1) first=-first;
                    if (countB==1) second=-second;
                    if (c.equals("+")) result = first+second;
                    if (c.equals("-")) result = first-second;
                    if (c.equals("*")) result = first * second;
                    if (c.equals("/")) result = first/second;
                    textView.setText(Float.toString(result));
                    line="";
                }
                else textView.setText(line);
            }
        });
    }
}