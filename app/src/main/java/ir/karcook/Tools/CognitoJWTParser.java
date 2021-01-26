package ir.karcook.Tools;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class CognitoJWTParser {
    private static final int HEADER = 0;
    private static final int PAYLOAD = 1;
    private static final int SIGNATURE = 2;
    private static final int JWT_PARTS = 3;
    /**
     * Returns header for a JWT as a JSON object.
     *
     * @param jwt       REQUIRED: valid JSON Web Token as String.
     * @return header as a JSONObject.
     */
    public static JSONObject getHeader(String jwt) {
        try {
            validateJWT(jwt);
            final byte[] sectionDecoded = Base64.decode(jwt.split("\\.")[HEADER], Base64.URL_SAFE);
            final String jwtSection = new String(sectionDecoded, "UTF-8");
            return new JSONObject(jwtSection);
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (final JSONException e) {
             e.printStackTrace();
        } catch (final Exception e) {
            Log.e("fff","error in parsing JSON");
        }
        return null;
    }

    /**
     * Returns payload of a JWT as a JSON object.
     *
     * @param jwt       REQUIRED: valid JSON Web Token as String.
     * @return payload as a JSONObject.
     */
    public static JSONObject getPayload(String jwt) {
        try {
            validateJWT(jwt);
            final String payload = jwt.split("\\.")[PAYLOAD];
            final byte[] sectionDecoded = Base64.decode(payload, Base64.URL_SAFE);
            final String jwtSection = new String(sectionDecoded, "UTF-8");
            return new JSONObject(jwtSection);
        } catch (final UnsupportedEncodingException e) {
             e.printStackTrace();
        } catch (final JSONException e) {
             e.printStackTrace();
        } catch (final Exception e) {
            Log.e("fff","error in parsing JSON");
        }
        return null;
    }

    /**
     * Returns signature of a JWT as a String.
     *
     * @param jwt       REQUIRED: valid JSON Web Token as String.
     * @return signature as a String.
     */
    public static String getSignature(String jwt) {
        try {
            validateJWT(jwt);
            final byte[] sectionDecoded = Base64.decode(jwt.split("\\.")[SIGNATURE], Base64.URL_SAFE);
            return new String(sectionDecoded, "UTF-8");
        } catch (final Exception e) {
            Log.e("fff","error in parsing JSON");
        }
        return null;
    }

    /**
     * Returns a claim, from the {@code JWT}s' payload, as a String.
     *
     * @param jwt       REQUIRED: valid JSON Web Token as String.
     * @param claim     REQUIRED: claim name as String.
     * @return  claim from the JWT as a String.
     */
    public static String getClaim(String jwt, String claim) {
        try {
            final JSONObject payload = getPayload(jwt);
            final Object claimValue = payload.get(claim);

            if (claimValue != null) {
                return claimValue.toString();
            }

        } catch (final Exception e) {
             e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks if {@code JWT} is a valid JSON Web Token.
     *
     * @param jwt REQUIRED: The JWT as a {@link String}.
     */
    public static void validateJWT(String jwt) {
        // Check if the the JWT has the three parts
        final String[] jwtParts = jwt.split("\\.");
        if (jwtParts.length != JWT_PARTS) {
           Log.e("fff","not a JSON Web Token");
        }
    }
}