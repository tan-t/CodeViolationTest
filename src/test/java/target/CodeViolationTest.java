package target;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CodeViolationTest {

	@Parameters(name="{0}")
	public static Iterable<Object[]> data(){
		// this is just a mock, we should use some kind of implementation finder
		return Arrays.asList(new Object[][]{
				new Object[]{
				"target.TargetServiceImpl"
		},new Object[]{
				"target.NonTargetServiceImpl"
		}});
	}
	
	private final String className;

	public CodeViolationTest(String className) {
		super();
		this.className = className;
	}
	
	@Test
	public void test(){
		try {
			Class<?> clazz = this.getClass().getClassLoader().loadClass(className);
			List<String> errors = Arrays.stream(clazz.getDeclaredFields())
			.filter(f->Objects.isNull(f.getDeclaredAnnotation(Autowired.class)))
			.filter(f->!Modifier.isFinal(f.getModifiers()))
			.map(Field::getName)
			.collect(Collectors.toList());
			
			assertThat(errors.toString(),is("[]"));
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
}
