package top.flyfire.json;

/**
 * Created by flyfire[dev.lluo@outlook.com] on 2016/3/15.
 */
public interface JsonGetter<T> {
    JsonBean get(T t);
}
