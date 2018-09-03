package com.example.max.higherlowerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int previousImageIndex;
    private int currentImageIndex = 3;
    private int currentScore = 0;
    private int highScore = 0;
    private int[] mImageNames;
    private ArrayAdapter<String> mAdapter;
    private TextView mCurrentScore;
    private TextView mHighScore;
    private ImageView mImageView;
    private ListView mListView;
    private List<String> mItems;
    private Button mLowerButton;
    private Button mHigherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrentScore = findViewById(R.id.currentscore);
        mHighScore = findViewById(R.id.highscore);
        mListView = findViewById(R.id.list_view);
        mImageView = findViewById(R.id.diceImage);
        mLowerButton = findViewById(R.id.button_lower);
        mHigherButton = findViewById(R.id.button_higher);

        mItems = new ArrayList<>();
        mImageNames = new int[]{R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6};

        //Initialise the first throw
        mImageView.setImageResource(mImageNames[currentImageIndex]);
        mItems.add("First throw is " + (currentImageIndex+1));
        updateUI();

        mLowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousImageIndex = currentImageIndex;
                currentImageIndex = getRandomNumberInRange(0,5);

                mImageView.setImageResource(mImageNames[currentImageIndex]);
                mItems.add("Throw is " + (currentImageIndex+1));
                updateUI();

                if(currentImageIndex < previousImageIndex) {
                    currentScore++;
                    mCurrentScore.setText("Current score: " + currentScore);

                    if(currentScore > highScore) {
                        highScore = currentScore;
                        mHighScore.setText("Highscore: " + highScore);
                    }
                    Toast.makeText(getApplicationContext(), "You win!", Toast.LENGTH_SHORT).show();
                } else {
                    currentScore = 0;
                    mCurrentScore.setText("Current score: " + currentScore);
                    Toast.makeText(getApplicationContext(), "You lose!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mHigherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousImageIndex = currentImageIndex;
                currentImageIndex = getRandomNumberInRange(0,5);

                mImageView.setImageResource(mImageNames[currentImageIndex]);
                mItems.add("Throw is " + (currentImageIndex+1));
                updateUI();

                if(currentImageIndex > previousImageIndex) {
                    currentScore++;
                    mCurrentScore.setText("Current score: " + currentScore);

                    if(currentScore > highScore) {
                        highScore = currentScore;
                        mHighScore.setText("Highscore: " + highScore);
                    }

                    Toast.makeText(getApplicationContext(), "You win!", Toast.LENGTH_SHORT).show();

                } else {
                    currentScore = 0;
                    mCurrentScore.setText("Current score: " + currentScore);
                    Toast.makeText(getApplicationContext(), "You lose!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private void updateUI() {
        // If the list adapter is null, a new one will be instantiated and set on our list view.
        if (mAdapter == null) {
            // We can use ‘this’ for the context argument because an Activity is a subclass of the Context class.
            // The ‘android.R.layout.simple_list_item_1’ argument refers to the simple_list_item_1 layout of the Android layout directory.
            mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mItems);
            mListView.setAdapter(mAdapter);
        } else {
            // When the adapter is not null, it has to know what to do when our dataset changes, when a change happens we need to call this method on the adapter so that it will update internally.
            mAdapter.notifyDataSetChanged();
        }
    }
}
