package anim.bro.com.practice.ui;


import android.os.Environment;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;

import java.io.File;

import anim.bro.com.practice.R;
import anim.bro.com.practice.util.DownloadUtil;

public class PDFActivity extends BaseActivity implements OnPageChangeListener, OnPageErrorListener {

    private PDFView pdfView;
    public static final String SAMPLE_FILE = "sample.pdf";
    private final String TAG = "PDFActivity";

    @Override
    public int getResourceID() {
        return R.layout.activity_pdf;
    }

    @Override
    public String getTitleName() {
        return "pdf 展示";
    }

    @Override
    public void initView() {
        pdfView = findViewById(R.id.pdfView);
    }

    @Override
    public void initData() {

        String mSDCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();//SD卡路径
//        String appPath= getApplicationContext().getFilesDir().getAbsolutePath();//此APP的files路径

//        String filePath = "https://bro-1257528710.cos.ap-beijing.myqcloud.com/gohome/Android_%E7%8E%8B%E5%9B%BD%E7%90%B3.pdf";
        String filePath = "https://bro-1257528710.cos.ap-beijing.myqcloud.com/gohome/%E6%80%A7%E8%83%BD%E6%B5%8B%E8%AF%95%E8%AF%BE%E7%A8%8B%E8%AE%B2%E4%B9%89V4-%E4%BF%AE%E6%94%B9.pdf";
        DownloadUtil.get().download(filePath, mSDCardPath + "/gome/pdf/", "1.pdf", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                pdfView.fromFile(file).load();
            }

            @Override
            public void onDownloading(int progress) {
                Log.i("bro", "下载进度: " + progress);
            }

            @Override
            public void onDownloadFailed(Exception e) {
                Log.i("bro", "pdf 下载失败");
            }
        });
        //        String filePath = "https://bro-1257528710.cos.ap-beijing.myqcloud.com/dangdang/3.pdf";
//        new RetrivePDFStream().execute(filePath);
//        String pdfUrl = "https://bro-1257528710.cos.ap-beijing.myqcloud.com/gohome/Android_%E7%8E%8B%E5%9B%BD%E7%90%B3.pdf";
//        String pdfUrl = "https://bro-1257528710.cos.ap-beijing.myqcloud.com/dangdang/3.pdf";
//        String pdfUrl = "https://github.com/barteksc/AndroidPdfViewer/blob/master/sample/src/main/assets/sample.pdf";
//        pdfView.fromUri(Uri.parse(pdfUrl))
////        pdfView.fromAsset(SAMPLE_FILE)
//                .defaultPage(0)
//                .onPageChange(this)
//                .enableAnnotationRendering(true)
////                .onLoad(this)
//                .scrollHandle(new DefaultScrollHandle(this))
//                .spacing(10) // in dp
//                .onPageError(this)
////                .pageFitPolicy(FitPolicy.BOTH)
//                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

        Log.i("bro", "page: " + page + "--- pageCount: " + pageCount);

    }

    @Override
    public void onPageError(int page, Throwable t) {
        Log.i("bro", "page: " + page + "--- Throwable t: " + t.getLocalizedMessage());
    }


}