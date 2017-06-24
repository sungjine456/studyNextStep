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
			$(".qna-comment-count").html("<strong>"+json.countOfAnswer+"</strong>개의 의견");
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
			if (json.status) {
				deleteBtn.closest('article').remove();
			}
		}
	});
}

function onError(xhr, status) {
	alert("error");
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