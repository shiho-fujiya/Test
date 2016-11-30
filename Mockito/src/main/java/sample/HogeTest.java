//package sample;
//
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//@RunWith(JUnit4.class)
//public class HogeTest {
//
//	@Mock(name = "nameList")
//	private List list;
//
//	@InjectMocks
//	private Hoge hoge = new Hoge();
//
//	@Before
//	public void setup() {
//		MockitoAnnotations.initMocks(this);
//	}
//
//	@Test
//	public void test_hoge () {
//
//		//実行
//		hoge.doSomething();
//
//		//検証
//		Mockito.verify(list).addAll(Mockito.anyCollection());
//	}
//}
