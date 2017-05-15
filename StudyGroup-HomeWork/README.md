#### 1. 로컬 개발 환경에 Tomcat 서버를 시작하면 Servlet Container의 초기화 과정이 진행된다. 현재 소스 코드에서 초기화되는 과정에 대해 설명해라. ( O )
----
```
ContextLoaderListener클래스의 contextInitialized메소드가 실행되어 Servlet Container를 초기화하며
jwp.sql에서 sql문을 읽어오고 DB를 연결하여 DB의 데이터를 초기화 해준다.
그 다음 ResourceFilter를 통해서 재사용 가능한 정적리소스가 있는 url유형을 메모리에 올려놓아 재사용할 수 있게 한다.
그 다음 DispatcherServlet클래스의 init메소드를 통해서 servlet을 초기화한다.
init메소드는 RequestMapping클래스에 등록된 Controller객체를 servlet에 등록한다.
```

#### 2. 로컬 개발 환경에 Tomcat 서버를 시작한 후 http://localhost:8080으로 접근하면 질문 목록을 확인할 수 있다. http://localhost:8080으로 접근해서 질문 목록이 보이기까지 소스 코드의 호출 순서 및 흐름을 설명하라. ( O )
----
```
ResourceFilter클래스에서 요청을 받고 URL이 정적리소스면 forward시키고 아니면 DispatcherServlet클래스의 
service메소드를 실행한다.
service메소드에서 요청받은 URL에 맞는 Controller가 있다면 해당 Controller의 execute메소드를 실행하여
필요에 따라 데이터를 가공하고 view의 유형을 정해주고 Controller가 없다면 Exception을 던진다.
view의 유형이 json이면 response의 ContentType을 바꿔 데이터를 넘겨주고 jsp라면 redirect나 forward를 한다.
```

#### 3. 질문 목록은 정상적으로 동작하지만 질문하기 기능은 정상적으로 동작하지 않는다. 질문하기 기능을 구현한다. 질문 추가 로직은 QuestionDao 클래스의 insert method 활용 가능하다. HttpServletRequest에서 값을 추출할 때는 ServletRequestUtils 클래스를 활용 가능하다. 질문하기를 성공한 후 질문 목록 페이지(“/”)로 이동해야 한다.

#### 4. 로그인하지 않은 사용자도 질문하기가 가능하다. 로그인한 사용자만 질문이 가능하도록 수정한다. 또한 질문할 때 글쓴이를 입력하지 않고 로그인한 사용자 정보를 가져와 글쓴이 이름으로 등록한다.

#### 5. 자바 기반으로 웹 프로그래밍을 할 경우 한글이 깨진다. 한글이 깨지는 문제를 해결하기 위해 ServletFilter를 활요해 문제를 해결할 수 있다. core.web.filter.CharacterEncodingFilter에 어노테이션 설정을 통해 한글 문제를 해결한다.

#### 6. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생할 가능성이 있는 코드이다. 멀티 쓰레드 상황에서 문제가 발생하지 않도록 수정한다. ( O )
----
```
ShowController는 하나의 객체만 만들어져 재사용 되기 때문에 맴버변수인 question변수와 answers변수를 이용할 때
'A'라는 쓰레드가 먼저 할당하고 사용하기 전에 'B'라는 쓰레드가 다른 내용으로 다시 할당하면 문제가 생긴다.
```