

function yangshi(){
	$("#next_gridPager").attr("title","下一页");
	$("#last_gridPager").attr("title","最后一页");
	$("#prev_gridPager").attr("title","上一页");
	$("#first_gridPager").attr("title","第一页");
	$("#last_gridPager").next().find("select").attr("title","每页显示数");
	$("#load_gridTable").html("正在加载...");
	
}
			
function yangshi1(){
	$("#next_gridPager1").attr("title","下一页");
	$("#last_gridPager1").attr("title","最后一页");
	$("#prev_gridPager1").attr("title","上一页");
	$("#first_gridPager1").attr("title","第一页");
	$("#last_gridPager1").next().find("select").attr("title","每页显示数");
	$("#load_gridTable1").html("正在加载...");
}

/**
 * Map集合方式一
 */
var hashMap ={
		Set:function(key,value){this[key]=value},
		Get:function(key){return this[key]},
		Contains:function(key){return this.Get(key)==null?faise:true},
		Remove:function(key){delete this[key]}
}

/**
 * Map集合方式二
 */
var nameAndStatuskeys = new Array();
var nameAndStatusdata = new Array();//存放设备名字和状态的List集合

function addMap(key,value){
   if(nameAndStatusdata[key] == null){
       nameAndStatuskeys.push(value);
   }
   nameAndStatusdata[key] = value;
}

function getMap(key){
   return nameAndStatusdata[key];
}
function removeMap(key){
	return delete nameAndStatusdata[key]
}


/**
 * map集合 方式三
 */
function Map(){
	this.container = new Object();
	}


	Map.prototype.put = function(key, value){
	this.container[key] = value;
	}


	Map.prototype.get = function(key){
	return this.container[key];
	}


	Map.prototype.keySet = function() {
	var keyset = new Array();
	var count = 0;
	for (var key in this.container) {
	// 跳过object的extend函数
	if (key == 'extend') {
	continue;
	}
	keyset[count] = key;
	count++;
	}
	return keyset;
	}


	Map.prototype.size = function() {
	var count = 0;
	for (var key in this.container) {
	// 跳过object的extend函数
	if (key == 'extend'){
	continue;
	}
	count++;
	}
	return count;
	}


	Map.prototype.remove = function(key) {
	delete this.container[key];
	}


	Map.prototype.toString = function(){
	var str = "";
	for (var i = 0, keys = this.keySet(), len = keys.length; i < len; i++) {
	str = str + keys[i] + "=" + this.container[keys[i]] + ";\n";
	}
	return str;
	}


//浏览器右下角消息提醒
function msg(title,context){
	$.messager.lays(200, 100); 
	$.messager.anim('fade', 2000); 
	$.messager.show(title, context,3000); 
}

//自定义信息弹窗
function createInfoWindow(title, content) {
    var info = document.createElement("div");
    info.className = "info";

    //可以通过下面的方式修改自定义窗体的宽高
    //info.style.width = "400px";
    // 定义顶部标题
    var top = document.createElement("div");
    var titleD = document.createElement("div");
    var closeX = document.createElement("img");
    top.className = "info-top";
    titleD.innerHTML = title;
    closeX.src = "http://webapi.amap.com/images/close2.gif";
    closeX.onclick = closeInfoWindow;

    top.appendChild(titleD);
    top.appendChild(closeX);
    info.appendChild(top);

    // 定义中部内容
    var middle = document.createElement("div");
    middle.className = "info-middle";
    middle.style.backgroundColor = 'white';
    middle.innerHTML = content;
    info.appendChild(middle);

    // 定义底部内容
    var bottom = document.createElement("div");
    bottom.className = "info-bottom";
    bottom.style.position = 'relative';
    bottom.style.top = '0px';
  	bottom.style.right = '70px';
    bottom.style.margin = '0 auto';
    var sharp = document.createElement("img");
    sharp.src = "http://webapi.amap.com/images/sharp.png";
    bottom.appendChild(sharp);
    info.appendChild(bottom);
    return info;
}

//关闭自定义弹窗
function closeInfoWindow() {
	map1.clearInfoWindow();
}
	

