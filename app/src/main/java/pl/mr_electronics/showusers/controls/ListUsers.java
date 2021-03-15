package pl.mr_electronics.showusers.controls;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import pl.mr_electronics.showusers.R;
import pl.mr_electronics.showusers.model.UserObj;

public class ListUsers extends ArrayAdapter<UserObj> {
    private Activity context;
    List<UserObj> list;

    public ListUsers(Activity context, int resource, List<UserObj> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_users, null,true);

        RelativeLayout root = rowView.findViewById(R.id.root);
        ImageView avatar = rowView.findViewById(R.id.avatar);
        TextView user_name = rowView.findViewById(R.id.user_name);
        TextView reposytory_name = rowView.findViewById(R.id.reposytory_name);

        UserObj obj = list.get(position);
        root.setBackgroundColor(obj.isHighlighted ? 0xffaaffaa : 0xffffffff);
        avatar.setImageBitmap(obj.avatar);
        user_name.setText(obj.name);
        reposytory_name.setText(obj.reposytory);

        return rowView;
    }
}
