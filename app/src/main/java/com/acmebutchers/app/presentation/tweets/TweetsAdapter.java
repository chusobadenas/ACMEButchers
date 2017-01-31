package com.acmebutchers.app.presentation.tweets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acmebutchers.app.R;
import com.acmebutchers.app.common.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import twitter4j.Status;
import twitter4j.User;
import twitter4j.util.TimeSpanConverter;

public class TweetsAdapter extends BaseAdapter {

  private final Context context;
  private final List<Status> tweets;

  public TweetsAdapter(Context context, List<Status> tweets) {
    super();
    this.context = context;
    this.tweets = tweets;
  }

  @Override
  public int getCount() {
    return tweets.size();
  }

  @Override
  public Status getItem(int position) {
    return tweets.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Reuse view
    ViewHolder holder;
    LinearLayout currentView = (LinearLayout) convertView;

    if (currentView == null) {
      currentView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.custom_tweet, null);
      holder = new ViewHolder(currentView);
      currentView.setTag(holder);
    } else {
      holder = (ViewHolder) currentView.getTag();
    }

    // Get item
    Status tweet = getItem(position);
    User user = tweet.getUser();

    // Display elements
    UIUtils.loadImageUrl(context, holder.avatarImageView, user.getProfileImageURL());
    holder.fullNameTextView.setText(user.getScreenName());
    String tweetTime = new TimeSpanConverter().toTimeSpanString(tweet.getCreatedAt());
    holder.nameTextView.setText(user.getName() + " · " + tweetTime);
    holder.statusTextView.setText(tweet.getText());

    return currentView;
  }

  class ViewHolder {

    @BindView(R.id.tweet_avatar)
    ImageView avatarImageView;
    @BindView(R.id.tweet_full_name)
    TextView fullNameTextView;
    @BindView(R.id.tweet_name)
    TextView nameTextView;
    @BindView(R.id.tweet_status)
    TextView statusTextView;

    ViewHolder(View view) {
      ButterKnife.bind(this, view);
    }
  }
}
