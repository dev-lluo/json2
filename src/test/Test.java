package test;

import top.flyfire.json.JsonBean;
import top.flyfire.json.Static;

/**
 * Created by shyy_work on 2016/3/23.
 */
public class Test {
    public  static void main(String[] args){
        String jsonlt1000 = "{\"a\":{\"a1\":123,\"a2\":{\"a\":\"123\"},\"a3\":true,\"a4\":false,\"a5\":\"2016-03-14T05:33:56.343Z\"},\"b\":{\"b1\":true,\"b2\":[1,\"2\",\"4\",\"134\",7,true,\"2016-03-14T05:34:43.577Z\"],\"b3\":\"adsfdal;fsdfalasdfskdjflaksdjfalsdfjalsdf;asdf103489-1329411111111112303333333222222444444444444444444444444444444444444444444444444444444444444444444444444444fwgdddddddddddddddddddddddddrtwertqrgsdfgsdfg\",\"b4\":{\"a\":\"123123123123123sdfasdfasdfa\",\"b\":\"14519234dlkfasdf我的到了发时间到了发生的是的法拉盛的风景啊是的啊时代发生的发啊时代发生的发\",\"c\":\"ladfsdf90-412=3=1234341234)lkdsflasd\"}},\"c\":[null,\"134123480dkldslkasdflasdflasdfasdfasdfa\",\"2016-03-14T05:36:30.723Z\"],\"c3\":[1,22343,4,5,6]}";
        JsonBean jsonBean = Static.json2Java(jsonlt1000);
        System.out.println(jsonBean);
    }
}
