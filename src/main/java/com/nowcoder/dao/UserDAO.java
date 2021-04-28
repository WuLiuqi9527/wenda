package com.nowcoder.dao;

import com.nowcoder.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @author hc
 */
@Mapper
public interface UserDAO {

    String TABLE_NAME = " user ";
    String INSERT_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 向 User 表添加用户
     * @param user
     * @return
     */
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    /**
     * 选取指定用户(id)
     * @param id
     * @return
     */
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    User selectById(int id);

    /**
     * 选取指定用户(name)
     * @param name
     * @return
     */
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where name=#{name}"})
    User selectByName(String name);

    /**
     * 更新指定用户 (id) 密码 (password)
     * @param user
     */
    @Update({"update", TABLE_NAME, "set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    /**
     * 删除指定用户(id)
     * @param id
     */
    @Delete({"delete from", TABLE_NAME, "where id=#{id}"})
    void deleteById(int id);
}
