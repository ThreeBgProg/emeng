package com.huiming.emeng.model;

public class Video {
    private Integer id;

    private String name;

    //picture
    private String pic;

    private String link;

    //所属课程id
    private Integer lesson;

    //章节id（非课程资源则为0）（对应chapter表的id）
    private Integer chapter;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public Integer getLesson() {
        return lesson;
    }

    public void setLesson(Integer lesson) {
        this.lesson = lesson;
    }

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }

	@Override
	public String toString() {
		return "Video [id=" + id + ", name=" + name + ", pic=" + pic + ", link=" + link + ", lesson=" + lesson
				+ ", chapter=" + chapter + "]";
	}
    
    
}