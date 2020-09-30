package com.njmetro.evaluation;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * MyBatis 代码生成器
 *
 * @author RCNJTECH
 * @date 2020/4/14 13:09
 */
public class MyBatisGenerator {

    public static void main(String[] args) {
        /*
         * 代码生成器
         */
        AutoGenerator generator = new AutoGenerator();

        /*
         * 全局策略配置
         */
        GlobalConfig globalConfig = new GlobalConfig()
                .setOutputDir(System.getProperty("user.dir") + "/src/main/java")
                .setFileOverride(true)
                .setOpen(false)
                .setServiceName("%sService")
                .setAuthor("zc");

        /*
         * 数据源配置，通过该配置，指定需要生成代码的具体数据库
         * 1. driverName 决定了 dbType（数据库类型）、typeConvert（类型转换器）和 dbQuery（数据库信息查询类）
         * 2. MySqlTypeConvert 的时间类型转换默认使用 Java 8 的 java.time 包下的新的时间类型（GlobalConfig 的 dateType 默认设置为 DateType.TIME_PACK）
         * 3. MySQL 中 database 与 schema 为同义词，故无需指定 schemaName
         */
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://192.168.138.37:3300/evaluation?serverTimezone=GMT%2B8")
                .setUsername("nj_evaluation")
                .setPassword("hkl42yOOhduXurnF");

        /*
         * 数据库表配置（策略配置项），通过该配置，可指定需要生成哪些表或者排除哪些表
         */
        StrategyConfig strategyConfig = new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityBuilderModel(true)
                .setEntityLombokModel(true)
                .setEntityBooleanColumnRemoveIsPrefix(true)
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(true)
                .setEntityTableFieldAnnotationEnable(true)
                .setInclude("pause_record");

        /*
         * 包名配置，通过该配置，指定生成代码的包路径
         */
        PackageConfig packageConfig = new PackageConfig()
                .setParent("com.njmetro.evaluation")
                .setEntity("domain");

        generator.setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .setGlobalConfig(globalConfig)
                .execute();
    }

}
