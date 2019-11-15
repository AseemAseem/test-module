package com.example.mybatis.common.mybatis;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MpGenerator {

	private static String SYSTEM_DIR = "d://crData";
//	SYSTEM_DIR

	// 数据库配置
	private static String JDBC_URL = "jdbc:mysql://localhost:3306/cr_jkxt?characterEncoding=utf8&serverTimezone=UTC";

	private static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String JDBC_USERNAME = "root";
//	private static String JDBC_PASSWORD = "Zdm123456!";
	private static String JDBC_PASSWORD = "123456";
	
	// 是否继承BaseEntity
	private static boolean BASE_ENTITY = true;
	// 生成test表
	private static String[] tabArr = new String[] { "cr_user" };
	// 生成的包路径
	private static String PACKAGE_DIR = "com.example.mybatis";
	// 模块名称
	private static String MODULE_NAME = "";
	// xml 生成路径
	private static String MYBATIS_XML = SYSTEM_DIR + "/src/main/resources/mybatis/";

	/**
	 * <p>
	 * 跟据数据库表自动生成java代码和mapper文件
	 * </p>
	 * 
	 */
	public static void main(String[] args) {
		execute(MODULE_NAME, tabArr);
	}

	/**
	 * 执行
	 * 
	 * @param moduleName
	 *            模块名
	 * @param tabArr
	 *            生成的表
	 */
	private static void execute(String moduleName, String[] tabArr) {

		AutoGenerator mpg = new AutoGenerator();

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(SYSTEM_DIR + "/src/main/java");
//		gc.setFileOverride(true);
		gc.setActiveRecord(false);
		gc.setEnableCache(false);
		
		gc.setBaseResultMap(true);
		gc.setBaseColumnList(true);
		gc.setOpen(false);
//		gc.setAuthor(SubVersion.getInstance().getCurrentConnectName());
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setTypeConvert(new MySqlTypeConvert() {
			// 自定义数据库表字段类型转换【可选】
			@Override
			public DbColumnType processTypeConvert(String fieldType) {
				System.out.println("转换类型：" + fieldType);
				// 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
				return super.processTypeConvert(fieldType);
			}
		});
		dsc.setDriverName(JDBC_DRIVER);
		dsc.setUsername(JDBC_USERNAME);
		dsc.setPassword(JDBC_PASSWORD);
		dsc.setUrl(JDBC_URL);
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setTablePrefix(new String[] { "cr_", "sys_" });// 此处可以修改为您的表前缀
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setInclude(tabArr);
		strategy.setLogicDeleteFieldName("del_flag");
		strategy.setRestControllerStyle(true);
		// strategy.setExclude(new String[]{"test"}); // 排除生成的表
		// 自定义实体父类
		strategy.setSuperControllerClass("com.example.mybatis.common.base.BaseController");
		if(BASE_ENTITY){
			strategy.setSuperEntityClass("com.example.mybatis.common.base.BaseEntity");
			// 自定义实体，公共字段
			strategy.setSuperEntityColumns(new String[] { "create_by", "create_time", "update_by", "update_time","del_flag", "remark" });
		}
		// 自定义 mapper 父类
		// strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
		// 自定义 service 父类
		// strategy.setSuperServiceClass("com.baomidou.demo.TestService");
		// 自定义 service 实现类父类
		// strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
		// 自定义 controller 父类
		// strategy.setSuperControllerClass("com.baomidou.demo.TestController");
		// 【实体】是否生成字段常量（默认 false）
		// public static final String ID = "test_id";
		// strategy.setEntityColumnConstant(true);
		// 【实体】是否为构建者模型（默认 false）
		// public User setName(String name) {this.name = name; return this;}
		// strategy.setEntityBuilderModel(true);
		mpg.setStrategy(strategy);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent(PACKAGE_DIR);
		pc.setModuleName(moduleName);
		mpg.setPackageInfo(pc);

		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<String, Object>();
				this.setMap(map);
			}
		};

		List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
		focList.add(new FileOutConfig("/template/mapper.xml.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return MYBATIS_XML + tableInfo.getEntityName() + "Mapper.xml";
			}
		});

		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);

		TemplateConfig tc = new TemplateConfig();
		
		tc.setController("/template/controller.java.vm");
		tc.setEntity("/template/entity.java.vm");
		tc.setMapper("/template/mapper.java.vm");
		tc.setXml(null);
		tc.setService("/template/service.java.vm");
		tc.setServiceImpl("/template/serviceImpl.java.vm");
		mpg.setTemplate(tc);

		// 执行生成
		mpg.execute();

	}

}