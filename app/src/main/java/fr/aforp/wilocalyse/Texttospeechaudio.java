package fr.aforp.wilocalyse;

import android.speech.tts.TextToSpeech;
import android.util.Log;

public class Texttospeechaudio implements TextToSpeech.OnInitListener{

    private TextToSpeech texttospeech;
    public boolean activate_audio = true;
    private android.content.Context activityContext;

    public Texttospeechaudio(android.content.Context context)
    {
        Log.i("Test", "Initialisation TexttospeechAudio");
        this.texttospeech = new TextToSpeech(context, this);
        this.activityContext = context;
    }

    public void speakOut(String textaudio) {

        Log.i("Test", textaudio);
        if (activate_audio) {
            this.texttospeech.speak(textaudio, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void onDestroy() {
        if (this.texttospeech != null) {
            this.texttospeech.stop();
            this.texttospeech.shutdown();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Log.i("TTS", "Initilization Success!");
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    public String updateformattolocalisation(String label_localisation) {
        String label_return = "";
        boolean step = false;
        String[] label_array = label_localisation.split(" ");
        for (int a = 0; a <= label_array.length - 1; a++) {
            if (label_array[a].equals("Bât") || label_array[a].equals("Bât.") ){
                if(label_array[a + 3].equals("RDC")){
                    label_return += this.activityContext.getString(R.string.at) + this.activityContext.getString(R.string.ground_floor_label) + " " + this.activityContext.getString(R.string.from) + " ";
                    step = true;
                }
                else {
                    label_return += this.activityContext.getString(R.string.at);
                }
                label_return += this.activityContext.getString(R.string.building);
            }else if(label_array[a].equals("-") || (label_array[a].equals("RDC") && step)){
                label_return += "";
            }else if(label_array[a].equals("RDC") && !step){
                label_return += this.activityContext.getString(R.string.ground_floor_label);
            }else if(label_array[a].matches("[A-D]E[0-3][0-9][0-9]")){
                label_return += this.activityContext.getString(R.string.room) + " " + label_array[a];
            }else if(label_array[a].equals("Livraison")){
                label_return += this.activityContext.getString(R.string.delivery);
            }else if(label_array[a].equals("Accueil")){
                label_return += this.activityContext.getString(R.string.reception);
            }else if(label_array[a].equals("Accès")){
                label_return += this.activityContext.getString(R.string.access);
            }else if(label_array[a].equals("piéton")){
                label_return += this.activityContext.getString(R.string.pedestrian);
            }else if(label_array[a].equals("Café")){
                label_return += this.activityContext.getString(R.string.coffee);
            }else if(label_array[a].equals("Motos")){
                label_return += this.activityContext.getString(R.string.motorcycle);
            }else if(label_array[a].equals("Vélo")){
                label_return += this.activityContext.getString(R.string.bicycle);
            }else if(label_array[a].equals("Sécurité")){
                label_return += this.activityContext.getString(R.string.security);
            }else if(label_array[a].equals("Toilettes")){
                label_return += this.activityContext.getString(R.string.sanitary);
            }else if(label_array[a].equals("Reprographie")){
                label_return += this.activityContext.getString(R.string.reprographics);
            }else if(label_array[a].equals("Vestaires")){
                label_return += this.activityContext.getString(R.string.locker_room);
            }else if(label_array[a].equals("de")){
                label_return += this.activityContext.getString(R.string.to);
            }else if(label_array[a].equals("réunion")){
                label_return += this.activityContext.getString(R.string.meeting);
            }else if(label_array[a].equals("Photocopieur")){
                label_return += this.activityContext.getString(R.string.photocopier);
            }else if(label_array[a].equals("Parking")){
                label_return += this.activityContext.getString(R.string.parking);
            }else if(label_array[a].equals("Infirmerie")){
                label_return += this.activityContext.getString(R.string.infirmary);
            }else if(label_array[a].equals("polyvalente")){
                label_return += this.activityContext.getString(R.string.multipurpose);
            }else if(label_array[a].equals("Escalier")){
                label_return += this.activityContext.getString(R.string.staircase);
            }else if(label_array[a].equals("vers")){
                label_return += this.activityContext.getString(R.string.vers);
            }else if(label_array[a].equals("Sous-sol")){
                label_return += this.activityContext.getString(R.string.basement);
            }else if(label_array[a].equals("Espace")){
                label_return += this.activityContext.getString(R.string.space);
            }else if(label_array[a].equals("détente")){
                label_return += this.activityContext.getString(R.string.relaxation);
            }else if(label_array[a].equals("collaborateurs")){
                label_return += this.activityContext.getString(R.string.collaborator);
            }else {
                label_return += label_array[a];
            }
            if (a < label_array.length -1 && !label_array[a].equals("-") && !label_array[a].equals("RDC")){
                label_return += " ";
            }
        }
        return label_return;
    }
}
