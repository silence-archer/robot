package com.silence.robot.domain.clock;


public class SaveSignRuleDate  {

    private Integer userId;

    private Integer projectId;

    private Integer ruleId;

    private Integer addrId;

    private Integer apprUserId;

    private Integer deptId;

    private String workReportType;

    private String longitude;

    private String latitude;

    private String address;

    private String imagePath;

    private String atcity;

    private String pbflag;

    private String beforeup;

    private String itcode;

    private String sbuId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getAddrId() {
        return addrId;
    }

    public void setAddrId(Integer addrId) {
        this.addrId = addrId;
    }

    public Integer getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(Integer apprUserId) {
        this.apprUserId = apprUserId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getWorkReportType() {
        return workReportType;
    }

    public void setWorkReportType(String workReportType) {
        this.workReportType = workReportType;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAtcity() {
        return atcity;
    }

    public void setAtcity(String atcity) {
        this.atcity = atcity;
    }

    public String getPbflag() {
        return pbflag;
    }

    public void setPbflag(String pbflag) {
        this.pbflag = pbflag;
    }

    public String getBeforeup() {
        return beforeup;
    }

    public void setBeforeup(String beforeup) {
        this.beforeup = beforeup;
    }

    public String getItcode() {
        return itcode;
    }

    public void setItcode(String itcode) {
        this.itcode = itcode;
    }

    public String getSbuId() {
        return sbuId;
    }

    public void setSbuId(String sbuId) {
        this.sbuId = sbuId;
    }

    @Override
    public String toString() {
        return "{" +
                "userId=" + userId +
                ", projectId=" + projectId +
                ", ruleId=" + ruleId +
                ", addrId=" + addrId +
                ", apprUserId=" + apprUserId +
                ", deptId=" + deptId +
                ", workReportType='" + workReportType + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", atcity='" + atcity + '\'' +
                ", pbflag='" + pbflag + '\'' +
                ", beforeup='" + beforeup + '\'' +
                ", itcode='" + itcode + '\'' +
                ", sbuId='" + sbuId + '\'' +
                '}';
    }

}

