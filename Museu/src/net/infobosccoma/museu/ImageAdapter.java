
package net.infobosccoma.museu;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<Imatge> llistaFotos;
	
      
    public ImageAdapter(Context c, ArrayList<Imatge> llistaImatge) {
        mContext = c;
        llistaFotos = new ArrayList<Imatge>();
        llistaFotos.addAll(llistaImatge);
    }

    public int getCount() {
    	return llistaFotos.size();
    }

    public Bitmap getItem(int position) {
    	return llistaFotos.get(position).getFoto();
    }

    public long getItemId(int position) {
    	return 0;    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(8, 8, 8, 8);
            
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(llistaFotos.get(position).getFoto());
       
        return imageView;
    }
}
