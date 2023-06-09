package com.silence.robot.enumeration;

/**
 * TODO
 *
 * @author silence
 * @since 2021/9/6
 */
public enum AutoInterfaceEnum {
    //
    AUTO_INTERFACE_HTML("loan.html", "interface_html.ftl","输入界面"),
    AUTO_INTERFACE_2_0_HTML("loan.html", "interface_2_0_html.ftl","输入界面"),
    AUTO_INTERFACE_JS("loan.js","interface_js.ftl","输入界面js"),
    AUTO_INTERFACE_2_0_JS("loan.js","interface_2_0_js.ftl","输入界面js"),
    AUTO_INTERFACE_RESULT_HTML("result/result.html","interface_result_html.ftl","输出界面"),
    AUTO_INTERFACE_2_0_RESULT_HTML("result/result.html","interface_2_0_result_html.ftl","输出界面"),
    AUTO_INTERFACE_RESULT_JS("result/result.js","interface_result_js.ftl","输出界面js"),
    AUTO_INTERFACE_2_0_RESULT_JS("result/result.js","interface_2_0_result_js.ftl","输出界面js"),
    COMMEMORATING("commemorating.html","commemorating.ftl","纪念页面"),
    ;


    private final String code;
    private final String value;
    private final String desc;

    AutoInterfaceEnum(String code, String value, String desc) {
        this.code = code;
        this.desc = desc;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getValue() {
        return value;
    }
}
