package top.flyfire.json;

/**
 * Created by shyy_work on 2016/3/21.
 */
public interface JsonIns {

    int JsonEnd = -2;
    static boolean isJsonEnd(int ins){
        return JsonEnd == ins;
    }


    int Undefined = -1;
    static boolean isUndefined(int ins){
        return Undefined == ins;
    }


    int Object = 100;
    static boolean isObject(int ins){
        return Object == ins;
    }

    int ObjectPD = 101;
    static boolean isObjectPD(int ins){
        return ObjectPD == ins;
    }

    int ObjectPS = 102;
    static boolean isObjectPS(int ins){
        return ObjectPS == ins;
    }

    int ObjectP = 103;
    static boolean isObjectP(int ins){
        return ObjectP == ins;
    }


    int Array = 200;
    static boolean isArray(int ins){
        return Array == ins;
    }

    int Primitive = 300;
    static boolean isPrimitive(int ins){
        return Primitive == ins;
    }

    int PrimitivePD = 301;
    static boolean isPrimitivePD(int ins){
        return PrimitivePD == 301;
    }

    int PrimitivePS = 302;
    static boolean isPrimitivePS(int ins){
        return PrimitivePS == 302;
    }
}
