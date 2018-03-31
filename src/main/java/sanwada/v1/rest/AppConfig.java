package sanwada.v1.rest;

import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

@ApplicationPath(value = "/v1")
public class AppConfig extends Application{

  public AppConfig() {
    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setVersion("v1");
    beanConfig.setSchemes(new String[] {"http"});
    beanConfig.setHost("localhost:8080");
    beanConfig.setBasePath("/v1/sanwada");
    beanConfig.setResourcePackage(QuestionResource.class.getPackage().getName());
    beanConfig.setScan(true);
  }

  @Override
  public Set<Class<?>> getClasses() {

    final Set<Class<?>> classes = new HashSet<>();

    // Register root resource classes
    classes.add(QuestionResource.class);
    classes.add(AnswerResource.class);

    // Swagger core providers
    classes.add(io.swagger.jaxrs.listing.ApiListingResource.class);
    classes.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
    return classes;

  }
}
