package com.huiming.emeng.mapper;

import com.huiming.emeng.common.LessonPageInfo;
import com.huiming.emeng.model.Passage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PassageMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Passage record);

    Passage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Passage record);

    int updateByPrimaryKeyWithBLOBs(Passage record);

    int updateByPrimaryKey(Passage record);

    /**
     *主页显示的文章
     */
    List<Passage> selectByTypeAndDescendWithTime(@Param("passageType") Byte passageType, @Param("showPassageNums") Integer showPassageNums);

    /**
     * 返回某类文章在数据库中的数目，service调用时需要考虑返回值为null，默认设置为0
     */
    int selectByPassageType(Byte passageType);

    /**
     * 根据文章类型查询第几页数据
     */
    List<Passage> selectPassageWithPagesizeFromFromindex(@Param("passageType") Byte passageType,
                                                         @Param("fromIndex") Integer fromIndex,
                                                         @Param("pageSize") Integer pageSize);

    /**
     * 返回热点推荐文章，默认根据推荐等级正序排列，返回7条数据
     */
    List<Passage> selectRecommendPassageList();

    /**
     * 返回章节下一级所对应模块，默认显示前7条数据
     */
    List<Passage> selectPassageByLessonIdAndChapterIdAndPassageType(
                                                    @Param("lessonId") Integer lessonId,
                                                    @Param("chapterId") Integer chapterId,
                                                    @Param("passageType") Byte passageType);

    /**
     * 查询某章某分块文章数目
     */
    Integer selectCountByLessonIdAndChapterIdAndPassageType(@Param("lessonId") Integer lessonId,
                                                            @Param("chapterId") Integer chapterId,
                                                            @Param("passageType") Byte passageType);

    /**
     * 根据课程分页信息查询课程文章某页
     */
    List<Passage> selectLessonPassageWithPagesizeFromFromindex(LessonPageInfo lessonPageInfo);

    /**
     * 根据文章类型查询该类型所有文章
     */
    List<Passage> selectAllPassageByPassageType(Byte passageType);

    /**
     * 根据课程文章标题模糊查询文章
     */
    List<Passage> selectLessonPassageByTitleWithPagesizeFromFromindex(@Param("title")String title,
                                                                      @Param("lessonId") Integer lessonId,
                                                                      @Param("pageSize") Integer pageSize,
                                                                      @Param("fromIndex") Integer fromIndex);
    /**
     * 根据文章标题非课程文章
     */
    List<Passage> selectPassageByTitle(@Param("title")String title,
                                       @Param("fromIndex") Integer fromIndex,
                                       @Param("pageSize") Integer pageSize);

    /**
     * 根据老师名字查询文章
     */
    List<Passage> selectPassageByAuthor(@Param("author")String author,
                                       @Param("fromIndex") Integer fromIndex,
                                       @Param("pageSize") Integer pageSize);

    /**
     * 返回对应老师的文章数
     */
    int selectCountByAuthor(@Param("author") String author);
    /**
     * 返回模糊查询文章个数
     * @param title
     * @return
     */
    int selectCountByTitle(@Param("title") String title);

    /**
     * 返回模糊查询课程文章个数
     * @param lessonId
     * @param title
     * @return
     */
    int selectCountOfLessonPassageByTitle(@Param("lessonId") Integer lessonId,
                                          @Param("title") String title);

    /**
     * 返回对应课程阅读书目分页
     * @param passageType
     * @param fromIndex
     * @param pageSize
     * @param lessonId
     * @return
     */
    List<Passage> selectReadPassageWithPagesizeFromFromindex(@Param("passageType") Byte passageType,
                                                             @Param("fromIndex") Integer fromIndex,
                                                             @Param("pageSize") Integer pageSize,
                                                             @Param("lessonId") Integer lessonId);

    List<Passage> selectTeacherPassageShowInTeacherPage(@Param("author")String author);
}