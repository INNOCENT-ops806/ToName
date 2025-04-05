package toname;

import blazing.BlazingResponse;
import blazing.Get;
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
			.add(new Scarfold(index));
		
		response.sendUiRespose(page);
	}
}
