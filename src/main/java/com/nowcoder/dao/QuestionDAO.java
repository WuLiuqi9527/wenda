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
     *
     * @param question
     * @return 返回主键 id
     */
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    /**
     * 选取指定问题 (id)
     *
     * @param id
     * @return
     */
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    Question getById(@Param("id") int id);

    /**
     * 选取指定问题 (userId, offset, limit)
     *
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME,
            "where user_id=#{userId} order by id desc limit #{offset}, #{limit}"})
    List<Question> getUserLatestQuestions(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    /**
     * 选取最近发布的问题 (offset, limit)
     *
     * @param offset
     * @param limit
     * @return
     */
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "order by id desc limit #{offset}, #{limit}"})
    List<Question> getLatestQuestions(@Param("offset") int offset,
                                      @Param("limit") int limit);

}
