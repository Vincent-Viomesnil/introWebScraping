package recipes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

public class RecipeApplication {
    public static void main(String[] args) {

        Epices epices = new Epices();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez les ingrédients (séparés par des virgules) : ");
        String ingredients = scanner.nextLine();
//div id => "div#content"  et div class => ".content"
        String url = "https://www.marmiton.org/recettes/index/categorie/plat-principal/";
        for (int currentPage = 0; currentPage<100; currentPage++) {
            try {
                Document document = Jsoup.connect(url + currentPage).timeout(6000).get();
                Elements recipes = document.select(".recipe-card");

                for (Element recipe : recipes) {
                    String linkRecipe = recipe.select(".recipe-card-link").attr("href"); // Récupérer le lien de la recette
                    Document doc2 = Jsoup.connect(linkRecipe).timeout(6000).get(); // Ouvrir la page de la recette

                    String recipeName = recipe.select(".recipe-card__title").text(); // Récupérer le nom de la recette

                    Elements ingredientRecipe = doc2.select(".RCP__sc-8cqrvd-3.itCXhd");
//                    System.out.println("ingredientRecipe" + ingredientRecipe);
                    StringBuilder recipeIngredients = new StringBuilder();
                    for (Element ingredientElement : ingredientRecipe) {
                        String ingredientText = ingredientElement.text();
                        recipeIngredients.append(ingredientText).append(", ");
                    }

                    if (containsAllIngredients(recipeIngredients.toString(), ingredients)) {
                        System.out.println("Nom de la recette : " + recipeName);
//                        System.out.println("Ingrédients : " + ingredients);
                        System.out.println("Ingrédients de la recette : " + recipeIngredients);
                        System.out.println("Lien de la recette : " + linkRecipe);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean containsAllIngredients(String recipeIngredients, String selectedIngredients) {
        String[] selectedIngredientsArray = selectedIngredients.split(",");

        for (String ingredient : selectedIngredientsArray) {
            if (!recipeIngredients.toLowerCase().contains(ingredient.trim().toLowerCase())) {
                return false; // Un ingrédient n'est pas trouvé, la recette est ignorée
            }
        }

        return true; // Tous les ingrédients sont trouvés, la recette est incluse
    }



}