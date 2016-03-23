package top.flyfire.json;

/**
 * Created by flyfire[dev.lluo@outlook.com] on 2016/3/15.
 */
public abstract class JsonBean {

    protected abstract void increase(int minIncrement);

    public <T> T C(){
        return (T)this;
    }

}
