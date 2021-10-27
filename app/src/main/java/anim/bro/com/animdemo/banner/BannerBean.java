package anim.bro.com.animdemo.banner;

import java.util.List;

/**
 * Created by zhangshan on 2021/10/15 10:18.
 * Description：请添加类注释
 */
public class BannerBean  {

    private Topic topic;
    private List<PitContent> pitContent;

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<PitContent> getPitContent() {
        return pitContent;
    }

    public void setPitContent(List<PitContent> pitContent) {
        this.pitContent = pitContent;
    }

    public static class Topic {
        private int bgStyle;
        private String bgImgUrl;
        private String bgColor;
        private int interval;

        public int getBgStyle() {
            return bgStyle;
        }

        public void setBgStyle(int bgStyle) {
            this.bgStyle = bgStyle;
        }

        public String getBgImgUrl() {
            return bgImgUrl;
        }

        public void setBgImgUrl(String bgImgUrl) {
            this.bgImgUrl = bgImgUrl;
        }

        public String getBgColor() {
            return bgColor;
        }

        public void setBgColor(String bgColor) {
            this.bgColor = bgColor;
        }

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }
    }

    public static class PitContent {
        private String imgUrl;
        private String linkUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }
    }
}
