package br.unicamp.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button btnHit;
    public static Button btnAbrir;
    public static TextView txtJson;
    public static TextView txtCoord;
    WebView webView;
    String ret[][];

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
                process.execute("https://power.larc.nasa.gov/api/temporal/daily/point?latitude=50.779734&longitude=11.510128&community=sb&parameters=DIRECT_ILLUMINANCE&start=20110104&end=20110112");
            }
        });

        btnAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtJson.getText().toString().length() != 0) {
                    String jSon = txtJson.getText().toString();
                    int quantosDados = 0;
                    while (!jSon.equals("")) {
                        quantosDados++;
                        jSon = jSon.substring(jSon.indexOf('\n') + 1);
                    }

                    ret = new String[quantosDados][2];
                    jSon = txtJson.getText().toString();
                    int posicaoAtual = 0;

                    while (!jSon.equals("")) {
                        String data = jSon.substring(0, jSon.indexOf(":"));
                        String luminosidade = jSon.substring(jSon.indexOf(":") + 1, jSon.indexOf('\n'));
                        jSon = jSon.substring(jSon.indexOf('\n') + 1);
                        ret[posicaoAtual][0] = data;
                        ret[posicaoAtual][1] = luminosidade;
                        posicaoAtual++;
                    }

                    webView.addJavascriptInterface(new WebAppInterface(), "Android");

                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl("file:///android_asset/chart.html");

                    txtJson.setText("");
                }
            }
        });
    }
    public class WebAppInterface {

        @JavascriptInterface
        public String getCoord() {
            return txtCoord.getText().toString();
        }

        @JavascriptInterface
        public int getQuantidade() {
            return ret.length;
        }

        @JavascriptInterface
        public String getDados(int numeroString, int numeroValor) {
            return ret[numeroString][numeroValor];
        }
    }
}