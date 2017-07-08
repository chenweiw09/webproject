package domain;

import java.util.*;

/**
 * Created by Cruise on 2017/4/27.
 */
public class StringListTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2,3,4,5");
        list.add("2,3,4,5");
//        list.add("2,3,4,5,6");
//        list.add("2,3");
//        list.add("2,3,4");


        Map<String, Object> map = getRefundList("2",list);

        System.out.println("haha");
    }




    // list按照时间倒叙排列
    public static Map<String, Object> getRefundList(String index, List<String> list){
        List<String> arrayList = new ArrayList<String>();
        Map<String, Object> map = new HashMap<String, Object>();
        int flag = 0;
        int maxLength = 0;
        for(String str:list){
            int cnt = str.length();
            if(cnt >= maxLength){
                maxLength = cnt;
            }

            if(cnt ==1){
                if(index.equals(str)){
                    if(arrayList.size() >0 && arrayList.contains(str) && maxLength<=cnt){
                        flag++;
                    }
                    arrayList.add(str);
                }
                continue;
            }

            if(str.length()>1){
                String[] sts = str.split(",");
                List<String> lst = Arrays.asList(sts);
                if(lst.contains(index)){
                    if(maxLength<=cnt){
                        flag = arrayList.size();
                    }
                    arrayList.add(str);
                    continue;
                }
            }
        }

        map.put("1",arrayList);
        map.put("2",flag);
        return map;
    }


}


