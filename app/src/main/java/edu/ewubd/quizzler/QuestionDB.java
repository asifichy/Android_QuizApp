package edu.ewubd.quizzler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuestionDB extends SQLiteOpenHelper {

	public QuestionDB(Context context) {

		super(context, "QuestionDB.db", null, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE questions  ("
				+ "ID TEXT PRIMARY KEY,"
				+ "Question TEXT,"
				+ "OptionA TEXT,"
				+ "OptionB TEXT,"
				+ "OptionC TEXT,"
				+ "OptionD TEXT,"
				+ "Answer TEXT,"
				+ "Category TEXT"
				+ ")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Implement this method if you need to modify the database schema in the future.
	}

	public long insertQuestion(String ID, String question, String optionA, String optionB, String optionC, String optionD, String answer, String category) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("ID", ID);
		values.put("Question", question);
		values.put("OptionA", optionA);
		values.put("OptionB", optionB);
		values.put("OptionC", optionC);
		values.put("OptionD", optionD);
		values.put("Answer", answer);
		values.put("Category", category);

		long newRowId = db.insert("questions", null, values);
		db.close();
		return newRowId;
	}

	public int updateQuestion(String ID, String question, String optionA, String optionB, String optionC, String optionD, String answer, String category) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("ID", ID);
		values.put("Question", question);
		values.put("OptionA", optionA);
		values.put("OptionB", optionB);
		values.put("OptionC", optionC);
		values.put("OptionD", optionD);
		values.put("Answer", answer);
		values.put("Category", category);

		int rowsAffected = db.update("questions", values, "ID=?", new String[]{ID});
		db.close();
		return rowsAffected;
	}

	public void deleteQuestion(String ID) {
		SQLiteDatabase db = this.getWritableDatabase();
		System.out.println("-----delete------------");
		System.out.println(ID);
		db.delete("questions", "ID=?", new String[ ] {ID} );
		db.close();
	}

	public Cursor selectQuestionsByCategory(String category) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = null;
		try {
			String query = "SELECT * FROM questions WHERE Category = ?";
			res = db.rawQuery(query, new String[]{category});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public Cursor selectQuestions(String query) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res=null;
		try {
			res = db.rawQuery(query, null);
		} catch (Exception e){
			e.printStackTrace();
		}
		return res;
	}
}