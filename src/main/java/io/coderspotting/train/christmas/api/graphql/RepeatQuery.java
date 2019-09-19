package io.coderspotting.train.christmas.api.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class RepeatQuery implements GraphQLQueryResolver
{
    public String repeat(String keyword)
    {
        return keyword + " " + keyword;
    }
}
