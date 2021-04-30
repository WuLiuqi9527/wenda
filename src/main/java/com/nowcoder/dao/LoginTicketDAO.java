package com.nowcoder.dao;

import com.nowcoder.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @author hc
 */
@Mapper
public interface LoginTicketDAO {

    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = " user_id, expired, status, ticket ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 向 login_ticket 表添加 ticket (用户)
     *
     * @param ticket
     * @return
     */
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket ticket);

    /**
     * 查看 login_ticket 表是否存在 ticket (用户)
     *
     * @param ticket
     * @return
     */
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    /**
     * 更新 ticket 状态 (登录态、登出态)
     *
     * @param ticket
     * @param status
     */
    @Update({"update", TABLE_NAME, "set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
