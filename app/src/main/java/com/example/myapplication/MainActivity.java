package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentOperator;
    private double firstOperand = Double.NaN;
    private double secondOperand;
    private boolean isOperatorPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc);

        display = findViewById(R.id.textView);


        Button[] numberButtons = {
                findViewById(R.id.button21),
                findViewById(R.id.button6),
                findViewById(R.id.button10),
                findViewById(R.id.button12),
                findViewById(R.id.button13),
                findViewById(R.id.button14),
                findViewById(R.id.button15),
                findViewById(R.id.button16),
                findViewById(R.id.button17),
                findViewById(R.id.button19)
        };

        for (Button button : numberButtons) {
            button.setOnClickListener(v -> {
                if (isOperatorPressed) {
                    display.setText(((Button) v).getText());
                    isOperatorPressed = false;
                } else {
                    display.append(((Button) v).getText());
                }
            });
        }

        findViewById(R.id.button11).setOnClickListener(v -> handleOperator("+"));
        findViewById(R.id.button22).setOnClickListener(v -> handleOperator("-"));

        findViewById(R.id.button23).setOnClickListener(v -> handleEqual());

        findViewById(R.id.button18).setOnClickListener(v -> {
            String currentText = display.getText().toString();
            if (currentText.length() > 0) {
                display.setText(currentText.substring(0, currentText.length() - 1));
            }
        });

        findViewById(R.id.button20).setOnClickListener(v -> {
            display.setText("");
            firstOperand = Double.NaN;
            secondOperand = 0;
            currentOperator = "";
        });
    }

    private void handleOperator(String operator) {
        if (!Double.isNaN(firstOperand)) {
            secondOperand = Double.parseDouble(display.getText().toString());
            firstOperand = performOperation(firstOperand, secondOperand, currentOperator);
            display.setText(String.valueOf(firstOperand));
        } else {
            firstOperand = Double.parseDouble(display.getText().toString());
        }
        currentOperator = operator;
        isOperatorPressed = true;
    }

    private void handleEqual() {
        if (!Double.isNaN(firstOperand)) {
            secondOperand = Double.parseDouble(display.getText().toString());
            double result = performOperation(firstOperand, secondOperand, currentOperator);
            display.setText(String.valueOf(result));
            firstOperand = result;
            currentOperator = "";
        }
    }

    private double performOperation(double firstOperand, double secondOperand, String operator) {
        switch (operator) {
            case "+":
                return firstOperand + secondOperand;
            case "-":
                return firstOperand - secondOperand;
            default:
                return secondOperand;
        }
    }
}
