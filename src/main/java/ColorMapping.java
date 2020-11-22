import java.awt.*;

class ColorMapping {
    private static final int MIN_HEIGHT = 300;
    private static final int MAX_HEIGHT = 1200;

    // Compute angles from here https://i.stack.imgur.com/HdZ73.png
    private static final float MIN_HUE = 0f / 255; //red
    private static final float MAX_HUE = 60f / 255; //yellow

    /**
     * Idea from here: https://stackoverflow.com/questions/44326765/color-mapping-for-specific-range
     */
    static Color fromHeight(float entityHeight) {
        float index;
        if (entityHeight <= MIN_HEIGHT) {
            index = 0;
        } else
        if (entityHeight >= MAX_HEIGHT) {
            index = 1;
        } else {
            index = (entityHeight - MIN_HEIGHT) / (MAX_HEIGHT - MIN_HEIGHT);
        }

        float hue = index* MAX_HUE + (1-index)* MIN_HUE;
        return new Color(Color.HSBtoRGB(hue, 1, 1f));
    }
}
