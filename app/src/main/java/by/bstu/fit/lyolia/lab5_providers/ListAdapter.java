package by.bstu.fit.lyolia.lab5_providers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 16.03.2018.
 */

public class ListAdapter extends ArrayAdapter<Student> {

    private final Activity context;
    private final List<Student> list;

    public ListAdapter(Activity context, List<Student> list) {
        super(context, R.layout.list_item, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView name;
        protected TextView grade;
        protected TextView adress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // Очищает сущетсвующий шаблон, если параметр задан
        // Работает только если базовый шаблон для всех классов один и тот же
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();

            rowView = inflater.inflate(R.layout.list_item, null, true);
            holder = new ViewHolder();
            holder.name = (TextView) rowView.findViewById(R.id.name_item);
            holder.grade = (TextView) rowView.findViewById(R.id.grade_item);
            holder.adress = (TextView) rowView.findViewById(R.id.adress_item);

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.name.setText(list.get(position).getName());
        holder.grade.setText(list.get(position).getGrade());
        holder.adress.setText(list.get(position).getAdress());

        return rowView;
    }



}
