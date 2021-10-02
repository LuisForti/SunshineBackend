package br.unicamp.sunshine;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class FetchData extends AsyncTask<String, Void, Void> {

    String resultado;
    String textoFormatado = "";

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";

            while (line != null)
            {
                line = bufferedReader.readLine();
                resultado += line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        MainActivity.txtJson.setText(this.textoFormatado);
    }
}