package anim.bro.com.practice.tuibida.multidrag;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import anim.bro.com.practice.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultiDragActivity extends AppCompatActivity implements ViewTypeAdapter.OnItemClickListener {

    private static final int MYLIVE_MODE_CHECK = 0;//取消状态(界面显示取消)
    private static final int MYLIVE_MODE_EDIT = 1;//编辑状态(界面显示编辑)
    private int mEditMode = MYLIVE_MODE_CHECK;
    private boolean editorStatus = false;
    private int index = 0;

    private final String TAG = "MultiDragActivity";

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_del)
    TextView mTvDel;
    @BindView(R.id.tv_edit_done)
    TextView mTvEditDone;
    private List<SelectBean> mDatas;
    private ViewTypeAdapter mTypeAdapter;
//    private boolean isEditOrDone = true;//默认是编辑

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_drag);
        ButterKnife.bind(this);
        initData();
        initRecy();
        initListener();
    }

    private void initListener() {
        mTypeAdapter.setOnItemClickListener(this);
    }

    private void initRecy() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(12));
        mTypeAdapter = new ViewTypeAdapter(this);
        mRecyclerView.setAdapter(mTypeAdapter);
        mTypeAdapter.setData(mDatas);

        RecyItemTouchHelperCallback itemTouchHelperCallback = new RecyItemTouchHelperCallback(mTypeAdapter);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
//                if (viewHolder instanceof ContentHolder) {
//                    ContentHolder viewHolder1 = (ContentHolder) viewHolder;
//                    String tvString = viewHolder1.tvContent.getText().toString();
//                    Toast.makeText(MultiDragActivity.this, "逗逗~" + tvString, Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                //选种删除时,不能排序
                if (mEditMode == MYLIVE_MODE_EDIT) {
                    Toast.makeText(MultiDragActivity.this, "编辑时不能排序", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MultiDragActivity.this, "长按了", Toast.LENGTH_SHORT).show();
                if (viewHolder instanceof ContentHolder) {
                    ContentHolder viewHolder1 = (ContentHolder) viewHolder;
                    //振动
//                    Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
//                    vib.vibrate(70);
                    //开始拖曳这个 item
                    itemTouchHelper.startDrag(viewHolder);
                }
            }
        });
    }

    private void initData() {
        mDatas = new ArrayList<SelectBean>();

        SelectBean selectBeanTitle = new SelectBean("我是标题1");
        selectBeanTitle.setType(SelectBean.TYPE_TITLE);
        mDatas.add(selectBeanTitle);

        SelectBean.ContentBean bean = new SelectBean.ContentBean("我是当前任务");
        SelectBean selectBeanContent = new SelectBean();
        selectBeanContent.setContentBean(bean);
        selectBeanContent.setType(SelectBean.TYPE_CONTENT);
        mDatas.add(selectBeanContent);

        SelectBean selectBeanTitle2 = new SelectBean("我是标题2");
        selectBeanTitle2.setType(SelectBean.TYPE_TITLE);
        mDatas.add(selectBeanTitle2);

        for (int i = 0; i < 24; i++) {
            SelectBean.ContentBean contentBeanContent2 = new SelectBean.ContentBean("我是正文" + i);
            SelectBean selectBean2 = new SelectBean();
            selectBean2.setContentBean(contentBeanContent2);
            selectBean2.setType(SelectBean.TYPE_CONTENT);
            mDatas.add(selectBean2);
        }
    }


    @OnClick(R.id.tv_del)
    public void onViewClicked() {

    }

    @OnClick({R.id.tv_edit_done, R.id.tv_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_edit_done:
                updataEditMode();
                break;
            case R.id.tv_del:
                deleteItem();
                break;
        }
    }
    //二次弹窗
    private void showSecondDialog() {
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = builder.findViewById(R.id.tv_msg);
        Button cancle = builder.findViewById(R.id.btn_cancle);
        Button sure = builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;
        msg.setText("真的确定要删除吗?");
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MultiDragActivity.this, "删除完毕", Toast.LENGTH_SHORT).show();

                boolean isSecondContentDel = false;
                ArrayList<SelectBean> mOldTempList = new ArrayList<>(mTypeAdapter.getDataList());
                for (int i = mTypeAdapter.getDataList().size(), j = 0; i > j; i--) {
                    SelectBean selectBean = mTypeAdapter.getDataList().get(i - 1);
                    if (selectBean.getType() == SelectBean.TYPE_CONTENT) {
                        if (selectBean.getContentBean().isSelect()) {
                            Log.i(TAG, i + "");
                            if (i == 2) {
                                isSecondContentDel = true;
                            }
                            mTypeAdapter.getDataList().remove(selectBean);
                            index--;
                        }
                    }
                }
                index = 0;
                setBtnBackground(index);

                //如果第二个任务被删了,则把第四个换到上面去

                List<SelectBean> list = mTypeAdapter.getDataList();
                if (list.size() >= 3) {
                    if (isSecondContentDel) {
                        Collections.swap(list, 1, 2);
                    }
                } else {
                    //没有任务了,显示空布局
                    showEmpytUI();
                }
                setRefreshDiffUtil(mOldTempList, mTypeAdapter.getDataList());
//                mTypeAdapter.notifyDataSetChanged();
                builder.dismiss();
            }
        });
    }

    /**
     * 点击删除
     */
    private void deleteItem() {
        if (index == 0) {
            mTvDel.setEnabled(false);
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = builder.findViewById(R.id.tv_msg);
        Button cancle = builder.findViewById(R.id.btn_cancle);
        Button sure = builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;

        if (index == 1) {
            msg.setText("删除后不可恢复，是否删除该条目？");
        } else {
            msg.setText("删除后不可恢复，是否删除这" + index + "个条目？");
        }
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                showSecondDialog();

            }
        });

    }

    private void setRefreshDiffUtil(final ArrayList<SelectBean> oldTemp, final List<SelectBean> datas) {

        // 实现 Callback
        DiffUtil.Callback callback = new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldTemp.size();
            }

            @Override
            public int getNewListSize() {
                return datas.size();
            }

            // 判断是否是同一个 item
            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldTemp.get(oldItemPosition).equals(datas.get(newItemPosition));
            }

            // 如果是同一个 item 判断内容是否相同
            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldTemp.get(oldItemPosition).equals(datas.get(newItemPosition));
            }
        };
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
        // 把结果应用到 adapter
        diffResult.dispatchUpdatesTo(mTypeAdapter);
        // 通知刷新了之后，要更新副本数据到最新
        oldTemp.clear();
        oldTemp.addAll(datas);

    }

    /**
     * 显示空布局
     */
    private void showEmpytUI() {


    }

    /**
     * 点击编辑
     */
    private void updataEditMode() {
        mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {
            mTvEditDone.setText("取消");
            mTvDel.setVisibility(View.VISIBLE);
//            mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);
            editorStatus = true;
        } else {
            mTvEditDone.setText("编辑");
            mTvDel.setVisibility(View.INVISIBLE);
//            mLlMycollectionBottomDialog.setVisibility(View.GONE);
            editorStatus = false;
            clearAll();
        }
        mTypeAdapter.setEditMode(mEditMode);

    }

    private void clearAll() {
        //取消所有选中
        selectNone();

        setBtnBackground(0);
    }


    /**
     * 根据选择的数量是否为0来判断按钮的是否可点击.
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        if (size != 0) {
//            mTvDel.setBackgroundResource(R.drawable.button_shape);
            mTvDel.setEnabled(true);
            mTvDel.setTextColor(ContextCompat.getColor(this, R.color.jumei_red));
        } else {
//            mTvDel.setBackgroundResource(R.drawable.button_noclickable_shape);
            mTvDel.setEnabled(false);
            mTvDel.setTextColor(ContextCompat.getColor(this, R.color.color_40000000));
        }
    }

    @Override
    public void onItemClickListener(int pos, List<SelectBean> mDatas) {
        if (editorStatus) {
            SelectBean selectBean = mDatas.get(pos);
            if (selectBean.getContentBean() == null) return;
            boolean isSelect = selectBean.getContentBean().isSelect();
            if (!isSelect) {
                index++;
                selectBean.getContentBean().setSelect(true);
//                if (index == mDatas.size()) {
//                    isSelectAll = true;
//                    mSelectAll.setText("取消全选");
//                }
            } else {
                selectBean.getContentBean().setSelect(false);
                index--;
//                isSelectAll = false;
//                mSelectAll.setText("全选");
            }
            setBtnBackground(index);
//            mTvSelectNum.setText(String.valueOf(index));
            mTypeAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 取消所有选中
     */
    private void selectNone() {
        if (mTypeAdapter == null) return;
        for (int i = 0, j = mTypeAdapter.getDataList().size(); i < j; i++) {
            if (mTypeAdapter.getDataList().get(i).getContentBean() == null) {
                continue;
            }
            mTypeAdapter.getDataList().get(i).getContentBean().setSelect(false);
        }
        index = 0;
        mTypeAdapter.notifyDataSetChanged();
        setBtnBackground(index);
    }

}
