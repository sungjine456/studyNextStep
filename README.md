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
```