<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" ; charset="UTF-8"/>
    <title>购买产品测试</title>
</head>
<script src="https://code.jquery.com/jquery-3.2.0.js">
</script>
<script type="text/javascript">

    var params = {
        userId: 1,
        productId: 1,
        quamtity: 3
    };


    $.post("./purchase", params, function (result) {

        // alert(result.message());

    })
    ;


</script>


<body>
<h1>抢购产品测试</h1>
</body>
</html>

