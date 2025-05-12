package test;

/**  Collection d'easings – à placer dans une classe utilitaire  */
public final class Easings {

    private Easings() {}                 // util class

    /** linéaire – pas de variation de vitesse */
    public static double linear(double t)       { return t; }

    /** accélère (quad) */
    public static double easeInQuad(double t)   { return t * t; }

    /** décélère (quad) */
    public static double easeOutQuad(double t)  { return 1 - (1 - t) * (1 - t); }

    /** accélère puis freine (quad) */
    public static double easeInOutQuad(double t){
        return t < .5 ? 2 * t * t
                      : 1 - Math.pow(-2 * t + 2, 2) / 2;
    }

    /** accélère (cubic) – plus prononcé que quad */
    public static double easeInCubic(double t)  { return t * t * t; }

    /** freine (cubic) */
    public static double easeOutCubic(double t) {
        return 1 - Math.pow(1 - t, 3);
    }

    /** accélère-freine (cubic) – le classique “EASE_BOTH” */
    public static double easeInOutCubic(double t){
        return t < .5 ? 4 * t * t * t
                      : 1 - Math.pow(-2 * t + 2, 3) / 2;
    }

    /** sinus – départ/arrêt très doux */
    public static double easeInOutSine(double t){
        return -(Math.cos(Math.PI * t) - 1) / 2;
    }

    /** expo – très brutal en début puis très doux en fin */
    public static double easeOutExpo(double t){
        return t == 1 ? 1 : 1 - Math.pow(2, -10 * t);
    }

    /** “back” – dépasse léger, puis revient (amorti s=1.70158) */
    public static double easeOutBack(double t){
        double s = 1.70158;
        return 1 + (s + 1) * Math.pow(t - 1, 3) + s * Math.pow(t - 1, 2);
    }

    /** “elastic” – rebond élastique (amplitude = 1, période = 0.3) */
    public static double easeOutElastic(double t){
        double c4 = (2 * Math.PI) / 0.3;
        return t == 0 ? 0
             : t == 1 ? 1
             : Math.pow(2, -10 * t) * Math.sin((t * 10 - .75) * c4) + 1;
    }

    /** bounce – rebonds multiples façon balle au sol */
    public static double easeOutBounce(double t){
        double n1 = 7.5625, d1 = 2.75;
        if (t < 1 / d1)          return n1 * t * t;
        else if (t < 2 / d1)     return n1 * (t -= 1.5 / d1) * t + .75;
        else if (t < 2.5 / d1)   return n1 * (t -= 2.25 / d1) * t + .9375;
        else                     return n1 * (t -= 2.625 / d1) * t + .984375;
    }
}
