package api.model.oder;

public class CreateOrderRequestModel {
    private String[] ingredients;

    public CreateOrderRequestModel(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public CreateOrderRequestModel() {
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
