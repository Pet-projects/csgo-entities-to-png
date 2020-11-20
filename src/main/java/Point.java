class Point {
    private float x;
    private float y;
    private float z;

    Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

    float getZ() {
        return z;
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f", x, y, z);
    }
}
