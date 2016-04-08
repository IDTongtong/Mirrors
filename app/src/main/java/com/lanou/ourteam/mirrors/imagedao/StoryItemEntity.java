package com.lanou.ourteam.mirrors.imagedao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "STORY_ITEM_ENTITY".
 */
public class StoryItemEntity {

    private Long id;
    private String story_img;
    private String story_title;

    public StoryItemEntity() {
    }

    public StoryItemEntity(Long id) {
        this.id = id;
    }

    public StoryItemEntity(Long id, String story_img, String story_title) {
        this.id = id;
        this.story_img = story_img;
        this.story_title = story_title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStory_img() {
        return story_img;
    }

    public void setStory_img(String story_img) {
        this.story_img = story_img;
    }

    public String getStory_title() {
        return story_title;
    }

    public void setStory_title(String story_title) {
        this.story_title = story_title;
    }

}
