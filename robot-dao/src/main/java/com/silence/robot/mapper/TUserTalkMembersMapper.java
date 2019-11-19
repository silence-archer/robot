package com.silence.robot.mapper;

import com.silence.robot.model.TUserTalkMembers;
import java.util.List;

public interface TUserTalkMembersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_talk_members
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_talk_members
     *
     * @mbg.generated
     */
    int insert(TUserTalkMembers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_talk_members
     *
     * @mbg.generated
     */
    TUserTalkMembers selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_talk_members
     *
     * @mbg.generated
     */
    List<TUserTalkMembers> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_talk_members
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TUserTalkMembers record);
}