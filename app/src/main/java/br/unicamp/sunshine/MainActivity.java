package br.unicamp.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnHit;
    Button btnAbrir;
    public static TextView txtJson;
    public static TextView txtCoord;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHit = (Button) findViewById(R.id.btnHit);
        btnAbrir = (Button) findViewById(R.id.btnAbrir);
        txtJson = (TextView) findViewById(R.id.tvJsonItem);
        txtCoord = (TextView) findViewById(R.id.tvCoord);
        webView = (WebView) findViewById(R.id.webGrafico);

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData process = new FetchData();
                process.execute("https://power.larc.nasa.gov/api/temporal/hourly/point?latitude=50.779734&longitude=11.510128&community=sb&parameters=DIRECT_ILLUMINANCE&start=20110101&end=20110101");
            }
        });

        btnAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebAppInterface teste = new WebAppInterface();
                teste.getDados();

                webView.addJavascriptInterface(new WebAppInterface(), "Android");

                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("file:///android_asset/chart.html");
            }
        });
    }
    public class WebAppInterface {

        @JavascriptInterface
        public String getCoord() {
            return txtCoord.getText().toString();
        }

        @JavascriptInterface
        public Array[] getDados() {
            String jSon = txtJson.getText().toString();
            int quantosDados = 0;
            while (jSon != "")
            {
                quantosDados++;
                jSon = jSon.substring(jSon.indexOf('\n')+1);
            }

            ArrayList[] ret = new ArrayList[quantosDados];
            jSon = txtJson.getText().toString();
            int posicaoAtual = 0;

            while (jSon != "")
            {
                String data = jSon.substring(0, jSon.indexOf(":"));
                Integer luminosidade = Integer.parseInt(jSon.substring(jSon.indexOf(":")+1, jSon.indexOf('\n')));
                jSon = jSon.substring(jSon.indexOf('\n'));
                ret[posicaoAtual] = [data, luminosidade];
                posicaoAtual++;
            }
            return ret;
        }
    }
}

