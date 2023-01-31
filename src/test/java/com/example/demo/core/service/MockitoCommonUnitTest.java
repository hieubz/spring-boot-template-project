package com.example.demo.core.service;

import com.example.demo.core.adapter.DefaultUserAdapter;
import com.example.demo.infrastructure.config.auth.CustomUserDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockitoCommonUnitTest {

  @InjectMocks @Spy private DefaultUserService userService;

  @Mock private DefaultUserAdapter userAdapter;

  @Before
  public void setUp() {
    // run setup for each test case
  }

  /**
   * - mock a method of a bean to return
   * - assert values
   * - verify method calling times from a bean
   */
  @Test
  public void getUserDetailByUserIdTest() {
    CustomUserDetails userDetails =
        CustomUserDetails.builder().userId(123L).username("hieupd").build();
    // mock a method in a bean to return
    when(userAdapter.getUserDetailByUserId(any())).thenReturn(userDetails);

    // call the main method
    CustomUserDetails actualUserDetails = userService.getUserDetailByUserId(1L);

    // assert values
    assertNotNull(actualUserDetails);
    assertEquals(userDetails.getUserId(), actualUserDetails.getUserId());

    // verify how many times a method was called
    verify(userAdapter, times(1)).getUserDetailByUserId(any());
  }

  /** mock a method of a bean to throw an exception */
  @Test
  public void getUserDetailByUserIdFailureTest() {
    // when().thenThrow() won't compile without a return value (non-void methods)
    when(userAdapter.getUserDetailByUserId(any())).thenThrow(new RuntimeException());
    CustomUserDetails actualUserDetails = null;
    try {
      actualUserDetails = userService.getUserDetailByUserId(1L);
      fail();
    } catch (Exception e) {
      // you can assert, verify methods call times here
      assertNull(actualUserDetails);
    }
  }

  /**
   * mock a void method to do nothing
   * Notes:
   *  - need to use @Spy to mock or verify methods of an instance
   */
  @Test
  public void mockVoidMethodDoNothingTest() {
    doNothing().when(userService).voidMethodForUnitTesting();
    userService.feature1();
    // feature2 calls == 1 because voidMethodForUnitTesting did nothing
    verify(userService, times(1)).feature2();
    verify(userService, times(1)).voidMethodForUnitTesting2();
  }

  /**
   * mock a void method to throw exception
   * Notes:
   *  - need to use @Spy to mock or verify methods of an instance
   */
  @Test
  public void mockVoidMethodThrowExceptionTest() {
    // need to use doThrow() for void methods
    doThrow(new RuntimeException()).when(userService).voidMethodForUnitTesting();
    try {
      userService.feature1();
    } catch (Exception e) {
      verify(userService, times(0)).feature2();
    }
  }
}
