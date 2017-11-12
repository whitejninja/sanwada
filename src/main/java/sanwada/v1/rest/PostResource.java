package sanwada.v1.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path(value = "post")
public class PostResource {

    @GET
    @Path(value = "test")
    public String viewAccount() {
        return "Test message";
    }  
}
