### RAIDEN-MOCK

通过简单定义生成测试数据

#### 1.接口定义，示例如下：

```ini
response_wrapper = {
                   "code": "code",
                   "message": "message",
                   "result": "result",
                   "timestamp": "@timestamp_c@"
}

[/demo/{id}]
request_method = GET
response_code = 200
response_headers = ["Content-Type: application/json"]
response_body = {
                "id": "#id",
                "name": "@name@",
                "age": "@age@{20,30}",
                "address": "@address@",
                "phone": "@mobile@",
                "date": "@date@",
                "time": "@time@",
                "datetime": "@datetime@{yyyyMMddHHmmss}",
                "idCard": "@idCard@",
                "random": "我是随机数:@random@{8}:@date@{yyyyMMdd}",
                "email": "@email@"
}
```

- 访问接口生成如下数据:

```json
{
  "result": {
    "date": "1991-06-05",
    "random": "我是随机数:qBiR0Nxk:19840429",
    "datetime": "20020109091011",
    "address": "香港特别行政区油尖旺区null",
    "phone": "17886195548",
    "idCard": "540227201307305225",
    "name": "周清",
    "id": "123456",
    "time": "09:11:18",
    "age": 29,
    "email": "唐广"
  },
  "code": "200",
  "message": "success",
  "timestamp": 1721630342131
}
```

- 定义如下：
    - `response_wrapper`： 返回数据外层包装 `code` `result` `message` 3个为固定`key` 对应返回数据包装的字段名，response_wrapper
      可以设置在最外层，作为当前文件的全局配置，也可以每个接口当独设置，接口中当独设置的优先级高于全局设置
    - `[/demo/{id}]`: 表示接口路径
    - `request_method`: 请求方法
    - `response_code`: 响应码
    - `response_headers`: 响应头
    - `response_body`: 响应数据体

#### 2.响应体定义：

- `@name@`: 生成人名
- `@age@`: 生成年纪，支持指定范围，表达式格式：`@age@{20,30}` 生成20-30之间的数字
- `@address@`: 生成地址
- `@mobile@`: 生成手机号码
- `@date@`: 生成日期 支持指定格式，表达式格式：`@date@{yyyyMMdd}` 生成格式如: 20211214
- `@time@`: 生成时间 支持指定格式，表达式格式：`@time@{HHmmss}` 生成格式如: 124842
- `@datetime@`: 生成完整日期时间 支持指定格式，表达式格式：`@datetime@{yyyyMMddHHmmss}` 生成格式如: 20211214124842
- `@idCard@`: 生成身份证号码
- `@random@`: 生成随机数，支持指定长度，表达式格式：`@random@{3}` 生成3位长度的随机字符
- `@email@`: 生成邮件地址
- `@number@`： 生成随机数字,支持指定范围，表达式格式：`@number@{20,30}` 生成20-30之间的数字
- `@timestamp@`: 生成随机时间戳
- `@timestamp_c@`: 生成当前时间戳
- `@ip@`: 生成随机IP