package pl.mr_electronics.showusers.mvp.main;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import pl.mr_electronics.showusers.Globals;
import pl.mr_electronics.showusers.R;
import pl.mr_electronics.showusers.controls.ListUsers;
import pl.mr_electronics.showusers.model.UserList;
import pl.mr_electronics.showusers.model.UserObj;

public class MainActivityView extends AppCompatActivity implements MainActivityContract.View {

    MainActivityContract.Presenter presenter;
    ListView list_view;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        list_view = findViewById(R.id.list_view);
        progressBar = findViewById(R.id.progressBar);

        Globals.context = this;
        presenter = new MainActivityPresenter(this);

        Bitmap bmp1 = BitmapFactory.decodeResource(Globals.context.getResources(),
                R.drawable.noawatar);
        // Test
        List<UserObj> users = new ArrayList<>();
        UserObj o1 = new UserObj();
        o1.name = "Test 1";
        o1.reposytory = "rep";
        o1.avatar = bmp1;
        users.add(o1);

        ListUsers adapter = new ListUsers(this, R.id.list_view, users);
        //String[] listItem = new String[] {"one", "two", "three"};
        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        list_view.setAdapter(adapter);
    }

    @Override
    public void showLoadingStatus() {

    }

    @Override
    public void showList(List<UserObj> users) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                ListUsers adapter = new ListUsers(MainActivityView.this, R.id.list_view, users);
                list_view.setAdapter(adapter);
            }
        });

    }
}