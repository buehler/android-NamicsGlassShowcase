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
            case BLUE:
                return new VivaltoLungo();
            case RED:
                return new DecaffeinatoLungo();
            case WHITE:
                return new Vanilio();
            default:
                return new Ristretto();
        }
    }

    private static boolean isInRange(int is, int should){
        int min = should - (255 / 100 * 25);
        int max = should + (255 / 100 * 25);
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

    public static class VivaltoLungo extends CapsuleInformation{
        public VivaltoLungo(){
            capsuleColor = CapsuleColor.BLUE;
            title = "Vivalto Lungo";
            origin = "Eine komplexe und kontrastreiche Mischung. Die Hochlagen-Arabicas aus Südamerika bringen eine leichte Säure ein. Der Arabica aus Äthiopien steuert seine blumige Note bei und der Sud de Minas aus Brasilien stärkt den Charakter und entwickelt die Bitterkeit.";
            roasting = "Eine separate Röstung gestattet es, den Charakter der verschiedenen Ursprungskaffees zu verfeinern. Die Mahlung entspricht der Extraktion in der grossen Tasse.";
            aromaProfile = "Ein vielschichtiges Temperament dank der perfekt beherrschten Verbindung verschiedener Ursprungskaffees: eine schöne Röstnote, eine leichte Holznote, eine milde Getreidenote und eine zarte Blumennote.";
            cupSizes = CUP_SIZE_LUNGO;
            intensity = 4;
            imageResource = R.drawable.vivalto_lungo_capsule;
        }
    }

    public static class Vanilio extends CapsuleInformation{
        public Vanilio(){
            capsuleColor = CapsuleColor.WHITE;
            title = "Vanilio";
            origin = "Eine ausgewogene Verbindung von natürlichem Vanillearoma mit dem cremigen Livanto Grand Cru. Diese unglaublich weiche Mischung zergeht auf der Zunge und bietet eine einzigartige Geschmackserfahrung.";
            roasting = "Ein Teil des Kaffees wird nur schwach geröstet, um die milden Noten zu betonen. Der andere Teil wird hingegen stärker geröstet, um die Mischung zu harmonisieren.";
            aromaProfile = "Dominierende milde Noten, die an Honig und Ahornsirup erinnern, auf einer Basisnote von maltiertem Getreide.";
            cupSizes = CUP_SIZE_ESPRESSO;
            intensity = 6;
            imageResource = R.drawable.vanilio_capsule;
        }
    }

    public static class DecaffeinatoLungo extends CapsuleInformation{
        public DecaffeinatoLungo(){
            capsuleColor = CapsuleColor.RED;
            title = "Decaffeinato Lungo";
            origin = "Die Mischung besteht aus den feinsten brasilianischen und kolumbianischen Arabicas mit einem Hauch sorgfältig ausgewählter Robustas.";
            roasting = "Langsam und intensiv, um die Röstnoten hervorzubringen.";
            aromaProfile = "Starke Röstnoten.";
            cupSizes = CUP_SIZE_LUNGO;
            intensity = 3;
            imageResource = R.drawable.decaffeinato_lungo_capsule;
        }
    }
}
