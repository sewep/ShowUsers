package pl.mr_electronics.showusers.model;

import android.util.Log;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.mr_electronics.showusers.Globals;
import pl.mr_electronics.showusers.R;
import pl.mr_electronics.showusers.model.tools.AvatarDownloader;
import pl.mr_electronics.showusers.model.tools.ResponseListener;
import pl.mr_electronics.showusers.model.tools.UserBitbucketGetter;
import pl.mr_electronics.showusers.model.tools.UserGithubGetter;

public class UserList implements ResponseListener {

    private static UserList instance = null;
    List<UserObj> users = new ArrayList<>();
    int runListDownload = 0;
    UserListCom userListCom;
    ExecutorService executor = Executors.newSingleThreadExecutor();

    private UserList() {}

    public static UserList getInstance() {
        if (instance == null) {
            instance = new UserList();
        }
        return instance;
    }

    public void downloadUsersLists() {
        executor.execute(() -> {
            if (isInternetAvailable()) {
                // Clear current list before update
                users.clear();
                runListDownload += 2;
                // Download user list from Bitbucket
                UserBitbucketGetter usersBitbucket = new UserBitbucketGetter(this);
                usersBitbucket.downloadList();
                // Download users from GitHub
                UserGithubGetter usersGithub = new UserGithubGetter(this);
                usersGithub.downloadList();
            } else {
                userListCom.loadError(Globals.context.getString(R.string.no_internet_connection));
            }
        });
    }

    public void downloadMissingAvatars() {
        AvatarDownloader downloader = new AvatarDownloader();
        for (UserObj o : users) {
            if (o.avatar == null) {
                downloader.downloadAvatar(o);
            }
        }
        downloader.waitToEnd();
        System.out.println("downloadMissingAvatars end.");
        downloader.assignBmpToUrlFromCache(this);
    }

    public void setUserListCom(UserListCom userListCom) {
        this.userListCom = userListCom;
    }

    public List<UserObj> getUsers() {
        return users;
    }

    public void sortByRepositoryName() {
        Collections.sort(users, (o1, o2) -> o1.repository.compareToIgnoreCase(o2.repository));
    }

    public void sortByUserName() {
        Collections.sort(users, (o1, o2) -> o1.name.compareToIgnoreCase(o2.name));
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.toString().equals("");

        } catch (Exception e) {
            return false;
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
            if (userListCom != null) {
                userListCom.loadListReady();
            }
        }
    }

    @Override
    public void ReceivedNewUserError(String str) {

    }
}
