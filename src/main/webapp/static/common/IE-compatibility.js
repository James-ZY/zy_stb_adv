/**
 * Created by Administrator on 2016/9/28 0028.
 */
$(function(){
    $("input,textarea").onblur(function(){
        alert(1);
        $(this).css({
            "outline-style": "solid",
        "border-color": "rgba(82,168,236,0.8)",
        "outline": 0,
        "outline": "thin dotted \9",
        "-webkit-box-shadow": "inset 0 1px 1px rgba(0,0,0,0.075),0 0 8px rgba(82,168,236,0.6)",
        "-moz-box-shadow": "inset 0 1px 1px rgba(0,0,0,0.075),0 0 8px rgba(82,168,236,0.6)",
        "box-shadow": "inset 0 1px 1px rgba(0,0,0,0.075),0 0 8px rgba(82,168,236,0.6)"
        });
    });
})