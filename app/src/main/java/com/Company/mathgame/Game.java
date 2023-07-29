package com.Company.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {
    TextView score;
    TextView time;
    TextView life;
    TextView question;
    EditText answer;
    Button ok,next;
    Random random = new Random();
    int num1,num2,userAns,correctAns,userScore =0,userLife=3;

    CountDownTimer timer;
    public static final long TIME_IN_MILLIS = 60000;
    boolean timer_running;
    long time_left_in_millis = TIME_IN_MILLIS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        score = findViewById(R.id.textViewScore);
        time = findViewById(R.id.textViewTime);
        life = findViewById(R.id.textViewLife);
        question = findViewById(R.id.textViewQuestion);
        answer = findViewById(R.id.editTextAnswer);
        ok = findViewById(R.id.buttonOk);
        next = findViewById(R.id.buttonNext);
        gameContinue();


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAns = Integer.valueOf(answer.getText().toString());
                pauseTimer();

                if(userAns==correctAns)
                {
                    userScore+=10;
                    score.setText(""+userScore);
                    question.setText("Congratulation!!!,your Answer is Corect!!");
                }
                else
                {
                    userLife-=1;
                    life.setText(""+userLife);
                    question.setText("WRONG ANSWER!!!!");


                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer.setText("");
                resetTimer();


                if(userLife<=0)
                {
                    Toast.makeText(getApplicationContext(), "Game Owari da", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Game.this,Result.class);
                    intent.putExtra("score",userScore);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    gameContinue();

                }

            }
        });

        }
    public void gameContinue()
    {

        num1 = random.nextInt(100);
        num2= random.nextInt(200);
        question.setText(num1 + " + " + num2);
        correctAns = num1+num2;
        startTimer();
    }
    public void startTimer()
    {
        timer = new CountDownTimer(time_left_in_millis,1000) {
            @Override
            public void onTick(long l) {
                time_left_in_millis=l;
                updateText();

            }

            @Override
            public void onFinish() {
                timer_running = false;
                pauseTimer();
                resetTimer();
                updateText();
                userLife-=1;
                life.setText(""+userLife);
                question.setText("Sorry,Time is UP! :(");

            }
        }.start();
        timer_running=true;
    }

    public void updateText()
    {
        int second = (int)(time_left_in_millis/1000)%60;
        String time_left = String.format(Locale.getDefault(),"%02d",second);
        time.setText(time_left);
        //timer_running=true;


    }

    public void pauseTimer()
    {
        timer.cancel();
        timer_running=false;

    }

    public void resetTimer()
    {
        time_left_in_millis = TIME_IN_MILLIS;
        updateText();

    }

}