package top.flyfire.json.type.object;

import top.flyfire.json.base.JsonBean;
import top.flyfire.json.base.JsonGetter;
import top.flyfire.json.linked.adapter.object.JsonObjectAdapter;

/**
 * Created by flyfire[dev.lluo@outlook.com] on 2016/3/15.
 */
public class JsonObject extends JsonBean implements JsonGetter<String>,JsonObjectAdapter {
    @Override
    public JsonBean get(String s) {
        return null;
    }

    @Override
    public void increase(int minIncrement) {

    }

    @Override
    public void set(String property, Object object) {

    }

    public class Entry implements EntryAdapter{
        @Override
        public void setValue(Object object) {

        }

        @Override
        public void setProperty(String property) {

        }
    }

}
