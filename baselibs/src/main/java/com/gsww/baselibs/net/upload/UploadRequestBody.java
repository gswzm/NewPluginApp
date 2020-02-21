package com.gsww.baselibs.net.upload;

import android.os.Handler;
import android.os.Looper;


import com.gsww.baselibs.net.netlistener.FileCallBackLis;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class UploadRequestBody extends RequestBody {

    private RequestBody requestBody;
    private FileCallBackLis callBackLis;
    private BufferedSink bufferedSink;

    public UploadRequestBody(File file, FileCallBackLis callBackLis) {
        this.requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        this.callBackLis = callBackLis;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (sink instanceof Buffer) {
            requestBody.writeTo(sink);
            return;
        }
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(sink(sink));
        }
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {

            long bytesWritten = 0L;
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);

                if (contentLength == 0L) {
                    contentLength = contentLength();
                }

                bytesWritten += byteCount;

                final int progress = (int) (bytesWritten * 100 / contentLength);

                if (callBackLis != null) {
                    //回调分发到主线程
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callBackLis.onProgress(progress);
                        }
                    });
                }
            }
        };
    }
}
