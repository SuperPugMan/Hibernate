package com.xdf.bean;

/**
 * 街道对应的实体类
 *
 */
public class Street {
     private  Integer   id;  //街道id
     private  String   name; //街道名称
    //多个街道对应一个区县
    private District district; // 就是hbm.xml文件中many-to-one的name属性值

    public Street() {
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Street(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Street(Integer id, String name, District district) {

        this.id = id;
        this.name = name;
        this.district = district;
    }

    @Override
    public String toString() {
        return "Street{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", district=" + district +
                '}';
    }
}
