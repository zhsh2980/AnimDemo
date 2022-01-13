package anim.bro.com.practice.ui;

import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.Gson;
import com.hjq.gson.factory.GsonFactory;

import anim.bro.com.practice.R;
import anim.bro.com.practice.bean.AgileBodyPromotionInfoModel;
import anim.bro.com.practice.network.GenericsCallback;
import anim.bro.com.practice.network.JsonGenericsSerializator;
import anim.bro.com.practice.network.OKhttpUtil;
import anim.bro.com.practice.tuibida.entity.User;
import anim.bro.com.practice.util.GetJsonDataUtil;

public class GsonParseActivity extends BaseActivity implements View.OnClickListener {

    private Button btnNetResponse;
    private TextView tvNetResponse;

    @Override
    public int getResourceID() {
        return R.layout.activity_gson_parse;
    }

    @Override
    public String getTitleName() {
        return "网络数据解析";
    }

    @Override
    public void initView() {
        btnNetResponse = (Button) findViewById(R.id.btn_net_response);
        btnNetResponse.setOnClickListener(this);
        tvNetResponse = (TextView) findViewById(R.id.tv_net_response);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_net_response:
                String beanJson = new GetJsonDataUtil().getJson(this, "flipper.json");//获取assets目录下的json文件数据
//                Gson gson = GsonFactory.getSingletonGson();
//                AgileBodyPromotionInfoModel bannerBean = gson.fromJson(beanJson, AgileBodyPromotionInfoModel.class);
                OKhttpUtil.getInstance().execute(beanJson, new GenericsCallback<AgileBodyPromotionInfoModel>() {
                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onResponse(AgileBodyPromotionInfoModel response) {
                        tvNetResponse.setText(response.toString() + "");
                    }
                });
                break;
            default:
        }
    }
}