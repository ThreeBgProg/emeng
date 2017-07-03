package com.huiming.emeng.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huiming.emeng.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectSelective(User record);
    
    /**
     * 查找分页用户数据
     * @param fromIndex
     * @param pageSize
     * @return
     */
    List<User> selectPagerUser(@Param("fromIndex")Integer fromIndex, @Param("pageSize")Integer pageSize);
    
    /**
     * 根据user实例查询分页数据
     * @param record
     * @param fromIndex
     * @param pageSize
     * @return
     */
    List<User> selectPagerUserSelective(@Param("user")User record,@Param("fromIndex")Integer fromIndex, @Param("pageSize")Integer pageSize);
    
    /**
     * 模糊查询吧
     * @param record
     * @param fromIndex
     * @param pageSize
     * @return
     */
    List<User> findSelective(@Param("user")User record,@Param("fromIndex")Integer fromIndex, @Param("pageSize")Integer pageSize);
    
    /**
     * 根据user查询总条数
     * @param record
     * @return
     */
    int countSelective(User record);
    
    /**
    * 查询总条数
    * @param record
    * @return
    */
   int selectCount();
   
   User selectByJobId(String jobId);

}