package com.silence.robot.domain;

import java.util.List;

/**
 * 分页Dto
 *
 * @author silence
 * @date 2020/10/21
 */
public class RobotPage<T> {

    /**
     * 总数
     */
    private Long total;
    /**
     * 查询数据
     */
    private List<T> list;

    public RobotPage(Long total, List<T> list) {
        this.list = list;
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
