package io.coderspotting.train.christmas.api.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver
{
    public String sayHello(String name)
    {
        return "Hello " + name + "!";
    }
}
