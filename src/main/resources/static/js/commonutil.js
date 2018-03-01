/*!
 *
 * 公共工具函数文件
 *
 */
 


 /**
  *
  * @param value 账号的状态
  * @param id    账号的id
  * 
  */
function changeState(value,id){
    $.ajax({
      type: "GET",
      dataType:'json', //接受数据格式 
      cache:false,
      url: "/account/updateAccountInfo?id="+id+"&state="+id,//请求的url
      beforeSend: function(XMLHttpRequest){

      },
      success: function(data){
          info('提示','你刚刚'+(value?'解锁了':'锁定了')+'id为：'+id+'的用户！')
       },
      error: function(){//请求出错处理
          info('错误','发生错误')
      }
    });
}
/**
 *
 * @param _title   提示信息的标题
 * @param _text    提示信息的内容
 */
function info(_title,_text){
    $.gritter.add({
        &times,
        title: _title,
        text: _text,
        time: 2000,
        speed:500,
        class_name: 'gritter-info gritter-center'
    });
}