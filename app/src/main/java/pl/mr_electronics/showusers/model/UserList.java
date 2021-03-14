package pl.mr_electronics.showusers.model;

import java.util.ArrayList;
import java.util.List;

import pl.mr_electronics.showusers.model.tools.ResponseListener;
import pl.mr_electronics.showusers.model.tools.UserGithubGetter;

public class UserList implements ResponseListener {

    List<UserObj> users = new ArrayList<>();

    public void downloadUsersLists() {
        // Clear current list before update
        users.clear();
        // Download user list from Bitbucket
        UserBitbucketGetter usersBitbucket = new UserBitbucketGetter(this);
        usersBitbucket.downloadList();
        //TODO: Download users from GitHub
        UserGithubGetter usersGithub = new UserGithubGetter(this);
        usersGithub.downloadList();
    }


    @Override
    public void ReceiveNewUserList(List<UserObj> list) {
        users.addAll(list);
        System.out.println("Receve new " + list + " users.");
    }

    @Override
    public void ReceviedNewUserError(String str) {

    }
}
