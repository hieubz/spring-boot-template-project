package com.example.demo.core.service;

import com.example.demo.core.adapter.DemoAdapterForTest;
import com.example.demo.core.domain.Post;
import com.example.demo.shared.utils.UtilForTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * 1. Use @RunWith(PowerMockRunner.class) to work with PowerMock
 * 2. Use @PrepareForTest to mock/verify static methods
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({UtilForTest.class, DemoServiceForTest.class})
public class PowerMockCommonUnitTest {

  @InjectMocks private DemoServiceForTest service;

  @Mock private DemoAdapterForTest adapter;

  @Before
  public void setUp() {
    // set up @PrepareForTest and PowerMockito.mockStatic || PowerMockito.spy
    // with the class which contains static method that we will create mocks
    PowerMockito.spy(UtilForTest.class);
  }

  /**
   * - mock a static method to return
   * - verify a static method
   */
  @Test
  public void mockStaticMethodReturnTest() {
    String mockedString = "Mocked String";
    // mock static method to return
    when(UtilForTest.nonVoidStaticMethod(anyString())).thenReturn(mockedString);
    String result = service.feature1("");
    assertEquals(mockedString, result);

    // verify instance method
    verify(adapter, times(1)).voidMethod(anyString());

    // verify static method
    PowerMockito.verifyStatic(UtilForTest.class, times(1));
    UtilForTest.voidStaticMethod();
  }

  /** mock a static method to throw exception */
  @Test
  public void mockStaticMethodThrowExceptionTest() {
    // mock static method to throw exceptions
    when(UtilForTest.nonVoidStaticMethod(anyString())).thenThrow(new RuntimeException());
    try {
      service.feature2("");
    } catch (Exception e) {
      // verify static method calls
      PowerMockito.verifyStatic(UtilForTest.class, times(1));
      UtilForTest.voidStaticMethod();
    }
  }

  /**
   * - mock a void static method to throw exception
   * - verify a void static method
   */
  @Test
  public void mockVoidStaticMethodThrowExceptionTest() throws Exception {
    PowerMockito.doThrow(new NullPointerException()).when(UtilForTest.class, "voidStaticMethod2", anyString());
    try {
      service.feature3("");
    } catch (NullPointerException e) {
      PowerMockito.verifyStatic(UtilForTest.class, never());
      UtilForTest.voidStaticMethod();
    }
  }

  /** mock a void static method to do nothing */
  @Test
  public void mockVoidStaticMethodDoNothingTest() throws Exception {
    PowerMockito.doNothing().when(UtilForTest.class, "voidStaticMethod2", anyString());

    service.feature3("");

    PowerMockito.verifyPrivate(UtilForTest.class, times(1)).invoke("privateVoidStaticMethod");
  }

  /** verify a private method
   * Notes:
   *  - must use PowerMockito.spy instead of Mockito.spy
   */
  @Test
  public void verifyPrivateMethodTest() throws Exception {
    DemoServiceForTest spiedService = PowerMockito.spy(service);
    spiedService.feature4();

    PowerMockito.verifyPrivate(spiedService, times(1)).invoke("privateMethod");
  }

  /**
   * - verify a private static method
   * - verify a private void static method
   */
  @Test
  public void verifyPrivateStaticMethodTest() throws Exception {
    service.feature3("");
    PowerMockito.verifyPrivate(UtilForTest.class, times(1)).invoke("privateStaticMethod");
    PowerMockito.verifyPrivate(UtilForTest.class, times(2)).invoke("privateVoidStaticMethod");
  }

  /** mock a private method to return by 2 ways:
   * - when(...) thenReturn(...) makes a real method call just before the specified value will be returned
   *- doReturn(...) when(...) does not call the method at all
   */
  @Test
  public void mockPrivateMethodReturnTest() throws Exception {
    // must use PowerMockito.spy instead of Mockito.spy
    DemoServiceForTest spiedService = PowerMockito.spy(service);
    String mockedStr = "mocked string";
    // we can use doReturn || when + thenReturn
    PowerMockito.when(spiedService, "privateMethod").thenReturn(mockedStr);
    // PowerMockito.doReturn(mockedStr).when(spiedService, "privateMethod");

    String result = spiedService.feature4();

    assertEquals(mockedStr, result);
  }

  /** mock a private static method to return */
  @Test
  public void mockPrivateStaticMethodReturnTest() throws Exception {
    String mockedStr = "mocked string";
    PowerMockito.when(UtilForTest.class, "privateStaticMethod").thenReturn(mockedStr);

    String result = service.feature1("");

    assertEquals(mockedStr, result);
  }

  /** mock a private method to do nothing
   *  Notes:
   *   - Only void methods can doNothing()!
   */
  @Test
  public void mockPrivateMethodDoNothingTest() throws Exception {
    DemoServiceForTest spiedService = PowerMockito.spy(service);
    PowerMockito.doNothing().when(spiedService, "privateMethod2", anyString());
    spiedService.feature5();
    PowerMockito.verifyPrivate(spiedService, never()).invoke("privateMethod");
  }

  /** mock a private static method to do nothing
   * Notes:
   *  - Only void methods can doNothing()!
   */
  @Test
  public void mockPrivateStaticMethodDoNothingTest() throws Exception {
    PowerMockito.doNothing().when(UtilForTest.class, "privateVoidStaticMethod");
    service.feature6("");
    PowerMockito.verifyPrivate(UtilForTest.class, never()).invoke("privateVoidStaticMethod2");
  }

  /** mock WhenNew objects */
  @Test
  public void mockWhenNewObjectTest() throws Exception {
    Post mockedPost = PowerMockito.mock(Post.class);
    PowerMockito.whenNew(Post.class).withNoArguments().thenReturn(mockedPost);
    service.feature7();
    verify(mockedPost, times(1)).setBody(anyString());
  }
}
