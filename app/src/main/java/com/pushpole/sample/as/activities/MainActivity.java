package com.pushpole.sample.as.activities;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.pushpole.sdk.PushPole;
import com.pushpole.sample.as.R;
import com.pushpole.sample.as.eventbus.MessageEvent;
import com.pushpole.sample.as.utils.Stuff;

import static com.pushpole.sample.as.utils.Stuff.addText;

/**
 * For further information Go to <a href="https://docs.push-pole.com">Docs</a>
 *
 * @author Mahdi Malvandi
 */
@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.statusContainer)
    ScrollView scroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        try {
            toolbar.setSubtitle("v" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName + " || click each icon to see info");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        status.setText("Click an action to test it.\nClick the info to see information.\n");

        PushPole.initialize(this, true);

        setupList();

        // Clear on long click
        status.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                status.setText("Click an action to test it.\nClick the info to see information.\n");
                return true;
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    /**
     * Set up recyclerView and add items to it.
     * Checkout class {@link Stuff} to see all util methods.
     */
    private void setupList() {
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new Adapter(
                Stuff.listOf(
                        "Manually Initialize PushPole",
                        "Check initialized",
                        "Get PushPoleId",
                        "Subscribe to topic",
                        "Unsubscribe from topic",
                        "Send notification to user",
                        "Send advanced notification to user",
                        "Send event"
                ),
                handleItemClick(),
                handleInfoClicked()
        ));
    }

    /**
     * Each clicked item have to do something when clicked.
     * Adapter takes an interface and calls it when an item was clicked.
     * This function does the work and returns an interface.
     */
    private ItemClickListener handleItemClick() {
        return new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (position) {
                    case 0: // Initialize
                        addText(status, "Initializing PushPole");
                        PushPole.initialize(MainActivity.this, true);
                        scroll.fullScroll(View.FOCUS_DOWN);
                        break;
                    case 1: // Check init
                        addText(status, "PushPole initialized: " + PushPole.isPushPoleInitialized(MainActivity.this));
                        scroll.fullScroll(View.FOCUS_DOWN);
                        break;
                    case 2: // PushPoleId
                        addText(status, "PushPoleId is: " + PushPole.getId(MainActivity.this));
                        scroll.fullScroll(View.FOCUS_DOWN);
                        break;
                    case 3: // Topic
                        Stuff.prompt(MainActivity.this,
                                "Subscribe to Topic",
                                "Enter topic name (Must be english character)",
                                new Consumer<String>() {
                                    @Override
                                    public void accept(String s) {
                                        PushPole.subscribe(MainActivity.this, s);
                                        addText(status, "Subscribe to topic: " + s);
                                        scroll.fullScroll(View.FOCUS_DOWN);
                                    }
                                });
                        break;
                    case 4: // Unsubscribe
                        Stuff.prompt(MainActivity.this,
                                "Unsubscribe from Topic",
                                "Enter topic name",
                                new Consumer<String>() {
                                    @Override
                                    public void accept(String s) {
                                        PushPole.unsubscribe(MainActivity.this, s);
                                        addText(status, "Unsubscribe from topic: " + s);
                                        scroll.fullScroll(View.FOCUS_DOWN);
                                    }
                                });
                        break;
                    case 5: // Send to user
                        Stuff.prompt(MainActivity.this,
                                "Send simple notification to user",
                                "Enter pushPoleId\nMessage:{title:title1, content:content1}",
                                PushPole.getId(MainActivity.this),
                                new Consumer<String>() {
                                    @Override
                                    public void accept(String pusheId) {
                                        PushPole.sendSimpleNotifToUser(MainActivity.this, pusheId, "title1", "content1");
                                        addText(status, "Sending simple notification to user.\ntitle: title1\ncontent: content1\nPushPoleId: " + pusheId);
                                        scroll.fullScroll(View.FOCUS_DOWN);
                                    }
                                });
                        break;
                    case 6: // send advanced to user
                        Stuff.prompt(MainActivity.this,
                                "Send advanced notification to user",
                                "Enter pushPoleId\nMessage:{title:title1, content:content1}",
                                PushPole.getId(MainActivity.this),
                                new Consumer<String>() {
                                    @Override
                                    public void accept(String pusheId) {
                                        try {
                                            JSONObject object = new JSONObject();
                                            object.put("title", "title1");
                                            object.put("content", "content1");
                                            PushPole.sendAdvancedNotifToUser(MainActivity.this, pusheId, object.toString());
                                            addText(status, "Sending advanced notification:\nNotification: " + object.toString() + "\nPushPoleId: " + pusheId);
                                            scroll.fullScroll(View.FOCUS_DOWN);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                        );
                        break;
                    case 7: // send event
                        Stuff.prompt(MainActivity.this,
                                "Send event",
                                "Enter event name to send",
                                new Consumer<String>() {
                                    @Override
                                    public void accept(String event) {
                                        PushPole.sendEvent(MainActivity.this, event);
                                        addText(status, "Sending event: " + event);
                                        scroll.fullScroll(View.FOCUS_DOWN);
                                    }
                                });
                }
            }
        };
    }

    private ItemClickListener handleInfoClicked() {
        return new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (position) {
                    case 0: // Initialize manually
                        Stuff.alert(MainActivity.this,
                                "initialize(context,showDialog)",
                                "Register this device to pushPole and get FCM token from server.\nSecond argument: If googlePlay does not exist or not updated it will show a dialog to install (if set to true)");
                        break;
                    case 1: // Check initialized
                        Stuff.alert(MainActivity.this,
                                "isPushPoleInitialized(context)",
                                "Returns true if registration is successful and token is saved.");
                        break;
                    case 2: // getId
                        Stuff.alert(MainActivity.this,
                                "getId(context)",
                                "Returns a unique id according to androidId and googleAdId which can be used to identify device.");
                        break;
                    case 3: // subscribe
                        Stuff.alert(MainActivity.this,
                                "subscribe(topicName)",
                                "If you want to add user to a specific group (for example premium), you can subscribe them into a topic.");
                        break;
                    case 4: // unsubscribe
                        Stuff.alert(MainActivity.this,
                                "unsubscribe(topicName)",
                                "If you want to remove user from a specific group (for example premium), you can unsubscribe them from that topic.");
                        break;
                    case 5: // send to user
                        Stuff.alert(MainActivity.this,
                                "sendSimpleNotifToUser(context,pushPoleId,title,content)",
                                "Having a pushPoleId of a device, you can send title and content to that device programmatically.");
                        break;
                    case 6: // send advanced to user
                        Stuff.alert(MainActivity.this,
                                "sendAdvancedNotifToUser(context,pushPoleId,notificationInJsonFormatAsString)",
                                "Having a pushPoleId of a device, you can send complete notification in json format (jsonObject.toString()) to that device programmatically.");
                        break;
                    case 7: // send event
                        Stuff.alert(MainActivity.this,
                                "sendEvent(context,eventName)",
                                "If anything has happened in the device, you can send it using this function.");

                }
            }
        };
    }

    // region EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        addText(status, event.getMessage());
        scroll.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    // endregion

    // region List

    // List adapter
    class Adapter extends RecyclerView.Adapter<Holder> {

        private List<String> dataSet;
        private ItemClickListener listener, infoListener;

        Adapter(List<String> dataSet, ItemClickListener listener, ItemClickListener infoListener) {
            this.dataSet = dataSet;
            this.listener = listener;
            this.infoListener = infoListener;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final Holder holder, int i) {
            final int position = i;
            holder.action.setText(dataSet.get(position));
            holder.action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view, position);
                }
            });
            holder.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    infoListener.onItemClick(view, position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }

    // List view holder
    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.text) TextView action;
        @BindView(R.id.info) ImageView info;

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    interface ItemClickListener {
        void onItemClick(View v, int position);
    }

    // endregion

}
