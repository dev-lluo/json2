package top.flyfire.json.type.primitive;

import top.flyfire.json.JsonBean;
import top.flyfire.json.JsonSign;
import top.flyfire.json.JsonString;

/**
 * Created by flyfire[dev.lluo@outlook.com] on 2016/3/15.
 */
public class JsonPrimitive extends JsonBean {

    public static final JsonPrimitive NULL = new JsonPrimitive(){

        @Override
        public String toString() {
            return JsonSign.NULL;
        }
    };

    private static final char[] EMPTY = new char[0];


    private char[] val;

    public JsonPrimitive(JsonString jsonString){
        this.val = jsonString.realVal();
    }

    public JsonPrimitive() {
        this.val = EMPTY;
    }

    @Override
    public String toString() {
        return new String(val);
    }

    @Override
    public void increase(int minIncrement) {

    }
}
