package sample;

public class SampleLogic {
	private final String ODD = "偶数";
	private final String EVEN = "奇数";

	//未完成クラス
	private TargetClass target;

	public class TargetClass {

		public int generateRandamNumber() {
			//TODO: 乱数を返却する
			return 1;
		}
	}


	public SampleLogic() {
		if (target == null) {
			target = new TargetClass();
		}
	}

	public String checkNumberType() {
		int randamNumber = target.generateRandamNumber();
		if (randamNumber % 2 == 0) {
			return ODD;
		} else {
			return EVEN;
		}
	}

}
