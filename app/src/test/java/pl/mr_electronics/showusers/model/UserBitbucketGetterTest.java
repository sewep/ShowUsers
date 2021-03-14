package pl.mr_electronics.showusers.model;

import junit.framework.TestCase;

import java.io.InputStream;

import pl.mr_electronics.showusers.model.tools.Converters;
import pl.mr_electronics.showusers.model.tools.UserGithubGetter;

public class UserBitbucketGetterTest extends TestCase {

    UserList uList;

    public void setUp() throws Exception {
        super.setUp();

        uList = new UserList();
    }

    public void testDownloadList() {
        UserBitbucketGetter o = new UserBitbucketGetter(uList);
        o.downloadList();
    }

    public void testParseResponse() {
        // Download example data
        InputStream isBit = UserBitbucketGetterTest.class.getResourceAsStream("/bitbucket.json");
        String strBit = Converters.convertStreamToString(isBit);
        InputStream isGit = UserBitbucketGetterTest.class.getResourceAsStream("/github.json");
        String strGit = Converters.convertStreamToString(isGit);

        UserBitbucketGetter o = new UserBitbucketGetter(uList);
        o.responseMessage(strBit);

        UserGithubGetter g = new UserGithubGetter(uList);
        g.responseMessage(strGit);

        System.out.println("UserList count = " + uList.users.size());
    }
}