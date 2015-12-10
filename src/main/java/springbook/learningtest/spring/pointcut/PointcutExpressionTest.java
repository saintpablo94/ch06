package springbook.learningtest.spring.pointcut;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class PointcutExpressionTest {
	
	@Test
	public void methodSignaturePointcut() throws NoSuchMethodException, SecurityException {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution("
				+ "public int springbook.learningtest.spring.porintcut.Target.minus"
				+ "(int,int) "
				+ "throws java.lang.Runtimeexception)");
		
		assertThat(pointcut.getClassFilter().matches(Target.class) &&
				   pointcut.getMethodMatcher().matches(Target.class.getMethod(
						   "minus", int.class, int.class), null), 
						   is(true));
		
		//System.out.println(Target.class.getMethod("minus", int.class,int.class));
	}
	
}
