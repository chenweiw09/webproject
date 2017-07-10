package smart;

import smart.ioc.annotation.Bean;

/**
 * Created by Cruise on 2017/7/9.
 */
@Bean
public class BeanTest {
    private String name;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
