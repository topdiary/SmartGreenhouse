package com.example.nailamundev.smartgreenhouse.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.example.nailamundev.smartgreenhouse.R;
import com.example.nailamundev.smartgreenhouse.dao.GreenhousesItemDao;
import com.example.nailamundev.smartgreenhouse.dao.GreenhousesItemListDao;
import com.example.nailamundev.smartgreenhouse.datatype.MutableInteger;
import com.example.nailamundev.smartgreenhouse.view.GreenhousesListItem;

/**
 * Created by BenZDeV on 4/16/2017.
 */

public class GreenhousesListAdapter extends BaseAdapter {


    GreenhousesItemListDao dao;
    MutableInteger lastPositionInteger;

    public GreenhousesListAdapter(MutableInteger lastPositionInteger) {
        this.lastPositionInteger = lastPositionInteger;
    }

    public GreenhousesItemListDao getDao() {
        return dao;
    }

    public void setDao(GreenhousesItemListDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {

        if (dao == null) {
            return 0;
        }
        if (dao.getmGreenhousesItemDao() == null) {
            return 0;
        }
        return dao.getmGreenhousesItemDao().size();
    }

    @Override
    public Object getItem(int i) {
        return dao.getGreenhousesPosition(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        GreenhousesListItem item;

        if (view != null) {
            item = (GreenhousesListItem) view;

        } else {
            item = new GreenhousesListItem(viewGroup.getContext());

        }

        GreenhousesItemDao dao = (GreenhousesItemDao) getItem(i);
        item.setName(dao.name);
        item.setAddress(dao.address);
        item.setType(dao.plant);
        item.setImageUrl(dao.img);

        if (i > lastPositionInteger.getValue()) {
            Animation anim = AnimationUtils.loadAnimation(viewGroup.getContext(),
                    R.anim.animation_up_form_bottom);
            item.startAnimation(anim);
            lastPositionInteger.setValue(i);
        }


        return item;
    }
}
