package com.nowcoder.dao;

import com.nowcoder.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author hc
 */
@Mapper
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 添加私信
     * @param message
     * @return
     */
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    /**
     * 获取站内信列表
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where conversation_id=#{conversationId} order by id desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 未读私信数量
     * @param userId
     * @param conversationId
     * @return
     */
    @Select({"select count(id) from", TABLE_NAME, "where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    /**
     * userId 的前 limit 条私信
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    @Select({"select", INSERT_FIELDS, ",count(id) as id from ( select * from ", TABLE_NAME,
            "where from_id=#{userId} or to_id=#{userId} order by id desc) tt group by conversation_id order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset, @Param("limit") int limit);
}
