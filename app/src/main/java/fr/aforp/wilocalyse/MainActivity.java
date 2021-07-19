package fr.aforp.wilocalyse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import fr.aforp.wilocalyse.navigation.MapActivity;

// TODO supprimer ces activités student/teacher/visitor !!!

public class MainActivity extends AppCompatActivity {

    private CardView imgBtTeacher;
    private CardView imgBtStudent;
    private CardView imgBtSpeaker;
    private CardView imgBtVisitor;
    private ImageView imgBtInfos;
    private ImageView imgBtLinkTuto;

    private TextView phoneLink;
    private TextView webLink;
    private TextView mailLink;
    private Texttospeechaudio audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imgBtTeacher = (CardView) findViewById(R.id.imgBtEmployee);
        this.imgBtStudent = (CardView) findViewById(R.id.imgBtppp);
        this.imgBtSpeaker = (CardView) findViewById(R.id.imgBtSpeaker);
        this.imgBtVisitor = (CardView) findViewById(R.id.imgBtVisitor);
        this.imgBtLinkTuto = (ImageView) findViewById(R.id.btLinkTuto);
        this.imgBtInfos = (ImageView) findViewById(R.id.btInfos);
        this.audio = new Texttospeechaudio(this);

        imgBtTeacher.setOnClickListener(view -> {
            audio.speakOut(getString(R.string.choose_profile) + " " + getString(R.string.visitor));
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        imgBtStudent.setOnClickListener(view -> {
            audio.speakOut(getString(R.string.choose_profile) + " " + getString(R.string.student));
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        imgBtSpeaker.setOnClickListener(view -> {
            audio.speakOut(getString(R.string.choose_profile) + " " + getString(R.string.speaker));
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        imgBtVisitor.setOnClickListener(view -> {
            audio.speakOut(getString(R.string.choose_profile) + " " + getString(R.string.employee));
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        imgBtLinkTuto.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://youtu.be/gqoZEHibjSo"));
            startActivity(intent);
        });

        imgBtInfos.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            View dialog = LayoutInflater.from(getBaseContext()).inflate(R.layout.popup_aforp_informations, null, false);
            builder.setView(dialog);
            final AlertDialog alertDialog = builder.create();

            Button buttonClose = dialog.findViewById(R.id.button_close);
            TextView phoneLink = dialog.findViewById(R.id.phoneLink);
            TextView mailLink = dialog.findViewById(R.id.mailLink);
            TextView webLink = dialog.findViewById(R.id.webLink);

            phoneLink.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+33143111070"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            });

            mailLink.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("mailto:contact@aforp.fr"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            });

            webLink.setOnClickListener(v -> {
                Intent intent  = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.aforp.fr/"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            });

            buttonClose.setOnClickListener(v -> {
                alertDialog.dismiss();
            });

            alertDialog.show();
        });
    }

    @Override
    public void onDestroy() {
        audio.onDestroy();
        super.onDestroy();
    }
}