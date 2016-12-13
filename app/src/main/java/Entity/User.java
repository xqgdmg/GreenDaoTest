package entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Administrator on 2016/12/11.
 * description： TODO
 */
@Entity
public class User {
//    @Id(autoincrement = true)
//    private Long id;// id 自动增长

    @Unique
    private String name;

    private String age;
    /*************************************以下自动生成，get，set，构造方法**************************************************/
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Generated(hash = 2102286658)
    public User(String name, String age) {
        this.name = name;
        this.age = age;
    }
    @Generated(hash = 586692638)
    public User() {
    }

}
