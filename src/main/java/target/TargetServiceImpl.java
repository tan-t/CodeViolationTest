package target;

public class TargetServiceImpl {
	
	@Autowired
	private Object otherService;
	
	private static final String CODE = "TEST";
	
	/**
	 * this field should be fail the test as it is violating the rule...
	 */
	private Object mutableField;
	

}
