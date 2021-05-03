package com.nowcoder.dao;

import com.nowcoder.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author hc
 */
@Mapper
public interface CommentDAO {

    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id, content, created_date, entity_id, entity_type, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 添加评论
     * @param comment (userId, content, createdDate, entityId, entityType, status)
     * @return
     */
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    /**
     * 筛选指定评论
     * @param id
     * @return
     */
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    Comment getCommentById(int id);

    /**
     * 筛选指定[entityId, entityType]下的评论
     * @param entityId
     * @param entityType
     * @return
     */
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME,
            "where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    /**
     * 得到 [entityId, entityType]下的评论条数
     * @param entityId
     * @param entityType
     * @return
     */
    @Select({"select count(id) from", TABLE_NAME, "where entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    /**
     * 更新 status 状态
     * @param id
     * @param status
     * @return
     */
    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);

    /**
     * 统计 userId 的评论数
     * @param userId
     * @return
     */
    @Select({"select count(id) from ", TABLE_NAME, " where user_id=#{userId}"})
    int getUserCommentCount(int userId);
}
