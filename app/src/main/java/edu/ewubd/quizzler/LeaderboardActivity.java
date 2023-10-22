package edu.ewubd.quizzler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {
    private ListView listLeaderboard;
    private ArrayList<LeaderboardItem> leaderboard;
    private LeaderboardAdapter leaderboardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        listLeaderboard = findViewById(R.id.listLeaderboard);
        leaderboard = new ArrayList<>();

        leaderboardAdapter = new LeaderboardAdapter(this, leaderboard);
        listLeaderboard.setAdapter(leaderboardAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);


        int highlightedItemId = getIntent().getIntExtra("highlightItem", 0);
        if (highlightedItemId != 0) {
            bottomNavigationView.setSelectedItemId(highlightedItemId);
        }

        for (int i=0; i<10; i++) {
            LeaderboardItem e = new LeaderboardItem("Mithun", "120", "1000");
            leaderboard.add(e);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_item1) {
                    Intent intent = new Intent(LeaderboardActivity.this, CategoryActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item1);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.action_item3) {
                    Intent intent = new Intent(LeaderboardActivity.this, AccountActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item3);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LeaderboardActivity.this, CategoryActivity.class);
        intent.putExtra("highlightItem", R.id.action_item1);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}