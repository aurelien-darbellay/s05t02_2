package s05t02.interactiveCV.globalVariables;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiPaths {
    
    public static final String BASE_PATH = "/api";
    //protected paths
    public static final String PROTECTED_BASE_PATH = BASE_PATH + "/protected";
    public static final String AUTHENTICATION_CHECK_PATH = PROTECTED_BASE_PATH + "/me";
    /// /user path
    public static final String USER_BASE_PATH = PROTECTED_BASE_PATH + "/users";
    public static final String USER_DASHBOARD_REL = "/dashboard";
    public static final String USER_DASHBOARD_PATH = USER_BASE_PATH + USER_DASHBOARD_REL;
    public static final String USER_DELETE_REL = "/delete";
    public static final String USER_DELETE_PATH = USER_BASE_PATH + USER_DELETE_REL;
    public static final String USER_GENERATE_PDF_REL = "/pdf";

    public static final String DOC_REL = "/doc";
    public static final String DOC_PATH = USER_BASE_PATH + DOC_REL; //newDoc in Body
    public static final String DOC_ID_REL = "/{docId}";
    public static final String DOC_ID_PATH = DOC_PATH + DOC_ID_REL; //get + update
    public static final String DELETE_DOC_REL = "/delete";
    public static final String DELETE_DOC_PATH = DOC_ID_PATH + DELETE_DOC_REL;

    public static final String PVs_PATH_REL = "/public-view"; // doc in Body
    public static final String PV_PATH_REL = PVs_PATH_REL + "/{id}";
    public static final String PV_DELETE = "/public-view/delete/{id}";

    public static final String ENTRY_BASE_PATH = DOC_ID_PATH + "/entry";
    public static final String ENTRY_ADD_REL = "/add";
    public static final String ENTRY_DELETE_REL = "/delete";
    public static final String ENTRY_UPDATE_REL = "/update";

    public static final String CLOUD_STORAGE_PATH = USER_BASE_PATH + "/cloud-storage";
    public static final String CLOUD_SIGNATURE_PATH = CLOUD_STORAGE_PATH + "/signature";
    public static final String CLOUD_DELETE_ASSET_PATH = CLOUD_STORAGE_PATH + "/delete";
    public static final String ADMIN_BASE_PATH = PROTECTED_BASE_PATH + "/admin";


    //unprotected paths
    public static final String LOGIN_PATH = BASE_PATH + "/login";
    public static final String LOGOUT_PATH = BASE_PATH + "/logout";
    public static final String REGISTER_PATH = BASE_PATH + "/register";
    public static final String PUBLIC_VIEWS_PATH = BASE_PATH + "/public-views";//add ?id={pv_id}
    public static final String CSRF_TOKEN_PATH = BASE_PATH + "/csrf";
    public static final String TYPES_CONFIG_PATH = BASE_PATH + "/config";


    public static String extractUserNameFromBaseUserSpaceUrl(String url) {
        String regex = USER_BASE_PATH.replace("{username}", "([^/]+)");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}
