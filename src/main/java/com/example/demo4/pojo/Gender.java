package com.example.demo4.pojo;

import com.example.demo4.exception.exceptions.UndefinedEnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;


@Getter
@AllArgsConstructor
public enum Gender implements BaseEnum{

    MALE("男",0),FEMALE("女",1);

    //添加属性，以对应上面的括号里的内容
    private final String sex;
    private final int state;

    private static HashMap<Integer,Gender> sexMap = new HashMap<>();
    private static HashMap<String,Gender> stringSexMap = new HashMap<>();
    //利用静态代码块，在new的时候，将这些字段加载进来
    static {
        for (Gender gender : Gender.values()) {
            sexMap.put(gender.getState(),gender);
            stringSexMap.put(gender.getSex(), gender);
        }
    }

    //通过这个方法，可以取到状态（0,1,2）对应的enum,然后将这个enum返回
    public static Gender getValue(Integer value){
        Gender sex = sexMap.get(value);
        if(sex == null) {
            throw new UndefinedEnumValue("No element matches in enum Gender " + value);
        }
        return sex;
    }

    public static Gender getValue(String value){
        Gender sex = stringSexMap.get(value);
        if(sex == null) {
            throw new UndefinedEnumValue("No element matches in enum Gender " + value);
        }
        return sex;
    }






}
