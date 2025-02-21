import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIO {

    // Save recipe to a text file
    public static void saveRecipeToTextFile(Recipe recipe, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Recipe Name: " + recipe.getRecipeName() + "\n");
            writer.write("Description: " + recipe.getDescription() + "\n");
            writer.write("Ingredients:\n");
            for (int i = 0; i < recipe.getIngredients().length; i++) {
                writer.write("- " + recipe.getNumIngredients()[i] + " " +
                        recipe.getIngredientUnit()[i] + " of " +
                        recipe.getIngredients()[i] + "\n");
            }
            writer.write("Instructions:\n");
            for (String instruction : recipe.getInstructions()) {
                writer.write("- " + instruction + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving recipe to text file: " + e.getMessage());
        }
    }

    // Save recipe to a JSON file * JSON FILES CAN ONLY HAVE ONE THING AT A TIME HUDOAADOHUAWOHUD
    public static void saveRecipeToJsonFile(Recipe recipe, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, false)) { // true enables append mode
            JSONObject recipeJson = new JSONObject();
            recipeJson.put("name", recipe.getRecipeName());
            recipeJson.put("description", recipe.getDescription());

            JSONArray ingredientsJson = new JSONArray();
            for (int i = 0; i < recipe.getIngredients().length; i++) {
                JSONObject ingredientJson = new JSONObject();
                ingredientJson.put("name", recipe.getIngredients()[i]);
                ingredientJson.put("quantity", recipe.getNumIngredients()[i]);
                ingredientJson.put("unit", recipe.getIngredientUnit()[i]);
                ingredientsJson.put(ingredientJson);
            }
            recipeJson.put("ingredients", ingredientsJson);

            JSONArray instructionsJson = new JSONArray();
            for (String instruction : recipe.getInstructions()) {
                instructionsJson.put(instruction);
            }
            recipeJson.put("instructions", instructionsJson);

            // Print JSON string to console for debugging
            System.out.println(recipeJson.toString(4));

            writer.write(recipeJson.toString(4) + "\n"); // Pretty-print with 4 spaces and add a newline
        } catch (IOException e) {
            System.err.println("Error saving recipe to JSON file: " + e.getMessage());
        }
    }

    // Load recipe from a JSON file
    public static Recipe loadRecipeFromJsonFile(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject recipeJson = new JSONObject(content);

            String name = recipeJson.getString("name");
            String description = recipeJson.getString("description");

            JSONArray ingredientsJson = recipeJson.getJSONArray("ingredients");
            String[] ingredients = new String[ingredientsJson.length()];
            double[] quantities = new double[ingredientsJson.length()];
            String[] units = new String[ingredientsJson.length()];

            for (int i = 0; i < ingredientsJson.length(); i++) {
                JSONObject ingredientJson = ingredientsJson.getJSONObject(i);
                ingredients[i] = ingredientJson.getString("name");
                quantities[i] = ingredientJson.getDouble("quantity");
                units[i] = ingredientJson.getString("unit");
            }

            JSONArray instructionsJson = recipeJson.getJSONArray("instructions");
            String[] instructions = new String[instructionsJson.length()];
            for (int i = 0; i < instructionsJson.length(); i++) {
                instructions[i] = instructionsJson.getString(i);
            }

            Recipe recipe = new Recipe(name, description);
            recipe.setIngredients(ingredients, quantities, units);
            recipe.setInstructions(instructions);
            return recipe;
        } catch (IOException e) {
            System.err.println("Error loading recipe from JSON file: " + e.getMessage());
            return null;
        }
    }
}
