package recipes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.util.Scanner;
// ... (imports et déclarations)

public class RecipeApplication {
    public static void main(String[] args) {

        Epices epices = new Epices();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez les ingrédients (séparés par des virgules et un espace après chaque virgule) : ");
        String ingredients = scanner.nextLine();

        String url = "https://www.marmiton.org/recettes/index/categorie/plat-principal/";
        for (int currentPage = 0; currentPage < 3; currentPage++) {
            try {
                Document document = Jsoup.connect(url + currentPage).timeout(6000).get();
                Elements recipes = document.select(".recipe-card");

                for (Element recipe : recipes) {
                    String linkRecipe = recipe.select(".recipe-card-link").attr("href");
                    Document doc2 = Jsoup.connect(linkRecipe).timeout(6000).get();

                    String recipeName = recipe.select(".recipe-card__title").text();
                    Elements ingredientRecipe = doc2.select("meta[property=og:description]");

                    String ingredientsList = ingredientRecipe.attr("content");
                    System.out.println("Ingrédients de la recette Avant : " + ingredientsList);

                    String[] ingredientsArray = ingredientsList.split(", ");
                    StringBuilder recipeIngredients = new StringBuilder();

                    for (String ingredient : ingredientsArray) {
                        ingredient = ingredient.toLowerCase().trim();
                        if (!epices.getEpicesListes().contains(ingredient)) {
                            recipeIngredients.append(ingredient).append(", ");
                        }
                    }

                    System.out.println("Ingrédients filtrés de la recette : " + recipeIngredients);

                    if (containsExactIngredients(recipeIngredients.toString(), ingredients)) {
                        System.out.println("Nom de la recette : " + recipeName);
                        System.out.println("Ingrédients de la recette : " + recipeIngredients);
                        System.out.println("Lien de la recette : " + linkRecipe);
                    } else {
                        System.out.println("Recette ignorée : " + recipeName);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean containsExactIngredients(String recipeIngredients, String selectedIngredients) {
        String[] selectedIngredientsArray = selectedIngredients.split(", ");
        for (String ingredient : selectedIngredientsArray) {
            if (!recipeIngredients.toLowerCase().contains(ingredient.trim().toLowerCase())) {
                return false; // Si un seul ingrédient n'est pas trouvé, la recette est ignorée
            }
        }
        return true;
    }
}
