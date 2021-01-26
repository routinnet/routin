package ir.karcook.ViewPresenter;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import ir.karcook.R;
import ir.karcook.Tools.G;
import ir.karcook.databinding.WebViewActivityBinding;

public class WebViewActivity extends AppCompatActivity {

    WebViewActivityBinding binding;
    boolean activityDestroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.web_view_activity);
        activityDestroyed = false;

        String url = getIntent().getExtras().getString("url");
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        G.getInstance().hideMainProgress(WebViewActivity.this);
                    }
                }, 1500);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                G.getInstance().errorMainProgress(WebViewActivity.this);
            }
        });

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.loadUrl(url);

        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (G.getInstance().isNetworkAvailable(getApplicationContext())) {
                    G.getInstance().showMainProgress(WebViewActivity.this);
                    binding.webView.reload();
                } else {
                    G.getInstance().customSnackBar(getApplicationContext(), binding.mainLayout, getString(R.string.netWorkError));

                }
            }
        });

        binding.toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (binding.webView.canGoBack())
            binding.webView.goBack();
        else
            super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityDestroyed = true;
    }
}