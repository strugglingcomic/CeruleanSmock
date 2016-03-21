package njtransit.mybus;
/**
 * @author codywang
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.speech.ui.SsmlOutputSpeech;

import njtransit.mybus.model.BusRouteETA;
import njtransit.mybus.model.Constants;
import njtransit.mybus.model.NJTransitMyBusRequest;

/**
 * TODO: comment
 */
public class MyBusSpeechlet implements Speechlet {
  private static final Logger log = LoggerFactory.getLogger(MyBusSpeechlet.class);
  
  public static final String NJTRANSIT_MYBUS_URL_ROOT = "http://mybusnow.njtransit.com/bustime/wireless/html/eta.jsp";
  
  private static final String SPEECH_SHORT_BREAK = "<break time=\"0.25s\"/>";
  private static final String DEFAULT_STOP_NAME = "Bergenline Avenue and 55th Street";
  
  private Client client;

  @Override
  public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
    log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());

    this.client = ClientBuilder.newClient();
    this.client.property(ClientProperties.CONNECT_TIMEOUT, 500);
    this.client.property(ClientProperties.READ_TIMEOUT, 500);
  }

  @Override
  public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
    log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
    return this.getWelcomeResponse();
  }

  @Override
  public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
    log.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());

    final Intent intent = request.getIntent();
    final String intentName = (intent != null) ? intent.getName() : null;

    if ("WelcomeIntent".equals(intentName)) {
      return this.getWelcomeResponse();
    } else if ("DefaultStatusIntent".equals(intentName)) {
      return this.getDefaultStatusResponse(intent, session);
    } else if ("AMAZON.HelpIntent".equals(intentName)) {
      return this.getHelpResponse();
    } else {
      return this.getUnknownResponse();
    }
  }

  @Override
  public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
    log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
    // any cleanup logic goes here
  }

  /**
   * Creates and returns a {@code SpeechletResponse} with a welcome message.
   * TODO: tweak this text
   *
   * @return SpeechletResponse spoken and visual response for the given intent
   */
  private SpeechletResponse getWelcomeResponse() {
    final String speechText = "Hi there.  You can ask me for the latest NJTransit MyBus information for your home bus stop: "
        + SPEECH_SHORT_BREAK + DEFAULT_STOP_NAME;
    return makeSimpleAskResponse(speechText);
  }

  private SpeechletResponse getDefaultStatusResponse(final Intent intent, final Session session) {
    String speechText = "For the " + DEFAULT_STOP_NAME + " stop: " + SPEECH_SHORT_BREAK;

    final List<BusRouteETA> defaultETAs = new ArrayList<BusRouteETA>();
    defaultETAs.addAll(new NJTransitMyBusRequest("156", "New York", "21880", false).getBusRouteETA(this.client, NJTRANSIT_MYBUS_URL_ROOT));
    defaultETAs.addAll(new NJTransitMyBusRequest("159", "New York", "21880", false).getBusRouteETA(this.client, NJTRANSIT_MYBUS_URL_ROOT));
    Collections.sort(defaultETAs);

    if(defaultETAs.isEmpty()) {
      speechText += " I couldn't find any buses for your search, within the next 30 minutes. ";
    } else {
      for (final BusRouteETA eta : defaultETAs) {
        speechText += " " + eta.toString() + ". ";
      }
    }
    // TODO: log something
    // log.info();
    return makeSimpleTellResponse(speechText);
  }

  /**
   * Default error response when unknown recipe is encountered
   *
   * @return
   */
  private SpeechletResponse getUnknownResponse() {
    final String speechText = "Sorry, I don't know how to do that. You can say, ask Buster for help, to ask for assistance.";
    return makeSimpleAskResponse(speechText);
  }

  /**
   * Creates a {@code SpeechletResponse} for the help intent.
   *
   * @return SpeechletResponse spoken and visual response for the given intent
   */
  private SpeechletResponse getHelpResponse() {
    final String speechText = "If you need help, just say: Alexa, ask " + Constants.INVOCATION_NAME + " when is the next bus?";
    return makeSimpleAskResponse(speechText);
  }

  /**
   * simple static method to do a basic tell response, using the same speech text throughout
   *
   * @param speechText
   * @return
   */
  private static SpeechletResponse makeSimpleTellResponse(final String speechText) {
    final SimpleCard card = new SimpleCard();
    card.setTitle(Constants.APP_NAME);
    card.setContent(speechText);

    final SsmlOutputSpeech speech = new SsmlOutputSpeech();
    speech.setSsml(String.format("<speak>%s</speak>", speechText));

    return SpeechletResponse.newTellResponse(speech, card);
  }

  /**
   * simple static method to a basic ask response, using the same speech text throughout
   *
   * @param speechText
   * @return
   */
  private static SpeechletResponse makeSimpleAskResponse(final String speechText) {
    final SimpleCard card = new SimpleCard();
    card.setTitle(Constants.APP_NAME);
    card.setContent(speechText);

    final SsmlOutputSpeech speech = new SsmlOutputSpeech();
    speech.setSsml(String.format("<speak>%s</speak>", speechText));

    final Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(speech);

    return SpeechletResponse.newAskResponse(speech, reprompt, card);
  }
}
