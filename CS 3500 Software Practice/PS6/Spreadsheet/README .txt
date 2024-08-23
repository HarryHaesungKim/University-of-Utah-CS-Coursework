3/19/2021
Authors: Braden Morfin & Harry Kim

When calculating a cells value and updating its dependents cells we did this using multithreading. We chose
to do this to hopefully decrease the amount of time it took.

We added two extra features, the first allows the user to nagivate between cells using the arrows keys and this
took us about an hour and half to figure out. The next was a clear button that would clear the spreadsheet, this only
took us about 20 minutes to implement. We had gotten stuck on how to use the proccessCmdKeys and how to use it, but 
after we finally figured it out it was easy. The clear button was also a bit harder than we thought and we had gotten 
stuck on how to clear the cells that were currently non-empty.

We used these websites to help with certain bits of code where we got stuck, including on how to use saveFileDialogs
and openFileDialogs. We also searched ways of being able to use the arrow keys to navigate between cells. The main 
site that we used was Microsofts documentation of the classes and methods to gain a basic understanidng and use
the examples provided.
http://net-informations.com/q/faq/arrowkeys.html
https://docs.microsoft.com/en-us/dotnet/desktop/winforms/controls/how-to-save-files-using-the-savefiledialog-component?view=netframeworkdesktop-4.8
https://docs.microsoft.com/en-us/dotnet/api/system.windows.forms.openfiledialog?view=net-5.0

We also used the code given to us in the examples repo, including the Ps6Skeleton code, as well as the demo code that
allows the user to open a new window and have multiple windows open at once. 

