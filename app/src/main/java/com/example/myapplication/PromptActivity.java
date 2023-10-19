package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {

    TextView answerTextView;
    Button showAnswerButton, backButton;
    private boolean correctAnswer;
    public static final String KEY_EXTRA_ANSWER_SHOWN = "answerShown";

    private static final int REQUEST_CODE_PROMPT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt_acitivity);
        answerTextView = findViewById(R.id.answer_text_view);
        showAnswerButton = findViewById(R.id.show_answer_button);
        backButton = findViewById(R.id.button_back);

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                answerTextView.setText(answer);
                setAnswerShownResult(true);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setAnswerShownResult(boolean answerWasShown) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShown);
        setResult(RESULT_OK, resultIntent);
    }

}