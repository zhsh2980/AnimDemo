package anim.bro.com.animdemo.rv;

import anim.bro.com.animdemo.R;

/**
 * Created by zhangshan on 4/21/21 7:17 PM.
 */
class RvBean {

    public String title;
    public int color = -1;

    public RvBean(String title) {
        this.title = title;
    }

    public RvBean(String title, int color) {
        this.title = title;
        this.color = color;
    }


}
