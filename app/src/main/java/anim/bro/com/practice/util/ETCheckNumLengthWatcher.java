package anim.bro.com.practice.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zhangshan on 2021/12/24 18:42
 * Description：EditText内容输入限制最大：小数点前七位，小数点后2位
 */
public class ETCheckNumLengthWatcher implements TextWatcher {
    private EditText editText;
    private Context context;

    public ETCheckNumLengthWatcher(EditText editText, Context context) {
        this.editText = editText;
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        checkNumLength(editable, editText);
    }

    /**
     * 金额输入框中的内容限制（最大：小数点前五位，小数点后2位）
     *
     * @param edt
     */
    public void checkNumLength(Editable edt, EditText editText) {

        String temp = edt.toString();
        int posDot = temp.indexOf(".");//返回指定字符在此字符串中第一次出现处的索引
        int index = editText.getSelectionStart();//获取光标位置
        //不包含小数点
        if (posDot < 0) {
            if (temp.length() > 7) {
                Toast.makeText(context, "小数点前不能超过 7 位", Toast.LENGTH_LONG).show();
                edt.delete(index - 1, index);//删除光标前的字符
            }
            return;//小于七位数直接返回
        }
        if (posDot > 7) {//小数点前大于7位数就删除光标前一位
            edt.delete(index - 1, index);//删除光标前的字符
            Toast.makeText(context, "小数点前不能超过 7 位", Toast.LENGTH_LONG).show();
            return;
        }
        if (temp.length() - posDot - 1 > 2)//如果包含小数点
        {
            edt.delete(index - 1, index);//删除光标前的字符
            Toast.makeText(context, "小数点后不能超过 2 位", Toast.LENGTH_LONG).show();
            return;
        }
    }
}