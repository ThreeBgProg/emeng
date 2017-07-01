package com.huiming.emeng.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.huiming.emeng.model.Post;
import com.huiming.emeng.model.PostWithBLOBs;
import com.huiming.emeng.model.States;

public interface PostMapper {
    
	int deleteByPrimaryKey(Integer id);

    int insert(PostWithBLOBs record);

    int insertSelective(PostWithBLOBs record);

    PostWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PostWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PostWithBLOBs record);

    int updateByPrimaryKey(Post record);
    
    public List<Post> findPost(Map<String, String> map);
    
    public List<Post> selectAllPost();
    
    /**
     * 返回广告在数据库中的数目，service调用时需要考虑返回值为null，默认设置为0
     */
    int selectNumberfromPost(States states);
    
    /**
     * 查询第几页数据
     */
    List<Post> selectPostindex(@Param("fromIndex") Integer fromIndex, @Param("pageSize") Integer pageSize);

    /**
     * 查询第几页数据
     */
    List<Post> selectPostWithPagesizeFromFromindex1(@Param("fromIndex") Integer fromIndex, @Param("pageSize") Integer pageSize);

    /**
     * 查询第几页数据
     */
    List<Post> selectPostWithPagesizeFromFromindex2(@Param("fromIndex") Integer fromIndex, @Param("pageSize") Integer pageSize);
    
    /**
     * 按照热度查询
     * @param fromIndex
     * @param pageSize
     * @return
     */
    List<Post> selectPostByVist(@Param("fromIndex") Integer fromIndex, @Param("pageSize") Integer pageSize);

}