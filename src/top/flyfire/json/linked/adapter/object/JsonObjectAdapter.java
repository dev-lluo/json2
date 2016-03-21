package top.flyfire.json.linked.adapter.object;

import top.flyfire.json.base.JsonString;

/**
 * Created by flyfire[dev.lluo@outlook.com] on 2016/3/15.
 */
public interface JsonObjectAdapter {

    void set(JsonString property,Object object);


    interface EntryAdapter{

        void setProperty(JsonString property);

        void setValue(Object object);

    }


}
