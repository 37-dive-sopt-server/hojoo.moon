package org.sopt.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.type.BasicType;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDSLContributor implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        BasicType<?> doubleType = functionContributions
                .getTypeConfiguration()
                .getBasicTypeRegistry()
                .resolve(StandardBasicTypes.DOUBLE);

        functionContributions.getFunctionRegistry().registerPattern(
                "match_against_title",
                "match (?1) against (?2 in natural language mode)",
                doubleType
        );
    }
}
