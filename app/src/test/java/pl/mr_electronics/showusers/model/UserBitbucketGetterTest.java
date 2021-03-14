package pl.mr_electronics.showusers.model;

import junit.framework.TestCase;

import java.io.InputStream;

import pl.mr_electronics.showusers.model.tools.Converters;

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
        InputStream is = UserBitbucketGetterTest.class.getResourceAsStream("/bitbucket.json");
        String str = Converters.convertStreamToString(is);

        UserBitbucketGetter o = new UserBitbucketGetter(uList);
        o.responseMessage(str);

        System.out.println("UserList count = " + uList.users.size());
    }
}