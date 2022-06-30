# Triple Home Work

* 안내드린 URL 주소로 github에서 프로젝트 import 하신 후
메일로 보내드린 DB 파일을 압축해제합니다.

* mysql 실행 후 압축해제한 DB를 import합니다.
  ######     (이때 mysql에서 'triple_db' Schema를 미리 생성해주셔야합니다) 
  
* 프로젝트 파일에서 resources 패키지에 있는 application.properties 파일에서
사용하시는 db 아이디와 패스워드로 변경합니다.
```java
spring.datasource.username=xxxx
spring.datasource.password=xxxx
```
* 세팅이 완료되었다면 DemoApplication.java 파일을 Run하여 애플리케이션을 시작합니다.
* [localhost:8080/jsp](localhost:8080/jsp) 로 접속하시고 메일로 보내드린 TestCase엑셀 파일을 참고하시어 테스트 진행해주시면 됩니다.
* TEST에 사용할 UserId
  * user1
  * user2
  * user3
  * user4
* 로 테스트 진행해주시면 감사하겠습니다.

이상 지원자 정준희였습니다. 감사합니다.

* 개발환경
  * Spring Boot v2.7.1 
  * mysql v8.0.21
  * JDK v1.8
  * mybatis v1.3.2
  * JavaScript,HTML,css
  * jQuery
  * Ajax
