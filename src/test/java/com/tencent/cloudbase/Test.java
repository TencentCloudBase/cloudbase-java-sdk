package com.tencent.cloudbase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.tencent.cloudbase.common.database.geos.Point;
import com.tencent.cloudbase.common.utils.Format;
import com.tencent.cloudbase.util.Env;


public class Test {

    public static void main(String args[]) throws Exception {
        System.out.println("hello tcb");

//        Env.setSystemProperty("localdebug","true");
//        Env.setSystemProperty("localproxy","true");
//
//
//        AppClient appClient = new AppClient();
//
//        List<String> list = new ArrayList<>();
//        list.add("cloud://dev-withnate-604e29.6465-dev-withnate-604e29-1259201219/tupload.txt");
//
//        Result<DeleteFileData> res = appClient.deleteFile(list);

        JSONObject data = new JSONObject();
        data.put("_id","name_05");
        data.put("int","-1");
        data.put("geo",new Point(90,23));

        JSONArray arr = new JSONArray();
        arr.add(new Point(90,23));
        arr.add(new Point(89,22));

        data.put("geoarr",arr);

        JSONObject dd = Format.dataFormat(data);

        JSONObject obj = JSON.parseObject(JSON.toJSONString(new Point(89,22)));


        ValueFilter filter = new ValueFilter() {
            @Override
            public Object process(Object object, String name, Object value) {

                if(value instanceof Double || value instanceof Float){
                    if((Double)value%1==0){
                        return  ((Number) value).intValue();
                    }
                    if((Float)value%1==0){
                        return ((Number) value).intValue();
                    }
                }

                return value;
            }
        };


        String s  = JSON.toJSONString(arr, filter);




        System.err.println("res2 : "+ JSON.toJSONString(arr,filter));

    }



}
