package pl.mr_electronics.showusers.model.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.provider.Settings;
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
import pl.mr_electronics.showusers.model.UserObj;

public class AvatarDownloader {
    int runThreads = 0;
    int downloaded = 0;
    List<BitmapCache> bitmapCaches = new ArrayList<>();

    public void downloadAvatar(UserObj userObj) {
        runThreads++;

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap bmp = findBitmapInCache(userObj.avatar_url);
                boolean addToCache = false;
                if (bmp == null) {
                    bmp = getBitmapFromURL(userObj.avatar_url);
                    addToCache = true;
                }
                if (bmp != null) {
                    userObj.avatar = getResizedBitmap(bmp, 64, 64);
                    System.out.println("Avatar download ok");
                    Log.i("userreceive","Avatar download ok");
                } else {
                    // Load default avatar
                    Bitmap bmp2 = BitmapFactory.decodeResource(Globals.context.getResources(),
                            R.drawable.noawatar);
                    userObj.avatar = getResizedBitmap(bmp2, 64, 64);
                    System.out.println("Avatar default image");
                    Log.i("userreceive","Avatar default image");
                }
                if (addToCache && bmp != null) {
                    bitmapCaches.add(new BitmapCache(userObj.avatar_url, bmp));
                }

                downloaded++;
                System.out.println("Avatar downloaded " + downloaded);
                runThreads--;
            }
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

    Bitmap findBitmapInCache(String url) {
        for (int i = 0; i < bitmapCaches.size(); i++) {
            if (bitmapCaches.get(i).url.equals(url)) {
                Log.i("mcache", "Image from cache.");
                return bitmapCaches.get(i).bitmap;
            }
        }
        return null;
    }

    class BitmapCache {
        public String url;
        public Bitmap bitmap;

        public BitmapCache(String url, Bitmap bitmap) {
            this.url = url;
            this.bitmap = bitmap;
        }
    }
}
