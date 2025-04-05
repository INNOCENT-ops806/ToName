package toname;

import blazing.https.BlazingRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author hexaredecimal
 */

public class Gemini {

	private static final String MODEL_ID = "gemini-2.0-flash-thinking-exp-01-21";
	private static final String GENERATE_CONTENT_API = "streamGenerateContent";

	public static String process(String term) {
		final String GEMINI_API_KEY = System.getenv("GEMINI_API_KEY");
		String url = String.format("https://generativelanguage.googleapis.com/v1beta/models/%s:%s?key=%s", MODEL_ID, GENERATE_CONTENT_API, GEMINI_API_KEY);
		var result = BlazingRequest.post(url, "application/json", String.format(getSysPrompt(), term));
		if (result.isErr()) {
			System.out.println("ERR: " + result);
			return null;
		}

		JSONTokener tokens = new JSONTokener(result.unwrap());
		JSONArray arr = new JSONArray(tokens);
		var obj = arr.getJSONObject(0);
		JSONArray candidates = obj.getJSONArray("candidates");
		obj = candidates.getJSONObject(0);
		obj = obj.getJSONObject("content");
		candidates = obj.getJSONArray("parts");
		obj = candidates.getJSONObject(0);
		return obj.getString("text").replaceAll("```html\n", "");
	}

	private static String getSysPrompt() {
		return """
{
      "contents": [
        {
          "role": "user",
          "parts": [
            {
              "text": "%s"
            },
          ]
        },
      ],
      "systemInstruction": {
        "parts": [
          {
              "text": "you have been designing a website for 20 years, when user prompt for you to create a website you use your skills you have acquired for those years, do not ask the user any questions just develop the websites. Do not use any fancy libraries, just use html and css and js inside script tags. Use modern development standards. Generate the website code, nothing"
          },
        ]
      },
      "generationConfig": {
        "responseMimeType": "text/plain",
      },
  }  
    """;
	}
}
