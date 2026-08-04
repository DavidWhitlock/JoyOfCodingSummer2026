package edu.pdx.cs.joy.whitlock;

import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    private static final int GET_SUM = 42;
    private ArrayAdapter<Flight> flights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView sumsWidget = findViewById(R.id.sums);
        flights = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        sumsWidget.setAdapter(flights);

        try {
            readFromSumsFile();
        } catch (IOException e) {
            Toast.makeText(this, "While reading sums file: " + e.getMessage(), LENGTH_LONG).show();
        }
    }

    private void readFromSumsFile() throws IOException {
        File sumsFile = getSumsFile();
        try (BufferedReader br = new BufferedReader(new FileReader(sumsFile))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                int sum = Integer.parseInt(line);
                this.flights.add(new Flight(sum));
            }
        }
    }

    public void launchCalculator(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivityForResult(intent, GET_SUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_SUM) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Flight flight = data.getSerializableExtra(CalculatorActivity.SUM_VALUE, Flight.class);
                    this.flights.add(flight);
                    try {
                        writeSumsToFile();
                    } catch (IOException e) {
                        Toast.makeText(this, "While writing sums file: " + e.getMessage(), LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private void writeSumsToFile() throws IOException {
        File sumsFile = getSumsFile();
        try (PrintWriter pw = new PrintWriter(new FileWriter(sumsFile))) {
            for (int i = 0; i < this.flights.getCount(); i++) {
                Flight flight = this.flights.getItem(i);
                if (flight != null) {
                    pw.println(flight.getNumber());
                }
            }
            pw.flush();
        }
    }

    private File getSumsFile() {
        File dataDir = this.getDataDir();
        return new File(dataDir, "sums.txt");
    }
}