package com.tencent.cloudbase;

import com.alibaba.fastjson.JSONObject;
import com.tencent.cloudbase.util.Context;
import com.tencent.cloudbase.util.Env;

public class Auth {

    Context context;

    public Auth(Context ctx){
        this.context = ctx;
    }

    public JSONObject getUserInfo(){
        JSONObject res = new JSONObject();

        res.put("openId", Env.getSystemProperty("WX_OPENID"));
        res.put("appId",Env.getSystemProperty("WX_APPID"));
        res.put("uid",Env.getSystemProperty("TCB_UUID"));

        return res;
    }
}
