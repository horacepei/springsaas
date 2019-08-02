package com.springboot.action.saas.common.logging.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
/*
* 日志数据和数据表实体类
* */
@Data
//jpa 实体
@Entity
//表名
@Table(name="mkt_log")
//lombok 注解，NoArgsConstructor 无参数构造函数
@NoArgsConstructor
public class Log {
    //设置ID主键,定义生成策略为自增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //描述
    private String description;
    //方法名
    private String method;
    //参数，定义字段类型为text
    @Column(columnDefinition = "text")
    private String params;
    //日志类型, 定义字段名称
    @Column(name = "log_type")
    private String logType;
    //请求ip, 定义字段名称
    @Column(name = "request_ip")
    private String requestIp;
    //请求耗时
    private Long time;
    //异常详细，定义字段名称
    @Column(name = "exception_detail", columnDefinition = "text")
    private String exceptionDetail;
    //请求时间，实体插入数据库的时候设置这个数值
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;
    //构造函数（代参）
    public Log(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}
