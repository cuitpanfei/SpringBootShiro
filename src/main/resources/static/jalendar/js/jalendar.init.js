function initmenu(){
    updateTransaction(_contextPath);
}
/**
 * 
 * @param _str
 *            网站的根
 */
function updateTransaction(_str) {
    $.ajax({
        type: "get",
        url: _str,
        success: function (data,status,code) {
        	if(status=='success'){
	        	var _Enevts = mosaicEvents(data);
	        	$("#jalendar").html("");
	        	$("#jalendar").append(_Enevts);
        	}
        },
        error:function(data,status){
        	console.log(status);
        },
        complete:function () {
            jalendar_event();
        }
    });
}

function mosaicEvents(obj) {
    var html = "";
    try{
    	obj.forEach(function(da){
        	html = html + '<div class="added-event" data-date="'+getNowFormatDate(da.creatdate)+'" data-title="'+da.schedulecontent+'"></div>\n';	
        });
    }catch(e){
    	console.log(e.message);
    }
    
    return html;
}

function getNowFormatDate(date) {
    var currentdate = new Date(date).format('dd-MM-yyyy');
    return currentdate;
}

Date.prototype.format =function(format){
    var o = {
        "M+" : this.getMonth()+1, // month
        "d+" : this.getDate(), // day
        "h+" : this.getHours(), // hour
        "m+" : this.getMinutes(), // minute
        "s+" : this.getSeconds(), // second
        "q+" : Math.floor((this.getMonth()+3)/3), // quarter
        "S" : this.getMilliseconds() // millisecond
    }
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
        (this.getFullYear()+"").substr(4- RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
        format = format.replace(RegExp.$1,
            // RegExp.$1.length==1? o[k] :
            // ("00"+ o[k]).substr((""+ o[k]).length));
            o[k]);
    return format;
}