package hhplus.ticketing.point.integration;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class CustomSpringELParser {

    public CustomSpringELParser() {
    }

    public static Object getDynamicValue(String[] parameterNames, Object[] args, String key){
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (int i=0;i<parameterNames.length;i++ ) {
            context.setVariable(parameterNames[i], args[i]);
        }
        System.out.println("key : " + parser.parseExpression(key).getValue(context, Object.class));
        return parser.parseExpression(key).getValue(context, Object.class);
    }

    }
