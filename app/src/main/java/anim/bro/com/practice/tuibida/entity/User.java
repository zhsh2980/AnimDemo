package anim.bro.com.practice.tuibida.entity;

import java.io.Serializable;

/**
 * Created by zhangshan on 2019-05-16 18:46.
 */
public class User implements Serializable {

    public int age;
    public String name;

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
