package sample;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.exceptions.verification.SmartNullPointerException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class HelloWorld {

	//基本的な使い方
	@Test
	public void testVerify() {

		//Mockito.mock()でMockを作る
		List mockList = Mockito.mock(List.class);
		mockList.add("foo");
		mockList.clear();

		//Mockito.verify()でMockのメソッドが呼び出された事を確認する
		Mockito.verify(mockList).add("foo");
		Mockito.verify(mockList).clear();
	}

	//Mockに特定の振る舞いをさせる
	@Test
	public void testStub() {

		LinkedList mockList = Mockito.mock(LinkedList.class);

		//mockList.get(0)で"first"を返すように設定する
		Mockito.when(mockList.get(0)).thenReturn("first");

		//上記の通りに動作する事を確認する
		Assert.assertEquals(mockList.get(0), "first");

		//設定していない引数にはnullが返る
		Assert.assertEquals(mockList.get(1), null);
	}

	//特定の値ではなく型を使って振る舞いを設定する
	@Test(expected = IllegalArgumentException.class)
	public void testArgType() {

		Map mockMap = Mockito.mock(Map.class);

		//引数がString型の時は"String"を返す
		Mockito.when(mockMap.get(Mockito.anyString())).thenReturn("String");

		//引数がint型の時は例外をスローする
		Mockito.when(mockMap.get(Mockito.anyInt())).thenThrow(IllegalArgumentException.class);

		//文字列を渡した時、上記通り動作する事を確認する
		Assert.assertEquals(mockMap.get("foo"), "String");

		//数値を渡した時はIllegalArgumentExceptionがスローされる
		mockMap.get(0);
	}

	//Mockが特定の引数で呼び出された回数を検証する
	@Test
	public void testVerifyNum() {

		List mockList = Mockito.mock(List.class);

		//foo:1回
		mockList.add("foo");

		//bar:1回
		mockList.add("bar");

		//baz:2回
		mockList.add("baz");
		mockList.add("baz");

		//fooは1回呼び出された
		Mockito.verify(mockList, Mockito.times(1)).add("foo");

		//barも1回呼び出された
		Mockito.verify(mockList, Mockito.times(1)).add("bar");

		//bazは2回呼び出された
		Mockito.verify(mockList, Mockito.times(2)).add("baz");

		//hogeは0回呼び出された　(=呼び出されていない)
		Mockito.verify(mockList, Mockito.times(0)).add("hoge");

		//Mockito.never()はMockito.times(0)のシンタックスシュガー
		Mockito.verify(mockList, Mockito.never()).add("fuga");
	}

	//返り値がvoid型の時の書き方
	@Test(expected = IllegalArgumentException.class)
	public void testStubVoidMethod() {

		List mockList = Mockito.mock(List.class);

		//返り値がvoidの時はwhen()の代わりにdoXXX()で始まる
		Mockito.doThrow(IllegalArgumentException.class).when(mockList).clear();
		mockList.clear();
	}

	//呼び出し順序を検証する
	@Test
	public void testInOrderSingle() {
		List singleMock = Mockito.mock(List.class);

		//"first" -> "second"の順番で呼び出す
		singleMock.add("first");
		singleMock.add("second");

		//順序を検証するにはInOrderを使う
		InOrder inOrder = Mockito.inOrder(singleMock);

		//InOrder.verify()を順番に使う
		inOrder.verify(singleMock).add("first");
		inOrder.verify(singleMock).add("second");
	}

	//複数のモックにまたがって呼び出し順序を検証する
	@Test
	public void testInOrderMultiple() {

		List firstMock = Mockito.mock(List.class);
		firstMock.add("first");
		List secondMock = Mockito.mock(List.class);
		secondMock.add("second");

		//Mockito.inOrder()に順序を検証したいモックを全て渡す
		InOrder inOrder = Mockito.inOrder(firstMock, secondMock);
		inOrder.verify(firstMock).add("first");
		inOrder.verify(secondMock).add("second");
	}

	//Mockが呼び出されていないことを検証する
	@Test
	public void testInteractionsNeverHappend() {

		List firstMock = Mockito.mock(List.class);
		firstMock.add("first");
		List secondMock = Mockito.mock(List.class);

		//secondMock.add("second"):　//このコメントアウト(外側)を外すとテストはfailする
		Mockito.verify(firstMock).add("first");

		//firstMockの呼び出しによってsecondMockに影響が及んでいないことを確認できる
		Mockito.verifyZeroInteractions(secondMock);
	}

	//実際のオブジェクトをstubしたりverify出来るSpyにして使う
	@Test
	public void testRealObjectSpy() {

		List realObj = new LinkedList();

		//実際のオブジェクトからSpyを作る
		List spyObj = Mockito.spy(realObj);

		//size()メソッドを使うときだけ実際にはありえない値を返すように設定する
		Mockito.when(spyObj.size()).thenReturn(100);

		//Spyに中身を2つ入れる
		spyObj.add("first");
		spyObj.add("second");

		//2つしか入れてないのにsize()の返り値は100になる
		Assert.assertEquals(spyObj.size(), 100);

		//呼び出しの検証も出来る
		Mockito.verify(spyObj).add("first");
		Mockito.verify(spyObj).add("second");
		Mockito.verify(spyObj).size();
	}

	private static class AnnotationSample {

		//@MockアノテーションでMockをInject出来る
		@Mock
		private List mock;

		//@SpyアノテーションでSpyをInject出来る
		@Spy
		private List spy = new ArrayList();

		public List getMock() {
			return this.mock;
		}

		public List getSpy() {
			return this.spy;
		}
	}

	//Mockアノテーションの使い方
	@Test
	public void testMockAnnotation() {

		AnnotationSample mockable = new AnnotationSample();

		//Inject前は普通のオブジェクト
		Assert.assertEquals(mockable.getMock(), null);

		//Mock/SpyをInjectする
		MockitoAnnotations.initMocks(mockable);

		//中身がMockになった
		Mockito.when(mockable.getMock().get(0)).thenReturn("Mocked");
		Assert.assertEquals(mockable.getMock().get(0), "Mocked");
		Mockito.verify(mockable.getMock()).get(0);
	}

	//Spyアノテーションの使い方
	@Test
	public void testSpyAnnotation() {

		AnnotationSample mockable = new AnnotationSample();

		//Mock/SpyをInjectする
		MockitoAnnotations.initMocks(mockable);

		//中身がSpyになった
		mockable.getSpy().add("hoge");
		Mockito.verify(mockable.getSpy()).add("hoge");
	}

	//Mockの呼び出し毎に返り値を変更する
	@Test(expected = NoSuchElementException.class)
    public void testConsecutiveCall() {
        Iterator mockIterator = Mockito.mock(Iterator.class);
        // Iterator らしく呼び出し毎に返り値を変化させていく
        Mockito.when(mockIterator.next())
                .thenReturn("first")
                .thenReturn("second")
                .thenReturn("third")
                .thenThrow(NoSuchElementException.class);
        // 意図通りに変化するか確認する
        Assert.assertEquals(mockIterator.next(), "first");
        Assert.assertEquals(mockIterator.next(), "second");
        Assert.assertEquals(mockIterator.next(), "third");
        mockIterator.next();
    }


     // Mock の返り値をコールバック形式で設定する
    @Test
    public void testCallbackAnswer() {

        List mockList = Mockito.mock(List.class);

        // Answer インターフェースを実装すればいい
        Mockito.when(mockList.get(Mockito.anyInt())).thenAnswer(new Answer() {

            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return "index: " + args[0];
            }
        });

        Assert.assertEquals(mockList.get(100), "index: 100");
    }

    private static class Parent {

        public Child getChild() {
            return new Child();
        }
    }

    private static class Child {

        public String getMessage() {
            return "Hello, World!!";
        }
    }

     //Mock が返す値の動作を変更する
    @Test
    public void testReturnType() {

        // 実際のメソッドを呼ぶ
        Parent mock = Mockito.mock(Parent.class, Mockito.CALLS_REAL_METHODS);
        Assert.assertEquals(mock.getChild().getMessage(), "Hello, World!!");

        // 再起的にスタブを返す
        mock = Mockito.mock(Parent.class, Mockito.RETURNS_DEEP_STUBS);
        Assert.assertNull(mock.getChild().getMessage());

        // 空のモックを返す
        mock = Mockito.mock(Parent.class, Mockito.RETURNS_MOCKS);
        Assert.assertEquals(mock.getChild().getMessage(), "");

        // null をたたいても NPE の代わりにトレーサビリティに優れた SmartNPE をスローする
        mock = Mockito.mock(Parent.class, Mockito.RETURNS_SMART_NULLS);

        try {
            mock.getChild().getMessage();
        } catch(SmartNullPointerException ex) {
            ;
        }

        // null を返す
        mock = Mockito.mock(Parent.class, Mockito.RETURNS_DEFAULTS);

        try {
            mock.getChild().getMessage();
        } catch(NullPointerException ex) {
            ;
        }
    }

    private static class NotImplementsEqualsClass {

        private String name;

        public NotImplementsEqualsClass(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }


     //メソッド呼び出しの引数の検証に verify を使わず自分でやる
    @Test
    public void testCapturingArguments() {
        List mock = Mockito.mock(List.class);
        mock.add(new NotImplementsEqualsClass("foo"));

         //Object#equals() が実装されていないと実質的に等価でも比較できない
         //Mockito.verify(mock).add(new NotImplementsEqualsClass("foo"));
        // 代わりに ArgumentCaptor を使う
        ArgumentCaptor
 captor = ArgumentCaptor.forClass(Object.class);
        Mockito.verify(mock).add(captor.capture());
        NotImplementsEqualsClass argumentObject =
                (NotImplementsEqualsClass)captor.getValue();

        // ArgumentCaptor で呼び出したときの引数を取り出して比較する
        Assert.assertEquals("foo", argumentObject.getName());
    }

    private static class SampleForRealPartialMock {

        public String getFoo() {
            return "foo";
        }

        public String getBar() {
            return "bar";
        }

    }

     //部分的に実際のメソッドを呼び出す
     //Spy は本物のオブジェクトにスタブを入れる引き算
     //こちらは Mock に本物のメソッドを入れる足し算
    @Test
    public void testRealPartialMock() {
        SampleForRealPartialMock mock =
                Mockito.mock(SampleForRealPartialMock.class);

        // foo() メソッドは実際のメソッドを使う
        Mockito.when(mock.getFoo()).thenCallRealMethod();
        Assert.assertEquals("foo", mock.getFoo());
        Assert.assertNull(mock.getBar());
    }

     //Mock の状態をリセットする
    @Test
    public void testResetMock() {
        List mock = Mockito.mock(List.class);
        mock.add(1);

        // リセットしたら呼び出したこともなかったことになる
        Mockito.reset(mock);
        Mockito.verify(mock, Mockito.never()).add(1);
    }


}