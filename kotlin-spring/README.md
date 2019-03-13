features:
-[x] 权限管理: 用户 角色 资源
  - [x] 使用code作为permission主键 instead `id`
  - [] Add `resource url` mapping to permission, 通过filter自动校验权限
-[ ] 登录校验, security, JWT信息
-[ ] Cache
-[ ] field validation
-[ ] redis
-[x] integration test,jpa test, unit test, mockk, assertj
-[ ] audit logging
-[x] jpa, ~~mybatis,~~ hibernate
  - hibernate: More suitable object-oriented, modeling, DDD
  - mybatis: Only use it if you have a lot of join/union/other complex DB operation
  - [Hibernate vs MyBatis](https://www.zhihu.com/question/21104468),
  Hibernate is more advantageous than mybatis in most of scenario.
-[ ] hibernate cache
-[ ] jacoco, ~~ktlint,~~ detekt
  - detekt include most of features of ktlint, idea also provides good lint.
