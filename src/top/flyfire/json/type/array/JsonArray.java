package top.flyfire.json.type.array;

import top.flyfire.json.JsonBean;
import top.flyfire.json.JsonGetter;
import top.flyfire.json.linked.adapter.array.JsonArrayAdapter;

/**
 * Created by flyfire[dev.lluo@outlook.com] on 2016/3/15.
 */
public class JsonArray extends JsonBean implements JsonGetter<Integer>,JsonArrayAdapter {

    private static final int DEFAULT_CAP = 10;

    private static final int DEFAULT_GROWTH = 10;

    private int length;

    private JsonBean[] cells;

    public JsonArray() {
        this.length = 0;
        this.cells = new JsonBean[JsonArray.DEFAULT_CAP];
    }

    @Override
    public JsonBean get(Integer integer) {
        return this.cells[integer];
    }

    @Override
    public void increase(int minIncrement) {
        int expect_length = this.length+minIncrement;
        if(expect_length>0&&expect_length<=Integer.MAX_VALUE) {
            JsonBean[] new_container = new JsonBean[expect_length];
            System.arraycopy(this.cells,0,new_container,0,this.length);
            this.cells = new_container;
        }else{
            throw new IllegalStateException();
        }
    }

    @Override
    public void add(Object object) {
        if(this.length==this.cells.length) {
            this.increase(DEFAULT_GROWTH);
        }
        this.cells[this.length++] = (JsonBean)object;
    }

    public int length(){
        return this.length;
    }
}
