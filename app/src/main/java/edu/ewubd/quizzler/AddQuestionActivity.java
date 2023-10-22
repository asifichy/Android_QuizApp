package edu.ewubd.quizzler;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddQuestionActivity extends AppCompatActivity {
    EditText questionEditText, optionAEditText, optionBEditText, optionCEditText, optionDEditText, answerEditText, categoryEditText;
    Button saveButton, cancelButton;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        questionEditText = findViewById(R.id.Question);
        optionAEditText = findViewById(R.id.OptionA);
        optionBEditText = findViewById(R.id.OptionB);
        optionCEditText = findViewById(R.id.OptionC);
        optionDEditText = findViewById(R.id.OptionD);
        answerEditText = findViewById(R.id.Answer);
        categoryEditText = findViewById(R.id.Category);
        saveButton = findViewById(R.id.btnSave);
        cancelButton = findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        if(intent.hasExtra("id")){
            id = intent.getStringExtra("id");
            categoryEditText.setText(intent.getStringExtra("category"));
            questionEditText.setText(intent.getStringExtra("questionText"));
            optionAEditText.setText(intent.getStringExtra("option1"));
            optionBEditText.setText(intent.getStringExtra("option2"));
            optionCEditText.setText(intent.getStringExtra("option3"));
            optionDEditText.setText(intent.getStringExtra("option4"));
            answerEditText.setText(intent.getStringExtra("answer"));
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("-----id check------");
                System.out.println(id);

                if (id.isEmpty()){
                    saveQuestion();
                }
                else{
                    updateQuestion();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnViewQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveQuestion() {
        String question = questionEditText.getText().toString();
        String optionA = optionAEditText.getText().toString();
        String optionB = optionBEditText.getText().toString();
        String optionC = optionCEditText.getText().toString();
        String optionD = optionDEditText.getText().toString();
        String answer = answerEditText.getText().toString();
        String category = categoryEditText.getText().toString();

        QuestionDB questionDB = new QuestionDB(this);
        id = String.valueOf(System.currentTimeMillis());
        questionDB.insertQuestion(id, question, optionA, optionB, optionC, optionD, answer, category);

        Toast.makeText(this, "Question saved successfully", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    private void updateQuestion () {
        String question = questionEditText.getText().toString();
        String optionA = optionAEditText.getText().toString();
        String optionB = optionBEditText.getText().toString();
        String optionC = optionCEditText.getText().toString();
        String optionD = optionDEditText.getText().toString();
        String answer = answerEditText.getText().toString();
        String category = categoryEditText.getText().toString();

        QuestionDB questionDB = new QuestionDB(this);
        questionDB.updateQuestion(id, question, optionA, optionB, optionC, optionD, answer, category);

        Toast.makeText(this, "Question update successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void clearFields() {
        questionEditText.setText("");
        optionAEditText.setText("");
        optionBEditText.setText("");
        optionCEditText.setText("");
        optionDEditText.setText("");
        answerEditText.setText("");
        categoryEditText.setText("");
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
