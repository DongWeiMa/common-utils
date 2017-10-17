package com.dongweima.utils.excel;

import com.dongweima.utils.excel.annotion.ExcelCell;
import com.dongweima.utils.excel.annotion.ExcelSheet;
import com.dongweima.utils.excel.bean.ExportHeader;
import com.dongweima.utils.excel.bean.Header;
import com.dongweima.utils.reflect.ReflectUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * excel 2003 和excel2007处理工具类.
 *
 * @author dongweima
 */
public class ExcelUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);
  private static final String XLSX = "xlsx";
  private static final String XLS = "xls";

  /**
   * 这个解析list到一个excel的方法.
   */
  @SuppressWarnings({"unchecked", "unused"})
  public static <T> void listExport(List<T> list, String filePath) throws Exception {
    if (list == null || list.size() == 0) {
      return;
    }
    File file = new File(filePath);
    if (!file.exists()) {
      file.createNewFile();
    }
    ExportHeader<T> header = getExportHeader(list.get(0).getClass());
    if (header == null) {
      return;
    }
    OutputStream out = null;
    try {
      Workbook workbook = getExportWorkbook(filePath);
      Sheet sheet = workbook.createSheet();
      //编辑第一行
      Row row = sheet.createRow(0);
      int i = 0;
      Map<String, String> map = header.getMap();
      for (String name : map.keySet()) {
        Cell cell = row.createCell(i);
        cell.setCellValue(name);
        i++;
      }
      //编辑后续行
      int rowNum = 0;
      for (T t : list) {
        rowNum++;
        row = sheet.createRow(rowNum);
        i = 0;
        for (String name : map.keySet()) {
          Cell cell = row.createCell(i);
          Object fieldValue = ReflectUtil.getFieldValue(map.get(name), t);
          setValue(cell, fieldValue);
          i++;
        }
      }
      out = new FileOutputStream(filePath);
      workbook.write(out);
    } catch (IOException e) {
      throw new Exception(e);
    } finally {
      if (out != null) {
        out.close();
      }
    }

  }

  /**
   * 因为可能一份excel有多份sheet 每份sheet都有自己的解析类 所以传入的是一个List.
   */
  @SuppressWarnings({"unchecked", "unused"})
  public static Map<String, Set<List>> readExcel(String filePath,
      List<Class> classes) throws Exception {
    Map<String, Set<List>> map = new HashMap<>(1);
    Map<String, Header> headerMap = new HashMap<>(1);
    for (Class clazz : classes) {
      Header header = getHeader(clazz);
      if (header != null) {
        Set<String> sheetNames = header.getSheetNames();
        if (sheetNames != null) {
          for (String sheetName : sheetNames) {
            headerMap.put(sheetName, header);
          }
        }
      }
    }
    Workbook workbook = getWorkbook(filePath);
    if (workbook == null) {
      return null;
    }
    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
      Sheet sheet = workbook.getSheetAt(i);
      if (sheet == null) {
        continue;
      }
      String sheetName = sheet.getSheetName();
      Header header = getHeader(headerMap, sheetName);
      if (header != null) {
        List list = readSheetToList(sheet, header);
        Set<List> set = map.get(header.getClazz().getName());
        if (set == null) {
          set = new HashSet<>();
          map.put(header.getClazz().getName(), set);
        }
        set.add(list);
      } else {
        LOGGER.info("filepath:{} sheetName:{} 该excel文件没有发现对应的bean", filePath, sheetName);
      }
    }
    return map;
  }

  private static <T> List<T> readSheetToList(Sheet sheet, Header<T> header) {

    //第一部分 头部
    int lastRowNum = sheet.getLastRowNum();
    int startLine = 0;
    int i = 0;
    Map<Integer, String> map = new HashMap<>(1);
    boolean ok = true;
    while (ok && startLine < lastRowNum) {
      Row firstRow = sheet.getRow(startLine);
      Iterator<Cell> ite = firstRow.cellIterator();
      while (ite.hasNext()) {
        Cell cell = ite.next();
        String name = getValue(cell);
        if (name != null) {
          for (String key : header.getMap().keySet()) {
            if (name.contains(key)) {
              map.put(i, header.getMap().get(key));
              ok = false;
              break;
            }
          }
        }
        i++;
      }
      if (ok) {
        i = 0;
        startLine++;
      }
    }
    //第二部分 数据部分
    List<T> list = new LinkedList<>();
    for (int rowNum = 1 + startLine; rowNum <= sheet.getLastRowNum(); rowNum++) {
      Row row = sheet.getRow(rowNum);
      if (row == null) {
        continue;
      }
      try {
        T obj = header.getClazz().newInstance();
        boolean notNullRow = false;
        for (Integer key : map.keySet()) {
          Object value = getValue(row.getCell(key));
          if (value != null) {
            notNullRow = true;
          }
          ReflectUtil.setFieldValueByName(map.get(key), obj, value);
        }
        if (notNullRow) {
          list.add(obj);
        }
      } catch (Throwable e) {
        LOGGER.warn(e.getMessage(), e);
      }
    }
    return list;
  }

  private static Header getHeader(Map<String, Header> map, String sheetName) {
    for (String key : map.keySet()) {
      if (sheetName.contains(key)) {
        return map.get(key);
      }
    }
    return null;
  }

  private static <T> Header<T> getHeader(Class<T> clazz) {
    Annotation sheet = clazz.getAnnotation(ExcelSheet.class);
    if (sheet == null) {
      return null;
    }
    String[] sheetArr = ((ExcelSheet) sheet).name();
    if (sheetArr.length == 0) {
      return null;
    }
    Set<String> sheetNames = new HashSet<>(Arrays.asList(sheetArr));
    Field[] fields = clazz.getDeclaredFields();
    Map<String, String> map = new HashMap<>(1);
    for (Field field : fields) {
      ExcelCell cell = field.getAnnotation(ExcelCell.class);
      if (cell != null) {
        String[] cellNames = cell.name();
        for (String cellName : cellNames) {
          map.put(cellName, field.getName());
        }
      }
    }
    Header<T> header = new Header<>();
    header.setClazz(clazz);
    header.setSheetNames(sheetNames);
    header.setMap(map);
    return header;
  }

  private static <T> ExportHeader getExportHeader(Class<T> clazz) {
    Annotation sheet = clazz.getAnnotation(ExcelSheet.class);
    if (sheet == null) {
      return null;
    }
    String[] sheetArr = ((ExcelSheet) sheet).name();
    if (sheetArr.length == 0) {
      return null;
    }
    Field[] fields = clazz.getDeclaredFields();
    Map<String, String> map = new HashMap<>(1);
    for (Field field : fields) {
      ExcelCell cell = field.getAnnotation(ExcelCell.class);
      if (cell != null) {
        String[] cellNames = cell.name();
        map.put(cellNames[0], field.getName());
      }
    }
    ExportHeader<T> header = new ExportHeader<>();
    header.setSheetName(sheetArr[0]);
    header.setMap(map);
    header.setClazz(clazz);
    return header;
  }

  private static String getValue(Cell cell) {
    if (cell == null) {
      return null;
    }

    int type = cell.getCellType();
    switch (type) {
      case Cell.CELL_TYPE_NUMERIC:
        return String.valueOf(((Double) (cell.getNumericCellValue())).longValue());
      case Cell.CELL_TYPE_STRING:
        return cell.getStringCellValue();
      case Cell.CELL_TYPE_FORMULA:
        return cell.getCellFormula();
      case Cell.CELL_TYPE_BOOLEAN:
        return String.valueOf(cell.getBooleanCellValue());
      default:
        return null;
    }
  }

  private static void setValue(Cell cell, Object value) {
    if (value == null) {
      return;
    }
    if (value instanceof Double) {
      cell.setCellType(Cell.CELL_TYPE_NUMERIC);
      cell.setCellValue((Double) value);
      return;
    }
    if (value instanceof Boolean) {
      cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
      cell.setCellValue((Boolean) value);
      return;
    }
    if (value instanceof String) {
      cell.setCellType(Cell.CELL_TYPE_STRING);
      cell.setCellValue((String) value);
      return;
    }
    cell.setCellValue(value.toString());
  }

  private static Workbook getWorkbook(String filePath) throws Exception {
    Workbook book;
    File file;
    FileInputStream fis = null;
    try {
      file = new File(filePath);
      if (!file.exists()) {
        file.createNewFile();
      }
      fis = new FileInputStream(file);
      book = getWorkbook(filePath, fis);
    } catch (Throwable e) {
      LOGGER.debug(e.getMessage());
      throw e;
    } finally {
      if (fis != null) {
        fis.close();
      }
    }
    return book;
  }

  private static Workbook getExportWorkbook(String filePath) throws IOException {
    if (filePath.endsWith(XLSX)) {
      return new XSSFWorkbook();
    } else if (filePath.endsWith(XLS)) {
      return new HSSFWorkbook();
    }
    return new XSSFWorkbook();
  }

  private static Workbook getWorkbook(String filePath, FileInputStream fis) throws IOException {
    if (filePath.endsWith(XLSX)) {
      return new XSSFWorkbook(fis);
    } else if (filePath.endsWith(XLS)) {
      return new HSSFWorkbook(fis);
    }
    return new XSSFWorkbook(fis);
  }

}