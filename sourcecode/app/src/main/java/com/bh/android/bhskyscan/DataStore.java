package com.bh.android.bhskyscan;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataStore {

    private final String TAG = "BH_SR_" + this.getClass().getSimpleName();
    private final static boolean DBG = true;

    private final String KEY_DATA_PREFERENCE = "private_data";

    private final String KEY_FB_ID = "FBID";
    private final String KEY_FB_TOKEN = "FBTOKEN";
    private final String KEY_FB_GENDER = "FB_GEMDER";
    private final String KEY_FB_FIRST_NAME = "FB_FIRST_NAME";
    private static final String KEY_FB_EMAIL = "FB_EMAIL";
    private final String KEY_FB_BIRTHDAY = "FB_BIRTHDAY";
    private final String KEY_USER_PROFILE = "KEY_USER_PROFILE";
    private final static String KEY_ENCRYPTED_MEMBER_ID = "MEMBERID";
    private final static String KEY_RAW_MEMBER_ID = "MEMBERID_RAW";

    private final static String KEY_SEARCH_RECORD = "SEARCH_RECORD";
    private final static String KEY_LIKE_SERVER_DEFAULT = "LIKE_SERVER_DEFAULT";

    private final static String KEY_LIKE_SETTINGS = "LIKE_SETTING";
    private final static String KEY_SEARCH_SERVER_DEFAULT = "SEARCH_SERVER_DEFAULT";

    // for Google Play install referrer.
    private final String KEY_INSTALL_REFERRER = "referrer";

    // key to save daily reward index.
    private final String KEY_DAILY_REWARD_INDEX = "reward_index";

    private SharedPreferences mSharedPreferences = null;
    private SharedPreferences.Editor mPrefEditor = null;

    private final String KEY_PICTURE_URI = "PICTURE_URI";

    public final static String KEY_NOTIFY_NEW_MSG = "pref_incomingMessage";
    public final static String KEY_NOTIFY_LIKE = "pref_makeFriends";
    public final static String KEY_NOTIFY_MUTUAL = "pref_you_got_match";
    public final static String KEY_NOTIFY_WHO_LOOK_ME = "pref_who_look_me";
    public final static String KEY_PROFILE_VISIBILITY = "pref_showMyProfile";


    public void setReferrer(String value) {
        mPrefEditor.putString(KEY_INSTALL_REFERRER, value).apply();

        if(DBG) Log.d(TAG, ">> setReferrer: " + value);

    }

    public String getReferrer() {
        return mSharedPreferences.getString(KEY_INSTALL_REFERRER, null);
    }

    public void setPreferenceEnable(String key, boolean value) {
        mPrefEditor.putBoolean(key, value).apply();
    }

    public boolean getPreferenceEnable(String key, boolean isEnabled) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public void setLastDailyRewardIndex(int value) {
        mPrefEditor.putInt(KEY_DAILY_REWARD_INDEX, value).apply();
    }

    public int getLastDailyRewardIndex() {
        return mSharedPreferences.getInt(KEY_DAILY_REWARD_INDEX, -1);
    }

    public void setFBEmail(String value) {
        mPrefEditor.putString(KEY_FB_EMAIL, value).apply();
        if(DBG) Log.d(TAG, "@@@@@@@>> setFBEmail: " + value);
    }

    public String getFBEmail() {
        JSONObject value = null;
        return mSharedPreferences.getString(KEY_FB_EMAIL, null);
    }


    public void setLastLikeServerDefault(JSONObject value) {
        mPrefEditor.putString(KEY_LIKE_SERVER_DEFAULT, value.toString()).apply();
        if(DBG) Log.d(TAG, "@@@@@@@>> setLastLikeServerDefault: " + value.toString());
    }

    public JSONObject getLastLikeServerDefault() {
        JSONObject value = null;
        String jsonString = mSharedPreferences.getString(KEY_LIKE_SERVER_DEFAULT, null);

        try {
            value = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(DBG) Log.d(TAG, ">> getLastLikeServerDefault: " + value);
        return value;
    }

    public void setLastSearchServerDefault(JSONObject value) {
        mPrefEditor.putString(KEY_SEARCH_SERVER_DEFAULT, value.toString()).apply();
        if(DBG) Log.d(TAG, "@@@@@@@>> setLastSearchServerDefault: " + value.toString());
    }

    public JSONObject getLastSearchServerDefault() {
        JSONObject value = null;
        String jsonString = mSharedPreferences.getString(KEY_SEARCH_SERVER_DEFAULT, null);

        try {
            value = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(DBG) Log.d(TAG, ">> getLastSearchServerDefault: " + value);
        return value;
    }

    public void setLastLikeSetting(JSONObject value) {
        mPrefEditor.putString(KEY_LIKE_SETTINGS, value.toString()).apply();
        if(DBG) Log.d(TAG, "@@@@@@@>> setLastLikeSetting: " + value.toString());
    }

    public JSONObject getLastLikeSetting() {
        JSONObject value = null;
        String jsonString = mSharedPreferences.getString(KEY_LIKE_SETTINGS, null);

        if(jsonString != null && !jsonString.equals("")) {
            try {
                value = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if(DBG) Log.d(TAG, ">> getLastLikeSetting: " + value);
        return value;
    }

    public void setLastSearchRecord(JSONArray value) {
        mPrefEditor.putString(KEY_SEARCH_RECORD, value.toString()).apply();
        if(DBG) Log.d(TAG, "@@@@@@@>> setLastSearchRecord: " + value.toString());
    }

    public JSONArray getLastSearchRecord() {
        JSONArray value = null;
        String jsonString = mSharedPreferences.getString(KEY_SEARCH_RECORD, null);

        try {
            value = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(DBG) Log.d(TAG, ">> getLastSearchRecord: " + value);
        return value;
    }

    public DataStore(Context context) {

        if(mSharedPreferences == null) {
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        if(mPrefEditor == null) {
            mPrefEditor = mSharedPreferences.edit();
        }
    }

    public void setRawMemberId(String value) {
        if(DBG) Log.d(TAG, ">> setRawMemberId");
        mPrefEditor.putString(KEY_RAW_MEMBER_ID, value).apply();
    }

    public String getRawMemberId() {
        String value;
        value = mSharedPreferences.getString(KEY_RAW_MEMBER_ID, null);
        if(DBG) Log.d(TAG, ">> getRawMemberId: " + value);
        return value;
    }

    public void setEncryptedMemberId(String value) {
        if(DBG) Log.d(TAG, ">> setEncryptedMemberId");
        mPrefEditor.putString(KEY_ENCRYPTED_MEMBER_ID, value).apply();
    }

    public String getKeyEncryptedMemberId() {
        String value;
        value = mSharedPreferences.getString(KEY_ENCRYPTED_MEMBER_ID, null);
        if(DBG) Log.d(TAG, ">> getKeyEncryptedMemberId: " + value);
        return value;
    }

    public void setUserProfile(String value) {
        if(DBG) Log.d(TAG, ">> setUserProfile");
        mPrefEditor.putString(KEY_USER_PROFILE, value).apply();
    }

    public void setUserProfile(JSONObject jsonObject) {
        if(DBG) Log.d(TAG, ">> setUserProfile");
        String value = jsonObject.toString();
        mPrefEditor.putString(KEY_USER_PROFILE, value).apply();
    }

    private String getUserProfile() {
        String value;
        value = mSharedPreferences.getString(KEY_USER_PROFILE, null);
        if(DBG) Log.d(TAG, ">> getUserProfile: " + value);
        return value;
    }

    public JSONObject getUsrProfile() {

        JSONObject jsonObject = new JSONObject();
        String value = getUserProfile();
        try {
            jsonObject = new JSONObject(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public void removeUserProfile() {
        mPrefEditor.remove(KEY_USER_PROFILE);
    }

    public String getUserBirthday() {
        String value;
        value = mSharedPreferences.getString(KEY_FB_BIRTHDAY, null);
        if(DBG) Log.d(TAG, ">> getUserBirthday: " + value);
        return value;
    }

    public void saveUserBirthday(String value) {
        if(DBG) Log.d(TAG, ">> saveUserBirthday");
        mPrefEditor.putString(KEY_FB_BIRTHDAY, value).apply();
    }

    public String getUserFirstName() {
        String value;
        value = mSharedPreferences.getString(KEY_FB_FIRST_NAME, null);
        if(DBG) Log.d(TAG, ">> getUserFirstName: " + value);
        return value;
    }

    public void saveUserFirstName(String value) {
        if(DBG) Log.d(TAG, ">> saveUserFirstName");
        mPrefEditor.putString(KEY_FB_FIRST_NAME, value).apply();
    }

    public String getUserGender() {

        String value;

        value = mSharedPreferences.getString(KEY_FB_GENDER, null);
        if(DBG) Log.d(TAG, ">> getUserGender: " + value);

        return value;
    }

    public void saveUserGender(String gender) {
        if(DBG) Log.d(TAG, ">> getUserGender");
        mPrefEditor.putString(KEY_FB_GENDER, gender).apply();
    }

    // save last Facebook ID and TOKEN
    public void saveFacebookInfo(String fbId, String fbToken) {

        if(DBG) Log.d(TAG, ">> saveFacebookInfo: " + fbId + ", " + fbToken);
        mPrefEditor.putString(KEY_FB_ID, fbId).putString(KEY_FB_TOKEN, fbToken).apply();
    }


    public class FacebookInfo {
        public String id;
        public String token;
    }

    public FacebookInfo getFacebookInfo(){
        FacebookInfo fbInfo = new FacebookInfo();
        fbInfo.id = mSharedPreferences.getString(KEY_FB_ID, null);
        fbInfo.token = mSharedPreferences.getString(KEY_FB_TOKEN, null);

        if(DBG) Log.d(TAG, ">> getFacebookInfo: " + fbInfo.id + ", " + fbInfo.token);
        return fbInfo;
    }

    public void saveLastPictureDataUri(Uri uri) {
        if(DBG) Log.d(TAG, ">> saveLastPictureDataUri: " + uri.toString());
        mPrefEditor.putString(KEY_PICTURE_URI, uri.toString()).apply();
    }

    public Uri getLastPictureDataUri() {

        String uriString = mSharedPreferences.getString(KEY_PICTURE_URI, null);
        Uri uri = null;
        if(uriString != null) {
            uri = Uri.parse(uriString);

        }

        if(DBG) Log.d(TAG, ">> getLastPictureDataUri: " + uriString);

        return uri;
    }

    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";


    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

}
