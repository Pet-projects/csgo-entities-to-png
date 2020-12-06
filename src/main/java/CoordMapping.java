class CoordMapping {
    private static final float ENTITY_START_POS_X = -8604;
    private static final float ENTITY_START_POS_Y = 8804;

    private static final int PROJECTION_CAMERA_POS_X = 450;
    private static final int PROJECTION_CAMERA_POS_Y = 580;
    private static final double PROJECTION_DELTA_POWER = 1.41;
    private static final int PROJECTION_DELTA_DIVISION = 40;
    private float scale;
    private float entityStartPosX;
    private float entityStartPosY;

    CoordMapping(float entityStartPosX, float entityStartPosY, float scale) {
        this.entityStartPosX = entityStartPosX;
        this.entityStartPosY = entityStartPosY;
        this.scale = scale;
    }

    OverviewPoint toOverviewPoint(EntityPoint origin) {
        EntityPoint translatedEntity = translateToOrigin(origin);
        OverviewPoint overviewPoint = scaleToOverview(translatedEntity);
        return new OverviewPoint(
                overviewPoint.x + projectionDelta(overviewPoint.x, PROJECTION_CAMERA_POS_X),
                overviewPoint.y + projectionDelta(overviewPoint.y, PROJECTION_CAMERA_POS_Y));
    }

    float scaleRadius(float radius) {
        return radius / scale;
    }

    //~~~~~~~~~~~~~

    private  EntityPoint translateToOrigin(EntityPoint origin) {
        float x = origin.x - this.entityStartPosX;
        float y = this.entityStartPosY - origin.y;
        return new EntityPoint(x, y);
    }

    private  OverviewPoint scaleToOverview(EntityPoint entity) {
        float overviewX = entity.x / this.scale;
        float overviewY = entity.y / this.scale;
        return new OverviewPoint(overviewX, overviewY);
    }

    private static float projectionDelta(float coord, int center_pos) {
        float offsetFromCentre = coord - center_pos;
        float signum = Math.signum(offsetFromCentre);
        float projectionDelta = (float) Math.pow(
                (double) Math.abs(offsetFromCentre) / PROJECTION_DELTA_DIVISION,
                PROJECTION_DELTA_POWER);
        return signum * projectionDelta;
    }
}
