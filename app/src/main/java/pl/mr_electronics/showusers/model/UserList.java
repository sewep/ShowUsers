package pl.mr_electronics.showusers.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import pl.mr_electronics.showusers.model.tools.ResponseListener;
import pl.mr_electronics.showusers.model.tools.UserBitbucketGetter;
import pl.mr_electronics.showusers.model.tools.UserGithubGetter;

public class UserList implements ResponseListener {

    List<UserObj> users = new ArrayList<>();
    int runListDownload = 0;

    public void downloadUsersLists() {
        // Clear current list before update
        users.clear();
        // Download user list from Bitbucket
        runListDownload++;
        UserBitbucketGetter usersBitbucket = new UserBitbucketGetter(this);
        usersBitbucket.downloadList();
        // Download users from GitHub
        runListDownload++;
        UserGithubGetter usersGithub = new UserGithubGetter(this);
        usersGithub.downloadList();
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpsURLConnection connection = (HttpsURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);

        return resizedBitmap;
    }

    public void downloadMissingAvatars() {
        for (UserObj o : users) {
            if (o.avatar == null) {
                Bitmap bmp = getBitmapFromURL(o.avatar_url);
                if (bmp != null) {
                    o.avatar = getResizedBitmap(bmp, 64, 64);
                    System.out.println("Avatar download ok");
                    Log.i("userreceive","Avatar download ok");
                } else {
                    //TODO: Load default avatar
                    System.out.println("Avatar default image");
                    Log.i("userreceive","Avatar default image");
                }
            }
        }
    }

    @Override
    public void ReceiveNewUserList(List<UserObj> list) {
        users.addAll(list);
        System.out.println("Receve new " + list + " users.");
        Log.i("userreceive","list update");
        runListDownload--;
        if (runListDownload == 0) {
            downloadMissingAvatars();
            Log.i("userreceive","avatars is update");
        }
    }

    @Override
    public void ReceviedNewUserError(String str) {

    }
}
