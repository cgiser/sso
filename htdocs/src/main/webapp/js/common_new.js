function Placehold (ctrlName,placeWord){
    this.defaultColor = "#999";
    this.highLightColor = '#000';
    this.cName = document.getElementById( ctrlName );
	this.defaultWord = placeWord||this.cName.getAttribute('placeholder')||"手机/邮箱/个性账号" ;

	function addEvent( el,type,fn )
    {
        if( el.addEventListener )
            el.addEventListener( type, fn, false );
        else 
            el.attachEvent( 'on' + type, fn );
    }    
	this.init = function(){
         
        //原生支持placeholder的高级浏览器不用js逻辑模拟
        var placeHolderNativeSupport = 'placeholder' in document.createElement('input'); 
        if( placeHolderNativeSupport ){
			this.cName.setAttribute('placeholder',this.defaultWord);
			return;
		}
        
		this.cName.style.color = this.defaultColor;
		this.cName.value = this.defaultWord; 
        addEvent( this.cName, 'focus', this.onFocus() );
        addEvent( this.cName, 'blur', this.onBlur() );  
	}

	this.onFocus = function(){ 
		var that = this;
		return function(){
			that.cName.style.color = that.highLightColor;
			if(that.cName.value == that.defaultWord){
			    //that.setCursorPos(that.cName,0);
				that.cName.value = "";
			}
		};
	}
	this.onBlur = function(){
		var that = this;
		return function(){
			//that.cName.style.color = "#999";
			if(that.cName.value.length==0){
				that.cName.value = that.defaultWord;
				that.cName.style.color = that.defaultColor;
			}else{//过滤掉placehoder文字
				that.cName.value = that.cName.value.replace(that.defaultWord,"");
			}
		}
	}
	this.onKeyUp = function(){
		var that = this;
		return function(){
			if(that.cName.value.length == 0){
				that.cName.value = that.defaultWord;
				that.setCursorPos(that.cName,0);
			}else if(that.cName.value != that.defaultWord){
				var iniPos = that.getCursorPos(that.cName);
				that.cName.value = that.cName.value.replace(that.defaultWord,"");
				that.setCursorPos(that.cName,iniPos);
			}else{
				that.setCursorPos(that.cName,0);
			}
		}
	}
	this.onMouseUP = function(){
		var that = this;
		return function(){
			//document.getElementById("tip").innerHTML = (new Date).getTime();
			if(that.cName.value.length == 0){
				that.cName.value = that.defaultWord;
				that.setCursorPos(that.cName,0);
			}else if(that.cName.value != that.defaultWord){
				var iniPos = that.getCursorPos(that.cName);
				that.cName.value = that.cName.value.replace(that.defaultWord,"");
				that.setCursorPos(that.cName,iniPos);
			}else{
				that.setCursorPos(that.cName,0);
			}
		}
	}
	this.setCursorPos = function(ctrl,pos){//设置光标位置函数
		if(ctrl.setSelectionRange){
			ctrl.focus();
			ctrl.setSelectionRange(pos,pos);
		}else if (ctrl.createTextRange) {
			var range = ctrl.createTextRange();
			range.collapse(true);
			range.moveEnd('character', pos);
			range.moveStart('character', pos);
			range.select();
		}		
	}
	this.getCursorPos = function(ctrl){//获取光标位置
		var CaretPos = 0;	// IE Support
		if (document.selection) {
			ctrl.focus ();
			var Sel = document.selection.createRange ();
			Sel.moveStart ('character', -ctrl.value.length);
			CaretPos = Sel.text.length;
		}else if (ctrl.selectionStart || ctrl.selectionStart == '0'){// Firefox support
			CaretPos = ctrl.selectionStart;
		}
		return (CaretPos);		
	}
}
