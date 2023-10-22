package edu.ewubd.quizzler;

public class GridItem {
    private int imageResource;
    private String title;

    public GridItem(int imageResource, String title) {
        this.imageResource = imageResource;
        this.title = title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getCategoryName() {
        return title;
    }
}

