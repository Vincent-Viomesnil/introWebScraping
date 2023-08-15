package recipes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecipeApplication {
    public static void main(String[] args) {

        Epices epices = new Epices();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez les ingrédients (séparés par des virgules) : ");
        String ingredients = scanner.nextLine();
//div id => "div#content"  et div class => ".content"
        String url = "https://www.marmiton.org/recettes/index/categorie/plat-principal/";
        for (int currentPage = 0; currentPage<3; currentPage++) {
            try {
                Document document = Jsoup.connect(url + currentPage).timeout(6000).get();
                Elements recipes = document.select(".recipe-card");

                for (Element recipe : recipes) {
                    String linkRecipe = recipe.select(".recipe-card-link").attr("href"); // Récupérer le lien de la recette
                    Document doc2 = Jsoup.connect(linkRecipe).timeout(6000).get(); // Ouvrir la page de la recette

                    String recipeName = recipe.select(".recipe-card__title").text(); // Récupérer le nom de la recette
                    Elements ingredientRecipe = doc2.select("meta[property=og:description]");

                        String ingredientsList = ingredientRecipe.attr("content"); //récupérer la liste des ingrédients dans une recette
                    System.out.println("content de la recette Avant : " + ingredientsList);

//                    Elements ingredientRecipe = doc2.select(".odescription");
//                    StringBuilder recipeIngredients = new StringBuilder();
//                    for (Element ingredientElement : ingredientRecipe) {
                        String ingredientText = ingredientsList.toLowerCase().trim();
                    System.out.println("ingredientText de la recette Avant : " + ingredientText);

                    String[] ingredientsArray = ingredientText.split(", ");
                    StringBuilder recipeIngredients = new StringBuilder();

                    for (String ingredient : ingredientsArray) {
                        if (!ingredient.contains(epices.epicesListes.toString())){
                            recipeIngredients.append(ingredient).append(", ");
                        }
                    }

                    System.out.println("epicesListes de la recette Avant : " + epices.getEpicesListes().toString());
                    System.out.println("recipeIngredients de la recette Avant : " + recipeIngredients);

                    if (containsExactIngredients(recipeIngredients.toString(), ingredients)) {
                        // Afficher la recette
                        System.out.println("Nom de la recette : " + recipeName);
                        System.out.println("Ingrédients de la recette : " + recipeIngredients);
                        System.out.println("Lien de la recette : " + linkRecipe);
                    } else {
                        System.out.println("Non trouvée: Nom de la recette : " + recipeName);
                        System.out.println("Non trouvée:Ingrédients de la recette : " + recipeIngredients);
                        System.out.println("Non trouvée: Lien de la recette : " + linkRecipe);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean containsExactIngredients(String recipeIngredients, String selectedIngredients) {
        String[] selectedIngredientsArray = selectedIngredients.split(",");
        for (String ingredient : selectedIngredientsArray) {
            if (!recipeIngredients.toLowerCase().contains(ingredient.trim().toLowerCase())) {
                return false; // Si un seul ingrédient n'est pas trouvé, la recette est ignorée
            }
        }
        return true;
    }

}