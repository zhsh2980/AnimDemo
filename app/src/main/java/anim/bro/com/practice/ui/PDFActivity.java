package anim.bro.com.practice.ui;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import anim.bro.com.practice.R;

public class PDFActivity extends BaseActivity {

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
//        pdfView.fromUri(Uri);
        pdfView.fromAsset(SAMPLE_FILE)
                .defaultPage(0)
//                .onPageChange(this)
                .enableAnnotationRendering(true)
//                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
//                .onPageError(this)
//                .pageFitPolicy(FitPolicy.BOTH)
                .load();
    }

}