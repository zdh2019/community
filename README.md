## 哈哈哈
##资料
[spring 文档](https://spring.io/guides)
[spring web](https://spring.io/guides/gs/serving-web-content/)
[es](https://elasticsearch.cn/explore)
[Git deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)
[Boostrap](https://v3.bootcss.com/getting-started/)
[Github Oauth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app)
[Spring](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)
[Spring Dev Tool](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#using-boot-devtools)
[Spring MVC](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)
##工具
[git](https://git-scm.com/download)
[visual-paradigm](https://www.visual-paradigm.com)


##sql脚本
```sql
create table USER
(
    ID           INT auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint TABLE_NAME_PK
        primary key (ID)
);
```
```
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```