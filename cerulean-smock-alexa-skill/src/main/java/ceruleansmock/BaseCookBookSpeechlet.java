/**
 * @author codywang
 * @author jeffking
 */
package ceruleansmock;

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

import ceruleansmock.model.Constants;
import ceruleansmock.model.Recipe;

/**
 * A base-level cookbook speechlet, for handling basic cookbook interactions;
 * TODO: for v0, recipe information is hard-coded; should move somewhere else... dynamo?)
 */
public class BaseCookBookSpeechlet implements Speechlet {
  private static final Logger log = LoggerFactory.getLogger(BaseCookBookSpeechlet.class);
  
  // intent slot(s), as defined in IntentSchema.json 
  private static final String RECIPE_SLOT = "recipe";
  
  // session keys
  private static final String SESSION_CURRENT_RECIPE = "recipe";
  private static final String SESSION_CURRENT_RECIPE_STEP = "step";

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

    // TODO: add additional intents
    if ("StartRecipeIntent".equals(intentName)) {
        return getStartRecipeResponse(intent, session);
    } else if ("ListIngredientsIntent".equals(intentName)) {
      return getListIngredientsResponse(intent, session);
    } else if ("AMAZON.HelpIntent".equals(intentName)) {
        return getHelpResponse();
    } else {
        throw new SpeechletException("Invalid Intent");
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
    String speechText = "Welcome to " + Constants.APP_NAME + "! "
        + " For example, you can say, \"start recipe " + Recipes.DEFAULT_RECIPE.getMetadata().getTitle() + "\", to get started cooking.";

    // TODO: Create the Simple card content.
    SimpleCard card = new SimpleCard();
    card.setTitle(Constants.APP_NAME);
    card.setContent(speechText);

    // TODO: Create the plain text output.
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    // TODO: Create reprompt
    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(speech);

    return SpeechletResponse.newAskResponse(speech, reprompt, card);
  }

  /**
   * Creates a {@code SpeechletResponse} for the start recipe intent.
   * @param intent 
   * @param session 
   *
   * @return SpeechletResponse spoken and visual response for the given intent
   */
  private SpeechletResponse getStartRecipeResponse(final Intent intent, final Session session) {
    String recipeTitle = intent.getSlot(RECIPE_SLOT).getValue();
    Recipe recipe = Recipes.get(recipeTitle);
    if(recipe != null) {
      session.setAttribute(SESSION_CURRENT_RECIPE, recipeTitle);
      session.setAttribute(SESSION_CURRENT_RECIPE_STEP, 0);

      //TODO: implement
      String speechText = "Ok, let's start cooking your: "
          + recipeTitle;

      // TODO: Create the Simple card content.
      SimpleCard card = new SimpleCard();
      card.setTitle(Constants.APP_NAME);
      card.setContent(speechText);

      // TODO: Create the plain text output.
      PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
      speech.setText(speechText);

      return SpeechletResponse.newTellResponse(speech, card);
    } else {
      return getUnknownRecipeResponse();
    }
  }
  
  /**
   * Creates a {@code SpeechletResponse} for the list ingredients intent.
   * Must be inside a session with a recipe selected already.
   * @param intent 
   * @param session 
   *
   * @return SpeechletResponse spoken and visual response for the given intent
   */
  private SpeechletResponse getListIngredientsResponse(final Intent intent, final Session session) {
    // verify we are inside a session with a recipe set
    if(session.getAttributes().containsKey(SESSION_CURRENT_RECIPE)) {
      String recipeTitle = (String) session.getAttribute(SESSION_CURRENT_RECIPE);
      Recipe recipe = Recipes.get(recipeTitle);

      //TODO: implement
      String speechText = "Ingredients for " + recipeTitle + ":\n"
          + recipe.toIngredientsString();

      // TODO: Create the Simple card content.
      SimpleCard card = new SimpleCard();
      card.setTitle(Constants.APP_NAME);
      card.setContent(speechText);

      // TODO: Create the plain text output.
      PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
      speech.setText(speechText);

      return SpeechletResponse.newTellResponse(speech, card);
    }
    
    return getUnknownRecipeResponse();
  }

  private SpeechletResponse getUnknownRecipeResponse() {
 // TODO: implement
    String speechText = "Sorry, I don't know what recipe you selected. "
        + " For example, please select a recipe by saying: start recipe " + Recipes.DEFAULT_RECIPE;

    // TODO: Create the Simple card content.
    SimpleCard card = new SimpleCard();
    card.setTitle(Constants.APP_NAME);
    card.setContent(speechText);

    // TODO: Create the plain text output.
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    // TODO: Create reprompt
    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(speech);

    return SpeechletResponse.newAskResponse(speech, reprompt, card);
  }
  
  /**
   * Creates a {@code SpeechletResponse} for the help intent.
   *
   * @return SpeechletResponse spoken and visual response for the given intent
   */
  private SpeechletResponse getHelpResponse() {
    // TODO: implement
    String speechText = "You can ask me to: "
        + " list ingredients, ";
        //+ " start recipe, ";
        //+ " continue, ";
        //+ " repeat, ";
        //+ " go back, ";

    // TODO: Create the Simple card content.
    SimpleCard card = new SimpleCard();
    card.setTitle(Constants.APP_NAME);
    card.setContent(speechText);

    // TODO: Create the plain text output.
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    // TODO: Create reprompt
    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(speech);

    return SpeechletResponse.newAskResponse(speech, reprompt, card);
  }
}
