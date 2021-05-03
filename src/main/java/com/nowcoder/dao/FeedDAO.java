package com.nowcoder.dao;

import com.nowcoder.model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author hc
 */
@Mapper
public interface FeedDAO {

    String TABLE_NAME = " feed ";
    String INSERT_FIELDS = " user_id, data, created_date, type ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 添加 feed
     * @param feed
     * @return
     */
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{data},#{createdDate},#{type})"})
    int addFeed(Feed feed);

    /**
     * 通过 id 筛选 feed
     * @param id
     * @return
     */
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    Feed getFeedById(int id);

    /**
     * 通过 user 筛选 feeds
     * @param maxId
     * @param userIds
     * @param count
     * @return
     */
    List<Feed> selectUserFeeds(@Param("maxId") int maxId, @Param("userIds") List<Integer> userIds,
                               @Param("count") int count);
}
