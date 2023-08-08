package com.example.easybbsweb;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceWrapper;
import com.alibaba.druid.stat.DruidStatManagerFacade;
import jakarta.activation.DataSource;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class DatasourceTest {
    @Resource
    DruidDataSourceWrapper dataSource;


    @Test
    void func1(){
        List<Map<String, Object>> sqlStatDataList = DruidStatManagerFacade.getInstance().getSqlStatDataList(dataSource);
        List<Map<String, Object>> dataSourceStatDataList = DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
        for(Map<String,Object> map:sqlStatDataList){
            String sql = (String) map.get("SQL");
            Date maxTimespanOccurTime = (Date) map.get("MaxTimespanOccurTime");
            log.info("sql:{}",sql);
            log.info("occuretime:{}",maxTimespanOccurTime);
        }
        System.out.println(dataSourceStatDataList);
    }
   @Test
    void func2(){
        String a="  Hi myLove";
        String b="hi     myloveBabe";
        a=a.trim().toLowerCase();
        b=b.trim().toLowerCase();
       boolean b1 = b.startsWith(a);
       System.out.println(b1);
    }

    public static void main(String[] args) {

    }







}

