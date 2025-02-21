import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private static Recipe currentRecipe = new Recipe("N/A","N/A");
    private static Recipe displayRecipe;
    private static UnitConverter unitConverter = new UnitConverter();

    public static void main(String[] args) {
        FlatDarculaLaf.setup();

        javax.swing.SwingUtilities.invokeLater(() -> {
            // main frame
            JFrame frame = new JFrame("Recipe Converter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Main panel
            JPanel mainPanel = new JPanel(new BorderLayout());

            // Tabbed pane
            JTabbedPane tabbedPane = new JTabbedPane();

            // Recipe name and description panel
            JPanel recipeInfoPanel = new JPanel();
            JPanel recipeInputPanel = new JPanel();
            recipeInfoPanel.setLayout(new BorderLayout(10, 10));
            JTextField recipeNameField = new JTextField(20);
            JTextField descriptionField = new JTextField(20);
            JButton submitButton = new JButton("Enter"); // Submit button
            recipeInfoPanel.add(new JLabel("Name:"));
            JTextField nameDisplayField = new JTextField(20); // Text field to display the result a
            nameDisplayField.setEditable(false);
            JTextArea descriptionDisplayField = new JTextArea(20,20);
            descriptionDisplayField.setEditable(false);
            submitButton.setPreferredSize(new Dimension(80, 25));
            recipeInputPanel.add(new JLabel("Recipe Name:"));
            recipeInputPanel.add(recipeNameField);
            recipeInputPanel.add(new JLabel("Description:"));
            recipeInputPanel.add(descriptionField);
            recipeInputPanel.add(new JLabel());
            recipeInputPanel.add(submitButton);
            recipeInfoPanel.add(recipeInputPanel, BorderLayout.NORTH);
            recipeInfoPanel.add(new JScrollPane(descriptionDisplayField), BorderLayout.CENTER);
            recipeInfoPanel.add(new JScrollPane(nameDisplayField),BorderLayout.SOUTH);

            //Display panel
            JPanel displayPanel = new JPanel();
            JPanel displayInputPanel = new JPanel();
            displayPanel.setLayout(new BorderLayout(10, 10));
            displayInputPanel.setLayout(new GridLayout(2,2,10,10));
            displayPanel.add(displayInputPanel,BorderLayout.NORTH);
            JButton displayCurrent = new JButton("Load Current Recipe");
            JTextArea display = new JTextArea(20,20);
            display.setEditable(false);

            displayInputPanel.add(displayCurrent);
            displayPanel.add(new JScrollPane(display),BorderLayout.CENTER);



            // Ingredients panel
            JPanel ingredientsPanel = new JPanel(new BorderLayout());
            JPanel ingredientsInputPanel = new JPanel();
            ingredientsInputPanel.setLayout(new GridLayout(5, 2, 10, 10));
            JTextField ingredientField = new JTextField(10);
            JTextField quantityField = new JTextField(10);
            JComboBox<String> unitComboBox = new JComboBox<>(new String[]{"cup", "ml", "g", "kg", "tsp", "tbsp", "oz", "lb"});
            JButton addIngredientButton = new JButton("Add Ingredient");
            JButton deleteIngredientButton = new JButton("Delete Ingredient");
            JTextArea ingredientsDisplayArea = new JTextArea(20, 40);
            ingredientsDisplayArea.setEditable(false);

            ingredientsInputPanel.add(new JLabel("Ingredient:"));
            ingredientsInputPanel.add(ingredientField);
            ingredientsInputPanel.add(new JLabel("Quantity:"));
            ingredientsInputPanel.add(quantityField);
            ingredientsInputPanel.add(new JLabel("Unit:"));
            ingredientsInputPanel.add(unitComboBox);
            ingredientsInputPanel.add(new JLabel(""));
            ingredientsInputPanel.add(addIngredientButton);
            ingredientsInputPanel.add(new JLabel(""));
            ingredientsInputPanel.add(deleteIngredientButton);

            ingredientsPanel.add(ingredientsInputPanel, BorderLayout.NORTH);
            ingredientsPanel.add(new JScrollPane(ingredientsDisplayArea), BorderLayout.CENTER);

            // Instructions panel
            JPanel instructionsPanel = new JPanel(new BorderLayout());
            JPanel instructionsInputPanel = new JPanel();
            instructionsInputPanel.setLayout(new BorderLayout());
            JTextArea instructionsField = new JTextArea(3, 20);
            instructionsField.setLineWrap(true);
            instructionsField.setWrapStyleWord(true);
            JButton addInstructionButton = new JButton("Add Instruction");
            JButton deleteInstructionButton = new JButton("Delete Instruction");
            JTextArea instructionsDisplayArea = new JTextArea(20, 40);
            instructionsDisplayArea.setEditable(false);

            instructionsInputPanel.add(new JLabel("Instructions:"), BorderLayout.NORTH);
            instructionsInputPanel.add(new JScrollPane(instructionsField), BorderLayout.CENTER);
            instructionsInputPanel.add(addInstructionButton, BorderLayout.WEST);
            instructionsInputPanel.add(deleteInstructionButton, BorderLayout.EAST);

            instructionsPanel.add(instructionsInputPanel, BorderLayout.NORTH);
            instructionsPanel.add(new JScrollPane(instructionsDisplayArea), BorderLayout.CENTER);

            //  tabbed pane
            tabbedPane.addTab("Recipe Info", recipeInfoPanel);
            tabbedPane.addTab("Ingredients", ingredientsPanel);
            tabbedPane.addTab("Instructions", instructionsPanel);
            tabbedPane.addTab("Recipe Display",displayPanel);


            // Button panel
            JPanel buttonPanel = new JPanel();
            JButton saveRecipeButton = new JButton("Save Recipe");
            JButton loadRecipeButton = new JButton("Load Recipe");
            JButton convertUnitsButton = new JButton("Convert Units");
            buttonPanel.add(saveRecipeButton);
            buttonPanel.add(loadRecipeButton);
            buttonPanel.add(convertUnitsButton);


            mainPanel.add(tabbedPane, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);


            frame.getContentPane().add(mainPanel);

            // Event listeners

            displayCurrent.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (currentRecipe != null) {
                        display.setText(""); // Clear the display
                        display.append("Recipe Name: " + currentRecipe.getRecipeName() + "\n");
                        display.append("Description: " + currentRecipe.getDescription() + "\n");
                        display.append("Ingredients:\n");
                        for (int i = 0; i < currentRecipe.getIngredients().length; i++) {
                            display.append("- " + currentRecipe.getNumIngredients()[i] + " " +
                                    currentRecipe.getIngredientUnit()[i] + " of " +
                                    currentRecipe.getIngredients()[i] + "\n");
                        }
                        display.append("Instructions:\n");
                        for (String instruction : currentRecipe.getInstructions()) {
                            display.append("- " + instruction + "\n");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "No recipe found or error loading recipe. Please check the file path and content.");
                    }



                }
            });

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Get the text from the input fields
                    String recipeName = recipeNameField.getText();
                    String description = descriptionField.getText();

                    currentRecipe.setRecipeName(recipeName);
                    currentRecipe.setDescription(description);

                    descriptionDisplayField.setText(description);
                    nameDisplayField.setText(recipeName);



                    // Combine the text and display it in the nameDisplayField
//                    String combinedText = "Recipe Name: " + recipeName + ", Description: " + description;
//                    nameDisplayField.setText(combinedText);
                }
            });
            addIngredientButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String ingredient = ingredientField.getText();
                    double quantity = Double.parseDouble(quantityField.getText());
                    String unit = (String) unitComboBox.getSelectedItem();

                    if (currentRecipe == null) {
                        currentRecipe = new Recipe(recipeNameField.getText(), descriptionField.getText());
                    }

                    currentRecipe.addIngredient(ingredient, quantity, unit);
                    ingredientsDisplayArea.append(quantity + " " + unit + " of " + ingredient + "\n");

                    // Clear input fields
                    ingredientField.setText("");
                    quantityField.setText("");
                }
            });

            deleteIngredientButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentRecipe != null) {
                        try {
                            int index = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the index of the ingredient to delete (starting from 0):"));
                            currentRecipe.deleteIngredient(index);
                            ingredientsDisplayArea.setText(""); // Clear the display
                            for (int i = 0; i < currentRecipe.getIngredients().length; i++) {
                                ingredientsDisplayArea.append(currentRecipe.getNumIngredients()[i] + " " +
                                        currentRecipe.getIngredientUnit()[i] + " of " +
                                        currentRecipe.getIngredients()[i] + "\n");
                            }
                        } catch (IndexOutOfBoundsException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid index.");
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number for the index.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "No recipe to delete ingredients from.");
                    }
                }
            });

            addInstructionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String instruction = instructionsField.getText();

                    if (currentRecipe == null) {
                        currentRecipe = new Recipe(recipeNameField.getText(), descriptionField.getText());
                    }

                    String[] currentInstructions = currentRecipe.getInstructions();
                    if (currentInstructions == null) {
                        currentRecipe.setInstructions(new String[]{instruction});
                    } else {
                        String[] newInstructions = new String[currentInstructions.length + 1];
                        System.arraycopy(currentInstructions, 0, newInstructions, 0, currentInstructions.length);
                        newInstructions[currentInstructions.length] = instruction;
                        currentRecipe.setInstructions(newInstructions);
                    }

                    instructionsDisplayArea.append("Instruction: " + instruction + "\n");

                    // Clear input field
                    instructionsField.setText("");
                }
            });

            deleteInstructionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentRecipe != null) {
                        try {
                            int index = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the index of the instruction to delete (starting from 0):"));
                            currentRecipe.deleteInstruction(index);
                            instructionsDisplayArea.setText(""); // Clear the display
                            for (String instruction : currentRecipe.getInstructions()) {
                                instructionsDisplayArea.append("Instruction: " + instruction + "\n");
                            }
                        } catch (IndexOutOfBoundsException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid index.");
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number for the index.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "No recipe to delete instructions from.");
                    }
                }
            });

            saveRecipeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentRecipe != null) {
                        FileIO.saveRecipeToJsonFile(currentRecipe, "recipe.json");
                        JOptionPane.showMessageDialog(frame, "Recipe saved to recipe.json!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "No recipe to save.");
                    }
                }
            });

            loadRecipeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayRecipe = FileIO.loadRecipeFromJsonFile("recipe.json");
                    if (displayRecipe != null) {
                        display.setText(""); // Clear the display
                        display.append("Recipe Name: " + displayRecipe.getRecipeName() + "\n");
                        display.append("Description: " + displayRecipe.getDescription() + "\n");
                        display.append("Ingredients:\n");
                        for (int i = 0; i < displayRecipe.getIngredients().length; i++) {
                            display.append("- " + displayRecipe.getNumIngredients()[i] + " " +
                                    displayRecipe.getIngredientUnit()[i] + " of " +
                                    displayRecipe.getIngredients()[i] + "\n");
                        }
//                        instructionsDisplayArea.append("Instructions:\n");
                        for (String instruction : displayRecipe.getInstructions()) {
                            display.append("- " + instruction + "\n");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "No recipe found or error loading recipe. Please check the file path and content.");
                    }
                }
            });
            convertUnitsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentRecipe != null) {
                        try {
                            int index = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the index of the ingredient to convert (starting from 0):"));
                            if (index >= 0 && index < currentRecipe.getIngredients().length) {
                                String currentUnit = currentRecipe.getIngredientUnit()[index];
                                String[] units;

                                // Determine if the unit is liquid or weight
                                if (unitConverter.isLiquidUnit(currentUnit)) {
                                    units = new String[]{"ml", "l", "cup", "tsp", "tbsp"};
                                } else if (unitConverter.isWeightUnit(currentUnit)) {
                                    units = new String[]{"mg", "g", "kg", "oz", "lb"};
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Unsupported unit: " + currentUnit);
                                    return;
                                }

                                // Create a dropdown menu for units
                                JComboBox<String> unitComboBox = new JComboBox<>(units);
                                int result = JOptionPane.showConfirmDialog(frame, unitComboBox, "Select the new unit", JOptionPane.OK_CANCEL_OPTION);
                                if (result == JOptionPane.OK_OPTION) {
                                    String newUnit = (String) unitComboBox.getSelectedItem();
                                    if (newUnit != null && !newUnit.isEmpty()) {
                                        currentRecipe.convertSingleIngredientUnit(index, unitConverter, newUnit);
                                        ingredientsDisplayArea.setText(""); // Clear the display
                                        for (int i = 0; i < currentRecipe.getIngredients().length; i++) {
                                            ingredientsDisplayArea.append(currentRecipe.getNumIngredients()[i] + " " +
                                                    currentRecipe.getIngredientUnit()[i] + " of " +
                                                    currentRecipe.getIngredients()[i] + "\n");
                                        }
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(frame, "Invalid index.");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number for the index.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "No recipe to convert.");
                    }
                }
            });
            frame.setVisible(true);
        });
    }
}