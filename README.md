# DAL
Date Access Layer

## 用户操作

- 选择数据库驱动
- 输入连接字符串
- 输入用户名
- 输入密码
- 选择需要生成的表
- 设置APPID
- 选择表需要进行count, max, min, avg, sum的字段
- 需要进行join操作的两表
- 默认排序字段
- generatorConfig.xml下载
- 生成代码下载
- 历史生成查看
- 删除历史

## 算法
1. 从配置模版加载默认配置项
2. 用户输入覆盖配置项
3. 保存配置XML
4. 生成原生Mapper
5. 生成自定义Mapper
6. 源代码压缩ZIP
7. 获取appid下的生成目录历史
8. 提供删除目录
9. 错误信息反馈