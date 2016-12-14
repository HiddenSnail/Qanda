/**
 * Created by huangrui on 2016/12/12.
 */
$(document).ready(function() {
    $("#bt").click(function() {
        var userdata = {
            "email":$("#em").val(),
            "password":$("#pw").val()
        }

        $.ajax({
            type:"POST",
            url:"/user/login",
            contentType:"application/json; charset=UTF-8",
            data: JSON.stringify(userdata),
            dataType: "text",
            success: function(data) {
                window.location.href = "/user/qanda"
            },
            error: function(request, error) {
                console.log(request.responseJSON.message)
            }
        });
    });
});