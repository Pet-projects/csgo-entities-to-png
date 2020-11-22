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
            EntityPoint origin = new EntityPoint(originX, originY);
            System.out.println(origin);

            OverviewPoint overviewPoint = CoordTranslation.toOverviewPoint(origin);
            System.out.println(overviewPoint);

            String radiusString = (String) jsonObject.get("radius");
            float scaledRadius;
            if (radiusString != null) {
                float radius = Float.parseFloat(radiusString);
                scaledRadius = CoordTranslation.scaleRadius(radius);
            } else {
                scaledRadius = 5;
            }

            g.setColor(Color.black);
            drawPoint(g, overviewPoint, scaledRadius);
        }

        File outfile = new File(outputPathname);
        ImageIO.write(bufferedImage, "png", outfile);
    }

    private static void drawPoint(Graphics2D g, OverviewPoint overviewPoint, float overviewRadius) {
        int boxX = Math.round(overviewPoint.x - overviewRadius);
        int boxY = Math.round(overviewPoint.y - overviewRadius);
        int width = Math.round(2 * overviewRadius);
        int height = Math.round(2 * overviewRadius);
        g.drawOval(boxX, boxY, width, height);
    }
}
