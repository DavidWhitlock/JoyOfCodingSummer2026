package edu.pdx.cs.joy.whitlock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity {

    static final String SUM_VALUE = "SUM";
    private int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void backToMain(View view) {
        Intent intent = new Intent();
        intent.putExtra(SUM_VALUE, this.sum);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void calculateSum(View view) {
        EditText leftWidget = findViewById(R.id.leftOperand);
        EditText rightWidget = findViewById(R.id.rightOperand);

        String leftString = leftWidget.getText().toString();
        String rightString = rightWidget.getText().toString();

        int leftOperand = Integer.parseInt(leftString);
        int rightOperand = Integer.parseInt(rightString);

        this.sum = leftOperand + rightOperand;

        EditText sumWidget = findViewById(R.id.sum);
        sumWidget.setText(String.valueOf(sum));

    }
}