package org.wirez.client.lienzo.util;

public class LienzoUtils {

    public static double[] getScaleFactor( final double width,
                                           final double height,
                                           final double targetWidth,
                                           final double targetHeight) {

        return new double[] {
                width > 0 ? targetWidth / width : 1,
                height > 0 ? targetHeight / height : 1 };

    }

}
