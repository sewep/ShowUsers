package pl.mr_electronics.showusers.mvp.details;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import pl.mr_electronics.showusers.R;
import pl.mr_electronics.showusers.model.UserList;
import pl.mr_electronics.showusers.model.UserObj;

public class DetailsActivityView extends AppCompatActivity implements DetailsActivityContract.View {

    DetailsActivityContract.Presenter presenter;

    TextView repo_name;
    TextView user_name;
    ImageView avatar;
    TextView info;
    WebView webview;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        presenter = new DetailsActivityPresenter(this);

        repo_name = findViewById(R.id.repo_name);
        user_name = findViewById(R.id.user_name);
        avatar = findViewById(R.id.avatar);
        info = findViewById(R.id.info);
        webview = findViewById(R.id.webview);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //progDailog.show();
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                //progDailog.dismiss();
                System.out.println("Page loaded.");
            }
        });


        // https://bitbucket.org/%7Ba288a0ab-e13b-43f0-a689-c4ef0a249875%7D/
        webview.loadUrl("https://bitbucket.org/%7Ba288a0ab-e13b-43f0-a689-c4ef0a249875%7D/");

        Bundle b = getIntent().getExtras();
        if(b != null)
            presenter.loadUserByIndex(b.getInt("position"));

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void showUserDetails(UserObj userObj) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                repo_name.setText(userObj.reposytory);
                user_name.setText(userObj.name);
                avatar.setImageBitmap(userObj.avatar);
                info.setText(userObj.info);
                webview.loadUrl(userObj.info);
            }
        });
    }
}