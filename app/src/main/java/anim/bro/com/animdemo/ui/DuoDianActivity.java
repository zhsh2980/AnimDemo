package anim.bro.com.animdemo.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.text.SimpleDateFormat;

import anim.bro.com.animdemo.R;
import butterknife.BindView;
import butterknife.OnClick;

public class DuoDianActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView textView;
    AlertDialog.Builder builder;

    @Override
    public int getResourceID() {
        return R.layout.activity_duo_dian;
    }

    @Override
    public String getTitleName() {
        return "多点";
    }

    @Override
    public void initView() {
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void initData() {
    }

    @OnClick(R.id.btn_order)
    public void onViewClicked() {
//        ToastUtils.showShort(getCurrentTime());
        openDialog();
        textView.append("开始抢购: " + getCurrentTime() + "\n");
    }

    private String getCurrentTime() {
        return TimeUtils.getNowString(new SimpleDateFormat("HH:mm:ss SSS"));
    }

    private void openDialog() {
        if (builder == null) {
            builder = new AlertDialog.Builder(this);
//            builder.setTitle("请回答");
            builder.setMessage("网络繁忙");
            builder.setCancelable(false);
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(this, "嘻嘻嘻",Toast.LENGTH_SHORT).show();
                }
            });
//            builder.setNegativeButton("我觉得不好看", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
////                    Toast.makeText(AlertDialogActivity.this, "嘤嘤嘤", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
        builder.show();
    }


}
