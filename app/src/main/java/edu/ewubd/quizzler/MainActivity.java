package edu.ewubd.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ListView listQuestion;
    private ArrayList<Questions> questions;
    private QuestionListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listQuestion = findViewById(R.id.listQuestions);
        questions = new ArrayList<>();

        findViewById(R.id.btnCreateNew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddQuestionActivity.class));
            }
        });

        findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Exit Confirmation");
                builder.setMessage("Are you sure you want to exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        questions.clear();
        QuestionDB db = new QuestionDB(this);
        Cursor rows = db.selectQuestions("SELECT * FROM questions");
        if (rows.getCount() > 0) {
            while (rows.moveToNext()) {
                String Id = rows.getString(0);
                String Question = rows.getString(1);
                String OptionA = rows.getString(2);
                String OptionB = rows.getString(3);
                String OptionC = rows.getString(4);
                String OptionD = rows.getString(5);
                String Answer = rows.getString(6);
                String Category = rows.getString(7);
                Questions e = new Questions(Id, Question, OptionA, OptionB, OptionC, OptionD, Answer, Category);
                questions.add(e);
            }
        }
        db.close();

        adapter = new QuestionListAdapter(this, questions);
        listQuestion.setAdapter(adapter);

        listQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, AddQuestionActivity.class);
                i.putExtra("id", questions.get(position).ID);
                i.putExtra("category", questions.get(position).category);
                i.putExtra("questionText", questions.get(position).question);
                i.putExtra("option1", questions.get(position).optionA);
                i.putExtra("option2", questions.get(position).optionB);
                i.putExtra("option3", questions.get(position).optionC);
                i.putExtra("option4", questions.get(position).optionD);
                i.putExtra("answer", questions.get(position).answer);
                startActivity(i);
            }
        });

        listQuestion.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String message = "Do you want to delete question - " + questions.get(position).question + " ?";
                showDialog(message, "Delete Question", questions.get(position).ID);
                System.out.println("=----------Long press");
                System.out.println(questions.get(position).ID);
                return true;
            }
        });
    }

    private void showDialog(String errMsg, String type, String questionId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(type);
        builder.setMessage(errMsg);
        builder.setCancelable(true);

        QuestionDB db = new QuestionDB(this);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteQuestion(questionId);
                loadData();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Exit Confirmation");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
}
