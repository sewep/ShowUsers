package pl.mr_electronics.showusers.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.mr_electronics.showusers.model.tools.AvatarDownloader;
import pl.mr_electronics.showusers.model.tools.ResponseListener;
import pl.mr_electronics.showusers.model.tools.UserBitbucketGetter;
import pl.mr_electronics.showusers.model.tools.UserGithubGetter;

public class UserList implements ResponseListener {

    List<UserObj> users = new ArrayList<>();
    int runListDownload = 0;
    UserListCom userListCom;

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

    public void downloadMissingAvatars() {
        AvatarDownloader downloader = new AvatarDownloader();
        for (UserObj o : users) {
            if (o.avatar == null) {
                downloader.downloadAvatar(o);
            }
        }
        downloader.waitToEnd();
        System.out.println("downloadMissingAvatars end.");
    }

    public void setUserListCom(UserListCom userListCom) {
        this.userListCom = userListCom;
    }

    public List<UserObj> getUsers() {
        return users;
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
    public void ReceviedNewUserError(String str) {

    }
}
