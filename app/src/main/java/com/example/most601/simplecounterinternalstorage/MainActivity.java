package com.example.most601.simplecounterinternalstorage;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //name of the file - The default location is always in the app instance
    private static final String BEST_SCORE_FILE = "BestScore";

    private FileManager m_fileManager;
    //connect to textviwe in layout
    private TextView m_textViewPoints;
    //num of points
    private int m_points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_textViewPoints = (TextView)findViewById(R.id.textViewPoints);

        m_fileManager = new FileManager(this);
        try {
            //Give a filename and it returns its data
            String fileContent = m_fileManager.readInternalFile(BEST_SCORE_FILE);
            // If it return a string then we will make it a number and save, otherwise we will save 0
            m_points = fileContent.length() == 0 ? 0 : Integer.parseInt(fileContent);
        } catch (IOException e) {
            //In case the file does not exist or did not find it
            m_points = 0;
        }

        updatePointsDisplay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            //Writing to a file,
            //(File name, input, whether to rewrite or continue the file) in our case rewrite
            m_fileManager.writeInternalFile(BEST_SCORE_FILE, Integer.toString(m_points), false);
        } catch (IOException e) {
            Log.e("IOError", "Could not read best score");
        }
    }


    @SuppressLint("SetTextI18n")
    private void updatePointsDisplay() {
        //View the data
        m_textViewPoints.setText(Integer.toString(m_points));
    }

    public void onClickAddPointButton(View view) {
        m_points += 1;
        updatePointsDisplay();
    }

    public void reset(View view) {
        m_points = 0;
        updatePointsDisplay();
    }
}