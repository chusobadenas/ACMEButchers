package com.acmebutchers.app.presentation.tweets;

import android.view.View;

import com.acmebutchers.app.AndroidApplicationTest;
import com.acmebutchers.app.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Collections;
import java.util.Date;

import twitter4j.Status;
import twitter4j.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(application = AndroidApplicationTest.class, constants = BuildConfig.class, sdk = 21)
public class TweetsAdapterTest {

  private TweetsAdapter adapter;

  @Mock
  private Status tweet;
  @Mock
  private User user;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    when(user.getScreenName()).thenReturn("John Doe");
    when(user.getProfileImageURL()).thenReturn("imageUrl");
    when(user.getName()).thenReturn("johndoe");
    when(tweet.getUser()).thenReturn(user);
    when(tweet.getCreatedAt()).thenReturn(new Date());
    when(tweet.getText()).thenReturn("Meat is healthy!");

    adapter = new TweetsAdapter(RuntimeEnvironment.application, Collections.singletonList(tweet));
  }

  @Test
  public void testGetCountSuccess() {
    assertEquals(adapter.getCount(), 1);
  }

  @Test
  public void testGetItemSuccess() {
    Status item = adapter.getItem(0);

    assertNotNull(item);
    assertEquals(item.getText(), "Meat is healthy!");
  }

  @Test
  public void testGetItemIdSuccess() {
    assertEquals(adapter.getItemId(0), 0);
  }

  @Test
  public void testGetViewFirstTime() {
    View view = adapter.getView(0, null, null);

    assertNotNull(view);
    verify(tweet).getUser();
    verify(user).getProfileImageURL();
    verify(user).getScreenName();
    verify(tweet).getCreatedAt();
    verify(user).getName();
    verify(tweet).getText();
  }
}
