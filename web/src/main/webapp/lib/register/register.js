$(function(){
    /**
     * 访问注册页面预加载省
     */
    $.ajax({
        url:"/sys/area",
        data:{id:150000},
        type:"POST",
        dataType: "json",
        success:function(data){
            $.each(data.data,function (v,d) {
                var option = '<option value="'+d.id+'">'+d.name+'</option>';
                $("#province").append(option);
            })
        },
        error:function(){
            layer.errMsg("网路异常");
        }
    });



    /**
     * 用户名正则判断
     */
    $.validator.addMethod("enname", function(value, element) {
        return this.optional(element) || /^[A-Za-z0-9]+$/.test(value);
    }, "格式不正确,应由英文字母、数字组成！");

    $.validator.addMethod("tobaccoLicense", function(value, element) {
        var flag = false;
        if(value == 1) {
            flag = $('.tobaccoLicenseSave').val().length > 0;
        }else{
            flag = true;
        }
        return flag;
    }, "请上传烟草证");

    $.validator.addMethod("types", function(value, element) {
        var va = '';
        if(value == 1){
            $('input[name="goodsType"]:checked').each(function(i,v){
                console.log(i,v.value);
                va += v.value + ',';
            });
            if(va.length > 0) {
                return true;
            }
            return false;
        }else{
            return true;
        }

    }, "商品分类必填");

    /**
     * 表单验证
     */
    $("#form-member-add").validate({
        ignore:[],
        rules:{
            mrhtType:{
                required:true,
                types:true
            },
            compactNo:{
                required:true,
            },
            compactName:{
                required:true,
            },
            compactExpiryDate:{
                required:true,
            },
            compactImage:{
                required:true,
            },
            mrhtName:{
                required:true,
            },
            loginName:{
                required:true,
                minlength:6,
                maxlength:16,
                enname:true,
                remote: {
                    url: "/merchant/exist",
                    type: "post",
                    async:false,
                    dataType: "json",
                    data: {
                        loginName:function () {
                            return $("#loginName").val();
                        }
                    },
                    dataFilter:function(data){
                        data = JSON.parse(data);
                        if(data.code == 2) {
                            return false;
                        }
                        return true;
                    }
                }
            },
            password:{
                required:true,
            },
            okPassword:{
                required:true,
                equalTo: "#password"
            },
            mobile:{
                required:true,
            },
            province:{
                required:true,
            },
            city:{
                required:true,
            },
            district:{
                required:true,
            },
            lng:{
                required:true,
                number:true
            },
            lat:{
                required:true,
                number:true
            },
            address:{
                required:true,
            },
            financialContact:{
                required:true,
            },
            contactNum:{
                required:true,
            },
            openBank:{
                required:true,
            },
            accountName:{
                required:true,
            },
            accountNum:{
                required:true,
            },
            hasTobacco:{
                required:true,
                tobaccoLicense:true
            },
        },
        messages:{
            mrhtType:{
                required:'这是必填字段'
            }
        },
        /**
         * 提交form表单
         */
        onkeyup:false,
        focusCleanup:true,
        success:"valid",
        submitHandler:function(form){
            $("#submit").attr({"disabled":"disabled"});
            $.ajax({
                type: 'post',
                url: '/merchant/save',
                data: $(form).serializeArray(),
                success:function (data) {
                    if(data.code == 1){
                        succMsg(data.msg);
                        setTimeout(function () {
                            location.href = '/';
                        },600);
                    }
                },
                error:function (data) {
                    errMsg("网络异常");
                }
            });

        }
    });
});


/**
 * 访问注册页面加载地区
 * @param parentId
 * @param type
 * @param addr
 */
function loadData(parentId,type,addr) {
    $.ajax({
        url:"/sys/area",
        data:{parentId:parentId},
        type:"POST",
        dataType: "json",
        success:function(data){
            $("#"+type).empty();
            var option = '<option value="">'+addr+'</option>';
            $("#"+type).append(option);
            $.each(data.data,function (v,d) {
                option = '<option value="'+d.id+'">'+d.name+'</option>';
                $("#"+type).append(option);
            })
        },
        error:function(){
            layer.errMsg("网路异常");
        }
    });
}



