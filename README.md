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
```