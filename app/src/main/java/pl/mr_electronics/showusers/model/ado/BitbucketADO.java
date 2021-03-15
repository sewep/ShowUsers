package pl.mr_electronics.showusers.model.ado;

// Structure generator online:  https://json2csharp.com/json-to-pojo

import java.util.List;

public class BitbucketADO {
    public List<Value> values;

    public class Value{
        public Owner owner;
        public String description;
        public String name;
    }

    public class Owner{
        public String display_name;
        public String uuid;
        public Links links;
        public String nickname;
        public String type;
        public String account_id;
        public String username;
    }

    public class Self{
        public String href;
    }

    public class Html{
        public String href;
    }

    public class Avatar{
        public String href;
    }

    public class Links{
        public Self self;
        public Html html;
        public Avatar avatar;
    }
}