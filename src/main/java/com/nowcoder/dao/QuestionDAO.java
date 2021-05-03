package com.nowcoder.dao;

import com.nowcoder.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author hc
 */
@Mapper
public interface QuestionDAO {

    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 向 Question 表添加问题
     * @param question
     * @return 返回主键 id
     */
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    /**
     * 选取指定问题 (userId, offset, limit)
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 选取指定问题 (id)
     * @param id
     * @return
     */
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    Question getById(int id);

    /**
     * 更新 Question 下的评论数目
     * @param id
     * @param commentCount
     * @return
     */
    @Update({"update", TABLE_NAME, "set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);
}
