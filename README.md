# 任务管理系统
初衷：记录自己每天的工作任务，后期用来总结和归纳。

使用 Spring Boot + MyBatis + Vue + Element 完成

>其他技术点
- Spring Security
- Editor.md

### TODO
- [x] 任务编辑 - 内容使用富文本编辑器
- [x] 任务列表 - 可以查看任务详情
  - [x] 表格每行根据当前任务状态对应上不同颜色
  - [x] 任务列表和任务详情 显示项目名称而不是code
  - [ ] 新增按任务状态查询任务
  - [ ] 添加 **任务开始时间** **任务结束时间** 两个字段，并在列表中添加 **开始任务** **结束任务** **不处理了** 三个按钮
 - [x] 任务编辑  
   - [x] 编辑页面文本框位置调整，上面部分右侧太空
   - [ ] ediot.md的图片上传
- [ ] tomcat开启压缩css,js

>使用editor.md展示时，要注意清空html元素内容，多次展示，防止重复添加

>使用Vue.next(function(){})来解决editor.md内容不显示的问题 

## 开启压缩
Tomcat配置使用gzip，在server.xml中

<Connector port="9098" protocol="HTTP/1.1" 
               connectionTimeout="20000" 
               redirectPort="8443" URIEncoding="UTF-8" 
               compression="on"
			   compressionMinSize="2048"
			   noCompressionUserAgents="gozilla,traviata" 
			   compressableMimeType="text/html,text/xml,text/javascript,application/javascript,text/css,text/plain"
			   />

 compression="on" 打开压缩功能 
 compressionMinSize="2048" 启用压缩的输出内容大小，这里面默认为2KB 
 noCompressionUserAgents="gozilla, traviata" 对于以下的浏览器，不启用压缩; 
 compressableMimeType="text/html,text/xml" 压缩类型

判断下是否需要加这个
useSendfile="false"

重启tomcat即可


