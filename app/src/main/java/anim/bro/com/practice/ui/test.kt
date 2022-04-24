package anim.bro.com.practice.ui
import com.google.gson.annotations.SerializedName


/**
 * Created by zhangshan on 2022/4/19 17:07.
 * Description：请添加类注释
 */
data class test(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("errorCode")
    var errorCode: Int?,
    @SerializedName("errorMsg")
    var errorMsg: String?
) {
    data class Data(
        @SerializedName("body")
        var body: Body?,
        @SerializedName("footer")
        var footer: Footer?,
        @SerializedName("header")
        var header: Header?
    ) {
        data class Body(
            @SerializedName("anim_interval")
            var animInterval: String?,
            @SerializedName("items")
            var items: List<Item?>?,
            @SerializedName("more_link")
            var moreLink: String?
        ) {
            data class Item(
                @SerializedName("items_inside")
                var itemsInside: List<ItemsInside?>?
            ) {
                data class ItemsInside(
                    @SerializedName("pic")
                    var pic: String?,
                    @SerializedName("pic_link")
                    var picLink: String?,
                    @SerializedName("title")
                    var title: String?
                )
            }
        }

        data class Footer(
            @SerializedName("bottom_link")
            var bottomLink: String?,
            @SerializedName("bottom_title")
            var bottomTitle: String?
        )

        data class Header(
            @SerializedName("background_pic_small")
            var backgroundPicSmall: String?,
            @SerializedName("main_title")
            var mainTitle: String?,
            @SerializedName("main_title_link")
            var mainTitleLink: String?,
            @SerializedName("main_title_pic")
            var mainTitlePic: String?,
            @SerializedName("show_pic_only")
            var showPicOnly: Boolean?,
            @SerializedName("subtitle")
            var subtitle: String?,
            @SerializedName("subtitle_link")
            var subtitleLink: String?,
            @SerializedName("subtitle_pic")
            var subtitlePic: String?
        )
    }
}