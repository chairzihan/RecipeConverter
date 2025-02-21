public class UnitConverter {


    String[] litres = {"ml", "l"};
    String[] grams = {"mg","g","kg"};
    String[] temp = {"F","C"};

    private static final double MILLILITERS_PER_LITER = 1000;
    private static final double MILLIGRAMS_PER_GRAM = 1000;
    private static final double GRAMS_PER_KILOGRAM = 1000;
    private static final double TEASPOONS_PER_TABLESPOON = 3;
    private static final double TABLESPOONS_PER_CUP = 16;
    private static final double TEASPOONS_PER_CUP = TEASPOONS_PER_TABLESPOON * TABLESPOONS_PER_CUP;
    private static final double OUNCES_PER_CUP = 8;
    private static final double GRAMS_PER_OUNCE = 28.3495;
    private static final double OUNCES_PER_POUND = 16;

    private static final String[] LIQUID_UNITS = {"ml", "l", "cup", "tsp", "tbsp"};
    private static final String[] WEIGHT_UNITS = {"mg", "g", "kg", "oz", "lb"};

    // Method to convert between units

    // Convert teaspoons to tablespoons
    public static double teaspoonsToTablespoons(double teaspoons) {
        return teaspoons / TEASPOONS_PER_TABLESPOON;
    }

    // Convert tablespoons to teaspoons
    public static double tablespoonsToTeaspoons(double tablespoons) {
        return tablespoons * TEASPOONS_PER_TABLESPOON;
    }

    // Convert tablespoons to cups
    public static double tablespoonsToCups(double tablespoons) {
        return tablespoons / TABLESPOONS_PER_CUP;
    }

    // Convert cups to tablespoons
    public static double cupsToTablespoons(double cups) {
        return cups * TABLESPOONS_PER_CUP;
    }

    // Convert teaspoons to cups
    public static double teaspoonsToCups(double teaspoons) {
        return teaspoons / TEASPOONS_PER_CUP;
    }

    // Convert cups to teaspoons
    public static double cupsToTeaspoons(double cups) {
        return cups * TEASPOONS_PER_CUP;
    }

    // Convert cups to ounces
    public static double cupsToOunces(double cups) {
        return cups * OUNCES_PER_CUP;
    }

    // Convert ounces to cups
    public static double ouncesToCups(double ounces) {
        return ounces / OUNCES_PER_CUP;
    }

    // Convert ounces to grams
    public static double ouncesToGrams(double ounces) {
        return ounces * GRAMS_PER_OUNCE;
    }

    // Convert grams to ounces
    public static double gramsToOunces(double grams) {
        return grams / GRAMS_PER_OUNCE;
    }

    // Convert grams to cups (assuming density of water for simplicity)
    public static double gramsToCups(double grams) {
        double ounces = gramsToOunces(grams);
        return ouncesToCups(ounces);
    }

    // Convert cups to grams
    public static double cupsToGrams(double cups) {
        double ounces = cupsToOunces(cups);
        return ouncesToGrams(ounces);
    }
    public boolean isLiquidUnit(String unit) {
        for (String liquidUnit : LIQUID_UNITS) {
            if (liquidUnit.equals(unit)) {
                return true;
            }
        }
        return false;
    }

    // Check if a unit is a weight unit
    public boolean isWeightUnit(String unit) {
        for (String weightUnit : WEIGHT_UNITS) {
            if (weightUnit.equals(unit)) {
                return true;
            }
        }
        return false;
    }

    // Convert between units
    public double unitConversion(double value, String fromUnit, String toUnit) {
        // Check if units are compatible
        if (isLiquidUnit(fromUnit) && isLiquidUnit(toUnit)) {
            return convertLiquid(value, fromUnit, toUnit);
        } else if (isWeightUnit(fromUnit) && isWeightUnit(toUnit)) {
            return convertWeight(value, fromUnit, toUnit);
        } else {
            // Show an error message for incompatible units
       
            return value; // Return the original value if conversion is not possible
        }
    }

    // Convert liquid units
    private double convertLiquid(double value, String fromUnit, String toUnit) {
        // Convert to a common unit (milliliters) first
        double valueInMl;
        switch (fromUnit) {
            case "ml":
                valueInMl = value;
                break;
            case "l":
                valueInMl = value * MILLILITERS_PER_LITER;
                break;
            case "cup":
                valueInMl = value * 240; // 1 cup = 240 ml
                break;
            case "tsp":
                valueInMl = value * 4.92892; // 1 tsp = 4.92892 ml
                break;
            case "tbsp":
                valueInMl = value * 14.7868; // 1 tbsp = 14.7868 ml
                break;
            default:
                throw new IllegalArgumentException("Unsupported liquid unit: " + fromUnit);
        }

        // Convert to the desired unit
        switch (toUnit) {
            case "ml":
                return valueInMl;
            case "l":
                return valueInMl / MILLILITERS_PER_LITER;
            case "cup":
                return valueInMl / 240;
            case "tsp":
                return valueInMl / 4.92892;
            case "tbsp":
                return valueInMl / 14.7868;
            default:
                throw new IllegalArgumentException("Unsupported liquid unit: " + toUnit);
        }
    }

    // Convert weight units
    private double convertWeight(double value, String fromUnit, String toUnit) {
        // Convert to a common unit (grams) first
        double valueInGrams;
        switch (fromUnit) {
            case "mg":
                valueInGrams = value / MILLIGRAMS_PER_GRAM;
                break;
            case "g":
                valueInGrams = value;
                break;
            case "kg":
                valueInGrams = value * GRAMS_PER_KILOGRAM;
                break;
            case "oz":
                valueInGrams = value * GRAMS_PER_OUNCE;
                break;
            case "lb":
                valueInGrams = value * GRAMS_PER_OUNCE * OUNCES_PER_POUND;
                break;
            default:
                throw new IllegalArgumentException("Unsupported weight unit: " + fromUnit);
        }

        // Convert to the target unit
        switch (toUnit) {
            case "mg":
                return valueInGrams * MILLIGRAMS_PER_GRAM;
            case "g":
                return valueInGrams;
            case "kg":
                return valueInGrams / GRAMS_PER_KILOGRAM;
            case "oz":
                return valueInGrams / GRAMS_PER_OUNCE;
            case "lb":
                return valueInGrams / (GRAMS_PER_OUNCE * OUNCES_PER_POUND);
            default:
                throw new IllegalArgumentException("Unsupported weight unit: " + toUnit);
        }
    }

    // check if a unit is in a given array
    private boolean isInArray(String unit, String[] unitArray) {
        for (String u : unitArray) {
            if (u.equals(unit)) {
                return true;
            }
        }
        return false;
    }

    // Convert volume (millilitres and litres)
    private double convertVolume(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals("ml") && toUnit.equals("l")) {
            return value / MILLILITERS_PER_LITER;
        } else if (fromUnit.equals("l") && toUnit.equals("ml")) {
            return value * MILLILITERS_PER_LITER;
        } else {
            return value; // No conversion needed if units are the same
        }
    }

    // Convert mass (milligrams, grams, kilograms)
    private double convertMass(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals("mg") && toUnit.equals("g")) {
            return value / MILLIGRAMS_PER_GRAM;
        } else if (fromUnit.equals("mg") && toUnit.equals("kg")) {
            return value / (MILLIGRAMS_PER_GRAM * GRAMS_PER_KILOGRAM);
        } else if (fromUnit.equals("g") && toUnit.equals("mg")) {
            return value * MILLIGRAMS_PER_GRAM;
        } else if (fromUnit.equals("g") && toUnit.equals("kg")) {
            return value / GRAMS_PER_KILOGRAM;
        } else if (fromUnit.equals("kg") && toUnit.equals("mg")) {
            return value * GRAMS_PER_KILOGRAM * MILLIGRAMS_PER_GRAM;
        } else if (fromUnit.equals("kg") && toUnit.equals("g")) {
            return value * GRAMS_PER_KILOGRAM;
        } else {
            return value; // No conversion needed if units are the same
        }
    }

    // Convert temperature (Fahrenheit and Celsius)
    private double convertTemperature(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals("C") && toUnit.equals("F")) {
            return (value * 9 / 5) + 32;
        } else if (fromUnit.equals("F") && toUnit.equals("C")) {
            return (value - 32) * 5 / 9;
        } else {
            return value; // No conversion needed if units are the same
        }
    }
}



