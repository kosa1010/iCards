package com.example.kosa1010.icards.logic;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

public class GetGoogleInf extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... arg0) {
        String webPage = "";
        String url = "http://picasaweb.google.com/data/entry/api/user/" + arg0[0];
        Connection connect = Jsoup.connect(url).timeout(10 * 1000);
        try {
            webPage = getWebPageSource(url);
        } catch (IOException e) {
            System.err.println("zepsuło się");
        }
        return webPage;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    /**
     * Metoda pobiera żródło strony internetowej
     *
     * @param sURL
     * @return
     * @throws IOException
     */
    private static String getWebPageSource(String sURL) throws IOException {
        URL url = new URL(sURL);
        URLConnection urlCon = url.openConnection();
        BufferedReader in;

        if (urlCon.getHeaderField("Content-Encoding") != null
                && urlCon.getHeaderField("Content-Encoding").equals("gzip")) {
            in = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    urlCon.getInputStream())));
        } else {
            in = new BufferedReader(new InputStreamReader(
                    urlCon.getInputStream()));
        }
        String inputLine;
        StringBuilder sb = new StringBuilder();

        while ((inputLine = in.readLine()) != null)
            sb.append(inputLine);
        in.close();
        return sb.toString();
    }
}