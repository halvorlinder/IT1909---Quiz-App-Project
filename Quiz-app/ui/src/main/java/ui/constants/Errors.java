package ui.constants;

/**
 * A class that contains all error messages that are displayed to the user
 */
public abstract class Errors {
    public static final String DEFAULT = "Beklager, noe gikk galt på serveren";

    public static final String GET_QUIZ_404 = "Beklager, quizen finnes ikke lenger";
    public static final String POST_QUIZ_403 = "Denne quizen finnes allerede";
    public static final String PUT_QUESTION_403 = "Du eier ikke denne quizen og du kan derfor ikke endre spørsmålet";
    public static final String PUT_QUESTION_404 = "Beklager, dette spørsmålet finnes ikke lenger";
    public static final String DELETE_QUIZ_403 = "Du eier ikke denne quizen og du kan derfor ikke slette den";
    public static final String DELETE_QUIZ_404 = "Beklager, denne quizen finnes ikke lenger";
    public static final String DELETE_QUESTION_403 = "Du eier ikke denne quizen og du kan derfor ikke slette spørsmålet";
    public static final String DELETE_QUESTION_404 = "Beklager, dette spørsmålet finnes ikke lenger";
    public static final String ADD_QUESTION_403 = "Du eier ikke denne quizen og du kan derfor ikke legge til et spørsmål";
    public static final String ADD_QUESTION_404 = "Beklager, denne quizen finnes ikke lenger";
    public static final String LOGIN_403 = "Brukernavn eller passord er feil";
    public static final String REGISTER_403 = "Beklager, dette brukernavnet er tatt";
    public static final String GET_LEADERBOARD_404 = "Beklager, denne ledertavlen finnes ikke lenger";
    public static final String POST_SCORE_404 = "Beklager, kunne ikke registrere poengsum fordi quizen ikke finnes lenger";

    public static final String LOAD_PAGE = "Klarte ikke å laste inn side";
    public static final String GO_BACK = "Kunne ikke gå tilbake";
    public static final String EMPTY_QUIZ = "Denne quizen har ingen spørsmål";
    public static final String EMPTY_NAME = "Vennligst fyll inn et navn";
    public static final String LOG_OUT = "Noe gikk galt, kunne ikke logge ut";
    public static final String EMPTY_QUESTION_TEXT = "Du må skrive inn et spørsmål";
    public static final String EMPTY_CHOICE_TEXT = "Du må fylle ut alle feltene!";
    public static final String TOTAL_FAILURE = "En fatal feil har oppstått, vennligst start appen på nytt";


}
