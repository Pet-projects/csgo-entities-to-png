import java.awt.*;

class ColorMapping {

    private final int minZ;
    private final int maxZ;

    ColorMapping(int minZ, int maxZ) {
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

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
    Color fromHeight(float entityHeight, Theme theme) {
        float index;
        if (entityHeight <= minZ) {
            index = 0;
        } else
        if (entityHeight >= maxZ) {
            index = 1;
        } else {
            index = (entityHeight - minZ) / (maxZ - minZ);
        }

        System.out.println("index: "+index);
        float hue = theme.minHue + index * (theme.maxHue - theme.minHue);
        System.out.println(hue);
        return new Color(Color.HSBtoRGB(hue/255f, 1, 1f));
    }
}
