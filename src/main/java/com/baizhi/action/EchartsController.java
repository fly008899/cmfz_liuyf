package com.baizhi.action;

import com.baizhi.entity.City;
import com.baizhi.entity.Pro;
import com.baizhi.entity.UserLocation;
import com.baizhi.entity.UserMonth;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("echarts")
public class EchartsController {
    @Autowired
    private UserService userService;

    @RequestMapping("queryUser")
    @ResponseBody
    public HashMap<String, Object> queryUser(){
        HashMap<String, Object> map = new HashMap<>();

        /*HashMap<String, Object> map = new HashMap<>();
        map.put("month", Arrays.asList("1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"));
        map.put("boys", Arrays.asList(5, 200, 36, 100, 10, 20,5, 200, 36, 100, 10, 20));
        map.put("girls", Arrays.asList(5, 200, 400, 100, 100, 200,5, 200, 36, 100, 10, 20));*/

        map.put("month", Arrays.asList("1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"));
        List<Integer> boyscount = new ArrayList<>();
        List<Integer> girlscount = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            List<UserMonth> counts = userService.selectmonth("" + i%10, "male");
            if(counts.size()==0){boyscount.add(i-1,0);}
            if(counts.size()!=0){boyscount.add(i-1,counts.size());}
        }
        for (int i = 1; i <= 12; i++) {
            List<UserMonth> counts = userService.selectmonth("" + i%10, "female");
            if(counts.size()==0){girlscount.add(i-1,0);}
            if(counts.size()!=0){girlscount.add(i-1,counts.size());}
        }
        map.put("boys",boyscount );
        map.put("girls",girlscount );
        return map;
    }

    @RequestMapping("queryUserChina")
    @ResponseBody
    public ArrayList<Pro> queryUserChina(){

        /*City city1 = new City("北京","300");
        City city2 = new City("河南","700");
        City city3 = new City("山东","500");
        City city4 = new City("云南","900");*/

        //封装 city 集合
        ArrayList<City> boys = new ArrayList<>();
        /*boys.add(city1);
        boys.add(city2);
        boys.add(city3);
        boys.add(city4);*/

        List<UserLocation> male = userService.selectlocaltion("male");
        for (UserLocation userLocation : male) {
            City city = new City(userLocation.getLocaltion(),""+userLocation.getCount());
            boys.add(city);
        }


        //封装 city 集合
        ArrayList<City> girls = new ArrayList<>();
        /*girls.add(new City("天津","400"));
        girls.add(new City("上海","700"));
        girls.add(new City("广东","200"));
        girls.add(new City("海南","800"));*/
        List<UserLocation> female = userService.selectlocaltion("female");
        for (UserLocation userLocation : female) {
            City city = new City(userLocation.getLocaltion(),""+userLocation.getCount());
            girls.add(city);
        }

        Pro pro1 = new Pro("女性",girls);
        Pro pro2 = new Pro("男性",boys);

        //封装  最外层集合
        ArrayList<Pro> pros = new ArrayList<>();
        pros.add(pro1);
        pros.add(pro2);

        return pros;
    }
}
