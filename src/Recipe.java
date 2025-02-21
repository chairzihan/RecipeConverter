public class Recipe {

    private String recipeName;
    private String[] ingredients;
    private double[] numIngredients;
    private String[] ingredientUnit;
    private String description;
    private String[] instructions;

    // Constructor to initialize the recipe name and description
    public Recipe(String recipeName, String description) {
        this.recipeName = recipeName;
        this.description = description;
    }

    // Method to set the ingredients, their quantities, and units
    public void setIngredients(String[] ingredients, double[] numIngredients, String[] ingredientUnit) {
        if (ingredients.length != numIngredients.length || ingredients.length != ingredientUnit.length) {
            throw new IllegalArgumentException("Arrays must be of the same length.");
        }
        this.ingredients = ingredients;
        this.numIngredients = numIngredients;
        this.ingredientUnit = ingredientUnit;
    }

    // Method to set the cooking instructions
    public void setInstructions(String[] instructions) {
        this.instructions = instructions;
    }

    public void setRecipeName(String name){
        recipeName = (name);
    }
    public void setDescription(String desc){
        description = (desc);
    }



    // Method to get the recipe name
    public String getRecipeName() {
        return recipeName;
    }

    // Method to get the recipe description
    public String getDescription() {
        return description;
    }

    // Method to get the ingredients
    public String[] getIngredients() {
        return ingredients;
    }

    // Method to get the quantities of ingredients
    public double[] getNumIngredients() {
        return numIngredients;
    }

    // Method to get the units of ingredients
    public String[] getIngredientUnit() {
        return ingredientUnit;
    }

    // Method to get the cooking instructions
    public String[] getInstructions() {
        return instructions;
    }

    // Method to display the recipe details
    public void displayRecipe() {
        System.out.println("Recipe Name: " + recipeName);
        System.out.println("Description: " + description);
        System.out.println("Ingredients:");
        for (int i = 0; i < ingredients.length; i++) {
            System.out.println("- " + numIngredients[i] + " " + ingredientUnit[i] + " of " + ingredients[i]);
        }
        System.out.println("Instructions:");
        for (String instruction : instructions) {
            System.out.println("- " + instruction);
        }
    }

    public void scaleRecipe(double factor) {
        for (int i = 0; i < numIngredients.length; i++) {
            numIngredients[i] *= factor;
        }
    }

    public void addIngredient(String ingredient, double quantity, String unit) {
        if (ingredients == null) {
            ingredients = new String[]{ingredient};
            numIngredients = new double[]{quantity};
            ingredientUnit = new String[]{unit};
        } else {
            String[] newIngredients = new String[ingredients.length + 1];
            double[] newQuantities = new double[numIngredients.length + 1];
            String[] newUnits = new String[ingredientUnit.length + 1];

            System.arraycopy(ingredients, 0, newIngredients, 0, ingredients.length);
            System.arraycopy(numIngredients, 0, newQuantities, 0, numIngredients.length);
            System.arraycopy(ingredientUnit, 0, newUnits, 0, ingredientUnit.length);

            newIngredients[ingredients.length] = ingredient;
            newQuantities[numIngredients.length] = quantity;
            newUnits[ingredientUnit.length] = unit;

            ingredients = newIngredients;
            numIngredients = newQuantities;
            ingredientUnit = newUnits;
        }
    }

    public void convertSingleIngredientUnit(int index, UnitConverter converter, String newUnit) {
        if (index < 0 || index >= ingredients.length) {
            throw new IndexOutOfBoundsException("Invalid ingredient index.");
        }
        String currentUnit = ingredientUnit[index];
        if (!currentUnit.equals(newUnit)) {
            // Convert the quantity to the new unit
            numIngredients[index] = converter.unitConversion(numIngredients[index], currentUnit, newUnit);
            // Update the unit
            ingredientUnit[index] = newUnit;
        }
    }
    // Method to delete an ingredient by index
    public void deleteIngredient(int index) {
        if (index < 0 || index >= ingredients.length) {
            throw new IndexOutOfBoundsException("Invalid ingredient index.");
        }
        String[] newIngredients = new String[ingredients.length - 1];
        double[] newQuantities = new double[numIngredients.length - 1];
        String[] newUnits = new String[ingredientUnit.length - 1];

        for (int i = 0, j = 0; i < ingredients.length; i++) {
            if (i != index) {
                newIngredients[j] = ingredients[i];
                newQuantities[j] = numIngredients[i];
                newUnits[j] = ingredientUnit[i];
                j++;
            }
        }

        ingredients = newIngredients;
        numIngredients = newQuantities;
        ingredientUnit = newUnits;
    }

    // Method to delete an instruction by index
    public void deleteInstruction(int index) {
        if (index < 0 || index >= instructions.length) {
            throw new IndexOutOfBoundsException("Invalid instruction index.");
        }
        String[] newInstructions = new String[instructions.length - 1];

        for (int i = 0, j = 0; i < instructions.length; i++) {
            if (i != index) {
                newInstructions[j] = instructions[i];
                j++;
            }
        }

        instructions = newInstructions;
    }




    public void convertIngredientUnits(UnitConverter converter, String newUnit) {
        for (int i = 0; i < ingredients.length; i++) {
            String currentUnit = ingredientUnit[i];
            if (!currentUnit.equals(newUnit)) {
                // Convert the quantity to the new unit
                numIngredients[i] = converter.unitConversion(numIngredients[i], currentUnit, newUnit);
                // Update the unit
                ingredientUnit[i] = newUnit;
            }
        }
    }


}
