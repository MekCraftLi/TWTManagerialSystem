package com.example.demo4.pojo;

import com.example.demo4.exception.exceptions.UndefinedEnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;


@Getter
@AllArgsConstructor
public enum Role implements BaseEnum{

    MANAGER("工作室负责人",0),
    TEAM_LEADER("组长",1),
    VICE_TEAM_LEADER("副组长",2),
    MEMBER("普通成员",3);

    //添加属性，以对应上面的括号里的内容
    private final String role;
    private final int state;

    //私有构造方法，填充类属性
//    private Role(String role,int state){
//        this.role = role;
//        this.state = state;
//    }

    private static HashMap<Integer,Role> roleMap = new HashMap<>();
    private static HashMap<String,Role> stringRoleMap = new HashMap<>();
    //利用静态代码块，在new的时候，将这些字段加载进来
    static {
        for (Role role : Role.values()) {
            roleMap.put(role.getState(),role);
            stringRoleMap.put(role.getRole(), role);
        }
    }

    //通过这个方法，可以取到状态（0,1,2）对应的enum,然后将这个enum返回
    public static Role getValue(Integer value){
        Role role = roleMap.get(value);
        if(role == null) {
            throw new UndefinedEnumValue("No element matches int enum Role " + value);
        }
        return role;
    }

    public static Role getValue(String value){
        Role role = stringRoleMap.get(value);
        if(role == null) {
            throw new UndefinedEnumValue("No element matches int enum Role " + value);
        }
        return role;
    }


}
