package njtransit.mybus;
/**
 * @author codywang
 */


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientProperties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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

/**
 * TODO: comment
 */
public class MyBusSpeechlet implements Speechlet {
  private static final Logger log = LoggerFactory.getLogger(MyBusSpeechlet.class);
  
  private static final String SPEECH_SHORT_BREAK = "\\<break time\\=\"0.25s\" \\/\\>";

  private static final Client CLIENT;
  private static final WebTarget RT_156_TARGET;
  private static final WebTarget RT_159_TARGET;
  
  static {
    CLIENT = ClientBuilder.newClient();
    CLIENT.property(ClientProperties.CONNECT_TIMEOUT, 500);
    CLIENT.property(ClientProperties.READ_TIMEOUT, 500);
    RT_156_TARGET = CLIENT.target("http://mybusnow.njtransit.com/bustime/wireless/html/eta.jsp?route=156&direction=New+York&id=21880&showAllBusses=off");
    RT_159_TARGET = CLIENT.target("http://mybusnow.njtransit.com/bustime/wireless/html/eta.jsp?route=159&direction=New+York&id=21880&showAllBusses=off");

  }

  @Override
  public void onSessionStarted(final SessionStartedRequest request, final Session session)
          throws SpeechletException {
    log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
            session.getSessionId());
    // TODO: any sessions-based initialization?
  }

  @Override
  public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
          throws SpeechletException {
    log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
            session.getSessionId());
    return getWelcomeResponse();
  }

  @Override
  public SpeechletResponse onIntent(final IntentRequest request, final Session session)
          throws SpeechletException {
    log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
            session.getSessionId());

    Intent intent = request.getIntent();
    String intentName = (intent != null) ? intent.getName() : null;

    if ("WelcomeIntent".equals(intentName)) {
      return getWelcomeResponse();
    } else if ("StatusIntent".equals(intentName)) {
      return getStatusResponse(intent, session);
    } else if ("AMAZON.HelpIntent".equals(intentName)) {
        return getHelpResponse();
    } else {
        return getUnknownResponse();
    }
  }

  @Override
  public void onSessionEnded(final SessionEndedRequest request, final Session session)
          throws SpeechletException {
    log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
            session.getSessionId());
    // any cleanup logic goes here
  }

  /**
   * Creates and returns a {@code SpeechletResponse} with a welcome message.
   *
   * @return SpeechletResponse spoken and visual response for the given intent
   */
  private SpeechletResponse getWelcomeResponse() {
    String speechText = "Hi, this skill is purpose-built to monitor exactly one bus stop at Bergenline and 55th street, in the New York bound direction.  Enjoy!";
    return makeSimpleAskResponse(speechText);
  }
  
  private SpeechletResponse getStatusResponse(final Intent intent, final Session session) {
    String speechText = StringUtils.EMPTY;
    Integer rt156Eta = null;
    Integer rt159Eta = null;

    // TODO: for hacking parsing... 35 elements is what we expect, anything else is considered to mean N/A no info available
    // index 16 is the route: "#159"
    // index 17 is the ETA: "27 MIN"
    
    final Response rt156Resp = RT_156_TARGET.request(MediaType.TEXT_PLAIN).get();
    final int rt156RespStatus = rt156Resp.getStatus();
    if (rt156RespStatus == Response.Status.OK.getStatusCode()) {
      Document document = Jsoup.parse(rt156Resp.readEntity(String.class));
      Elements elements = document.body().select("*");
      if(elements.size() == 35) {
        String rawStr = elements.get(16).text();
        if(Integer.parseInt(rawStr.substring(1).replace("\u00a0","").trim()) == 156) {
          String eta = elements.get(17).text();
          rt156Eta = Integer.parseInt(eta.substring(0, eta.indexOf("\u00a0")));
        }
      }
    }

    final Response rt159Resp = RT_159_TARGET.request(MediaType.TEXT_PLAIN).get();
    final int rt159RespStatus = rt159Resp.getStatus();
    if (rt159RespStatus == Response.Status.OK.getStatusCode()) {
      Document document = Jsoup.parse(rt159Resp.readEntity(String.class));
      Elements elements = document.body().select("*");
      if(elements.size() == 35) {
        String rawStr = elements.get(16).text();
        if(Integer.parseInt(rawStr.substring(1).replace("\u00a0","").trim()) == 159) {
          String eta = elements.get(17).text();
          rt159Eta = Integer.parseInt(eta.substring(0, eta.indexOf("\u00a0")));
        }
      }
    }
    
    if(rt156Eta != null) {
      speechText = "For the Bergenline Avenue and 55th Street stop, heading towards New York City: ";
      if(rt159Eta != null) {
        // both routes availabe, speak times in the right order
        if(rt156Eta <= rt159Eta) {
          speechText += "Route one fifty six is " + rt156Eta + " minutes away, and ";
          speechText += "Route one fifty nine is " + rt159Eta + " minutes away. ";
        } else {
          speechText += "Route one fifty nine is " + rt159Eta + " minutes away, and ";
          speechText += "Route one fifty six is " + rt156Eta + " minutes away. ";
        }
      } else {
        // only the 156 is available, so say that only
        speechText += "Route one fifty six is " + rt156Eta + " minutes away. ";
      }
    } else {
      if(rt159Eta != null) {
        // only the 159 is available, so say that only
        speechText = "For the Bergenline Avenue and 55th Street stop, heading towards New York City: ";
        speechText += "Route one fifty nine is " + rt159Eta + " minutes away. ";
      } else {
        // neither route has info available, say so
        speechText = "Sorry, neither the one fifty six nor the one fifty nine seem to be running right now. ";
        speechText += " Either that, or I just have no idea when the bus is gonna get here.  You should probably just go down and check.  May the force be with you!";
        
      }
    }

    // TODO: log something
    //log.info();
    return makeSimpleTellResponse(speechText);
  }

  /**
   * Default error response when unknown recipe is encountered
   * @return
   */
  private SpeechletResponse getUnknownResponse() {
    String speechText = "Sorry, I don't know how to do that. You can say \"help\", to ask for assistance.";
    return makeSimpleAskResponse(speechText);
  }

  /**
   * Creates a {@code SpeechletResponse} for the help intent.
   *
   * @return SpeechletResponse spoken and visual response for the given intent
   */
  private SpeechletResponse getHelpResponse() {
    String speechText = "If you need help, just say, Alexa, ask my bus when is the next bus?";
    return makeSimpleAskResponse(speechText);
  }

  /**
   * simple static method to do a basic tell response, using the same speech text throughout
   * @param speechText
   * @return
   */
  private static SpeechletResponse makeSimpleTellResponse(final String speechText) {
    SimpleCard card = new SimpleCard();
    card.setTitle(Constants.APP_NAME);
    card.setContent(speechText);

    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    return SpeechletResponse.newTellResponse(speech, card);
  }

  /**
   * simple static method to a basic ask response, using the same speech text throughout
   * @param speechText
   * @return
   */
  private static SpeechletResponse makeSimpleAskResponse(final String speechText) {
    SimpleCard card = new SimpleCard();
    card.setTitle(Constants.APP_NAME);
    card.setContent(speechText);

    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(speech);

    return SpeechletResponse.newAskResponse(speech, reprompt, card);
  }
}
