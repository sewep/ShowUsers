package pl.mr_electronics.showusers.model;


import java.util.List;

import pl.mr_electronics.showusers.model.tools.RestClient;

public class UserBitbucketGetter extends RestClient {

    public void downloadList() {
        sendGet("https://api.bitbucket.org/2.0/repositories?fields=values.name,values.owner,values.description");
    }

    public List<UserObj> parseResponse(String msg) {
        return null;
    }



    @Override
    public void responseMessage(String msg) {
        System.out.println("RECEIVED " + msg.length() + " : " + msg);
    }

    @Override
    public void responseError(String log) {
        System.out.println("ERROR: " + log);
    }
}
