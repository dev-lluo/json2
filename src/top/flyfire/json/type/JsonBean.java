package top.flyfire.json.type;

/**
 * Created by flyfire[dev.lluo@outlook.com] on 2016/3/15.
 */
public abstract class JsonBean {

    public abstract void increase(int minIncrement);

    public <T> T C(){
        return (T)this;
    }

}
