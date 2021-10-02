package br.unicamp.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnHit;
    Button btnFormatar;
    public static TextView txtJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHit = (Button) findViewById(R.id.btnHit);
        txtJson = (TextView) findViewById(R.id.tvJsonItem);

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData process = new FetchData();
                process.execute("https://power.larc.nasa.gov/api/temporal/hourly/point?start=20200102&end=20200102&latitude=50.7399&longitude=10.5904&community=sb&parameters=DIRECT_ILLUMINANCE");
            }
        });

        /*btnFormatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultado = txtJson.getText().toString();

                int posicaoInicial = resultado.indexOf("DIRECT_ILLUMINANCE") + 20;
                int posicaoFinal = resultado.indexOf("}}}") + 1;
                String textoSemiFormatado = resultado.substring(posicaoInicial, posicaoFinal);

                try {
                    String json = textoSemiFormatado;
                    JSONObject jObject = new JSONObject(json.trim());
                    Iterator<?> keys = jObject.keys();

                    String data = "";

                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Object objetoAtual = jObject.get(key);

                        data = key.substring(4, 6);
                        data += "/";
                        data += key.substring(6, 8);
                        data += "/";
                        data += key.substring(0, 4);

                        textoFormatado += data + ": " + objetoAtual + "\n";
                    }
                }
                catch (Exception err)
                {
                    System.out.println(err);
                }

                txtFormatado.setText(textoFormatado);
            }
        });*/
    }
}

