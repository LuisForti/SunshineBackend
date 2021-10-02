package br.unicamp.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btnHit;
    Button btnFormatar;
    public static TextView txtJson;
    TextView txtFormatado;
    String resultado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHit = (Button) findViewById(R.id.btnHit);
        btnFormatar = (Button) findViewById(R.id.btnFormatar);
        txtJson = (TextView) findViewById(R.id.tvJsonItem);
        txtFormatado = (TextView) findViewById(R.id.txtFormatado);

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData process = new FetchData();
                process.execute("https://power.larc.nasa.gov/api/temporal/hourly/point?start=20200102&end=20200102&latitude=50.7399&longitude=10.5904&community=sb&parameters=DIRECT_ILLUMINANCE");
            }
        });

        btnFormatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultado = txtJson.getText().toString();

                int posicaoInicial = resultado.indexOf("DIRECT_ILLUMINANCE") + 21;
                int posicaoFinal = resultado.indexOf("}}}");
                String textoSemiFormatado = resultado.substring(posicaoInicial, posicaoFinal);

                int qualCaracter = 1;
                String textoFormatado = "";
                while (qualCaracter < textoSemiFormatado.length())
                {
                    textoFormatado += textoSemiFormatado.substring(qualCaracter+4, qualCaracter + 6);
                    textoFormatado += "/";
                    textoFormatado += textoSemiFormatado.substring(qualCaracter+6, qualCaracter + 8);
                    textoFormatado += "/";
                    textoFormatado += textoSemiFormatado.substring(qualCaracter, qualCaracter + 4);
                    textoFormatado += "  ";
                    textoFormatado += textoSemiFormatado.substring(qualCaracter+8, qualCaracter + 10);
                    textoFormatado += ": ";
                    qualCaracter += 10;

                    qualCaracter = textoSemiFormatado.length();
                }

                txtFormatado.setText(textoFormatado);
            }
        });
    }


}

