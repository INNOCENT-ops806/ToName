package toname;

import blazing.https.BlazingRequest;

/**
 *
 * @author hexaredecimal
 */

public class Gemini {

	private static final String MODEL_ID = "gemini-2.5-pro-preview-03-25";
	private static final String GENERATE_CONTENT_API = "streamGenerateContent";

	public static void process(String term) {
		final String GEMINI_API_KEY = System.getenv("GEMINI_API_KEY");
		String url = String.format("https://generativelanguage.googleapis.com/v1beta/models/%s:%s?key=%s", MODEL_ID, GENERATE_CONTENT_API, GEMINI_API_KEY);
		var result = BlazingRequest.post(url, "application/json", String.format(getSysPrompt(), term));
		System.out.println("" + result);
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
