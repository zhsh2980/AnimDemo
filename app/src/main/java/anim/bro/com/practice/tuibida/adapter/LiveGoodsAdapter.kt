package anim.bro.com.practice.tuibida.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import anim.bro.com.practice.R
import anim.bro.com.practice.tuibida.entity.LiveGoodsAnchorEntity
import anim.bro.com.practice.tuibida.utils.HideShopAnimUtil
import bro.tuibida.com.utils.click
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import kotlinx.android.synthetic.main.activity_home_live_goods_item.view.*


/**
 * 直播带货
 */
class LiveGoodsAdapter(context: Context, var room_id: String, var anchor_id: String, var isAnchorOrAudience: Boolean, var mShopAnimUtil: HideShopAnimUtil)
    : RecyclerArrayAdapter<LiveGoodsAnchorEntity.LiveGoodsAnchorListBean>(context) {
    var style = ""
    private var recyclerView: RecyclerView? = null

    override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<*> {
        return ViewHolder(parent)
    }

    inner class ViewHolder(parent: ViewGroup?) : BaseViewHolder<LiveGoodsAnchorEntity.LiveGoodsAnchorListBean>(parent, R.layout.activity_home_live_goods_item) {
        override fun setData(data: LiveGoodsAnchorEntity.LiveGoodsAnchorListBean) {
            if (data == null || context == null || itemView == null) return
//                val transform = RoundedCornersTransformation(UIUtils.dip2px(3f), 0, RoundedCornersTransformation.CornerType.ALL)
//                Glide.with(context)
//                        .load(data.imageUrl).transform(CenterCrop(), transform).into(itemView.iv_live_goods_pic)
            itemView.tv_num_left_top.text = data.sequence + ""
            itemView.tv_live_goods_title.text = data.goodsName + " " + data.goodsDesc
            itemView.tv_live_goods_price_now.text = "价格:¥" + data.currentPrice
            itemView.tv_live_goods_price_origin.text = "原价:¥" + data.originalPrice
            //删除线
            itemView.tv_live_goods_price_origin.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.tv_live_goods_price_origin.paint.isAntiAlias = true
            if (TextUtils.isEmpty(data.originalPrice)) {
                itemView.tv_live_goods_price_origin.visibility = View.GONE
            } else {
                itemView.tv_live_goods_price_origin.visibility = View.VISIBLE
            }
            itemView.click {
                //主播点击商品链接不跳转
                if (!isAnchorOrAudience) {
                }
            }
            //推荐按钮状态
            if (data.isRecommend) {
                setBtnRecommended(itemView)
            } else {
                setBtnUnRecommended(itemView)
            }
            //置顶按钮状态
            if (data.isTop) {
                setBtnMoveTop(itemView)
            } else {
                setBtnUnMoveTop(itemView)
            }
            //主播才可以置顶和推荐
            if (isAnchorOrAudience) {
                itemView.tv_live_goods_move_top.click {
                    if (data.isTop) {
                        //取消置顶
                        doCancelMoveTop(adapterPosition)
                    } else {
                        //置顶
                        doMoveTop(adapterPosition)
                    }
                }
                itemView.tv_live_goods_recommend.click {
                    //                        LogUtil.info("点了推荐")
                    if (data.isRecommend) {
                        //取消推荐
                        doCancelRecommend(adapterPosition)
                    } else {
                        //推荐
                        doRecommend(adapterPosition)
                    }
                }
            }
        }
    }

    /**
     * 供外界调用
     * 置顶
     */
    fun doMoveTopById(goodsLink: String) {
        //根据 id 找到 item
        for (index in allData.indices) {
            if (TextUtils.equals(allData[index].goodsLink, goodsLink)) {
                //如果已经置顶了则return
                if (allData[index].isTop) {
                    break
                } else {
                    doMoveTop(index)
                }
            }
        }
    }
    /**
     * 供外界调用
     * 取消置顶
     */
    fun doCancelMoveTopById(goodsLink: String) {
        //根据 id 找到 item
        for (index in allData.indices) {
            if (TextUtils.equals(allData[index].goodsLink, goodsLink)) {
                //如果没有置顶了return
                if (!allData[index].isTop) {
                    break
                } else {
                    doCancelMoveTop(index)
                }
            }
        }
    }
    /**
     * 供外界调用
     * 推荐
     */
    fun doRecommendById(goodsLink: String) {
        //根据 id 找到 item
        for (index in allData.indices) {
            if (TextUtils.equals(allData[index].goodsLink, goodsLink)) {
                //如果没有置顶了return
                if (allData[index].isRecommend) {
                    break
                } else {
                    doRecommend(index)
                }
            }
        }
    }
    /**
     * 供外界调用
     * 取消推荐
     */
    fun doCancelRecommendById(goodsLink: String) {
        //根据 id 找到 item
        for (index in allData.indices) {
            if (TextUtils.equals(allData[index].goodsLink, goodsLink)) {
                //如果没有置顶了return
                if (!allData[index].isRecommend) {
                    break
                } else {
                    doCancelRecommend(index)
                }
            }
        }
    }
    /**
     * 供内部调用
     * 置顶
     */
    private fun doMoveTop(position: Int) {
        if (position >= count || position < 0) return
        var data = allData[position]
        var tempHolder = recyclerView?.findViewHolderForAdapterPosition(position)
        if (tempHolder == null) {
            notifyItemChanged(position)
        } else {
            //1. 改变按钮状态
            var dataCopy: LiveGoodsAnchorEntity.LiveGoodsAnchorListBean = data.clone() as LiveGoodsAnchorEntity.LiveGoodsAnchorListBean
            data.isTop = true
            dataCopy.isTop = true
            //2. 插入一条 data 到第一个条目
            addItem(0, dataCopy)
            //滑到第一位
            mRecyclerView.scrollToPosition(0)
        }
    }

    /**
     * 供内部调用
     * 取消置顶
     */
    private fun doCancelMoveTop(position: Int) {
        if (position >= count || position < 0) return
        var data = allData[position]
        var tempHolder = recyclerView?.findViewHolderForAdapterPosition(position)
        if (tempHolder == null) {
            notifyItemChanged(position)
        } else {
            //1. 如果上面有相同条目,则删除该条目
            if (checkHasMoveTop(data.goodsLink, position)) {
                //改变当前按钮的状态为未置顶
                setBtnUnMoveTop(tempHolder.itemView)
                data.isTop = false
            }
            //2. 如果上面没有相同条目,则改变当前条目按钮状态
            else {
                //1. 改变列表中相同的置顶按钮状态
                notifyButtonMoveTop(data.goodsLink, false)
                //2. 删除 item
                removeData(position)
            }
        }
    }
    /**
     * 供内部调用
     * 推荐
     */
    private fun doRecommend(position: Int) {
        if (position >= count || position < 0) return
        var data = allData[position]
        var tempHolder = recyclerView?.findViewHolderForAdapterPosition(position)
        if (tempHolder == null) {
            notifyItemChanged(position)
        } else {
            //只能推荐一个,查看列表中有推荐的就不会再推荐
            //先取消其他推荐商品
            cancelOtherRecommend(tempHolder.itemView.tv_live_goods_recommend)
            setBtnRecommended(tempHolder.itemView)
            data.isRecommend = true
            //1. 改变列表中相同的推荐按钮状态
            notifyButtonRecommend(data.goodsLink, true, tempHolder.itemView.tv_live_goods_recommend)
            //2. 显示右上角的推荐 ImageView
            mShopAnimUtil.showShopView()
        }
    }

    /**
     * 供内部调用
     * 取消推荐
     */
    private fun doCancelRecommend(position: Int) {
        if (position >= count || position < 0) return
        var data = allData[position]
        var tempHolder = recyclerView?.findViewHolderForAdapterPosition(position)
        if (tempHolder == null) {
            notifyItemChanged(position)
        } else {
            setBtnUnRecommended(tempHolder.itemView)
            data.isRecommend = false
            //1. 改变列表中相同的推荐按钮状态
            notifyButtonRecommend(data.goodsLink, false, tempHolder.itemView.tv_live_goods_recommend)
            //2. 隐藏右上角的推荐 ImageView
            mShopAnimUtil.hideShopView()
        }
    }

    /**
     * 检查 position 以上有无置顶
     */
    private fun checkHasMoveTop(goodsLink: String, position: Int): Boolean {
        if (position >= allData.size) return false
        for (i in 0 until position) {
            if (TextUtils.equals(allData[i].goodsLink, goodsLink)) {
                removeData(i)
                return true
            }
        }
        return false
    }


    //推荐按钮从已推荐变为未推荐
    private fun setBtnUnRecommended(itemView: View) {
        itemView.tv_live_goods_recommend.setBackgroundResource(R.drawable.shape_live_goods_recommend_enable)
        itemView.tv_live_goods_recommend.setTextColor(Color.parseColor("#FFFFFF"))
    }

    //推荐按钮从未推荐变为已推荐
    private fun setBtnRecommended(itemView: View) {
        itemView.tv_live_goods_recommend.setBackgroundResource(R.drawable.shape_live_goods_recommend_unnable)
        itemView.tv_live_goods_recommend.setTextColor(Color.parseColor("#131320"))
    }

    //置顶按钮变为未置顶
    private fun setBtnUnMoveTop(itemView: View) {
        itemView.tv_live_goods_move_top.setBackgroundResource(R.drawable.shape_live_goods_move_top_enable)
        itemView.tv_live_goods_move_top.setTextColor(Color.parseColor("#6C7B8A"))
    }

    //推荐按钮变为已置顶
    private fun setBtnMoveTop(itemView: View) {
        itemView.tv_live_goods_move_top.setBackgroundResource(R.drawable.shape_live_goods_move_top_unnable)
        itemView.tv_live_goods_move_top.setTextColor(Color.parseColor("#666C7B8A"))
    }

    /**
     * 改变按钮状态
     * @param goodsLink 商品链接
     * @param toMoveTop 置顶? 还是取消置顶
     */
    private fun notifyButtonMoveTop(goodsLink: String, toMoveTop: Boolean) {
        for (data in allData) {
            if (TextUtils.equals(data.goodsLink, goodsLink) && data.isTop != toMoveTop) {
                data.isTop = toMoveTop
            }
        }
    }

    /**
     * 改变按钮状态
     * @param goodsLink 商品链接
     * @param toMoveTop 置顶? 还是取消置顶
     */
    private fun notifyButtonRecommend(goodsLink: String, toRecommend: Boolean, tvLiveGoodsRecommend: TextView) {
        for (index in allData.indices) {
            if (TextUtils.equals(allData[index].goodsLink, goodsLink)) {
                allData[index].isRecommend = toRecommend
                notifyPart(index, tvLiveGoodsRecommend)
            }
        }
    }


    private fun cancelOtherRecommend(tvLiveGoodsRecommend: TextView) {
        for (index in allData.indices) {
            if (allData[index].isRecommend) {
                allData[index].isRecommend = false
                notifyPart(index, tvLiveGoodsRecommend)
            }
        }
    }

    //删除 item
    private fun removeData(position: Int) {
        allData.removeAt(position)//删除数据源,移除集合中当前下标的数据
        notifyItemRemoved(position)//刷新被删除的地方
        notifyItemRangeChanged(position, count) //刷新被删除数据，以及其后面的数据
    }

    private fun notifyPart(position: Int, tvLiveGoodsRecommend: TextView) {
        notifyItemChanged(position, tvLiveGoodsRecommend)//刷新指定一个。不闪
    }

    //添加数据
    private fun addItem(position: Int, data: LiveGoodsAnchorEntity.LiveGoodsAnchorListBean) {
        allData.add(position, data)
        notifyItemInserted(position) //通知演示插入动画
        notifyItemRangeChanged(position, allData.size - position) //通知数据与界面重新绑定
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<*>) {
        super.onViewAttachedToWindow(holder)
        if (allData.size > holder.adapterPosition) {
            doOnViewEvent(allData[holder.adapterPosition])
        }
    }

    private fun doOnViewEvent(data: LiveGoodsAnchorEntity.LiveGoodsAnchorListBean) {
    }

}
