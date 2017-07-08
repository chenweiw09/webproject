package domain;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Cruise on 2017/7/2.
 */
public class ListTest {
    public static void main(String[] args) {
        ListTest ls = new ListTest();
        List<String> list = new LinkedList<String>();
        ls.addCl(list, "a");
        ls.addCl(list, "b");

        System.out.println(list.size());

        StringBuffer sb = new StringBuffer();
        ls.addSB(sb,"ss");
        System.out.println(sb.toString()+"--------");
    }

    public void addCl(List<String> list, String str){
        list.add(str);
    }

    public void addSB(StringBuffer sb, String str){
        sb.append(str);
    }
}
