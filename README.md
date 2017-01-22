﻿## 'Next Step' 책 문제 풀이
----

## 사용기술
----
```
back end
	1. java 8
	2. junit 4.12
	3. logback 1.1.2
	4. guava 18.0
```
```
build tool
	1. Maven
```
```
issue tracker
	1. Github
```
```
DVCS
	1. Git
```

## 진행 순서
----

### part2
```
문자열을 받아 계산하기.
	1. 빈 문자열과 Null은 0을 리턴
	2. 하나의 숫자만 들어오면 그 숫자를 리턴
	3. ,나 :가 들어있다면 ,나:로 문자열을 잘라 나오는 숫자들을 합하여 리턴
	4. "//"와 "\n" 사이의 문자로 "\n" 이후 나오는 문자열을 잘라 나오는 숫자들을 합하여 리턴
	5. 음수가 나오면 RuntimeException을 throw
```
### part3
```
web-server 실습하기
	1. GitHub에서 소스 받아오기
		1) project를 maven project로 만들어 Dynamic Web Module Project로 수정
		2) zip파일로 받은 소스 옮기기(webapp파일을 WebContent파일로 수정하니 index.html이 나온다.)
		
	2. index.html 응답하기
		1) BufferedReader로 요청정보를 전부 읽는다.
		2) 요청정보에서 URL을 찾는다.
		3) URL에 맞는 파일을 전달한다.
		
	3. GET 방식으로 회원가입하기
		1) 요청 URL에서 접근 경로와 데이터를 추출한다.
		2) 데이터 파싱 후 User 클래스에 담는다.
		3) create파일이 없어 에러가 나기에 create파일 생성
		
	4. POST 방식으로 회원가입하기
		1) 데이터가 HTML 본문으로 들어오기 때문에 URL을 자를 필요가 없다.
		2) HTML 헤더에서 Content-Length가 본문의에서 데이터의 위치이기 때문에 Content-Length 값을 찾는다.
		3) IOUtils의 readData()를 이용하여 데이터를 추출하여 User객체를 만든다.
		
	5. 302 status code 적용
		1) URL이 /user/create로 올때 index.html파일로 Redirect시킬 코드를 작성
		2) create파일의 필요가 없어졌기 때문에 삭제
		
	6. 로그인 하기
		1) 로그인 성공시 index.html로 이동, 실패시 login_failed.html로 이동
		2) 로그인 성공시 Cookie값을 logined=true로 실패시 Cookie값을 logined=false로 전달 
		3) 성공 여부는 회원가입시 DataBase 클래스를 이용한다.
		
	7. 사용자 목록 출력
		1) 로그인 상태이면 목록 출력, 아니라면 login.html로 이동
		2) 로그인 상태는 Cookie값에 logined가 true인지 false인지로 확인
		3) parseValues에서 cookie의 key값이 중복될 때 duplicate 에러가 나서 수정
		
	8. CSS 적용
		1) 파일의 .css확장자를 찾아 contextType을 바꾸게 수정
```
### part4
```
part3에서 내가 작성한 코드와 javajigi님께서 작성한 코드의 차이점 (숫자는 이전 코딩과 책의 차이점, ">"는 책과 다르게 코딩한 점)
	1. index.html 응답하기
		1) bufferedReader의 try-with-resources적용함
		2) line이 null인지 체크 하기 전에 split을 함 (null 체크 후 split을 하는게 좋은 듯 싶다.)
		3) line에 ""이 들어오는지 체크(할 필요가 없어 보인다.)
		
	2. GET 방식으로 회원가입하기
		1) url을 자르는 것과 User를 만드는 것을 method로 뽑아냈다.
		2) 요청 url을 자르고 시작했다.(요청 url이 /user/create일 때만 자르는게 맞는것 같다.)
		3) user의 데이터가 잘 들어왔는지 확인하지 않았다.(데이터가 Client에서 Server로 잘 들어왔는지 확인해야할 필요가있다.)
		4) 책에서 "/user/create".startsWith(url) << 이 부분은 url.startsWith("/user/create")로 수정해아할 필요가 있다. 
		   url의 시작이 "/user/create" 이어야 하기 때문이다.
		> "/user/create"로 요청이올 때 index.html을 응답으로 보내도록 구현
		
	3. POST 방식으로 회원가입하기
		1) HttpRequestUtils 클래스에 있는 parseHeader 매소드를 사용했는데 javajigi님은 새로운 매소드를 만드셨다.
		
	4. 302 status code 적용
		1) response302Header메소드에 index.html을 그냥 써넣었다.
		2) response302Header와 response200Header를 if/else로 나눠서 코딩했다.
		> DataOutputStream는 if("/user/create".equals(url)) 한번만 하면 될것 같다.
		
	5. 로그인하기
		1) 맴버변수를 만들 필요가 없었다.(로그인할 때 true 로그아웃할 때 false로 cookie를 바꿔주기만 하면된다.)
		2) 가입과 로그인을 하나의 메소드로 해결하려고 했다.
		> responseResource 메소드의 OutputStream 파라미터를 DataOutputStream 파라미터로 수정
		> user가 null인지 비교하는 것을 패스워드와 null이 아닌지 비교하는 것으로 수정하고 responseResource 메소드를
		  한번만 사용
		> 마지막 else 삭제 후 responseResource 메소드만 사용
	
	6. 사용자 목록 출력
		1) /user/list.html로 보내는 것이 아닌 사용자의 목록으로 페이지를 구성하심 
```

## 배운 내용들
----

### HTTP 통신 규약
----

#### * Client -> Server 
```
	1. POST index.html HTTP/1.1
	2. HOST: localhost:8080
	2. Connection-length: 59
	2. Content-Type: application/x-www-form-urlencoded
	3.
	4. userId=id
	
	1. 요청 라인
		1) HTTP의 메소드, URI와 HTTP의 버전으로 띄어쓰기로 나누어져 있다.
	2. 요청 헤더
		1) Key: Value 쌍으로 존재한다.
		2) Key: Value1, Value2 와 같이 여러개의 값을 전달할 수 있다. 
	3. 요청 헤더와 요청 본문 사이에는 빈공간이 있어야한다.
	4. 요청 본문
		1) HTTP메소드가 POST일 때 Client에서 Server로 보내는 데이터를 나타낸다.
```	

#### * Server -> Client
```
	1. HTTP/1.1 200 OK
	2. Content-Type: text/html;charset=utf-8
	2. content-Length: 20
	3.
	4. <HTML></HTML>
	
	1. 상태라인
		1) HTTP의 버전, 상태코드(200 OK)가 띄어쓰기로 나누어져 있다.
	2. 응답헤더
		1) Key: Value 쌍으로 존재한다.
		2) Key: Value1, Value2 와 같이 여러개의 값을 전달할 수 있다.
	3. 응답 헤더와 응답 본문 사이에는 빈공간이 있어야한다.
	4. 응답 본문
		1) Server에서 Client로 보내는 데이터를 나타낸다.
```

### 상태 코드
----

```
	1. 2XX : 성공
	2. 3XX : Redirect
	3. 4XX : 요청 오류
	4. 5XX : 서버 오류
```