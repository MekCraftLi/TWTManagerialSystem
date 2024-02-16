package com.example.demo4.pojo;

import com.example.demo4.exception.exceptions.UndefinedEnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@Getter
@AllArgsConstructor
public enum Group implements BaseEnum{

    PRODUCT("产品组",0),
    OPERATION("运营组",1),
    MOBILE("移动开发组",2),
    SERVER("后端开发组",3),
    WEB("前端开发组",4),
    DESIGN("设计组",5),
    NEWS("新闻中心",6);

    //添加属性，以对应上面的括号里的内容
    private final String group;
    private final int state;

    //私有构造方法，填充类属性
//    Group(String group,int state){
//        this.group = group;
//        this.state = state;
//    }

    private static HashMap<Integer,Group> groupMap = new HashMap<>();
    //利用静态代码块，在new的时候，将这些字段加载进来
    static {
        for (Group group : Group.values()) {
            groupMap.put(group.getState(),group);
        }
    }

    //通过这个方法，可以取到状态（0,1,2）对应的enum,然后将这个enum返回
    public static Group getValue(Integer value){
        Group group = groupMap.get(value);
        if(group == null) {
            throw new UndefinedEnumValue("No element matches in enum Group " + value);
        }
        return group;
    }


}
