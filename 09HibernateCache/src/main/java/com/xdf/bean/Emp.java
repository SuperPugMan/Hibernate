package com.xdf.bean;

/**
 *  员工类
 */
public class Emp {
    private  Integer empId; //员工编号
    private  String name; //员工姓名
    private  String job; //员工职位
    private  double sal; //员工薪水
  //多个员工属于一个部门
    private   Dept dept;

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Emp(Integer empId, String name, String job, double sal,Dept dept) {
        this.empId = empId;
        this.name = name;
        this.job = job;
        this.sal = sal;
        this.dept=dept;
    }

    public Emp() {

    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "empId=" + empId +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", sal=" + sal +
                ", dept=" + dept +
                '}';
    }
}
