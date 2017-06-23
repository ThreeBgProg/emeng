package com.huiming.emeng.common;

/**
 * Created by LeoMs on 2017/6/1 0001.
 */
public class LessonPageInfo {

    private Integer lessonId;

    private Integer chapterId;

    private Integer pageNum;

    private Integer pageSize;

    private Byte passageType;

    private Integer fromIndex;

    public LessonPageInfo() {
    }

    public LessonPageInfo(Integer lessonId, Integer chapterId, Integer pageNum, Integer pageSize, Byte passageType) {
        this.lessonId = lessonId;
        this.chapterId = chapterId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.passageType = passageType;
    }

    public LessonPageInfo(Integer lessonId, Integer chapterId, Integer pageNum, Integer pageSize, Byte passageType, Integer fromIndex) {
        this.lessonId = lessonId;
        this.chapterId = chapterId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.passageType = passageType;
        this.fromIndex = fromIndex;
    }

    public Integer getFromIndex() {
        return fromIndex;
    }

    public void setFromIndex(Integer fromIndex) {
        this.fromIndex = fromIndex;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Byte getPassageType() {
        return passageType;
    }

    public void setPassageType(Byte passageType) {
        this.passageType = passageType;
    }
}
