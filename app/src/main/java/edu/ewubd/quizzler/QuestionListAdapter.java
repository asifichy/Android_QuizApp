package edu.ewubd.quizzler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Date;

public class QuestionListAdapter extends ArrayAdapter<Questions> {

    private final Context context;
    private final ArrayList<Questions> values;

    public QuestionListAdapter(@NonNull Context context, @NonNull ArrayList<Questions> items) {
        super(context, -1, items);
        this.context = context;
        this.values = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.question_row, parent, false);

        TextView tvQuestion = rowView.findViewById(R.id.tvQuestion);
        TextView radioOptionA = rowView.findViewById(R.id.radioOptionA);
        TextView radioOptionB = rowView.findViewById(R.id.radioOptionB);
        TextView radioOptionC = rowView.findViewById(R.id.radioOptionC);
        TextView radioOptionD = rowView.findViewById(R.id.radioOptionD);
        TextView tvCategory = rowView.findViewById(R.id.tvCategory);
        TextView tvAnswer = rowView.findViewById(R.id.tvAnswer);

        Questions e = values.get(position);
        tvQuestion.setText("Question: " +e.question);
        radioOptionA.setText("A: " + e.optionA);
        radioOptionB.setText("B: " + e.optionB);
        radioOptionC.setText("C: " + e.optionC);
        radioOptionD.setText("D: " + e.optionD);
        tvCategory.setText("Category: " + e.category);
        tvAnswer.setText("Answer: " + e.answer);
        return rowView;
    }
}

