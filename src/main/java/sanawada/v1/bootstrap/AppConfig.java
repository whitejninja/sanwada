package sanawada.v1.bootstrap;

import javax.ws.rs.core.Application;

import sanwada.v1.rest.FooResource;
import sanwada.v1.rest.QuestionResource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

@ApplicationPath(value = "/v1")
public class AppConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {

    final Set<Class<?>> classes = new HashSet<>();
    // register root resource classes
    classes.add(QuestionResource.class);
    classes.add(FooResource.class);
    return classes;

  }
}
