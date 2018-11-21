package com.dongweima.utils.excel;

import com.dongweima.utils.excel.bean.ExcelIterable;
import com.dongweima.utils.file.PathUtil;

import java.util.List;

public class ExcelTest {
    public static void main(String[] args)throws Exception {
        ExcelIterable<Student> students = new ExcelIterable<Student>(){
            int i = 0;
            int pageSize=100;

            @Override
            public int getPageSize() {
                return pageSize;
            }

            public  boolean hasNext(){
                i++;
                if(i<1000000/pageSize){
                    return  true;
                }
                return false;
            }
            public  void next(List<Student> subList){
                for(int j=0;j<pageSize;j++){
                    Student student = new Student();
                    student.setSex("男");
                    student.setBirth("2018-10-10");
                    student.setClazz("701");
                    student.setGrade("7");
                    student.setPhone1("18106526227");
                    student.setPhone2("18106526227");
                    student.setName("马东伟");
                    student.setNumber("11111");
                    subList.add(student);
                }
            }
        };
        ExcelUtil.exportBigDataExcel(Student.class, PathUtil.getBaseDir()+"test.xlsx",students);
        System.out.printf("ok");
    }
}
