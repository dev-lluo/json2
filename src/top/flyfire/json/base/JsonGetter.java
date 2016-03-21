package top.flyfire.json.base;

import top.flyfire.json.base.JsonBean;

/**
 * Created by flyfire[dev.lluo@outlook.com] on 2016/3/15.
 */
public interface JsonGetter<T> {
    JsonBean get(T t);
}
