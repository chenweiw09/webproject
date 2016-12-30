package ${packageName};
import java.io.Serializable;

/**
*  Created by ${author}
*  Version 1.0
*/
public class ${className} implements Serializable{
private static final long serialVersionUID = 3908849094512306748L;
<#list attrs as a>
/** ${a.marker!} */
private ${a.javaType} ${a.field};
</#list>

<#list attrs as a>
public void set${a.field?cap_first}(${a.javaType} ${a.field}){
this.${a.field} = ${a.field};
}

public ${a.javaType} get${a.field?cap_first}(){
return this.${a.field};
}

</#list>
}