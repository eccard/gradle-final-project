package com.udacity.gradle.builditbigger.backend;


import com.eccard.joker.Joker;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;


/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.endpoints.migration.example.com",
                ownerName = "backend.endpoints.migration.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name + "!");

        return response;
    }

    private Joker joker = new Joker();

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "tellJoke", httpMethod = ApiMethod.HttpMethod.GET)
    public MyBean tellJoke() {
        MyBean response = new MyBean();
        response.setData(joker.getJoker());

        return response;
    }

}
