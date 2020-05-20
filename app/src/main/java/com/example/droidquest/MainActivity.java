package com.example.droidquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mDeceitButton;
    private ImageButton mNextButton;
    private ImageButton mBackButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_android,true),
            new Question(R.string.question_linear,false),
            new Question(R.string.question_service,false),
            new Question(R.string.question_res,true),
            new Question(R.string.question_manifest,true),
            new Question(R.string.question_version_names,false),
            new Question(R.string.question_android_media,true),
            new Question(R.string.question_r_java,true),
            new Question(R.string.question_activity_states,false),
            new Question(R.string.question_broadcast_receiver,true),

    };
    private int mCurrentIndex = 0;
    private  static final String KEY_INDEX = "index";
    private static final String TAG = "MainActivity";


    public void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }
    private void checkAnswer(boolean userPressedTrue)
    {
        boolean AnswerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if(userPressedTrue == AnswerIsTrue) messageResId = R.string.correct_toast;
        else messageResId = R.string.incorrect_toast;
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() вызван");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() вызван");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() вызван");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() вызван");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() вызван");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate(Bundle) вызван");

        if(savedInstanceState!=null)mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        updateQuestion();

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);

                boolean AnswerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

                if(AnswerIsTrue){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                }
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);

                boolean AnswerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

                if(!AnswerIsTrue){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                }

            }
        });

        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mBackButton = (ImageButton)findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex!=0){
                    mCurrentIndex=(mCurrentIndex - 1) % mQuestionBank.length;
                } else{
                    mCurrentIndex=mQuestionBank.length-1;
                }
                updateQuestion();
            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mDeceitButton = (Button)findViewById(R.id.deceit_button);
        mDeceitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = DeceitActivity.newIntent(MainActivity.this,answerIsTrue);
                startActivity(i);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savdInstanceState) {
        super.onSaveInstanceState(savdInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savdInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }


}
