package com.example.demo4.pojo;

import com.example.demo4.exception.exceptions.UndefinedEnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;


@Getter
@AllArgsConstructor
public enum Status implements BaseEnum{

    EXIT("已退出",0),ON_JOB("在职",1);

    //添加属性，以对应上面的括号里的内容
    private final String status;
    private final int state;

    //私有构造方法，填充类属性
//    private Gender(String sex,int state){
//        this.sex= sex;
//        this.state=state;
//    }

    private static HashMap<Integer,Status> statusMap = new HashMap<>();
    private static HashMap<String,Status> stringStatusMap = new HashMap<>();
    //利用静态代码块，在new的时候，将这些字段加载进来
    static {
        for (Status status : Status.values()) {
            statusMap.put(status.getState(),status);
            stringStatusMap.put(status.getStatus(), status);
        }
    }

    //通过这个方法，可以取到状态（0,1,2）对应的enum,然后将这个enum返回
    public static Status getValue(Integer value){
        Status status = statusMap.get(value);
        if(status == null) {
            throw new UndefinedEnumValue("No element matches in enum Status " + value);
        }
        return status;
    }

    public static Status getValue(String value){
        Status status = stringStatusMap.get(value);
        if(status == null) {
            throw new UndefinedEnumValue("No element matches in enum Status " + value);
        }
        return status;
    }




}
