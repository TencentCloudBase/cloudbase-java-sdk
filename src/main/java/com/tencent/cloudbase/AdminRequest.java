package com.tencent.cloudbase;

import com.alibaba.fastjson.JSONObject;
import com.tencent.cloudbase.common.exception.TcbException;
import com.tencent.cloudbase.common.utils.Request;
import com.tencent.cloudbase.util.Context;
import com.tencent.cloudbase.util.Env;
import com.tencent.cloudbase.util.HttpUtils;

public class AdminRequest extends Request {

    private Context context;

    public AdminRequest(Context context){
        this.context = context;
    }

    @Override
    public String sendString(String action, JSONObject params) throws TcbException{

        params.put("action",action);

        JSONObject headers = new JSONObject();
        headers.put("content-type","application/json");

        JSONObject req = new JSONObject();

        if("true".equals(Env.getSystemProperty("localdebug"))){
            req.put("url","http://localhost:8002/admin");
        }

        req.put("params",params);
        req.put("headers",headers);

        String resString =  HttpUtils.buildReq(this.context,req);

        return resString;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
