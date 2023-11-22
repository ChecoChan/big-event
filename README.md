# big-event

本项目是一个文章管理系统的后端项目，前端在这👉[big-event 前端](https://github.com/ChecoChan/big-event-frontend)，支持 Docker 一键启动。

项目框架：SpringBoot + MySQL + MyBatis-Plus + Redis + Spring Cache

## 项目启动
- 环境要求：
    - Docker
    - JDK 8
- 打包：`mvn clean package -DskipTests`
- 运行 [docker-compose.yml](https://github.com/ChecoChan/big-event/blob/master/docker-compose.yml) 一键启动项目

## 接口介绍

### 用户相关接口

- 注册接口：
  
  ```shell
  curl --location --request POST 'http://localhost:8080/user/register' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data 'username=zhangsan&password=123456'
  }'
  ```

- 登录接口：

  ```shell
  curl --location --request POST 'http://localhost:8080/user/login' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data 'username=zhangsan&password=123456'
  }'
  ```

- 获取用户详细信息：

  ```bash
  # 请求头 Authorization 字段值为登录接口返回的 Token 值
  curl --request GET 'http://localhost:8080/user/userInfo' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzAwNzE4ODAwfQ.ExwxYkWs4nLnkDKGVx-R-RC6WpElbKbXAdzmMyPU7GQ'
  ```

- 更新用户基本信息：

  ```shell
  # 请求头 Authorization 字段值为登录接口返回的 Token 值
  curl --request PUT 'http://localhost:8080/user/update' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzAwNzE4ODAwfQ.ExwxYkWs4nLnkDKGVx-R-RC6WpElbKbXAdzmMyPU7GQ' \
  --data-raw '{
    "id": 2,
    "username": "zhangsan",
    "nickname": "ZHANGSAN",
    "email": "zhangsan@gmail.com"
  }'
  ```

- 更改用户密码：

  ```shell
  # 请求头 Authorization 字段值为登录接口返回的 Token 值
  curl --request PATCH 'http://localhost:8080/user/updatePwd' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzAwNzE4ODAwfQ.ExwxYkWs4nLnkDKGVx-R-RC6WpElbKbXAdzmMyPU7GQ' \
  --data-raw '{
    "old_pwd": "123456",
    "new_pwd": "123456",
    "re_pwd": "123456"
  }'
  ```

- 更新用户头像：
  
  ```shell
  # 请求头 Authorization 字段值为登录接口返回的 Token 值
  curl --request PATCH 'http://localhost:8080/user/updateAvatar?avatarUrl=https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzAwNzE4MjM5fQ.G6ZdJO4J9l6TURqKUDnNRumkAcZH1n4dO_htkqjx0qg'
  ```

### 文章分类相关接口

- 新增文章分类：

  ```shell
  # 请求头 Authorization 字段值为登录接口返回的 Token 值
  curl --request POST 'http://localhost:8080/category' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzAwNzE4MjM5fQ.G6ZdJO4J9l6TURqKUDnNRumkAcZH1n4dO_htkqjx0qg' \
  --data-raw '{
    "categoryName": "Travel",
    "categoryAlias": "LvYou"
  }'
  ```

- 查询文章分类列表：
  
  ```shell
  # 请求头 Authorization 字段值为登录接口返回的 Token 值
  curl --request GET 'http://localhost:8080/category' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzAwNzE4MjM5fQ.G6ZdJO4J9l6TURqKUDnNRumkAcZH1n4dO_htkqjx0qg'
  ```
  
- 查询文章分类信息：
  
  ```shell
  # 请求头 Authorization 字段值为登录接口返回的 Token 值
  curl --request GET 'http://localhost:8080/category/detail?id=1' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzAwNzE4MjM5fQ.G6ZdJO4J9l6TURqKUDnNRumkAcZH1n4dO_htkqjx0qg'
  ```
  
- 修改文章分类信息：

  ```shell
  # 请求头 Authorization 字段值为登录接口返回的 Token 值
  curl --request PUT 'http://localhost:8080/category' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzAwNzE4MjM5fQ.G6ZdJO4J9l6TURqKUDnNRumkAcZH1n4dO_htkqjx0qg' \
  --data-raw '{
    "id": "1",
    "categoryName": "Finance and Economics",
    "categoryAlias": "CaiJing" 
  }'
  ```

- 删除文章分类：

  ```shell
  # 请求头 Authorization 字段值为登录接口返回的 Token 值
  curl --request DELETE 'http://localhost:8080/category?id=3' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzAwNzE4MjM5fQ.G6ZdJO4J9l6TURqKUDnNRumkAcZH1n4dO_htkqjx0qg'
  ```


### 文章管理相关接口

- 新增文章：

  ```shell
  # 请求头 Authorization 字段值为登录接口返回的 Token 值
  curl --request POST 'http://localhost:8080/article' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzAwNzE4MjM5fQ.G6ZdJO4J9l6TURqKUDnNRumkAcZH1n4dO_htkqjx0qg' \
  --data-raw '{
    "title": "陕西旅游攻略",
    "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
    "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
    "state": "草稿",
    "categoryId": 2
  }'
  ```

- 获取文章列表：

  ```shell
  # 请求头 Authorization 字段值为登录接口返回的 Token 值
  curl --request GET 'http://localhost:8080/article?pageNum=1&pageSize=3&categoryId=2&state=草稿' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzAwNzE4MjM5fQ.G6ZdJO4J9l6TURqKUDnNRumkAcZH1n4dO_htkqjx0qg'
  ```

- 获取文章详细信息：

  ```shell
  # 请求头 Authorization 字段值为登录接口返回的 Token 值
  curl --request GET 'http://localhost:8080/article/detail?id=1' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzAwNzE4MjM5fQ.G6ZdJO4J9l6TURqKUDnNRumkAcZH1n4dO_htkqjx0qg'
  ```

- 更新文章信息：

  ```shell
  # 请求头 Authorization 字段值为登录接口返回的 Token 值
  curl --request PUT 'http://localhost:8080/article/update' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzAwNzE4MjM5fQ.G6ZdJO4J9l6TURqKUDnNRumkAcZH1n4dO_htkqjx0qg' \
  --data-raw '{
    "id":3,
    "title": "北京旅游攻略",
    "content": "天安门,颐和园,鸟巢,长城...爱去哪去哪...",
    "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
    "state": "已发布",
    "categoryId": 2
  }'
  ```

- 删除文章：

  ```shell
  # 请求头 Authorization 字段值为登录接口返回的 Token 值
  curl --request DELETE 'http://localhost:8080/article?id=2' \
  --header 'Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzAwNzE4MjM5fQ.G6ZdJO4J9l6TURqKUDnNRumkAcZH1n4dO_htkqjx0qg'
  ```
  