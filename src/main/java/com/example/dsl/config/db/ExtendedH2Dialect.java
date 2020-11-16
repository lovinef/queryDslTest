package com.example.dsl.config.db;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class ExtendedH2Dialect extends H2Dialect {
    public ExtendedH2Dialect() {
        registerFunction("to_char", new SQLFunctionTemplate(StandardBasicTypes.STRING, "to_char(?1, ?2)"));
    }
}
