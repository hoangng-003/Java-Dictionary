package Service;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Speak {
    private Voice voice;

    /**
     * Setting text to speak.
     */
    public Speak() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null)
            voice.allocate();
        voice.setRate(130); //Setting the rate of the voice
        voice.setPitch(100); //Setting the Pitch of the voice
        voice.setVolume(4); //Setting the volume of the voice
    }

    public void speak(String word) {
        voice.speak(word);
    }

}
