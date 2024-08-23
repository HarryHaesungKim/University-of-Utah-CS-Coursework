# Project 1: The Lifestyle App

CS 4530: Mobile App Programming<br>
Spring 2023<br>
University of Utah<br>

Harry Kim<br>
Casey Lee<br>
Huy Nguyen<br>
Tarik Vu<br>

# References
  - [Reactive text boxes](https://www.youtube.com/watch?v=IxhIa3eZxz8) 
  - [Navigation bar](https://www.youtube.com/watch?v=YlIHxIAoHzU)
  - [Navigation Drawer](https://www.youtube.com/watch?v=do4vb0MdLFY)

# Discoveries and Decisions
  - Decided on a bottom navigation bar to switch between different fragments within the applicaiton.
  - Tablets will have navigation drawers on the left hand side instead of a navigation bar.
  - Got rid of calories icon. Decided that we didn't need it because the caloric intake is shown at all times.
  - Made a button for showing the caloric intake at all times. When clicked, the user is taken to a page to update their profile information. This way they can easily change not only their activity level, but also other factors like weight, which also affects caloric intake, so they can see how caloric intake changes.
  - Sliders are used for numeric inputs so that the user doesn't have to type in any numbers (e.g. for age, height, weight).
  - The user can share their current location so they don't have to type in the name of their city and country. These fields will be filled in automatically.
  
# Issues
  - Trying to update profile crashes the application - fixed.
  - Rotation to landscape mode hides fields for user input - fixed.
  - Rotation on BMR fragment crashes the application due to a null pointer exception - fixed.
  - Swapping back and forth through the fragments while in landscape mode crashes the app - fixed.
  - Weather information not populating on tablets - fixed.
  - User data is lost when the app is closed out and then restored - fixed.
  - Selected item on the navigation bar is incorrect after the caloric intake button is clicked - fixed.
  - Invalid location inputs (e.g. a country that does not exist) causing the app to crash when geocoding - fixed.
  - Weather information not available when places are given as abbreviations (e.g. "SLC" instead of "Salt Lake City") - fixed.
