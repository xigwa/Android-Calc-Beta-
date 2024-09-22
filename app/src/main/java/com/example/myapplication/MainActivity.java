package com.example.myapplication;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
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
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.textView);

        findViewById(R.id.buttonComma).setOnClickListener(v -> {
            String currentText = display.getText().toString();
            if (!currentText.contains(",")) {
                display.append(",");
            }
        });

        Button[] numberButtons = {
                findViewById(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3),
                findViewById(R.id.button4),
                findViewById(R.id.button5),
                findViewById(R.id.button6),
                findViewById(R.id.button7),
                findViewById(R.id.button8),
                findViewById(R.id.button9),
                findViewById(R.id.button0)
        };

        for (Button button : numberButtons) {
            button.setOnClickListener(v -> {
                display.append(((Button) v).getText());
                isOperatorPressed = false;
            });
        }

        findViewById(R.id.buttonPlus).setOnClickListener(v -> handleOperator("+"));
        findViewById(R.id.buttonSubtract).setOnClickListener(v -> handleOperator("-"));
        findViewById(R.id.buttonMultiply).setOnClickListener(v -> handleOperator("×"));
        findViewById(R.id.buttonDivide).setOnClickListener(v -> handleOperator("÷"));

        findViewById(R.id.equals).setOnClickListener(v -> handleEqual());
        findViewById(R.id.buttonDel).setOnClickListener(v -> {
            String currentText = display.getText().toString();
            if (currentText.length() > 0) {
                display.setText(currentText.substring(0, currentText.length() - 1));
            }
        });
        findViewById(R.id.buttonClear).setOnClickListener(v -> {
            display.setText("");
            firstOperand = Double.NaN;
            secondOperand = 0;
            currentOperator = "";
        });
        findViewById(R.id.buttonParentheses).setOnClickListener(v -> {
            String currentText = display.getText().toString();
            if (currentText.contains("("))
                display.setText(currentText + ")");
             else
                display.setText(currentText + "(");
        });
        findViewById(R.id.buttonPercent).setOnClickListener(v -> {
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("displayText", display.getText().toString());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        display.setText(savedInstanceState.getString("displayText", "0"));
    }

    private void handleOperator(String operator) {
        String currentText = display.getText().toString();
        if (currentText.length() > 0 && isOperator(currentText.charAt(currentText.length() - 1)))
            display.setText(currentText.substring(0, currentText.length() - 1) + operator);
         else
            display.append(operator);
        currentOperator = operator;
        isOperatorPressed = true;
    }
    private boolean isOperator(char character) {
        return character == '+' || character == '-' || character == '×' || character == '÷';
    }
    private void handleEqual() {
        if (display.getText().toString().isEmpty()) return;

        try {
            String expressionString = display.getText().toString()
                    .replace("×", "*")
                    .replace("÷", "/")
                    .replace(",", ".");
            int openBrackets = expressionString.length() - expressionString.replace("(", "").length();
            int closeBrackets = expressionString.length() - expressionString.replace(")", "").length();
            if (openBrackets != closeBrackets) {
                display.setText("Error");
                return;
            }
            Expression expression = new ExpressionBuilder(expressionString)
                    .build();
            double result = expression.evaluate();
            display.setText(String.valueOf(result));
        } catch (Exception e) {
            display.setText("Error");
        }
    }
}
