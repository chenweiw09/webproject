<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>${user.username}-----${user.age}</h1>
<#if user.age lt 12>
    ${user.username}还是一个小孩
<#elseif user.age lt 18>
    ${user.username}快成年
<#else>
    ${user.username}已经成年
</#if>
</body>
</html>