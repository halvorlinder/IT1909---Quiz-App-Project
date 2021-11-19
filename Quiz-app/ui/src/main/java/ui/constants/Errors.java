package ui.constants;

/**
 * A class that contains all error messages that are displayed to the user
 */
public abstract class Errors {
    public final static String DEFAULT = "Beklager, noe gikk galt på serveren";

    public final static String GET_QUIZ_404 = "Beklager, quizen finnes ikke lenger";
    public final static String POST_QUIZ_403 = "Denne quizen finnes allerede";
    public final static String PUT_QUESTION_403 = "Du eier ikke denne quizen og du kan derfor ikke endre spørsmålet";
    public final static String PUT_QUESTION_404 = "Beklager, dette spørsmålet finnes ikke lenger";
    public final static String DELETE_QUIZ_403 = "Du eier ikke denne quizen og du kan derfor ikke slette den";
    public final static String DELETE_QUIZ_404 = "Beklager, denne quizen finnes ikke lenger";
    public final static String DELETE_QUESTION_403 = "Du eier ikke denne quizen og du kan derfor ikke slette spørsmålet";
    public final static String DELETE_QUESTION_404 = "Beklager, dette spørsmålet finnes ikke lenger";
    public final static String ADD_QUESTION_403 = "Du eier ikke denne quizen og du kan derfor ikke legge til et spørsmål";
    public final static String ADD_QUESTION_404 = "Beklager, denne quizen finnes ikke lenger";
    public final static String LOGIN_403 = "Brukernavn eller passord er feil";
    public final static String REGISTER_403 = "Beklager, dette brukernavnet er tatt";
    public final static String GET_LEADERBOARD_404 = "Beklager, denne ledertavlen finnes ikke lenger";
    public final static String POST_SCORE_404 = "Beklager, kunne ikke registrere poengsum fordi quizen ikke finnes lenger";

    public final static String LOAD_PAGE = "Klarte ikke å laste inn side";
    public final static String GO_BACK = "Kunne ikke gå tilbake";
    public final static String EMPTY_QUIZ = "Denne quizen har ingen spørsmål";
    public final static String EMPTY_NAME = "Vennligst fyll inn et navn";
    public final static String LOG_OUT = "Noe gikk galt, kunne ikke logge ut";
    public final static String EMPTY_QUESTION_TEXT = "Du må skrive inn et spørsmål";
    public final static String EMPTY_CHOICE_TEXT = "Du må fylle ut alle feltene!";
    public final static String TOTAL_FAILURE = "En fatal feil har oppstått, vennligst start appen på nytt";


}
