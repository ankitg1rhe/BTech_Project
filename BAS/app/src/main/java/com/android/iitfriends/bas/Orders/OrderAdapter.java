package com.android.iitfriends.bas.Orders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.iitfriends.bas.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    final private ListItemClickListener mOnClickListener;
    public ArrayList<Order> orders;
    private static int viewHolderCount;

    private int mNumberItems;

    /**
     * The interface that receives onClick messages.
     */
    public interface ListItemClickListener{
        void onListItemClick(Order o);
    }

    public OrderAdapter(ListItemClickListener mOnClickListener, String response){
        orders = QueryUtils.orderDataFromResponse(response);
        this.mOnClickListener = mOnClickListener;
        mNumberItems = orders.size();
        viewHolderCount  = 0;
    }


    /**
     *
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new NumberViewHolder that holds the View for each list item
     */
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        OrderViewHolder viewHolder = new OrderViewHolder(view);

        int backgroundColorForViewHolder = ColorUtils
                .getViewHolderBackgroundColorFromInstance(context, viewHolderCount);
        // COMPLETED (14) Set the background color of viewHolder.itemView with the color from above
        viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);
        viewHolderCount++;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder viewHolder, int position) {
        Order currentOrder = orders.get(position);
        String reg_date = currentOrder.getRegDate();
        int src = currentOrder.getSource();
        int dest = currentOrder.getDestination();
        int orderNum = currentOrder.getOrderNumber();
        int autoId = currentOrder.getAutoID();
        int noOfCus = currentOrder.getNoOfCustomer();
        int status = currentOrder.getStatus();

        String srcString = "";
        switch (src){
            case 1:
                srcString = "CV Raman Hostel";
                break;
            case 2:
                srcString = "Dhanrajgiri Corner";
                break;
            case 3:
                srcString = "Health Center";
                break;
            case 4:
                srcString = "Limbdi Corner";
                break;
            case 5:
                srcString = "Morvi Hostel";
                break;
            case 6:
                srcString = "Ramanujan Hostel";
                break;
            case 7:
                srcString = "Swatantrata Bhavan";
                break;
            case 8:
                srcString = "Vishwakarma Hostel";
                break;
            case 9:
                srcString = "Vivekanand Hostel";
                break;
            case 10:
                srcString = "Lanaka Gate";
                break;
            case 11:
                srcString = "Vishwanath Temple";
        }

        String destString = "";
        switch (dest){
            case 1:
                destString = "CV Raman Hostel";
                break;
            case 2:
                destString = "Dhanrajgiri Corner";
                break;
            case 3:
                destString = "Health Center";
                break;
            case 4:
                destString = "Limbdi Corner";
                break;
            case 5:
                destString = "Morvi Hostel";
                break;
            case 6:
                destString = "Ramanujan Hostel";
                break;
            case 7:
                destString = "Swatantrata Bhavan";
                break;
            case 8:
                destString = "Vishwakarma Hostel";
                break;
            case 9:
                destString = "Vivekanand Hostel";
                break;
            case 10:
                destString = "Lanaka Gate";
                break;
            case 11:
                destString = "Vishwanath Temple";
        }

        viewHolder.orderTimeView.setText(reg_date);
        viewHolder.fromView.setText("From: " + srcString);
        viewHolder.toView.setText("To: " + destString);
        viewHolder.noOfCusView.setText("No.of customer: " + noOfCus );
        viewHolder.orderNoView.setText("Order no.: #" + orderNum);
        viewHolder.statusView.setText("Status");
        viewHolder.statusDetail.setText("Pending");
        if(status == 1){
            viewHolder.autoIdView.setText("AUTO ID : BHU" + autoId);
            viewHolder.statusDetail.setText("Confirmed");
        }

        if(status == 2)
            viewHolder.statusDetail.setText("Cancelled");

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView orderTimeView;
        public TextView fromView;
        public TextView toView;
        public TextView noOfCusView;
        public TextView orderNoView;
        public TextView autoIdView;
        public TextView statusView;
        public TextView statusDetail;

        public OrderViewHolder(View itemView) {
            super(itemView);

            orderTimeView = (TextView) itemView.findViewById(R.id.order_date_time);
            fromView = (TextView) itemView.findViewById(R.id.from_source);
            toView = (TextView) itemView.findViewById(R.id.to_destination);
            noOfCusView = (TextView) itemView.findViewById(R.id.no_of_customer);
            orderNoView = (TextView) itemView.findViewById(R.id.order_number);
            autoIdView = (TextView) itemView.findViewById(R.id.auto_driver_detail);
            statusView = (TextView) itemView.findViewById(R.id.status_view);
            statusDetail = (TextView) itemView.findViewById(R.id.status_detail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Order order = orders.get(clickedPosition);
            mOnClickListener.onListItemClick(order);
        }
    }
}

