package top.flyfire.json.type.array;

import top.flyfire.json.base.JsonBean;
import top.flyfire.json.base.JsonGetter;
import top.flyfire.json.linked.adapter.array.JsonArrayAdapter;

/**
 * Created by flyfire[dev.lluo@outlook.com] on 2016/3/15.
 */
public class JsonArray extends JsonBean implements JsonGetter<Integer>,JsonArrayAdapter {
    @Override
    public JsonBean get(Integer integer) {
        return null;
    }

    @Override
    public void increase(int minIncrement) {

    }

    @Override
    public void add(Object object) {

    }
}
