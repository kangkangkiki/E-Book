package com.example.android_ebook_reader.media;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.Locale;

public class MediaController {
    private Context context;
    private TextToSpeech textToSpeech;

    public MediaController(Context context) {
        this.context = context;
        initializeTextToSpeech();
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {
                            // Handle the start of speech synthesis
                        }

                        @Override
                        public void onDone(String utteranceId) {
                            // Handle the completion of speech synthesis
                        }

                        @Override
                        public void onError(String utteranceId) {
                            // Handle errors during speech synthesis
                        }
                    });
                }
            }
        });
    }

    public void speak(String text) {
        if (textToSpeech != null && textToSpeech.isLanguageAvailable(Locale.getDefault()) != TextToSpeech.LANG_NOT_SUPPORTED) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
        }
    }

    public void stop() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
    }

    public void shutdown() {
        if (textToSpeech != null) {
            textToSpeech.shutdown();
        }
    }
}
