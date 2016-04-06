package com.lanou.ourteam.mirrors.bean;



import java.io.Serializable;

/**
 * Created by ZHDelete on 16/4/6.
 */
public class StoryDetailText implements Serializable{

    private String verticalTitle;
    private String verticalTitleColor;
    private String smallTitle;
    private String title;
    private String titleColor;
    private String subTitle;
    private String colorTitle;
    private String colorTitleColor;
    private String category;
    private String img_array;


    public StoryDetailText() {
        super();
    }

    public StoryDetailText(String verticalTitle,
                           String verticalTitleColor,
                           String smallTitle,
                           String title,
                           String titleColor,
                           String subTitle,
                           String colorTitle,
                           String colorTitleColor,
                           String category,
                           String img_array) {
        this.verticalTitle = verticalTitle;
        this.verticalTitleColor = verticalTitleColor;
        this.smallTitle = smallTitle;
        this.title = title;
        this.titleColor = titleColor;
        this.subTitle = subTitle;
        this.colorTitle = colorTitle;
        this.colorTitleColor = colorTitleColor;
        this.category = category;
        this.img_array = img_array;
    }

    public String getVerticalTitle() {
        return verticalTitle;
    }

    public void setVerticalTitle(String verticalTitle) {
        this.verticalTitle = verticalTitle;
    }

    public String getVerticalTitleColor() {
        return verticalTitleColor;
    }

    public void setVerticalTitleColor(String verticalTitleColor) {
        this.verticalTitleColor = verticalTitleColor;
    }

    public String getSmallTitle() {
        return smallTitle;
    }

    public void setSmallTitle(String smallTitle) {
        this.smallTitle = smallTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getColorTitle() {
        return colorTitle;
    }

    public void setColorTitle(String colorTitle) {
        this.colorTitle = colorTitle;
    }

    public String getColorTitleColor() {
        return colorTitleColor;
    }

    public void setColorTitleColor(String colorTitleColor) {
        this.colorTitleColor = colorTitleColor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImg_array() {
        return img_array;
    }

    public void setImg_array(String img_array) {
        this.img_array = img_array;
    }
}

