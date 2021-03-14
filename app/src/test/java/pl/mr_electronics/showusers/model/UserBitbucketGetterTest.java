package pl.mr_electronics.showusers.model;

import junit.framework.TestCase;

public class UserBitbucketGetterTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testDownloadList() {
        UserBitbucketGetter o = new UserBitbucketGetter();
        o.downloadList();
    }
}