package com.example.kosa1010.icards;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kosa1010.icards.model.Card;
import com.example.kosa1010.icards.repository.OrmCardsRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class ShowCardDetailsActivity extends AppCompatActivity {

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        id = Long.parseLong(getIntent().getStringExtra("CardId"));
        Card card = OrmCardsRepository.findById(this, id);
        setTitle("Karta " + card.getName());
        TextView cardNumber = (TextView) findViewById(R.id.tvCodeNumber);
        TextView cardNote = (TextView) findViewById(R.id.tvNote);
        cardNumber.setText(card.getCode());
        cardNote.setText(card.getNote());
        ImageView im = (ImageView) findViewById(R.id.QRcode);
        try {
            im.setImageBitmap(encodeAsBitmap(card.getCode(), card.getType()));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    Bitmap encodeAsBitmap(String str, String format) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.valueOf(format), 320, 320, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 320, 0, 0, w, h);
        return bitmap;
    }
}
