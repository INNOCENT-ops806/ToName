package toname;

import blazing.BlazingResponse;
import blazing.Get;
import blazing.Post;
import blazing.WebServer;
import blazing.fs.FileSystem;
import webx.Html;
import webx.Scarfold;

/**
 *
 * @author hexaredecimal
 */
@WebServer("8080")
public class BackServer {

	@Get("/")
	public static void home(BlazingResponse response) {
		var index = FileSystem.readFileToString("./templates/index.html").unwrap();
		var page = new Html()
			.addHeaderStyleLink("https://cdn.jsdelivr.net/npm/daisyui@5")
			.addHeaderScript("https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4")
			.addHeaderStyleLink("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css")
			.addHeaderScript("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/js/all.js")
			.addHeaderScript("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/fontawesome.css")
			.add(new Scarfold(index));

		response.sendUiRespose(page);
	}

	@Post("/v1/api/llm/gen")
	public static void generate(BlazingResponse response) {
		String term = response.params().get("term");
		
		if (term.isEmpty()) {
			response.setHeader("Location", "/");
			response.sendStatus(302);
			return;
		}
		
		Gemini.process(term);
		response.sendResponse("Hello world");
	}
}
