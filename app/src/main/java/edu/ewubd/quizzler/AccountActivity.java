package edu.ewubd.quizzler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {
    private ImageView image;
    private TextView total_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        image = findViewById(R.id.image);
        total_score = findViewById(R.id.total_score);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(sharedPreferences.getString("name", ""));

        String name = sharedPreferences.getString("name", "");
        char firstLetter = name.isEmpty() ? 'A' : name.charAt(0);
        Bitmap circularTextImage = createCircularTextImage(firstLetter);
        image.setImageBitmap(circularTextImage);

        TextView tvREmail = (TextView) findViewById(R.id.tvREmail);
        tvREmail.setText(sharedPreferences.getString("email", ""));

        TextView tvMobile = (TextView) findViewById(R.id.tvMobile);
        tvMobile.setText(sharedPreferences.getString("mobile", ""));

        total_score.setText(sharedPreferences.getString("score", ""));

        LinearLayout profileTextView = findViewById(R.id.profile);
        profileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_edit_profile, null);

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;

                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                popupView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        int highlightedItemId = getIntent().getIntExtra("highlightItem", 0);
        if (highlightedItemId != 0) {
            bottomNavigationView.setSelectedItemId(highlightedItemId);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_item1) {
                    Intent intent = new Intent(AccountActivity.this, CategoryActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item1);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.action_item2) {
                    Intent intent = new Intent(AccountActivity.this, LeaderboardActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item2);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.logoutB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isRememberMe", false);
                editor.apply();
                Intent i = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private Bitmap createCircularTextImage(char firstLetter) {
        int size = 200;
        Bitmap image = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);

        // Set background color and paint attributes
        canvas.drawColor(Color.parseColor("#FF5722"));
        Paint paint = new Paint();
        paint.setColor(Color.WHITE); // Text color
        paint.setTextSize(100); // Text size
        paint.setAntiAlias(true);

        // Calculate the text position to center it in the circle
        Rect bounds = new Rect();
        String text = String.valueOf(firstLetter);
        paint.getTextBounds(text, 0, text.length(), bounds);
        float x = (size - bounds.width()) / 2f;
        float y = (size + bounds.height()) / 2f;

        // Draw the text on the canvas
        canvas.drawText(text, x, y, paint);

        // Create a circular mask for the image
        Bitmap mask = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas maskCanvas = new Canvas(mask);
        maskCanvas.drawCircle(size / 2f, size / 2f, size / 2f, new Paint(Paint.ANTI_ALIAS_FLAG));

        // Apply the circular mask to the image
        Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas resultCanvas = new Canvas(result);
        resultCanvas.drawBitmap(image, 0, 0, null);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        resultCanvas.drawBitmap(mask, 0, 0, paint);

        return result;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AccountActivity.this, CategoryActivity.class);
        intent.putExtra("highlightItem", R.id.action_item1);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}