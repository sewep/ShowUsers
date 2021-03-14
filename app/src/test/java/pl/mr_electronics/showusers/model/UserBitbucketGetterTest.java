package pl.mr_electronics.showusers.model;

import junit.framework.TestCase;

import java.io.InputStream;
import java.net.URL;

import pl.mr_electronics.showusers.model.tools.Converters;

public class UserBitbucketGetterTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testDownloadList() {
        UserBitbucketGetter o = new UserBitbucketGetter();
        o.downloadList();
    }

    public void testParseResponse() {
        InputStream is = UserBitbucketGetterTest.class.getResourceAsStream("/bitbucket.json");
        String str = Converters.convertStreamToString(is);

        UserBitbucketGetter o = new UserBitbucketGetter();
        o.parseResponse(str);
    }
}