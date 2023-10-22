package edu.ewubd.quizzler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private String errMessage = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        try {
            if (sharedPreferences.getBoolean("isRememberMe", false)) {
                startActivity(new Intent(LoginActivity.this, CategoryActivity.class));
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        findViewById(R.id.signInId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ((EditText) findViewById(R.id.emailId)).getText().toString().trim();
                String password = ((EditText) findViewById(R.id.passwordId)).getText().toString().trim();
                boolean cbRememberMe = ((CheckBox) findViewById(R.id.rememberMe)).isChecked();

                if ("admin@gmail.com".equals(email) && "1111".equals(password)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (validateLoginCredentials(email, password)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isRememberMe", cbRememberMe);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showErrorDialog(errMessage);
                }
            }
        });

        findViewById(R.id.SignupTextId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private boolean validateLoginCredentials(String email, String password) {
        errMessage = "";
        if (email.isEmpty() || password.isEmpty()) {
            errMessage += "All fields are required.\n---------------------\n";
        }

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)) {
            errMessage += "Invalid email format.\n";
        }

        if (password.length() < 4 || password.length() > 6) {
            errMessage += "Password should be 4-6 characters long.\n";
        }

        if (errMessage.isEmpty()) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            if (!sharedPreferences.getBoolean("isRememberMe", false)) {
                String keys[] = {"action","email"};
                String values[] = {"login", email};
                httpRequest(keys, values);
            }
            if (email.equals(sharedPreferences.getString("email", "")) && password.equals(sharedPreferences.getString("password", ""))) {
                return true;
            } else {
                errMessage += "Invalid password.\n";
                return false;
            }
        } else {
            return false;
        }
    }

    private void showErrorDialog(String errMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(errMessage);
        builder.setTitle("Error");
        builder.setCancelable(true);

        builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
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
                    getUserDataByServerData(data);
                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                } else{
                    System.out.println("No data found");
                }
            }
        }.execute();
    }
    private void getUserDataByServerData(String data) {
        System.out.println("found");
        try {
            JSONObject jo = new JSONObject(data);
            String msg = jo.getString("msg");

            if ("OK".equals(msg)) {
                JSONArray userArray = jo.getJSONArray("user");
                JSONObject user = userArray.getJSONObject(0);
                String name = user.getString("name");
                String email = user.getString("email");
                String mobile = user.getString("mobile");
                String password = user.getString("password");
                String score = user.getString("score");

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("name", name);
                editor.putString("email", email);
                editor.putString("mobile", mobile);
                editor.putString("password", password);
                editor.putString("score", score);
                editor.apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}