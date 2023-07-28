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

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author silence
 * @since 2020/1/7
 * 
 */
public class Excelutils {

    private static final Logger logger = LoggerFactory.getLogger(Excelutils.class);

    public static String readExcel(Workbook workbook, int sheetIndex, int rowIndex, int cellIndex) {

        Sheet sheet = workbook.getSheetAt(sheetIndex);
        Row row = sheet.getRow(rowIndex);
        Cell cell = row.getCell(cellIndex);
        if(cell == null){
            return null;
        }
        return cell.getStringCellValue();

    }

    public static Workbook getWorkbook(String fileName) {
        try {
            return new XSSFWorkbook(fileName);
        } catch (IOException e) {
            logger.error("读取excel文件{}失败", fileName, e);
            return null;
        }
    }

    public static Workbook createExcel(String sheetName, List<List<String>> list) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        for (int i = 0; i < list.size(); i++) {
            for (int i1 = 0; i1 < list.get(i)
                .size(); i1++) {
                Row row = sheet.createRow(i);
                Cell cell = row.createCell(i1);
                cell.setCellValue(list.get(i).get(i1));
            }
        }
        return workbook;
    }

}