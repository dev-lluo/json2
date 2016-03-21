package top.flyfire.json.base;

/**
 * Created by shyy_work on 2016/3/21.
 */
public interface JsonIns {

    int Undefined = -1;
    static boolean isUndefined(int ins){
        return Undefined == ins;
    }


    int Object = 100;
    static boolean isObject(int ins){
        return Object == ins;
    }

    int Array = 200;
    static boolean isArray(int ins){
        return Array == ins;
    }

    int Primitive = 300;
    static boolean isPrimitive(int ins){
        return Primitive == ins;
    }
}
