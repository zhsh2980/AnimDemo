package anim.bro.com.practice.ui;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import anim.bro.com.practice.R;
import anim.bro.com.practice.bean.AgileBodyPromotionInfoModel;
import anim.bro.com.practice.network.GenericsCallback;
import anim.bro.com.practice.network.OKhttpUtil;
import anim.bro.com.practice.util.GetJsonDataUtil;

public class GsonParseActivity extends BaseActivity implements View.OnClickListener {

    private Button btnNetResponse;
    private Button btnJsonParse;
    private TextView tvNetResponse;
    private final String TAG = "GsonParseActivity";

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
        btnJsonParse = (Button) findViewById(R.id.btn_json_parse);
        btnJsonParse.setOnClickListener(this);
        tvNetResponse = (TextView) findViewById(R.id.tv_net_response);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_json_parse:
                parseTestJson();
                break;
            case R.id.btn_net_response:
                String beanJson = new GetJsonDataUtil().getJson(this, "flipper.json");//获取assets目录下的json文件数据
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

    private void parseTestJson() {
        try {
            String beanJson = new GetJsonDataUtil().getJson(this, "json_error_test.json");//获取assets目录下的json文件数据
            JSONObject jsonObject = new JSONObject(beanJson);

            int int_test = jsonObject.optInt("int_test");
            Log.i(TAG, "int_test: " + int_test);

            JSONObject priceJson = jsonObject.optJSONObject("object_test");
            if (!isNullJSONObject(priceJson)) {
                String price = priceJson.optString("price");
                Log.i(TAG, "price: " + price);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "JSONException: " + e.getMessage());
        }

    }

    private boolean isNullJSONObject(JSONObject jsonObject) {
        return jsonObject == null || JSONObject.NULL.equals(jsonObject);
    }

}