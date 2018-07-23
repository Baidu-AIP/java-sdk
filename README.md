## 安装Java SDK

**Java SDK主要目录结构**

    com.baidu.aip
           ├── auth                                //签名相关类
           ├── http                                //Http通信相关类
           ├── client                              //公用类
           ├── exception                           //exception类
           ├── ocr
           │       └── AipOcr                      //OCR服务入口
           ├── face
           │       └── AipFace                     //人脸服务入口
           ├── imagecensor
           │       └── AipImageCensor              //图像审核服务入口
           ├── imageclassify
           │       └── AipImageClassify            //图像识别服务入口
           ├── nlp
           │       └── AipNlp                      //Nlp服务入口
           ├── kg
           │       └── AipKnowledgeGraphic         //知识图谱服务入口
           ├── speech
           │       └── AipSpeech                   //语音服务入口
           └── util                                //工具类

**支持 JAVA版本：1.7+**

**直接使用JAR包步骤如下：**

1.在[官方网站](http://ai.baidu.com/sdk)下载Java SDK压缩工具包。

2.将下载的`aip-java-sdk-version.zip`解压后，复制到工程文件夹中。

3.在Eclipse右键“工程 -> Properties -> Java Build Path -> Add JARs”。

4.添加SDK工具包`aip-java-sdk-version.jar`和第三方依赖工具包`json-20160810.jar`。

其中，`version`为版本号，添加完成后，用户就可以在工程中使用OCR Java SDK。


**使用maven依赖：**

添加以下依赖：

```
<dependency>
    <groupId>com.baidu.aip</groupId>
    <artifactId>java-sdk</artifactId>
    <version>4.5.0</version>
</dependency>
```



## 详细使用文档

参考[百度AI开放平台官方文档](http://ai.baidu.com/docs)

