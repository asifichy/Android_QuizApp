package edu.ewubd.quizzler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private TextView tvScore, tvCorrect, tvWrong, tvNotAnswer, totalquestion, timeneed;
    private String score = "0";
    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvScore = findViewById(R.id.tvScore);
        tvCorrect = findViewById(R.id.tvCorrect);
        tvWrong = findViewById(R.id.tvWrong);
        tvNotAnswer = findViewById(R.id.tvNotAnswer);
        totalquestion = findViewById(R.id.totalquestion);
        timeneed = findViewById(R.id.timeneed);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        score = sharedPreferences.getString("score", "");
        email = sharedPreferences.getString("email", "");
        int totalScore = Integer.parseInt(score) + (getIntent().getIntExtra("SCORE", -1));
        score = String.valueOf(totalScore);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("score", String.valueOf(totalScore));
        editor.apply();

        tvScore.setText(String.valueOf(getIntent().getIntExtra("SCORE", -1)));
        tvCorrect.setText(String.valueOf(getIntent().getIntExtra("correct", -1)));
        tvWrong.setText(String.valueOf(getIntent().getIntExtra("wrong", -1)));
        tvNotAnswer.setText(String.valueOf(getIntent().getIntExtra("not_answer", -1)));
        totalquestion.setText(String.valueOf(getIntent().getIntExtra("correct", -1) +
                getIntent().getIntExtra("wrong", -1) +
                getIntent().getIntExtra("not_answer", -1)));
        timeneed.setText(String.format("%d Minutes", getIntent().getLongExtra("time", -1) / (60 * 1000)));

        findViewById(R.id.btnReturnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_item2) {
                    Intent intent = new Intent(ResultActivity.this, LeaderboardActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item2);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.action_item3) {
                    Intent intent = new Intent(ResultActivity.this, AccountActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item3);
                    startActivity(intent);
                    finish();
                    return true;
                }
                else if (itemId == R.id.action_item1) {
                    Intent intent = new Intent(ResultActivity.this, CategoryActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item1);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        String keys[] = {"action", "email", "score"};
        String values[] = {"update", email, score};
        httpRequest(keys, values);
        System.out.println("------On P R Activity");
        System.out.println(score);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void httpRequest(final String keys[],final String values[]){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {
                List<NameValuePair> params=new ArrayList<NameValuePair>();
                for (int i=0; i<keys.length; i++){
                    params.add(new BasicNameValuePair(keys[i],values[i]));
                }
                String url= "http://localhost/quizzler/";
                String data="";
                try {
                    data=JSONParser.getInstance().makeHttpRequest(url,"POST",params);
                    System.out.println(data);
                    return data;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            protected void onPostExecute(String data){
                if(data!=null){
                    System.out.println(data);
                    System.out.println("Ok2");
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}