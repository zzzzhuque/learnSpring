package com.zt.spring5.bean;

// 员工类
public class Employee {
    private String employeeName;
    private String gender;
    // 员工属于某一个部门
    private Department department;

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 为了<property name="department.departmentName" value="Tech Department"></property>
     * 这种写法必须要有
     */
    public Department getDepartment() {
        return department;
    }

    public void printEmployeeInfo() {
        System.out.println(employeeName + ":" + gender + ":" + department);
    }
}
