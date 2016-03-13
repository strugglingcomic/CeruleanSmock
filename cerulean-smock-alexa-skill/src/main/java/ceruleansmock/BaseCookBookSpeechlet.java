/**
 * @author codywang
 * @author jeffking
 */
package ceruleansmock;

import org.apache.commons.lang3.StringUtils;
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
  private static final String RECIPE_SLOT = "Recipe";
  private static final String STEP_SLOT = "Step";
  
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

    // TODO: add additional intents: repeat step
    if ("WelcomeIntent".equals(intentName)) {
      return getWelcomeResponse();
    } else if ("PickRecipeIntent".equals(intentName)) {
        return getPickRecipeResponse(intent, session);
    } else if ("ListIngredientsIntent".equals(intentName)) {
      return getListIngredientsResponse(intent, session);
    } else if ("NextStepIntent".equals(intentName)) {
      return getNextStepResponse(intent, session);
    } else if ("QueryStepIntent".equals(intentName)) {
      return getQueryStepResponse(intent, session);
    } else if ("StatusIntent".equals(intentName)) {
      return getStatusResponse(intent, session);
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
        + " For example, you can say, \"pick recipe " + Recipes.DEFAULT_RECIPE.getMetadata().getTitle() + "\", to get started cooking.";

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
   * Creates a {@code SpeechletResponse} for the pick recipe intent
   * @param intent 
   * @param session 
   *
   * @return SpeechletResponse spoken and visual response for the given intent
   */
  private SpeechletResponse getPickRecipeResponse(final Intent intent, final Session session) {
    String recipeTitle = intent.getSlot(RECIPE_SLOT).getValue();
    Recipe recipe = Recipes.get(recipeTitle);
    if(recipe != null) {
      session.setAttribute(SESSION_CURRENT_RECIPE, recipeTitle);
      session.setAttribute(SESSION_CURRENT_RECIPE_STEP, 0);

      String speechText = "Ok, you picked: " + recipe.getRecipeFullTitle() + ". "
          + " This recipe should take around " + recipe.getMetadata().getCookTime() + " minutes to cook. "
          + " It'll make " + recipe.getMetadata().getServings() + " servings, "
          + " and has " + recipe.getMetadata().getCalories() + " calories total. "
          + " <break time=\"0.5s\" /> Let me know when you're ready to get started!";

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

      log.info("getPickRecipeResponse sessionId={}, picked recipeTitle={}", session.getSessionId(), recipeTitle);
      return SpeechletResponse.newAskResponse(speech, reprompt, card);
    } else {
      return getUnknownResponse();
    }
  }
  
  /**
   * Creates a {@code SpeechletResponse} for the list ingredients intent.
   * Can either list the ingredients for this session's recipe, or the named recipe
   * @param intent 
   * @param session 
   *
   * @return SpeechletResponse spoken and visual response for the given intent
   */
  private SpeechletResponse getListIngredientsResponse(final Intent intent, final Session session) {
    // use the current session recipe, if set
    if(session.getAttributes().containsKey(SESSION_CURRENT_RECIPE)) {
      String speechText = StringUtils.EMPTY;
      String recipeTitle = (String) session.getAttribute(SESSION_CURRENT_RECIPE);
      Recipe recipe = Recipes.get(recipeTitle);
      if(recipe != null) {
        speechText = "Ingredients for " + recipeTitle + ":\n"
            + recipe.getIngredientsString();
      } else {
        log.error("Invalid recipeTitle={} set in sessionId={}", recipeTitle, session.getSessionId());
        return getUnknownResponse();
      }

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

      log.info("getListIngredientsResponse sessionId={}, list ingredients for recipeTitle={}", session.getSessionId(), recipeTitle);
      return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }
    
    return getUnknownResponse();
  }
  
  /**
   * This intent response steps through the recipe, incrementing by 1 step at a time.
   * Steps are stored in a 0-indexed list, but spoken using 1-index.
   * @param intent
   * @param session
   * @return
   */
  private SpeechletResponse getNextStepResponse(final Intent intent, final Session session) {
    // use the current session recipe and step, if set
    if(session.getAttributes().containsKey(SESSION_CURRENT_RECIPE) && session.getAttributes().containsKey(SESSION_CURRENT_RECIPE_STEP)) {
      String recipeTitle = (String) session.getAttribute(SESSION_CURRENT_RECIPE);
      Recipe recipe = Recipes.get(recipeTitle);

      int currentStep = (int) session.getAttribute(SESSION_CURRENT_RECIPE_STEP); // this is 0-indexed
      int maxSteps = recipe.getSteps().size();
      String speechText = StringUtils.EMPTY;
      if(currentStep < maxSteps) {
        int speechStep = currentStep + 1;
        speechText = "Step " + speechStep + ": " + recipe.getSteps().get(currentStep).toString();
      } else {
        return getDoneResponse();
      }

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

      int nextStep = currentStep + 1;
      session.setAttribute(SESSION_CURRENT_RECIPE_STEP, nextStep);
      log.info("getNextStepResponse sessionId={}, recipeTitle={}, moving to step={}", session.getSessionId(), recipeTitle, nextStep);
      return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }
    
    return getUnknownResponse();
  }
  

  /**
   * returns specific step, either for current session or for a named recipe
   * TODO: implement querying step for recipe
   * @param intent
   * @param session
   * @return
   */
  private SpeechletResponse getQueryStepResponse(final Intent intent, final Session session) {
    try {
      String speechText = StringUtils.EMPTY;
      String recipeTitle = intent.getSlot(RECIPE_SLOT).getValue();
      int queryStep = Integer.parseInt(intent.getSlot(STEP_SLOT).getValue()) - 1; // convert to 0-index
      
      Recipe recipe = null;
      if(!StringUtils.isBlank(recipeTitle)) {
        recipe = Recipes.get(recipeTitle);
      } else if(session.getAttributes().containsKey(SESSION_CURRENT_RECIPE)) {
        recipeTitle = (String) session.getAttribute(SESSION_CURRENT_RECIPE);
        recipe = Recipes.get(recipeTitle);
      }
      
      if(recipe != null) {
        if(queryStep < recipe.getSteps().size() && queryStep >= 0) {
          int speechStep = queryStep + 1; // convert 0-index to 1-index
          speechText = "Step " + speechStep + " of " + recipe.getRecipeFullTitle() + " says: <break time=\"0.2s\" /> "
              + recipe.getSteps().get(queryStep);
          log.info("getStatusResponse sessionId={}, recipeTitle={}, step={}", session.getSessionId(), recipeTitle, queryStep);
        }

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
    
        // TODO: implement
        log.info("getQueryStepResponse sessionId={}, recipeTitle={}, step={}", session.getSessionId(), StringUtils.EMPTY, StringUtils.EMPTY);
        return SpeechletResponse.newAskResponse(speech, reprompt, card);
      }
      
      return getUnknownResponse();
    } catch (NumberFormatException e) {
      return getUnknownResponse();
    }
  }
  
  /**
   * Returns a status update based on session info;
   * TODO: add a time-remaining estimate?
   * @param intent
   * @param session
   * @return
   */
  private SpeechletResponse getStatusResponse(final Intent intent, final Session session) {
    String speechText = "Sorry, we haven't picked a recipe yet.";
    if(session.getAttributes().containsKey(SESSION_CURRENT_RECIPE)) {
      String recipeTitle = (String) session.getAttribute(SESSION_CURRENT_RECIPE);
      Recipe recipe = Recipes.get(recipeTitle);
      if(recipe != null) {
        int recipeStep = (int) session.getAttribute(SESSION_CURRENT_RECIPE_STEP);
        int speechStep = recipeStep + 1; // convert 0-index to 1-index
        int stepsRemaining = recipe.getSteps().size() - speechStep;
        speechText = "Right now, we are on step " + recipeStep + " of making: " 
            + recipe.getRecipeTitleAndOverview() + " <break time=\"0.5s\" /> "
            + " Hang in there, there's only " + stepsRemaining + " steps after this one!";
        log.info("getStatusResponse sessionId={}, recipeTitle={}, step={}", session.getSessionId(), recipeTitle, recipeStep);
      }
    }
    
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

    log.info("getStatusResponse sessionId={}, recipeTitle={}, step={}", session.getSessionId(), StringUtils.EMPTY, StringUtils.EMPTY);
    return SpeechletResponse.newAskResponse(speech, reprompt, card);
  }

  private SpeechletResponse getDoneResponse() {
    String speechText = "Hey, guess what? We're done! Wooooohoooo, I'm so proud of us! Let's do it again soon! Goodbye.";

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

    return SpeechletResponse.newTellResponse(speech, card);
  
  }

  /**
   * Default error response when unknown recipe is encountered
   * @return
   */
  private SpeechletResponse getUnknownResponse() {
    String speechText = "Sorry, I don't know how to do that. You can say \"help\", to ask for assistance.";

    SimpleCard card = new SimpleCard();
    card.setTitle(Constants.APP_NAME);
    card.setContent(speechText);

    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

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
        + " pick recipe, "
        + " give a status update, "
        + " list ingredients, "
        + " start recipe or next step, ";
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
