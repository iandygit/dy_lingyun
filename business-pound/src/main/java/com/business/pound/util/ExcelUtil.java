package com.business.pound.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {
    HSSFWorkbook wb = new HSSFWorkbook();
    String excelName="details";
    public ExcelUtil(String sheeetNname, String colums[], List<String[]> dataList){
        excelName=sheeetNname;
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet=wb.createSheet(sheeetNname);
        excelName=sheeetNname;
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个


        HSSFRow row1=sheet.createRow(0);
        for (int i=0 ;i<colums.length;i++){

            HSSFCell cell=row1.createCell(i);

            //设置单元格内容
            cell.setCellValue(colums[i]);
        }


        for (int j=0;j<dataList.size();j++){
            HSSFRow row2=sheet.createRow(j+1);//第二行 开始加载数据
            for(int k=0;k<colums.length;k++){
                HSSFCell cel2=row2.createCell(k);
                //设置单元格内容
                cel2.setCellValue(dataList.get(j)[k]);
            }
        }

    }

    public ServletOutputStream outStreamExcel(HttpServletResponse response){
        ServletOutputStream output= null;
        try {
            String fileNamee=new String(excelName.getBytes("iso-8859-1"),"utf-8");
            output = response.getOutputStream();
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment; filename="+fileNamee+".xls");
            response.setContentType("application/msexcel");
        } catch (IOException e) {
            e.printStackTrace();
        }
          return   output;

    }

    public void writeExcel(ServletOutputStream output) throws Exception {
        if (null==output){
            throw new Exception("读取io流失败");
        }
        try {
            wb.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
