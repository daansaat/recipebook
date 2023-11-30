# Recipe Book Application
This application is made using the Spring Boot framework, with Spring Data JPA for data handling and an H2 in-memory database for storage.

## Architectural Design
The application follows a layered architecture, which separates concerns and promotes maintainability and scalability.

Service Layer (RecipeService): Contains the business logic of the application.

Repository Layer (RecipeRepository and IngredientRepository): Interfaces with the database to perform CRUD operations.

Entity Layer (Recipe and Ingredient): The domain model of the application. These are the JPA entities that map to the database tables.

Data Transfer Object (DTO) (RecipeDto): Facilitates the transfer of data between layers.

## Running the Application
###### Dependency: Docker
Go to the project's root directory in terminal.
Run the application using the following Makefile commands:

| Command     | Description |
|-------------|-------------|
| `make`      | Builds the Docker image using the Dockerfile. |
| `make run`  | Runs the image, creating a new container if it does not exist, or starts the existing container. |
| `make clean`| Removes both the Docker image and the associated container. |

## API Usage
After running the application, you can interact with it by using the provided CommandLineRunner for initial data setup and testing.

### RecipeService methods
findAllIngredients()

    Usage: Retrieve a complete list of all ingredients in the database.
    Returns: A list of Ingredient objects.

findAllRecipe()

    Usage: Fetch all recipes available.
    Returns: A list of Recipe objects.

findRecipeByIngredient(String ingredientName)

    Usage: Look for recipes that include a specific ingredient.
    Returns: A set of Recipe objects containing the specified ingredient. Throws an exception if the ingredient is not found.

addRecipe(RecipeDto newRecipe)

    Usage: Add a new recipe to the database using the provided recipe data.
    Returns: The newly created Recipe object.

updateRecipe(Long recipeId, RecipeDto updatedRecipe)

    Usage: Update an existing recipe identified by recipeId with new details.
    Returns: The updated Recipe object. If the recipe is not found, an exception is thrown.

removeRecipe(Long recipeId)

    Usage: Delete a recipe from the database using its ID.
    Returns: Nothing (void). Throws an exception if the recipe does not exist.

filterRecipe(Boolean vegetarian, Integer servings, Set<String> includedIngredients, Set<String> excludedIngredients, String searchText)

    Usage: Filter recipes based on vegetarian status, number of servings, ingredients to include or exclude, and a text search query.
    Returns: A list of Recipe objects that match the given criteria.
