package com.android.iitfriends.badriver.Orders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.iitfriends.badriver.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    private static final String TAG = OrderAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;
    public ArrayList<Order> orderList;
    private static int holderPosition;

    private int mNumberItems;

    /**
     * The interface that receives onClick messages.
     */
    public interface ListItemClickListener{
        void onListItemClick(Order o);
    }

    /**
     * Constructor for GreenAdapter that accepts a number of items to display and the specification
     * for the ListItemClickListener.
     *
     * @param mOnClickListener
     * @param response Number of items to display in list
     */
    public OrderAdapter(ListItemClickListener mOnClickListener, String response, Context context) {
        orderList = QueryUtils.orderDataFromResponse(response, context);
        this.mOnClickListener = mOnClickListener;
        mNumberItems = orderList.size();
        holderPosition = 0;
    }

    /**
     *
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new NumberViewHolder that holds the View for each list item
     */
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.order_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        OrderViewHolder viewHolder = new OrderViewHolder(view);

        int backgroundColorForViewHolder = ColorUtils
                .getViewHolderBackgroundColorFromInstance(context, holderPosition);
        viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);

        holderPosition++;
        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: " + holderPosition);
        return viewHolder;
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param viewHolder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(OrderViewHolder viewHolder, int position) {
        Log.d(TAG, "#" + position);

        Order currentOrder = orderList.get(position);
        String reg_date = currentOrder.getRegDate();
        int src = currentOrder.getmSource();
        int dest = currentOrder.getmDestination();
        int orderNum = currentOrder.getmNumber();
        int noOfCus = currentOrder.getNoOfCustomer();
        String mCustomer = currentOrder.getCustomerId();

        String[] arr = reg_date.split(" ");
        String[] tm = arr[1].split(":");
        int hr = Integer.parseInt(tm[0].trim());
        String when = " AM";
        if(hr > 12){
            hr -= 12;
            if(hr > 9) tm[0] = String.valueOf(hr);
            else  tm[0] = "0" + String.valueOf(hr);
            when = " PM";
        }
        reg_date = "दिनांक: " + arr[0].split("-")[2] + "-" + arr[0].split("-")[1]
        + "-" + arr[0].split("-")[0];
        arr[1] = "समय: " + tm[0] + ":" + tm[1] + when;

/*

समय
रामानुजन
विश्वकर्मा
धनराजगिरी
मोर्वी
लिम्बडी
विवेकानंद
स्वतंत्रता भवन
विश्वनाथ मंदिर
स्वास्थ्य संकुल
मुख्य द्वार or लंका गेट
विश्वेश्वरैया
छात्रावास
सिवी रमन
दिनांक
से तक
प्रवासि संख्या
काॅर्नर
*/

        String srcString = "";
        switch (src){
            case 1:
                srcString = "सिवी रमन छात्रावास";
                break;
            case 2:
                srcString = "धनराजगिरी काॅर्नर";
                break;
            case 3:
                srcString = "स्वास्थ्य संकुल/Health Center";
                break;
            case 4:
                srcString = "लिम्बडी काॅर्नर";
                break;
            case 5:
                srcString = "मोर्वी छात्रावास";
                break;
            case 6:
                srcString = "रामानुजन छात्रावास";
                break;
            case 7:
                srcString = "स्वतंत्रता भवन";
                break;
            case 8:
                srcString = "विश्वकर्मा छात्रावास";
                break;
            case 9:
                srcString = "विवेकानंद छात्रावास";
                break;
            case 10:
                srcString = "लंका, मुख्य द्वार";
                break;
            case 11:
                srcString = "विश्वनाथ मंदिर";
        }

        String destString = "";
        switch (dest){
            case 1:
                destString = "सिवी रमन छात्रावास";
                break;
            case 2:
                destString = "धनराजगिरी काॅर्नर";
                break;
            case 3:
                destString = "स्वास्थ्य संकुल /Health Center";
                break;
            case 4:
                destString = "लिम्बडी काॅर्नर";
                break;
            case 5:
                destString = "मोर्वी छात्रावास";
                break;
            case 6:
                destString = "रामानुजन छात्रावास";
                break;
            case 7:
                destString = "स्वतंत्रता भवन";
                break;
            case 8:
                destString = "विश्वकर्मा छात्रावास";
                break;
            case 9:
                destString = "विवेकानंद छात्रावास";
                break;
            case 10:
                destString = "लंका, मुख्य द्वार";
                break;
            case 11:
                destString = "विश्वनाथ मंदिर";
        }

        viewHolder.orderTimeView.setText(arr[1]);
        viewHolder.orderDateView.setText(reg_date);
        viewHolder.sourceView.setText("From: " + srcString);
        viewHolder.destinationView.setText("To: " + destString);
        viewHolder.numOfCusView.setText("प्रवासि संख्या: " + noOfCus);
        viewHolder.orderNumberView.setText("Order number: #" + orderNum);
        viewHolder.customerNameView.setText("प्रवासि ID: " + mCustomer);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        return mNumberItems;
    }


    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView orderTimeView;
        TextView orderDateView;
        TextView sourceView;
        TextView destinationView;
        TextView numOfCusView;
        TextView orderNumberView;
        TextView customerNameView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         * @param itemView The View that you inflated in
         *                 {@link OrderAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public OrderViewHolder(View itemView) {
            super(itemView);

            orderTimeView = (TextView) itemView.findViewById(R.id.order_time);
            orderDateView = (TextView) itemView.findViewById(R.id.order_date);
            sourceView = (TextView) itemView.findViewById(R.id.from_source);
            destinationView = (TextView) itemView.findViewById(R.id.to_destination);
            numOfCusView = (TextView) itemView.findViewById(R.id.no_of_customer);
            orderNumberView = (TextView) itemView.findViewById(R.id.order_number);
            customerNameView = (TextView) itemView.findViewById(R.id.customer_name);
            itemView.setOnClickListener(this);
        }


        /**
         * Called whenever a user clicks on an item in the list.
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Order order = orderList.get(clickedPosition);
            mOnClickListener.onListItemClick(order);
        }
    }
}
