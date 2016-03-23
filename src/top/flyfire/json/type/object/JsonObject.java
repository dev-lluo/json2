package top.flyfire.json.type.object;

import top.flyfire.json.JsonBean;
import top.flyfire.json.JsonException;
import top.flyfire.json.JsonGetter;
import top.flyfire.json.JsonString;
import top.flyfire.json.linked.adapter.object.JsonObjectAdapter;

/**
 * Created by flyfire[dev.lluo@outlook.com] on 2016/3/15.
 */
public class JsonObject extends JsonBean implements JsonGetter<JsonString>,JsonObjectAdapter {

    private static final int DEFAULT_CAP = 10;

    private static final int DEFAULT_GROWTH = 10;

    private static final int MAX_ENTRY_SIZE = 10;

    private Entry[] entries;

    public JsonObject() {
        this.entries = new Entry[DEFAULT_CAP];
    }

    @Override
    public JsonBean get(JsonString property) {
        int hash = property.hashCode();
        hash = (hash ^ (hash >> 31)) - (hash >> 31);
        int index = hash%this.entries.length;
        Entry entry = this.entries[index];
        while (entry!=null){
            if(property.equals(entry.property)){
                return entry.value;
            }
        }
        throw new JsonException("unknown property["+property+"]");
    }

    @Override
    public void increase(int minIncrement) {
        int expect_length = this.entries.length+minIncrement;
        if(expect_length>0&&expect_length<=Integer.MAX_VALUE) {
            Entry[] new_container = new Entry[expect_length];
            Entry[] old_container = this.entries;
            this.entries = new_container;
            for(int i = 0;i<old_container.length;i++){
                Entry entry = old_container[i];
                if(entry==null)continue;
                do{
                    this.set(entry.property,entry.value);
                    entry = entry.next;
                }while (entry!=null);
            }
        }else{
            throw new IllegalStateException();
        }
    }

    @Override
    public void set(JsonString property, Object object) {
        int hash = property.hashCode();
        hash = (hash ^ (hash >> 31)) - (hash >> 31);
        int index = hash%this.entries.length;
        this.entries[index] = new Entry(property,(JsonBean)object,this.entries[index]);
        if(this.entries[index].size==JsonObject.MAX_ENTRY_SIZE){
            this.increase(DEFAULT_GROWTH);
        }
    }

    public class Entry implements EntryAdapter{

        private JsonString property;

        private JsonBean value;

        private Entry next;

        private int size;

        public Entry(JsonString property, JsonBean value, Entry next) {
            this.property = property;
            this.value = value;
            this.next = next;
            if(this.next==null)this.size  = 1;
            else this.size = this.next.size+1;
        }

        @Override
        public void setValue(Object object) {
            this.value = (JsonBean)object;
        }

        @Override
        public void setProperty(JsonString property) {
            this.property = property;
        }


    }

}
