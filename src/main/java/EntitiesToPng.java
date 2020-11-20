import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EntitiesToPng {
    private static final float POS_X = - 8604;
    private static final float POS_Y = 8804;
    private static final float SCALE = 17;

    public static void main(String[] args) throws IOException, ParseException {
        if (args.length < 2) {
            System.err.println("Usage: <input_file> <json_file>");
            System.exit(1);
        }

        String inputPathname = args[0];
        String outputPathname = args[1];

        // retrieve image
        BufferedImage bufferedImage = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(3));

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader( inputPathname ));
        for (Object o : (JSONArray) obj) {
            JSONObject jsonObject = (JSONObject) o;
            System.out.println(jsonObject.get("door_name"));
            String originString = (String) jsonObject.get("origin");
            String[] originParts = originString.split("\\s+");

            float originX = Float.parseFloat(originParts[0]);
            float originY = Float.parseFloat(originParts[1]);
            float originZ = Float.parseFloat(originParts[2]);
            Point origin = new Point(originX, originY, originZ);
            System.out.println(origin);

            Point scaledOrigin = scaleAndTranslateOrigin(origin);
            System.out.println(scaledOrigin);

            String radiusString = (String) jsonObject.get("radius");
            float scaledRadius;
            if (radiusString != null) {
                float radius = Float.parseFloat(radiusString);
                scaledRadius = radius/ SCALE;
            } else {
                scaledRadius = 5;
            }

            g.setColor(Color.black);
            drawPoint(g, scaledOrigin, scaledRadius);
//            break;
        }
        g.setColor(Color.red);
        drawPoint(g, scaleAndTranslateOrigin(new Point(0,0,0)), 3);

        File outfile = new File(outputPathname);
        ImageIO.write(bufferedImage, "png", outfile);
    }

    private static void drawPoint(Graphics2D g, Point scaledOrigin, float scaledRadius) {
        int boxX = Math.round(scaledOrigin.getX() - scaledRadius);
        int boxY = Math.round(scaledOrigin.getY() - scaledRadius);
        int width = Math.round(2 * scaledRadius);
        int height = Math.round(2 * scaledRadius);
        g.drawOval(boxX, boxY, width, height);
    }

    private static Point scaleAndTranslateOrigin(Point origin) {
        float scaledOriginX = (origin.getX() - POS_X)/ SCALE;
        float scaledOriginY = (POS_Y - origin.getY())/ SCALE;
        float scaledOriginZ = origin.getZ()/ SCALE;
        return new Point(scaledOriginX, scaledOriginY, scaledOriginZ);
    }
}
