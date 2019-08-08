package com.zxc.zhanglj.main_lib.fragment.tabfragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxc.zhanglj.common.base.baseui.BaseFragment;
import com.zxc.zhanglj.common.base.baseui.BasePresenter;
import com.zxc.zhanglj.common.widget.DBHelp;
import com.zxc.zhanglj.common.widget.explosion.BooleanFactory;
import com.zxc.zhanglj.common.widget.explosion.ClickCallback;
import com.zxc.zhanglj.common.widget.explosion.ExplosionField;
import com.zxc.zhanglj.main_lib.R;
import com.zxc.zhanglj.main_lib.fragment.activity.SlidingSimpleActivity;
import com.zxc.zhanglj.utils.LogUtil;
import com.zxc.zhanglj.utils.ToastUtils;

/**
 * Author:ZLJ
 * Date: 2019/1/24 18:24
 */
public class FriendsFragment extends BaseFragment {
    private static final String TAG = "FriendsFragment";

    public static FriendsFragment newInstance() {
        Bundle args = new Bundle();

        FriendsFragment fragment = new FriendsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_common;
    }

    ImageView img_ll;
    TextView textView;
    private DBHelp dbHelp;
    private SQLiteDatabase writableDatabase;

    @Override
    protected void initView(Bundle savedInstanceState) {
        img_ll = mBaseRootView.findViewById(R.id.img_ll);
        textView = mBaseRootView.findViewById(R.id.text_view);
        textView.setText(TAG);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(getContext(), "vv点击");
                if (dbHelp == null) {
                    dbHelp = new DBHelp(getContext(), DBHelp.DB_NAME, null, DBHelp.DB_VERSION);
                }
                if (writableDatabase == null) {
                    writableDatabase = dbHelp.getWritableDatabase();
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "yuyu");
                contentValues.put("tel_no", "9898000");
                contentValues.put("cls_id", 66);
                contentValues.put("address", "北京市朝阳");

                writableDatabase.insert(DBHelp.TABLE_STUDENT, null, contentValues);

                updateStudents();
                deleteStudents();
                queryStudents();
            }
        });
        //initExplosion();
    }

    private void updateStudents() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "hghg");
        writableDatabase.update(DBHelp.TABLE_STUDENT,
                contentValues, "tel_no = ?", new String[]{"12345678900"});
    }

    private void deleteStudents(){

        writableDatabase.delete(DBHelp.TABLE_STUDENT, "tel_no = ?", new String[]{"12345678900"});
    }

    private void queryStudents() {

        // 相当于 select * from students 语句
        Cursor cursor = writableDatabase.query(DBHelp.TABLE_STUDENT, null,
                "cls_id > ? and id >= 1", new String[]{"3"},
                null, null, null, null);

        // 不断移动光标获取值
        while (cursor.moveToNext()) {
            // 直接通过索引获取字段值
            int stuId = cursor.getInt(0);

            // 先获取 name 的索引值，然后再通过索引获取字段值
            String stuName = cursor.getString(cursor.getColumnIndex("name"));
            Log.e("ZLJ", "id: " + stuId + " name: " + stuName);
        }
        // 关闭光标
        cursor.close();
    }


    @Override
    public void onTvmClick(View v) {

    }


    private void initExplosion() {
        ExplosionField explosionField = new ExplosionField(this.getActivity(), new BooleanFactory());
        explosionField.setClickCallback(new ClickCallback() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(getContext(), "initExplosion v点击");
                startActivity(new Intent().setClass(getActivity(), SlidingSimpleActivity.class));
            }
        });
        explosionField.addListener(img_ll);
        explosionField.addListener(textView);

    }


    private void initFirstData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.i(TAG, "initFirstData");
                inittwoThread();
            }
        }).start();
    }


    private void inittwoThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.i(TAG, "inittwoThread");
            }
        }).start();
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected Object getPresenterView() {
        return null;
    }
}
