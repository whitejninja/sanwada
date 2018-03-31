
package sanwada.v1.bootstrap;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

import sanwada.v1.dao.MongoDataSourceClient;

public class SanwadaEventListener implements ApplicationEventListener{

  Config config;

  @Override
  public void onEvent(ApplicationEvent event) {
    switch (event.getType()) {
      case INITIALIZATION_FINISHED:
        System.out.println(event.getResourceConfig().getApplicationName() + " initialized.");
        setUpDataSourceConnection();
        break;
      case DESTROY_FINISHED:
        System.out.println(event.getResourceConfig().getApplicationName() + " destroyed.");
        terminateDataSourceConnection();
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

  void setUpDataSourceConnection() {
    config = ConfigProvider.getConfig();
    String host = config.getValue("datasource.host", String.class);
    int port = config.getValue("datasource.port", Integer.class);
    new MongoDataSourceClient(host, port);
  }

  void terminateDataSourceConnection() {
    MongoDataSourceClient.getClient().close();
  }
}
