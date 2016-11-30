package sample;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import sample.SampleLogic.TargetClass;

public class SampleLogicTest {
    @Mock
    private TargetClass mockObject;
    @InjectMocks
    private SampleLogic testTarget = new SampleLogic();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void test() {
		// generateRandamNumberメソッドのモックを作成する
		when(mockObject.generateRandamNumber()).thenReturn(2);
		String result = testTarget.checkNumberType();
		assertEquals("偶数", result);
	}

}
