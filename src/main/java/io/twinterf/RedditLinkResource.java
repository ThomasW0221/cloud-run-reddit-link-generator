package io.twinterf;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.twinterf.pojos.ListOfLinks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Path("/links")
@Produces(MediaType.APPLICATION_JSON)
public class RedditLinkResource {

    private ObjectMapper om = new ObjectMapper();

    @GET
    @Path("/{subredditName}")
    public ListOfLinks getLinks(@PathParam("subredditName") String subredditName) throws IOException, InterruptedException {
        var links = new ArrayList<String>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.reddit.com/r/" + subredditName + "/hot/.json?count=20"))
                .header("User-agent", "reddit-link-generator v1.0")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseString = response.body();
        JsonNode jsonResponse = om.readTree(responseString);
        JsonNode listOfPosts = jsonResponse.get("data").get("children");
        listOfPosts.forEach((node) -> {
            node = node.get("data");
            if(!node.get("is_self").booleanValue()) {
                links.add(node.get("url").textValue());
            }
        });
        return new ListOfLinks(links);
    }
}
