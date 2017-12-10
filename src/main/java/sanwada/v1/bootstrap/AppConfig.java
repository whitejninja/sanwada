package sanwada.v1.bootstrap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletProperties;

import sanwada.v1.rest.QuestionResource;

@ApplicationPath(value = "/v1")
public class AppConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {

    final Set<Class<?>> classes = new HashSet<>();
    // Register root resource classes
    classes.add(QuestionResource.class);

    // Register jersey application event listener
    classes.add(SanwadaEventListener.class);

    return classes;
  }

  public Map<String, Object> getProperties() {
      final Map<String, Object> properties = new HashMap<String, Object>();

      properties.put(ServletProperties.JAXRS_APPLICATION_CLASS, "sanawada.v1.bootstrap.AppConfig.class");
      properties.put(ServerProperties.APPLICATION_NAME, "sanwada");
      properties.put(ServerProperties.TRACING, "ALL");
      properties.put(ServerProperties.MONITORING_ENABLED, true);
      properties.put(ServerProperties. MONITORING_STATISTICS_ENABLED, true);
      properties.put(ServerProperties.PROVIDER_PACKAGES, "sanwada.v1.rest");

      return properties;
    }
}
