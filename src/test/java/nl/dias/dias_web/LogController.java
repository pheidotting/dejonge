package nl.dias.dias_web;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;

import nl.dias.dias_web.domein.LogObject;

@Path("/log4javascript")
public class LogController {

	@POST
	@Path("/loggen")
	public void loggen(MultivaluedMap<String, String> formParams) {

		List<String> level = formParams.get("level");
		List<String> message = formParams.get("message");
		List<String> timestamp = formParams.get("timestamp");
		List<String> url = formParams.get("url");

		for (int i = 0; i < level.size(); i++) {
			System.out.println(new LogObject(level.get(i), message.get(i), timestamp.get(i), url.get(i)));
		}
	}
}
