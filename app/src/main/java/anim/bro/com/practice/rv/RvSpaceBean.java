package anim.bro.com.practice.rv;

/**
 * Created by zhangshan on 4/21/21 7:17 PM.
 */
class RvSpaceBean {

    public String title;
    public String bottomText;//有可能是超长的文字
    public int color = -1;


    public String getTitle() {
        return title;
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
