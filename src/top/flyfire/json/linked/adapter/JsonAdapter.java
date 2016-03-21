package top.flyfire.json.linked.adapter;

import top.flyfire.json.base.JsonException;
import top.flyfire.json.base.JsonIns;
import top.flyfire.json.base.JsonSign;
import top.flyfire.json.base.JsonString;
import top.flyfire.json.linked.adapter.array.JsonArrayAdapter;
import top.flyfire.json.linked.adapter.object.JsonObjectAdapter;
import top.flyfire.json.type.array.JsonArray;
import top.flyfire.json.type.object.JsonObject;

/**
 * Created by shyy_work on 2016/3/21.
 */
public class JsonAdapter {

    private JsonString jsonString ;

    private int destIndex ;

    private int upBound ;

    private Stack stack ;

    public JsonAdapter(JsonString jsonString) {
        this.jsonString = jsonString;
        this.stack = new Stack();
        this.upBound = jsonString.length()-1;
    }

    public void run(){
        this.discriminate();
        if(JsonIns.isObject(this.stack.peek())){
            JsonObjectAdapter jsonObjectAdapter = new JsonObject();
            while (this.hasNextEntryOrEndObject()){
                jsonObjectAdapter.set(this.readProperty(),this.readValue());
            }
        }else if(JsonIns.isArray(this.stack.peek())){
            JsonArrayAdapter jsonArrayAdapter = new JsonArray();
            while(this.hasNextCellOrEndArray()){
                jsonArrayAdapter.add(this.readCell());
            }
        }
    }

    public void discriminate(){
        char dest = this.jsonString.charAt(destIndex++);
        if (JsonSign.isObjectStart(dest)) {
            this.stack.push(JsonIns.Object);
        } else if (JsonSign.isArrayStart(dest)) {
            this.stack.push(JsonIns.Array);
        } else {
            this.stack.push(JsonIns.Primitive);
        }
    }

    public boolean hasNextEntryOrEndObject(){
        char dest = this.jsonString.charAt(destIndex++);
        if(JsonSign.isObjectEnd(dest)){
            this.stack.popAndValidate(JsonIns.Object);
            return false;
        }else{
            return true;
        }
    }

    public JsonString readProperty(){
        return null;
    }

    public Object readValue(){
        return null;
    }


    public boolean hasNextCellOrEndArray(){
        char dest = this.jsonString.charAt(destIndex++);
        if(JsonSign.isArrayEnd(dest)){
            this.stack.popAndValidate(JsonIns.Array);
            return false;
        }else{
            return true;
        }
    }

    public Object readCell(){
        return null;
    }


    class Stack {
        private StackCell top;

        public void push(int stackVal){
            top = new StackCell(top,stackVal);
        }

        public int popAndValidate(int ins){
            if(this.isEmpty()){
                return -1;
            }else {
                int stackVal = this.top.stackVal;
                if((stackVal&ins)==stackVal) {
                    StackCell newTop = top.next;
                    top.destory();
                    top = newTop;
                    return stackVal;
                }else{
                    throw new JsonException("UnExpectGroup( start:"+stackVal+",end:"+ins+")");
                }
            }
        }

        public int peek(){
            if(this.isEmpty()){
                return -1;
            }else{
                return this.top.stackVal;
            }
        }

        public boolean isEmpty(){
            return top==null;
        }
    }

    class StackCell{
        private StackCell next;

        private int stackVal;

        public StackCell(StackCell next, int stackVal) {
            this.next = next;
            this.stackVal = stackVal;
        }

        public void destory(){
            this.next = null;
            this.stackVal = -1;
        }
    }


}
