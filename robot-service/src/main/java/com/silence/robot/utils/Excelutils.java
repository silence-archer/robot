/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: Excelutils
 * Author:   silence
 * Date:     2020/1/7 15:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.silence.robot.utils;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author silence
 * @create 2020/1/7
 * @since 1.0.0
 */
public class Excelutils {

    public static void readExcel(InputStream inputStream){
        try{
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            OutputStream outputStream = new FileOutputStream("D:\\study\\vscode-workspace\\1.xlsx");
            xssfWorkbook.write(outputStream);
        }catch (IOException e){

        }

    }

}