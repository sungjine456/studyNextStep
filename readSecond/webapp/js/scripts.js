$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
	e.preventDefault();

	var queryString = $("form[name=answer]").serialize();

	$.ajax({
		type : 'post',
		url : '/api/qna/addAnswer',
		data : queryString,
		dataType : 'json',
		error : onError,
		success : function(json, status) {
			var answerTemplate = $("#answerTemplate").html();
			var template = answerTemplate.format(json.answer.writer,new Date(json.answer.createDate), 
					json.answer.contents, json.answer.answerId);
			$(".qna-comment-slipp-articles").prepend(template);
			alert(json.countOfAnswer);
			countOfAnswerFuction(json.countOfAnswer);
		},
	});
}

$(".qna-comment").on("click", ".form-delete", deleteAnswer);

function deleteAnswer(e) {
	e.preventDefault();

	var deleteBtn = $(this);
	var queryString = deleteBtn.closest("form").serialize();

	$.ajax({
		type : 'post',
		url : "/api/qna/deleteAnswer",
		data : queryString,
		dataType : 'json',
		error : onError,
		success : function(json, status) {
			if (json.result.status) {
				deleteBtn.closest('article').remove();
				countOfAnswerFuction(json.countOfAnswer);
			}
		}
	});
}

function onError(xhr, status) {
	alert("error");
}

function countOfAnswerFuction(countOfAnswer){
	$(".qna-comment-count").html("<strong>"+countOfAnswer+"</strong>개의 의견");
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};