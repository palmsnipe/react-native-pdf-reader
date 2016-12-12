package com.palmsnipe.rnpdfreader;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class PdfReaderManager extends SimpleViewManager<PDFView> {
    public static final String REACT_CLASS = "PDFView";
    private ThemedReactContext ctx;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected PDFView createViewInstance(ThemedReactContext context) {
        ctx = context;

        return new PDFView(context, null);
    }

    @ReactProp(name = "url")
    public void fromUrl(final PDFView pdfView, String url) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Log.e("TAG", "download file fail: " + e.getLocalizedMessage());
                    WritableMap event = Arguments.createMap();
                    event.putString("failed", e.getLocalizedMessage());
                    ctx.getJSModule(RCTEventEmitter.class).receiveEvent(
                            pdfView.getId(),
                            "topChange",
                            event);
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    Log.v("PDFView", "response : " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        pdfView.fromStream(response.body().byteStream())
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .defaultPage(0)
                                .enableAnnotationRendering(false)
                                .password(null)
                                .scrollHandle(null)
                                .load();
                    }
                }
            });


        } catch (Exception ex) {
            Log.e("PDFView", "fromUri", ex);
        }
    }
}
