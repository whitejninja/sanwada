package sanwada.v1.bootstrap;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

public class SanwadaEventListener implements ApplicationEventListener {

    @Override
    public void onEvent(ApplicationEvent event) {
        switch (event.getType()) {
            case INITIALIZATION_FINISHED:
                System.out.println(event.getResourceConfig().getApplicationName() + " initialized.");
                break;
            case DESTROY_FINISHED:
                System.out.println(event.getResourceConfig().getApplicationName() + " destroyed.");
                break;
            default:
                break;
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        // No listeners per request implemented yet
        return null;
    }

}
