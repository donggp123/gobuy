/**
 * 错误提示
 * @param msg
 */
function errMsg(msg){
    layer.msg(msg,{icon:2,time:1000});
}

/**
 * 成功提示
 * @param msg
 */
function succMsg(msg) {
    layer.msg(msg,{icon:1,time:1000});
}

function refresh() {
    location.replace(location.href);
}

/**
 * 添加
 */
function add(title,url,w,h){
    layer_show(title,url,w,h);
}

/**
 * 编辑
 */
function edit(title,url,w,h){
    var id = '';
    var count = 0;
    $.each($('input:checkbox:checked'),function(){
        if($(this).val() != '') {
            id += $(this).val() + ',';
            count ++;
        }
    });
    id = id.substring(0,id.length-1);
    if(id == '' || id == null || count > 1) {
        errMsg("请选择一条数据编辑！");
        return;
    }
    layer_show(title,url+"?id="+id,w,h);
}

/**
 * 删除
 */
function del(id,url){
    layer.confirm('确认要删除吗？',function(){
        $.ajax({
            type: 'POST',
            url: url,
            data: {ids:id},
            success: function(data){
                succMsg(data.msg);
                setTimeout(function () {
                    refresh();
                },1000)
            },
            error:function(data) {
                errMsg(data.msg);
            },
        });
    });

}

/**
 * 批量删除
 */
function datadel(url) {
    var ids = '';
    $.each($('input:checkbox:checked'),function(){
        if($(this).val() != '') {
            ids += $(this).val() + ',';
        }
    });
    ids = ids.substring(0,ids.length-1);
    if(ids.length > 0) {
        del(ids,url);
    }
}

function query() {
    $("#pn").val('');
    $('form').submit();
}

/**
 * 启用禁用
 * flag 0代表 启用 1代表 禁用
 * @param flag
 * @param id
 * @param msg
 * @param url
 */
function enableAndDisable(flag,msg,url) {
    var id = '';
    var count = 0;
    $.each($('input:checkbox:checked'),function(){
        if($(this).val() != '') {
            id += $(this).val() + ',';
            count ++;
        }
    });
    id = id.substring(0,id.length-1);
    if(id == '' || id == null) {
        errMsg("请选择一条数据编辑！");
        return;
    }
    layer.confirm(msg, function () {
        $.ajax({
            url:url,
            data:{id:id,num:flag},
            type:"post",
            success:function (data) {
                succMsg(data.msg);
                setTimeout(function () {
                    refresh();
                },1000)
            },
            error:function () {
                errMsg("网路异常");
            }
        });

    });
}

function checked(obj) {
    if($(obj).children('td:first-child').children('input').prop('checked')==true){
        $(obj).children('td:first-child').children('input').prop('checked',false);
    }else {
        $(obj).children('td:first-child').children('input').prop('checked',true);
    }
}



/**
 * 图片拆封方法
 * @param imgValue
 * @param id
 * @param judge
 */
function loadImg(imgValue,url,id,judge){
    imgValue = imgValue.split(";");
    if (judge == 1){
        $.each(imgValue,function (i,d) {
            $("#"+id).append('<img class="example" style="margin-right: 10px;max-width:158px; max-width: 158px; min-width:this.width; max-height:88px; min-height: this.Height" src="'+url+'/'+d+'">')
        });
    }else{
        $("#"+id).append('否')
    }

}

/**
 * 图片上传
 * @param id
 * @param save
 * @param show
 */
function uploads(id,save,show,flag){
    $("#"+id).trigger("click");
    $("#"+id).on("change",function () {
        var data = new FormData();
        data.append('uploadFile', $('#'+id)[0].files[0]);
        $.ajax({
            url:'/upload',
            type:'POST',
            data:data,
            contentType: false,    //不可缺
            processData: false,    //不可缺
            success:function(data){
                if(data.code == 1){
                    if(flag == 1 ){
                       $("."+save).val("");
                       $("#"+show).children("a").remove();
                    }
                    var src = data.data.imgServer+"/"+data.data.imgPath;
                    if($("."+save).val().length>0) {
                        $("."+save).val($("."+save).val()+';'+data.data.imgPath);
                        $("#"+show).append('<div style="position: relative;float:left;margin-right: 15px;margin-bottom: 10px;">' +
                            '               <img style="width:158px;height: 88px;" src="'+src+'" alt="">' +
                            '               <span onclick="delImg(this);" style="text-align:center;line-height:20px;color:#fff;cursor:pointer;font-size: 24px;display: block;width: 20px;height:20px;background: red;position: absolute;top: 0px;right:0px;">×</span>' +
                            '               </div>')
                    }else{

                        $("."+save).val(data.data.imgPath);
                        $("#"+show).append('<div style="position: relative;float:left;margin-right: 15px;margin-bottom: 10px;">' +
                            '               <img style="width:158px;height: 88px;" src="'+src+'" alt="">' +
                            '               <span onclick="delImg(this);" style="text-align:center;line-height:20px;color:#fff;cursor:pointer;font-size: 24px;display: block;width: 20px;height:20px;background: red;position: absolute;top: 0px;right:0px;">×</span>' +
                            '               </div>')
                    }
                    $("#"+show).css("display","block");
                    var obj = document.getElementById(id) ;
                    obj.outerHTML=obj.outerHTML;
                }
            }
        });
    })

}

/**
 * 删除上传图片
 * @param self
 */
function delImg(self){
    var parent = $(self).parent().parent().children();
    var arr = parent.val().split(";");
    var url = $(self).prev().attr("src");
    var value = '';
    $.each(arr,function (i,d) {
        if(url.indexOf(d) > 0){
            $(self).parent().remove();
        }else{
            value += arr[i]+";"
        }
    });
    value = value.substring(0, value.length - 1);
    parent.val(value);

}