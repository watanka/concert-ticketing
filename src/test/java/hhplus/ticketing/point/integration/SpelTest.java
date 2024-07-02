package hhplus.ticketing.point.integration;

import hhplus.ticketing.domain.point.models.Point;
import hhplus.ticketing.domain.point.models.PointType;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;

public class SpelTest {

    ExpressionParser parser = new SpelExpressionParser();

    @Test
    void spel_execute_string_command(){
        assertThat(parser.parseExpression("'Hello World'.toUpperCase()").getValue())
                .isEqualTo("HELLO WORLD");
    }

    @Test
    void spel_usecase2(){
        // Create and set a calendar
        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);

        //  The constructor arguments are name, birthday, and nationality.
        Point point = new Point(1000, PointType.USE);

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("amount");
        EvaluationContext context = new StandardEvaluationContext(point);

        System.out.println(exp.getValue(context));
    }

    @Test
    void 파라미터가_없이_함수_호출(){
        assertThat(funcWithoutParam(new String[]{"hey", "hi"})).hasSize(2);
    }

    String[] funcWithoutParam(String[] args){
        return args;
    }
}
