package recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Epices {

    List<String> epicesListes = List.of("citron jaune","câpre", "vin blanc","huile d'olive","eau","beurre","bouillon","cube de bouillon","cubes de bouillon","farine","sel", "poivre", "ail", "cumin", "paprika", "curcuma", "gingembre", "cannelle", "muscade", "coriandre", "cardamome", "clou de girofle", "anis", "safran", "piment de Cayenne", "thym", "romarin", "origan", "laurier", "estragon", "basilic", "menthe", "persil", "ciboulette", "fenugrec", "marjolaine", "curcuma", "masala", "carvi", "noix de muscade", "aneth", "piment", "poudre de chili", "piment de la Jamaïque", "quatre-épices", "fenouil", "céleri", "graines de moutarde", "paprika fumé", "curcuma", "poivre de Sichuan", "épices pour tarte à la citrouille", "piment de la Jamaïque", "sumac", "estragon", "marjolaine", "persil", "sarriette", "fenugrec", "macis"
    );

    public List<String> getEpicesListes() {
        return epicesListes;
    }

    public void setEpicesListes(List<String> epicesListes) {
        this.epicesListes = epicesListes;
    }

    public boolean containsAnySpice(String recipeIngredients) {
        for (String spice : epicesListes) {
            if (recipeIngredients.toLowerCase().contains(spice.trim().toLowerCase())) {
                return true; // Au moins une épice est trouvée, la recette contient une épice
            }
        }
        return false; // Aucune épice n'est trouvée, la recette ne contient pas d'épices
    }
}
