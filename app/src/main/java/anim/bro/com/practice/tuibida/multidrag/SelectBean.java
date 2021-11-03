package anim.bro.com.practice.tuibida.multidrag;

/**
 * Created by zhangshan on 2019/1/5 14:56.
 */
public class SelectBean {

  private String title;
  private ContentBean mContentBean;
  private int type;
  public static int TYPE_CONTENT = 0;
  public static int TYPE_TITLE = 1;

  public SelectBean(String title) {
    this.title = title;
  }

  public SelectBean() {
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ContentBean getContentBean() {
    return mContentBean;
  }

  public void setContentBean(ContentBean contentBean) {
    mContentBean = contentBean;
  }

  public static class ContentBean {
    private String content;
    private boolean isSelect;

    public ContentBean() {
    }

    public ContentBean(String content) {
      this.content = content;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public boolean isSelect() {
      return isSelect;
    }

    public void setSelect(boolean select) {
      isSelect = select;
    }
  }
}
