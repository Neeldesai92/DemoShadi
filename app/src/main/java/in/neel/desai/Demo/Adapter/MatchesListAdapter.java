package in.neel.desai.Demo.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import in.neel.desai.Demo.Model.Result;
import in.neel.desai.Demo.R;
import in.neel.desai.Demo.database.DatabaseClient;


public class MatchesListAdapter extends RecyclerView.Adapter<MatchesListAdapter.MatchesItemViewHolder> {
    private final Activity mActivity;
    private ArrayList<Result> mItemList;
    private ArrayList<Result> copyList;
    private Fragment mFragment;


    public MatchesListAdapter(ArrayList<Result> itemList, Fragment fragment) {
        this.mItemList = itemList;
        copyList = new ArrayList<Result>();
        copyList.addAll(mItemList);
        mFragment = fragment;
        mActivity = fragment.getActivity();

    }


    //***************************************************************************//
    //          FILETER LOGIC
    //***************************************************************************//

    public void filter(String queryText) {
        mItemList.clear();

        if (queryText.isEmpty()) {
            mItemList.addAll(copyList);
        } else {

            for (Result name : copyList) {
                if (name.getPhone().toLowerCase().contains(queryText.toLowerCase())) {
                    mItemList.add(name);
                }
            }

        }
        notifyDataSetChanged();
    }

    @Override
    public MatchesItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_list, parent, false);
        return new MatchesItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesItemViewHolder holder, int position) {
        final Result result = mItemList.get(position);
        Glide.with(mActivity).load(result.getPicture().getLarge()).into(holder.mIvProfile);

        if (result.getRequestFlg().equalsIgnoreCase("accepted")) {
            holder.mLlMsg.setVisibility(View.VISIBLE);
            holder.mLlAcation.setVisibility(View.GONE);
            holder.mTvMsg.setText("Member accepted");
        } else if (result.getRequestFlg().equalsIgnoreCase("declined")) {
            holder.mLlMsg.setVisibility(View.VISIBLE);
            holder.mLlAcation.setVisibility(View.GONE);
            holder.mTvMsg.setText("Member declined");
        } else {
            holder.mLlAcation.setVisibility(View.VISIBLE);
            holder.mLlMsg.setVisibility(View.GONE);
        }

        holder.mIvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setRequestFlg("accepted");
                DatabaseClient.getInstance(mActivity).getAppDatabase()
                        .taskDao()
                        .update(result);
                notifyDataSetChanged();
            }
        });
        holder.mIvReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setRequestFlg("declined");
                DatabaseClient.getInstance(mActivity).getAppDatabase()
                        .taskDao()
                        .update(result);
                notifyDataSetChanged();
            }
        });
    }

    /**
     * @param strphn : Consignee or exsotel api no
     */


    @Override
    public int getItemCount() {
        return mItemList == null ? 1 : mItemList.size();
    }


    public class MatchesItemViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView mIvProfile;
        AppCompatImageView mIvReject;
        AppCompatImageView mIvAccept;
        LinearLayout mLlAcation;
        AppCompatTextView mTvMsg;
        LinearLayout mLlMsg;
        RelativeLayout mLinearLayout;
        CardView mCvRoot;

        MatchesItemViewHolder(View view) {
            super(view);
            this.view = view;
            this.mIvProfile = (ImageView) view.findViewById(R.id.iv_Profile);
            this.mIvReject = (AppCompatImageView) view.findViewById(R.id.iv_reject);
            this.mIvAccept = (AppCompatImageView) view.findViewById(R.id.iv_accept);
            this.mLlAcation = (LinearLayout) view.findViewById(R.id.ll_acation);
            this.mTvMsg = (AppCompatTextView) view.findViewById(R.id.tv_msg);
            this.mLlMsg = (LinearLayout) view.findViewById(R.id.ll_msg);
            this.mLinearLayout = (RelativeLayout) view.findViewById(R.id.linearLayout);
            this.mCvRoot = (CardView) view.findViewById(R.id.cv_root);
        }
    }

}
