<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="kr">
<head>
    <%@ include file="/include/header.jspf" %>
</head>
<body>
	<%@ include file="/include/navigater.jspf" %>
<div class="container" id="main">
   <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
      <div class="panel panel-default qna-list">
          <ul class="list">
              <li>
                  <div class="wrap">
                      <div class="main">
                          <strong class="subject">
                              <a href="./qna/show.jsp">국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?</a>
                          </strong>
                          <div class="auth-info">
                              <i class="icon-add-comment"></i>
                              <span class="time">2016-01-15 18:47</span>
                              <a href="./user/profile.jsp" class="author">자바지기</a>
                          </div>
                          <div class="reply" title="댓글">
                              <i class="icon-reply"></i>
                              <span class="point">8</span>
                          </div>
                      </div>
                  </div>
              </li>
              <li>
                  <div class="wrap">
                      <div class="main">
                          <strong class="subject">
                              <a href="./qna/show.jsp">runtime 에 reflect 발동 주체 객체가 뭔지 알 방법이 있을까요?</a>
                          </strong>
                          <div class="auth-info">
                              <i class="icon-add-comment"></i>
                              <span class="time">2016-01-05 18:47</span>
                              <a href="./user/profile.jsp" class="author">김문수</a>
                          </div>
                          <div class="reply" title="댓글">
                              <i class="icon-reply"></i>
                              <span class="point">12</span>
                          </div>
                      </div>
                  </div>
              </li>
          </ul>
          <div class="row">
              <div class="col-md-3"></div>
              <div class="col-md-6 text-center">
                  <ul class="pagination center-block" style="display:inline-block;">
                      <li><a href="#">«</a></li>
                      <li><a href="#">1</a></li>
                      <li><a href="#">2</a></li>
                      <li><a href="#">3</a></li>
                      <li><a href="#">4</a></li>
                      <li><a href="#">5</a></li>
                      <li><a href="#">»</a></li>
                </ul>
              </div>
              <div class="col-md-3 qna-write">
                  <a href="./qna/form.jsp" class="btn btn-primary pull-right" role="button">질문하기</a>
              </div>
          </div>
        </div>
    </div>
</div>
<%@ include file="/include/footer.jspf" %>
	</body>
</html>