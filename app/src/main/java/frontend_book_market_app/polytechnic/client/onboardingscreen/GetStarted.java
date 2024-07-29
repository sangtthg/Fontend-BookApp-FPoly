package frontend_book_market_app.polytechnic.client.onboardingscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import frontend_book_market_app.polytechnic.client.MainActivity;
import frontend_book_market_app.polytechnic.client.R;

public class GetStarted extends AppCompatActivity {
    Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetStarted.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}