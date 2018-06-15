/**
 * Created by Administrator on 2017/4/11 0011.
 */
		$(document).ready(function() {
		    /******弹框样式全局配置*******/
		    layer.config({
		     extend: 'myskin/style.css',
		     skin: 'layui-layer-rim',
		     shadeClose: true, //点击遮罩关闭
		     type: 1,
		     title:accipiter.getLang_(lang,"t3"),
		     area: ['350px', '200px'],
		        btn: [accipiter.getLang_(messageLang,"import.confirm"),accipiter.getLang_(messageLang,"import.close")],
		        content: '\<\div style="padding:25px 20px;text-align: center;"><i></i><span>'+accipiter.getLang_(messageLang,"file.notNull")+'</span>\<\/div>',
		     });
		})