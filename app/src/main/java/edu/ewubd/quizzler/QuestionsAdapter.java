package edu.ewubd.quizzler;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAdapter extends BaseAdapter {
    private Context context;
    private List<QuestionItem> questions;
    private SparseArray<Integer> selectedOptions;
    private List<String> correctAnswers;

    private int tvCorrect = 0, tvNotAnswer = 0, tvWrong = 0;

    public QuestionsAdapter(Context context, List<QuestionItem> questions) {
        this.context = context;
        this.questions = questions;
        this.selectedOptions = new SparseArray<>();

        this.correctAnswers = new ArrayList<>();
        for (QuestionItem question : questions) {
            correctAnswers.add(question.getAnswer());
        }
        System.out.println("--------------------------------------");
        System.out.println(correctAnswers);
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.question_item_layout, parent, false);
        }

        final QuestionItem question = questions.get(position);
        TextView questionText = convertView.findViewById(R.id.ques);
        RadioGroup optionsGroup = convertView.findViewById(R.id.optionsGroup);
        RadioButton option1 = convertView.findViewById(R.id.option1);
        RadioButton option2 = convertView.findViewById(R.id.option2);
        RadioButton option3 = convertView.findViewById(R.id.option3);
        RadioButton option4 = convertView.findViewById(R.id.option4);

        questionText.setText(question.getQuestionText());

        optionsGroup.setOnCheckedChangeListener(null);

        int selectedOption = selectedOptions.get(position, -1);
        if (selectedOption != -1) {
            RadioButton selectedRadioButton;
            switch (selectedOption) {
                case 0:
                    selectedRadioButton = option1;
                    break;
                case 1:
                    selectedRadioButton = option2;
                    break;
                case 2:
                    selectedRadioButton = option3;
                    break;
                case 3:
                    selectedRadioButton = option4;
                    break;
                default:
                    selectedRadioButton = null;
            }
            if (selectedRadioButton != null) {
                selectedRadioButton.setChecked(true);
            }
        } else {
            optionsGroup.clearCheck();
        }

        option1.setText(question.getOption1());
        option2.setText(question.getOption2());
        option3.setText(question.getOption3());
        option4.setText(question.getOption4());

        optionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedOptions.put(position, getSelectedOptionIndex(checkedId)); // Store the selected option
            }
        });

        return convertView;
    }

    private int getSelectedOptionIndex(int checkedId) {
        if (checkedId == R.id.option1) {
            return 0;
        } else if (checkedId == R.id.option2) {
            return 1;
        } else if (checkedId == R.id.option3) {
            return 2;
        } else if (checkedId == R.id.option4) {
            return 3;
        } else {
            return -1;
        }
    }

    public int calculateScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            QuestionItem question = questions.get(i);
            int selectedOptionIndex = selectedOptions.get(i, -1);

            if (selectedOptionIndex != -1){
                if (selectedOptionIndex == getOptionIndex(question.getAnswer())){
                    score++;
                    tvCorrect++;
                } else {
                    tvWrong++;
                }

            } else {
                tvNotAnswer++;
            }
        }
        return score;
    }

    private int getOptionIndex(String answer) {
        switch (answer.toUpperCase()) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            default:
                return -1;
        }
    }

    public int getCorrect() {
        return tvCorrect;
    }

    public int getNotAnswe() {
        return tvNotAnswer;
    }

    public int getWromg() {
        return tvWrong;
    }
}