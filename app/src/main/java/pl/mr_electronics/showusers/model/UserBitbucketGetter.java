package pl.mr_electronics.showusers.model;


import pl.mr_electronics.showusers.model.tools.RestClient;

public class UserBitbucketGetter extends RestClient {

    public void downloadList() {
        sendGet("https://api.bitbucket.org/2.0/repositories?fields=values.name,values.owner,values.description");
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
