package gent.zeus.tappb.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import gent.zeus.tappb.R;
import gent.zeus.tappb.entity.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // User.getInstance().load("Test","a","b");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
