#   云函数

##  API

```java

/**
 * 执行云函数
 * @param functionName  函数名
 * @param data  参数
 * @return 执行结果
 * @throws Exception
 */
public Result<JSONObject> call(String functionName, JSONObject data) throws Exception；
```


`demo`


```java

JSONObject req = new JSONObject();
req.put("name","zzz");
Result<JSONObject> res = null;
try {
    res = appClient.call("testc", req);
}catch (Exception e){

}
```