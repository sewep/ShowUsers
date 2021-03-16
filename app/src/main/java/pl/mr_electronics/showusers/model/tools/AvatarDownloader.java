package pl.mr_electronics.showusers.model.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

import pl.mr_electronics.showusers.Globals;
import pl.mr_electronics.showusers.R;
import pl.mr_electronics.showusers.model.UserList;
import pl.mr_electronics.showusers.model.UserObj;

public class AvatarDownloader {
    int runThreads = 0;
    int downloaded = 0;
    List<BitmapCache> bitmapCaches = new ArrayList<>();

    public void downloadAvatar(UserObj userObj) {

        if (isUrlInBitmapCache(userObj.avatar_url)) {
            Log.i("userreceive","Avatar is downloadin by other thread.");
            return;
        }
        BitmapCache bitmapCache = new BitmapCache(userObj.avatar_url, null);
        bitmapCaches.add(bitmapCache);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            runThreads++;

            Bitmap bmp = getBitmapFromURL(userObj.avatar_url);
            if (bmp != null) {
                bitmapCache.bitmap = getResizedBitmap(bmp, 64, 64);
                Log.i("userreceive","Avatar download ok");
            } else {
                // Load default avatar
                Bitmap bmp2 = BitmapFactory.decodeResource(Globals.context.getResources(),
                        R.drawable.noawatar);
                bitmapCache.bitmap = getResizedBitmap(bmp2, 64, 64);
                Log.i("userreceive","Avatar default image");
            }

            downloaded++;
            System.out.println("Avatar downloaded " + downloaded);
            runThreads--;
        });
    }

    public boolean isFinish() {
        return runThreads == 0;
    }

    public void waitToEnd() {
        while (!isFinish()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.i("userreceive","Avatar image in cach: " + bitmapCaches.size());
    }

    public void assignBmpToUrlFromCache(UserList userList) {
        for (int i = 0; i < userList.getUsers().size(); i++) {
            UserObj userObj = userList.getUsers().get(i);
            userObj.avatar = findBitmapInCache(userObj.avatar_url);
        }
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpsURLConnection connection = (HttpsURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.setUseCaches(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
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
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

    boolean isUrlInBitmapCache(String url) {
        for (int i = 0; i < bitmapCaches.size(); i++) {
            if (bitmapCaches.get(i).url.equals(url)) {
                return true;
            }
        }
        return false;
    }

    Bitmap findBitmapInCache(String url) {
        for (int i = 0; i < bitmapCaches.size(); i++) {
            if (bitmapCaches.get(i).url.equals(url)) {
                Log.i("mcache", "Image from cache.");
                return bitmapCaches.get(i).bitmap;
            }
        }
        return null;
    }

    static class BitmapCache {
        public String url;
        public Bitmap bitmap;

        public BitmapCache(String url, Bitmap bitmap) {
            this.url = url;
            this.bitmap = bitmap;
        }
    }
}
