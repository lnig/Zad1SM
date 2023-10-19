package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton, falseButton, nextButton, promptButton;
    private TextView questionTextView;

    private Question[] questions = new Question[]{
            new Question(R.string.q_activity, true),
            new Question(R.string.q_find_resources, false),
            new Question(R.string.q_listener, true),
            new Question(R.string.q_resources, true),
            new Question(R.string.q_version, false)
    };

    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String QUIZ_TAG = "MainActivity";
    public static final String KEY_EXTRA_ANSWER = "com.example.myapplication.quiz.correctAnswer";

    private static final int REQUEST_CODE_PROMPT = 0;

    private int currentIndex = 0;
    private int correctAnswersCount = 0;
    private boolean answerWasShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onCreate");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        promptButton = findViewById(R.id.prompt_button);
        questionTextView = findViewById(R.id.question_text_view);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % questions.length;
                answerWasShown = false;
                setNextQuestion();
            }
        });

        promptButton.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            boolean correctAnswer = questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);
        });

        setNextQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG, "Wywołana została metoda: onSaveInstanceState");
    }



    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();

        int resultMessageId = 0;
        if (answerWasShown){
            resultMessageId = R.string.answer_was_shown;
        }
        if (userAnswer == correctAnswer){
            resultMessageId = R.string.correct_answer;
            correctAnswersCount++;
        }
        if (userAnswer != correctAnswer){
            resultMessageId = R.string.incorrect_answer;
        }
        if (currentIndex == questions.length - 1){
            displayResult();
        }

        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
        if (questions[currentIndex].getQuestionId() == R.string.q_activity){
            correctAnswersCount = 0;
        }
    }

    public void displayResult(){
        String resultMessage = getString(R.string.result_message, correctAnswersCount, questions.length);
        Toast.makeText(this, resultMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null)
                return;
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}