package com.example.mybatis.common.dto;

import java.io.Serializable;
import java.util.List;

public class StatisticFiterDTO implements Serializable {
    //上报医院ID
    private Long hospitalId;
    //机构省代码
    private String provinceCode;
    //机构省名称
    private String provinceName;
    //机构市代码
    private String cityCode;
    //机构市名称
    private String cityName;
    //机构县代码
    private String countyCode;
    //机构县名称
    private String countyName;
    //机构乡代码
    private String townCode;
    //机构乡名称
    private String townName;
    //发病日期，还是审核日期  0发病日期 1-审核日期
    private String isAttackDate;
    //是否显示到区县。0-不显示到区县，1-显示到区县
    private String showArea;
    //是否发病率和死亡率。0-不显示发病率和死亡率，1-显示到发病率和死亡率
    private Integer diseaseRate;
    //疾病分类  1 临床诊断病例  2-确诊病例 3-疑似病例 4-病原携带者
    private List<String> firstCaseCodeList;
    //病例二级分类代码 0-未分类 1-急性 2-慢性
    private List<String> secondCaseCodeList;
    //疾病代码
    private String diseaseCode;
    //疾病名称
    private String diseaseName;
    //开始时间
    private String startDate;
    //结束时间
    private String endDate;
    //上报的科室名称 ,历史遗留问题，这个是科室名称
    private String reportDeptCode;
    //统计类型  1-年统计表 2-月统计表 3-周统计表  4-自定义
    private String statisticalReportType;
    //本县区 本市其他县区 本省其他城市 其他省 港澳台 外籍
    private  List<String> belongCode;

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getTownCode() {
        return townCode;
    }

    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getIsAttackDate() {
        return isAttackDate;
    }

    public void setIsAttackDate(String isAttackDate) {
        this.isAttackDate = isAttackDate;
    }

    public String getShowArea() {
        return showArea;
    }

    public void setShowArea(String showArea) {
        this.showArea = showArea;
    }

    public Integer getDiseaseRate() {
        return diseaseRate;
    }

    public void setDiseaseRate(Integer diseaseRate) {
        this.diseaseRate = diseaseRate;
    }

    public List<String> getFirstCaseCodeList() {
        return firstCaseCodeList;
    }

    public void setFirstCaseCodeList(List<String> firstCaseCodeList) {
        this.firstCaseCodeList = firstCaseCodeList;
    }

    public List<String> getSecondCaseCodeList() {
        return secondCaseCodeList;
    }

    public void setSecondCaseCodeList(List<String> secondCaseCodeList) {
        this.secondCaseCodeList = secondCaseCodeList;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReportDeptCode() {
        return reportDeptCode;
    }

    public void setReportDeptCode(String reportDeptCode) {
        this.reportDeptCode = reportDeptCode;
    }

    public String getStatisticalReportType() {
        return statisticalReportType;
    }

    public void setStatisticalReportType(String statisticalReportType) {
        this.statisticalReportType = statisticalReportType;
    }

    public List<String> getBelongCode() {
        return belongCode;
    }

    public void setBelongCode(List<String> belongCode) {
        this.belongCode = belongCode;
    }
}
