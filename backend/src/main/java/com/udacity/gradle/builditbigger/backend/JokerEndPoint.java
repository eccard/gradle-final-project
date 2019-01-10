package com.udacity.gradle.builditbigger.backend;

import com.eccard.joker.Joker;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;


/** An endpoint class we are exposing */
@Api(
        name = "jokerApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class JokerEndPoint {

    private Joker joker = new Joker();

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(
            name = "tellJoke",
            httpMethod = ApiMethod.HttpMethod.GET)
    public MyBean tellJoke() {
        MyBean response = new MyBean();
        response.setData(joker.getJoker());

        return response;
    }

}
