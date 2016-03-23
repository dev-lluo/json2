package top.flyfire.json;

/**
 * Created by flyfire[dev.lluo@outlook.com] on 2016/3/15.
 */
public class JsonString implements CharSequence {

    private final static int EMPTY = 0;

    private final static int DEFAULT_CAP = 500;

    private final static int DEFAULT_INCREMENT = 1000;

    private char[] container;

    private int length;

    private int hashCode;

    public JsonString() {
        this.container = new char[DEFAULT_CAP];
        this.length = EMPTY;
    }

    public JsonString(char[] value) {
        this.container = value;
    }

    public JsonString(String json){
        this.container = json.toCharArray();
        this.length = this.container.length;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public char charAt(int index) {
        return this.container[index];
    }

    public boolean isEmpty(){
        return EMPTY == this.length;
    }

    public void clear(){
        this.length = EMPTY;
    }
    @Override
    public JsonString subSequence(int start, int end) {
        int valueLength = end-start+1;
        char[] value = new char[valueLength];
        System.arraycopy(this.container,start,value,0,valueLength);
        return new JsonString(value);
    }

    public char[] realVal(){
        char[] realVal = new char[this.length];
        System.arraycopy(this.container, 0, realVal, 0, this.length);
        return  realVal;
    }


    public void append(char[] value){
       this.append(value, value.length);
    }

    public void append(JsonString jsonString){
        this.append(jsonString.container,jsonString.length());
    }

    @Override
    public int hashCode(){
        int h = hashCode;
        if(h==0&&!this.isEmpty()){
            char val[] = this.container;

            for (int i = 0; i < this.length; i++) {
                h = 31 * h + val[i];
            }
            hashCode = h;
        }
        return h;
    }

    private void append(char[] value,int expectLength){
        if(expectLength>=this.freeLength()){
            this.increase(expectLength);
        }
        System.arraycopy(value,0,this.container,this.length,expectLength);
        this.length = this.length+expectLength;
    }

    private int freeLength(){
       return this.container.length-this.length;
    }

    private void increase(int lessIncrement){
        lessIncrement = lessIncrement>=DEFAULT_INCREMENT?(lessIncrement+DEFAULT_INCREMENT):lessIncrement;
        int newCap = this.container.length+lessIncrement;
        char[] newContainer = new char[newCap];
        System.arraycopy(this.container,0,newContainer,0,this.length);
        this.container = newContainer;
    }

}
