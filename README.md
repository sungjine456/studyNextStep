## 'Next Step' 책 문제 풀이
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
		
	7. CSS 지원하기
		1) 메소드를 나누는 것이 아닌 메소드 하나에 파라미터를 하나더 넣어서 사용 
```
### part5
```
혼자 실습
	1. 요청 데이터를 별도의 클래스로 분리
		1) HttpRequest 클래스를 만든후 GET과 POST를 if로 나누어 관리(test 성공)
		
	2. 응답 데이터를 별도의 클래스로 분리
		1) 클래스 다이어그램을 보고 메소드 작성
		2) processHeaders 메소드는 어떤 내용을 분리해야할지 모르겠음
		
	3. 다형성을 활용해 URL에 대한 분기 처리를 제거
		1) 이번 실습은 구조는 잡았지만, 이 외에는 모르겠다.

책을 통한 리팩토링
	1. 요청 데이터를 별도의 클래스로 분리
		1.1 책의 코드를 따라침
			1) line이 null일 때 처리
			2) 내가 그냥 POST와 GET으로 나눴다면 요청라인, 요청 헤더 요청 본문으로 나눈 후 POST와 GET으로 나누셨다.
			> 요청 헤더를 읽을 때 while문에서 line이 null일 때 체크해주지 않으면 nullPointException이 난다.
		1.2 책을 보며 책의 코드를 리팩토링 진행
			1) processRequestLine 메소드를 RequestLine 클래스로 분리
			2) method를 enum으로 분리
			3) RequestHandler에 적용
			
	2. 응답 데이터를 별도의 클래스로 분리
		1) 응답 헤더를 인스턴스 변수로 관리 한다.
		2) processHeaders 메소드로 한번에 응답 헤더를 만든다.
		3) forward 메소드에서 .css, .js, 등등 으로 나누어 Content-Type을 관리 한다.
		
	3. 다형성을 활용해 URL에 대한 분기 처리를 제거
		1) 각 클래스에 controller를 implements하여 service메소드를 Overriding하여 재정의 한다.
			RequestHandler에 메소드를 만들어 호출한다.
		2) RequestMapping 클래스를 만들어 Controller를 담는 Collection을 만들고 URL을 키값으로
			Controller를 implements한 클래스들을 담는다.
			RequestHandler에서는 만들었던 메소드를 삭제하고 URL을 가지고 RequestMapping클래스 에서
			Controller를 가져온다.
		3) AbstractController를 만들어 controller를 implements하고 service메소드를 재정의 하는데
			method에 따라 doGet과 doPost로 나눠지게 분기를 나눈다.
			이전에 controller를 implements하고 있던 클래스들은 AbstractController를 extends하고
			doGet이나 doPost를 Overriding하여 재정의 하면 된다.
```
### part6
```
5장 복습
	1. 5장에서 본 Servlet/JSP 복습
	
	2. 개인정보수정 실습
		1) list페이지에서 userId를 가지고 updateFormServlet으로 이동
		2) list페이지에서 받은 userId가지고 해당 user의 정보 updateForm페이지에 데이터 뿌려주기
		3) User클래스에 update메소드 추가
		4) updateForm에서 수정할 정보를 가지고 updateServlet으로 이동
		5) updateServlet에서 정보를 수정하고 list페이지에 데이터 뿌려주기
		
	3. 로그인/로그아웃 실습
		1) loginServlet을 만들어 입력받은 아이디가 있는지 확인 후 페스워드를 확인하고 맞다면 session을 set한다.
		2) index.jsp를 만들어 session이 있는지 확인하여 보여줄 내용만 보여 준다.
		3) logoutServlet을 만들어 session을 지워준다.
		
	4. 회원 목록 및 개인정보 수정 보안 강화 실습
		1) 회원 목록을 뿌려줄 때는 session에 user가 있는지 확인한 후 있으면 목록을 뿌려 주면된다.
		2) updateForm과 update는 session에 user가 있는지 확인한 후 user.userId와 입력받은 userId를 비교한다.
			일치 하면 update할 수 있게하고 없으면 index.jsp로 이동하게 한다.
	5. 중복 코드 제거
		1) .jsp 파일의 header, navigation과 footer를 각각 .jsp파일을 만들어 include시켰다.
		
직접 구현
	1. 세션 구현
		1) 고유한 아이디 생성
		2) 쿠키를 사용해서 아이디 전달
		3) 쿠키클래스 생성하여 쿠키 관리
		4) Session클래스 생성한 후 User의 로그인을 session을 통해 관리
		
MVC 프레임워크 구현
	1. 1단계
		1) DispatcherServlet을 만든다.
		2) DispatcherServlet을 통해서 URL을 받아 Controller를 찾는다.
		3) Controller에서 Data를 URL에 보낼 데이터 가공
```
### part7
```
JDBC 실습
	1. JDBC적용 해보기
		1) User생성, User찾기, User목록 찾기, User수정을 JDBC를 통해서 적용하기
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

### socket closed Exception
----
```
	part5 에서 책을 통한 리팩토링을 진행하다가 HttpRequest에서 bufferedReader를 close했다.
	그랬더니 < java.net.SocketException: Socket closed >가 났다.
	bufferedReader등 Stream의 close는 잘 보고 해야겠다.
```