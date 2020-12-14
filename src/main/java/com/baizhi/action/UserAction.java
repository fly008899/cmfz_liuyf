package com.baizhi.action;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserAction {
    @Autowired
    private UserService userService;

    @RequestMapping("/showAll")
    @ResponseBody
    public Map<String,Object> showAll(Integer page, Integer rows){
        //当前页的数据
        List<User> users = userService.showAllUser(page, rows);
        //查询总条数
        Integer totalCount = userService.totalCount();
        Map<String,Object> maps=new HashMap<String,Object>();
        //当前页号
        maps.put("page",page);
        //总条数
        maps.put("records",totalCount);
        //总页数
        Integer  pageCount=0;
        if (totalCount%rows==0){
            pageCount=totalCount/rows;
        }else {
            pageCount=totalCount/rows+1;
        }
        maps.put("total",pageCount);
        //每页具体的数据
        maps.put("rows",users);
        return   maps;
    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    public HashMap<String, Object> updateStatus(String id,String status){
        User user = userService.selectOne(id);
        if(user.getStatus().equals("未激活")){
            user.setStatus("激活");
        }else{
            user.setStatus("未激活");
        }
        userService.update(user);
        HashMap<String, Object> map = new HashMap<>();
        map.put("success","200");
        map.put("message","修改成功!");
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public String add(User user, String oper){
        String uuid = null;
        //执行添加方法
        if(oper.equals("add")){
            uuid = userService.add(user);
            //发布消息  发布地址，appkey
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-fd441772316d4c9aaca97b36656cf51d");
            //参数: 管道(标识)名称,发布的内容
            String content="Hello, 158 GoEasy!";
            goEasy.publish("myChannel158", content);
        }
        //执行修改方法
        if(oper.equals("edit")){
            if(user.getPicture()==""){
                user.setPicture(null);
            }
            userService.update(user);
        }
        //执行删除方法
        if(oper.equals("del")){
            userService.del(user.getId());
        }
        return uuid;
    }

    @RequestMapping("/uploadPic")
    public void uploadPic(MultipartFile picture, String id, HttpServletRequest request){
        //获取要上传文件夹的路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
        //获取文件夹
        File file = new File(realPath);
        //创建文件夹
        if(!file.exists()){
            file.mkdirs();
        }
        //获取上传文件的名字
        String filename = picture.getOriginalFilename();
        String name = new Date().getTime()+"_"+filename;
        //文件上传
        try {
            picture.transferTo(new File(realPath,name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setId(id);
        user.setPicture(name);
        //做修改,修改默认的图片路径
        userService.update(user);
    }

    @RequestMapping("/output")
    @ResponseBody
    public HashMap<String, Object> updateStatus(){
        //查询数据库返回集合
        List<User> list = userService.showAll();
//---------------------------------------原始poi方法-------------------------------------------

       /* //创建一个Excel文档
        Workbook workbook = new HSSFWorkbook();
        //创建一个工作薄   参数：工作薄名字(sheet1,shet2....)
        Sheet sheet = workbook.createSheet("用户信息表");
        //创建一个标题行  参数：行下标(下标从0开始)
        Row row0 = sheet.createRow(0);
        //设置内容
        row0.createCell(0).setCellValue("用户信息");
        //设置列宽  参数：列索引，列宽(注意：单位为1/256)
        sheet.setColumnWidth(3,20*256);

        //设置字体样式
        Font font = workbook.createFont();
        font.setBold(true); //加粗
        font.setColor(Font.COLOR_RED);  //设置字体颜色
        font.setFontHeightInPoints((short) 24);  //设置字体大小
        font.setFontName("宋体");  //设置字体
        font.setItalic(true);  //设置斜体

        //创建样式对象
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setFont(font);
        //创建一行  参数：行下标(下标从0开始)
        Row row = sheet.createRow(1);
        //设置行高   参数：行高(注意：单位为1/20,short类型)
        row.setHeight((short) 900);
        //创建一个日期格式对象
        DataFormat dataFormat = workbook.createDataFormat();
        //创建一个样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        //将日期格式放入样式对象中
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy年MM月dd日"));

        //给目录行设置数据
        String[] title={"ID","手机","密码","salt","头像","昵称","用户名","性别","地址","个性签名","状态","创建时间","上师id"};
        for (int i = 0; i < title.length; i++) {
            //创建单元格
            Cell cell = row.createCell(i);
            //设置单元格内容
            cell.setCellValue(title[i]);
            //给字体设置样式
            cell.setCellStyle(cellStyle1);
        }

        //处理数据行数据
        for (int i = 0; i < list.size(); i++) {
            //创建行
            Row row1 = sheet.createRow(i + 2);
            //创建单元格
            Cell cell = row1.createCell(0);
            //设置单元格内容
            cell.setCellValue(list.get(i).getId());
            //创建单元格并设置单元格内容
            row1.createCell(0).setCellValue(list.get(i).getId());
            row1.createCell(1).setCellValue(list.get(i).getPhone());
            row1.createCell(2).setCellValue(list.get(i).getPassword());
            row1.createCell(3).setCellValue(list.get(i).getSalt());
            row1.createCell(4).setCellValue(list.get(i).getPicture());
            row1.createCell(5).setCellValue(list.get(i).getNick());
            row1.createCell(6).setCellValue(list.get(i).getName());
            row1.createCell(7).setCellValue(list.get(i).getSex());
            row1.createCell(8).setCellValue(list.get(i).getLocaltion());
            row1.createCell(9).setCellValue(list.get(i).getSign());
            row1.createCell(10).setCellValue(list.get(i).getStatus());
            //处理日期数据
            Cell cell1 = row1.createCell(11);
            cell1.setCellValue(list.get(i).getCreat_time());  //设置单元格内容
            cell1.setCellStyle(cellStyle);  //设置单元格日期样式
            row1.createCell(12).setCellValue(list.get(i).getGuru_id());
        }

        //导出单元格
        try {
            workbook.write(new FileOutputStream(new File("E://UserPoi.xls")));

            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
//---------------------------------easypoi----------------------------------------------
        //easypoi 封装完整，简单导出好用。需要引jar包，支持内嵌集合


        //easypoi导出  参数：Excel表格的title,工作薄的名字 要导出的对象，要导出对象的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户列表","用户表"),User.class, list);

        HashMap<String, Object> map = new HashMap<>();
        //导出
        try {
            workbook.write(new FileOutputStream(new File("G://UserPoi.xls")));
            workbook.close();
        } catch (IOException e) {
            map.put("success","400");
            map.put("message","导出表失败!");
            e.printStackTrace();
        }

        map.put("success","200");
        map.put("message","导出表单成功!");
        return map;
    }
}
