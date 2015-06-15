package observable.map;

public class Empty_Case extends abstr_Case {
	public Empty_Case(){}

	@Override
	public abstr_Case Clone() {
		return new Empty_Case();
	}
}
