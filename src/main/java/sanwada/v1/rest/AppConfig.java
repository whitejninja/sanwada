package sanwada.v1.rest;

import javax.ws.rs.core.Application;

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
        return classes;

    }
}
