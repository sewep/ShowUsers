package pl.mr_electronics.showusers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pl.mr_electronics.showusers.model.UserList;

public class MainActivity extends AppCompatActivity {

    UserList users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Globals.context = this;

        users = new UserList();
        users.downloadUsersLists();



    }
}