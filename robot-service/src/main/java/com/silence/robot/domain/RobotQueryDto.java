package com.silence.robot.domain;

/**
 * 分页查询dto
 *
 * @author silence
 * @date 2021/7/19
 */
public class RobotQueryDto {
    private Integer page;
    private Integer limit;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
