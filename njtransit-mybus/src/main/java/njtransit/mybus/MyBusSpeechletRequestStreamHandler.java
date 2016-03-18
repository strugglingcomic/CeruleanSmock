package njtransit.mybus;
/**
 * @author codywang
 */


import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

/**
 * TODO: comment
 */
public final class MyBusSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds = new HashSet<String>();
    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds.add("amzn1.echo-sdk-ams.app.74b5eb58-4dfd-49e6-8ff9-3859a7ac65ba");
    }

    public MyBusSpeechletRequestStreamHandler() {
        super(new MyBusSpeechlet(), supportedApplicationIds);
    }
}
