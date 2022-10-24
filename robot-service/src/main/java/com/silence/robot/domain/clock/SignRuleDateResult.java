package com.silence.robot.domain.clock;

import java.util.List;

public class SignRuleDateResult {

    private String msg;

    private String success;

    private Data data;

    public static class Data{
        private String sbuid;
        private String missionType;
        private String AFTERDOWN;
        private String ATT_NAME;
        private String userName;
        private String BEFOREDOWN;
        private String BEFOREUP;
        private String VALIDFLAG;
        private boolean isProjGroup;
        private String WORKDAYS;
        private String pbflag;
        private String RULETYPE;
        private String SELECT_ADDRESS;
        private Integer apprUserId;
        private boolean projectSign;
        private Integer projectId;
        private Integer ID;
        private String projectCode;
        private String FREEFLAG;
        private String employeeType;
        private String projectName;
        private String AFTERUP;
        private Long TAKEFFECT_TIME;
        private String ADDTIME;
        private String TANTIME;
        private String ATTENDANCEMODEL;
        private String loginname;
        private String departmentName;
        private Integer deptId;
        private Integer employeeId;
        private List<AddRess> addRessList;

        public String getSbuid() {
            return sbuid;
        }

        public void setSbuid(String sbuid) {
            this.sbuid = sbuid;
        }

        public String getMissionType() {
            return missionType;
        }

        public void setMissionType(String missionType) {
            this.missionType = missionType;
        }

        public String getAFTERDOWN() {
            return AFTERDOWN;
        }

        public void setAFTERDOWN(String AFTERDOWN) {
            this.AFTERDOWN = AFTERDOWN;
        }

        public String getATT_NAME() {
            return ATT_NAME;
        }

        public void setATT_NAME(String ATT_NAME) {
            this.ATT_NAME = ATT_NAME;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getBEFOREDOWN() {
            return BEFOREDOWN;
        }

        public void setBEFOREDOWN(String BEFOREDOWN) {
            this.BEFOREDOWN = BEFOREDOWN;
        }

        public String getBEFOREUP() {
            return BEFOREUP;
        }

        public void setBEFOREUP(String BEFOREUP) {
            this.BEFOREUP = BEFOREUP;
        }

        public String getVALIDFLAG() {
            return VALIDFLAG;
        }

        public void setVALIDFLAG(String VALIDFLAG) {
            this.VALIDFLAG = VALIDFLAG;
        }

        public boolean isProjGroup() {
            return isProjGroup;
        }

        public void setProjGroup(boolean projGroup) {
            isProjGroup = projGroup;
        }

        public String getWORKDAYS() {
            return WORKDAYS;
        }

        public void setWORKDAYS(String WORKDAYS) {
            this.WORKDAYS = WORKDAYS;
        }

        public String getPbflag() {
            return pbflag;
        }

        public void setPbflag(String pbflag) {
            this.pbflag = pbflag;
        }

        public String getRULETYPE() {
            return RULETYPE;
        }

        public void setRULETYPE(String RULETYPE) {
            this.RULETYPE = RULETYPE;
        }

        public String getSELECT_ADDRESS() {
            return SELECT_ADDRESS;
        }

        public void setSELECT_ADDRESS(String SELECT_ADDRESS) {
            this.SELECT_ADDRESS = SELECT_ADDRESS;
        }

        public Integer getApprUserId() {
            return apprUserId;
        }

        public void setApprUserId(Integer apprUserId) {
            this.apprUserId = apprUserId;
        }

        public boolean isProjectSign() {
            return projectSign;
        }

        public void setProjectSign(boolean projectSign) {
            this.projectSign = projectSign;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }

        public Integer getID() {
            return ID;
        }

        public void setID(Integer ID) {
            this.ID = ID;
        }

        public String getProjectCode() {
            return projectCode;
        }

        public void setProjectCode(String projectCode) {
            this.projectCode = projectCode;
        }

        public String getFREEFLAG() {
            return FREEFLAG;
        }

        public void setFREEFLAG(String FREEFLAG) {
            this.FREEFLAG = FREEFLAG;
        }

        public String getEmployeeType() {
            return employeeType;
        }

        public void setEmployeeType(String employeeType) {
            this.employeeType = employeeType;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getAFTERUP() {
            return AFTERUP;
        }

        public void setAFTERUP(String AFTERUP) {
            this.AFTERUP = AFTERUP;
        }

        public Long getTAKEFFECT_TIME() {
            return TAKEFFECT_TIME;
        }

        public void setTAKEFFECT_TIME(Long TAKEFFECT_TIME) {
            this.TAKEFFECT_TIME = TAKEFFECT_TIME;
        }

        public String getADDTIME() {
            return ADDTIME;
        }

        public void setADDTIME(String ADDTIME) {
            this.ADDTIME = ADDTIME;
        }

        public String getTANTIME() {
            return TANTIME;
        }

        public void setTANTIME(String TANTIME) {
            this.TANTIME = TANTIME;
        }

        public String getATTENDANCEMODEL() {
            return ATTENDANCEMODEL;
        }

        public void setATTENDANCEMODEL(String ATTENDANCEMODEL) {
            this.ATTENDANCEMODEL = ATTENDANCEMODEL;
        }

        public String getLoginname() {
            return loginname;
        }

        public void setLoginname(String loginname) {
            this.loginname = loginname;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public Integer getDeptId() {
            return deptId;
        }

        public void setDeptId(Integer deptId) {
            this.deptId = deptId;
        }

        public Integer getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(Integer employeeId) {
            this.employeeId = employeeId;
        }

        public List<AddRess> getAddRessList() {
            return addRessList;
        }

        public void setAddRessList(List<AddRess> addRessList) {
            this.addRessList = addRessList;
        }
    }

    public static class AddRess{

        private Integer attendanceLon;

        private Integer id;

        private Integer attendanceLat;

        private Integer attendanceArea;

        public Integer getAttendanceLon() {
            return attendanceLon;
        }

        public void setAttendanceLon(Integer attendanceLon) {
            this.attendanceLon = attendanceLon;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAttendanceLat() {
            return attendanceLat;
        }

        public void setAttendanceLat(Integer attendanceLat) {
            this.attendanceLat = attendanceLat;
        }

        public Integer getAttendanceArea() {
            return attendanceArea;
        }

        public void setAttendanceArea(Integer attendanceArea) {
            this.attendanceArea = attendanceArea;
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
