package com.baidu.aip.http2;

import com.baidu.aip.http.Headers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CBFutureTask extends FutureTask<String> implements Callback {

    public String getRequestId() {
        return requestId;
    }

    private String requestId;

    private String result;

    private String protocol;

    public String getProtocol() {
        return protocol;
    }

    public CBFutureTask(String requestId) {
        super(new Callable<String>() {
            public String call() throws Exception {
                return null;
            }
        });
        this.requestId = requestId;
    }

    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        System.out.println("request failure" + e.getMessage());
        setException(e);
    }

    public void onResponse(@NotNull Call call, @NotNull Response response)  {
        try {
            String charset = call.request().header(Headers.CONTENT_ENCODING);
            protocol = response.protocol().toString();
            if (charset != null) {
                result = new String(response.body().bytes(), Charset.forName(charset));
            } else {
                result = new String(response.body().bytes());
            }
            response.close();
            set(result);
        } catch (IOException e) {
            setException(e);
        }
    }
}
