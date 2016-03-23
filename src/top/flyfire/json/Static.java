package top.flyfire.json;

import top.flyfire.json.linked.adapter.JsonAdapter;

/**
 * Created by flyfire[dev.lluo@outlook.com] on 2016/3/15.
 */
public interface Static {

    static JsonBean json2Java (String json){
        return new JsonAdapter(new JsonString(json)).run().result();
    }

    static JsonString java2Json(String json){
        return null;
    }

}
