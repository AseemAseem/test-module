<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
        <#list table.fields as field>
            <#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" />
            </#if>
        </#list>
<#list table.fields as field>
    <#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
    </#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
    <result column="${field.name}" property="${field.propertyName}" />
</#list>
    </resultMap>

</#if>
<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
         <#list table.fields as field>
             <#if field_index != 0>,</#if>a.${field.name}
         </#list>
        <#list table.commonFields as field>
            ,a.${field.name}
        </#list>
    </sql>
</#if>

<#if baseColumnList>
    <!-- 列名前缀结果列 -->
    <sql id="ColumnPrefixList">
         <#list table.fields as field>
             <#if field_index != 0>,</#if>${r'${alias}.'}${field.name} AS ${r'${prefix}'}${field.name}
         </#list>
        <#list table.commonFields as field>
        <#-- 注：field.propertyName为大写 -->
            ,${r'${alias}.'}${field.name} AS ${r'${prefix}'}${field.name}
        </#list>
    </sql>
</#if>

    <sql id="entityWrapperJoin">
        <where>
            AND 1=1
            AND a.del_flag=0
            <if test="ew != null">
                <if test="ew.entity != null">
                    <#list table.fields as field>
                        <#if field.propertyType == 'String'>
                            <if test="ew.entity.${field.propertyName}!=null and ew.entity.${field.propertyName}!=''">
                                AND a.${field.name}=${r'#{ew.entity.'}${field.propertyName}}
                            </if>
                        <#else>
                             <if test="ew.entity.${field.propertyName}!=null">
                                 AND a.${field.name}=${r'#{ew.entity.'}${field.propertyName}}
                             </if>
                        </#if>
                    </#list>
                </if>
                <if test="ew!=null and ew.sqlSegment!=null and ew.notEmptyOfWhere">
                ${r'${ew.sqlSegment}'}
                </if>
            </if>
        </where>
        <if test="ew!=null and ew.sqlSegment!=null and ew.emptyOfWhere">
        ${r'${ew.sqlSegment}'}
        </if>
    </sql>
</mapper>