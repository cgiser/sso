var ClickLog = {
    log:function(msg){
        if(window.sdoLogger){
            sdoLogger.authId = "9a32ec7597229a9f";
            sdoLogger.clickLog("action="+msg);
        }
    }
}

function SendAPPDownLoadSMS(mobile){	
	var script=document.createElement('script');
	document.body.appendChild(script);
	script.src="http://txz.sdo.com/common/msgsend/?m="+mobile+"&t=2&method=SendAPPDownLoadSMSCallback&fromid=weblogin&r="+Math.random();
}

function SendAPPDownLoadSMSCallback(data){
	var d = eval('('+data+')');
	if(d.return_code==0){
		$("#form_tips").removeClass("error").html("已发送到您手机，请注意查收");
	}else{
		$("#form_tips").html(d.msg.replace(' ',''));
	}
}


function isPhone(val){
    var gvPhoneRegExpress=new Array();
    gvPhoneRegExpress.push(/^14[57]\d{8}$/);
    gvPhoneRegExpress.push(/^15[012356789]\d{8}$/);
    gvPhoneRegExpress.push(/^13[0-9]\d{8}$/);
    gvPhoneRegExpress.push(/^18[0256789]\d{8}$/);   
    var lvCellphoneIsOk=false;
    for (var i=0;i<gvPhoneRegExpress.length;i++){
        if(gvPhoneRegExpress[i].test(val)){
            lvCellphoneIsOk=true;
            break;
        }
    }
    return lvCellphoneIsOk;
}

function RefreshUI(){
	$(".status_notInstalled p").html("下载链接将以短信的方式发送至您的手机");
	$(".sms_btn").html("发送短信");
	if(!window.clearID){
		$(".sms_btn").removeClass("disabled");
	}
	$(".username").prop("disabled", false);
	$(".form_tips").removeClass("status_index");
	$(".clear_btn").hide();
	if($(".username").val()==""){$(".clear_btn").hide();}else{$(".clear_btn").show();}
	
}

/*
        状态标识                添加全局class           说明
    toClass = index              index                  帐号密码登录
              mobile             mobile                 手机一键登录
              mobile_wait        mobile_wait            手机一键登录-后续状态
              code2d             code2d                 二维码登录
              sms                sms                    短信登录
              checkcode          checkcode              图片验证码
              safe_code_a8       safe_code_a8           A8密保
              safe_code_a6       safe_code_a6           A6密保
              safe_code_card     safe_code_card         安全卡
              fcm                fcm                    防沉迷

    toClass = mobile_wait &                             手机一键登录-后续状态
    status  = suc                suc                    手机一键登录-成功
              notInstalled       notInstalled           手机一键登录-未安装
              bindMobile         bindMobile             手机一键登录-绑定手机
*/
function jump(toClass,status) {
	RefreshUI();
	
    CURRENT_TAB = toClass

    if(toClass.length < 1){
        return false;
    } else {
        $("#app_sdo_login")[0].className =  "app_sdo_login " + toClass ;
        $("#login_frame")[0].className = "login_frame"
    }
    
    $(".app_sdo_login #form_tips").removeClass("show_tips error mobile_tip status_index").html("&nbsp");
    $("#user_name_input .cell_input_notice").html("手机/邮箱/个性账号");
    $(".code2d_tip").hide();
    $("#user_name_input").removeClass("no_icon input_cur").addClass("with_icon");

    if(toClass == "index" || toClass == "mobile" || toClass == "code2d" || toClass == "sms") {
        setCurInSlide($("#nav .btn_"+toClass));
    }
	
	if(toClass == "sms"){
		$(".sms_btn").html("获取验证码");
	}

    if(toClass == "mobile"){
		$(".app_sdo_login.mobile #form_tips").addClass("show_tips status_index").html("<a href='http://txz.sdo.com/pushlogin/?fromid=48' target='_blank'>手机一划，轻松登录</a>");
        if(status == "notInstalled" || status == "bindMobile"){
            $("#user_name_input .cell_input_notice").html("请输入真实手机号码");
        }else{
            $("#user_name_input .cell_input_notice").html("手机账号/绑定手机的账号");
        }
    }

    if(toClass == "mobile_wait"){
		$("#user_name_input .cell_input_notice").html("请输入真实手机号码");
        $(".app_sdo_login").addClass("mobile mobile_wait");
        $("#user_name_input").addClass("input_cur").removeClass("width_clear_btn");
        $("#user_name_input .cell_input_notice").addClass("cell_input_notice_hide");
        setCurInSlide($("#nav .btn_mobile"));
        if(status == "suc"){
            $(".app_sdo_login.mobile.mobile_wait").addClass(status);
            $(".app_sdo_login.mobile #form_tips").addClass("show_tips").html("登录确认码已发送，请打开手机确认");
		}else if(status == "error"){
            $(".app_sdo_login.mobile.mobile_wait").addClass(status);
			$(".app_sdo_login.mobile #form_tips").addClass("show_tips error").html('您屏蔽了账号登录，可按<a target="_blank" href="http://txz.sdo.com/txz/pushlogindown1?fromid=48#pbdl">引导打开</a>');
        }else if(status == "notInstalled"){
            $(".app_sdo_login.mobile.mobile_wait").addClass(status);
            $(".app_sdo_login.mobile #form_tips").addClass("show_tips error").html("您需安装盛大通行证手机版");
            $("#user_name_input").removeClass("with_icon").addClass("no_icon");
			
        }else if(status == "bindMobile"){
            $(".app_sdo_login.mobile.mobile_wait").addClass(status);
            $(".app_sdo_login.mobile #form_tips").addClass("show_tips error").html("您需安装盛大通行证手机版，并绑定账号");
			$(".username").val("");
			setTimeout(function(){$(".username").focus();},500);
            $("#user_name_input").removeClass("with_icon").addClass("no_icon");
        }
		$(".clear_btn").hide();
    }


    if(toClass == "setpass"){
        $(".setpass_account").text($(".username").val());
        setCurInSlide($("#nav .btn_sms"));
    }

    //处理二维码时的轮询
    if(window.QRCodeBiz){
        if(CURRENT_TAB == "code2d"){
            QRCodeBiz.Start();
        }else{
            QRCodeBiz.Stop();
        }
    }
}


    
$(function(){

    window.QRCodeBiz = {
        MaxTimes: 80,
        Interval: 2000,
        ReqTimeOut: 5000,
        PollingData:{},
        Work: 0,
        OpExpired:120*1000,
        t:null,
        Start: function() {
            setTimeout(QRCodeBiz.FreshQRImage,10);
            this.Work = 1;
            this.MaxTimes = 400;
            this.Polling();
            this.PollingData = $.CONFIGS.Login.QRCode.reqQRCodePollingData;
        },
        StartWait: function(w) {
            setTimeout(QRCodeBiz.FreshQRImage,10);
            this.Work = 1;
            this.MaxTimes = 400;
            setTimeout(QRCodeBiz.Polling,w);
        },
        FreshQRImage:function(){
            $("#code2 img").prop("src", $.CONFIGS.Login.QRCode.QRCodeImageUrl + '&r=' + Math.random());
        },
        Stop: function() {
            this.Work = 0;
        },
        Resume:function(){
            this.Work = 1;
            this.Polling();
        },
        Polling: function() {
            clearTimeout(QRCodeBiz.t);
            if (this.MaxTimes > 0 && this.Work == 1 ){
                this.t = setTimeout(QRCodeBiz.ReqRQCodeLogin, this.Interval);
            }
        },
        PollingSuc: function(d) {
            this.Work = 0;
            var url = $.CONFIGS.App.serviceUrl;
            LOGIN_TAB = "code2d"
            setCookieForLoginWay("CURRENT_TAB",LOGIN_TAB)
            if(d.data.nextAction==0){
                if(url.indexOf("?")>-1){
                    url = url +"&ticket="+d.data.ticket;
                }else{
                    url = url +"?ticket="+d.data.ticket;
                }
			
                setTimeout(function(){
                    if($.CONFIGS.App.target=="iframe"){
                        location = url;
                    }else{
                        top.location = url;
                    }
                },600);
            }else{
                if(typeof  d.data.nextAction !== "undefined"){
                    nextActions[d.data.nextAction](d)
                }
            }
	
        },
        ReqRQCodeLogin: function() {
            RequestHPS("codeKeyLogin_JSONPMethod",QRCodeBiz.PollingData,$.CONFIGS.Login.codeKeyLogin.url,QRCodeBiz.Success,QRCodeBiz.Failed,QRCodeBiz.ReqTimeOut);
            QRCodeBiz.MaxTimes--;
        },
        Success: function(d) {
            if (d.return_code == 0) {
                QRCodeBiz.PollingSuc(d);
            } else {
                QRCodeBiz.Polling();
            }
        },
        Failed: function() {
            this.Work = 0;
        },
        Expired: function() {
            this.Work = 0;
        }
    };
    //=====================================================================
    // 辅助函数
    //=====================================================================
    //请求HPS过程
    function RequestHPS(method,data,url,success,error,timeout){
        error = error || function(e,statusText){
            nextActions["show_error"]("对不起，网络繁忙请重试");
            log("RequestHPS error "+statusText)
        }
        timeout =  typeof timeout == "number" ? timeout : 5000;
        var obj = {
            url : url,
            cache : false,
            data : data,
            dataType : "jsonp",
            jsonpCallback:method,
            success : success,
            error : error,
            timeout : timeout 
        }
        if(method == "random") {
            delete obj.jsonpCallback
        }
        log("RequestHPS")
        return $.ajax(obj);
    }
    window.log = function(msg){
        window.console && console.log(msg)
    }
	

    //=====================================================================
    // 验证框架
    //=====================================================================
    $.Valid = {
        asserts: {},
        assert:function(name, obj){
            $.Valid.asserts[name] = obj;
        },
        check: function(array, values){
            var asserts = $.Valid.asserts 
            for(var i = 0; i < array.length; i++){
                var obj = asserts[array[i]];
                var val = values[i];
                if(obj){
                    for(var msg in  obj){
                        if(obj.hasOwnProperty(msg)){
                            if(obj[msg](val)){
                                return msg
                            }
                        }
                    }
                }else{
                    throw "没有声明此断言组"
                }
            }
            return true
        }
    }
    var assert = $.Valid.assert;
    var check = $.Valid.check;
    var checkAccountHash = {
        getobj:function (username){
            if(checkAccountHash[username]){
                return checkAccountHash[username];
            }else{
                return false;
            }
        },
        getJSON: function(username,callback){
            if(checkAccountHash[username]){
                return callback(checkAccountHash[username])
            }
            var  params = $.CONFIGS.Login.checkAccountType;
            var  url = params.url;
            var  data = params.requestdata;
            data.inputUserId = username;
            RequestHPS("checkAccountType_JSONPMethod", data, url, function(json){
                checkAccountHash[username]=json;
                callback(json);
            })
        }
    };//保存输入帐户
    assert("username",{
        "请输入账号": function(value){
            return value == ""
        },
        "账号不能小于4位": function(value){
            log(value)
            return value.length < 4;
        },
        "账号不能大于40位": function(value){
            return value.length >= 40
        },
        "checkAccountType": function(value){
            if(value.length >= 4 && CURRENT_TAB != "sms" && CURRENT_TAB != "mobile_wait"){
                checkAccountHash.getJSON(value, function(json){
                    if(json.data.existing ==0){
                        nextActions["show_error"]("对不起，账号不存在，请重新输入");
                    }
                })
            }

        }
    })
    assert("username_no_check_exist",{
        "请输入账号": function(value){
            return value == ""
        },
        "账号不能小于4位": function(value){
            log(value)
            return value.length < 4;
        },
        "账号不能大于40位": function(value){
            return value.length >= 40
        }

    })


    assert("password",{
        "请输入密码": function(value){
            return value == ""
        },
        "密码不能大于40位": function(value){
            return value.length >= 40
        }
    });
    //重置密码时的新规则
    assert("newPassword",{
        "请输入密码": function(value){
            return value == ""
        },
        "密码由6~30位字母或数字组成": function(value){
            return  value.length > 30 || value.length < 6 || /[\W_]/.test(value)
        }
    })
    
    assert("checkCode",{
        "请输入验证码": function(value){
            return value == ""
        },
        "验证码不能大于40位": function(value){
            return value.length >= 40
        }
    })
    assert("codekey1",{
        "请输入第一个指定位的密宝数字": function(value){
            return value == ""
        }
    })
    assert("codekey2",{
        "请输入第二个指定位的密宝数字": function(value){
            return value == ""
        }
    })
    assert("codekey3",{
        "请输入第三个指定位的密宝数字": function(value){
            return value == ""
        }
    })
    assert("codekey4",{
        "请输入第四个指定位的密宝数字": function(value){
            return value == ""
        }
    })
	assert("ptekey",{
        "请输入密宝": function(value){
            return value == ""
        }
    })
	
	
    //=====================================================================
    // 开始登陆
    //=====================================================================

    //用于记住登陆方式
    var LOGIN_TAB;
    //如果retrun_code 为0说明操作成功, 然后查看nextAction，做相应的处理
    var nextActions ={
        0: function(json){//登录成功，转向应用地址
            log("跳转");
            var url  = $.CONFIGS.App.serviceUrl;
            if($.CONFIGS.App.isSupportAutoLogin==1&&$("[name='autoLoginFlag']")[0].checked){
                ClickLog.log("autoLoginSuc");
                url += (url.indexOf("?") > -1 ? "&autologin=1&ticket=" : "?autologin=1&ticket=") + json.data.ticket;
            }else{
                url += (url.indexOf("?") > -1 ? "&ticket=" : "?ticket=") + json.data.ticket;
            }
            
            //保持这次的登录方式到cookie
            var lastuser = ($("#username").val()=="undefined")?"":$("#username").val();
            setCookieForLoginWay("CURRENT_TAB",(LOGIN_TAB || "index")+"|"+escape(lastuser));
            setTimeout(function(){
                window[ $.CONFIGS.App.target=="iframe" ? "self" : "top"].location = url;
            }, 600);
        },
        2:  function(json){ //静态认证
            jump("index");
        },  
        8:  function(json){  //图片验证码
            $(".login_btn").removeClass("disabled")
            jump("checkcode");
            $("#img_password").focus().val("");//当出现图片验证码，安全卡或密宝时，有光标在中间闪，，并且需要点一下输入框，才能输入
            var data = $.CONFIGS.Login.checkCodeLogin.requestdata;
            data.guid = json.data.guid;
            //更换图片
            $("#captchaImg").prop("src",json.data.checkCodeUrl)
            //点击换一张新图片
            $(".checkcode_change").unbind("click").click(function(){
                $("#captchaImg").prop("src",json.data.checkCodeUrl+"&xxx="+Math.random())
            });
        },  
        13: function(json){  //密宝
            $(".login_btn").removeClass("disabled");
            
            var data = $.CONFIGS.Login.dynamicLogin.requestdata;
            data.guid = json.data.guid;
            data.loginType = 1;
            //每次进来时把上次的都清空
            $(".safeCodeList input").val("")
            /*
                登录方式
		1: ekey
		2: ecard
                密宝类型
                X6	1	6位随机码机制的密宝
                X8	2	8位随机码机制的密宝
                A8	3	挑战码机制的密宝
                E8	2	8位随机码机制的密宝
                D6	1	6位随机码机制的密宝
                USB	4	USB密宝，也是挑战码，提示页面和其它密宝有差异
             */
            switch(json.data.deviceType){
                case 1 :
                case 2 :
                    var challenge = json.data.challenge.split('');
                    $("#safe_code_a6_input li .safeCodeText").each(function(i){
                        $(this).text("第"+challenge[i]+"位");
                    });
                    jump("safe_code_a6");
                    $("#codekey1").focus();
                    break;
                case 3 :
                    $("#safe_code_a8_input .tzcode").text(json.data.challenge);
                    jump("safe_code_a8");
                    $("#ptekey").focus();
                    break;
                case 4 :
                    break;
            }
			
        }, 
        18: function(json){//安全卡
            $(".login_btn").removeClass("disabled");
           
            jump("safe_code_card");//切换面板与收集从后端返回的数据
            //每次进来时把上次的都清空
            $("#safe_code_code_input .safeCodeList input").val("").eq(0).focus()
            var data = $.CONFIGS.Login.dynamicLogin.requestdata;
            //重写坐标
            log("安全卡坐标")
            var array =  json.data.challenge.split("|");
            $("#safe_code_code_input .safeCodeText").each(function(i){
                $(this).text(array[i])
            });     
            data.guid = json.data.guid;
            data.loginType = 2;
        },
        100:$.noop,  //声纹  
        201:$.noop,  //账号升级引导
        202:$.noop,  //防沉迷引导 
        203:$.noop,  //手机引导 (提示绑定手机) 
        204:$.noop,  //手机引导 (提示手机登录)
        205:$.noop,  //注册即自动登录是返回到cas.sdo.com/cas/login?service,自己跳转给自己 
        206:$.noop,  //注销的下一步是去注销页面
        207:$.noop,   //去默认页面（注销时非信任域名）
        show_error: function(json){
            var error = typeof json == "string" ? json : json.data.failReason;
            $("#form_tips").removeClass("status_index").addClass("show_tips error").html(error).show();
        },
        //手机一键登录的轮询处理
        check_mobile_login: function(url, data, method){
            var args = arguments;
            var action = nextActions["check_mobile_login"]
            RequestHPS(method, data, url, function(json){
                log("还有 "+action.times+ "次轮询")
                //如果发生错误，说明没有操作或操作失败
                if(json.return_code != 0){
                    //判定操作时间与判定操作次数
					
                    if(json.return_code=="-10516809"){//手机拒绝一键登录
                        jump('mobile_wait','refuse');
                        nextActions["check_mobile_login"].start = false;
                        ClickLog.log("PMLoginRefuse");
						nextActions["show_error"]("您拒绝了本次登录");
                        return 
                    }
                    
                    if(json.return_code=="-10516808"){//action.total_time
                        //如果次数小于0 或用时超过给定时间，就报错
                        if(action.times <  0  || (new Date - action.start_time > action.total_time)){
                            jump('mobile_wait','step3error')
                            nextActions["show_error"]("处理已超时，请重试");
                        }else{
                            action.times--;
                            action.timeoutID =  setTimeout(function(){
                                log("action.start"+ action.start+ "CURRENT_TAB " + CURRENT_TAB)
                                if(action.start && CURRENT_TAB == "mobile_wait"){
                                    action.apply(null, args)
                                }
                            },action.delay)
                        }
                        return		
                    }else{
                        ClickLog.log("PMLoginErr");
                        nextActions["show_error"](json);
                        return
                    }
                    json.data.nextAction = "show_error";  
                }else{
                    ClickLog.log("PMLoginSuc");
                }
				
                if(typeof  json.data.nextAction !== "undefined"){
					
                    nextActions[json.data.nextAction](json)
                }
            },$.noop, 5000)
            
        },
        set_password: function(json){
            jump("setpass");
            var url = $.CONFIGS.App.serviceUrl;
            if(url.indexOf("?")>-1){
                url = url +"&ticket="+json.data.ticket
            }else{
                url = url +"?ticket="+json.data.ticket
            }
            window.set_password_redirect = url
            $.CONFIGS.Login.modifyPassword.requestdata.authKey = json.data.authKey;
			$.CONFIGS.Login.sendPassword.requestdata.authKey = json.data.authKey;
			var json_pre = json;
            $(".setpass_link a").unbind("click").click(function(){//直接进入应用
				var  params = $.CONFIGS.Login.sendPassword;
				var  url = params.url;
				var  data = params.requestdata;
				RequestHPS("sendPassword_JSONPMethod",data,url,function(json){
					nextActions["0"](json_pre);
				});
            });
            $(".setpass_account").text($(".username").val());
        },
        
        login: function(url, data, method, checkAfter, checkBefore){
            RequestHPS(method,data, url, function(json){
                //如果存在错误，就显示错误，再者有特殊处理，就特殊处理，没有就根据返回信息处理
                if(typeof checkBefore == "function"){
                    checkBefore(json)
                }
                if(json.return_code != 0){
                   
                    if(json.data.nextAction != void 0){
                        //切换卡切换时会清掉错误提示，因此需要延迟一下
                        setTimeout(function(){
                            nextActions["show_error"](json)
                        },33)
                    }else{//如果没有指示下一步要做什么（json.data.nextAction != void 0），那么就直接显示错误
                        json.data.nextAction = "show_error"
                    }
                }else if( typeof checkAfter == "function" &&  checkAfter(json) == false){
                    return
                }
                var SuccessLoginLogHash = {
                    index: "AccountPwdLoginSuc",
                    code2d: "QrcodeLoginSuc",
                    sms: "SmsLoginSuc"
                }
                var msg = SuccessLoginLogHash[LOGIN_TAB]
                if(SuccessLoginLogHash[LOGIN_TAB]){
                    ClickLog.log(msg)
                }
                // 2 免注册登录 3 短信登录
                if($.CONFIGS.Login.sendPhoneCheckCode.requestdata.type ==2){
                    ClickLog.log( "NewMobileLoginSuc");
                }
                if(typeof  json.data.nextAction !== "undefined"){
                    var fn = nextActions[json.data.nextAction ] 
                    if(typeof fn != "function"){
                        fn = $.noop
                    }
                    fn( json );
                }
            })
        },
        msm_login: $.noop
    }
    //3.1.7 一键登录的错误提示
    /* 11.06 一键登录-绑定提示点击效果 */
    $("body").delegate(".mobile_wait .form_tipList_tips","click",function(){
        $(this).toggle(
            function(){
                errorListSlide("up");
            },function(){
                errorListSlide("down");
            }
            ).trigger('click');
    });
    //=====================================================================
    // 处理个性化配置
    //=====================================================================
    window.CURRENT_TAB = "index"
    new function(){
        var UIConfig = $.CONFIGS.App.UIConfig;

        if($.trim(UIConfig.ADWords).length){
            $("#form_tips").addClass("show_tips")
        }
        //LoginWays:'STATICLOGIN|PUSHMESSAGE|QRCODE|SMS',
        var titles = UIConfig.LoginWays.toLowerCase();
        
        if(!titles){//如果为空
            return
        }
        
        titles = titles.replace("staticlogin","index")
        .replace("pushmessage","mobile")
        .replace("qrcode","code2d")
        var old = "index|mobile|code2d|sms";
        if(titles !== old){
            var parent = $("#nav")[0],nodes = [];
            //先移除全部，再添加合要求的
            old.replace(/\w+/g, function(word){
                var el =  $("#nav>.btn_"+word)[0]
                nodes.push(el);
                parent.removeChild(el)
            });
            nodes = $(nodes);
            CURRENT_TAB = titles.split("|")[0]

            titles.replace(/\w+/g, function(word){
                var el = nodes.filter(".btn_"+word)[0]
                if(el){
					$(el).removeClass("first").removeClass("last");
                    parent.appendChild(el);
                }
            });
			$(parent.childNodes[0]).addClass("first");
			$(parent.childNodes[parent.childNodes.length-1]).addClass("last");
            
        }
    }();
    //=====================================================================
    // 导航切换
    //=====================================================================
    /* 11.20 修改 nav滑动效果*/
    window.setCurInSlide = function(a) {
        a.siblings("li").removeClass("cur");
        a.siblings("li").find("a").removeClass("cur");
        a.addClass("cur");
        a.children("a").addClass("cur");
        a.siblings(".back").stop(true,true).animate({
            "left": (a.offset() || {
                left:0,
                top: 0

            }).left-1 + "px",
            "width": a.outerWidth()-2 + "px"
        },300,"swing");
    };

    
    window.LastUser = void 0;

    $("#nav>li").click(function(){
        var array = this.className.match( /btn_(\w+)/),cur = array[1]
        if(cur !== CURRENT_TAB){
            jump( cur );
            if(cur == "mobile"){
                ClickLog.log("ClickPMLoginTab");
            }
        }
    })
 
    //向服务器发送cookie的信息，记录上次登陆的方式|username
    function setCookieForLoginWay(name,value){ 
        var url =  location.protocol + '//' + location.host + '/sdo/Login/Tool.php?act=setCookie&name=' + escape(name) + '&value=' + escape(value)+"&r="+Math.random();		
        var image = new Image
        image.src = url;
    }
    function getCookie(name){
        var rerase = new RegExp('(?:^| )' + name + '(?:(?:=([^;]*))|;|$)')
        var match = String(document.cookie).match( rerase );
        return match ? decodeURIComponent(match[1]) : ""
    }
       
    //用上次成功登录的方式登录，因此需要自动切换到上次的切换卡去
    new function(){
        var cur = getCookie("CURRENT_TAB").split("|")[0];
        if(cur && CURRENT_TAB != cur){
            CURRENT_TAB = cur
        }
        jump( CURRENT_TAB );	
        //  }
        if($("#nav>.cur").length){
            setCurInSlide( $("#nav>.btn_"+cur));
        }else{
            setCurInSlide( $("#nav>li:eq(1)") );
        }
        //  alert(cur)
        //   setCurInSlide( $("#nav>.btn_"+cur));
        if(CURRENT_TAB == "mobile"){
            ClickLog.log("LastLoginByPM");
        }
    }
   

    window.LastUser = unescape( getCookie("CURRENT_TAB").split("|")[1] || "") ;
    //当它存在，并且不是undefined
    if(  window.LastUser ){
        $("#username").val(window.LastUser).trigger("keyup");
        $(".cell_input_notice").addClass("cell_input_notice_hide");
    }
    //所有面板
    //当点击面板下方的 “手机免注册通道”链接
    $("#mb_login").click(function(){
        CURRENT_TAB = "sms";
        jump('sms','index');
    });
    //=====================================================================
    // 各种点击事件绑定
    //=====================================================================
    //3.1.7  
    $(".login_form input").bind("focus",function(){
        var parent = $(this).parent()
        //隐藏placeholder
        parent.children(".cell_input_notice").addClass("cell_input_notice_hide");
        parent.addClass("input_cur")
    }).blur(function(){
        var parent = $(this).parent()
        $(this).parent().removeClass("input_cur")
        //如果它里面没有内容，则重新显示placeholder
        if( !this.value.length  ){
            parent.children(".cell_input_notice").removeClass("cell_input_notice_hide");
        }
    });
    //这里是处理帐号输入框右边的图标，没有内容时出现人头，有内容出现交叉
    var intervalID 
    if( $("#username").val().length ){//如果存在内容
        $("#username").parent().addClass("width_clear_btn")
    }
    $("#username").bind("mouseenter",function(){
        var parent = $(this).parent(), node = this;
        if(!intervalID){
            intervalID = setInterval(function(){
                if( node.value.length  ){
                    parent.addClass("width_clear_btn")
                }else{
                    parent.removeClass("width_clear_btn")
                }
            },10)
        }
    }).bind("mouseleave", function(){
        if(intervalID){
            clearInterval(intervalID);
            intervalID = 0;
        }
		
		if(CURRENT_TAB!="mobile_wait"){
			if($(".username").val()==""){$(".clear_btn").hide();}else{$(".clear_btn").show();}
		}
    })
    
    //当输入时隐藏错误提示
    $(".login_form").delegate(":text,:password","click",function(e){
        if(CURRENT_TAB != "mobile_wait"){
			$(".form_tips.error").removeClass("show_tips error").html("&nbsp;");
        }
    });

    //第一个面板
    //当点击用户名的最右边的X时，清空内容
    $(".clear_btn").click(function(){
        if($("#username").prop("disabled"))
            return
        var parent  = $(this).parent()
        parent.children("input.text").val("").focus();
        $("#password").val("");
        $("#checkCode").val("");
        parent.removeClass("width_clear_btn");
        $(".cell_input_notice").addClass("cell_input_notice_hide");
		$(".clear_btn").hide();
    });
    //点击复选框出现提示
    $(".autologin input, .autologin label").mousedown(function(){
        if($(this).parent().find("input").is(":checked")){
            $("#form_tips").removeClass("show_tips error").html("");
        }else{
            $("#form_tips").addClass("show_tips error").html($(this).parent().attr("title"));
        }
    });
    //一键登录，无需密码，安全便捷
    $("body").delegate(".index #password","focus", function(e){
        var value = $("#username").val();
        if(value.length >= 4){
            log("检测是否需要一键登录")
            checkAccountHash.getJSON(value, function(json){
                if(json.data.recommendLoginType & 16){//& 16
					setTimeout(function(){
						$(".form_tips").addClass("show_tips").html("体验<a href='javascript:void 0' id='js_jump_mobile'>手机一键登录</a>，无需密码，安全便捷");
						ClickLog.log("ShowRecommendPMLoginLink");
					},200);//事件注册冲突，优先业务的标准设置最终生效的逻辑。
                    
                }
            });
        }
    })
    //当点击"一键登录，无需密码，安全便捷"中的“一键登录”
    $("body").delegate("#js_jump_mobile","click",function(){
        ClickLog.log("ClickRecommendPMLoginLink");
        jump("mobile")
    })
    
    /* 11.20 新增 二维码浮层 */
    $(".setup_code2d").click(function(){
        $(this).siblings(".code2d_tip").slideToggle(200);
    })

    //处理各种安全卡，密码填空数据时高亮(密宝，安全码)     
    $(".safeCodeList input").focus(function(){
        $(this).parents(".safeCodeList").find(".cur").removeClass("cur");
        $(this).parents("li").addClass("cur")
    });
    $(".safeCodeList input").keyup(function(e){
        var self = $(this);
        setTimeout(function(){
            self.val(self.val().replace(/[^\d]/g,''));
            if(self.val().length == Number(self.attr("maxlength")) ){
                //这里的逻辑如下，如果用户填够足够的字母或数字（由maxlength决定）
                //就去掉当前元素的高亮，高亮下一个元素，并将焦点转移一下
                var el = self.parents(".safeCodeList").find(".cur").removeClass("cur")
                .next()
                if((el.find("input").val() || "").length){
                    el = el.next()
                }
                el.addClass("cur").find("input").focus();
            }
         
            //安全卡，按退格键清除密码时自动前移至上一个坐标框
            if(self.val().length == 0 && e.which == 8){
                self.parents(".safeCodeList").find(".cur").removeClass("cur")
                .prev().addClass("cur").find("input").focus().val("")
            }
			
        },0)
    })

    //第二个面板的隐藏按钮
    $("#login_btn .cancel_btn").add($("#login_btn .back_btn")).click(function(){
        $(".form_tips").removeClass("show_tips").html("");
        $("#password").val("");
        $("#checkCode").val("");
        $(".login_btn").removeClass("disabled");//将登录按钮去掉不可点状态
        switch(CURRENT_TAB){
            case "mobile" :
                ClickLog.log("PMLoginCancel");
            case "safe_code_card":
            case "safe_code_a8":
            case "safe_code_a6":
            case "checkcode":
                $(".username").val("").focus();
                jump("index")
                break;
            case "mobile_wait":
                ClickLog.log("PMLoginCancel");
                //手机一键登录不清除当前输入账号
                //停止轮询并跳转到mobile面板，并隐藏错误与填写内容
                nextActions["check_mobile_login"].start = false;
                jump("mobile")
                break;
        }
    })
    //第四个面板
    //当点击短信验证码按钮
    $(".sms_btn").click(function(){
		
        var self = $(this);
        if(self.hasClass("disabled")){
            return
        }
        
		/*发送下载APP链接*/
		if(CURRENT_TAB=="mobile_wait"){
			if(isPhone($(".username").val())){
				SendAPPDownLoadSMS($(".username").val());
				self.addClass("disabled");
				self.text("已发送");
			}else{
				$("#form_tips").html("请输入您的真实手机号码");
			} 
		}else{
			
			var username = $(".username").val();//用户名
			if(username==''){
				nextActions["show_error"]("请输入账号");
				return;
			}
			var error = check(["username" ], [username]);
			
			if(typeof error == "string"){
				return nextActions["show_error"](error);
			}
			
			//这里存在两个请求，一个用于获出验证码与checkCodeSessionKey
			//一个用于判定用户有没有设置密码，没有登陆后跳转到设置密码页面
			checkAccountHash.getJSON(username,function(json){
				if(json.data.existing==0&&(!isPhone(username))){
					nextActions["show_error"]("对不起，账号不存在，请确认后重新输入");
					self.removeClass("disabled");
					self.text("获取验证码");
					$(".username").prop("disabled", false)
					clearInterval(window.clearID || 0);
					log("判定手机号")
					return
				}else{
					//等于1 时就不用设置密码
					window.hasPwdLoginRecord = json.data.existing
					log(window.hasPwdLoginRecord)
					//发送短信验证码
					var params = $.CONFIGS.Login.sendPhoneCheckCode;
					var url = params.url;
					var data = params.requestdata;
					data.inputUserId = username;//手机号码
					if(json.data.existing==1){
						data.type = 3;
					}else{
						data.type = 2;//(注册)手机免注册登录
					}
					log("发送手机验证码")
					//     alert(1)
				 
					nextActions["msm_login"].smsSent = true;
					nextActions["login"](url, data, "sendPhoneCheckCode_JSONPMethod",function(){},function(json){
						//这是获取手机验证码后得到的参数   
						log("获取手机验证码后得到的参数 ")
						if(json.return_code != 0){
							log("sendPhoneCheckCode_JSONPMethod error")
							nextActions["show_error"](json.data.failReason);
						}else{
							log("开始倒计时")
							self.addClass("disabled");
							$(".username").prop("disabled", true)
							$(".form_tips").addClass("show_tips").html("短信验证码已发送，请于5分钟内输入")
							var time = 30
							window.clearID = setInterval(function(){
								if(--time){
									self.text(time +"秒");
								}else{
									self.removeClass("disabled");
									$(".username").prop("disabled", false)
									self.text("获取验证码");
									clearInterval(window.clearID);
								}  
							},1000);
							log("sendPhoneCheckCode_JSONPMethod ok")
							//如果是手机免注册登录，我们不让它发密码给用户，以便让用户进入设置密码的流程
							if(data.type == 2){
								$.CONFIGS.Login.phoneCheckCodeLogin.requestdata.sendPassword = 2 
							}
							$.CONFIGS.Login.phoneCheckCodeLogin.requestdata.checkCodeSessionKey = 
							json.data.checkCodeSessionKey;
						}

					});
				}
					
			})
		}
    });
  
    //所有面板
    $(document).keyup(function(e){
        var keycode = e.which;//回车提交功能
        if(keycode == 13 || keycode == 108){
			if(CURRENT_TAB!="mobile_wait"){
				 $(".login_btn").click();
			}else{
				if(nextActions["check_mobile_login"].start){
					$(".cancel_btn").click();
				}else{
					$(".sms_btn.sent_btn").click();
				}
				
			}
        }
    });
    
    $("#btn_to_feedback").bind("click",function(){
        $("#login_wrap").animate({
            "left":"-468px"
        },400);
        $("#feedback").animate({
            "left":0
        },400);
    })
    $("#btn_fb_back").bind("click", function(){
        $("#login_wrap").animate({
            "left":0
        },400);
        $("#feedback").animate({
            "left":"468px"
        },400);
    });
    
    /*单点登录*/
    RequestHPS("ssoLogin_JSONPMethod",$.CONFIGS.Login.ssoLogin.requestdata,$.CONFIGS.Login.ssoLogin.url,function(json){
        if(json.return_code==0){
            nextActions[json.data.nextAction](json);
        }else if($.CONFIGS.App.isAutomaticallyLogged==1&&$.CONFIGS.App.isSupportAutoLogin==1){
            /*自动登录*/
            var params = $.CONFIGS.Login.autoLogin;
            var url = params.url;
            var data = params.requestdata;
            nextActions["login"](url,data,"autoLogin_JSONPMethod",function(){});
        }
    });
	
	
    //当点击登录按钮
    $(".login_form").on("click",".login_btn",function(e){
        //手机一键登录的发送手机号后，会出现等待界面，这时下方的“等待确认”按钮应该不能触发事件
        if(e.target.className == "btn_user_login_mobile_wait")
            return 
        switch(e.target.className){
            case "btn_user_login_mobile" :
                ClickLog.log("ClickPMLoginBtn");
                break;
            case "btn_user_login_mobile_retry" :
                ClickLog.log("PMLoginRetry");
                break;
        }
		
        var self = $(this);
        if(self.hasClass("disabled")){
            return 
        }
        self.addClass("disabled");
        log("self.className = "+ this.className)
        setTimeout(function(){
            self.removeClass("disabled")
        },2000)
        var form = $(".login_form")[0]
        var username = form.username.value;
        var password = form.password.value;
        var checkCode = form.checkCode.value;
        var codekey1  = form.codekey1.value;
        var codekey2  = form.codekey2.value;
        var codekey3  = form.codekey3.value;
        var codekey4  = form.codekey4.value;
        var method, url, data, params, callback
        switch(CURRENT_TAB){
            case "index"://账号密码(还可能存在动密登录)
                LOGIN_TAB = "index"
                var error = check(["username","password" ], [username,password]);
                if(typeof error == "string"){
                    return  nextActions["show_error"](error);
                }
                log("静密登录")
                params = $.CONFIGS.Login.staticLogin;
                data = params.requestdata     //取得公用参数
                data.inputUserId = username;  //用户名
                data.password = password;     //密码

                data.autoLoginFlag = form.autoLoginFlag && form.autoLoginFlag.checked ? 1 : 0;//自动登陆
                if(data.autoLoginFlay==1){
                    data.autoLoginKeepTime = $.CONFIGS.App.autoLoginSaveTime;
                }

                url = params.url;
                method = "staticLogin_JSONPMethod"
                //账号密码登录请求量（调用后台接口发起用户名和密码验证的请求次数）
                ClickLog.log("AccountPwdLoginReq");
                break;
            case "safe_code_card"://安全卡
                params = $.CONFIGS.Login.dynamicLogin;
                data = params.requestdata     //取得公用参数
                password = ""
                $("#safe_code_code_input li input").each(function(){
                    password += $(this).val() +'|'
                })
                data.password = password.slice(0,-1);  //密码
                url = params.url
                method = "dynamicLogin_JSONPMethod"
                break;
            case  "safe_code_a8"://A8密宝
				var error = check(["ptekey"],[$("#ptekey").val()]);
				if(typeof error == "string"){
                    return  nextActions["show_error"](error);
                }
                params = $.CONFIGS.Login.dynamicLogin;
                data = params.requestdata     //取得公用参数
                data.password = $("#ptekey").val();  //密码
                url = params.url
                method = "dynamicLogin_JSONPMethod"
                break;
            case "safe_code_a6"://D6密宝
                var error = check(["codekey1","codekey2","codekey3","codekey4"],[codekey1,codekey2,codekey3,codekey4]);
                if(typeof error == "string"){
                    return  nextActions["show_error"](error);
                }
                params = $.CONFIGS.Login.dynamicLogin;
                data = params.requestdata     //取得公用参数
                password = "";
                $("#safe_code_a6_input li input").each(function(){
                    password += $(this).val()
                });
                data.password = password;  //密码
                url = params.url
                method = "dynamicLogin_JSONPMethod"
                break;
            case "checkcode"://图形验证码
                params = $.CONFIGS.Login.checkCodeLogin;
                data = params.requestdata;
                password = $("#img_password").val();
                error = check(["checkCode" ], [password]);
                if(typeof error == "string"){
                    return  nextActions["show_error"](error);
                }
                data.password = password;  
                url = params.url;
                method = "checkCodeLogin_JSONPMethod";
                break;
            case "mobile_wait":
            case "mobile"://手机一键登录
                LOGIN_TAB = "mobile"
                error = check(["username_no_check_exist"], [username]);
                if(typeof error == "string"){
                    jump("mobile")
                    return  nextActions["show_error"](error);
                }
                checkAccountHash.getJSON(username,function(json){
                    if(json.data.existing==0){
                        jump("mobile");
                        nextActions["show_error"]("对不起，账号不存在，请确认后重新输入");
                    }else{
                        params = $.CONFIGS.Login.sendPushMessage;
                        data = params.requestdata     //取得公用参数
                        data.inputUserId = username;  //手机号码
                        url = params.url
                        method = "sendPushMessage_JSONPMethod" 
                        jump('mobile_wait','step1')
                        //由于要对错误码进行区别对待，不进最下面的请求
                        ClickLog.log("PMLoginReq");
                        RequestHPS(method,data, url, function(json){ 
                            if(json.return_code==0){
                                ClickLog.log("ReqPMLoginSuc");
                            }else{
                                ClickLog.log("ReqPMLoginErr");
                            }
                            switch(json.return_code){
                                case 0:
                                    //成功了，就开始轮询
                                    //将确认码显示出来
                                    $("#confirm_code .confirm_code_outer span").text(json.data.pushMsgSerialNum);
                                    params = $.CONFIGS.Login.pushMessageLogin;
                                    data = params.requestdata     //取得公用参数
                                    data.pushMsgSessionKey = json.data.pushMsgSessionKey
                                    url = params.url
                                    method = "pushMessageLogin_JSONPMethod"
                                    //  jump('mobile_wait','step3');
                                    jump('mobile_wait','suc')
                                    //开始轮询
                                    var action = nextActions["check_mobile_login"];
                                    action.total_time = 120*1000;    //轮询的总时间，超过还没有中断强制中断
                                    action.start_time = new Date - 0;//轮询的起始时间
                                    action.start = true;
                                    action.times  =  40; //还能轮询多少次
                                    action.delay = 2000;//失败之后再等多少ms再发出一次循环
                                   
                                    action(url, data, method )
                                    return false;
                                case -10742110:
                                    //该账号尚未绑定手机
                                    jump('mobile_wait','bindMobile');
                                    ClickLog.log("AccountNoBindMobile");
									if(isPhone($(".username").val())){
										$(".step.step_1 a").attr("href","http://txz.sdo.com/txz/pushlogindown?fromid=48&m="+$(".username").val());
									}
                                    break;
                                case -16027608: //客户端安全版本太低
                                case -10742135:
                                    jump('mobile_wait','notInstalled')
                                    var html, logText
                                    if(json.return_code== -16027608){
                                        html = "您需要安装盛大通行证手机版最新版本"
                                        logText = "LowAppVersion"
                                    }else{
                                        html = "你需要安装盛大通行证手机版"
                                        logText = "NotInstalledApp"
                                    }
                                    ClickLog.log(logText);
                                    $(".tipList_notInstalled").html( html )
									if(isPhone($(".username").val())){
										$(".status_notInstalled a").attr("href","http://txz.sdo.com/txz/pushlogindown?fromid=48&m="+$(".username").val());
									}
                                    break;
								case -10742134: //登录开关关闭
                                case -10742133: //所有应用被屏蔽
                                    ClickLog.log("ForbidLogin");
                                    jump('mobile_wait','error')
                                    break;
                                case -16027609:
                                    //您屏蔽了一键登录，可按引导打开
                                    ClickLog.log("ForbidPMLogin");
                                    nextActions["show_error"]('您屏蔽了一键登录，可按<a target="_blank" href="http://txz.sdo.com/txz/pushlogindown1?fromid=48">引导打开</a>')
                                    break; 
                                case -16027605: //手机不在线 
                                    nextActions["show_error"]("您的手机需联网，请打开wifi或移动网络")
                                    ClickLog.log("AppOffline");
                                    break;
                                default:
                                    nextActions["show_error"]("系统繁忙，请稍后再试")
                                    ClickLog.log("OtherPMRequestError");
                                    break; 

                            } 
                        });
                    }
                })
                return
            case "code2d"://二维码登录
                LOGIN_TAB = "code2d"
                break;
            case "sms"://短信登录(第四个面板)
                LOGIN_TAB = "sms"
                error = check(["username_no_check_exist"], [username]);
                if(typeof error == "string"){//先判定用户名
                    return  nextActions["show_error"](error);
                }
                if(!nextActions["msm_login"].smsSent){//再判定有没有点发送验证码按钮
                    return nextActions["show_error"]("请先发送短信验证码后再登录！");
                }
             
                error = check(["checkCode"], [checkCode]);
                if(typeof error == "string"){//最后判定验证码
                    return  nextActions["show_error"](error);
                }
                //  delete nextActions["msm_login"].smsSent
                params = $.CONFIGS.Login.phoneCheckCodeLogin;
                data = params.requestdata;     //取得公用参数
                data.checkCode = checkCode;
                url = params.url;
                method = "phoneCheckCodeLogin_JSONPMethod";
                log("短信登录")
                //短信登录请求量（调用后台接口发起用户名和短信验证码验证的请求次数）
                ClickLog.log("SmsLoginReq");

                callback = function(json){
                    if(window.hasPwdLoginRecord == 0){
                        nextActions["set_password"]( json );                     
                        return false;
                    }
                    window.hasPwdLoginRecord  = void 0;
                }
                break;
            case "setpass":
                params = $.CONFIGS.Login.modifyPassword;
                url = params.url;
                data = params.requestdata;
               
                var password =  $("#setpass_set_pass :password").val();
                var password2 =  $("#setpass_confirm_pass :password").val();
                data.newPassword = password//newPaw
                error = check(["newPassword"], [password]);
                if(typeof error == "string"){
                    return  nextActions["show_error"](error);
                }
                if(password != password2){
                    return  nextActions["show_error"]("两次密码输入不一致");
                }
                // setpass_confirm_pass
                method = "modifyPassword_JSONPMethod"
                log("设置密码")
                callback = function(json){
                    var url = window.set_password_redirect
                    setCookieForLoginWay("CURRENT_TAB",("sms")+"|"+escape($.CONFIGS.Login.sendPhoneCheckCode.requestdata.inputUserId));
                    if(url){
                        window.set_password_redirect = void 0;
                        log("设置密码完成，准备跳转")
                        setTimeout(function(){
                            window[ $.CONFIGS.App.target =="iframe" ? "self" : "top" ]. location = url
                        },400)
                    }
                }
                break;
                    
        }
        //-10742001 必填参数为空 
        nextActions["login"](url, data, method, callback )

    })
	

    
   
    
})

