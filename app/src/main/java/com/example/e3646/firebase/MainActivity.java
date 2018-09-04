package com.example.e3646.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String mEmail = "bbb@gmail";
    private String mKey = "-LLXV9MZgrADQceYmH_s";
    private String mFriendKey;
    private String mFriendsEmail;
    private String mRequestKey;

    private Button mButton;
    private Button mButtonCreateArticle;
    private Button mButtonSearchArticle;
    private Button mButtonInvitation;
    private Button mButtonAccept;
    private Button mButtonReject;
    private Button mButtonTagBeauty;
    private Button mButtonTagGossip;
    private Button mButtonTagJoke;
    private Button mButtonTagLive;

    private EditText mUserEmail;
    private EditText mUserName;
    private EditText mArticleTitle;
    private EditText mArticleContent;
    private EditText mArticleTag;
    private EditText mSearchAuthor;
    private EditText mSearchTag;
    private EditText mFriendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        ////////////////////      註冊    /////////////////////////

        mUserEmail = findViewById(R.id.edit_email);
        mUserName = findViewById(R.id.edit_name);
        mButton = findViewById(R.id.button_create_user);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUser();
                mUserEmail.setText("");
                mUserName.setText("");

            }
        });

        /////////////////////     發文    //////////////////////////

        mArticleContent = findViewById(R.id.edit_article_content);
        mArticleTitle = findViewById(R.id.edit_article_title);
        mArticleTag = findViewById(R.id.edit_article_tag);

        mButtonTagBeauty = findViewById(R.id.button_tag_beauty);
        mButtonTagBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mArticleTag.setText("表特");
                mSearchTag.setText("表特");
            }
        });

        mButtonTagGossip = findViewById(R.id.button_tag_gossip);
        mButtonTagGossip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mArticleTag.setText("八卦");
                mSearchTag.setText("八卦");
            }
        });

        mButtonTagJoke = findViewById(R.id.button_tag_joke);
        mButtonTagJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mArticleTag.setText("就可");
                mSearchTag.setText("就可");
            }
        });

        mButtonTagLive = findViewById(R.id.button_tag_live);
        mButtonTagLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mArticleTag.setText("生活");
                mSearchTag.setText("生活");
            }
        });

        mButtonCreateArticle = findViewById(R.id.button_create_article);
        mButtonCreateArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createArticle();
                mArticleContent.setText("");
                mArticleTitle.setText("");
                mArticleTag.setText("");
            }
        });

        /////////////////////     搜尋文章    //////////////////////////

        mSearchAuthor = findViewById(R.id.search_article_author);
        mSearchTag = findViewById(R.id.search_article_tag);
        mButtonSearchArticle = findViewById(R.id.button_search_article);
        mButtonSearchArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchArticle();
                mSearchAuthor.setText("");
                mSearchTag.setText("");
            }
        });

        /////////////////////     好友邀請    //////////////////////////

        mFriendEmail = findViewById(R.id.edit_friend_email);
        mButtonInvitation = findViewById(R.id.button_send_invitation);
        mButtonInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendInvitation();
                mFriendEmail.setText("");

            }
        });

        mButtonAccept = findViewById(R.id.button_accept);
        mButtonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                acceptInvitation();

            }
        });

        mButtonReject = findViewById(R.id.button_reject);
        mButtonReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rejectInvitation();

            }
        });
    }

    public void createUser() {

        Firebase firebase = new Firebase("https://appwork-school-ptt.firebaseio.com/");

        String key = firebase.child("member").push().getKey();
        String email = mUserEmail.getText().toString();
        String name = mUserName.getText().toString();

        this.mEmail = email;
        this.mKey = key;

        Firebase member = firebase.child("member").child(key);
        member.child("user_email").setValue(email);
        member.child("user_name").setValue(name);

        Log.d("►►►CreateUser", "key: " + key + ", email: " + email + ", name: " + name);

    }

    public void createArticle() {

        Firebase firebase = new Firebase("https://appwork-school-ptt.firebaseio.com/");

        String key = firebase.child("posts").push().getKey();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis()) ;
        String created_time = formatter.format(curDate);
        String article_content = mArticleContent.getText().toString();
        String article_title = mArticleTitle.getText().toString();
        String article_tag = mArticleTag.getText().toString();
        String author_tag = mEmail + article_tag;

        Article articledata = new Article(article_content, key, article_tag, article_title, mEmail, created_time, author_tag);

        Firebase article = firebase.child("posts").child(key);
        article.setValue(articledata);

        Log.d("►►►CreateArticle", "Success");

    }

    public void searchArticle() {

        Firebase firebase = new Firebase("https://appwork-school-ptt.firebaseio.com/");

        String author = mSearchAuthor.getText().toString();
        String tag = mSearchTag.getText().toString();
        String search = author + tag;

        if (!author.equals("") && !tag.equals("")) {

            Query queryArticle = firebase.child("posts").orderByChild("author_tag").equalTo(search);
            queryArticle.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("►►►SearchArticle by author and tag", "article: " + dataSnapshot.toString());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        } else if (!author.equals("") && tag.equals("")) {

            Query queryArticle = firebase.child("posts").orderByChild("author").equalTo(author);
            queryArticle.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("►►►SearchArticle by author", "article: " + dataSnapshot.toString());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        } else if (author.equals("") && !tag.equals("")) {

            Query queryArticle = firebase.child("posts").orderByChild("article_tag").equalTo(tag);
            queryArticle.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("►►►SearchArticle by tag", "article: " + dataSnapshot.toString());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }

    public void sendInvitation() {

        final Firebase firebase = new Firebase("https://appwork-school-ptt.firebaseio.com/");
        mFriendsEmail = mFriendEmail.getText().toString();

        Query queryUser = firebase.child("member").orderByChild("user_email").equalTo(mFriendsEmail);
        queryUser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                mFriendKey = dataSnapshot.getKey();

                Firebase mine = firebase.child("member").child(mKey);
                mine.child("friend").child(mFriendKey).child("invite_status").setValue("pending_send");

                Firebase friend = firebase.child("member").child(mFriendKey);
                friend.child("friend").child(mKey).child("invite_status").setValue("pending_confirm");

                Log.d("►►►SendInvitation", "key: " + dataSnapshot.getKey());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void acceptInvitation() {

        final Firebase firebase = new Firebase("https://appwork-school-ptt.firebaseio.com/");
        Query queryInvitation = firebase.child("member").child(mKey).child("friend").orderByChild("invite_status").equalTo("pending_confirm");
        queryInvitation.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                mRequestKey = dataSnapshot.getKey();
                Firebase mine = firebase.child("member").child(mKey);
                mine.child("friend").child(mRequestKey).child("invite_status").setValue("Valid");

                Firebase friend = firebase.child("member").child(mRequestKey);
                friend.child("friend").child(mKey).child("invite_status").setValue("Valid");

                Log.d("►►►AcceptInvitation", "key: " + dataSnapshot.getKey());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void rejectInvitation() {

        final Firebase firebase = new Firebase("https://appwork-school-ptt.firebaseio.com/");
        Query queryInvitation = firebase.child("member").child(mKey).child("friend").orderByChild("invite_status").equalTo("pending_confirm");
        queryInvitation.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                mRequestKey = dataSnapshot.getKey();
                Firebase mine = firebase.child("member").child(mKey);
                mine.child("friend").child(mRequestKey).child("invite_status").removeValue();

                Firebase friend = firebase.child("member").child(mRequestKey);
                friend.child("friend").child(mKey).child("invite_status").removeValue();

                Log.d("►►►RejectInvitation", "key: " + dataSnapshot.getKey());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
