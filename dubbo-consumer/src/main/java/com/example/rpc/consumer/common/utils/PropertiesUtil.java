package com.example.rpc.consumer.common.utils;

import org.apache.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Configuration
public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private static Properties props;

    static {
        String fileName = "custom.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
        } catch (IOException e) {
            logger.error("读取配置文件" + fileName + "异常", e);
        }
    }

    public static String getProperty(String key) {
        String property = props.getProperty(key.trim());
        if (StringUtils.isBlank(property)) {
            return null;
        }
        return property;
    }
}
