package anim.bro.com.practice.ui;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import anim.bro.com.practice.R;

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
        String filePath = "https://bro-1257528710.cos.ap-beijing.myqcloud.com/gohome/Android_%E7%8E%8B%E5%9B%BD%E7%90%B3.pdf";
//        String filePath = "https://bro-1257528710.cos.ap-beijing.myqcloud.com/dangdang/3.pdf";
        new RetrivePDFStream().execute(filePath);
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


    class RetrivePDFStream extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL uri = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                return null;
            }
            return inputStream;
        }
        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    }

}