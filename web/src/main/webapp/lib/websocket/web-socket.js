$(function () {

    var websocket;
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://" + path + "/ws");
    } else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket("ws://" + path + "/ws");
    } else {
        websocket = new SockJS("http://" + path + "/ws/sockjs");
    }
    websocket.onopen = function(event) {
        console.log("Web-Socket:已建立连接",event);
    };
    websocket.onmessage = function(event) {
        var data = JSON.parse(event.data);
        console.log('收到消息',data)
        showAuto();
        var title = data.title;
        var url = '/message/view?id='+data.id;
        var content = data.text;
        playMusic(CTX + '/lib/websocket/new_msg.mp3');
        new Pop(title,url,content);
    };
    websocket.onerror = function(event) {
        console.log("Web-Socket:发生错误",event);
        console.log(event);
    };
    websocket.onclose = function(event) {
        console.log("Web-Socket:已关闭连接",event);
    }

    function sendMsg(data){
        websocket.send(JSON.stringify(data));
    }

    //播放提示音的方法
    function playMusic(url){
        var div = $('#popContent');
        div.append('<audio src="'+url+'" autoplay></audio>')
    }
})