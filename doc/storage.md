#   存储


##  API

```java
/**
 * 上传文件
 * @param cloudPath  存储的文件名称
 * @param file  本地文件
 * @return  上传结果
 * @throws TcbException
 */
public Result<UploadFileData> uploadFile(String cloudPath, File file) throws TcbException;

/**
 * 下载文件
 * @param fileId    云存储的文件id
 * @param tempFilePath  本地存储的文件路径
 * @return  文件本地存储的实际路径
 * @throws TcbException
 */
public String downloadFile(String fileId,String tempFilePath) throws TcbException;

/**
 * 获取文件访问链接
 * @param fileList  文件列表
 * @return  返回值
 * @throws TcbException
 */
public Result<TempFileUrlData> getTempFileURL(List<TempFile> fileList) throws TcbException;
/**
 * 删除文件
 * @param fileList  文件id列表
 * @return  删除结果
 * @throws TcbException
 */
public Result<DeleteFileData> deleteFile(List<String> fileList) throws TcbException;
```

