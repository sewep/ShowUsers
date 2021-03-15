package pl.mr_electronics.showusers.mvp.details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import pl.mr_electronics.showusers.R;
import pl.mr_electronics.showusers.model.UserObj;

public class DetailsActivityView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        Bundle b = getIntent().getExtras();
        int position = -1;
        if(b != null)
            position = b.getInt("position");


        Log.i("DetailsActiv", "" + position);
    }
}