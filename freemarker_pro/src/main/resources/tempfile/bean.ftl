package ${packageName};
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.math.BigDecimal;
/**
*  Created by ${author} on ${date}.
*  Version 1.0
*/
public class ${className} implements Serializable{
    private static final long serialVersionUID = 3908849094512306748L;
    <#list attrs as a>
    /** ${a.marker!} */
    private ${a.javaType} ${a.field};
    </#list>

    <#list attrs as a>
    <#if a.primary>
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)</#if>
    public ${a.javaType} get${a.field?cap_first}(){
        return this.${a.field};
    }

    public void set${a.field?cap_first}(${a.javaType} ${a.field}){
        this.${a.field} = ${a.field};
    }

    </#list>
}