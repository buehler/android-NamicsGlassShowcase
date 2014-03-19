package com.namics.glassshowcase.nespressocapsuleinformation.capsuleinformation;

import com.namics.glassshowcase.nespressocapsuleinformation.R;

/**
 * Created by chbuehler on 17.03.14.
 */
public class CapsuleInformation {
    public enum CapsuleColor{
        BLACK,
        RED,
        BLUE,
        WHITE
    }

    public static int CUP_SIZE_RISTRETTO = 0x00000001;
    public static int CUP_SIZE_ESPRESSO = 0x00000010;
    public static int CUP_SIZE_LUNGO = 0x00000100;

    protected static CapsuleColor capsuleColor;
    protected static String origin;
    protected static String aromaProfile;
    protected static String roasting;
    protected static String title;
    protected static int cupSizes;
    protected static byte intensity;
    protected static int imageResource;

    public CapsuleColor getCapsuleColor() {
        return capsuleColor;
    }
    public String getOrigin() {
        return origin;
    }
    public String getAromaProfile() {
        return aromaProfile;
    }
    public String getRoasting() {
        return roasting;
    }
    public String getTitle(){
        return title;
    }
    public int getCupSizes() {
        return cupSizes;
    }
    public byte getIntensity() {
        return intensity;
    }
    public int getImageResource(){
        return imageResource;
    }

    public static CapsuleInformation getCapsuleForColor(int colorCode){
        int r = (colorCode >> 16) & 0xff;
        int g = (colorCode >> 8) & 0xff;
        int b = colorCode & 0xff;

        switch (getCapsuleColorForPixelColor(colorCode)){
            case BLACK:
                return new Ristretto();
            default:
                return new Ristretto();
        }
    }

    public static class Ristretto extends CapsuleInformation{
        public Ristretto(){
            capsuleColor = CapsuleColor.BLACK;
            title = "Ristretto";
            origin = "Ristretto, der vorwiegend aus südamerikanischen Arabicas, (Kolumbien und Brasilien) besteht, enthält auch einige grosse, leicht säuerliche Arabicas aus Ostafrika, und eine Spur von etwas temperamentvollerem Robusta.";
            roasting = "Die Bohnen werden langsam und separat geröstet. Auf diese Weise erhält man ein originelles Bouquet mit säuerlichen, fruchtigen und gerösteten Noten. Die sehr feine Mahlung ergibt einen Kaffee «à l'italienne»: intensiv, mit reichem Geschmack und sehr präsentem Körper.";
            aromaProfile = "Starke Röstnoten, abgemildert durch Schokoladenoten. Ein feiner Kontrast zwischen Stärke und Bitterkeit, Säure und Fruchtnoten.";
            cupSizes = CUP_SIZE_RISTRETTO | CUP_SIZE_ESPRESSO;
            intensity = 10;
            imageResource = R.drawable.ristretto_capsule;
        }
    }

    private static boolean isInRange(int is, int should){
        int min = should - (255 / 100 * 20);
        int max = should + (255 / 100 * 20);
        return (is >= min && is <= max);
    }

    public static CapsuleColor getCapsuleColorForPixelColor(int pixelColor){
        int r = (pixelColor >> 16) & 0xff;
        int g = (pixelColor >> 8) & 0xff;
        int b = pixelColor & 0xff;

        if (isInRange(r, 11) && isInRange(g, 17) && isInRange(b, 17)) return CapsuleColor.BLACK;
        if (isInRange(r, 114) && isInRange(g, 104) && isInRange(b, 72)) return CapsuleColor.WHITE;
        if (isInRange(r, 25) && isInRange(g, 55) && isInRange(b, 97)) return CapsuleColor.BLUE;
        if (isInRange(r, 125) && isInRange(g, 24) && isInRange(b, 16)) return CapsuleColor.RED;
        return null;
    }
}
