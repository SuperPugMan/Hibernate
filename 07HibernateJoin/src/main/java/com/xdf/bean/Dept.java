package com.xdf.bean;

import java.util.HashSet;
import java.util.Set;

/**
 *  部门类
 */
public class Dept {
    private  Integer deptId; //部门编号
    private  String name; //部门姓名
    private  String location; //部门地址
    //一个部门对应多个员工
    private Set<Emp> emps=new HashSet<Emp>();

    @Override
    public String toString() {
        return "Dept{" +
                "deptId=" + deptId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", emps=" + emps.size() +
                '}';
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Emp> getEmps() {
        return emps;
    }

    public void setEmps(Set<Emp> emps) {
        this.emps = emps;
    }

    public Dept(Integer deptId, String name, String location, Set<Emp> emps) {

        this.deptId = deptId;
        this.name = name;
        this.location = location;
        this.emps = emps;
    }

    public Dept() {

    }
}
