package com.tencent.cloudbase.function;

import com.alibaba.fastjson.JSONObject;
import com.tencent.cloudbase.AdminRequest;
import com.tencent.cloudbase.common.Result;

public class FunctionService {

    private final String ActionName = "functions.invokeFunction";

    AdminRequest adminRequest;

    public FunctionService(AdminRequest adminRequest){
        this.adminRequest = adminRequest;
    }


    public Result<JSONObject> call(String functionName, JSONObject data) throws Exception{

        JSONObject params = new JSONObject();
        params.put("function_name",functionName);
        String requestData = "";
        if(data!=null){
            requestData = data.toJSONString();
        }
        params.put("request_data",requestData);


        String resString = adminRequest.sendString("functions.invokeFunction",params);

        JSONObject resObj = JSONObject.parseObject(resString);

        Result<JSONObject> res = new Result<>();

        res.setRequestId(resObj.getString("requestId"));
        res.setData(resObj.getJSONObject("data").getJSONObject("response_data"));

        return res;
    }

}
