package pl.mr_electronics.showusers.mvp.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.List;

import pl.mr_electronics.showusers.Globals;
import pl.mr_electronics.showusers.R;
import pl.mr_electronics.showusers.controls.ListUsers;
import pl.mr_electronics.showusers.model.UserObj;

public class MainActivityView extends AppCompatActivity implements MainActivityContract.View {

    MainActivityContract.Presenter presenter;
    ListView list_view;
    ProgressBar progressBar;
    Spinner sort_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        list_view = findViewById(R.id.list_view);
        progressBar = findViewById(R.id.progressBar);
        sort_select = findViewById(R.id.sort_select);

        Globals.context = this;
        presenter = new MainActivityPresenter(this);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.showDetailsUser(position);
            }
        });
        sort_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.selectSortMethod(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showLoadingStatus() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
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