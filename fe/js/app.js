      var serverUrl = "http://localhost:8080/api/todo/";

      function checklist() {
          $("input:checkbox[class='toggle']").each(function () {
              if (this.value == "1") { //값 비교
                  this.checked = true; //checked 처리
              }
          });
          var todo = $("input:checkbox[class='toggle']").length
          var left = $("input:checkbox[class='toggle']:checked").length
          var active = todo - left;
          $(".left").html(active);
      }


      function alltodo() {
          getAllList();
      }

      function active() {
          $('li').filter(".completed").hide();
          $('li').filter(".activetodo").show();

      };

      function completed() {
          $('li').filter(".activetodo").hide();
          $('li').filter(".completed").show();
      }

      function getAllList() {

          $.getJSON(serverUrl, function (data) {

              var str = "";

              $(data).each(
                  function () {
                      if (this.completed == 0) {
                          str += "<li class='activetodo'><div class='view' data-id=" + this.id + ">" +
                              "<input class='toggle' type='checkbox'><label>" + this.todo + "</label>" +
                              "<button class='destroy'></button></div></li>";
                      } else {
                          str += "<li class='completed'><div class='view' data-id=" + this.id + ">" +
                              "<input class='toggle' type='checkbox' value=" + this.completed + "><label>" + this.todo + "</label>" +
                              '<button class="destroy"></button></div></li>';
                      }
                  });
              $(".todo-list").html(str);
              checklist();
          }).error(function (e) {
              alert('데이터 불러오기 실패');
          });
      }

      //todo 작성
      $(".new-todo").keydown(function (key) {
          if (key.keyCode == 13) {
              if ($(".new-todo").val() == '') {
                  alert('내용을 입력하세요');
              } else {
                  var date = new Date().valueOf();
                  var todo = $(".new-todo").val();
                  $.ajax({
                      type: 'POST',
                      url: serverUrl,
                      headers: {
                          "Content-type": "application/json",
                          "X-HTTP-Method-Override": "POST"
                      },
                      dataType: 'text',
                      data: JSON.stringify({
                          todo: todo,
                          completed: 0,
                          date: date
                      }),
                      success: function (result) {
                          getAllList();
                          $(".new-todo").val('');
                      }
                  }).error(function (error) {
                      alert('ajax err' + error);
                  })
              }
          }
      });

      //todo 삭제
      $('.todo-list').on("click", ".destroy", function (result) {

          var todo = $(this).parents();
          var id = todo.attr('data-id');
          $.ajax({
              type: 'delete',
              url: serverUrl + id,
              headers: {
                  "Content-Type": "application/json",
                  "X-HTTP-Method-Override": "DELETE"
              },
              dataType: 'text',
              success: function (result) {
                  getAllList();
              }
          }).error(function (error) {
              alert('ajax err' + error);
          })
      
      });

      //업데이트
      $('.todo-list').on("change", ".toggle", function () {

          var todo = $(this).parents();
          var id = todo.attr('data-id');

          if ($(this).is(":checked")) {
              $.ajax({
                  type: 'PUT',
                  url: serverUrl + id,
                  headers: {
                      "Content-type": "application/json",
                      "X-HTTP-Method-Override": "PUT"
                  },
                  dataType: 'text',
                  data: JSON.stringify({
                      completed: 1,
                  }),
                  success: function (result) {
                      getAllList();
                  }
              }).error(function (error) {
                  alert('ajax err' + error);
              })

          } else {
              $.ajax({
                  type: 'PUT',
                  url: serverUrl + id,
                  headers: {
                      "Content-type": "application/json",
                      "X-HTTP-Method-Override": "PUT"
                  },
                  dataType: 'text',
                  data: JSON.stringify({
                      completed: 0,
                  }),
                  success: function (result) {
                      getAllList();
                  }
              }).error(function (error) {
                  alert('ajax err' + error);
              })

          }
      });

//완료한 일 삭제
      $(".clear-completed").click(function () {
          $.ajax({
              type: "DELETE",
              url: serverUrl + "/completedDelete",
              success: function (resData) {
                  alert("완료한 일 삭제 성공");
                  $(".completed").remove();
              }
          }).error(function (error) {
              alert('ajax err' + error);
          })
      });
      getAllList();
