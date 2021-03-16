package pl.mr_electronics.showusers.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.mr_electronics.showusers.R;
import pl.mr_electronics.showusers.model.UserObj;

public class DetailsActivityView extends AppCompatActivity implements DetailsActivityContract.View {

    DetailsActivityContract.Presenter presenter;

    LinearLayout id_bar;
    TextView repo_name;
    TextView user_name;
    ImageView avatar;
    WebView webview;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        presenter = new DetailsActivityPresenter(this);

        id_bar = findViewById(R.id.id_bar);
        repo_name = findViewById(R.id.repo_name);
        user_name = findViewById(R.id.user_name);
        avatar = findViewById(R.id.avatar);
        webview = findViewById(R.id.webview);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                System.out.println("Page loaded.");
            }
        });

        Bundle b = getIntent().getExtras();
        if(b != null)
            presenter.loadUserByIndex(b.getInt("position"));

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void showUserDetails(UserObj userObj) {
        new Handler(Looper.getMainLooper()).post(() -> {
            id_bar.setBackgroundColor(userObj.isHighlighted
                    ? ContextCompat.getColor(DetailsActivityView.this, R.color.teal_200)
                    : ContextCompat.getColor(DetailsActivityView.this, R.color.white));
            repo_name.setText(userObj.repository);
            user_name.setText(userObj.name);
            avatar.setImageBitmap(userObj.avatar);
            webview.loadUrl(userObj.info);
        });
    }
}