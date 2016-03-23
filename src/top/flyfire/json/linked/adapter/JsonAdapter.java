package top.flyfire.json.linked.adapter;

import top.flyfire.json.*;
import top.flyfire.json.linked.adapter.array.JsonArrayAdapter;
import top.flyfire.json.linked.adapter.object.JsonObjectAdapter;
import top.flyfire.json.type.array.JsonArray;
import top.flyfire.json.type.object.JsonObject;
import top.flyfire.json.type.primitive.JsonPrimitive;

/**
 * Created by shyy_work on 2016/3/21.
 */
public class JsonAdapter {

    private JsonString jsonString ;

    private int destIndex ;

    private int upBound ;

    private Stack stack ;

    private JsonBean result;

    public JsonAdapter(JsonString jsonString) {
        this.jsonString = jsonString;
        this.stack = new Stack();
        this.upBound = jsonString.length()-1;
    }

    public JsonAdapter run(){
        this.discriminate();
        if(JsonIns.isObject(this.stack.peek())){
            JsonObjectAdapter jsonObjectAdapter = new JsonObject();
            while (this.hasNextEntryOrEndObject()){
                jsonObjectAdapter.set(this.readProperty(),this.readValue());
            }
            this.result = (JsonBean)jsonObjectAdapter;
        }else if(JsonIns.isArray(this.stack.peek())){
            JsonArrayAdapter jsonArrayAdapter = new JsonArray();
            while(this.hasNextCellOrEndArray()){
                jsonArrayAdapter.add(this.readCell());
            }
            this.result = (JsonBean)jsonArrayAdapter;
        }else if(JsonIns.isJsonEnd(this.stack.peek())){
            this.result = JsonPrimitive.NULL;
        }else {
            this.result = (JsonBean)this.readPrimitive();
        }

        return this;
    }

    public <T extends JsonBean> T result(){
        return result.C();
    }


    private boolean hasNextEntryOrEndObject(){
        if(this.hasMoreChars()) {
            char dest = this.dest();
            if (JsonSign.isObjectEnd(dest)) {
                this.stack.popAndValidate(JsonIns.Object);
                return false;
            } else {

                return true;
            }
        }else{
            throw new JsonException("unexpected end of json[behavior mode:hasNextEntryOrEndObject]");
        }
    }

    private JsonString readProperty(){
        int start = -1;
        int end = -1;
        if(this.hasMoreChars()){
            char dest = dest();
            if(JsonSign.isDQuote(dest)){
                this.stack.push(JsonIns.ObjectPD);
                start = this.destIndex-1;
                while (this.hasMoreChars()){
                    dest = dest();
                    if(JsonSign.isDQuote(dest)){
                        this.stack.popAndValidate(JsonIns.ObjectPD);
                        end = this.destIndex-1;
                        break;
                    }
                }
                this.readP2V();
                return this.jsonString.subSequence(start,end);
            }else if(JsonSign.isSQuote(dest)){
                this.stack.push(JsonIns.ObjectPS);
                start = this.destIndex-1;
                while (this.hasMoreChars()){
                    dest = dest();
                    if(JsonSign.isSQuote(dest)){
                        this.stack.popAndValidate(JsonIns.ObjectPD);
                        end = this.destIndex-1;
                        break;
                    }
                }
                this.readP2V();
                return this.jsonString.subSequence(start,end);
            }else{
                this.stack.push(JsonIns.ObjectP);
                start = this.destIndex-1;
                end = this.readP2V()-1;
                return this.jsonString.subSequence(start,end);
            }
        }else{
            throw new JsonException("unexpected end of json[behavior mode:readProperty]");
        }
    }

    private int readP2V(){
        while (this.hasMoreChars()){
            char dest = dest();
            if(JsonSign.isSpace(dest)){
               continue;
            }else if(JsonSign.isComma(dest)){
                return this.destIndex;
            }else{
                throw new JsonException("unexpected end of json[behavior mode:readP2V]");
            }
        }
        throw new JsonException("unexpected end of json[behavior mode:readP2V]");
    }

    private Object readValue(){
        this.discriminate();
        if(JsonIns.isObject(this.stack.peek())){
            JsonObjectAdapter jsonObjectAdapter = new JsonObject();
            while (this.hasNextEntryOrEndObject()){
                jsonObjectAdapter.set(this.readProperty(),this.readValue());
            }
            return jsonObjectAdapter;
        }else if(JsonIns.isArray(this.stack.peek())){
            JsonArrayAdapter jsonArrayAdapter = new JsonArray();
            while(this.hasNextCellOrEndArray()){
                jsonArrayAdapter.add(this.readCell());
            }
            return jsonArrayAdapter;
        }else {
            return this.readPrimitive();
        }
    }


    private boolean hasNextCellOrEndArray(){
        if(this.hasMoreChars()) {
            char dest = this.dest();
            if (JsonSign.isArrayEnd(dest)) {
                this.stack.popAndValidate(JsonIns.Array);
                return false;
            } else {
                return true;
            }
        }else{
            throw new JsonException("unexpected end of json[behavior mode:hasNextCellOrEndArray]");
        }
    }

    private Object readCell(){
        this.discriminate();
        if(JsonIns.isObject(this.stack.peek())){
            JsonObjectAdapter jsonObjectAdapter = new JsonObject();
            while (this.hasNextEntryOrEndObject()){
                jsonObjectAdapter.set(this.readProperty(),this.readValue());
            }
            return jsonObjectAdapter;
        }else if(JsonIns.isArray(this.stack.peek())){
            JsonArrayAdapter jsonArrayAdapter = new JsonArray();
            while(this.hasNextCellOrEndArray()){
                jsonArrayAdapter.add(this.readCell());
            }
            return jsonArrayAdapter;
        }else {
            return this.readPrimitive();
        }
    }

    private Object readPrimitive(){
        if(this.hasMoreChars()) {
            int start = this.destIndex - 1;
            int end = -1;
            if (JsonIns.isPrimitivePD(this.stack.peek())) {
                while (this.hasMoreChars()){
                    char dest = dest();
                    if(JsonSign.isDQuote(dest)){
                        this.stack.popAndValidate(JsonIns.PrimitivePD);
                        end = this.destIndex-1;
                        break;
                    }
                }
                return new JsonPrimitive(this.jsonString.subSequence(start,end));
            } else if (JsonIns.isPrimitivePS(this.stack.peek())) {
                while (this.hasMoreChars()){
                    char dest = dest();
                    if(JsonSign.isSQuote(dest)){
                        this.stack.popAndValidate(JsonIns.PrimitivePS);
                        end = this.destIndex-1;
                        break;
                    }
                }
                return new JsonPrimitive(this.jsonString.subSequence(start,end));
            } else if (JsonIns.isPrimitive(this.stack.peek())) {
                this.stack.popAndValidate(JsonIns.Primitive);
                if(this.stack.isEmpty()){
                    end = upBound;
                }else if(JsonIns.isObject(this.stack.peek())){
                    while (this.hasMoreChars()){
                        char dest = dest();
                        if(JsonSign.isComma(dest)||JsonSign.isObjectEnd(dest)){
                            end = this.destIndex-1;
                            break;
                        }
                    }
                }else if(JsonIns.isArray(this.stack.peek())){
                    while (this.hasMoreChars()){
                        char dest = dest();
                        if(JsonSign.isComma(dest)||JsonSign.isArrayEnd(dest)){
                            end = this.destIndex-1;
                            break;
                        }
                    }
                }else{
                    throw new JsonException("unexpected behavior of json[stack value:" + this.stack.peek() + "]");
                }
                return new JsonPrimitive(this.jsonString.subSequence(start,end));
            } else {
                throw new JsonException("unexpected behavior of json[stack value:" + this.stack.peek() + "]");
            }
        }else{
            throw new JsonException("unexpected end of json[behavior mode:readPrimitive]");
        }
    }

    private void discriminate(){
        if(this.hasMoreChars()) {
            char dest = this.dest();
            if (JsonSign.isObjectStart(dest)) {
                this.stack.push(JsonIns.Object);
            } else if (JsonSign.isArrayStart(dest)) {
                this.stack.push(JsonIns.Array);
            } else if (JsonSign.isDQuote(dest)){
                this.stack.push(JsonIns.PrimitivePD);
            } else if (JsonSign.isSQuote(dest)){
                this.stack.push(JsonIns.PrimitivePS);
            } else {
                this.stack.push(JsonIns.Primitive);
            }
        }else{
            this.stack.push(JsonIns.JsonEnd);
        }
    }

    private char dest() {
        return this.jsonString.charAt(this.destIndex++);
    }

    private boolean hasMoreChars(){
        return this.destIndex<=this.upBound;
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
