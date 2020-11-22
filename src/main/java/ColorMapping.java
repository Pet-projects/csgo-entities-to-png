import java.awt.*;

class ColorMapping {
    private static final int MIN_HEIGHT = 300;
    private static final int MAX_HEIGHT = 1200;

    enum Theme {
        // Compute angles from here https://i.stack.imgur.com/HdZ73.png
        RED_TO_YELLOW(0f, 60f),
        GREEN_TO_CYAN(120f, 180f);

        private final float minHue;
        private final float maxHue;

        Theme(float minHue, float maxHue) {
            this.minHue = minHue;
            this.maxHue = maxHue;
        }
    }

    /**
     * Idea from here: https://stackoverflow.com/questions/44326765/color-mapping-for-specific-range
     */
    static Color fromHeight(float entityHeight, Theme theme) {
        float index;
        if (entityHeight <= MIN_HEIGHT) {
            index = 0;
        } else
        if (entityHeight >= MAX_HEIGHT) {
            index = 1;
        } else {
            index = (entityHeight - MIN_HEIGHT) / (MAX_HEIGHT - MIN_HEIGHT);
        }

        System.out.println("index: "+index);
        float hue = theme.minHue + index * (theme.maxHue - theme.minHue);
        System.out.println(hue);
        return new Color(Color.HSBtoRGB(hue/255f, 1, 1f));
    }
}
