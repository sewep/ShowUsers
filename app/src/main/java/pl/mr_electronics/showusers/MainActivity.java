package pl.mr_electronics.showusers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pl.mr_electronics.showusers.model.UserBitbucketGetter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserBitbucketGetter u = new UserBitbucketGetter();
        u.downloadList();

    }
}