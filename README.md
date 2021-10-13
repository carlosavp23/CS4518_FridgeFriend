-We implement our home screen with the SaveActivity which the
user is directed to after they login via the LoginActivity

-From the SaveActivity (the home screen), the user can click
on buttons to navigate to either the list which shows what
food is in my fridge or the shopping list. Clicking on one of
these buttons will direct the user to the ListActivity which
hosts a RecyclerView and shows the user the list that they
navigated to.

-From the ListActivity, the user can add an item to the list 
that they are viewing by pressing on the add item button. The 
add item button will direct the user to the AddFoodActivity.

-In the AddFoodActivity, the user can add a name for their new
food item, an expiration date, and a picture. When the user
presses the submit button, they are directed back to the 
ListActivity which presents the list that they were viewing
when they pressed the add item button (either the Food In My
Fridge list of the Shopping List).

-The LoginActivity Page uses Google OAuth 2.0 to allow users to
log into their Google account.

-We use Model-View-Controller in our app's design and
implementation. We use a Food entity data class which has
a randomly generated UUID, a boolean for if the food item's
checkbox is checked or not, a name as a string, an image as
a string which is the name of the file, and an expiration
as a string. We also use a FoodViewModel to handle data 
storage of our lists. The FoodViewModel contains two
mutable lists of Food items, one for the Food In My
Fridge list and one for the Shopping List.

-This is the link to our GitHub: https://github.com/carlosavp23/CS4518_FridgeFriend