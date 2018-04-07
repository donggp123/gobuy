function isMerchant(){
    if($("#merchant").val() == '' ){
        $(".merchant").html("商户名称不能为空");
        $(".merchant").css("color","red");
        return false;
    }
    $(".merchant").html("");
    return true;
}
function isUserName(){
    if($("#username").val() == ""){
        $(".username").html("用户名不能为空");
        $(".username").css("color","red");
        return false;
    }
    $(".username").html("");
    return true;
}
function isPassword(){
    if($("#password").val() == ""){
        $(".password").html("密码不能为空");
        $(".password").css("color","red");
        return false;
    }
    if($("#password").val().length < 6){
        $(".password").html("密码长度最小六位");
        $(".password").css("color","red");
        return false;
    }
    $(".password").html("");
    return true;
}
function isOkPassword(){
    if($("#okpassword").val() == ""){
        $(".okpassword").html("确认密码不能为空");
        $(".okpassword").css("color","red");

        return false;
    }
    if($("#password").val() != $("#okpassword").val() && $("#password").val().length != $("#okpassword").val().length){
        $(".okpassword").html("俩次密码不一致");
        $(".okpassword").css("color","red");
        return false;
    }
    $(".okpassword").html("");
    return true;
}
function registers(){
    if(isMerchant() && isUserName() && isPassword() && isOkPassword()){
        $.ajax({
            url:"",
            data:{

            },
            type:"psot",
            success:function(data){

            },
            error:function(){

            }
        })
    }
    return false;
}