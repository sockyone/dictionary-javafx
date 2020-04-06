package sample.model;
import java.io.FileOutputStream;
import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

public class VoiceAPI {
    private static String apiKey = "d0be01d1f36e49dcb87c40543212f67b";
    public static void loadVoice  (String text) throws Exception {

        VoiceProvider tts = new VoiceProvider(apiKey);

        VoiceParameters params = new VoiceParameters(text, Languages.English_UnitedStates);
        params.setCodec(AudioCodec.MP3);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_8bit_mono);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        byte[] voice = tts.speech(params);

        FileOutputStream fos = new FileOutputStream("../voice.mp3");
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();

    }
}
