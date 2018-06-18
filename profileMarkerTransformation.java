import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.squareup.picasso.Transformation;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.RED;
import static android.graphics.Color.TRANSPARENT;
import static android.graphics.Color.green;

public class profileMarkerTransformation implements Transformation {
    private Paint paint;
    private RectF oval;
    private Path fillPath;
    private Path strokeFillPath;

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();//self explanatory
        int height = source.getHeight();
        int size = Math.min(width, height);

        paint = new Paint();//for image "paste"
        paint.setAntiAlias(true);


        //uset setShader to make the bitmap our paint object - "paste" it with setShader()
        paint.setShader(new BitmapShader(source, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        oval = new RectF();//top oval
        fillPath = new Path();
        strokeFillPath = new Path();

        oval.bottom = source.getHeight();
        oval.left = 0;
        oval.right = source.getWidth();
        oval.top = 0;

        // create new Bitmap the same size as image and use this for our canvas
        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        //draws top arc using RectF oval - and "pastes" our paint object
        canvas.drawArc(oval, 0, -180, true, paint);

        //below creates the fillpath for our canvas - the bottom marker shape
        fillPath.moveTo(0, height / 2); // Your origin point
        fillPath.lineTo(width, height / 2); // First point
        // Repeat above line for all points on your line graph
        fillPath.lineTo(width / 2, height); // Final point
        fillPath.lineTo(0, height / 2); // Same origin point

        //finally draw the Bottom path with painted bitmap inserted
        canvas.drawPath(fillPath, paint);

        //now make a border
        Paint trianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //create stroke
        trianglePaint.setStrokeWidth(3);
        trianglePaint.setColor(Color.CYAN);
        trianglePaint.setStyle(Paint.Style.STROKE);
        trianglePaint.setAntiAlias(true);

        strokeFillPath.moveTo(0, height / 2); // Your origin - leftmost point
        strokeFillPath.lineTo(width / 2, height); // First - bottom point
        strokeFillPath.lineTo(width, height / 2); // First - rightmost

        //put it all together - just like the profile image
        canvas.drawArc(oval, 0, -180, false, trianglePaint);
        canvas.drawPath(strokeFillPath,trianglePaint);

        if (source != output) {
            source.recycle();
        }

        return output;
    }

    @Override
    public String key() {
        return "someuniquestring";
    }
}
