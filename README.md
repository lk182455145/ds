新版本的数据服务程序，
使用Spring Boot重写项目，改进数据缓存，改进连接池问题。

整个系统有三个缓存
* svcCache
 服务缓存，缓存服务的相关信息
* appCache  
应用缓存，缓存应用的信息
* resultCache  
查询结果缓存，缓存查询结果数据

现行缓存清理策略
appCache自己控制自己的缓存清理策略

当对Svc进行任何更改之后(增，删，改),会清空所有的 svcCache和resultCache

## 项目如何打包

这个项目包含前端页面。在编译之前需要先编译并打包前端工程。
cd src/main/web
npm install
npm run package
项目会自动打包到src/main/resources/static下

然后再运行
mvn clean package 打包成jar包即可