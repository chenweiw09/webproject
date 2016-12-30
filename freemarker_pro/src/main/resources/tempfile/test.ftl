<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>${username}-----${age}</h1>
<#if age lt 12>
    ${username}还是一个小孩
<#elseif age lt 18>
    ${username}快成年
<#else>
    ${username}已经成年
</#if>
</body>
</html>