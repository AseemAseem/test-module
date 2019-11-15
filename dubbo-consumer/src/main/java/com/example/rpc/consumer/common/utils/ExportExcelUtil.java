package com.example.rpc.consumer.common.utils;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

public class ExportExcelUtil {

    /**
     * @param os        输出流
     * @param sheetName 表格的名称
     * @param headName  表格的标题
     * @param rowName   表格的列名
     * @param dataType  数据的类型
     * @param dataList  表格的数据
     * @throws Exception
     */
    public static void createWorkbook(OutputStream os, String sheetName, String headName, String[] rowName, String[] dataType,
                                      List<Object[]> dataList) throws Exception {
        dataType = dataType == null ? rowName : dataType;
        XSSFWorkbook wb = createWorkbook(sheetName, headName, rowName, dataType, dataList);
        wb.write(os);
    }

    /**
     * @param sheetName 表格的名称
     * @param headName  表格的标题
     * @param rowName   表格的列名
     * @param dataType  数据的类型
     * @param dataList  表格的数据
     * @return
     */
    private static XSSFWorkbook createWorkbook(String sheetName, String headName, String[] rowName, String[] dataType,
                                               List<Object[]> dataList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName); // 创建工作表
        sheet.setDefaultRowHeight((short) (20 * 20));
        XSSFDrawing patriarch = sheet.createDrawingPatriarch(); // 创建图形工具

        // 样式定义
        XSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);// 获取列头样式对象
        XSSFCellStyle style = getStyle(workbook); // 单元格样式对象

        // 所需列数
        int columnNum = rowName.length;

        // 设置标题值
        XSSFRow rowTitle = sheet.createRow(0);
        sheet.getRow(0).setHeight((short) (22 * 20));
        for (int n = 0; n < columnNum; n++) {
            XSSFCell cellRowName = rowTitle.createCell(n); // 创建列头对应个数的单元格
            if (n == 0) {
                cellRowName.setCellType(XSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
                XSSFRichTextString text = new XSSFRichTextString(headName);
                cellRowName.setCellValue(text); // 设置列头单元格的值
            }
            cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (columnNum - 1)));// 合并单元格

        // 设置标题值
        XSSFRow rowTitle1 = sheet.createRow(1);
        sheet.getRow(1).setHeight((short) (22 * 20));
        for (int n = 0; n < columnNum; n++) {
            XSSFCell cellRowName = rowTitle1.createCell(n); // 创建列头对应个数的单元格
            cellRowName.setCellType(XSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
            XSSFRichTextString text = new XSSFRichTextString(rowName[n]);
            cellRowName.setCellValue(text); // 设置列头单元格的值
            cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
        }

        // 设置单元格的数据
        for (int i = 0; i < dataList.size(); i++) {
            Object[] obj = dataList.get(i);// 遍历每个对象
            XSSFRow row = sheet.createRow(i + 2);// 创建所需的行数
            sheet.getRow(i + 2).setHeight((short) (20 * 20));
            for (int j = 0; j < obj.length; j++) {
                XSSFCell cell;
                String type = dataType[j];
                String value = obj[j] == null ? "" : obj[j] + "";
                // 设置单元格的数据类型
                if ("double".equalsIgnoreCase(type)) {// 数字
                    cell = row.createCell(j, XSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(Double.parseDouble(value)); // 设置单元格的值
                } else if ("long".equalsIgnoreCase(type)) {
                    cell = row.createCell(j, XSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(Long.parseLong(value));
                } else if ("imgUrl".equalsIgnoreCase(type)) {// 网络图片
                    cell = row.createCell(j, XSSFCell.CELL_TYPE_STRING);
                    createPicture(workbook, cell, patriarch, obj[j].toString());
                } else {
                    cell = row.createCell(j, XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(value);
                }
                cell.setCellStyle(style); // 设置单元格样式
            }
        }

        // 设置列宽自动适应
        for (int colNum = 0; colNum < columnNum; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                // 当前行未被使用过
                XSSFRow currentRow = sheet.getRow(rowNum) == null ? sheet.createRow(rowNum) : sheet.getRow(rowNum);
                XSSFCell currentCell = currentRow.getCell(colNum);
                if (currentCell != null && currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                    String cellValue = currentCell.getStringCellValue();
                    int length = 0;
                    if (cellValue != null) {
                        length = cellValue.getBytes().length;
                    }
                    columnWidth = columnWidth < length ? length : columnWidth;
                }
            }
            int curWidth = (columnWidth + 8) * 256;
            curWidth = curWidth > 15000 ? 15000 : curWidth;
            sheet.setColumnWidth(colNum, curWidth);
        }

        return workbook;
    }

    /**
     * 列头单元格样式
     *
     * @param workbook
     * @return
     */
    private static XSSFCellStyle getColumnTopStyle(XSSFWorkbook workbook) {
        // 设置样式;
        XSSFCellStyle style = workbook.createCellStyle();

        // 设置字体
        XSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 14);
        // 字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        // 设置字体名字
        font.setFontName("宋体");
        // 在样式用应用设置的字体;
        style.setFont(font);

        // 边框颜色
        XSSFColor borderColor = new XSSFColor(new byte[]{0, 0, 0});

        // 设置底边框;
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(borderColor);

        // 设置左边框;
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(borderColor);

        // 设置右边框;
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(borderColor);

        // 设置顶边框;
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(borderColor);

        // 设置自动换行;
        style.setWrapText(false);

        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        return style;
    }

    /**
     * 列数据信息单元格样式
     *
     * @param workbook
     * @return
     */
    private static XSSFCellStyle getStyle(XSSFWorkbook workbook) {
        // 设置样式;
        XSSFCellStyle style = workbook.createCellStyle();

        // 设置字体
        XSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 13);
        // 字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        // 设置字体名字
        font.setFontName("宋体");
        // 在样式用应用设置的字体;
        style.setFont(font);

        // 边框颜色
        XSSFColor borderColor = new XSSFColor(new byte[]{0, 0, 0});

        // 设置底边框;
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(borderColor);

        // 设置左边框;
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(borderColor);

        // 设置右边框;
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(borderColor);

        // 设置顶边框;
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(borderColor);

        // 设置自动换行;
        style.setWrapText(false);

        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        return style;
    }

    /**
     * 创建图片
     *
     * @param workbook
     * @param cell
     * @param patriarch
     * @param webUrl
     */
    public static void createPicture(XSSFWorkbook workbook, XSSFCell cell, XSSFDrawing patriarch, String webUrl) {
        try {
            byte[] data = getResourcesData(webUrl);
            if (data != null) {
                //anchor主要用于设置图片的属性
                XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(), cell.getRowIndex(), (short) cell.getColumnIndex() + 1, cell.getRowIndex() + 1);
                //图片在单元格的位置
                anchor.setAnchorType(0);
                XSSFPicture p = patriarch.createPicture(anchor, workbook.addPicture(data, XSSFWorkbook.PICTURE_TYPE_JPEG));
                p.setLineWidth(100);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 获取网络资源
     *
     * @param webUrl
     * @return
     */
    private static byte[] getResourcesData(String webUrl) {
        try {
            URL url = new URL(webUrl);
            BufferedImage bufferImg = ImageIO.read(url);
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            ImageIO.write(bufferImg, "jpg", byteArrayOut);
            return byteArrayOut.toByteArray();
        } catch (Exception e) {
        }
        return null;
    }
}