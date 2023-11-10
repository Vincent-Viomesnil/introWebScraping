package recipes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
// ... (imports et déclarations)

public class RecipeApplication {
    public static void main(String[] args) {


                Epices epices = new Epices();

                Scanner scanner = new Scanner(System.in);
                System.out.print("Entrez les ingrédients (séparés par des virgules et un espace après chaque virgule) : ");
                String ingredients = scanner.nextLine(); //test avec "rôti de boeuf" OK

                String url = "https://www.marmiton.org/recettes/index/categorie/plat-principal/";
                for (int currentPage = 0; currentPage < 1; currentPage++) {
                    try {
                        Document document = Jsoup.connect(url + currentPage).timeout(6000).get();
                        Elements recipes = document.select(".recipe-card"); //récupère l'ensemble des recettes

                        for (Element recipe : recipes) {
                            String linkRecipe = recipe.select(".recipe-card-link").attr("href"); //récupère le lien url de la recette
                            Document doc2 = Jsoup.connect(linkRecipe).timeout(6000).get(); //Permet de se connecter au lien de la recette

                            String recipeName = recipe.select(".recipe-card__title").text(); //récupère le nom de la recette
                            Elements ingredientRecipe = doc2.select("meta[property=og:description]"); //récupère les ingrédients de la recette

                            String ingredientsList = ingredientRecipe.attr("content"); //retourne la liste des ingrédients séparés par une virgule
                            System.out.println("Ingrédients de la recette Avant : " + ingredientsList);

                            String[] ingredientsArray = ingredientsList.split(", ");//retourne un tableau avec la liste des ingrédients séparés par une virgule
                            System.out.println("ingredientsArray : " + Arrays.toString(ingredientsArray));

//                            StringBuilder recipeIngredients = new StringBuilder();
                            List<String> recipeIngredients = new ArrayList<>();

                            for (String ingredient : ingredientsArray) {
                                ingredient = ingredient.toLowerCase().trim();
                                if (!epices.getEpicesListes().contains(ingredient) && containsExactIngredient(ingredient, ingredients)) {
//                                    recipeIngredients.append(ingredient).append(", ");
                                    recipeIngredients.add(ingredient);
                                }
                            }

//                            int wordCount = countWords(recipeIngredients);

                            System.out.println("Ingrédients filtrés de la recette : " + recipeIngredients);
//
//                            if (wordCount == ingredients.length()) {
//                                System.out.println("Nom de la recette : " + recipeName);
//                                System.out.println("Lien de la recette : " + linkRecipe);
//                            } else {

                            if (!recipeIngredients.isEmpty()) {
                                System.out.println("Nom de la recette : " + recipeName);
                                System.out.println("Lien de la recette : " + linkRecipe);
                            } else {
                                System.out.println("Recette ignorée : " + recipeName);
//                                System.out.println(recipeIngredients.length() +"recipeIngredients.length()");
                                System.out.println(recipeIngredients.size() +"recipeIngredients.length()");

                                System.out.println(ingredients.length() + "ingredients.length()");
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            private static boolean containsExactIngredient(String ingredient, String selectedIngredients) {
                String[] selectedIngredientsArray = selectedIngredients.split(", ");
                for (String selectedIngredient : selectedIngredientsArray) {
                    if (ingredient.equalsIgnoreCase(selectedIngredient.trim())) {
                        return true; // Si l'ingrédient correspond exactement, la recette est incluse
                    }
                }
                return false;
            }
//    private static int countWords(StringBuilder text) {
//        String[] words = new String[]{text.toString().trim()};
//        return words.length;
//    }
    private static int countWords(String text) {
        String[] words = new String[]{text.trim()};
        return words.length;
    }

//    private static int countWords(StringBuilder text) {
//        String[] words = text.toString().trim();
//        return words.length;
//    }
        }

